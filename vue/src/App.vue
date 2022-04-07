<template>
  <div id="app">
    <SiteHeader v-if="$store.state.token == ''" />
    <SiteNavigation v-if="$store.state.token != ''" />

    <b-container fluid id="container-main">
      <router-view />
      <About v-if="$store.state.token == ''" />
    </b-container>

    <SiteFooter />
  </div>
</template>

<script>
import SiteNavigation from "./components/SiteNavigation.vue";
import SiteHeader from "./components/SiteHeader.vue";
import SiteFooter from "./components/SiteFooter.vue";
import About from "./views/About.vue";

export default {
  components: { SiteNavigation, SiteHeader, About, SiteFooter },
};
</script>

<style>
/* Coolers palette: 
https://coolors.co/palette/729b79-475b63-2e2c2f-d34e24-f5e4d7 */
:root {
  --green: #729B79;
  --grey: #475B63;
  --dark: #2E2C2F;
  --orange: #D34E24;
  --light: #F5E4D7;
}
#app {
  background-color: var(--grey);
}
#container-main {
  min-height: 86vh;
  height: auto;
  background-color: var(--light);
  border-top: 2px solid var(--orange);
  padding-top: 20px;
}
h2 {
  color: var(--dark);
}
a {
  color: var(--dark);
}
</style>