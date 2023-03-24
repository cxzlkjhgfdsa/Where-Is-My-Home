<template>
    <div class="headerBar">
        <b-navbar toggleable="sm" type="light" variant="light">
            <span class="mx-3"></span>
            <b-icon icon="house" class="px-3" @click="moveHome"></b-icon>
            <b-navbar-brand class="px-1" @click="moveHome">Where Is my Home</b-navbar-brand>

            <b-navbar-nav class="col-lg-8">
                <b-nav-text class="px-2" @click="moveSearch()">실거래가 조회</b-nav-text>
                <b-nav-text class="px-2" @click="moveBoard()">자유 게시판</b-nav-text>
                <b-nav-text class="px-2" @click="moveFavorite()" v-show="userInfo"
                    >관심매물 확인</b-nav-text
                >
            </b-navbar-nav>

            <b-navbar-nav class="col-lg text-warning" v-if="userInfo">
                <span class="mt-1">{{ userInfo.nickname }} 님 </span>
                <button class="btn btn-outline-secondary mx-2 px-2 pt-1" @click="memberInfo">
                    회원정보
                </button>
                <button
                    class="btn btn-outline-secondary mx-2 px-2 pt-1"
                    @click.prevent="onClickLogout"
                >
                    로그아웃
                </button>
            </b-navbar-nav>

            <b-navbar-nav class="col-lg" v-show="userInfo == null">
                <button class="btn btn-outline-secondary mx-2 px-2 pt-1" @click="login">
                    sign in
                </button>
                <button class="btn btn-outline-secondary mx-2 px-2 pt-1" @click="register">
                    sign up
                </button>
            </b-navbar-nav>
        </b-navbar>
    </div>
</template>

<script>
import { mapState, mapGetters, mapActions } from "vuex";
const memberStore = "memberStore";

export default {
    name: "headerBar",
    methods: {
        moveBoard() {
            if (this.$route.path !== "/board/boardlist") this.$router.push({ name: "BoardView" });
        },
        moveHome() {
            if (this.$route.path !== "/") this.$router.push({ name: "home" });
        },
        moveSearch() {
            if (this.$route.path !== "/houseSearch") this.$router.push({ name: "HouseSearchView" });
        },
        moveFavorite() {
            if (this.$route.path !== "/favoriteView") this.$router.push({ name: "FavoriteView" });
        },
        /////////////////////////////////////////////////////
        login() {
            this.$router.push({ name: "login" });
        },
        register() {
            this.$router.push({ name: "register" });
        },
        memberInfo() {
            this.$router.push({ name: "userinfo" });
        },
        ...mapActions(memberStore, ["userLogout"]),
        // ...mapMutations(memberStore, ["SET_IS_LOGIN", "SET_USER_INFO"]),
        onClickLogout() {
            // this.SET_IS_LOGIN(false);
            // this.SET_USER_INFO(null);
            // sessionStorage.removeItem("access-token");
            // if (this.$route.path != "/") this.$router.push({ name: "main" });
            console.log(this.userInfo.userid);
            alert("로그아웃 되었습니다. 감사합니다.");
            //vuex actions에서 userLogout 실행(Backend에 저장 된 리프레시 토큰 없애기
            //+ satate에 isLogin, userInfo 정보 변경)
            // this.$store.dispatch("userLogout", this.userInfo.userid);
            this.userLogout(this.userInfo.userid);
            sessionStorage.removeItem("access-token"); //저장된 토큰 없애기
            sessionStorage.removeItem("refresh-token"); //저장된 토큰 없애기
            if (this.$route.path != "/") this.$router.push({ name: "home" });
        },
    },
    computed: {
        ...mapState(memberStore, ["isLogin", "userInfo"]),
        ...mapGetters(["checkUserInfo"]),
    },
};
</script>

<style>
.headerBar {
    box-shadow: 0 4px 4px -4px black;
}
</style>
