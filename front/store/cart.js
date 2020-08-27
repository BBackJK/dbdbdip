export const state = () => ({
  cartItems: [],
  inCarted: false,
});

export const mutations = {
  PUSH_CART(state, data) {
    state.cartItems.push(data);
    state.inCarted = true;
  },
  POP_CART(state, data) {
    const idx = state.cartItems.findIndex((item) => {
      return item.id === data.id;
    });
    if (idx > -1) state.cartItems.splice(idx, 1);
  },
  CHECK_CARTED(state) {
    state.inCarted = false;
  },
};

export const actions = {
  pushCartData({ commit }, data) {
    if (!data.orderQuantity) data.orderQuantity = 1;
    commit('PUSH_CART', data);
  },
  popCartData({ commit }, data) {
    commit('POP_CART', data);
  },
  checkCarted({ commit }) {
    commit('CHECK_CARTED');
  },
};
