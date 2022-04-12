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
  /* --green: #729B79;
  --grey: #475B63;
  --dark: #2E2C2F;
  --orange: #D34E24;
  --light: #F5E4D7; */
  /* Coolers palette: https://coolors.co/palette/190b28-bacdb0-729b79-475b63-b74f6f */
  --green: #729b79;
  --grey: #475b63;
  --dark: #190b28;
  --orange: #b74f6f;
  --light: #bacdb0;
  /* Extra color for font where needed */
  --platinum: #ecedea;
  /* --green:  #F2CC8F;
  --grey: #81B29A;
  --dark: #3D405B;
  --orange: #E07A5F;
  --light: #F4F1DE; */
}
#app {
  background-color: var(--orange);
  /* background-color: var(--grey); */
  /* background-color: var(--dark); */
}
#container-main {
  background-color: var(--grey);
  background-color: var(--light);
  min-height: 86vh;
  height: auto;
  border: 1px solid var(--dark);
  /* background-color: var(--light); */
  border-top: 7px solid var(--grey);
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
.btn.default {
  background-color: var(--dark);
  border-right: 3px solid var(--orange);
  border-left: 3px solid var(--orange);
  color: var(--platinum);
}
.btn.delete {
  background-color: var(--orange);
  border-right: 3px solid var(--dark);
  border-left: 3px solid var(--dark);
  color: var(--light);
  color: var(--dark);
}
</style>