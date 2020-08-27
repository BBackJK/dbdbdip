<template>
  <v-container fluid>
    <form>
      <v-text-field
        v-model="email"
        label="E-mail"
        required
        readonly
      ></v-text-field>
      <v-text-field v-model="name" label="Name" required></v-text-field>
      <v-text-field v-model="phone" label="Phone" required></v-text-field>
      <v-text-field v-model="zipcode" label="Zip-code" required></v-text-field>
      <v-text-field v-model="address" label="Address" required></v-text-field>
      <v-text-field v-model="updatedAt" label="Last Updated Date" readonly />
      <br />
      <v-btn
        class="mr-4"
        color="primary"
        :disabled="!updatableButton"
        @click="onSubmitInfoUpdate"
        >UPDATE</v-btn
      >
      <v-btn class="mr-4">
        <nuxt-link to="/mypage/password" class="link-set">
          <div class="text-set">
            PASSWORD UPDATE
          </div>
        </nuxt-link>
      </v-btn>
      <v-btn @click="onMyPageClear">CLEAR</v-btn>
      <Snackbar
        v-if="snackbar"
        title="Update Success!"
        :snackbar-flag="snackbar"
      />
    </form>
  </v-container>
</template>

<script>
import { mapState } from 'vuex';

import formatDate from '~/modules/format.js';

export default {
  data() {
    return {
      show: false,
      email: '',
      name: '',
      phone: '',
      zipcode: '',
      address: '',
      updatedAt: '',
      snackbar: false,
    };
  },
  mounted() {
    console.log(this.userInfo);

    this.email = this.userInfo.email;
    this.name = this.userInfo.name;
    this.phone = this.userInfo.phone;
    this.zipcode = this.userInfo.zipcode;
    this.address = this.userInfo.address;

    const date = !this.userInfo.updatedAt
      ? formatDate(this.userInfo.createdAt)
      : formatDate(this.userInfo.updatedAt);

    this.updatedAt = date;
  },
  computed: {
    ...mapState({
      userInfo: (state) => state.user.userInfo,
    }),
    updatableButton() {
      if (
        this.name !== this.userInfo.name ||
        this.phone !== this.userInfo.phone ||
        this.zipcode !== this.userInfo.zipcode ||
        this.address !== this.userInfo.address
      ) {
        return true;
      } else {
        return false;
      }
    },
  },
  methods: {
    onMyPageClear() {
      this.name = '';
      this.phone = '';
      this.zipcode = '';
      this.address = '';
    },
    onSubmitInfoUpdate() {
      const putData = {
        email: this.email,
        name: this.name,
        phone: this.phone,
        zipcode: this.zipcode,
        address: this.address,
      };

      this.$store.dispatch('user/updateUserInfo', putData);

      if (this.$store.state.user.updated) {
        this.snackbar = true;
        this.createdAt = formatDate(this.userInfo.updatedAt);
        const time = setInterval(() => {
          if (this.snackbar) {
            this.snackbar = false;
            clearInterval(time);
          }
        }, 5000);
      }
    },
  },
};
</script>

<style scoped>
.link-set {
  text-decoration: none;
}

.text-set {
  color: white;
}
</style>
