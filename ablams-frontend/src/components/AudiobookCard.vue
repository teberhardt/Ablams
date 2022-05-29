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
               :src="imageSrcUrl"
               v-on:error="onImgError"
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
    import {AudiobookDTO} from 'ablams-models/ablams/communication';
    import Component from 'vue-class-component';
    import Vue from 'vue';

    @Component({
        props: {
            abook: Object as () => AudiobookDTO
        }

    })
    export default class AudiobookCard extends Vue {

        imageSrcUrl: string|undefined  = `/api/audio-books/${this.$props.abook.id}/cover/image`;

        public onImgError(){
            console.log(`Cover for ${this.$props.abook.id} not available`);
            this.imageSrcUrl = undefined;
        }



        public initPlay(){
            console.log("heeeeeeeeeeeeeeeeeeeeeeeelo")
          this.$root.$emit('playAudiobook', this.$props.abook)
        }
    }
</script>
<style>
    .card-actions {
        position: absolute;
        bottom: 0;
    }
</style>
