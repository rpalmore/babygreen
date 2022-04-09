<template>
  <div id="app">
    <SiteHeader v-if="$store.state.token == ''" />
    <SiteNavigation v-if="$store.state.token != ''" />

    <b-container fluid id="container-main">
      <!-- <span id="branding">baby green</span> -->
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
  /* background-color: var(--grey); */
  background-color: var(--dark);
}
#container-main {
  background-color: var(--grey);
  min-height: 86vh;
  height: auto;
  border: 1px solid var(--dark);
  /* background-color: var(--light); */
  border-top: 1px solid var(--orange);
  /* padding-top: 20px; */
}
/* span#branding {
  display: flex;
  justify-content: end;
  color: var(--orange);
  font-weight: bold;
} */
h2 {
  color: var(--dark);
}
a {
  color: var(--dark);
}
.btn.homepage {
  background-color: var(--dark);
  border-right: 3px solid var(--orange);
  border-left: 3px solid var(--orange);
  color: var(--light);
}
</style>