<template>
    <ValidationObserver ref="obs" v-slot="{ invalid, validated }">
        <h2 style="text-align: center" v-if="!isChecked">회원정보 확인</h2>
        <b-card v-if="!isChecked" class="mt-5" style="max-width: 50rem; float: none; margin: 0 auto">
            <b-card-title style="text-align: center" class="mt-2"
                >유저정보 확인을 위해 비밀번호를 재입력 해주세요</b-card-title
            >

            <b-container fluid class="my-4">
                <b-row style="float: none; margin: 100 auto">
                    <b-col class="col-lg-9">
                        <input
                            class="form-control"
                            placeholder="비밀번호를 입력해주세요"
                            type="password"
                            v-model="password"
                            width="100%"
                        />
                    </b-col>
                    <b-col>
                        <button class="ml-2 btn btn-outline-secondary" @click="verifyUser">입력</button>
                    </b-col>
                </b-row>
            </b-container>
        </b-card>
        <form v-else>
            <div class="m-5">
                <b-row align-v="center" style="float: none; margin: 0 auto">
                    <b-card style="max-width: 100rem; float: none; margin: 0 auto">
                        <b-card-title class="text-center">사용자 정보 확인 및 수정</b-card-title>
                        <!--email 유효성검사-->
                        Email<b-col sm="9"><b-input class="m-3" name="email" v-model="id" readonly> </b-input></b-col
                        ><br />

                        <!--password 유효성검사-->
                        <validation-provider name="비밀번호" rules="required|limit|password" v-slot="{ errors, valid }">
                            비밀번호<b-col sm="9"
                                ><b-input
                                    v-model="password"
                                    name="pw"
                                    class="m-3"
                                    placeholder="user-password"
                                    :error-messages="errors"
                                    :success="valid"
                                    required
                                    type="password"
                                >
                                </b-input
                            ></b-col>
                            <span class="text-danger">{{ errors }}</span
                            ><br /><br />
                        </validation-provider>
                        <!--password 체크 -->
                        <validation-provider
                            name="비밀번호재확인"
                            rules="confirmed:비밀번호"
                            v-slot="{ errors, valid }"
                        >
                            비밀번호 재확인<b-col sm="9"
                                ><b-input
                                    v-model="passwordCheck"
                                    name="passwordCheck"
                                    class="m-3"
                                    placeholder="비밀번호와 같은 값을 입력해 주세요"
                                    :error-messages="errors"
                                    :success="valid"
                                    required
                                    type="password"
                                >
                                </b-input
                            ></b-col>
                            <span class="text-danger">{{ errors }}</span
                            ><br /><br />
                        </validation-provider>

                        <validation-provider name="별명" rules="max:40|required" v-slot="{ errors, valid }">
                            별명<b-row
                                ><b-col sm="9"
                                    ><b-input
                                        class="m-3"
                                        name="nickname"
                                        v-model="nickname"
                                        placeholder="프로필로 사용할 별명(닉네임)을 입력해 주세요."
                                        :error-messages="errors"
                                        :success="valid"
                                        required
                                    >
                                    </b-input></b-col
                                ><b-col sm="2"
                                    ><b-button
                                        style="margin: 100 auto"
                                        class="mx-5"
                                        width="100%"
                                        variant="outline-secondary"
                                        @click="nicknameDuplicateCheck"
                                        >[nickname]중복체크</b-button
                                    ></b-col
                                >
                            </b-row>
                            <span class="text-warning">{{ errors }}</span
                            ><br /><br />
                        </validation-provider>

                        <b-button class="btn-outline-primary" @click="modifyMember" :disabled="invalid || !validated"
                            >회원정보 수정</b-button
                        >
                        <b-button class="btn-outline-danger mx-3" @click="deleteUser"> 회원탈퇴 </b-button>
                    </b-card>
                </b-row>
            </div>
        </form>
    </ValidationObserver>
</template>

<script>
import { ValidationProvider, ValidationObserver } from "vee-validate";
import http from "@/api/http";
import { mapState, mapActions } from "vuex";
const memberStore = "memberStore";

export default {
    name: "RegisterForm",
    data() {
        return {
            email: "",
            password: "",
            passwordCheck: "",
            name: "",
            nickname: "",
            isChecked: false,
            nicknameChecked: false,
        };
    },
    components: {
        ValidationProvider,
        ValidationObserver,
    },
    computed: {
        ...mapState(memberStore, ["isLogin", "userInfo"]),
    },
    methods: {
        ...mapActions(memberStore, ["getUserInfo", "modifyUser"]),
        modifyMember() {
            let member = {
                id: this.userInfo.id,
                password: this.password,
                nickname: this.nickname,
            };
            if (this.userInfo.nickname === this.nickname || this.nicknameChecked) {
                http.post("user/modify", JSON.stringify(member))
                    .then(({ data }) => {
                        alert("수정이 완료되었습니다!");
                        this.modifyUser(this.nickname);
                        console.log(data);
                        window.location.reload();
                    })
                    .catch();
            } else {
                alert("별명을 다시 확인해주세요");
            }
        },

        verifyUser() {
            let member = {
                id: this.userInfo.id,
                password: this.password,
            };
            console.log(member);
            http.post("user/pwCheck", JSON.stringify(member))
                .then(({ data }) => {
                    console.log(data.message);
                    if (data.message === "verified") {
                        alert("유저 확인!");
                        this.isChecked = true;
                    } else {
                        alert("비밀번호를 다시 확인해주세요");
                    }
                })
                .catch();
        },
        ...mapActions(memberStore, ["userLogout"]),
        deleteUser() {
            let member = {
                id: this.userInfo.id,
                password: this.userInfo.password,
                nickname: this.userInfo.nickname,
            };
            let deleteCheck1 = confirm("정말 탈퇴하시겠습니까?");
            if (deleteCheck1) {
                let userInput = prompt("다음과 같이 입력해주세요\n[ 예 탈퇴하겠습니다 ]");
                if (userInput === "예 탈퇴하겠습니다") {
                    http.post("user/delete", JSON.stringify(member))
                        .then(({ data }) => {
                            if (data.message === "success") {
                                alert("회원 탈퇴가 완료되었습니다. 이용해주셔서 감사합니다 :)");
                                console.log(data);
                                this.userLogout(this.userInfo.userid);
                                sessionStorage.removeItem("access-token"); //저장된 토큰 없애기
                                sessionStorage.removeItem("refresh-token"); //저장된 토큰 없애기
                                if (this.$route.path != "/") this.$router.push({ name: "home" });
                            } else {
                                console.log("ERROR 삭제 불가");
                                alert("ERROR : 삭제되지 않았습니다.");
                            }
                        })
                        .catch();
                } else {
                    alert("텍스트 입력이 잘못되었습니다.");
                }
            }
        },
        nicknameDuplicateCheck() {
            let member = {
                nickname: this.nickname,
            };
            http.post("user/nicknamecheck", JSON.stringify(member))
                .then(({ data }) => {
                    console.log(data);
                    if (this.nickname === "") {
                        alert("별명을 입력해주세요");
                    } else if (data) {
                        alert("이미 가입된 별명입니다.");
                    } else {
                        alert("사용할 수 있는 별명입니다!");
                        this.nicknameChecked = true;
                    }
                })
                .catch();
        },
    },
    created() {
        this.id = this.userInfo.id;
        this.nickname = this.userInfo.nickname;
    },
};
</script>

<style></style>
