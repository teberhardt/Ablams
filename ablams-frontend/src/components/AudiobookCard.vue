<template>
    <v-card
        class="ma-4"
        hover
        shaped
        width="200"
        height="350"
    >
        <v-img contain
               align-center
               justify-center
               :src="getImageSrcUrl()"
        />
        <v-progress-linear value="10" color="green"></v-progress-linear>

        <v-card-text>{{abook.name}}</v-card-text>

        <v-card-actions class="card-actions" >
            <v-btn color="orange" text v-on:click="initPlay">
                Play
            </v-btn>
            <v-btn color="orange" text>
                Details
            </v-btn>
        </v-card-actions>
    </v-card>
</template>
<script lang="ts">
    import {Component, Prop, Vue} from 'vue-property-decorator';
    import {AudiobookDTO} from 'ablams-communication/ablams/communication';
    import PlayService from "@/service/PlayService";

    @Component({
        props: {
            abook: Object as () => AudiobookDTO
        }
    })
    export default class AudiobookCard extends Vue {

/*
        protected mounted(): void {
            this.$root.$on('playAudiobook',
                    (audiobookDTO: AudiobookDTO) => {
                        console.log("initPlay")
                        const audio = new Audio();
                        PlayService.play(audiobookDTO, audio);
                    });
        }
*/

        protected initialize(): void {

        }

        public getImageSrcUrl(): string{
            return `/api/audio-books/${this.$props.abook.id}/cover/image`;
        }

        public initPlay(){
            console.log("heeeeeeeeeeeeeeeeeeeeeeeelo")
          this.$root.$root.$emit('playAudiobook', this.$props.abook)
        }
    }
</script>
<style>
    .card-actions {
        position: absolute;
        bottom: 0;
    }
</style>
