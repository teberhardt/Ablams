<template>
    <div>
        <v-subheader>Alle Audiobooks</v-subheader>
        <v-container fluid>
            <v-row justify="start">
                <v-col cols="10"
                       sm="6"
                       md="3"
                       lg="2"
                       xl="2"

                    v-for="abook in aBooks"
                    :key="abook.id"
                    :cols="abook.name">
                  <AudioBookCard :abook="abook"></AudioBookCard>
                </v-col>
            </v-row>
        </v-container>
    </div>
</template>

<script lang="ts">
    import {AudiobookDTO} from 'ablams-models/ablams/communication';
    import {Component, Vue} from 'vue-property-decorator';
    import AudioBookResource from "@/rest/AudioBookResource";
    import AudiobookCard from "@/components/AudiobookCard.vue";

    @Component({
        components: {AudioBookCard: AudiobookCard}
    })
    export default class AudioBooksView extends Vue {

        private aBooks: AudiobookDTO[] = [];

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
