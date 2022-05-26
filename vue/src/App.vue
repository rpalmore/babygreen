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
/* Another test palette I haven't managed to save yet
https://coolors.co/190b28-accccc-ffcb77-4d6a6d-b74f6f */
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
  --light-shade1: #ACC3A1;
  /* Extra color for font where needed */
  --platinum: #ecedea;
  --yellow: #FFCB77;
  /* --green:  #F2CC8F;
  --grey: #81B29A;
  --dark: #3D405B;
  --orange: #E07A5F;
  --light: #F4F1DE; */
  /* Coolers palette based on 
  plant placeholder image: https://coolors.co/palette/20140b-accccc-4a9494-cb4a00-ea9029 */
  /* --green: #4A9494;
  --grey: #EA9029;
  --dark: #20140B;
  --orange: #CB4A00;
  --light: #ACCCCC;
  */
  /* Another to try: https://coolors.co/palette/f4f1de-e07a5f-3d405b-81b29a-f2cc8f */
} 
#app {
  background-color: var(--orange);
  /* background-color: var(--grey); */
  /* background-color: var(--dark); */
}
#container-main {
  background-color: var(--light);
  min-height: 86vh;
  height: auto;
  border: 1px solid var(--dark);
  border-top: 5px solid var(--dark);
  padding-bottom: 3rem;
}
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
  color: var(--dark);
}
</style>