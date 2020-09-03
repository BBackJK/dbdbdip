export const state = () => ({
  orderItems: null, // 결제 전 주문 데이터
  orderedItems: [], // 주문 완료된 데이터
  totalPrice: 0,
  ordered: false,
  orderNumber: null,
  getted: false,
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
  GET_ORDER_BY_ID_SUCCESS(state, data) {
    state.orderedItems = null;
    state.orderedItems = data;
    state.getted = true;
  },
  GET_ORDER_BY_ID_FAILURE(state) {
    state.orderedItems = [];
    state.getted = false;
  },
  GET_ORDER_BY_NUMBER_SUCCESS(state, data) {
    state.orderedItems = null;
    state.orderedItems = data;
    state.getted = true;
  },
  GET_ORDER_BY_NUMBER_FAILURE(state) {
    state.orderedItems = [];
    state.getted = false;
  },
  GET_ORDER_BY_DATE_SUCCESS(state, data) {
    state.orderedItems = null;
    state.orderedItems = data;
    state.getted = true;
  },
  GET_ORDER_BY_DATE_FAILURE(state) {
    state.orderedItems = [];
    state.getted = false;
  },
  MODIFY_ORDER_FLAG(state) {
    state.ordered = false;
  },
  PUSH_ORDER_NUMBER(state, data) {
    state.orderNumber = data;
  },
  RESET_BUYING_LIST(state) {
    state.orderedItems = [];
  },
  RESET_GETTED(state) {
    state.getted = false;
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
  getOrderById({ commit }, data) {
    this.$axios
      .get('/order', {
        params: {
          id: data,
        },
      })
      .then((res) => {
        console.log(res);
        commit('GET_ORDER_BY_ID_SUCCESS', res.data.data.orders);
      })
      .catch((err) => {
        console.log(err);
        commit('GET_ORDER_BY_ID_FAILURE');
      });
  },
  getOrderByNumber({ commit }, data) {
    this.$axios
      .get('/order/notuser', {
        params: {
          orderNumber: data,
        },
      })
      .then((res) => {
        console.log(res);
        commit('GET_ORDER_BY_NUMBER_SUCCESS', res.data.data.orders);
      })
      .catch((err) => {
        console.log(err);
        commit('GET_ORDER_BY_NUMBER_FAILURE');
      });
  },
  getOrderByDate({ commit }, data) {
    this.$axios
      .get('/order/date', {
        params: {
          date: data.date,
          userId: data.id,
        },
      })
      .then((res) => {
        console.log(res);
        commit('GET_ORDER_BY_DATE_SUCCESS', res.data.data.orders);
      })
      .catch((err) => {
        console.log(err);
        commit('GET_ORDER_BY_DATE_FAILURE');
      });
  },
  pushOrderNumber({ commit }, data) {
    commit('PUSH_ORDER_NUMBER', data);
  },
  modifyOrderFlag({ commit }) {
    commit('MODIFY_ORDER_FLAG');
  },
  resetBuyingList({ commit }) {
    commit('RESET_BUYING_LIST');
  },
  resetGetted({ commit }) {
    commit('RESET_GETTED');
  },
};
