<template>
  <v-layout column justify-center align-center>
    <v-flex xs12 sm8 md6>
      <div class="text-center">
        <img src="~assets/dbdbdip/test1.png" />
      </div>
    </v-flex>
  </v-layout>
</template>

<script>
export default {
  created() {
    this.$store.dispatch('page/getCurrentPage', this.$nuxt.$route.name);
  },
};
</script>
