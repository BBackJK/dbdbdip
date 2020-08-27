export const state = () => ({
  orderItems: null,
  totalPrice: 0,
});

export const mutations = {
  PUSH_ORDER(state, data) {
    state.orderItems = data;
  },
  UPDATE_TOTAL_PRICE(state, data) {
    state.totalPrice = data;
  },
};

export const actions = {
  pushOrderData({ commit }, data) {
    commit('PUSH_ORDER', data);
  },
  updateTotalPrice({ commit }, data) {
    commit('UPDATE_TOTAL_PRICE', data);
  },
};
