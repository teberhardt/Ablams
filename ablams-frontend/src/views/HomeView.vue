<template>
  <div>
    <v-carousel :show-arrows="false" hide-delimiter-background="true" delimiter-icon="mdi-minus" cycle="true" continuous="true" height="300">
      <v-carousel-item
        v-for="(item,i) in TopCarouselItems"
        :key="i"
        :src="item.src"
      ></v-carousel-item>
    </v-carousel>
    <v-subheader>Zuletzt gehört</v-subheader>
    <v-sheet
        elevation="7"
        class="ma-0 pa-0"
      >
        <v-slide-group
          v-model="model"
          class="ma-0"
          :prev-icon="prevIcon ? 'mdi-minus' : undefined"
          :next-icon="nextIcon ? 'mdi-plus' : undefined"
          :multiple="multiple"
          :mandatory="mandatory"
          :show-arrows="showArrows"
          :center-active="centerActive"

        >
        
          <v-slide-item
            v-for="n in 15"
            :key="n"
            v-slot:default="{ active, toggle }"
          >
            <v-card
              :color="active ? 'primary' : 'grey lighten-1'"
              class="ma-4"
              height="200"
              width="135"
              @mouseover="toggle"
            >
              <v-row
                class="fill-height"
                align="center"
                justify="center"
              >
                <v-scale-transition>
                  <v-icon
                    v-if="active"
                    color="white"
                    size="48"
                  ></v-icon>
                </v-scale-transition>
              </v-row>
            </v-card>
          </v-slide-item>
        </v-slide-group>
      </v-sheet>



    <v-subheader>Neu</v-subheader>
    <v-sheet
        elevation="7"
        class="ma-0 pa-0"
      >
        <v-slide-group
          v-model="model"
          class="ma-0"
          :prev-icon="prevIcon ? 'mdi-minus' : undefined"
          :next-icon="nextIcon ? 'mdi-plus' : undefined"
          :multiple="multiple"
          :mandatory="mandatory"
          :show-arrows="showArrows"
          :center-active="centerActive"

        >
        
          <v-slide-item
            v-for="n in 15"
            :key="n"
            v-slot:default="{ active, toggle }"
          >
            <v-card
              :color="active ? 'primary' : 'grey lighten-1'"
              class="ma-4"
              height="200"
              width="135"
              @mouseover="toggle"
            >
              <v-row
                class="fill-height"
                align="center"
                justify="center"
              >
                <v-scale-transition>
                  <v-icon
                    v-if="active"
                    color="white"
                    size="48"
                  ></v-icon>
                </v-scale-transition>
              </v-row>
            </v-card>
          </v-slide-item>
        </v-slide-group>
      </v-sheet>
  </div>
</template>

<script lang="ts">
import Vue from 'vue';

export default Vue.extend({
  components: {
  },
  data: () => ({
            TopCarouselItems: [
              { src: 'https://steamuserimages-a.akamaihd.net/ugc/866240102625297315/AA2C1F9CD9F438CAEEA3882398054BCBA441DE24/'},
               
                { src: 'https://medienbewusst.de/cms/wp-content/uploads/pics/Ressort_Musik/fragezeichen.jpg'},
                { src: 'https://www.heldenderfreizeit.com/wp-content/uploads/2014/03/Ursula-Poznanski-Erebos-Aufmacherfoto-1200x800_c.jpg'},
                 ],
                
        }),
        methods: {
        },
});
</script>
