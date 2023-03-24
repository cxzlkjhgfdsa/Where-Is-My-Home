<template>
    <div class="mt-5">
        <!-- 로그인 전에는 유저를 확인해야한다 -->
        <!-- 로그인되면 info 화면이 뜬다 -->
        <user-info></user-info>
    </div>
</template>

<script>
import UserInfo from "@/components/member/UserInfo.vue";
export default {
    name: "UserInfoView",
    data() {
        return {};
    },
    components: {
        UserInfo,
    },
    methods: {},
};
</script>

<style></style>
