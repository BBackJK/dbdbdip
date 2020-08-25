export const state = () => ({
  userInfo: null,
  message: null,
  loggedIn: false,
});

export const mutations = {
  LOGIN_SUCCESS(state, data) {
    state.message = data.message;
    state.userInfo = data.userInfo;
    state.loggedIn = true;
  },
  LOGIN_FAILURE(state, data) {
    state.loggedIn = false;
    state.message = data.message;
    state.userInfo = null;
  },
  LOGOUT_SUCCESS(state) {
    state.loggedIn = false;
    state.userInfo = null;
  },
};

export const actions = {
  async login({ commit }, loginData) {
    await this.$axios
      .post('/auth/login', loginData)
      .then((res) => {
        console.log(res);
        localStorage.accessToken = res.data.data.token;
        const data = {
          message: 'success',
          userInfo: res.data.data.user,
        };
        commit('LOGIN_SUCCESS', data);
      })
      .catch((err) => {
        console.log(err);
        const data = {};

        if (err.response.status === 401) {
          data.message = 'Unauthorized';
          commit('LOGIN_FAILURE', data);

          return;
        }

        data.message = 'Unknown Error';
        commit('LOGIN_FAILURE', data);
      });
  },
  logout({ commit }) {
    commit('LOGOUT_SUCCESS');
  },
};

export const getters = {
  getAccessToken(state) {
    return state.accessToken;
  },
  getUserInfo(state) {
    return state.userInfo;
  },
  getMessage(state) {
    return state.message;
  },
  getLoggedIn(state) {
    return state.loggedIn;
  },
};
