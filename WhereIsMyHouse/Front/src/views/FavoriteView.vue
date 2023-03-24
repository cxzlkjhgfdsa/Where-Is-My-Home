<template>
    <b-container>
        <b-row class="mb-4">
            <favorite-select-bar></favorite-select-bar>
        </b-row>
        <b-row>
            <favorite-map></favorite-map>
        </b-row>
    </b-container>
</template>

<script>
import favoriteSelectBar from "../components/house/favorite/FavoriteSelectBar.vue";
import favoriteMap from "../components/house/favorite/FavoriteMap.vue";

export default {
    name: "favoriteView",
    components: {
        favoriteSelectBar,
        favoriteMap,
    },
};
</script>

<style></style>
