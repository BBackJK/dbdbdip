<template>
  <v-container fluid>
    <v-row dense>
      <v-col v-for="item in dogItems" :key="item.id" cols="3">
        <v-card class="mx-auto" max-width="400">
          <div @click="onProductDetail(item)">
            <v-img
              class="white--text align-end"
              height="200px"
              :src="item.imagePath"
            ></v-img>

            <v-card-title class="pb-0">{{ item.name }}</v-card-title>
            <v-card-text class="text--primary">
              <div class="item-price">{{ item.price }} won</div>

              <br />
            </v-card-text>
          </div>
          <v-card-actions>
            <v-row no-gutters>
              <v-col cols="3" v-if="userInfo">
                <v-btn color="orange" text @click="onUserCart(item)">Cart</v-btn>
              </v-col>
              <v-col cols="3" v-else>
                <v-btn color="orange" text @click="onCart(item)">Cart</v-btn>
              </v-col>
            </v-row>
          </v-card-actions>
        </v-card>
        <Snackbar v-if="snackbar" :title="title" :snackbar-flag="snackbar" />
      </v-col>
    </v-row>
    <v-row>
      <v-btn
        v-if="admin"
        color="red"
        fab
        large
        dark
        bottom
        right
        @click="onAddProduct"
      >
        <v-icon>mdi-plus</v-icon>
      </v-btn>
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
  mounted() {
    if (!this.dogItems) {
      this.$store.dispatch('product/getDogProducts', 'dog');
    }
  },
  components: {
    Snackbar,
  },
  computed: {
    ...mapState({
      dogItems: (state) => state.product.dogItems,
      admin: (state) => state.user.admin,
      userInfo: (state) => state.user.userInfo,
      inCarted: (state) => state.cart.inCarted,
      cartItems: (state) => state.cart.cartItems,
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
    onProductDetail(item) {
      console.log(item);
      this.$router.push(`/product/dog/${item.id}`);
    },
    onAddProduct() {
      this.$router.push('/admin/add/item');
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
