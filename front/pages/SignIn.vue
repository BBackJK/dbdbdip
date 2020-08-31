<template>
  <div class="container">
    <form>
      <v-text-field
        ref="email"
        v-model="email"
        label="E-mail"
        required
      ></v-text-field>
      <v-text-field
        v-model="password"
        :append-icon="show ? 'mdi-eye' : 'mdi-eye-off'"
        :type="show ? 'text' : 'password'"
        label="Password"
        required
        @click:append="show = !show"
      ></v-text-field>
      <br />
      <v-btn class="mr-4" color="primary" @click="loginSubmit">SIGN IN</v-btn>
      <v-btn @click="loginClear">CLEAR</v-btn>
      <br />
      <br />
      <nuxt-link to="signup" class="signup-link">
        <span class="signup-text">Not Family? Go Sign Up</span>
      </nuxt-link>

      <Snackbar v-if="snackbar" :title="title" :snackbar-flag="snackbar" />

      <Dialog
        v-if="dialog"
        :dialog-flag="dialog"
        headline="Sign In Success!"
        link-title="Go Home!"
        link-push="/"
      />
    </form>
  </div>
</template>

<script>
import { mapState } from 'vuex';

import Snackbar from '@/components/Snackbar.vue';
import Dialog from '@/components/Dialog.vue';

export default {
  components: {
    Snackbar,
    Dialog,
  },
  data: () => ({
    email: 'test1@example.com',
    password: 'pjkrud6839@@',
    show: false,
    snackbar: false,
    dialog: false,
    title: '',
  }),

  computed: {
    ...mapState({
      userInfo: (state) => state.user.userInfo,
      message: (state) => state.user.message,
      loggedIn: (state) => state.user.loggedIn,
    }),
  },

  methods: {
    async loginSubmit() {
      this.snackbar = false;
      this.dialog = false;
      const data = {
        email: this.email,
        password: this.password,
      };

      await this.$store.dispatch('user/login', data);

      if (this.loggedIn) {
        this.dialog = true;
        this.snackbar = false;
      } else if (this.message === 'Unauthorized') {
        this.snackbar = true;
        this.dialog = false;
        this.title = 'Checking Email and Password !';
      } else {
        this.snackbar = true;
        this.dialog = false;
        this.title = 'Unknown error! Repeat Sign In!';
      }
    },

    loginClear() {
      this.password = '';
      this.email = '';
    },
  },
};
</script>

<style>
.container {
  margin: 0 auto;
  min-height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
  text-align: center;
}

.signup-link {
  text-decoration: none;
}

.signup-text {
  color: red;
}
</style>
