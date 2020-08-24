<template>
  <v-layout column justify-center align-center>
    <v-flex xs12 sm8 md6>
      <div class="text-center">
      </div>
    </v-flex>
  </v-layout>
</template>

<script>
import { mapState } from 'vuex';

export default {
  // data() {
  //   return {
  //     userInfo: [],
  //   }
  // },
  // computed: {
  //   ...mapState({
  //     loggingEmail: state => state.auth.loggingEmail,
  //   })
  // },
  // created() {
  //   this.$store.dispatch('user/getUserInfo', this.loggingEmail)
  //     .then(res => {
  //       this.userInfo = res;
  //   })
  // },
  // mounted() {
  //   console.log()
  //   this.$emit('childs-event', this.userInfo);
  // }
}
</script>
