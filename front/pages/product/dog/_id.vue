<template>
  <div>
    <v-card class="mx-auto" max-width="344">
      <v-img :src="dogSelectItem.image" height="200px"></v-img>

      <v-card-title>
        {{ dogSelectItem.name }}
      </v-card-title>

      <v-card-text class="product-right">
        {{ dogSelectItem.price }} won
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
          @click="onOrder(dogSelectItem)"
        >
          Order
        </v-btn>

        <v-btn color="orange" text @click="onCart(dogSelectItem)">
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
            {{ dogSelectItem.description }}
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
    console.log('detail page');
    this.$store.dispatch('page/getCurrentPage', this.$nuxt.$route.name);
  },
  components: {
    Snackbar,
  },
  computed: {
    ...mapState({
      dogSelectItem: (state) => state.product.dogSelectItem,
      cartItems: (state) => state.cart.cartItems,
    }),
  },
  methods: {
    onCart(item) {
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
      item.orderQuantity = this.orderQuantity;
      this.title = 'Complete Carting';
      this.snackbar = true;
      const time = setInterval(() => {
        if (this.snackbar) {
          this.snackbar = false;
          clearInterval(time);
        }
      }, 5000);

      this.$store.dispatch('cart/pushCartData', item);
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
      console.log('onUpQuantity');
      this.orderQuantity++;
    },
    onDownQuantity() {
      console.log('onDownQuantity');
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
