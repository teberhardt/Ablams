<template>
    <div class="audio-library-list">
        <h1>AudioLibrary List</h1>
        <div v-for="audioLibrary in audioLibraries">
            {{ audioLibrary.filepath }}
        </div>
    </div>
</template>
<script lang="ts">
    import { Component, Vue } from 'vue-property-decorator';

    @Component
    export default class AudioLibraryList extends Vue {

    }
</script>

