<template>

</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator';
import {AudiobookDTO} from 'ablams-communication/ablams/communication';
import PlayService from "@/service/PlayService";


export default Vue.extend({
  name: 'PlayBar',
  mounted() {
   this.$root.$on('initPlay',
       (audiobookDTO: AudiobookDTO) => {
          const audio = new Audio();

          PlayService.play(audiobookDTO, audio);
       });
  }
});
</script>

<style scoped>

</style>
