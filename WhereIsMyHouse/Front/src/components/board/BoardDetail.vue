<template>
    <b-container class="d-flex justify-center align-center">
        <b-container>
            <b-row class="my-4"><h4 class="title">자유게시판 > 게시글 상세</h4></b-row>
            <b-row class="my-5"></b-row>
            <b-row class="mt-4 mb-3">
                <b-card border-variant="light" header="제목" class="text-center">
                    <b-card-text
                        ><h5>{{ board.title }}</h5>
                    </b-card-text>
                    <span style="width: 100%" class="text-secondary">[{{ board.nickname }}]</span>
                    <div>
                        <b-icon icon="heart-fill" variant="danger"></b-icon> {{ board.favCount }}
                    </div>
                </b-card>
            </b-row>
            <b-row class="my-2">
                <b-card border-variant="light" header="내용" class="text-center">
                    <b-card-text v-html="board.content"></b-card-text>
                </b-card>
            </b-row>
            <!--좋아요 -->
            <div class="nav my-3" v-if="isLike" @click="checkLike">
                <b-icon icon="heart" variant="danger"></b-icon>
            </div>
            <div class="nav my-3" v-else @click="checkLike">
                <b-icon icon="heart-fill" variant="danger"></b-icon>
            </div>
            <!-- 좋아요 끝 -->
            <b-row>
                <b-col class="col-lg-11">
                    <b-button variant="outline-warning" @click="boardModifyForm" v-if="checkWriter"
                        >글 수정</b-button
                    >
                    <b-button variant="outline-info" class="mx-3" @click="moveBoardList"
                        >글목록</b-button
                    >
                    <b-button
                        v-if="commentTg"
                        variant="outline-danger"
                        class=""
                        @click="showComentForm"
                        >댓글 달기</b-button
                    >
                </b-col>
                <b-col>
                    <b-button variant="outline-danger" @click="deleteBoard" v-if="checkWriter"
                        >글 삭제</b-button
                    >
                </b-col>
            </b-row>

            <b-row class="my-1" v-if="checkComment">
                <span class="text-center"><h6>댓글</h6></span>

                <b-card v-for="(com, index) in commentList" :key="index">
                    <div v-if="commentChk(`${index}`)">
                        <p>닉네임 : {{ com.nickname }}</p>
                        <p>댓글 : {{ com.comment }}</p>
                        <button
                            class="btn btn-outline-danger float-end"
                            @click="removeComment(`${index}`)"
                            v-if="checkCoWriter(`${com.nickname}`)"
                        >
                            삭제
                        </button>
                        <button
                            class="btn btn-outline-info float-end mx-2"
                            @click="modifyCommentForm(`${index}`)"
                            v-if="checkCoWriter(`${com.nickname}`)"
                        >
                            수정
                        </button>
                    </div>
                    <div v-else>
                        <p>닉네임 : {{ com.nickname }}</p>
                        <b-form-textarea
                            id="textarea-rows"
                            rows="4"
                            v-model="coModiText"
                            @keyup.enter="modifyComment"
                        ></b-form-textarea>
                        <span class="text-secondary text-lg-end mb-2"
                            >{{ coModiText.length }} / 500</span
                        ><br />
                        <button class="btn btn-outline-info float-end mx-2" @click="modifyComment">
                            수정
                        </button>
                    </div>
                </b-card>
                <button class="btn btn-outline-secondary" v-if="showBtn" @click="showMore">
                    댓글 더보기
                </button>
            </b-row>
            <!-- 댓글 수장-->
            <div v-if="coModify"></div>
            <!-- 댓글 추가 -->
            <div v-if="commentTg2">
                <b-row class="mt-4">
                    <b-form-textarea
                        id="textarea-rows"
                        placeholder="댓글을 입력하세요"
                        rows="4"
                        v-model="comment"
                        @keyup.enter="addComent"
                    ></b-form-textarea>
                    <span class="text-secondary text-lg-end mb-2">{{ comment.length }} / 500</span>
                </b-row>
                <b-button variant="outline-warning" @click="addComent">댓글 작성</b-button>
            </div>
        </b-container>
    </b-container>
</template>

<script>
import http from "@/api/http";
import { mapState } from "vuex";

