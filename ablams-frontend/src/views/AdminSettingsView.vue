<template>
    <div>
        <template>
            <v-data-table
                :headers="headers"
                :items="aLibs"
                sort-by="id"
                class="elevation-1"
            >
                <template v-slot:top>
                    <v-toolbar flat color="white">
                        <v-toolbar-title>Audiolibraries</v-toolbar-title>
                        <v-divider
                            class="mx-4"
                            inset
                            vertical
                        ></v-divider>
                        <v-spacer></v-spacer>
                        <v-dialog v-model="dialog" max-width="500px">
                            <template v-slot:activator="{ on }">
                                <v-btn color="primary" dark class="mb-2" v-on="on">New Item</v-btn>
                            </template>
                            <v-card>
                                <v-card-title>
                                    <span class="headline">{{ formTitle }}</span>
                                </v-card-title>

                                <v-card-text>
                                    <v-container>
                                        <v-row>
                                            <v-col cols="12" sm="6" md="4">
                                                <v-text-field
                                                    readonly
                                                    disabled
                                                    v-model="editedItem.id"
                                                              label="Id"></v-text-field>
                                            </v-col>
                                            <v-col cols="12" sm="6" md="4">
                                                <v-text-field v-model="editedItem.filepath"
                                                              label="Filepath"></v-text-field>
                                            </v-col>
                                        </v-row>
                                    </v-container>
                                </v-card-text>

                                <v-card-actions>
                                    <v-spacer></v-spacer>
                                    <v-btn color="blue darken-1" text @click="close()">Cancel</v-btn>
                                    <v-btn color="blue darken-1" text @click="save()">Save</v-btn>
                                </v-card-actions>
                            </v-card>
                        </v-dialog>
                    </v-toolbar>
                </template>
                <template v-slot:item.action="{ item }">
                    <v-icon small class="mr-2" @click="editItem(item)">mdi-pencil</v-icon>
                    <v-icon small @click="deleteItem(item)">mdi-delete</v-icon>
                </template>

                <template v-slot:no-data>
                    <v-btn color="primary" @click="initialize()">Reset</v-btn>
                </template>
            </v-data-table>
        </template>
    </div>


</template>

<script lang="ts">
    import {AudioLibraryDTO} from 'ablams-js-dto/src/domain/models';
    import {Component, Vue} from 'vue-property-decorator';
    import AudioLibraryResource from '@/rest/AudioLibraryResource';

    @Component
    export default class AdminSettingsView extends Vue {

        private dialog: boolean = false;
        private headers: any = [
            {
                text: 'Id',
                align: 'left',
                sortable: false,
                value: 'id',
            },
            {text: 'Filepath', value: 'filepath'},
            { text: 'Actions', value: 'action', sortable: false },
        ];

        private aLibs: AudioLibraryDTO[] = [];
        private editedIndex: number = -1;

        private editedItem: AudioLibraryDTO = {
            id: 0,
            filepath: '',
        };

        private defaultItem: AudioLibraryDTO = {
            id: 0,
            filepath: '',
        };

        protected get formTitle(): string {
            return this.editedIndex === -1 ? 'New Item' : 'Edit Item';
        }


        protected created(): void {
            this.initialize();
        }


        protected initialize(): void {
            AudioLibraryResource.fetchAll().then((value) => {
                this.aLibs = value.data;
            });
        }

        protected editItem(item: AudioLibraryDTO): void {
            this.editedIndex = this.aLibs.indexOf(item);
            this.editedItem = Object.assign({}, item);
            this.dialog = true;
        }

        protected deleteItem(item: AudioLibraryDTO): void {
            const index = this.aLibs.indexOf(item);
            if (confirm('Are you sure you want to delete this item?')) {
                this.aLibs.splice(index, 1);
            }
        }

        protected close(): void {
            this.dialog = false;
            setTimeout(() => {
                this.editedItem = Object.assign({}, this.defaultItem);
                this.editedIndex = -1;
            }, 300);
        }

        protected save(): void {
            if (this.editedIndex > -1) {
                Object.assign(this.aLibs[this.editedIndex], this.editedItem);
            } else {
                this.aLibs.push(this.editedItem);
                AudioLibraryResource.insert(this.editedItem);
            }
            this.close();
        }
    }
</script>

<style scoped>

</style>
