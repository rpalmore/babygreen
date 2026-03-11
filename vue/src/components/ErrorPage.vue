<template>
  <b-container id="error-container">
    <b-row align-h="center">
      <p class="section-header">
        {{ "Oops!" }}
      </p>
    </b-row>
    <b-row align-h="center">
      <p>Your session has expired.</p>
    </b-row>
    <b-row align-h="center">
      <p style="text-align: center">
        Please sign in again to complete your work. We apologize for the disruption!
      </p>
    </b-row>
    <b-row align-h="center">
      <img
        id="plant-flipped"
        src="@/assets/CandaceStone_Pixabay400.png"
        alt=""
      />
    </b-row>
  </b-container>
</template>

<script>
import { LinkPlugin } from "bootstrap-vue";
import Vue from "vue";
export default {
  name: "error-page",
  created() {
    Vue.use(LinkPlugin);
    setTimeout(() => this.logoutOnDelay(), 2000);
  },
  methods: {
    logoutOnDelay() {
      this.$store.commit("LOGOUT");
      if (this.$route != "/") {
        this.$router.push("/");
      }
    },
  },
};
</script>

<style>
#plant-flipped {
  -webkit-transform: scaleY(-1);
  transform: scaleY(-1);
  max-height: 100%;
  max-width: 100%;
}
</style>