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
            <v-btn
                color="orange"
                text
            >
                Play
            </v-btn>

            <v-btn
                color="orange"
                text
            >
                Details
            </v-btn>
        </v-card-actions>
    </v-card>
</template>
<script lang="ts">
    import {Component, Prop, Vue} from 'vue-property-decorator';
    import {AudioBookDTO} from 'ablams-js-dto/src/domain/models';

    @Component ({
        props: {
            abook: Object as () => AudioBookDTO
        }
    })
    export default class AudiobookCard extends Vue {

        protected created(): void {
            this.initialize();
        }

        protected initialize(): void {

        }

        public getImageSrcUrl(): string{
            return `/api/audio-books/${this.$props.abook.id}/cover/raw`
        }
    }
</script>
<style>
    .card-actions {
        position: absolute;
        bottom: 0;
    }
</style>
