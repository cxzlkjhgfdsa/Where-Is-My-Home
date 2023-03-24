<template>
    <ValidationObserver ref="obs" v-slot="{ invalid, validated }">
        <form>
            <div class="m-5">
                <b-row align-v="center">
                    <b-card>
                        <b-card-title class="text-center">로그인</b-card-title>
                        <!--email 유효성검사-->
                        <validation-provider name="이메일" rules="required" v-slot="{ errors, valid }">
                            Email<b-input
                                class="m-3"
                                name="email"
                                v-model="user.id"
                                placeholder="email 주소를 정확히 입력해 주세요"
                                :error-messages="errors"
                                :success="valid"
                                required
                            >
                            </b-input>
                            <br />
                        </validation-provider>

                        <!--password 유효성검사-->
                        <validation-provider name="비밀번호" rules="required" v-slot="{ errors, valid }">
                            비밀번호<b-input
                                v-model="user.password"
                                name="pw"
                                class="m-3"
                                placeholder="영문, 숫자, 특수문자를 조합하여 입력해주세요 (8~16자)"
                                :error-messages="errors"
                                :success="valid"
                                required
                                type="password"
                            >
                            </b-input>
                            <br />
                        </validation-provider>

                        <b-button @click="confirm" :disabled="invalid || !validated">로그인</b-button>
                    </b-card>
                </b-row>
            </div>
        </form>
    </ValidationObserver>
</template>

<script>
import { ValidationProvider, ValidationObserver } from "vee-validate";
import { mapState, mapActions } from "vuex";

const memberStore = "memberStore";
export default {
    name: "LoginForm",
    data() {
        return {
            user: {
                id: null,
                password: null,
            },
        };
    },
    components: {
        ValidationProvider,
        ValidationObserver,
    },
    computed: {
        ...mapState(memberStore, ["isLogin", "isLoginError", "userInfo"]),
    },
    methods: {
        ...mapActions(memberStore, ["userConfirm", "getUserInfo"]),
        async confirm() {
            console.log(this.user);
            await this.userConfirm(this.user);
            let token = sessionStorage.getItem("access-token");
            //console.log(this.user.id + " " + this.user.password);
            // console.log("1. confirm() token >> " + token);
            console.log(this.isLogin);
            if (this.isLogin) {
                await this.getUserInfo(token); // 요게 안먹는당
                //console.log("4. confirm() userInfo :: ", this.userInfo);
                alert(`환영합니다 :) ${this.userInfo.nickname}님`);
                this.$router.push({ name: "home" });
            }
        },
        movePage() {
            this.$router.push({ name: "join" });
        },
    },
};
</script>

<style></style>
