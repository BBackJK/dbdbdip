<template>
  <div class="container">
    <form>
      <v-text-field
        v-model="name"
        :error-messages="nameErrors"
        label="Name"
        required
        @input="$v.name.$touch()"
        @blur="$v.name.$touch()"
      ></v-text-field>
      <v-text-field
        ref="email"
        v-model="email"
        :error-messages="emailErrors"
        label="E-mail"
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
      <v-text-field
        v-model="passwordCheck"
        :error-messages="passwordCheckErrors"
        :append-icon="show ? 'mdi-eye' : 'mdi-eye-off'"
        :type="show ? 'text' : 'password'"
        label="Repeat Password"
        required
        @input="$v.passwordCheck.$touch()"
        @blur="$v.passwordCheck.$touch()"
        @click:append="show = !show"
      ></v-text-field>
      <v-text-field
        v-model="phone"
        :error-messages="phoneErrors"
        label="Phone (not hypen -)"
        required
        @input="$v.phone.$touch()"
        @blur="$v.phone.$touch()"
      ></v-text-field>
      <v-text-field
        v-model="zipcode"
        :error-messages="zipcodeErrors"
        label="Zipcode"
        required
        @input="$v.zipcode.$touch()"
        @blur="$v.zipcode.$touch()"
      ></v-text-field>
      <v-text-field
        v-model="address"
        :error-messages="addressErrors"
        label="Address"
        required
        @input="$v.address.$touch()"
        @blur="$v.address.$touch()"
      ></v-text-field>
      <br />
      <v-btn class="mr-4" color="primary" @click="signupSubmit">SIGN UP</v-btn>
      <v-btn @click="signupClear">CLEAR</v-btn>

      <Snackbar v-if="snackbar" :title="title" :snackbar-flag="snackbar" />

      <Dialog
        v-if="dialog"
        :dialog-flag="dialog"
        headline="Sign Up Success!"
        link-title="Go Sign In!"
        link-push="/signin"
      />
    </form>
  </div>
</template>

<script>
import { validationMixin } from 'vuelidate';
import { required, minLength, email, sameAs } from 'vuelidate/lib/validators';
import { mapState } from 'vuex';

import Snackbar from '@/components/Snackbar.vue';
import Dialog from '@/components/Dialog.vue';

export default {
  components: {
    Snackbar,
    Dialog,
  },
  mixins: [validationMixin],

  validations: {
    password: {
      required,
      minLength: minLength(6),
      valid: (value) => {
        const containsHangul = /[ㄱ-ㅎ|ㅏ-ㅣ|가-힣]/.test(value);
        const containsAlphabat = /[a-z]/.test(value);
        const containsNumber = /[0-9]/.test(value);
        const containsSpecial = /[!@#$%^&*()]/.test(value);

        return (
          (containsHangul || containsAlphabat) &&
          containsNumber &&
          containsSpecial
        );
      },
    },
    passwordCheck: {
      required,
      sameAsPassword: sameAs('password'),
    },
    email: { required, email },
    name: { required },
    zipcode: { required },
    address: { required },
    phone: {
      required,
      valid: (v) => {
        const phoneValid = /010[0-9]/.test(v);

        return phoneValid;
      },
    },
  },
  data() {
    return {
      show: false,
      name: 'pjk',
      email: 'test@example.com',
      password: 'pjkrud6839@@',
      passwordCheck: 'pjkrud6839@@',
      phone: '01039096839',
      zipcode: '03370',
      address: 'adasa',
      snackbar: false,
      dialog: false,
      title: '',
    };
  },

  computed: {
    ...mapState({
      signedUp: (state) => state.user.signedUp,
      message: (state) => state.user.message,
    }),
    passwordCheckErrors() {
      const errors = [];

      if (!this.$v.passwordCheck.$dirty) return errors;

      !this.$v.passwordCheck.sameAsPassword &&
        errors.push('Must same password!');

      !this.$v.passwordCheck.required &&
        errors.push('Password Check is required.');

      return errors;
    },

    passwordErrors() {
      const errors = [];

      if (!this.$v.password.$dirty) return errors;

      !this.$v.password.valid &&
        errors.push(
          'Password contains alpabat and number and special characters!'
        );

      !this.$v.password.minLength &&
        errors.push('Password is 6 characters at least.');

      !this.$v.password.required && errors.push('Name is required.');

      return errors;
    },
    emailErrors() {
      const errors = [];

      if (!this.$v.email.$dirty) return errors;

      !this.$v.email.email && errors.push('Must be valid e-mail');

      !this.$v.email.required && errors.push('E-mail is required');

      return errors;
    },
    nameErrors() {
      const errors = [];

      if (!this.$v.name.$dirty) return errors;

      !this.$v.name.required && errors.push('Name is required');

      return errors;
    },
    zipcodeErrors() {
      const errors = [];

      if (!this.$v.zipcode.$dirty) return errors;

      !this.$v.zipcode.required && errors.push('zipcode is required');

      return errors;
    },
    addressErrors() {
      const errors = [];

      if (!this.$v.address.$dirty) return errors;

      !this.$v.address.required && errors.push('address is required');

      return errors;
    },
    phoneErrors() {
      const errors = [];

      if (!this.$v.phone.$dirty) return errors;

      !this.$v.phone.valid && errors.push("phone is starting as '010'");

      !this.$v.phone.required && errors.push('phone is required');

      return errors;
    },
  },

  methods: {
    async signupSubmit() {
      this.$v.$touch();
      this.snackbar = false;
      this.dialog = false;

      const postData = {
        name: this.name,
        password: this.password,
        email: this.email,
        phone: this.phone,
        zipcode: this.zipcode,
        address: this.address,
      };

      await this.$store.dispatch('user/signup', postData);

      if (this.signedUp) {
        this.dialog = true;
        this.snackbar = false;
      } else if (this.message === 'Conflict') {
        this.$refs.email.focus();
        this.snackbar = true;
        this.dialog = false;
        this.title = 'Already Exist Email !!';
      } else {
        this.snackbar = true;
        this.dialog = false;
        this.title = 'Unknown Error! Repeat Sign Up!';
      }
    },
    signupClear() {
      this.$v.$reset();
      this.name = '';
      this.passwordCheck = '';
      this.password = '';
      this.email = '';
      this.phone = '';
      this.zipcode = '';
      this.address = '';
    },

    onSuccessSignUp() {
      this.dialog = false;
      this.snackbar = false;
      this.$router.push('/signin');
      this.$v.$reset();
      this.name = '';
      this.passwordCheck = '';
      this.password = '';
      this.email = '';
      this.phone = '';
      this.zipcode = '';
      this.address = '';
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
</style>
