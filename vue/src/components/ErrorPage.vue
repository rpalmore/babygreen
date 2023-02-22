<template>
  <b-container id="error-container">
    <b-row align-h="center">
      <p class="section-header">
        {{ "Oops!" }}
      </p>
    </b-row>
    <b-row align-h="center">
      <p>
        Something went wrong.
        <b-link id="logout-link" :to="{ name: 'logout' }"
          >Please log out</b-link
        >
        and log back in. We apologize for the disruption!
      </p>
    </b-row>
    <b-row align-h="center">
      <img id="plant-flipped" src="@/assets/CandaceStone_Pixabay400.png" alt="" />
    </b-row>
  </b-container>
</template>

<script>
import { LinkPlugin } from 'bootstrap-vue';
import Vue from 'vue'
export default {
  name: "error-page",
  created() {
    Vue.use(LinkPlugin);
  }
};
</script>

<style>
#logout-link:hover {
  border-bottom: 2px solid var(--orange);
}
#plant-flipped {
  -webkit-transform: scaleY(-1);
  transform: scaleY(-1);
  max-height: 100%;
  max-width: 100%;
}
</style>