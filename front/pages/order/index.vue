<template>
  <v-row align="center">
    <v-expansion-panels :multiple="true" :popout="true">
      <v-expansion-panel>
        <v-expansion-panel-header>
          <v-row no-gutters>
            <v-col cols="8">Product Order List</v-col>
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
              <v-list-item-title>Name</v-list-item-title>
              <v-list-item-subtitle>{{
                item.product.name
              }}</v-list-item-subtitle>
            </v-list-item-content>

            <v-list-item-content>
              <v-list-item-title>Price</v-list-item-title>
              <v-list-item-subtitle
                >{{ item.product.price }} won</v-list-item-subtitle
              >
            </v-list-item-content>

            <v-list-item-content>
              <v-list-item-title>Quantity</v-list-item-title>
              <v-list-item-subtitle
                >{{ item.orderQuantity }} ea</v-list-item-subtitle
              >
            </v-list-item-content>

            <v-list-item-content>
              <v-list-item-title>Sub-Total</v-list-item-title>
              <v-list-item-subtitle>
                {{ item.orderQuantity * item.product.price * 1 }}
                won
              </v-list-item-subtitle>
            </v-list-item-content>
          </v-list-item>
        </v-expansion-panel-content>
      </v-expansion-panel>
      <v-expansion-panel>
        <v-expansion-panel-header>
          <v-row no-gutters>
            <v-col cols="24">Order User Info</v-col>
            <v-col v-if="name === ''" cols="6" class="text--secondary"
              >Order User name : Write Your name</v-col
            >
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
      <v-btn v-if="userInfo" class="mr-4" color="orange" @click="onUserOrder"
        >PAYMENT</v-btn
      >
      <v-btn v-else class="mr-4" color="orange" @click="onOrder">PAYMENT</v-btn>
      <v-btn @click="onBack">CANCEL</v-btn>
    </v-main>
    <Dialog
      v-if="dialog"
      :dialog-flag="dialog"
      headline="Order Success!"
      link-title="Check Order"
      link-push="/order/result"
    />
  </v-row>
</template>

<script>
import { mapState } from 'vuex';

import Dialog from '@/components/Dialog.vue';

export default {
  components: {
    Dialog,
  },
  data() {
    return {
      name: '',
      email: '',
      phone: '',
      zipcode: '',
      address: '',
      postData: [],
      dialog: false,
      orderNumber: '',
    };
  },
  computed: {
    ...mapState({
      orderItems: (state) => state.order.orderItems,
      totalPrice: (state) => state.order.totalPrice,
      ordered: (state) => state.order.ordered,
      userInfo: (state) => state.user.userInfo,
    }),
    orderUsername() {
      return this.name;
    },
  },
  watch: {
    ordered(val) {
      if (val) {
        this.dialog = true;
        this.$store.dispatch('order/pushOrderNumber', this.orderNumber);
      }
    },
  },
  created() {
    console.log(this.orderItems);
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
  mounted() {
    const getYear = new Date().getFullYear();
    const getMonth = new Date().getMonth();
    const getDay = new Date().getDate();
    const fullDate = getYear + '' + (getMonth + 1) + '' + getDay + '';
    const getRandom = Math.random().toString(36).substr(2, 11).toUpperCase();
    this.orderNumber = fullDate + getRandom;
  },
  methods: {
    onUserOrder() {
      this.$store.dispatch('order/modifyOrderFlag');

      for (let i = 0; i < this.orderItems.length; i++) {
        const pushData = {};
        pushData.orderNumber = this.orderNumber;
        pushData.orderQuantity = this.orderItems[i].orderQuantity;
        pushData.totalPrice =
          this.orderItems[i].orderQuantity *
          this.orderItems[i].product.price *
          1;
        pushData.user_id = this.userInfo.id;
        pushData.product_id = this.orderItems[i].product.id;
        this.postData.push(pushData);
      }
      console.log(this.postData);
      this.$store.dispatch('order/postOrderData', this.postData);
      this.postData = [];
    },
    onOrder() {
      this.$store.dispatch('order/modifyOrderFlag');
      for (let i = 0; i < this.orderItems.length; i++) {
        const pushData = {};
        pushData.orderNumber = this.orderNumber;
        pushData.orderQuantity = this.orderItems[i].orderQuantity;
        pushData.totalPrice =
          this.orderItems[i].orderQuantity *
          this.orderItems[i].product.price *
          1;
        pushData.username = this.name;
        pushData.userEmail = this.email;
        pushData.userPhone = this.phone;
        pushData.userZipcode = this.zipcode;
        pushData.userAddress = this.address;
        pushData.product_id = this.orderItems[i].product.id;
        this.postData.push(pushData);
      }

      console.log(this.postData);
      this.$store.dispatch('order/postOrderForNoMember', this.postData);
      this.postData = [];
    },
    onBack() {
      this.$router.go(-1);
    },
  },
};
</script>

<style>
.price-where {
  float: right;
}
</style>
