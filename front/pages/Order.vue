<template>
<v-list three-line>
      <template >
        <v-subheader>Cart List</v-subheader>

        <v-list-item
        v-for="(item) in orderItems"
          :key="item.id"
        >
          <v-list-item-avatar>
            <v-img :src="item.image"></v-img>
          </v-list-item-avatar>

          <v-list-item-content>
            <v-list-item-title v-html="item.name"></v-list-item-title>
            <v-list-item-subtitle> {{ item.price }}  won</v-list-item-subtitle>
          </v-list-item-content>
          <!-- <v-list-item-content>
            Quantity: 
            <v-list-item-subtitle>
            <v-btn x-small :disabled="item.orderQuantity === 1" @click="onDownQuantity(item.id)"><v-icon>mdi-minus</v-icon></v-btn>
          {{item.orderQuantity}}
      <v-btn x-small @click="onUpQuantity(item.id)"><v-icon>mdi-plus</v-icon></v-btn>
            </v-list-item-subtitle>
          </v-list-item-content> -->
          <v-list-item-content>
            Quantity: 
            <v-list-item-subtitle>
            {{ item.orderQuantity }}
            </v-list-item-subtitle>
          </v-list-item-content>
        </v-list-item>
      </template>
      <br />
      Total Price : {{totalPrice}} won
      <br />      
    </v-list>
</template>

<script>
import { mapState } from 'vuex';

export default {
  computed: {
    ...mapState({
      orderItems: (state) => state.order.orderItems,
      totalPrice: (state) => state.order.totalPrice
    })
  },
  mounted() {
    console.log(this.orderItems);
    console.log(this.totalPrice);
  }
}
</script>

<style>

</style>

