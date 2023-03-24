<template>
    <footer class="cardspace">
        <b-container class="d-flex" fluid>
            <div class="col-lg-10">
                <div class="text-secondary pb-3">
                    <b-icon icon="house" class="px-3"></b-icon> Where Is my Home
                </div>
                <div>
                    <div class="px-3 text-secondary">©Copyright Where Is My Home</div>
                </div>
            </div>
            <div class="col row">
                <div>
                    <span class="text-secondary">phone : </span
                    ><span class="text-warning">010-1111-2222</span><br />
                    <span class="text-secondary">email : </span
                    ><span class="text-warning">seoul_19_ssafy@ssafy.com</span>
                </div>
                <div class="pt-1">
                    <b-icon icon="house" class="mx-1"></b-icon>
                    <b-icon icon="instagram" class="mx-1"></b-icon>
                    <b-icon icon="facebook" class="mx-1"></b-icon>
                </div>
            </div>
        </b-container>
    </footer>
</template>

<script>
export default {
    name: "footerBar",
};
</script>

<style>
.cardspace {
    width: 100%;
    height: 100px;
    bottom: 0;
    color: white;
}
</style>
