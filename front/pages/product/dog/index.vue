<template>
  <v-container fluid>
    <v-row dense>
      <v-col v-for="item in dogItems" :key="item.id" cols="3">
        <v-card class="mx-auto" max-width="400">
          <div @click="onProductDetail(item)">
            <v-img
              class="white--text align-end"
              height="200px"
              :src="item.image"
            ></v-img>

            <v-card-title class="pb-0">{{ item.name }}</v-card-title>
            <v-card-text class="text--primary">
              <div class="item-price">{{ item.price }} won</div>

              <br />
            </v-card-text>
          </div>
          <v-card-actions>
            <v-row no-gutters>
              <v-col cols="3">
                <v-btn color="orange" text @click="onCart(item)">Cart</v-btn>
              </v-col>
            </v-row>
          </v-card-actions>
        </v-card>
        <Snackbar v-if="snackbar" :title="title" :snackbar-flag="snackbar" />
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import { mapState } from 'vuex';

import Snackbar from '@/components/Snackbar.vue';

export default {
  data() {
    return {
      snackbar: false,
      title: '',
    };
  },
  created() {
    this.$store.dispatch('page/getCurrentPage', this.$nuxt.$route.name);
  },
  components: {
    Snackbar,
  },
  computed: {
    ...mapState({
      dogItems: (state) => state.product.dogItems,
      cartItems: (state) => state.cart.cartItems,
    }),
  },
  methods: {
    onCart(item) {
      console.log('onCart', item);
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
      this.$store.dispatch('cart/pushCartData', item);
    },
    onProductDetail(item) {
      console.log(item);
      this.$store.dispatch('product/pushSelectData', item);
      this.$router.push(`/product/dog/${item.id}`);
    },
  },
};
</script>
<style scoped>
.item-price {
  text-align: right;
}
.item-description {
  text-align: left;
}
.link-set {
  text-decoration: none;
}
.right-btn {
  display: inline-block;
}

.button-area {
  /* margin: 0;
  padding: 0; */
  /* display: -webkit-box;
  display: -moz-box;
  display: -ms-flexbox;
  display: -moz-flex;
  display: -webkit-flex; */
  display: flex;
  justify-content: space-between;
}
.button-item {
  /* display: inline-block; */
  text-align: center;
  float: right;
}
</style>
