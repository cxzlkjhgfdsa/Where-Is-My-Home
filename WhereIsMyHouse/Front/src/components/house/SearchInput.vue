<template>
    <b-row class="mt-4 mb-4 px-4">
        <b-col class="mr-4 col-lg-1">
            <b-form-select
                v-model="sidoCode"
                :options="sidos"
                @change="gugunList"
                class="form-select"
            ></b-form-select>
        </b-col>
        <b-col class="col-lg-1">
            <b-form-select
                v-model="gugunCode"
                :options="guguns"
                @change="dongList"
                class="form-select"
            ></b-form-select>
        </b-col>
        <b-col class="col-lg-1">
            <b-form-select v-model="dongCode" :options="dongs" class="form-select"></b-form-select>
        </b-col>
        <b-col class="col-lg-1">
            <b-form-select v-model="year" :options="years" class="form-select"></b-form-select>
        </b-col>
        <b-col class="col-lg-1">
            <b-form-select v-model="month" :options="months" class="form-select"></b-form-select>
        </b-col>
        <b-col>
            <button class="btn btn-outline-info" @click="searchHouse">검색</button>
        </b-col>
    </b-row>
</template>

<script>
import { mapState, mapActions, mapMutations } from "vuex";
const houseStore = "houseStore";

export default {
    name: "searchInput",
    data() {
        return {
            sidoCode: null,
            gugunCode: null,
            dongCode: null,
            year: null,
            month: null,
        };
    },
    computed: {
        ...mapState(houseStore, ["sidos", "guguns", "dongs", "houses", "years", "months"]),
    },
    created() {
        this.CLEAR_SIDO_LIST();
        this.CLEAR_HOUSE_LIST();
        this.CLEAR_GUGUN_LIST();
        this.CLEAR_DONG_LIST();
        this.getSido();
    },
    methods: {
        ...mapActions(houseStore, ["getSido", "getGugun", "getDong", "getHouseList"]),
        ...mapMutations(houseStore, [
            "CLEAR_SIDO_LIST",
            "CLEAR_GUGUN_LIST",
            "CLEAR_HOUSE_LIST",
            "CLEAR_DONG_LIST",
        ]),
        gugunList() {
            this.CLEAR_GUGUN_LIST();
            this.gugunCode = null;
            if (this.sidoCode) {
                this.getGugun(this.sidoCode);
            }
        },
        dongList() {
            this.CLEAR_DONG_LIST();
            this.dongCode = null;
            if (this.gugunCode) {
                this.getDong(this.gugunCode);
            }
        },
        searchHouse() {
            this.CLEAR_HOUSE_LIST();
            if (this.sidoCode == null || this.gugunCode == null || this.dongCode == null) {
                return;
            }
            let info = { dongCode: this.dongCode, year: this.year, month: this.month };
            if (this.dongCode) {
                this.getHouseList(info);
            }
        },
    },
};
</script>

<style></style>
