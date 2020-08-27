export const state = () => ({
  currentPageName: '',
});

export const mutations = {
  GET_CURRENT_PAGE_NAME(state, data) {
    state.currentPageName = data;
  }
}

export const actions = {
  getCurrentPage({ commit }, data) {
    commit('GET_CURRENT_PAGE_NAME', data);
  }
}