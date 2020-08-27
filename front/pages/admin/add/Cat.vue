<template>
  <div class="container">
    <form>
      <v-text-field v-model="name" label="Name" required></v-text-field>
      <v-text-field v-model="category" label="Category" required readonly></v-text-field>
      <v-text-field v-model="price" label="Price" required></v-text-field>
      <v-text-field v-model="imagePath" label="Image Path" required></v-text-field>
      <v-text-field v-model="description" label="Description" required></v-text-field>
      <v-text-field v-model="quantity" label="Quantity" required></v-text-field>
      <br />
      <v-btn class="mr-4" color="orange" @click="addCatSubmit">ADD</v-btn>
      <v-btn @click="addCatClear">CLEAR</v-btn>
    </form>
  </div>
</template>

<script>
export default {
  data() {
    return {
      name: '',
      category: 'cat',
      price: '',
      imagePath: '',
      description: '',
      quantity: '',
    };
  },
  created() {
    this.$store.dispatch('page/getCurrentPage', this.$nuxt.$route.name);
  },
  methods: {
    addCatSubmit() {
      const postData = {
        name: this.name,
        category: this.category,
        price: this.price,
        imagePath: this.imagePath,
        description: this.description,
        quantity: this.quantity * 1,
      };

      console.log(postData);
      console.log(typeof postData.quantity);
    },
    addCatClear() {
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
