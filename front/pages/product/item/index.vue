<template>
  <v-container fluid>
    <v-row dense>
      <v-col v-for="item in items" :key="item.id" cols="3">
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
              <v-col v-if="userInfo" cols="3">
                <v-btn color="orange" text @click="onUserCart(item)"
                  >Cart</v-btn
                >
              </v-col>
              <v-col v-else cols="3">
                <v-btn color="orange" text @click="onCart(item)">Cart</v-btn>
              </v-col>
            </v-row>
          </v-card-actions>
        </v-card>
        <Snackbar v-if="snackbar" :title="title" :snackbar-flag="snackbar" />
      </v-col>
    </v-row>
    <v-row dense>
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

export default {
  data() {
    return {
      snackbar: false,
      title: '',
    };
  },
  computed: {
    ...mapState({
      items: (state) => state.product.items,
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
    if (!this.items) {
      this.$store.dispatch(
        'product/getItemProducts',
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
      this.$router.push(`/product/item/${item.id}`);
    },
    onAddProduct() {
      this.$router.push('/admin/add/item');
    },
  },
};
</script>
<style scoped></style>
