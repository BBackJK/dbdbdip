export const state = () => ({
  cartItems: [],
  inCarted: false,
  deleted: false,
  message: '',
});

export const mutations = {
  /** 비회원 카트 **/
  PUSH_CART(state, data) {
    state.cartItems.push(data);
    state.inCarted = true;
  },
  POP_CART(state, data) {
    const idx = state.cartItems.findIndex((item) => {
      return item.product.id === data;
    });
    if (idx > -1) state.cartItems.splice(idx, 1);
  },
  CHECK_CARTED(state) {
    state.inCarted = false;
  },

  /** 테스트 mutation */
  UP_QUANTITY(state, data) {
    const idx = state.cartItems.findIndex((item) => {
      return item.product.id === data;
    });
    if (idx > -1) state.cartItems[idx].orderQuantity++;
  },
  DOWN_QUANTITY(state, data) {
    const idx = state.cartItems.findIndex((item) => {
      return item.product.id === data;
    });
    if (idx > -1) state.cartItems[idx].orderQuantity--;
  },

  /** 회원 카트 */
  /** cart post */
  CREATE_CART_DATA_SUCCESS(state) {
    state.inCarted = true;
    state.message = '';
  },
  CREATE_CART_DATA_FAILURE(state, data) {
    state.inCarted = false;
    state.message = data;
  },
  GET_CART_DATA_SUCCESS(state, data) {
    state.cartItems = null;
    state.cartItems = data;
  },
  GET_CART_DATA_FAILURE(state) {
    state.cartItems = [];
  },
  DELETE_CART_DATA_SUCCESS(state, data) {
    state.deleted = true;
    const idx = state.cartItems.findIndex((item) => {
      return item.id === data;
    });
    if (idx > -1) state.cartItems.splice(idx, 1);
  },
  DELETE_CART_DATA_FAILURE(state) {
    state.deleted = false;
  },
  MODIFY_CREATED_FLAG(state) {
    state.inCarted = !state.inCarted;
    state.message = '';
  },
};

export const actions = {
  /** 비회원 actions */
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

  /** 회원 actions */
  createCartData({ commit }, data) {
    this.$axios
      .post('/cart/register', data)
      .then((res) => {
        console.log(res);

        if (res.data.code === 409) {
          commit('CREATE_CART_DATA_FAILURE', 'Conflict');
          return;
        }

        commit('CREATE_CART_DATA_SUCCESS');
      })
      .catch((err) => {
        console.log(err);
        commit('CREATE_CART_DATA_FAILURE', '');
      });
  },
  getCartData({ commit }, data) {
    this.$axios
      .get('/cart', {
        params: {
          id: data,
        },
      })
      .then((res) => {
        console.log(res);
        commit('GET_CART_DATA_SUCCESS', res.data.data.carts);
      })
      .catch((err) => {
        console.log(err);
        commit('GET_CART_DATA_FAILURE');
      });
  },
  deleteCartData({ commit }, data) {
    this.$axios
      .delete('/cart/remove', {
        params: {
          id: data,
        },
      })
      .then((res) => {
        console.log(res);
        commit('DELETE_CART_DATA_SUCCESS', data);
      })
      .catch((err) => {
        console.log(err);
        commit('DELETE_CART_DATA_FAILURE');
      });
  },
  upOrderQuantity({ commit }, data) {
    commit('UP_QUANTITY', data);
  },
  downOrderQuantity({ commit }, data) {
    commit('DOWN_QUANTITY', data);
  },
  modifyCreatedFlag({ commit }) {
    commit('MODIFY_CREATED_FLAG');
  },
};
