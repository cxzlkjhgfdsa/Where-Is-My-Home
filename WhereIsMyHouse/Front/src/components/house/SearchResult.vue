<template>
    <div v-if="show" style="max-height: 600px; overflow: auto; width: 300px" class="px-2">
        <b-card-group v-for="(item, index) in houses" :key="index">
            <b-card
                :title="item.aptName"
                :img-src="images[Math.floor(Math.random() * 10)]"
                img-alt="Image"
                img-top
                class="mb-2"
                @click="moveLocation(`${item.lat},${item.lng}`)"
            >
                <table class="table">
                    <tbody>
                        <tr>
                            <td class="text-secondary">주소</td>
                            <td>{{ item.roadName }}</td>
                        </tr>
                        <tr>
                            <td class="text-secondary">건축년도</td>
                            <td>{{ item.buildYear }} 년</td>
                        </tr>
                        <tr>
                            <td class="text-secondary">층</td>
                            <td>{{ item.floor }} 층</td>
                        </tr>
                        <tr>
                            <td class="text-secondary">면적</td>
                            <td>{{ item.area }} ㎡</td>
                        </tr>
                        <tr>
                            <td class="text-secondary">거래가</td>
                            <td>{{ item.dealAmount }} 만원</td>
                        </tr>
                    </tbody>
                </table>
                <b-badge
                    v-show="userInfo"
                    class="bg-warning"
                    @click="addFavorite(`${item.aptCode}`)"
                    >관심매물 추가</b-badge
                >
            </b-card>
        </b-card-group>
    </div>
</template>

<script>
import { mapState, mapMutations } from "vuex";
const houseStore = "houseStore";
import http from "@/api/http";

export default {
    name: "searchResult",
    computed: {
        ...mapState(houseStore, ["houses", "images", "showResult"]),
        ...mapState("memberStore", ["userInfo"]),
        show() {
            if (this.showResult) {
                return true;
            } else {
                return false;
            }
        },
    },
    methods: {
        ...mapMutations(houseStore, ["SET_SHOW_RESULT", "SET_CENTER_LOCATION"]),
        addFavorite(aptCode) {
            let item = { nickname: this.userInfo.nickname, aptCode: aptCode };
            http.post("/house/addFavorite", item)
                .then(({ data }) => {
                    alert(data.message);
                })
                .catch();
        },
        moveLocation(item) {
            let arr = item.split(",");
            this.SET_CENTER_LOCATION(arr);
        },
    },
    created() {
        this.SET_SHOW_RESULT();
    },
};
</script>

<style></style>
