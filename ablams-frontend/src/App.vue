<template>
    <main>
        <transition mode="out-in">
            <router-view/>
        </transition>
    </main>
</template>

<script lang="ts">
import Vue from 'vue';

export default Vue.extend({
  name: 'App',
  components: {
  },
  data: () => ({
    //
  }),
  methods: {
  },
});
</script>
