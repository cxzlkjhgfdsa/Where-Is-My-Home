<template>
    <div id="app">
        <header-bar></header-bar>
        <router-view></router-view>
        <footer-bar class="mt-4"></footer-bar>
    </div>
</template>

<script>
import HeaderBar from "@/components/commons/HeaderBar.vue";
import FooterBar from "@/components/commons/FooterBar.vue";

export default {
    name: "App",
    components: {
        HeaderBar,
        FooterBar,
    },
};
</script>

<style></style>
