<template>
  <v-container>
    <v-row>
      <v-row>
        <v-col>
          <v-row>
            <v-toolbar>
              <v-toolbar-title>Search by Order Number</v-toolbar-title>
              <v-spacer></v-spacer>
              <v-toolbar-items>
                <v-text-field
                  v-model="searchNumber"
                  placeholder="Search Order Number..."
                  class="text-field"
                ></v-text-field>
                <v-btn icon @click="onSearchNumber">
                  <v-icon>mdi-magnify</v-icon>
                </v-btn>
              </v-toolbar-items>
            </v-toolbar>
          </v-row>
          <v-row v-if="userInfo" align="center">
            <v-date-picker
              v-model="date"
              full-width
              class="mt-4"
            ></v-date-picker>
          </v-row>
          <v-row>
            <v-spacer></v-spacer>
            <v-btn
              v-if="userInfo"
              x-large
              color="orange"
              text
              @click="onSearchDate"
              >Date Search</v-btn
            >
          </v-row>
        </v-col>
        <v-col>
          <v-card max-width="800" class="mx-auto">
            <v-card-subtitle>Buying List</v-card-subtitle>
            <v-list three-line>
              <template>
                <template v-if="orderedItems.length !== 0">
                  <v-list-item
                    v-for="item in orderedItems"
                    :key="item.id"
                    @click="test(item)"
                  >
                    <v-subheader>{{
                      item.createdAt.substr(0, 10)
                    }}</v-subheader>
                    <v-list-item-avatar>
                      <v-img :src="item.product.imagePath"></v-img>
                    </v-list-item-avatar>

                    <v-list-item-content>
                      <v-list-item-title>
                        {{ item.product.name }}
                      </v-list-item-title>
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
                      <v-list-item-title>Sub Total Price</v-list-item-title>
                      <v-list-item-subtitle>
                        {{ item.orderQuantity * item.product.price * 1 }}
                        won
                      </v-list-item-subtitle>
                    </v-list-item-content>

                    <v-list-item-content>
                      <v-list-item-title>Status</v-list-item-title>
                      <v-list-item-subtitle>
                        {{ item.status }}
                      </v-list-item-subtitle>
                    </v-list-item-content>
                  </v-list-item>
                </template>
                <template v-else>No Data.</template>
              </template>
            </v-list>
          </v-card>
        </v-col>
      </v-row>
    </v-row>
  </v-container>
</template>

<script>
import { mapState } from 'vuex';

export default {
  data: () => ({
    title: 'Buying List',
    date: '',
    searchNumber: '',
  }),
  computed: {
    ...mapState({
      userInfo: (state) => state.user.userInfo,
      orderedItems: (state) => state.order.orderedItems,
      getted: (state) => state.order.getted,
    }),
  },
  mounted() {
    this.date = new Date().toISOString().substr(0, 10);
    if (this.userInfo) {
      this.$store.dispatch('order/getOrderById', this.userInfo.id);
    }
  },
  destroyed() {
    this.$store.dispatch('order/resetBuyingList');
    this.$store.dispatch('order/resetGetted');
  },
  methods: {
    onSearchNumber() {
      this.$store.dispatch('order/resetGetted');
      console.log(this.searchNumber);
      this.$store.dispatch('order/getOrderByNumber', this.searchNumber);
    },
    onSearchDate() {
      this.$store.dispatch('order/resetGetted');
      const getData = {
        date: this.date,
        id: this.userInfo.id,
      };
      this.$store.dispatch('order/getOrderByDate', getData);
    },
    test(item) {
      console.log(item);
      this.$router.push(`/product/${item.product.category}/${item.product.id}`);
    },
  },
};
</script>
<style scoped>
.text-field {
  margin-top: 10px;
}
.hr-test {
  color: red;
}
</style>
