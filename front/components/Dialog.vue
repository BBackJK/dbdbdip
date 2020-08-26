<template>
  <div>
    <v-dialog v-model="dialogFlag" max-width="290" v-if="!resetFlag">
      <v-card>
        <v-card-title class="headline">{{ headline }}</v-card-title>

        <v-card-actions>
          <v-spacer></v-spacer>

          <v-btn color="green darken-1" text>
            <nuxt-link :to="linkPush" class="link-set">
              <div class="text-set">
                {{ linkTitle }}
              </div>
            </nuxt-link>
          </v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
    <v-dialog v-model="dialogFlag" max-width="290" v-if="resetFlag">
      <v-card>
        <v-card-title class="headline">{{ headline }}</v-card-title>
        <v-card-actions>
          <v-spacer></v-spacer>

          <v-btn color="green darken-1" text>
            <a href="/" class="link-set">
              <div class="text-set">
                {{ linkTitle }}
              </div>
            </a>
          </v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </div>
</template>

<script>
export default {
  props: {
    headline: {
      type: String,
      default: 'Head Line',
    },
    linkTitle: {
      type: String,
      default: 'Go Home',
    },
    linkPush: {
      type: String,
      default: '/',
    },
    dialogFlag: {
      type: Boolean,
      default: false,
    },
    resetFlag: {
      type: Boolean,
      default: false,
    },
  },
};
</script>

<style scoped>
.link-set {
  text-decoration: none;
}
.text-set {
  color: green;
}
</style>
