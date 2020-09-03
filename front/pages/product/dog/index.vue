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
            <br />
            <v-card-text>
              <div class="float-right">{{ item.price }} won</div>
            </v-card-text>
          </div>
          <br />
          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn v-if="userInfo" color="orange" text @click="onUserCart(item)"
              >Cart</v-btn
            >
            <v-btn v-else color="orange" text @click="onCart(item)">Cart</v-btn>
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
  components: {
    Snackbar,
  },
  data() {
    return {
      snackbar: false,
      title: '',
    };
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
  watch: {
    message(val) {
      this.snackbar = false;
      if (val === 'Success') {
        this.title = 'Complete Carting';
        this.snackbar = true;
      }
      if (val === 'Conflict') {
        this.title = 'Duplicated Items!';
        this.snackbar = true;
      }
    },
  },
  created() {
    this.$store.dispatch('page/getCurrentPage', this.$nuxt.$route.name);
  },
  mounted() {
    if (!this.dogItems) {
      this.$store.dispatch(
        'product/getDogProducts',
        this.$route.name.split('-')[1]
      );
    }
  },
  methods: {
    onUserCart(item) {
      this.$store.dispatch('cart/modifyCreatedFlag');

      const postData = {
        orderQuantity: 1,
        user_id: this.userInfo.id,
        product_id: item.id,
      };

      this.$store.dispatch('cart/createCartData', postData);
    },
    onCart(item) {
      this.snackbar = false;

      const data = {
        orderQuantity: 1,
        product: item,
      };

      const idx = this.cartItems.findIndex((i) => {
        return i.product.id === item.id;
      });

      if (idx > -1) {
        this.title = 'Duplicated Items!';
        this.snackbar = true;
        return;
      }

      this.$store.dispatch('cart/pushCartData', data);

      this.title = 'Complete Carting';
      this.snackbar = true;
    },
    onProductDetail(item) {
      this.$router.push(`/product/dog/${item.id}`);
    },
    onAddProduct() {
      this.$router.push('/admin/add/dog');
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
