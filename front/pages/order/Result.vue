<template>
  <v-card class="mx-auto" max-width="344" outlined>
    <v-list-item three-line>
      <v-list-item-content>
        <v-list-item-title class="headline mb-1"
          >Thank You !!</v-list-item-title
        >
        <hr />
        <br />
        <v-list-item-subtitle class="headline"
          >Order Complete !!</v-list-item-subtitle
        >
        <br />
        <br />
        <br />
        <v-list-item-subtitle
          >Remember That {{ orderNumber }}</v-list-item-subtitle
        >
      </v-list-item-content>
    </v-list-item>

    <v-card-actions>
      <v-spacer></v-spacer>
      <v-btn color="orange" text @click="goHome">HOME</v-btn>
      <v-btn color="orange" text @click="goBuyingList">Buying List</v-btn>
    </v-card-actions>
  </v-card>
</template>

<script>
import { mapState } from 'vuex';

export default {
  data() {
    return {};
  },
  computed: {
    ...mapState({
      orderNumber: (state) => state.order.orderNumber,
    }),
  },
  methods: {
    goHome() {
      this.$router.push('/');
    },
    goBuyingList() {
      this.$router.push('/buyinglist');
    },
  },
};
</script>

<style scoped></style>
