<template>
    <b-container>
        <login-form></login-form>
    </b-container>
</template>

<script>
import LoginForm from "@/components/member/LoginForm.vue";
export default {
    name: "SignInView",
    components: {
        LoginForm,
    },
};
</script>

<style></style>
