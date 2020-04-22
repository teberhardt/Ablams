<template>
    <div>
        <v-subheader>Alle Audiobooks</v-subheader>

        <v-container fluid>
            <v-row dense>
                <v-col
                    v-for="abook in aBooks"
                    :key="abook.id"
                    :cols="abook.name"
                >
                    <v-card
                        class="mx-auto"
                        max-width="400"
                        outlined
                    >
                        <v-img
                            class="white--text align-end"
                            height="200px"
                            src="https://cdn.vuetifyjs.com/images/cards/docks.jpg"
                        >
                            <v-card-title>{{abook.name}}</v-card-title>
                        </v-img>

                        <v-card-actions>
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
                </v-col>
            </v-row>
        </v-container>
    </div>
</template>

<script lang="ts">
    import {AudioBookDTO} from 'ablams-js-dto/src/domain/models';
    import {Component, Vue} from 'vue-property-decorator';
    import AudioLibraryResource from '@/rest/AudioLibraryResource';
    import AudioBookResource from "@/rest/AudioBookResource";

    @Component
    export default class AudioBooksView extends Vue {

        private aBooks: AudioBookDTO[] = [];



        protected created(): void {
            this.initialize();
        }


        protected initialize(): void {
            AudioBookResource.fetchAll().then((value) => {
                this.aBooks = value.data;
            });
        }


    }
</script>

<style scoped>

</style>
