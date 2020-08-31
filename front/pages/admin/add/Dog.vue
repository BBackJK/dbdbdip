<template>
  <div class="container">
    <form>
      <v-text-field v-model="name" label="Name" required></v-text-field>
      <v-text-field
        v-model="category"
        label="Category"
        required
        readonly
      ></v-text-field>
      <v-text-field v-model="price" label="Price" required></v-text-field>
      <v-text-field
        v-model="imagePath"
        label="Image Path"
        required
      ></v-text-field>
      <v-text-field
        v-model="description"
        label="Description"
        required
      ></v-text-field>
      <v-text-field v-model="quantity" label="Quantity" required></v-text-field>
      <br />
      <v-btn class="mr-4" color="orange" @click="addDogSubmit">ADD</v-btn>
      <v-btn @click="addDogClear">CLEAR</v-btn>

      <Snackbar
        v-if="snackbar"
        title="CREATE PRODUCT SUCCESS !!"
        :snackbar-flag="snackbar"
      />
    </form>
  </div>
</template>

<script>
import { mapState } from 'vuex';

import Snackbar from '@/components/Snackbar.vue';

export default {
  components: {
    Snackbar,
  },
  data() {
    return {
      name: '',
      category: 'dog',
      price: '',
      imagePath: '',
      description: '',
      quantity: '',
      snackbar: false,
    };
  },
  computed: {
    ...mapState({
      productCreated: (state) => state.product.created,
    }),
  },
  watch: {
    productCreated(val) {
      if (val) {
        this.snackbar = true;
      }
    },
  },
  created() {
    this.$store.dispatch('page/getCurrentPage', this.$nuxt.$route.name);
  },
  methods: {
    addDogSubmit() {
      this.$store.dispatch('product/modifyCreatedFlag');
      this.snackbar = false;

      const postData = {
        name: this.name,
        category: this.category,
        price: this.price,
        imagePath: this.imagePath,
        description: this.description,
        quantity: this.quantity * 1,
        sellable: true,
      };

      this.$store.dispatch('product/createProduct', postData);
    },
    addDogClear() {
      this.name = '';
      this.price = '';
      this.imagePath = '';
      this.description = '';
      this.quantity = '';
    },
  },
};
</script>
<style scoped></style>
