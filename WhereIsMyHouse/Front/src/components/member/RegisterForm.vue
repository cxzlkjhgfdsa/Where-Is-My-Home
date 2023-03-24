<template>
    <ValidationObserver ref="obs" v-slot="{ invalid, validated }">
        <form>
            <div class="col-md-8 mt-5" style="float: none; margin: 0 auto">
                <b-card style="max-width: 100rem">
                    <b-card-title class="text-center">회원가입</b-card-title>
                    <!--email 유효성검사-->
                    <validation-provider
                        name="이메일"
                        rules="email|required"
                        v-slot="{ errors, valid }"
                    >
                        Email
                        <b-row>
                            <b-col sm="9"
                                ><b-input
                                    class="m-3"
                                    name="id"
                                    v-model="id"
                                    placeholder="id로 사용할 email 주소를 등록해주세요"
                                    :error-messages="errors"
                                    :success="valid"
                                    required
                                    @keyup="idChecked = false"
                                >
                                </b-input></b-col
                            ><b-col sm="2"
                                ><b-button
                                    style="margin: 100 auto"
                                    class="mx-5"
                                    width="100%"
                                    variant="outline-secondary"
                                    @click="idDuplicateCheck"
                                    >[email] 중복체크</b-button
                                ></b-col
                            >
                        </b-row>
                        <span>{{ errors }}</span
                        ><br /><br />
                    </validation-provider>

                    <!--password 유효성검사-->
                    <validation-provider
                        name="비밀번호"
                        rules="required|limit|password"
                        v-slot="{ errors, valid }"
                    >
                        비밀번호
                        <b-col sm="9">
                            <b-input
                                v-model="password"
                                name="password"
                                class="m-3"
                                placeholder="영문, 숫자, 특수문자를 조합하여 입력해주세요 (8~16자)"
                                :error-messages="errors"
                                :success="valid"
                                required
                                type="password"
                            >
                            </b-input
                        ></b-col>
                        <span>{{ errors }}</span
                        ><br /><br />
                    </validation-provider>
                    <!--password 체크 -->
                    <validation-provider
                        name="비밀번호재확인"
                        rules="confirmed:비밀번호"
                        v-slot="{ errors, valid }"
                    >
                        비밀번호 재확인
                        <b-col sm="9"
                            ><b-input
                                v-model="pwCheck"
                                name="pwCheck"
                                class="m-3"
                                placeholder="비밀번호와 같은 값을 입력해 주세요"
                                :error-messages="errors"
                                :success="valid"
                                required
                                type="password"
                            >
                            </b-input
                        ></b-col>
                        <span>{{ errors }}</span
                        ><br /><br />
                    </validation-provider>

                    <validation-provider
                        name="별명"
                        rules="max:40|required"
                        v-slot="{ errors, valid }"
                    >
                        별명
                        <b-row>
                            <b-col sm="9"
                                ><b-input
                                    class="m-3"
                                    name="nickname"
                                    v-model="nickname"
                                    placeholder="프로필로 사용할 별명(닉네임)을 입력해 주세요. 닉네임은 중복되면 안됩니다!"
                                    :error-messages="errors"
                                    :success="valid"
                                    required
                                    @keyup="nicknameChecked = false"
                                >
                                </b-input
                            ></b-col>
                            <b-col sm="2"
                                ><b-button
                                    style="margin: 100 auto"
                                    class="mx-5"
                                    width="100%"
                                    variant="outline-secondary"
                                    @click="nicknameDuplicateCheck"
                                    >[nickname]중복체크</b-button
                                ></b-col
                            >
                        </b-row>
                        <span>{{ errors }}</span
                        ><br /><br />
                    </validation-provider>

                    <b-button class="mx-2" @click="submit" :disabled="invalid || !validated"
                        >회원가입</b-button
                    >
                    <b-button class="mx-2" variant="warning" width="100%" @click="kakao">
                        카카오로 회원가입
                    </b-button>
                </b-card>
            </div>
        </form>
    </ValidationObserver>
</template>

<script>
import { ValidationProvider, ValidationObserver } from "vee-validate";
import http from "@/api/http";

export default {
    name: "RegisterForm",
    data() {
        return {
            id: "",
            password: "",
            pwCheck: "",
            nickname: "",
            idChecked: false,
            nicknameChecked: false,
        };
    },
    components: {
        ValidationProvider,
        ValidationObserver,
    },
    computed: {},
    methods: {
        submit() {
            if (!this.idChecked || !this.nicknameChecked) {
                alert("이메일(id) 과 별명 중복체크를 해주세요");
            } else {
                let member = {
                    id: this.id,
                    password: this.password,
                    nickname: this.nickname,
                };
                console.log(member);
                http.post("user/regist", JSON.stringify(member))
                    .then(({ data }) => {
                        console.log(data);
                        alert("회원가입 완료!");
                        this.idChecked = false;
                        this.nicknameChecked = false;
                        this.$router.push({ name: "home" });
                    })
                    .catch();
            }
        },
        kakao() {
            alert("카카오 회원가입 이동");
        },
        idDuplicateCheck() {
            let member = {
                id: this.id,
            };
            http.post("user/userinfo", JSON.stringify(member))
                .then(({ data }) => {
                    console.log(data);
                    if (this.id === "") {
                        alert("아이디를 입력해주세요");
                    } else if (data) {
                        alert("이미 가입된 아이디입니다.");
                    } else {
                        alert("사용할 수 있는 아이디입니다!");
                        this.idChecked = true;
                    }
                })
                .catch();
        },
        nicknameDuplicateCheck() {
            let member = {
                nickname: this.nickname,
            };
            http.post("user/nicknamecheck", JSON.stringify(member))
                .then(({ data }) => {
                    console.log(data);
                    if (this.nickname === "") {
                        alert("별명을 입력해주세요");
                    } else if (data) {
                        alert("이미 가입된 별명입니다.");
                    } else {
                        alert("사용할 수 있는 별명입니다!");
                        this.nicknameChecked = true;
                    }
                })
                .catch();
        },
    },
};
</script>

<style></style>
