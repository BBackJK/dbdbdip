export const state = () => ({
  cartItems: [],
  inCarted: false,
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
      return item.id === data.id;
    });
    if (idx > -1) state.cartItems.splice(idx, 1);
  },
  CHECK_CARTED(state) {
    state.inCarted = false;
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
  MODIFY_CREATED_FLAG(state) {
    state.inCarted = !state.inCarted;
    state.message = '';
  }
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
    this.$axios.post('/cart/register', data)
    .then(res => {
      console.log(res);

      if (res.data.code === 409) {
        commit('CREATE_CART_DATA_FAILURE', 'Conflict');
      }

      commit('CREATE_CART_DATA_SUCCESS');
    })
    .catch(err => {
      console.log(err);
      commit('CREATE_CART_DATA_FAILURE', '');
    })
  },
  getCartData({ commit }, data) {
    this.$axios.get('/cart', {
      params: {
        id: data
      }
    })
    .then(res => {
      console.log(res);
      commit('GET_CART_DATA_SUCCESS', res.data.data.carts);
    })
    .catch(err => {
      console.log(err);
      commit('GET_CART_DATA_FAILURE');
    })
  },
  modifyCreatedFlag({ commit }) {
    commit('MODIFY_CREATED_FLAG');
  }
};
