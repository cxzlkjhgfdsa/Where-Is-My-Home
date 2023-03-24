<template>
    <b-container class="d-flex justify-center align-center mb-4">
        <b-container>
            <b-row class="my-4"><h4 class="title">자유게시판 > 게시글 수정</h4></b-row>
            <b-row class="my-5"></b-row>
            <b-row class="mt-4">
                <b-col>
                    <b-form-input placeholder="글 제목을 입력하세요" v-model="title"></b-form-input>
                </b-col>
            </b-row>
            <b-row class="text-lg-end mb-4">
                <span class="text-secondary">{{ title.length }} / 100</span>
            </b-row>
            <b-row class="m">
                <b-col>
                    <b-form-textarea
                        id="textarea-rows"
                        placeholder="글 내용을 입력하세요"
                        rows="8"
                        v-model="content"
                    ></b-form-textarea>
                </b-col>
            </b-row>
            <b-row class="text-lg-end mb-4">
                <span class="text-secondary">{{ content.length }} / 500</span>
            </b-row>

            <button class="btn btn-outline-warning float-end" @click="modifyBoard">글 수정</button>
            <button class="btn btn-outline-info" @click="moveBoardList">글 목록</button>
        </b-container>
    </b-container>
</template>

<script>
import http from "@/api/http";
import { mapState } from "vuex";

export default {
    name: "boardModify",
    data() {
        return {
            title: "",
            content: "",
            code: "",
        };
    },
    created() {
        let board = this.$route.params;
        this.title = board.title;
        this.content = board.content;
        this.code = board.code;
    },
    methods: {
        async modifyBoard() {
            http.defaults.headers.common["access-token"] = sessionStorage.getItem("access-token");
            await http
                .post("/board/modifyBoard", {
                    code: this.code,
                    title: this.title,
                    content: this.content,
                    nickname: this.userInfo.nickname,
                })
                .then()
                .catch();
            this.$router.push({ name: "BoardDetail" });
        },
        moveBoardList() {
            this.$router.push({ name: "BoardList" });
        },
    },
    computed: {
        ...mapState("memberStore", ["userInfo"]),
    },
};
</script>

<style>
.title {
    font: 1rem/4 var(--font-family-sans-serif);
}
</style>
