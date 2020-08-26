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
  GET_INFO_ME_SUCCESS(state, data) {
    state.loggedIn = true;
    state.userInfo = data.data.data.user;
  },
  GET_INFO_ME_FAILURE(state) {
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
    localStorage.clear();
    commit('LOGOUT_SUCCESS');
  },
  getInfo({ commit }) {
    this.$axios
      .get('/auth/me', {
        headers: {
          Authorization: `Bearer ${localStorage.accessToken}`,
        },
      })
      .then((res) => {
        commit('GET_INFO_ME_SUCCESS', res);
      })
      .catch((err) => {
        console.log(err);
        commit('GET_INFO_ME_FAILURE');
      });
  },
};
