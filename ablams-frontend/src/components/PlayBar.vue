<template>
    <v-card shaped tile class="sticky-card"  v-if="currentAudiobook !== null" height="100px">
        <v-progress-linear
            :value="50"
            class="my-0"
            height="3"/>

        <v-list>
            <v-list-item>
                    <v-img
                        contain
                        class="image"
                       :src="getImageSrcUrl()"/>
                <v-list>
                    <v-list-item-content>
                        <v-list-item-title>{{ currentAudiobook.name }}</v-list-item-title>
                        <v-list-item-subtitle>The author</v-list-item-subtitle>
                    </v-list-item-content>
                </v-list>

                <v-spacer></v-spacer>

                <v-list-item-icon>
                    <v-btn icon>
                        <v-icon>mdi-rewind</v-icon>
                    </v-btn>
                </v-list-item-icon>

                <v-list-item-icon :class="{ 'mx-5': $vuetify.breakpoint.mdAndUp }">
                    <v-btn icon>
                        <v-icon v-if="isPaused" v-on:click="pause">mdi-pause</v-icon>
                        <v-icon v-else v-on:click="play">mdi-play</v-icon>
                    </v-btn>
                </v-list-item-icon>

                <v-list-item-icon
                    class="ml-0"
                    :class="{ 'mr-3': $vuetify.breakpoint.mdAndUp }"
                >
                    <v-btn icon>
                        <v-icon>mdi-fast-forward</v-icon>
                    </v-btn>
                </v-list-item-icon>
            </v-list-item>
        </v-list>
    </v-card>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator';
import {AudiobookDTO} from 'ablams-communication/ablams/communication';
import PlayService from "@/service/PlayService";
import {VBottomSheet} from "vuetify/lib";

@Component({
    components: {bottomSheet: VBottomSheet}
})
export default class PlayBar extends Vue {

    private audio = new Audio();
    private currentAudiobook: AudiobookDTO | null = null;

    protected mounted(): void {
        this.$root.$on('playAudiobook',(audiobookDTO: AudiobookDTO) => {
                this.currentAudiobook = audiobookDTO;
                console.log(this.currentAudiobook);
                this.play();
            });
    }

    private play() {
        if(this.currentAudiobook != undefined) {
            console.log("play " + this.currentAudiobook.name)
            PlayService.play(this.audio, this.currentAudiobook);
        }
    }

    private pause() {
        this.audio.pause()
    }

    private isPaused(): boolean {
        return this.audio.paused;
    }

    protected created(): void {
        this.initialize();
    }

    protected initialize(): void {
    }

    public getImageSrcUrl(): string{
        return `/api/audio-books/${this.currentAudiobook?.id}/cover/image`;
    }
}
</script>

<style scoped>

.sticky-card {
    position: fixed;
    bottom: 0;
    z-index: 1;
    left: 12.5%;
    right: 12.5%;

/*    !* centering *!

    transform: translate(-50%, -50%);*/
}

.image {
    max-height: 50px;
    display: block;
}
</style>
