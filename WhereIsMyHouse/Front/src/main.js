import Vue from "vue";
import App from "./App.vue";
import router from "./router";
import store from "./store";
import { BootstrapVue, IconsPlugin } from "bootstrap-vue";
import { BBadge } from "bootstrap-vue";
import { BadgePlugin } from "bootstrap-vue";
import "@/plugins/vee-validate";
// Note: Vue automatically prefixes the directive name with 'v-'

import "bootstrap/dist/css/bootstrap.css";
import "bootstrap-vue/dist/bootstrap-vue.css";

Vue.component("b-badge", BBadge);
Vue.use(BadgePlugin);
Vue.use(BootstrapVue);
Vue.use(IconsPlugin);

Vue.config.productionTip = false;

new Vue({
    router,
    store,
    render: (h) => h(App),
}).$mount("#app");
