<template>
  <v-container fluid>
    <form>
      <v-text-field v-model="email" label="E-mail" required></v-text-field>
      <v-text-field v-model="name" label="Name" required></v-text-field>
      <v-text-field v-model="phone" label="Phone" required></v-text-field>
      <v-text-field v-model="zipcode" label="Zip-code" required></v-text-field>
      <v-text-field v-model="address" label="Address" required></v-text-field>
    </form>
  </v-container>
</template>

<script>
export default {
  data() {
    return {
      show: false,
      email: '',
      name: '',
      phone: '',
      zipcode: '',
      address: '',
      createdAt: '',
    };
  },
  created() {
    const userInfo = this.$store.state.auth.userInfo;
    console.log(userInfo);

    this.email = userInfo.email;
    this.name = userInfo.name;
    this.phone = userInfo.phone;
    this.zipcode = userInfo.zipcode;
    this.address = userInfo.address;
    this.createdAt = userInfo.createdAt;
  },
};
</script>

<style scoped></style>
