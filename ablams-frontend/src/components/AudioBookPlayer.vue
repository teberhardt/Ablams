<template>
    <v-card
        class="mt-5"
        ref="playerContainer"
        :loading="!audioDownloaded"
        v-bind="$attrs"
    >
        <audio
            ref="audio"
            @pause="playing = false"
            @play="playing = true"
            @timeupdate="handleTimeUpdate"
            @durationchange="setDuration"
            @canplaythrough="audioDownloaded = true"
            @ended="handleAudioEnd"
            @error="$emit('error', $event)"
        />

        <v-slider
            class="audio-seeker"
            min="0"
            max="1000000"
            :value="parseInt((currentTime / duration) * 1000000) || 0"
            @input="seek($event)"
            @focus="seekerFocused = true"
            @blur="seekerFocused = false"
        />

        <v-container class="grey lighten-5">
            <v-row no-gutters>
                <v-col sm="4">
                    <v-avatar tile class="d-inline-block" v-if="albumArt">
                        <v-img :src="albumArt" aspect-ratio="1"></v-img>
                    </v-avatar>
                    <div
                        class="mx-auto"
                        :class="albumArt && 'ml-3 d-inline-block'">
                        <span v-if="trackTitle" class="d-block" v-text="trackTitle"></span>
                        <span
                            v-text="trackSubtitle"
                            class="d-block text-uppercase font-weight-bold"
                            style="letter-spacing: 0.05em"
                        ></span>
                        <span
                            v-text="abookProgress.trackNr"
                            class="d-block text-uppercase font-weight-bold"
                            style="letter-spacing: 0.05em"
                        ></span>
                    </div>
                </v-col>
                <v-col sm="2">
                        <span>{{currentTime}}/{{duration}}</span>
                </v-col>
                <v-spacer cols="2"></v-spacer>
                <v-col
                    sm="2"
                    class="d-flex"
                >

                    <div class="mx-2">
                        <v-btn
                            icon
                            :disabled="!audioDownloaded || !allowPrevious"
                            @click="$emit('previous-audio')"
                        >
                            <v-icon size="20">mdi-skip-previous</v-icon>
                        </v-btn>
                    </div>

                    <div class="mx-2">
                        <v-btn
                            icon
                            :disabled="!audioDownloaded"
                            @click="forwardSeconds(-5)"
                        >
                            <v-icon size="20">mdi-rewind-5</v-icon>
                        </v-btn>
                    </div>

                    <div class="mx-3">
                        <v-btn
                            icon
                            :disabled="!audioDownloaded"
                            @click="playing = !playing"
                        >
                            <v-icon
                                size="30"
                                v-text="playing ? 'mdi-pause' : 'mdi-play'"
                            ></v-icon>
                        </v-btn>
                    </div>

                    <div class="mx-2">
                        <v-btn icon :disabled="!audioDownloaded" @click="forwardSeconds(5)">
                            <v-icon size="20">mdi-fast-forward-5</v-icon>
                        </v-btn>
                    </div>

                    <div class="mx-2">
                        <v-btn
                            icon
                            :disabled="!audioDownloaded || !allowNext"
                            @click="$emit('next-audio')"
                        >
                            <v-icon size="20">mdi-skip-next</v-icon>
                        </v-btn>
                    </div>
                </v-col>
                <v-col
                       sm="2">
                    <div
                        class="d-flex align-center mx-auto justify-end"
                        style="max-width: 12rem"
                    >
                        <v-btn icon @click="muted = !muted">
                            <v-icon v-text="volumeIcon"></v-icon>
                        </v-btn>

                        <v-slider
                            class="mt-2 volume-slider"
                            :value="muted ? 0 : volume"
                            @input="setVolume"
                            thumb-label
                            max="100"
                            min="0"
                        ></v-slider>
                    </div>
                </v-col>
            </v-row>
        </v-container>
    </v-card>
</template>

<script lang="ts">
import {AudiobookDTO, ProgressableDTO} from 'ablams-models/ablams/communication';
import ProgressRessource from '@/rest/ProgressRessource';
import AudiofileResource from '@/rest/AudiofileResource';
import {Vue, Watch, Component, Prop, PropSync} from 'vue-property-decorator';

class ProgressableUpdate implements ProgressableDTO {
    audiobookId = -1;
    id = 0;
    secondsInto: number = -1;
    trackNr = -1;
    userId = -1;
}

@Component
export default class AudioBookPlayer extends Vue {

    @Prop(AudiobookDTO) readonly audiobook!: AudiobookDTO

    autoplay = true;
    audio!: HTMLAudioElement;
    abookProgress = new ProgressableUpdate();

    albumArt = "";

