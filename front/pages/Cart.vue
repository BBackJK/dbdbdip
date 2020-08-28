<template>
  <div>
    <v-list three-line>
      <template>
        <v-subheader>Cart List</v-subheader>

        <v-list-item v-if="cartItems.length === 0">
          {{ noCartProduct }}
        </v-list-item>

        <v-list-item v-for="(item, index) in cartItems" :key="item.id" v-else>
          <template v-if="userInfo">
          <v-list-item-avatar>
            <v-img :src="item.product.imagePath"></v-img>
          </v-list-item-avatar>

          <v-list-item-content>
            <v-list-item-title v-html="item.product.name"></v-list-item-title>
            <v-list-item-subtitle> {{ item.product.price }} won</v-list-item-subtitle>
          </v-list-item-content>
          </template>

          <template v-else>
          <v-list-item-avatar>
            <v-img :src="item.imagePath"></v-img>
          </v-list-item-avatar>

          <v-list-item-content>
            <v-list-item-title v-html="item.name"></v-list-item-title>
            <v-list-item-subtitle> {{ item.price }} won</v-list-item-subtitle>
          </v-list-item-content>
          </template>

          <v-list-item-content>
            <v-list-item-title>
              Quantity
            </v-list-item-title>
            <v-list-item-subtitle v-if="cartItems.length !== 0">
              <!-- <v-btn
                x-small
                :disabled="orderInfo[index].orderQuantity === 1"
                @click="onDownQuantity(item.id)"
                ><v-icon>mdi-minus</v-icon></v-btn
              >
              {{ orderInfo[index].orderQuantity }} -->
              <v-btn
                x-small
                :disabled="getQuantity.orderQuantity === 1"
                @click="onDownQuantity(item.id)"
                ><v-icon>mdi-minus</v-icon></v-btn
              >
              {{ getQuantity[index].orderQuantity }}
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
  async created() {
    this.$store.dispatch('page/getCurrentPage', this.$nuxt.$route.name);

    // 회원 카트 겟
    if (this.userInfo) {
      await this.$store.dispatch('cart/checkCarted');
      await this.$store.dispatch('cart/getCartData', this.userInfo.id);
      return;
    }

    // 비회원 카트 겟
    for (let i = 0; i < this.cartItems.length; i++) {
      const data = {
        id: this.cartItems[i].id,
        orderQuantity: this.cartItems[i].orderQuantity,
        price: this.cartItems[i].price,
      };
      this.orderInfo.push(data);
    }
    this.$store.dispatch('cart/checkCarted');
  },
  computed: {
    ...mapState({
      cartItems: (state) => state.cart.cartItems,
      inCarted: (state) => state.cart.inCarted,
      userInfo: (state) => state.user.userInfo,
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
    getQuantity() {
      if (this.userInfo && this.cartItems.length !== 0){
        for (let i = 0; i < this.cartItems.length; i++) {
          const data = {
            id : this.cartItems[i].product.id,
            orderQuantity: this.cartItems[i].orderQuantity,
            price: this.cartItems[i].product.price
          }
          this.orderInfo.push(data);
        }
        return this.orderInfo;
      }
    }
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

      if (!this.userInfo) {
        for (let i = 0; i < this.cartItems.length; i++) {
          for (let j = 0; j < this.cartItems.length; j++) {
            if (this.orderInfo[i].id === this.cartItems[j].id) {
              this.cartItems[j].orderQuantity = this.orderInfo[i].orderQuantity;
            }
          }
        }
      } else {
        for (let i = 0; i < this.cartItems.length; i++) {
          for (let j = 0; j < this.cartItems.length; j++) {
            if (this.orderInfo[i].id === this.cartItems[j].product.id) {
              this.cartItems[j].product.orderQuantity = this.orderInfo[i].orderQuantity;
            }
          }
        }
      }
      this.$store.dispatch('order/updateTotalPrice', this.total);
      this.$store.dispatch('order/pushOrderData', this.cartItems);
      console.log(this.cartItems);
      this.$router.push('/order');
    },
    onRemoveCartItem(item) {
      console.log(item);
      // console.log(this.cartItems[0]);
      // console.log(item === this.cartItems[0]);

      // 비회원
      if (!this.userInfo) {
        this.$store.dispatch('cart/popCartData', item);
      }
    },
  },
};
</script>
<style scoped></style>
