<template>
    <b-container fluid>
        <b-row>
            <search-input></search-input>
        </b-row>
        <b-container fluid>
            <b-row class="d-flex justify-center" fluid>
                <!-- search result -->
                <b-col class="col-lg-2">
                    <search-result></search-result>
                </b-col>
                <!-- search map -->
                <b-col>
                    <search-map></search-map>
                </b-col>
            </b-row>
        </b-container>
    </b-container>
</template>

<script>
import SearchInput from "../components/house/SearchInput.vue";
import SearchResult from "../components/house/SearchResult.vue";
import SearchMap from "../components/house/SearchMap.vue";

export default {
    name: "SearchView",
    components: {
        SearchInput,
        SearchResult,
        SearchMap,
    },
};
</script>

<style>
hr {
    opacity: 50%;
}
</style>
