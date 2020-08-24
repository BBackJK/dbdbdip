<template>
    <div class="container">
      <form>
        <v-text-field
          v-model="email"
          :error-messages="emailErrors"
          label="E-mail"
          ref="email"
          required
          @input="$v.email.$touch()"
          @blur="$v.email.$touch()"
        ></v-text-field>
        <v-text-field
          v-model="password"
          :error-messages="passwordErrors"
          :append-icon="show ? 'mdi-eye' : 'mdi-eye-off'"
          :type="show ? 'text' : 'password'"
          label="Password"
          required
          @input="$v.password.$touch()"
          @blur="$v.password.$touch()"
          @click:append="show = !show"
        ></v-text-field>
        <br/>
        <v-btn class="mr-4" @click="loginSubmit">submit</v-btn>
        <v-btn @click="loginClear">clear</v-btn>
        <br/>
        <br/>
        <nuxt-link to='signup' class="signup-link"><span class="signup-text">Not Family? Go Sign Up</span></nuxt-link>

        <v-snackbar 
        v-model="snackbar" 
        :top="true">
      Checking Email and Password !

      <template v-slot:action="{ attrs }">
        <v-btn
          color="blue"
          text
          v-bind="attrs"
          @click="snackbar = false"
        >
          Close
        </v-btn>
      </template>
    </v-snackbar>

    <v-dialog
      v-model="dialog"
      max-width="290"
    >
      <v-card>
        <v-card-title class="headline">Success!</v-card-title>

        <v-card-actions>
          <v-spacer></v-spacer>

          <v-btn
            color="green darken-1"
            text
            @click="onSuccessSignIn"
          >
            Go Home!
          </v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
      </form>
    </div>
</template>

<script>
import axios from 'axios';
import { validationMixin } from 'vuelidate'
import { required, minLength, email } from 'vuelidate/lib/validators'
import  { mapState } from 'vuex'; 

export default {
  mixins: [validationMixin],

  validations: {
    password: { 
      required, 
      minLength: minLength(6),
      valid: (value) => {
        const containsHangul = /[ㄱ-ㅎ|ㅏ-ㅣ|가-힣]/.test(value)
        const containsAlphabat = /[a-z]/.test(value);
        const containsNumber = /[0-9]/.test(value);
        const containsSpecial = /[!@#$%^&*()]/.test(value);

        return (containsHangul || containsAlphabat) && containsNumber && containsSpecial;
      }
    },
    email: { required, email },
  },

  data: () => ({
    email: 'test8@example.com',
    password: 'pjkrud6839@@',
    show: false,
    snackbar: false,
    dialog: false,
  }),

  computed: {
    ...mapState({
      userInfo: state => state.auth.userInfo,
      accessToken: state => state.auth.accessToken,
      message: state => state.auth.message,
      loggedIn: state => state.auth.message,
    }),
    passwordErrors () {
      const errors = [];

      if (!this.$v.password.$dirty) return errors;

      !this.$v.password.valid && errors.push('Password contains alpabat and number and special characters!');

      !this.$v.password.minLength && errors.push('Password is 6 characters at least.');

      !this.$v.password.required && errors.push('Name is required.');

      return errors;
    },
    emailErrors () {
      const errors = [];

      if (!this.$v.email.$dirty) return errors;

      !this.$v.email.email && errors.push('Must be valid e-mail');

      !this.$v.email.required && errors.push('E-mail is required');

      return errors;
    },
  },

  methods: {
    async loginSubmit () {
      this.$v.$touch();
      const data = {
        email : this.email,
        password : this.password
      };

      await this.$store.dispatch('auth/login', data);

      this.message === "Unauthorized" ? this.snackbar = true : this.dialog = true;
    },

    loginClear () {
      this.$v.$reset();
      this.password = '';
      this.email = '';
    },

    onSuccessSignIn() {
      this.$v.$reset();
      this.password = '';
      this.email = '';
      this.$router.push('/');
    }
  },
}
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