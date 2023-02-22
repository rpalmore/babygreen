<template>
  <div id="app">
    <SiteHeader v-if="$store.state.token == ''" />
    <SiteNavigation v-if="$store.state.token != ''" />

    <b-container fluid id="container-main">
      <router-view />
    </b-container>

    <SiteFooter />
  </div>
</template>

<script>
const SiteNavigation = () => import ("./components/SiteNavigation.vue");
const SiteHeader = ()  => import ("./components/SiteHeader.vue");
import SiteFooter from "./components/SiteFooter.vue";

export default {
  components: { SiteNavigation, SiteHeader, SiteFooter },
  onIdle() {
    this.logout();
  },
  methods: {
    logout() {
      this.$store.commit("LOGOUT");
      if (this.$route != "/") {
        this.$router.push("/");
      }
    },
  },
};
</script>

<style>
:root {
  /* Coolers palette: https://coolors.co/palette/190b28-bacdb0-729b79-475b63-b74f6f */
  --green: #729b79;
  --grey: #475b63;
  --dark: #190b28;
  --orange: #b74f6f;
  --light: #bacdb0;
  --light-shade1: #acc3a1;
  --platinum: #ecedea;
  --yellow: #ffcb77;
}
#app {
  background-color: var(--orange);
}
#container-main {
  background-color: var(--light);
  min-height: 86vh;
  height: auto;
  border: 1px solid var(--dark);
  border-top: 5px solid var(--dark);
  padding-bottom: 3rem;
}
a {
  color: var(--dark);
}
a,
a:hover {
  text-decoration: none;
}
.btn.default {
  background-color: var(--dark);
  border-right: 3px solid var(--orange);
  border-left: 3px solid var(--orange);
  color: var(--platinum);
}
.card {
  border: 1px solid var(--gray);
}
.card-title {
  font-weight: 350;
}
#b-card-img {
  background-color: var(--orange);
}
.card-footer-btn {
  background-color: var(--light);
  border: 1px solid var(--orange);
  color: var(--dark);
  height: 100%;
  width: 100%;
}
.card-footer-btn:hover {
  background-color: var(--light-shade1);
  border: 1px solid var(--green);
  color: var(--dark);
}
.card-footer-btn.disabled {
  border: 1px solid var(--dark);
}
.card-footer-btn.disabled:hover {
  border: 1px solid var(--dark);
  background-color: #6c757d;
  opacity: .65;
  color: #fff;
}
.delete {
  display: flex;
  justify-content: end;
}
.default-message {
  margin-top: 1rem;
}
.subsection-header {
  font-size: 1.3rem;
  font-weight: 400;
  margin-top: 1rem;
  padding: 0.3rem;
  padding-left: 1rem;
  background-color: var(--light-shade1);
  border-left: 5px solid var(--green);
}
.section-header {
  border-top: 3px solid var(--orange);
  border-bottom: 3px solid var(--orange);
  margin-top: 20px;
  font-size: 1.5rem;
  font-weight: 300;
  color: var(--dark);
  background-color: var(--light-shade1);
  padding-right: 0.3rem;
  padding-left: 0.3rem;
  text-align: center;
}
</style>