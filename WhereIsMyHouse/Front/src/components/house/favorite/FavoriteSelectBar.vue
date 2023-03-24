<template>
    <b-container fluid>
        <b-row>
            <b-col>
                <b-form-select
                    v-model="selected"
                    :options="favoriteList"
                    class="form-select mt-4"
                ></b-form-select>
            </b-col>
            <b-col class="mt-4">
                <input
                    type="checkbox"
                    class="btn-check"
                    id="btn-check-outlined"
                    autocomplete="off"
                />
                <label
                    class="btn btn-outline-warning mx-4"
                    for="btn-check-outlined"
                    @click="toggle"
                    >{{ status }}</label
                >
                <button class="btn btn-outline-info float-end" @click="deleteFavorite">
                    관심 매물 삭제
                </button>

                <button v-b-modal.modal-1 class="btn btn-outline-primary" v-if="showDeal">
                    거래 내역 보기
                </button>

                <b-modal id="modal-1" size="xl" title="거래가 변동 내역">
                    <GChart :type="type" :data="dealLog" :options="options" />
                </b-modal>
            </b-col>
        </b-row>
    </b-container>
</template>

<script>
import { mapState, mapMutations, mapActions } from "vuex";
import http from "@/api/http";
import { GChart } from "vue-google-charts/legacy";

const houseStore = "houseStore";
export default {
    name: "favoriteSelectBar",
    data() {
        return {
            selected: null,
            status: "주변 학교 숨김",
            tg: false,
            modal: false,
            type: "LineChart",
            options: {
                title: "최근 8년간 변동 평균 가격",
                curveType: "function",
                legend: { position: "bottom" },
                width: 1100,
                height: 600,
            },
            checked: 0,
        };
    },
    components: {
        GChart,
    },
    created() {
        let user = this.userInfo.nickname;
        this.CLEAR_DEAL_LOG();
        this.CLEAR_FAVORITE_LIST();
        this.getFavorite(user);
    },
    computed: {
        ...mapState(houseStore, ["favoriteList", "dealLog", "houseInfo", "dongAvg"]),
        ...mapState("memberStore", ["userInfo"]),
        showDeal() {
            if (this.dealLog.length > 1) {
                return true;
            } else {
                return false;
            }
        },
    },
    methods: {
        ...mapActions(houseStore, ["getFavorite", "getAptInfo", "getDealLog", "getAvgDong"]),
        ...mapMutations(houseStore, [
            "CLEAR_FAVORITE_LIST",
            "SET_SHOW_SCHOOL",
            "SET_HIDE_SCHOOL",
            "CLEAR_DEAL_LOG",
            "CLEAR_DONG_AVG",
        ]),
        toggle() {
            if (this.tg) {
                this.tg = false;
                this.status = "주변 학교 숨김";
                this.SET_HIDE_SCHOOL();
            } else {
                this.tg = true;
                this.status = "주변 학교 보임";
                this.SET_SHOW_SCHOOL();
            }
        },
        deleteFavorite() {
            let data = { nickname: this.userInfo.nickname, aptCode: this.selected };
            http.post("house/deleteFavorite", data)
                .then(({ data }) => {
                    alert(data.message);
                    location.reload();
                })
                .catch();
        },
        openModal() {
            this.modal = true;
        },
        closeModal() {
            this.modal = false;
        },
    },
    watch: {
        async selected() {
            this.CLEAR_DEAL_LOG();
            this.CLEAR_DONG_AVG();
            await this.getAptInfo(this.selected);
            await this.getAvgDong(this.houseInfo.dongCode);
        },
        dongAvg() {
            if (this.dongAvg.length == 8) {
                this.checked++;
            }
        },
        checked() {
            this.getDealLog(this.selected);
        },
    },
};
</script>

<style></style>
