import http from "@/api/http";
import SampleImg1 from "@/assets/sample.jpg";
import SampleImg2 from "@/assets/sample_2.jpg";
import SampleImg3 from "@/assets/sample_3.jpg";
import SampleImg4 from "@/assets/sample_4.jpg";
import SampleImg5 from "@/assets/sample_5.jpg";
import SampleImg6 from "@/assets/sample_6.jpg";
import SampleImg7 from "@/assets/sample_7.jpg";
import SampleImg8 from "@/assets/sample_8.jpg";
import SampleImg9 from "@/assets/sample_9.jpg";
import SampleImg10 from "@/assets/sample_10.jpg";

const houseStore = {
    namespaced: true,
    state: {
        sidos: [{ value: null, text: "시도 선택" }],
        guguns: [{ value: null, text: "구군 선택" }],
        dongs: [{ values: null, text: "동 선택" }],
        images: [
            SampleImg1,
            SampleImg2,
            SampleImg3,
            SampleImg4,
            SampleImg5,
            SampleImg6,
            SampleImg7,
            SampleImg8,
            SampleImg9,
            SampleImg10,
        ],
        favoriteList: [{ value: null, text: "관심 매물을 선택하세요" }],
        houses: [],
        house: null,
        showResult: false,
        checkVal: 0,
        center: null,
        houseInfo: {},
        showSchool: null,
        avgDeal: [26250, 28469, 31369, 32419, 35266, 36210, 34422, 28916],
        dongAvg: [],
        years: [
            { value: null, text: "년도 선택" },
            { value: 2015, text: "2015" },
            { value: 2016, text: "2016" },
            { value: 2017, text: "2017" },
            { value: 2018, text: "2018" },
            { value: 2019, text: "2019" },
            { value: 2020, text: "2020" },
            { value: 2021, text: "2021" },
            { value: 2022, text: "2022" },
        ],
        months: [
            { value: null, text: "월 선택" },
            { value: 1, text: "1" },
            { value: 2, text: "2" },
            { value: 3, text: "3" },
            { value: 4, text: "4" },
            { value: 5, text: "5" },
            { value: 6, text: "6" },
            { value: 7, text: "7" },
            { value: 8, text: "8" },
            { value: 9, text: "9" },
            { value: 10, text: "10" },
            { value: 11, text: "11" },
            { value: 12, text: "12" },
        ],
        dealLog: [["Year", "선택 매물 거래가", "동 평균 거래가", "전국 평균 거래가"]],
    },
    getters: {},
    mutations: {
        CLEAR_SIDO_LIST(state) {
            state.sidos = [{ value: null, text: "시도 선택" }];
        },
        CLEAR_GUGUN_LIST(state) {
            state.guguns = [{ value: null, text: "구군 선택" }];
        },
        CLEAR_DONG_LIST(state) {
            state.dongs = [{ value: null, text: "동 선택" }];
        },
        CLEAR_HOUSE_LIST(state) {
            state.houses = [];
            state.house = null;
        },
        CLEAR_FAVORITE_LIST(state) {
            state.favoriteList = [{ value: null, text: "관심 매물을 선택하세요" }];
        },
        SET_SIDO_LIST(state, sidos) {
            sidos.forEach((sido) => {
                state.sidos.push({ value: sido.sidoCode, text: sido.sidoName });
            });
        },
        SET_GUGUN_LIST(state, guguns) {
            guguns.forEach((gugun) => {
                state.guguns.push({ value: gugun.gugunCode, text: gugun.gugunName });
            });
        },
        SET_DONG_LIST(state, dongs) {
            dongs.forEach((dong) => {
                state.dongs.push({ value: dong.dongCode, text: dong.dongName });
            });
        },
        SET_HOUSE_LIST(state, houses) {
            state.houses = houses;
            state.showResult = true;
            state.checkVal++;
        },
        SET_DETAIL_HOUSE(state, house) {
            state.house = house;
        },
        SET_SHOW_RESULT(state) {
            state.showResult = false;
        },
        SET_FAVORITE_LIST(state, data) {
            data.forEach((item) => {
                state.favoriteList.push({
                    value: item.aptCode,
                    text: item.roadName + " " + item.aptName,
                });
            });
        },
        SET_CENTER_LOCATION(state, arr) {
            state.center = arr;
        },
        SET_HOUSE_INFO(state, data) {
            state.houseInfo = data;
        },
        SET_SHOW_SCHOOL(state) {
            state.showSchool = { tg: true };
        },
        SET_HIDE_SCHOOL(state) {
            state.showSchool = { tg: false };
        },
        SET_DEAL_LOG(state, data) {
            data.forEach((item, index) => {
                //console.log(state.dongAvg[index]);
                state.dealLog.push([
                    item.year,
                    parseInt(item.price),
                    parseInt(state.dongAvg[index]),
                    parseInt(state.avgDeal[index]),
                ]);
            });
            //console.log(state.dealLog);
        },
        CLEAR_DEAL_LOG(state) {
            state.dealLog = [["Year", "선택 매물 거래가", "동 평균 거래가", "전국 평균 거래가"]];
        },
        CLEAR_DONG_AVG(state) {
            state.dongAvg = [];
        },
        SET_DONG_AVG(state, data) {
            //console.log(data);
            data.forEach((item) => {
                state.dongAvg.push(item.price);
            });
        },
    },
    actions: {
        getSido: ({ commit }) => {
            http.post("/house/getSido")
                .then(({ data }) => {
                    commit("SET_SIDO_LIST", data);
                })
                .catch();
        },
        getGugun: ({ commit }, sidoCode) => {
            http.post("/house/getGugun", { sidoCode: sidoCode })
                .then(({ data }) => {
                    commit("SET_GUGUN_LIST", data);
                })
                .catch();
        },
        getDong: ({ commit }, gugunCode) => {
            http.post("/house/getDong", { gugunCode: gugunCode })
                .then(({ data }) => {
                    commit("SET_DONG_LIST", data);
                })
                .catch();
        },
        getHouseList: ({ commit }, info) => {
            http.post("/house/getHouseList", info)
                .then(({ data }) => {
                    //console.log(data);
                    commit("SET_HOUSE_LIST", data);
                })
                .catch();
        },
        getFavorite: ({ commit }, user) => {
            http.post("/house/getFavorite", { nickname: user })
                .then(({ data }) => {
                    commit("SET_FAVORITE_LIST", data);
                })
                .catch();
        },
        getAptInfo: ({ commit }, code) => {
            http.post("/house/getAptInfo", { aptCode: code })
                .then(({ data }) => {
                    //console.log(data);
                    commit("SET_HOUSE_INFO", data);
                })
                .catch();
        },
        getDealLog: ({ commit }, code) => {
            http.post("/house/getDealLog", { aptCode: code }).then(({ data }) => {
                //console.log(data);
                commit("SET_DEAL_LOG", data);
            });
        },
        getAvgDong: ({ commit }, code) => {
            http.post("/house/getAvgDong", { dongCode: code }).then(({ data }) => {
                //console.log(data);
                commit("SET_DONG_AVG", data);
            });
        },
    },
};

export default houseStore;
