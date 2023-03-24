const boardStore = {
    namespaced: true,
    state: {
        code: "",
    },
    getters: {},
    mutations: {
        SET_CODE(state, no) {
            state.code = no;
        },
    },
    actions: {
        setCode: ({ commit }, no) => {
            commit("SET_CODE", no);
        },
    },
};

export default boardStore;
