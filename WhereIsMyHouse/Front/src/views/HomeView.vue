<template>
    <div class="home">
        <home-banner></home-banner>
        <home-list></home-list>
    </div>
</template>

<script>
import HomeBanner from "@/components/home/HomeBanner.vue";
import HomeList from "@/components/home/HomeList.vue";

export default {
    name: "HomeView",
    components: {
        HomeBanner,
        HomeList,
    },
};
</script>
