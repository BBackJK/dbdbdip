<template>
  <v-row align="center">
    <v-expansion-panels :multiple="true" :popout="true">
      <v-expansion-panel>
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
              <v-list-item-subtitle>
                {{ item.product.name }}</v-list-item-subtitle
              >
            </v-list-item-content>

            <v-list-item-content>
              <v-list-item-title>Price</v-list-item-title>
              <v-list-item-subtitle>
                {{ item.product.price }} won</v-list-item-subtitle
              >
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
                {{ item.orderQuantity * item.product.price * 1 }}
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
            <v-col v-if="name === ''" cols="6" class="text--secondary">
              Order User name : Write Your name
            </v-col>
            <v-col v-else cols="4" class="text--secondary">
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
  mounted() {
    console.log(this.orderItems);
    console.log(this.totalPrice);
    this.$store.dispatch('page/getCurrentPage', this.$nuxt.$route.name);

    if (this.userInfo) {
      // eslint-disable-next-line no-unused-expressions
      (this.name = this.userInfo.name),
        (this.email = this.userInfo.email),
        (this.phone = this.userInfo.phone),
        (this.zipcode = this.userInfo.zipcode),
        (this.address = this.userInfo.address);
    }
  },
  methods: {
    onClickTest() {
      console.log('click!!');
      const orderUserData = {
        name: this.name,
        email: this.email,
        phone: this.phone,
        zipcode: this.zipcode,
        address: this.address,
      };
      const orderProductData = {};

      orderProductData.orderQuantitys = [];
      orderProductData.productIds = [];
      for (let i = 0; i < this.orderItems.length; i++) {
        orderProductData.orderQuantitys.push(this.orderItems[i].orderQuantity);
        orderProductData.productIds.push(this.orderItems[i].product.id);
      }

      console.log(orderUserData);
      console.log(orderProductData);
    },
  },
};
</script>

<style>
.price-where {
  float: right;
}
</style>
