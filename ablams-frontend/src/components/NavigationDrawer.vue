<template>
    <v-navigation-drawer
        v-model="drawer"
        clipped
        app
        expand-on-hover
        dark
        class="#344955"
    >
        <v-list-item link
         :value="HomeItem.active"
            active-class="orange--text"
            :to="HomeItem.path == '#' ? '' : HomeItem.path"
            :exact="HomeItem.exact">
            <v-list-item-icon>
                <v-icon>{{ HomeItem.icon }}</v-icon>
            </v-list-item-icon>
            <v-list-item-content>
                <v-list-item-title>{{ HomeItem.title }}</v-list-item-title>
            </v-list-item-content>
        </v-list-item>

        <v-list>
            <v-list-item
            v-for="item in MainItems"
            :key="item.title"
            link
            :value="item.active"
            active-class="orange--text"
            :to="item.path == '#' ? '' : item.path"
            :exact="item.exact"
            >
                <v-list-item-icon>
                    <v-icon>{{ item.icon }}</v-icon>
                </v-list-item-icon>
                <v-list-item-content>
                    <v-list-item-title>{{ item.title }}</v-list-item-title>
                </v-list-item-content>
            </v-list-item>
        </v-list>
        <template v-slot:append>
            <v-list-item
            v-for="item in BottomItems"
            :key="item.title"
            link
             :value="item.active"
            active-class="orange--text"
            :to="item.path == '#' ? '' : item.path"
            :exact="item.exact"
            >
            <v-list-item-icon>
                <v-icon>{{ item.icon }}</v-icon>
            </v-list-item-icon>
            <v-list-item-content>
                <v-list-item-title>{{ item.title }}</v-list-item-title>
            </v-list-item-content>
            </v-list-item>
        </template>

    </v-navigation-drawer>
</template>

<script lang="ts">
    import { Component, Vue } from 'vue-property-decorator';

    export default Vue.extend({
        name: 'NavigationDrawer',
        components: {

        },
        data: () => ({
            drawer: null,
            HomeItem: { icon: 'mdi-home', title: 'Home', path: '/home'},
            MainItems: [
                { icon: 'mdi-audiobook', title: 'Audiobooks', path: '/audiobooks'},
                { icon: 'mdi-account-group', title: 'Authors', path: '/authors'},
                ],
            BottomItems: [
                { icon: 'mdi-settings', title: 'Admin Settings', path: '/admin-settings'},
                ],
        }),
        methods: {
        },
    });
</script>

<style scoped>

</style>
