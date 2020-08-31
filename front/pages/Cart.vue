<template>
  <div>
    <v-list three-line>
      <template>
        <v-subheader>Cart List</v-subheader>

        <v-list-item v-if="cartItems.length === 0">{{
          noCartProduct
        }}</v-list-item>

        <v-list-item v-for="item in cartItems" :key="item.id">
          <template>
            <v-list-item-avatar>
              <v-img :src="item.product.imagePath"></v-img>
            </v-list-item-avatar>

            <v-list-item-content>
              <v-list-item-title>{{ item.product.name }}</v-list-item-title>
              <v-list-item-subtitle
                >{{ item.product.price }} won</v-list-item-subtitle
              >
            </v-list-item-content>
          </template>

          <v-list-item-content>
            <v-list-item-title>Quantity</v-list-item-title>
            <v-list-item-subtitle>
              <v-btn
                x-small
                :disabled="item.orderQuantity === 1"
                @click="onDownQuantity(item.product.id)"
              >
                <v-icon>mdi-minus</v-icon>
              </v-btn>
              {{ item.orderQuantity }}
              <v-btn x-small @click="onUpQuantity(item.product.id)">
                <v-icon>mdi-plus</v-icon>
              </v-btn>
            </v-list-item-subtitle>
          </v-list-item-content>
          <v-list-item-action>
            <v-btn icon @click="onRemoveCartItem(item)">
              <v-icon color="red">mdi-cart-remove</v-icon>
            </v-btn>
          </v-list-item-action>
        </v-list-item>
      </template>
      <br />
      Total Price : {{ onTotalPrice }} won
      <br />
    </v-list>
    <br />
    <v-btn
      color="orange"
      text
      class="mr-12"
      :disabled="cartItems.length === 0"
      @click="onOrder()"
      >Order</v-btn
    >
    <v-btn color="orange" text @click="onBack">Back</v-btn>
  </div>
</template>

<script>
import { mapState } from 'vuex';

export default {
  data() {
    return {
      noCartProduct: 'Empty Cart, Go shopping',
      total: 0,
      overlay: true,
    };
  },
  computed: {
    ...mapState({
      cartItems: (state) => state.cart.cartItems,
      inCarted: (state) => state.cart.inCarted,
      userInfo: (state) => state.user.userInfo,
    }),
    onTotalPrice() {
      let totalPrice = 0;

      for (let i = 0; i < this.cartItems.length; i++) {
        totalPrice +=
          this.cartItems[i].product.price * this.cartItems[i].orderQuantity;
      }

      // eslint-disable-next-line vue/no-side-effects-in-computed-properties
      this.total = totalPrice;
      return totalPrice;
    },
  },
  created() {
    this.$store.dispatch('page/getCurrentPage', this.$nuxt.$route.name);

    // 회원 카트 겟
    if (this.userInfo) {
      this.$store.dispatch('cart/getCartData', this.userInfo.id);
    }

    this.$store.dispatch('cart/checkCarted');
  },
  methods: {
    onDownQuantity(id) {
      this.$store.dispatch('cart/downOrderQuantity', id);
    },
    onUpQuantity(id) {
      this.$store.dispatch('cart/upOrderQuantity', id);
    },
    onOrder() {
      this.$store.dispatch('order/updateTotalPrice', this.total);
      this.$store.dispatch('order/pushOrderData', this.cartItems);
      this.$router.push('/order');
    },
    onRemoveCartItem(item) {
      // 비회원
      if (!this.userInfo) {
        this.$store.dispatch('cart/popCartData', item.product.id);
      }
      // 회원
      else {
        this.$store.dispatch('cart/deleteCartData', item.id);
      }
    },
    onBack() {
      this.$router.go(-1);
    },
  },
};
</script>
<style scoped></style>
