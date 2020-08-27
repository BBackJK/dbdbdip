<template>
  <div>
    <v-list three-line>
      <template>
        <v-subheader>Cart List</v-subheader>

        <v-list-item v-if="cartItems.length === 0">
          {{ noCartProduct }}
        </v-list-item>

        <v-list-item v-for="(item, index) in cartItems" :key="item.id" v-else>
          <v-list-item-avatar>
            <v-img :src="item.image"></v-img>
          </v-list-item-avatar>

          <v-list-item-content>
            <v-list-item-title v-html="item.name"></v-list-item-title>
            <v-list-item-subtitle> {{ item.price }} won</v-list-item-subtitle>
          </v-list-item-content>
          <v-list-item-content>
            <v-list-item-title>
              Quantity
            </v-list-item-title>
            <v-list-item-subtitle>
              <v-btn
                x-small
                :disabled="orderInfo[index].orderQuantity === 1"
                @click="onDownQuantity(item.id)"
                ><v-icon>mdi-minus</v-icon></v-btn
              >
              {{ orderInfo[index].orderQuantity }}
              <v-btn x-small @click="onUpQuantity(item.id)"
                ><v-icon>mdi-plus</v-icon></v-btn
              >
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
    >
      Order
    </v-btn>
    <v-btn color="orange" text @click="onBack">
      Back
    </v-btn>
  </div>
</template>

<script>
import { mapState } from 'vuex';

export default {
  data() {
    return {
      orderInfo: [],
      noCartProduct: 'Empty Cart, Go shopping',
      total: 0,
    };
  },
  created() {
    this.$store.dispatch('page/getCurrentPage', this.$nuxt.$route.name);
    this.$store.dispatch('cart/checkCarted');
    for (let i = 0; i < this.cartItems.length; i++) {
      const data = {
        id: this.cartItems[i].id,
        orderQuantity: this.cartItems[i].orderQuantity,
        price: this.cartItems[i].price,
      };
      this.orderInfo.push(data);
    }
  },
  computed: {
    ...mapState({
      cartItems: (state) => state.cart.cartItems,
    }),
    onTotalPrice() {
      let totalPrice = 0;
      for (let i = 0; i < this.orderInfo.length; i++) {
        totalPrice += this.orderInfo[i].price * this.orderInfo[i].orderQuantity;
      }
      // eslint-disable-next-line vue/no-side-effects-in-computed-properties
      this.total = totalPrice;
      return totalPrice;
    },
  },
  methods: {
    onDownQuantity(id) {
      for (let i = 0; i < this.cartItems.length; i++) {
        if (this.orderInfo[i].id === id) {
          this.orderInfo[i].orderQuantity--;
        }
      }
    },
    onUpQuantity(id) {
      for (let i = 0; i < this.cartItems.length; i++) {
        if (this.orderInfo[i].id === id) {
          this.orderInfo[i].orderQuantity++;
        }
      }
    },
    onBack() {
      this.$router.go(-1);
    },
    onOrder() {
      for (let i = 0; i < this.cartItems.length; i++) {
        for (let j = 0; j < this.cartItems.length; j++) {
          if (this.orderInfo[i].id === this.cartItems[j].id) {
            this.cartItems[j].orderQuantity = this.orderInfo[i].orderQuantity;
          }
        }
      }
      console.log('this.cartItems', typeof this.cartItems);
      this.$store.dispatch('order/updateTotalPrice', this.total);
      this.$store.dispatch('order/pushOrderData', this.cartItems);
      this.$router.push('/order');
    },
    onRemoveCartItem(item) {
      // console.log(item);
      // console.log(this.cartItems[0]);
      // console.log(item === this.cartItems[0]);
      this.$store.dispatch('cart/popCartData', item);
    },
  },
};
</script>
<style scoped></style>
