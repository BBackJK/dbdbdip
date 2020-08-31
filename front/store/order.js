export const state = () => ({
  orderItems: null,
  totalPrice: 0,
  ordered: false,
  orderNumber: null,
});

export const mutations = {
  PUSH_ORDER(state, data) {
    state.orderItems = data;
  },
  UPDATE_TOTAL_PRICE(state, data) {
    state.totalPrice = data;
  },
  POST_ORDER_DATA_SUCCESS(state) {
    state.ordered = true;
  },
  POST_ORDER_DATA_FAILURE(state) {
    state.ordered = false;
  },
  MODIFY_ORDER_FLAG(state) {
    state.ordered = false;
  },
  PUSH_ORDER_NUMBER(state, data) {
    state.orderNumber = data;
  },

  /** 비회원용 주문 */
  POST_ORDER_FOR_NO_MEMBER_SUCCESS(state) {
    state.ordered = true;
  },
  POST_ORDER_FOR_NO_MEMBER_FAILURE(state) {
    state.ordered = false;
  },
};

export const actions = {
  pushOrderData({ commit }, data) {
    commit('PUSH_ORDER', data);
  },
  updateTotalPrice({ commit }, data) {
    commit('UPDATE_TOTAL_PRICE', data);
  },
  postOrderData({ commit }, data) {
    this.$axios
      .post('/order/register', data)
      .then((res) => {
        console.log(res);

        commit('POST_ORDER_DATA_SUCCESS');
      })
      .catch((err) => {
        console.log(err);
        commit('POST_ORDER_DATA_FAILURE');
      });
  },
  postOrderForNoMember({ commit }, data) {
    this.$axios
      .post('/order/notuser/register', data)
      .then((res) => {
        console.log(res);
        commit('POST_ORDER_FOR_NO_MEMBER_SUCCESS');
      })
      .catch((err) => {
        console.log(err);
        commit('POST_ORDER_FOR_NO_MEMBER_FAILURE');
      });
  },
  pushOrderNumber({ commit }, data) {
    commit('PUSH_ORDER_NUMBER', data);
  },
  modifyOrderFlag({ commit }) {
    commit('MODIFY_ORDER_FLAG');
  },
};
