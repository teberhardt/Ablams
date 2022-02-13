<template>
    <main>
        <v-app>
            <Appbar/>
            <NavigationDrawer/>
            <!-- Sizes your content based upon application components -->
            <v-content app>
                <!-- Provides the application the proper gutter -->
                <router-view/>
            </v-content>
            <Bottombar class="bottombar"></Bottombar>
        </v-app>
    </main>
</template>

<script lang="ts">
import Vue from 'vue';
import Appbar from '@/components/Appbar.vue';
import NavigationDrawer from '@/components/NavigationDrawer.vue';
import axios from 'axios';
import Bottombar from '@/components/Bottombar.vue';

Vue.prototype.$http = axios;

export default Vue.extend({
  name: 'App',
  components: {
      Appbar,
      NavigationDrawer,
      Bottombar},
  data: () => ({
    //
  }),
  methods: {
  },
});
</script>
<style lang="scss">
.bottombar {
    position: fixed !important;
    bottom: 0;
    left: 5%;
    right: 1%;
}
</style>

