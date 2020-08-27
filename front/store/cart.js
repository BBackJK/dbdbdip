export const state = () => ({
  cartItems: [],
  inCarted: false,
});

export const mutations = {
  PUSH_CART(state, data) {
    state.cartItems.push(data);
    state.inCarted = true;
  },
  // UP_ORDER_QUANTITY(state, data) {
  //   for(let i = 0 ; i < state.cartItems.length ; i++) {
  //     if (state.cartItems[i].id === data) {
  //       console.log(state.cartItems[i].orderQuantity);
  //       state.cartItems[i].orderQuantity++;
  //       console.log(state.cartItems[i].orderQuantity);
  //       i = state.cartItems.length + 1;
  //     }
  //   }
  // }
};

export const actions = {
  pushCartData({ commit }, data) {
    if (!data.orderQuantity) data.orderQuantity = 1;
    commit('PUSH_CART', data);
  },
  // upOrderQuantity({ commit }, data) {
  //   commit('UP_ORDER_QUANTITY', data);
  // }
}