export default {
    name: "boardDetail",
    data() {
        return {
            commentTg: true,
            commentTg2: false,
            comment: "",
            commentList: [],
            board: {},
            coModify: false,
            coModiNick: "",
            coModiText: "",
            coModiIndex: "",
            commentSize: null,
            currentPage: 1,
            commentToggle: [],
            isLike: true,
        };
    },
    created() {
        //console.log(this.code);
        let no = (this.currentPage - 1) * 5;
        http.post("board/getComment", { item: this.code, num: no })
            .then(({ data }) => {
                this.commentList = data;
                let i = this.commentSize;
                for (let n = 0; n < i; n++) {
                    this.commentToggle.push(false);
                }
            })
            .catch();
        http.post("board/getArticle", { item: this.code })
            .then(({ data }) => {
                console.log(data.content);
                //console.log(data);
                this.board = data;
            })
            .catch();
        http.post("board/checkLike", { nickname: this.userInfo.nickname, code: this.code })
            .then(({ data }) => {
                //console.log(data);
                if (data.toggle === "true") {
                    this.isLike = false;
                } else {
                    this.isLike = true;
                }
            })
            .catch();
        this.countCommet();
    },
    methods: {
        async deleteBoard() {
            await http.post("/board/deleteBoard", { code: this.board.code }).then().catch();
            this.$router.push({ name: "BoardList" });
        },
        boardModifyForm() {
            this.$router.push({
                name: "BoardModify",
                params: this.board,
            });
        },
        modifyCommentForm(index) {
            this.coModify = true;
            this.coModiNick = this.commentList[index].nickname;
            this.coModiText = this.commentList[index].comment;
            this.coModiIndex = index;
            this.commentToggle[index] = true;
        },
        async modifyComment() {
            let num = this.commentList[this.coModiIndex].no;
            await http
                .post("/board/modifyComment", { no: num, comment: this.coModiText })
                .then()
                .catch();
            this.commentList[this.coModiIndex].comment = this.coModiText;
            this.coModify = false;
            this.commentToggle[this.coModiIndex] = false;
        },
        moveBoardList() {
            this.$router.push({ name: "BoardList" });
        },
        showComentForm() {
            this.commentTg = false;
            this.commentTg2 = true;
        },
        async addComent() {
            if (this.comment == "") {
                return;
            }
            this.currentPage = 1;
            let co = { code: this.code, nickname: this.userInfo.nickname, comment: this.comment };
            let no = (this.currentPage - 1) * 5;
            http.defaults.headers.common["access-token"] = sessionStorage.getItem("access-token");
            await http.post("/board/addComment", co).then().catch();
            await http
                .post("board/getComment", { item: this.code, num: no })
                .then(({ data }) => {
                    this.commentList = data;
                })
                .catch();
            this.countCommet();
            this.commentToggle = [];
            let i = this.commentSize;
            for (let n = 0; n < i; n++) {
                this.commentToggle.push(false);
            }
            this.comment = "";
            this.commentTg = true;
            this.commentTg2 = false;
        },
        async removeComment(index) {
            this.currentPage = 1;
            let no = this.commentList[index].no;
            let num = (this.currentPage - 1) * 5;
            //console.log(no);
            await http.post("/board/removeComment", { item: no }).then().catch();
            http.post("board/getComment", { item: this.code, num: num })
                .then(({ data }) => {
                    this.commentList = data;
                })
                .catch();
            this.countCommet();
            this.commentToggle = [];
            let i = this.commentSize;
            for (let n = 0; n < i; n++) {
                this.commentToggle.push(false);
            }
        },
        checkCoWriter(nick) {
            //console.log(nick);
            if (nick === this.userInfo.nickname) {
                return true;
            } else {
                return false;
            }
        },
        countCommet() {
            http.post("/board/countComment", { item: this.code })
                .then(({ data }) => {
                    this.commentSize = data.size;
                })
                .catch();
        },
        showMore() {
            this.currentPage = this.currentPage + 1;
            let no = (this.currentPage - 1) * 5;
            http.post("board/getComment", { item: this.code, num: no })
                .then(({ data }) => {
                    data.forEach((item) => {
                        this.commentList.push(item);
                    });
                })
                .catch();
        },
        async checkLike() {
            if (this.isLike) {
                await http.post("board/upCount", { code: this.code }).then().catch();
                await http
                    .post("board/getArticle", { item: this.code })
                    .then(({ data }) => {
                        //console.log(data);
                        this.board = data;
                    })
                    .catch();
                await http
                    .post("board/addLike", { code: this.code, nickname: this.userInfo.nickname })
                    .then()
                    .catch();
                this.isLike = false;
            } else {
                await http.post("board/downCount", { code: this.code }).then().catch();
                await http
                    .post("board/getArticle", { item: this.code })
                    .then(({ data }) => {
                        //console.log(data);
                        this.board = data;
                    })
                    .catch();
                await http
                    .post("board/deleteLike", { code: this.code, nickname: this.userInfo.nickname })
                    .then()
                    .catch();
                this.isLike = true;
            }
        },
    },
    computed: {
        ...mapState("boardStore", ["code"]),
        ...mapState("memberStore", ["userInfo"]),
        checkComment() {
            if (Object.keys(this.commentList).length > 0) {
                return true;
            }
            return false;
        },
        checkWriter() {
            if (this.board.nickname === this.userInfo.nickname) {
                return true;
            } else {
                return false;
            }
        },
        showBtn() {
            if (this.commentSize > this.currentPage * 5) {
                return true;
            } else {
                return false;
            }
        },
        commentChk() {
            return (item) => {
                //console.log(this.commentToggle[item]);
                if (this.commentToggle[item]) {
                    return false;
                } else {
                    return true;
                }
            };
        },
    },
};
</script>

<style>
.title {
    font: 1rem/4 var(--font-family-sans-serif);
}
.nav {
    margin-left: auto;
}
</style>
