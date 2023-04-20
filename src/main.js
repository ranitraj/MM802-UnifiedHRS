// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue';
import BootstrapVue from 'bootstrap-vue';
import * as VueGoogleMaps from 'vue2-google-maps';
import VueTouch from 'vue-touch';
import Trend from 'vuetrend';
import Toasted from 'vue-toasted';
import VueApexCharts from 'vue-apexcharts';
import { initializeApp } from "firebase/app";
import { getMessaging } from "firebase/messaging";

import store from './store';
import router from './Routes';
import App from './App';
import layoutMixin from './mixins/layout';
import Widget from './components/Widget/Widget';

Vue.use(BootstrapVue);
Vue.use(VueTouch);
Vue.use(Trend);
Vue.component('Widget', Widget);
Vue.use(VueGoogleMaps, {
  load: {
    key: 'AIzaSyB7OXmzfQYua_1LEhRdqsoYzyJOPh9hGLg',
  },
});
Vue.component('apexchart', VueApexCharts);
Vue.mixin(layoutMixin);
Vue.use(Toasted, {duration: 10000});

Vue.config.productionTip = false;

const firebaseConfig = {
  apiKey: "AIzaSyBRQH7uyvgwDyc4gxGNy92KJ6DziZL4G8U",
  authDomain: "unified-hrs.firebaseapp.com",
  projectId: "unified-hrs",
  storageBucket: "unified-hrs.appspot.com",
  messagingSenderId: "551974725458",
  appId: "1:551974725458:web:2a688d900b8ef340f53c8b",
  measurementId: "G-FYD1CWNTHF"
}

const firebaseApp = initializeApp(firebaseConfig);

// var admin = require("firebase-admin");

// var serviceAccount = require("src/unified-hrs-firebase-adminsdk-7kr57-aaa8ca3a9e.json");

// admin.initializeApp({
//   credential: admin.credential.cert(serviceAccount),
// });


export default {
  messaging: getMessaging(firebaseApp)
}

/* eslint-disable no-new */
new Vue({
  el: '#app',
  store,
  router,
  render: h => h(App),
});