    allowPrevious = false;
    allowNext = false;
    audioDownloaded = false;
    currentTime = 0;
    duration = 0;
    playing = false;
    volume = 20;
    seekerFocused = false;
    keydownListener : any  = null;
    muted = false;

    get volumeIcon(){
        if (this.muted) {
            return "mdi-volume-off";
        } else if (this.volume === 0) {
            return "mdi-volume-low";
        } else if (this.volume >= 50) {
            return "mdi-volume-high";
        } else {
            return "mdi-volume-medium";
        }
    }

    get trackTitle(){
        return this.audiobook?.name;
    }

    get trackSubtitle(){
        return this.audiobook?.authorId;
    }

    mounted() {
        this.audio = this.$refs.audio as HTMLAudioElement;

        this.keydownListener = (event: any) => {
            if (event.keyCode === 32 && this.seekerFocused) {
                event.preventDefault();
                this.playing = !this.playing;
            }
        };
        document.addEventListener('keydown', this.keydownListener);

        this.audio.volume = this.volume / 100;
        this.muted = this.audio.muted;

        this.onAudiobook();
    }

    fetchAndApplyLastProgress(){
        if(this.audiobook?.id != undefined) {
            ProgressRessource.fetchByAudioBookId(this.audiobook.id).then(e => {
                this.abookProgress = e.data
                this.audio!.src = AudiofileResource.getStreamEndpointForAudioFile(this.abookProgress.trackNr);
                this.audio!.currentTime = this.abookProgress.secondsInto;
                this.playing = true;
                this.audioDownloaded = false;
            }).catch(e => {
                console.log("error starting progress" + e)
            });
        }
    }

    saveCurrentProgress(){
        if(this.audiobook?.id != undefined) {

            let p = new ProgressableUpdate;
            p.audiobookId = this.audiobook.id;
            p.secondsInto = this.currentTime;
            p.trackNr = this.abookProgress.trackNr;
            p.userId = -1;

          ProgressRessource.updateByAudioBookId(p).then(e => {
              console.log(`update progress to track: ${p.trackNr} time: ${p.secondsInto}`)
            }).catch(e => {
                console.log("error starting progress" + e)
            });
        }
    }

    setVolume(value: number) {
        this.volume = value;
        this.audio.volume = value / 100;
    }

    forwardSeconds(seconds: number) {
        let newTimestamp = this.currentTime + seconds;

        if (newTimestamp < 0) {
            newTimestamp = 0;
        } else if (newTimestamp > this.duration) {
            newTimestamp = this.duration;
        }

        this.audio.currentTime = newTimestamp;
    }

    setDuration() {
        this.$data.duration = (this.audio as HTMLAudioElement).duration;
    }

    handleTimeUpdate() {
        this.currentTime = this.audio.currentTime;
        console.log("time is now " + this.$data.currentTime)
    }

    handleAudioEnd() {
        if (this.$data.allowNext) {
            this.$emit("next-audio");
        }
    }

    seek(timePercent: number) {
        this.audio.currentTime =
            this.audio.duration * (timePercent / 1000000.0);
    }

    beforeDestroy() {
        document.removeEventListener("keydown", this.keydownListener);
    }

    public playAudiobook() {
            this.albumArt = `/api/audio-books/${this.audiobook?.id}/cover/image`
            this.fetchAndApplyLastProgress();
    }

    onAudiobook() {
        this.albumArt = `/api/audio-books/${this.audiobook?.id}/cover/image`
        this.fetchAndApplyLastProgress();
    }

    @Watch('playing')
    onPlaying(playing: boolean) {
        if (playing) {
            return this.audio.play();
        }
        this.audio.pause();
    }

    @Watch('muted')
    onMuting(value: boolean) {
        this.audio.muted = value;
    }

    @Watch('audioDownloaded')
    onAudioDownloaded(muted: boolean) {
        if (this.autoplay) {
            if (muted) {
                this.playing = true;
            }
        }
    }

    @Watch('volume')
    onVolume() {
        this.muted = false;
    }

}
</script>

<style lang="scss">

.volume-slider .v-messages {
  display: none;
}

.player-container{
    display: flex
}

.audio-seeker {
  .v-slider {
    min-height: 0;
  }

  .v-slider--horizontal {
    margin-left: 0;
    margin-right: 0;
  }

  .v-slider__track-background {
    width: 100% !important;
  }

  .v-messages {
    display: none;
  }

  .v-slider__thumb:before {
    opacity: 0;
  }

  .v-slider__thumb {
    height: 10px;
    width: 10px;
    cursor: pointer;
  }

  .v-slider__track-container {
    cursor: pointer;
    height: 6px !important;
  }

  .v-slider__track-fill,
  .v-slider__track-background,
  .v-slider__track-container {
    border-radius: 9999px;
  }

}
</style>
