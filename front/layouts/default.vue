<template>
  <v-app dark>
    <v-navigation-drawer
      v-model="drawer"
      :mini-variant="false"
      :clipped="clipped"
      fixed
      app
    >
      <v-list>
        <v-list-item
          v-for="(item, i) in items"
          :key="i"
          :to="item.to"
          router
          exact
        >
          <v-list-item-action>
            <v-icon>{{ item.icon }}</v-icon>
          </v-list-item-action>
          <v-list-item-content>
            <v-list-item-title v-text="item.title" />
          </v-list-item-content>
        </v-list-item>
      </v-list>
    </v-navigation-drawer>
    <v-app-bar :clipped-left="clipped" fixed app>
      <v-app-bar-nav-icon @click.stop="drawer = !drawer" />
      <nuxt-link to="/" class="link-set">
        <v-toolbar-title>
          <div class="text-set">dbdbdip</div>
        </v-toolbar-title>
      </nuxt-link>
      <v-spacer />
      <v-menu v-if="admin" offset-y>
        <template v-slot:activator="{ on, attrs }">
          <v-btn dark v-bind="attrs" v-on="on">
            <div class="text-set">ADMIN</div>
          </v-btn>
        </template>
        <v-list>
          <v-list-item>
            <v-btn text>
              <nuxt-link to="/admin/product" class="link-set">
                <div class="text-set">PRODUCT MANAGE</div>
              </nuxt-link>
            </v-btn>
          </v-list-item>
          <v-list-item>
            <v-btn text>
              <nuxt-link to="/admin/board" class="link-set">
                <div class="text-set">BOARD MANAGE</div>
              </nuxt-link>
            </v-btn>
          </v-list-item>
          <v-list-item>
            <v-btn text>
              <nuxt-link to="/admin/user" class="link-set">
                <div class="text-set">USER MANAGE</div>
              </nuxt-link>
            </v-btn>
          </v-list-item>
          <v-list-item>
            <v-btn text @click="onClickButton">LOG OUT</v-btn>
          </v-list-item>
        </v-list>
      </v-menu>
      <v-btn>
        <nuxt-link to="/cart" class="link-set">
          <div class="text-set">
            <v-icon>mdi-cart-minus</v-icon>
            Cart
            <v-badge v-if="inCarted" color="red" dot />
          </div>
        </nuxt-link>
      </v-btn>
      <v-btn v-if="!userInfo && !admin">
        <nuxt-link to="/signin" class="link-set">
          <div class="text-set">Sign In</div>
        </nuxt-link>
      </v-btn>
      <v-menu v-if="userInfo && !admin" offset-y>
        <template v-slot:activator="{ on, attrs }">
          <v-btn dark v-bind="attrs" v-on="on">
            <div class="text-set">
              {{ userInfo.email }} ( {{ userInfo.name }} )
            </div>
          </v-btn>
        </template>
        <v-list>
          <v-list-item>
            <v-btn text>
              <nuxt-link to="/mypage" class="link-set">
                <div class="text-set">MY PAGE</div>
              </nuxt-link>
            </v-btn>
          </v-list-item>
          <v-list-item>
            <v-btn text @click="onClickButton">LOG OUT</v-btn>
          </v-list-item>
        </v-list>
      </v-menu>
    </v-app-bar>
    <v-main>
      <v-tabs v-if="currentPage.startsWith('product')">
        <v-tab
          v-for="category in categories"
          :key="category.id"
          @click="onTabClick(category.name)"
        >
          {{ category.name }}
        </v-tab>
      </v-tabs>
      <v-container>
        <nuxt />
      </v-container>
    </v-main>
    <v-snackbar v-model="snackbar" :top="true" :timeout="null">
      Log Out?
      <template v-slot:action="{ attrs }">
        <v-btn color="blue" text v-bind="attrs" @click="onLogout">Yes</v-btn>
        <v-btn text v-bind="attrs" @click="snackbar = false">No</v-btn>
      </template>
    </v-snackbar>

    <Dialog
      v-if="dialog"
      :dialog-flag="dialog"
      headline="Logout Success!"
      link-title="Go Home"
      link-push="/"
      :reset-flag="true"
    />
  </v-app>
</template>

<script>
import { mapState } from 'vuex';

import Dialog from '@/components/Dialog.vue';

export default {
  components: {
    Dialog,
  },
  data() {
    return {
      clipped: false,
      drawer: false,
      fixed: false,
      loginStatus: 'SignIn',
      items: [
        {
          icon: 'mdi-dog',
          title: 'Product',
          to: '/product/dog',
        },
        {
          icon: 'mdi-format-list-checkbox',
          title: 'Buying list',
          to: '/buyinglist',
        },
      ],
      title: 'dbdbdip',
      snackbar: false,
      dialog: false,
    };
  },
  computed: {
    ...mapState({
      userInfo: (state) => state.user.userInfo,
      admin: (state) => state.user.admin,
      currentPage: (state) => state.page.currentPageName,
      categories: (state) => state.category.categories,
      inCarted: (state) => state.cart.inCarted,
    }),
  },
  mounted() {
    if (localStorage.accessToken !== undefined && !this.admin) {
      console.log('have a access token');
      this.$store.dispatch('user/getInfo');
    }
  },
  methods: {
    onClickButton() {
      this.dialog = false;
      this.snackbar = true;
    },
    onLogout() {
      this.snackbar = false;
      this.dialog = true;
      this.$store.dispatch('user/logout');
    },
    onTabClick(name) {
      this.$router.push(`/product/${name.toLowerCase()}`);
    },
  },
};
</script>

<style>
.link-set {
  text-decoration: none;
}

.text-set {
  color: white;
}
</style>
