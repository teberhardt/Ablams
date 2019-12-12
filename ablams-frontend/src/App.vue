<template>
    <main>
        <v-app>
<!--            <v-navigation-drawer app>
                &lt;!&ndash; &ndash;&gt;
            </v-navigation-drawer>-->

            <Appbar/>

            <NavigationDrawer/>

            <!-- Sizes your content based upon application components -->
            <v-content>

                <!-- Provides the application the proper gutter -->
                <v-container fluid>

                    <!-- If using vue-router -->
                    <router-view></router-view>
                </v-container>
            </v-content>
        </v-app>
    </main>
</template>

<script lang="ts">
import Vue from 'vue';
import Appbar from '@/components/Appbar.vue';
import NavigationDrawer from '@/components/NavigationDrawer.vue';
import axios from 'axios';

Vue.prototype.$http = axios;

export default Vue.extend({
  name: 'App',
  components: {
      Appbar,
      NavigationDrawer,
  },
  data: () => ({
    //
  }),
  methods: {
  },
});
</script>
