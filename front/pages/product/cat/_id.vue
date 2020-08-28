<template>
  <div>
    <v-card class="mx-auto" max-width="344" v-if="selectItem">
      <v-img :src="selectItem.imagePath" height="200px"></v-img>

      <v-card-title>
        {{ selectItem.name }}
      </v-card-title>

      <v-card-text class="product-right">
        {{ selectItem.price }} won
      </v-card-text>

      <v-card-text>
        Quantity :
        <span class="product-right">
          <v-btn x-small :disabled="orderQuantity === 1" @click="onDownQuantity"
            ><v-icon>mdi-minus</v-icon></v-btn
          >
          {{ orderQuantity }}
          <v-btn x-small @click="onUpQuantity"><v-icon>mdi-plus</v-icon></v-btn>
        </span>
      </v-card-text>
      <v-card-actions>
        <v-btn
          color="orange"
          text
          class="mr-14"
          @click="onOrder(selectItem)"
        >
          Order
        </v-btn>
        <v-btn color="orange" text @click="onUserCart(item)" v-if="userInfo">Cart</v-btn>
        <v-btn color="orange" text @click="onCart(selectItem)" v-else>
          Cart
        </v-btn>

        <v-spacer></v-spacer>
      </v-card-actions>
      <v-btn icon @click="show = !show">
        Description
        <v-icon>{{ show ? 'mdi-chevron-up' : 'mdi-chevron-down' }}</v-icon>
      </v-btn>

      <v-expand-transition>
        <div v-show="show">
          <v-divider></v-divider>

          <v-card-text>
            {{ selectItem.description }}
          </v-card-text>
        </div>
      </v-expand-transition>
    </v-card>

    <Snackbar v-if="snackbar" :title="title" :snackbar-flag="snackbar" />
  </div>
</template>

<script>
import { mapState } from 'vuex';

import Snackbar from '@/components/Snackbar.vue';

export default {
  data: () => ({
    show: false,
    orderQuantity: 1,
    snackbar: false,
    title: '',
  }),
  created() {
    this.$store.dispatch('page/getCurrentPage', this.$nuxt.$route.name);
    this.$store.dispatch('product/getProductById', this.$route.params.id);
  },
  components: {
    Snackbar,
  },
  computed: {
    ...mapState({
      selectItem: (state) => state.product.selectItem,
      cartItems: (state) => state.cart.cartItems,
      dogItems: (state) => state.product.dogItems,
      admin: (state) => state.user.admin,
      userInfo: (state) => state.user.userInfo,
      inCarted: (state) => state.cart.inCarted,
      message: (state) => state.cart.message,
    }),
  },
  methods: {
    onUserCart(item) {
      this.snackbar = false;

        console.log(item);
        const postData = {
          orderQuantity: 1,
          user_id: this.userInfo.id,
          product_id: item.id,
        }

        this.$store.dispatch('cart/createCartData', postData);

        if (this.inCarted) {
          this.$store.dispatch('cart/modifyCreatedFlag');
          this.title = 'Complete Carting';
          this.snackbar = true;

          const time = setInterval(() => {
            if (this.snackbar) {
              this.snackbar = false;
            clearInterval(time);
            }
          }, 5000);
        }

        if(!this.inCarted && this.message === 'Conflict') {
          this.title = 'Duplicated Items!';
          this.snackbar = true;
          const time = setInterval(() => {
            if (this.snackbar) {
              this.snackbar = false;
              clearInterval(time);
            }
          }, 5000);
          return;
        }
    },
    onCart(item) {
      this.$store.dispatch('cart/pushCartData', item);

      for (let i = 0; i < this.cartItems.length; i++) {
        if (item.name === this.cartItems[i].name) {
          this.title = 'Duplicated Items!';
          this.snackbar = true;
          const time = setInterval(() => {
            if (this.snackbar) {
              this.snackbar = false;
              clearInterval(time);
            }
          }, 5000);
          return;
        }
      }

      this.title = 'Complete Carting';
      this.snackbar = true;
      const time = setInterval(() => {
        if (this.snackbar) {
          this.snackbar = false;
          clearInterval(time);
        }
      }, 5000);
    },
    onOrder(item) {
      const orderItem = [];
      item.orderQuantity = this.orderQuantity;

      const totalPrice = item.orderQuantity * item.price * 1;
      orderItem.push(item);

      this.$store.dispatch('order/updateTotalPrice', totalPrice);
      this.$store.dispatch('order/pushOrderData', orderItem);
      this.$router.push('/order');
    },
    onUpQuantity() {
      this.orderQuantity++;
    },
    onDownQuantity() {
      this.orderQuantity--;
    },
  },
};
</script>

<style>
.product-right {
  text-align: right;
  /* width: 70%; */
}
.text-field {
  width: 10%;
  float: right;
}
</style>
