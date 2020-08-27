<template>
  <div>Item List</div>
</template>

<script>
export default {
    created() {
    console.log(this.$nuxt.$route.name);
  }
};
</script>
<style scoped></style>
