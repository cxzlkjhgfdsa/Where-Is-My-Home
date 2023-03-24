<template>
    <div id="homeList">
        <b-container class="mt-3 p-3">
            <b-row class="mt-3 px-3">
                <b-col>
                    <b-img
                        :src="imgs[0]"
                        fluid
                        alt="Fluid image"
                        v-bind="mainProps"
                        thumbnail
                    ></b-img>
                </b-col>
                <b-col>
                    <b-img
                        :src="imgs[1]"
                        fluid
                        alt="Fluid image"
                        v-bind="mainProps"
                        thumbnail
                    ></b-img>
                </b-col>
                <b-col>
                    <b-img
                        :src="imgs[2]"
                        fluid
                        alt="Fluid image"
                        v-bind="mainProps"
                        thumbnail
                    ></b-img>
                </b-col>
            </b-row>
        </b-container>
    </div>
</template>

<script>
import SampleImg1 from "@/assets/sample.jpg";
import SampleImg2 from "@/assets/sample_2.jpg";
import SampleImg3 from "@/assets/sample_3.jpg";

export default {
    name: "homeList",
    data() {
        return {
            imgs: [SampleImg1, SampleImg2, SampleImg3],
            mainProps: { width: 300, height: 300, class: "mx-3" },
        };
    },
};
</script>

<style></style>
