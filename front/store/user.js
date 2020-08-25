export const state = () => ({
  message: null,
  signedUp: false,
  userTokenInfo: null,
  gettingUser: false,
});

export const mutations = {
  SIGNUP_SUCCESS(state, data) {
    state.signedUp = true;
    state.message = data.message;
  },
  SIGNUP_FAILURE(state, data) {
    state.signedUp = false;
    state.message = data.message;
  },
  GET_USER_INFO_SUCCESS(state, data) {
    state.userTokenInfo = data.userInfo;
    state.message = data.message;
    state.gettingUser = true;
  },
  GET_USER_INFO_FAILURE(state, data) {
    state.userTokenInfo = null;
    state.message = data.message;
    state.gettingUser = false;
  },
};

export const actions = {
  async signup({ commit }, registerData) {
    await this.$axios
      .post('/user/register', registerData)
      .then((res) => {
        console.log(res);
        const data = {};

        if (res.data.code === 409) {
          data.message = 'Conflict';
          commit('SIGNUP_FAILURE', data);
          return;
        }

        data.message = 'success';
        commit('SIGNUP_SUCCESS', data);
      })
      .catch((err) => {
        const data = {};

        data.message = err.message;
        commit('SIGNUP_FAILURE', data);
      });
  },
  getUserInfo({ commit }, paramData) {
    return this.$axios
      .get('/user/info', {
        params: {
          email: paramData,
        },
      })
      .then((res) => {
        const data = {};

        data.message = 'success';
        data.userInfo = res.data.data.user;

        commit('GET_USER_INFO_SUCCESS', data);

        return res.data.data.user;
      })
      .catch((err) => {
        const data = {};

        data.message = err.message;
        commit('GET_USER_INFO_FAILURE', data);
      });
  },
};
