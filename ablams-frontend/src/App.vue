<template>
    <main>
        <v-app>
            <template v-if="!$route.path.includes('login')">
                <Appbar/>
                <NavigationDrawer/>
                <!-- Sizes your content based upon application components -->
                <v-content app>
                        <!-- Provides the application the proper gutter -->
                        <router-view></router-view>
                </v-content>
                <Bottombar class="bottombar"></Bottombar>
            </template>
            <template v-else>
                <!-- Sizes your content based upon application components -->
                <v-content app>
                    <keep-alive :include="['login']">
                        <!-- Provides the application the proper gutter -->
                        <router-view></router-view>
                    </keep-alive>
                </v-content>
            </template>
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
        Bottombar,
    },
    data: () => ({
        //
    }),
    methods: {},
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

