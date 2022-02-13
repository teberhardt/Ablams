<template>
    <div v-if="currentAudiobook != null" >
        <AudioBookPlayer :audiobook="currentAudiobook"></AudioBookPlayer>
    </div>
</template>

<script lang="ts">
import {AudiobookDTO} from 'ablams-models/ablams/communication';
import { Vue, Component } from 'vue-property-decorator';
import AudioBookPlayer from '@/components/AudioBookPlayer.vue';

@Component({
    components: {AudioBookPlayer}
})
export default class Bottombar extends Vue {

   currentAudiobook : AudiobookDTO | null = null ;

    mounted() {
        this.$root.$on('playAudiobook', (audiobookDTO: AudiobookDTO) => {
            console.log("receiving event to play audiobook " + audiobookDTO.name);
            this.currentAudiobook = audiobookDTO;
        });
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
