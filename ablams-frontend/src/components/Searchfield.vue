<template>
    <v-autocomplete prepend-inner-icon="mdi-magnify"
                    single-line
                    clearable
                    filled
                    dense
                    hide-details
                    rounded
                    placeholder="Search..."
    />
</template>

<script>
    import { Component, Vue } from 'vue-property-decorator';

    export default Vue.extend({
        name: 'Searchfield',
        components: {

        },
        data: () => ({
        }),
        methods: {
        },
    });
</script>

<style scoped>

</style>
