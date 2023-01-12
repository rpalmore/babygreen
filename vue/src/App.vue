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
import SiteNavigation from "./components/SiteNavigation.vue";
import SiteHeader from "./components/SiteHeader.vue";
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
h2,
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
.btn.delete {
  background-color: var(--orange);
  border-right: 3px solid var(--dark);
  border-left: 3px solid var(--dark);
  color: var(--dark);
}
.btn#cancel {
  background-color: var(--gray);
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
}
.card-footer-btn:hover {
  background-color: var(--light-shade1);
  border: 1px solid var(--green);
  color: var(--dark);
}
.card-footer .btn {
  width: 100%;
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
#deleteTreatment {
  background-color: var(--orange);
}
.delete {
  display: flex;
  justify-content: end;
}
.avatar-icon-camera {
  background-color: var(--yellow);
  border: 1px solid var(--orange);
  color: var(--dark);
}
.avatar-icon-pencil {
  border: 1px solid var(--orange);
  background-color: var(--platinum);
  color: var(--dark);
}
.avatar-icon-trash {
  border: 1px solid var(--platinum);
  background-color: var(--orange);
}
.avatar-custom#misted {
  background-color: var(--light);
  border: 1px solid var(--orange);
}
.avatar-custom#pest-treated {
  background-color: var(--orange);
  border: 1px solid var(--green);
}
.avatar-custom#fertilized {
  background-color: var(--yellow);
  border: 1px solid var(--green);
}
.avatar-custom#repotted {
  background-color: var(--platinum);
  border: 1px solid var(--green);
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