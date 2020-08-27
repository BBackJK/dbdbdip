<template>
  <v-container fluid>
    <v-row dense>
      <v-btn v-if="admin" color="red" fab large dark bottom right @click="onAddProduct">
        <v-icon>mdi-plus</v-icon>
      </v-btn>
    </v-row>
  </v-container>
</template>

<script>
import { mapState } from 'vuex';

export default {
  created() {
    this.$store.dispatch('page/getCurrentPage', this.$nuxt.$route.name);
  },
  computed: {
    ...mapState({
      admin: (state) => state.user.admin,
    }),
  },
  methods: {
    onAddProduct() {
      this.$router.push('/admin/add/cat');
    },
  },
};
</script>
<style scoped></style>
