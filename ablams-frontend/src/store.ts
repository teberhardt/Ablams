import Vue from 'vue';
import Vuex from 'vuex';
// @ts-ignore
import { auth } from './store/auth.module';
import createPersistedState from 'vuex-persistedstate';

Vue.use(Vuex);

export default new Vuex.Store({
  state: {

  },
  mutations: {

  },
  actions: {

  },
  modules: {
        auth,
  },
  plugins: [createPersistedState()],
});
