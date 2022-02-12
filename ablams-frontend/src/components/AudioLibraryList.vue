<template>
    <div class="audio-library-list">
        <h1>AudioLibrary List</h1>
        <div v-for="audioLibrary in audioLibraries">
            {{ audioLibrary.filepath }}
        </div>
    </div>
</template>
<script lang="ts">
import Vue from 'vue'
import Component from 'vue-class-component'

    @Component
    export default class AudioLibraryList extends Vue {

    }
</script>
