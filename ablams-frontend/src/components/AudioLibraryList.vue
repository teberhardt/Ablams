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
    import axios from 'axios';

    export interface AudioLibrary {
        id: number;
        filepath: string;
    }

    @Component
    export default class AudioLibraryList extends Vue {

        public audioLibraries: AudioLibrary[] = [];

        private async created() {
            const response = await axios.get('/api/audio-libraries');
            this.audioLibraries = await response.data;
        }
    }
</script>

