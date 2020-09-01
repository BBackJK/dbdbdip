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
          <v-row align="center">
            <v-date-picker
              v-model="date"
              full-width
              class="mt-4"
            ></v-date-picker>
          </v-row>
          <v-row>
            <v-spacer></v-spacer>
            <v-btn x-large color="orange" text @click="test">Date Search</v-btn>
          </v-row>
        </v-col>
        <v-col>
          <v-card max-width="450" class="mx-auto">
            <v-list three-line>
              <template>
                <v-subheader> {{ date }}</v-subheader>
                <v-list-item>
                  <!-- <v-list-item-avatar>
                    <v-img></v-img>
                  </v-list-item-avatar>

                  <v-list-item-content>
                    <v-list-item-title>test</v-list-item-title>
                    <v-list-item-subtitle> sub title </v-list-item-subtitle>
                  </v-list-item-content> -->
                  No Data.
                </v-list-item>
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
    date: new Date().toISOString().substr(0, 10),
    searchNumber: '',
  }),
  computed: {
    ...mapState({
      userInfo: (state) => state.user.userInfo,
    }),
  },
  mounted() {
    if (this.userInfo) {
      this.$store.dispatch('order/getOrderById', this.userInfo.id);
    }
  },
  methods: {
    onSearchNumber() {
      console.log(this.searchNumber);
      this.$store.dispatch('order/getOrderByNumber', this.searchNumber);
    },
    test() {
      console.log(this.date);
      const getData = {
        date: this.date,
        id: 2,
      };
      this.$store.dispatch('order/getOrderByDate', getData);
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
