<template>
    <v-app-bar
        app
        clipped-left
        color="primary"
        dark
        flat
    >
        <v-toolbar-title>{{title}}</v-toolbar-title>
        <v-spacer/>
        <Searchfield></Searchfield>
        <v-spacer/>
        <v-avatar color="red">
            <span class="white--text headline">TE</span>
        </v-avatar>
    </v-app-bar>
</template>

<script lang="ts">
import Vue from 'vue';
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
