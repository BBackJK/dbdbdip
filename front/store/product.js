export const state = () => ({
  dogItems: null,
  catItems: null,
  items: null,
  selectItem: null,
  created: false
})

export const mutations = {
  GET_ITEM_PRODUCTS_SUCCESS(state, data) {
    state.items=data;
  },
  GET_ITEM_PRODUCTS_FAILURE(state) {
    state.items = null;
  },
  GET_DOG_PRODUCTS_SUCCESS(state, data) {
    state.dogItems=data;
  },
  GET_DOG_PRODUCTS_FAILURE(state) {
    state.dogitems = null;
  },
  GET_CAT_PRODUCTS_SUCCESS(state, data) {
    state.catItems=data;
  },
  GET_CAT_PRODUCTS_FAILURE(state) {
    state.catItems = null;
  },
  GET_PRODUCT_SELECT_SUCCESS(state, data) {
    state.selectItem = data;
  },
  GET_PRODUCT_SELECT_FAILURE(state) {
    state.selectItem = null; 
  },
  CREATE_PRODUCT_SUCCESS(state) {
    state.created = true;
  },
  CREATE_PRODUCT_FAILURE(state) {
    state.created = false;
  },
  MODIFY_CREATED_FLAG(state) {
    state.created = !state.created;
  }
}

export const actions = {
  getProductById({ commit }, data) {
    this.$axios.get('/product', {
      params: {
        id: data
      }
    })
    .then(res => {
      console.log(res);
      commit('GET_PRODUCT_SELECT_SUCCESS', res.data.data.product);
    })
    .catch(err => {
      console.log(err);
      commit('GET_PRODUCT_SELECT_FAILURE');
    })
  },
  getItemProducts({ commit }, data) {
    console.log(data);
    this.$axios.get('/product/all', {
      params: {
        category: data
      }
    })
    .then(res => {
      console.log(res);
      commit('GET_ITEM_PRODUCTS_SUCCESS', res.data.data.products);
    })
    .catch(err => {
      console.log(err);
      commit('GET_ITEM_PRODUCTS_FAILURE');
    })
  },
  getDogProducts({ commit }, data) {
    this.$axios.get('/product/all', {
      params: {
        category: data
      }
    })
    .then(res => {
      console.log(res);
      commit('GET_DOG_PRODUCTS_SUCCESS', res.data.data.products);
    })
    .catch(err => {
      console.log(err);
      commit('GET_DOG_PRODUCTS_FAILURE');
    })
  },
  getCatProducts({ commit }, data) {
    this.$axios.get('/product/all', {
      params: {
        category: data
      }
    })
    .then(res => {
      console.log(res);
      commit('GET_CAT_PRODUCTS_SUCCESS', res.data.data.products);
    })
    .catch(err => {
      console.log(err);
      commit('GET_CAT_PRODUCTS_FAILURE');
    })
  },
  createProduct({ commit }, data) {
    this.$axios.post('/product/register', data)
      .then(res => {
        console.log(res);
        commit('CREATE_PRODUCT_SUCCESS');
      })
      .catch(err => {
        console.log(err);
        commit('CREATE_PRODUCT_FAILURE');
      })
  },
  modifyCreatedFlag({ commit }) {
    commit('MODIFY_CREATED_FLAG');
  }
}