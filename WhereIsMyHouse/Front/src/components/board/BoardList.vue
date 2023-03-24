<template>
    <b-container>
        <b-row class="my-4"><h4 class="">자유게시판</h4></b-row>
        <b-row class="mb-5">
            <b-col sm="6">
                <b-form-input
                    v-model="text"
                    placeholder="글 제목으로 검색하세요"
                    @keyup.enter="searchByTitle"
                ></b-form-input>
            </b-col>
            <b-col sm="5"></b-col>
            <b-col class="justify-content-end">
                <b-button variant="outline-warning" @click="moveBoardWrite" v-if="userInfo"
                    >글 작성</b-button
                >
            </b-col>
        </b-row>
        <b-row class="mb-4">
            <table class="table">
                <thead>
                    <tr>
                        <td><h5>글 번호</h5></td>
                        <td><h5>제목</h5></td>
                        <td><h5>글쓴이</h5></td>
                        <td><h5>작성일자</h5></td>
                        <td><h5>조회수</h5></td>
                    </tr>
                </thead>
                <tbody>
                    <tr v-for="(item, index) in items" :key="index">
                        <td>{{ item.code }}</td>
                        <td @click="moveDetail(`${item.code}`)">{{ item.title }}</td>
                        <td>{{ item.nickname }}</td>
                        <td>{{ item.reg_datetime }}</td>
                        <td>{{ item.hits }}</td>
                    </tr>
                </tbody>
            </table>
        </b-row>
        <b-row class="col-md-3" style="float: none; margin: 0 auto">
            <div class="overflow-auto">
                <b-pagination
                    v-model="currentPage"
                    :total-rows="rows"
                    :per-page="perPage"
                    aria-controls="my-table"
                    class="customPagination"
                ></b-pagination>
            </div>
        </b-row>
    </b-container>
</template>

<script>
import http from "@/api/http";
import { mapActions, mapState } from "vuex";

export default {
    name: "boardList",
    data() {
        return {
            perPage: 10,
            currentPage: 1,
            items: [],
            totalPage: 0,
            text: null,
        };
    },
    computed: {
        ...mapState("memberStore", ["userInfo"]),
        rows() {
            this.countArticle();
            return this.totalPage;
        },
    },
    watch: {
        currentPage() {
            if (this.text == null) {
                http.post("board/selectBoard", this.currentPage)
                    .then(({ data }) => {
                        //console.log(data);
                        this.items = data;
                    })
                    .catch();
            } else {
                http.post("board/searchTitlePage", { text: this.text, page: this.currentPage })
                    .then(({ data }) => {
                        //console.log(data);
                        this.items = data;
                    })
                    .catch();
            }
        },
    },
    created() {
        http.post("board/selectBoard", this.currentPage)
            .then(({ data }) => {
                //console.log(data);
                this.items = data;
            })
            .catch();
    },
    methods: {
        ...mapActions("boardStore", ["setCode"]),

        moveDetail(item) {
            if (this.userInfo === null) {
                return;
            }
            //console.log(item);
            this.setCode(item);
            http.post("/board/addHits", { item }).then().catch();
            this.$router.push({ name: "BoardDetail", params: { item } });
        },
        moveBoardWrite() {
            this.$router.push({ name: "BoardWrite" });
        },
        countArticle() {
            if (this.text == null) {
                http.post("board/count")
                    .then(({ data }) => {
                        this.totalPage = data.count;
                    })
                    .catch();
            } else {
                http.post("board/countArticleText", { text: this.text })
                    .then(({ data }) => {
                        this.totalPage = data.count;
                    })
                    .catch();
            }
        },
        searchByTitle() {
            if (this.text) {
                http.post("board/searchTitle", { text: this.text })
                    .then(({ data }) => {
                        this.items = data;
                    })
                    .catch();
            }
        },
    },
};
</script>

<style></style>
