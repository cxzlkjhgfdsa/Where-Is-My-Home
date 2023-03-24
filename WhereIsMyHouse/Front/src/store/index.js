import Vue from "vue";
import Vuex from "vuex";
import createPersistedState from "vuex-persistedstate";
import boardStore from "@/store/modules/boardStore";
import houseStore from "@/store/modules/houseStore";
import memberStore from "@/store/modules/memberStore";
Vue.use(Vuex);

export default new Vuex.Store({
    modules: {
        boardStore,
        houseStore,
        memberStore,
    },
    plugins: [
        createPersistedState({
            storage: sessionStorage,
        }),
    ],
});
