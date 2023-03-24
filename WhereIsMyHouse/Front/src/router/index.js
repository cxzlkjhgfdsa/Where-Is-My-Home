import Vue from "vue";
import VueRouter from "vue-router";
import HomeView from "../views/HomeView.vue";
import store from "@/store";

Vue.use(VueRouter);

// 라우터 가드
const onlyAuthUser = async (to, from, next) => {
    const checkUserInfo = store.getters["memberStore/checkUserInfo"];
    const checkToken = store.getters["memberStore/checkToken"];
    let token = sessionStorage.getItem("access-token");
    console.log("로그인 처리 전", checkUserInfo, token);

    if (checkUserInfo != null && token) {
        console.log("토큰 유효성 체크하러 가자!!!!");
        await store.dispatch("memberStore/getUserInfo", token);
    }
    if (!checkToken || checkUserInfo === null) {
        alert("로그인이 필요한 페이지입니다..");
        // next({ name: "login" });
        router.push({ name: "login" });
    } else {
        console.log("로그인 했다!!!!!!!!!!!!!.");
        next();
    }
};

const routes = [
    {
        path: "/",
        name: "home",
        component: HomeView,
    },
    {
        path: "/HeaderBar",
        name: "HeaderBar",
        component: () => import("../components/commons/HeaderBar.vue"),
    },
    {
        path: "/board",
        name: "BoardView",
        component: () => import("../views/BoardView.vue"),
        redirect: "/board/boardlist",
        children: [
            {
                path: "boardlist",
                name: "BoardList",
                beforeEnter: onlyAuthUser,
                component: () => import("../components/board/BoardList.vue"),
            },
            {
                path: "boardwrite",
                name: "BoardWrite",
                beforeEnter: onlyAuthUser,
                component: () => import("../components/board/BoardWrite.vue"),
            },
            {
                path: "boarddetail",
                name: "BoardDetail",
                component: () => import("../components/board/BoardDetail.vue"),
            },
            {
                path: "boardmodify",
                name: "BoardModify",
                component: () => import("../components/board/BoardModify.vue"),
            },
        ],
    },
    {
        path: "/houseSearch",
        name: "HouseSearchView",
        component: () => import("../views/HouseSearchView.vue"),
    },
    {
        path: "/favoriteView",
        name: "FavoriteView",
        component: () => import("../views/FavoriteView.vue"),
    },
    {
        path: "/login",
        name: "login",
        component: () => import("@/views/LoginView.vue"),
    },
    {
        path: "/register",
        name: "register",
        component: () => import("@/views/RegisterView.vue"),
    },
    {
        path: "/userinfo",
        name: "userinfo",
        beforeEnter: onlyAuthUser,
        component: () => import("@/views/UserInfoView.vue"),
    },
];

const router = new VueRouter({
    mode: "history",
    base: process.env.BASE_URL,
    routes,
});

export default router;
