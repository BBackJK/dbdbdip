<template>
  <v-row align="center">
    <v-expansion-panels :multiple="true" :popout="true">

      <v-expansion-panel v-if="!userInfo">

        <v-expansion-panel-header>
          <v-row no-gutters>
            <v-col cols="8">
              Product Order List
            </v-col>
            <v-col cols="3" class="text--secondary">
              Total Price :
              {{ totalPrice }} won
            </v-col>
          </v-row>
        </v-expansion-panel-header>

        <v-expansion-panel-content>
          <v-list-item v-for="item in orderItems" :key="item.id">
            <v-list-item-avatar>
              <v-img :src="item.imagePath"></v-img>
            </v-list-item-avatar>          

            <v-list-item-content>
              <v-list-item-title> Name </v-list-item-title>
              <v-list-item-subtitle> {{ item.name }}</v-list-item-subtitle>
            </v-list-item-content>

            <v-list-item-content>
              <v-list-item-title>Price</v-list-item-title>
              <v-list-item-subtitle> {{ item.price }} won</v-list-item-subtitle>
            </v-list-item-content>

            <v-list-item-content>
              <v-list-item-title>Quantity</v-list-item-title>
              <v-list-item-subtitle>
                {{ item.orderQuantity }} ea</v-list-item-subtitle
              >
            </v-list-item-content>

            <v-list-item-content>
              <v-list-item-title>Sub-Total</v-list-item-title>
              <v-list-item-subtitle>
                {{ item.orderQuantity * item.price * 1 }}
                won</v-list-item-subtitle
              >
            </v-list-item-content>
          </v-list-item>
        </v-expansion-panel-content>

      </v-expansion-panel>

      <v-expansion-panel v-else>

        <v-expansion-panel-header>
          <v-row no-gutters>
            <v-col cols="8">
              Product Order List
            </v-col>
            <v-col cols="3" class="text--secondary">
              Total Price :
              {{ totalPrice }} won
            </v-col>
          </v-row>
        </v-expansion-panel-header>

        <v-expansion-panel-content>
          <v-list-item v-for="item in orderItems" :key="item.id">
            <v-list-item-avatar>
              <v-img :src="item.product.imagePath"></v-img>
            </v-list-item-avatar>          

            <v-list-item-content>
              <v-list-item-title> Name </v-list-item-title>
              <v-list-item-subtitle> {{ item.product.name }}</v-list-item-subtitle>
            </v-list-item-content>

            <v-list-item-content>
              <v-list-item-title>Price</v-list-item-title>
              <v-list-item-subtitle> {{ item.product.price }} won</v-list-item-subtitle>
            </v-list-item-content>

            <v-list-item-content>
              <v-list-item-title>Quantity</v-list-item-title>
              <v-list-item-subtitle>
                {{ item.product.orderQuantity }} ea</v-list-item-subtitle
              >
            </v-list-item-content>

            <v-list-item-content>
              <v-list-item-title>Sub-Total</v-list-item-title>
              <v-list-item-subtitle>
                {{ item.product.orderQuantity * item.product.price * 1 }}
                won</v-list-item-subtitle
              >
            </v-list-item-content>
          </v-list-item>
        </v-expansion-panel-content>

      </v-expansion-panel>
      <v-expansion-panel>
        <v-expansion-panel-header>
          <v-row no-gutters>
            <v-col cols="24">
              Order User Info
            </v-col>
            <v-col cols="6" class="text--secondary" v-if="this.name === ''">
              Order User name : Write Your name
            </v-col>
            <v-col cols="4" class="text--secondary" v-else>
              Order User name :
              {{ orderUsername }}
            </v-col>
          </v-row>
        </v-expansion-panel-header>
        <v-expansion-panel-content>
          <form>
            <v-text-field v-model="name" label="Name" required></v-text-field>
            <v-text-field
              v-model="email"
              label="E-mail"
              required
            ></v-text-field>
            <v-text-field
              v-model="phone"
              label="Phone (not hypen -)"
              required
            ></v-text-field>
            <v-text-field
              v-model="zipcode"
              label="Zipcode"
              required
            ></v-text-field>
            <v-text-field
              v-model="address"
              label="Address"
              required
            ></v-text-field>
            <br />
          </form>
        </v-expansion-panel-content>
      </v-expansion-panel>
    </v-expansion-panels>
    <br />
    <br />
    <br />
    <br />
    <br />
    <v-main>
      <v-btn class="mr-4" color="orange" @click="onClickTest">PAYMENT</v-btn>
      <v-btn>CANCEL</v-btn>
    </v-main>
  </v-row>
</template>

<script>
import { mapState } from 'vuex';

export default {
  data() {
    return {
      name: '',
      email: '',
      phone: '',
      zipcode: '',
      address: '',
    };
  },
  mounted() {
    this.$store.dispatch('page/getCurrentPage', this.$nuxt.$route.name);

    if (this.userInfo) {
      this.name = this.userInfo.name,
      this.email = this.userInfo.email,
      this.phone = this.userInfo.phone,
      this.zipcode = this.userInfo.zipcode,
      this.address = this.userInfo.address
    }
  },
  computed: {
    ...mapState({
      orderItems: (state) => state.order.orderItems,
      totalPrice: (state) => state.order.totalPrice,
      userInfo: (state) => state.user.userInfo,
    }),
    orderUsername() {
      return this.name;
    },
  },
  methods: {
    onClickTest() {
      console.log('click!!');
      const orderData = {
        name: this.name,
        email: this.email,
        phone: this.phone,
        zipcode: this.zipcode,
        address: this.address,
      };
      console.log(orderData);
    },
  },
};
</script>

<style>
.price-where {
  float: right;
}
</style>
