import Vue from 'vue';
import Vuetify from 'vuetify';
import 'vuetify/dist/vuetify.min.css';
import de from 'vuetify/src/locale/de';

Vue.use(Vuetify);

export default new Vuetify({
    lang: {
      locales: { de },
      current: 'de',
    },
  icons: {
    iconfont: 'mdi',
  },
});
