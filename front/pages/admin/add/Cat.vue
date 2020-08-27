<template>
  <div>Admin Add cat product</div>
</template>

<script>
export default {
  created() {
    this.$store.dispatch('page/getCurrentPage', this.$nuxt.$route.name);
  },
};
</script>
<style scoped></style>
