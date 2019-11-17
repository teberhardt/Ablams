import Vue from 'vue';

// Sync router with store
import { sync } from 'vuex-router-sync';

// Application imports
import App from './App.vue';
import router from './router';
import './registerServiceWorker';
import vuetify from './plugins/vuetify';
import store from './store';
import 'vuetify/dist/vuetify.min.css';
import axios from 'axios';

Vue.prototype.$http = axios;

// Sync store with router
sync(store, router);

Vue.use(
    vuetify, {
    iconfont: 'mdi',
});

Vue.config.productionTip = false;

new Vue({
  router,
  store,
  vuetify,
  render: (h) => h(App),
}).$mount('#app');
