export const state = () => ({
  userInfo: null,
  message: null,
  loggedIn: false,
  signedUp: false,
  updated: false,
  updatedPassword: false,
  admin: false,
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
  UPDATE_USER_INFO_SUCCESS(state, data) {
    state.updated = true;
    state.userInfo = data;
  },
  UPDATE_USER_INFO_FAILURE(state) {
    state.updated = false;
    state.userInfo = null;
  },
  UPDATE_USER_PASSWORD_SUCCESS(state, data) {
    state.updatedPassword = true;
    state.message = data.message;
  },
  UPDATE_USER_PASSWORD_FAILURE(state, data) {
    state.updatedPassword = false;
    state.message = data.message;
  },
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
  LOGIN_ADMIN_SUCCESS(state) {
    state.loggedIn = true;
    state.admin = true;
  },
  LOGIN_ADMIN_FAILURE(state) {
    state.loggedIn = false;
    state.admin = false;
  },
  LOGOUT_SUCCESS(state) {
    state.loggedIn = false;
    state.userInfo = null;
    state.admin = false;
  },
  GET_INFO_ME_SUCCESS(state, data) {
    state.loggedIn = true;
    state.userInfo = data.data.data.user;
  },
  GET_INFO_ME_FAILURE(state) {
    state.loggedIn = false;
    state.userInfo = null;
  },
  MODIFY_UPDATED_FLAG(state) {
    state.updated = false;
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
  async updateUserInfo({ commit }, putData) {
    await this.$axios
      .put('/user', putData)
      .then((res) => {
        console.log(res);
        commit('UPDATE_USER_INFO_SUCCESS', res.data.data.user);
      })
      .catch((err) => {
        console.log(err);
        commit('UPDATE_USER_INFO_FAILURE');
      });
  },
  async updateUserPassword({ commit }, putData) {
    await this.$axios
      .put('/user/password', putData)
      .then((res) => {
        console.log(res);

        const data = {};

        if (res.data.code === 409) {
          data.message = 'Password Confilct!';

          commit('UPDATE_USER_PASSWORD_FAILURE', data);

          return;
        }

        data.message = 'Updated Success!';

        commit('UPDATE_USER_PASSWORD_SUCCESS', data);
      })
      .catch((err) => {
        console.log(err);

        const data = {};

        data.message = err.response.message;

        commit('UPDATE_USER_PASSWORD_FAILURE', data);
      });
  },
  async login({ commit }, loginData) {
    await this.$axios
      .post('/auth/login', loginData)
      .then((res) => {
        console.log(res);
        if (res.data.data.user.email === 'admin') {
          commit('LOGIN_ADMIN_SUCCESS');
          return;
        }

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
  async getInfo({ commit }) {
    await this.$axios
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
  onModifyUpdatedFlag({ commit }) {
    commit('MODIFY_UPDATED_FLAG');
  },
  logout({ commit }) {
    localStorage.removeItem('accessToken');
    commit('LOGOUT_SUCCESS');
  },
};
