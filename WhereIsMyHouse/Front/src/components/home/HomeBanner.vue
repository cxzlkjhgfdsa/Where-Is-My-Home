<template>
    <div class="HomeBanner">
        <b-container class="px-5">
            <b-row class="m-5"></b-row>
            <b-row>
                <b-col class="d-flex align-center m-5">
                    <div>
                        <h1 class="mb-3" style="font-size: 2rem">
                            거주하고 싶은 지역이 있으신가요?
                        </h1>

                        <h3 class="text-secondary mb-4" style="font-size: 1rem">
                            원하는 집의 <span class="text-warning">실거래가</span>를 확인하세요.
                        </h3>
                    </div>
                </b-col>

                <b-col cols="5">
                    <b-img :src="img" width="450" height="300"></b-img>
                </b-col>
            </b-row>
        </b-container>
    </div>
</template>

<script>
import HomeImage from "../../assets/happy-house.jpg";

export default {
    name: "HomeBanner",
    data() {
        return {
            img: HomeImage,
        };
    },
};
</script>

<style scoped></style>
