import Vue from "vue";
import { extend, ValidationObserver, ValidationProvider } from "vee-validate";
import { max, required, digits, numeric, email, confirmed } from "vee-validate/dist/rules";

extend("max", {
    ...max,
    message: "40자 이내로 입력해야 합니다",
});

extend("password", {
    message:
        "올바른 비밀번호 입력형식을 지켜주세요 (소문자 / 대문자 / 특수문자 / 숫자)",
    validate: (value) => {
        const strong_password = new RegExp("^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*])^"); // regex to check our strong password

        return strong_password.test(value); //Test the regex. Test function returns a true or false value.
    },
});


extend("limit", (value) => {
    if ((value && value.length < 8) || value.length > 16) {
        return `{_field_} 필드는 8자 ~ 16로 입력되어야 합니다`;
    }
    return true;
});

extend("required", {
    ...required,
    message: "{_field_} 는 반드시 입력해야 합니다",
});

extend("required-select", {
    ...required,
    message: "{_field_} 필드는 반드시 선택해야 합니다",
});

extend("numeric", {
    ...numeric,
    message: "{_field_} 필드는 숫자로만 구성되어야 합니다",
});
extend("digits", {
    ...digits,
    message: "{_field_} 필드는 11자리 여야 합니다",
});

extend("email", {
    ...email,
    message: "잘못 입력된 이메일 주소 입니다",
});

// provider 에 :rules="{ confirmed: 'provider name 명을 적어주면 된다'}"
extend("confirmed", {
    ...confirmed,
    message: "비밀번호와 비밀번호확인이 일치하지 않습니다",
});

Vue.component("ValidationObserver", ValidationObserver);
Vue.component("ValidationProvider", ValidationProvider);
