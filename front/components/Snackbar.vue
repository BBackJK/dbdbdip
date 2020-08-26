<template>
  <v-snackbar v-model="snackbar" :top="true">
    {{ title }}
    <template v-slot:action="{ attrs }">
      <v-btn color="blue" text v-bind="attrs" @click="snackbar = false">
        Close
      </v-btn>
    </template>
  </v-snackbar>
</template>

<script>
export default {
  data() {
    return {
      snackbar: '',
    };
  },
  props: {
    title: {
      type: String,
      default: null,
    },
    snackbarFlag: {
      type: Boolean,
      default: false,
    },
  },
  created() {
    this.snackbar = this.snackbarFlag;
    setTimeout(() => {
      if (this.snackbar) this.snackbar = false;
    }, 5000);
  },
};
</script>

<style scoped></style>
