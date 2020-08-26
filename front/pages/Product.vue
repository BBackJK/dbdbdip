<template>
  <div>
    <v-tabs>
      <v-tab>Item One</v-tab>
      <v-tab>Item Two</v-tab>
      <v-tab>Item Three</v-tab>
    </v-tabs>Product
  </div>
</template>

<script>
export default {
  created() {
    console.log(this.$nuxt.$route.name);
  },
};
</script>
<style scoped></style>
