<template>
    <b-container class="d-flex justify-center align-center">
        <b-container>
            <b-row class="my-4"><h4 class="title">자유게시판 > 게시글 작성</h4></b-row>
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

            <b-button variant="outline-warning" @click="registBoard">글 등록</b-button>
            <b-button variant="outline-info" class="mx-4" @click="moveBoardList">글 목록</b-button>
        </b-container>
    </b-container>
</template>

<script>
import http from "@/api/http";
import { mapState } from "vuex";
export default {
    name: "boardWrite",
    data() {
        return {
            title: "",
            content: "",
        };
    },
    computed: {
        ...mapState("memberStore", ["userInfo"]),
    },
    methods: {
        moveBoardList() {
            this.$router.push({ name: "BoardList" });
        },
        registBoard() {
            let article = {
                title: this.title,
                nickname: this.userInfo.nickname,
                content: this.content,
            };

            http.defaults.headers.common["access-token"] = sessionStorage.getItem("access-token");
            http.post("board/writeBoard", JSON.stringify(article))
                .then(({ data }) => {
                    alert(data.message);
                    this.$router.push({ name: "BoardList" });
                })
                .catch();
        },
    },
};
</script>

<style>
.title {
    font: 1rem/4 var(--font-family-sans-serif);
}
</style>
