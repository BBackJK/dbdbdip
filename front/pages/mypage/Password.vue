<template>
  <v-container fluid>
    <form>
      <v-text-field
        ref="oldPassword"
        v-model="oldPassword"
        label="Origin Password"
        :error-messages="passwordErrors"
        :append-icon="oldPasswordShow ? 'mdi-eye' : 'mdi-eye-off'"
        :type="oldPasswordShow ? 'text' : 'password'"
        required
        @input="$v.oldPassword.$touch()"
        @blur="$v.oldPassword.$touch()"
        @click:append="oldPasswordShow = !oldPasswordShow"
      ></v-text-field>
      <v-text-field
        v-model="newPassword"
        label="New Password"
        :error-messages="newPasswordErrors"
        :append-icon="newPasswordShow ? 'mdi-eye' : 'mdi-eye-off'"
        :type="newPasswordShow ? 'text' : 'password'"
        required
        @input="$v.newPassword.$touch()"
        @blur="$v.newPassword.$touch()"
        @click:append="newPasswordShow = !newPasswordShow"
      ></v-text-field>
      <v-text-field
        v-model="newPasswordCheck"
        label="New Password Check"
        :error-messages="passwordCheckErrors"
        :append-icon="newPasswordCheckShow ? 'mdi-eye' : 'mdi-eye-off'"
        :type="newPasswordCheckShow ? 'text' : 'password'"
        required
        @input="$v.newPasswordCheck.$touch()"
        @blur="$v.newPasswordCheck.$touch()"
        @click:append="newPasswordCheckShow = !newPasswordCheckShow"
      ></v-text-field>
      <br />
      <v-btn class="mr-4" color="primary" @click="onUpdatePassword"
        >UPDATE</v-btn
      >
      <v-btn>CLEAR</v-btn>

      <Snackbar v-if="snackbar" :title="title" :snackbar-flag="snackbar" />
      <Dialog
        v-if="dialog"
        :dialog-flag="dialog"
        :headline="title"
        link-title="Go My Page"
        link-push="/mypage"
      />
    </form>
  </v-container>
</template>

<script>
import { validationMixin } from 'vuelidate';
import { required, minLength, sameAs } from 'vuelidate/lib/validators';
import { mapState } from 'vuex';

export default {
  mixins: [validationMixin],

  validations: {
    oldPassword: {
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
    newPassword: {
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
    newPasswordCheck: {
      required,
      sameAsPassword: sameAs('newPassword'),
    },
  },
  data() {
    return {
      oldPasswordShow: false,
      newPasswordShow: false,
      newPasswordCheckShow: false,
      snackbar: false,
      dialog: false,
      oldPassword: '',
      newPassword: '',
      newPasswordCheck: '',
      title: '',
    };
  },
  computed: {
    ...mapState({
      userInfo: (state) => state.user.userInfo,
      updatedPassword: (state) => state.user.updatedPassword,
      message: (state) => state.user.message,
    }),
    passwordErrors() {
      const errors = [];

      if (!this.$v.oldPassword.$dirty) return errors;

      !this.$v.oldPassword.valid &&
        errors.push(
          'Password contains alpabat and number and special characters!'
        );

      !this.$v.oldPassword.minLength &&
        errors.push('Password is 6 characters at least.');

      !this.$v.oldPassword.required && errors.push('Name is required.');

      return errors;
    },
    newPasswordErrors() {
      const errors = [];

      if (!this.$v.newPassword.$dirty) return errors;

      !this.$v.newPassword.valid &&
        errors.push(
          'Password contains alpabat and number and special characters!'
        );

      !this.$v.newPassword.minLength &&
        errors.push('Password is 6 characters at least.');

      !this.$v.newPassword.required && errors.push('Name is required.');

      return errors;
    },
    passwordCheckErrors() {
      const errors = [];

      if (!this.$v.newPasswordCheck.$dirty) return errors;

      !this.$v.newPasswordCheck.sameAsPassword &&
        errors.push('Must same password!');

      !this.$v.newPasswordCheck.required &&
        errors.push('Password Check is required.');

      return errors;
    },
  },
  methods: {
    async onUpdatePassword() {
      this.snackbar = false;

      const putData = {
        email: this.userInfo.email,
        oldPassword: this.oldPassword,
        newPassword: this.newPassword,
      };

      await this.$store.dispatch('user/updateUserPassword', putData);

      if (this.updatedPassword && this.message === 'Updated Success!') {
        this.dialog = true;
        this.snackbar = false;
        this.title = this.message;
      } else {
        this.snackbar = true;
        this.dialog = false;
        this.title = this.message;
        this.$refs.oldPassword.focus();
      }
    },
  },
};
</script>

<style scoped></style>
