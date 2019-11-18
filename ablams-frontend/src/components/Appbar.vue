<template>
    <v-app-bar
        app
        clipped-left
        color="blue"
    >
        <v-app-bar-nav-icon/>
        <v-toolbar-title>{{title}}</v-toolbar-title>
        <v-spacer/>
        <Searchfield></Searchfield>
        <v-spacer/>
        <v-btn icon>
            <v-icon>mdi-account-settings</v-icon>
        </v-btn>
    </v-app-bar>
</template>

<script lang="ts">
    import { Component, Vue } from 'vue-property-decorator';
    import Searchfield from '@/components/Searchfield.vue';

    export default Vue.extend({
        name: 'Appbar',
        components: {
            Searchfield,
        },
        data: () => ({
            title: 'Ablams',
        }),
        methods: {
        },
    });
</script>

<style>

</style>
