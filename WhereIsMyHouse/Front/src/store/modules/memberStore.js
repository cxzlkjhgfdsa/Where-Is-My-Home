import jwtDecode from "jwt-decode";
import router from "@/router";
import { login, findById, tokenRegeneration, logout } from "@/api/member";
import store from "..";

const memberStore = {
    namespaced: true,
    state: {
        nickname: null,
        id: null,
        isLogin: false,
        isLoginError: false,
        userInfo: null,
        isValidToken: false,
        retry: 0,
    },
    getters: {
        checkUserInfo: function (state) {
            return state.userInfo;
        },
        checkToken: function (state) {
            return state.isValidToken;
        },
    },
    mutations: {
        SET_IS_LOGIN: (state, isLogin) => {
            state.isLogin = isLogin;
        },
        SET_IS_LOGIN_ERROR: (state, isLoginError) => {
            state.isLoginError = isLoginError;
        },
        SET_IS_VALID_TOKEN: (state, isValidToken) => {
            state.isValidToken = isValidToken;
        },
        SET_USER_INFO: (state, userInfo) => {
            state.isLogin = true;
            state.userInfo = userInfo;
        },
    },
    actions: {
        async userConfirm({ commit }, user) {
            await login(
                user,
                ({ data }) => {
                    if (data.message === "success") {
                        let accessToken = data["access-token"];
                        let refreshToken = data["refresh-token"];
                        // console.log("login success token created!!!! >> ", accessToken, refreshToken);
                        commit("SET_IS_LOGIN", true);
                        commit("SET_IS_LOGIN_ERROR", false);
                        commit("SET_IS_VALID_TOKEN", true);
                        sessionStorage.setItem("access-token", accessToken);
                        sessionStorage.setItem("refresh-token", refreshToken);
                    } else if (data.message === "noid") {
                        alert("해당하는 아이디가 없습니다. 이메일주소를 다시 확인해주세요");
                        console.log(data.message);
                        // commit("SET_IS_LOGIN", false);
                        // commit("SET_IS_LOGIN_ERROR", true);
                        // commit("SET_IS_VALID_TOKEN", false);
                    } else {
                        store.state.retry = data.retry;
                        commit("SET_IS_LOGIN", false);
                        commit("SET_IS_LOGIN_ERROR", true);
                        commit("SET_IS_VALID_TOKEN", false);

                        if (store.state.retry >= 5) {
                            alert(`비밀번호 5회 이상 오입력으로 계정이 임시 정지되었습니다`);
                            // 로그인 횟수가 5회 이상이면
                        } else {
                            // 로그인 횟수가 5회 미만이면
                            //console.log(`데이터 : ${this.$store}`);
                            alert(`비밀번호를 다시 확인해주세요!  현재 틀린횟수 : ${store.state.retry}회`);
                        }
                    }
                },
                (error) => {
                    console.log(error);
                }
            );
        },
        async getUserInfo({ commit, dispatch }, token) {
            let decodeToken = jwtDecode(token);
            console.log("2. getUserInfo() decodeToken :: ", decodeToken);
            await findById(
                decodeToken.id,
                ({ data }) => {
                    if (data.message === "success") {
                        commit("SET_USER_INFO", data.userInfo);
                        console.log("3. getUserInfo data >> ", data);

                        store.state.id = data.userInfo.id;
                        store.state.nickname = data.userInfo.nickname;
                        //console.log(store.state.id + "   " + store.state.nickname);
                    } else {
                        console.log("유저 정보 없음!!!!");
                    }
                },
                async (error) => {
                    console.log("getUserInfo() error code [토큰 만료되어 사용 불가능.] ::: ", error.response.status);
                    commit("SET_IS_VALID_TOKEN", false);
                    await dispatch("tokenRegeneration");
                }
            );
        },
        async tokenRegeneration({ commit, state }) {
            console.log("토큰 재발급 >> 기존 토큰 정보 : {}", sessionStorage.getItem("access-token"));
            await tokenRegeneration(
                JSON.stringify(state.userInfo),
                ({ data }) => {
                    if (data.message === "success") {
                        let accessToken = data["access-token"];
                        console.log("재발급 완료 >> 새로운 토큰 : {}", accessToken);
                        sessionStorage.setItem("access-token", accessToken);
                        commit("SET_IS_VALID_TOKEN", true);
                    }
                },
                async (error) => {
                    // HttpStatus.UNAUTHORIZE(401) : RefreshToken 기간 만료 >> 다시 로그인!!!!
                    if (error.response.status === 401) {
                        console.log("갱신 실패");
                        // 다시 로그인 전 DB에 저장된 RefreshToken 제거.
                        await logout(
                            state.userInfo.id,
                            ({ data }) => {
                                if (data.message === "success") {
                                    console.log("리프레시 토큰 제거 성공");
                                } else {
                                    console.log("리프레시 토큰 제거 실패");
                                }
                                alert("RefreshToken 기간 만료!!! 다시 로그인해 주세요.");
                                commit("SET_IS_LOGIN", false);
                                commit("SET_USER_INFO", null);
                                commit("SET_IS_VALID_TOKEN", false);
                                store.state.id = null;
                                store.state.nickname = null;
                                //console.log(store.state.id + "   " + store.state.nickname);
                                router.push({ name: "login" });
                            },
                            (error) => {
                                console.log(error);
                                commit("SET_IS_LOGIN", false);
                                commit("SET_USER_INFO", null);
                            }
                        );
                    }
                }
            );
        },
        async userLogout({ commit }, id) {
            await logout(
                id,
                ({ data }) => {
                    if (data.message === "success") {
                        commit("SET_IS_LOGIN", false);
                        commit("SET_USER_INFO", null);
                        commit("SET_IS_VALID_TOKEN", false);
                        store.state.id = null;
                        store.state.nickname = null;
                        //console.log(store.state.id + "   " + store.state.nickname);
                    } else {
                        console.log("유저 정보 없음!!!!");
                    }
                },
                (error) => {
                    console.log(error);
                }
            );
        },
        async modifyUser({ commit }, nickname) {
            console.log(commit);
            console.log(nickname);
            store.state.nickname = nickname;
            //console.log(store.state.id + "   " + store.state.nickname);
        },
    },
};

export default memberStore;
