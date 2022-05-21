<template>
  <b-card-header id="site-nav" header-tag="nav" v-if="$store.state.token != ''">
    <b-nav pills>
      <b-nav-item-dropdown
        id="nav-dropdown"
        text="My Plants"
        toggle-class="nav-link-custom"
        left
      >
        <b-dropdown-item
          :to="{ name: 'add-plant' }"
          exact
          exact-active-class="active"
          >Add a plant</b-dropdown-item
        >
        <b-dropdown-divider></b-dropdown-divider>
        <b-dropdown-item
          v-bind:plant="plant"
          v-for="plant in plants"
          v-bind:key="plant.plantId"
          :to="{ name: 'plant-detail', params: { plantId: plant.plantId } }"
          exact
          exact-active-class="active"
          >{{ plant.plantName }}</b-dropdown-item
        >
      </b-nav-item-dropdown>
      <b-nav-item :to="{ name: 'plants' }" exact exact-active-class="active"
        >Log Care
      </b-nav-item>
      <b-nav-item :to="{ name: 'care' }" exact exact-active-class="active"
        >Treatments</b-nav-item
      >
      <b-nav-item-dropdown
        id="nav-dropdown"
        text="Account"
        toggle-class="nav-link-custom"
        left
      >
        <b-dropdown-item
          :to="{ name: 'profile' }"
          exact
          exact-active-class="active"
          >Profile</b-dropdown-item
        >
        <b-dropdown-item
          :to="{ name: 'about' }"
          exact
          exact-active-class="active"
          >About</b-dropdown-item
        >
        <b-dropdown-item
          :to="{ name: 'logout' }"
          exact
          exact-active-class="active"
          >Logout</b-dropdown-item
        >
      </b-nav-item-dropdown>
    </b-nav>
    <b-nav-text>baby green</b-nav-text>
  </b-card-header>
</template>

<script>
export default {
  name: "site-header",
  computed: {
    plants() {
      return this.$store.state.plants;
    },
  },
};
</script>

<style>
#site-nav.card-header {
  display: flex;
  background: url(../assets/avinash-kumar-1-unsplash-nav.webp) center center;
  background-size: cover;
  border-bottom: 2px solid var(--orange);
  justify-content: space-between;
}
.nav {
  align-items: center;
}
.nav-link {
  background-color: var(--platinum);
  opacity: 0.9;
}
/* a:hover {
  color: var(--orange);
} */
.nav-pills .nav-link.active,
.nav-pills .show > .nav-link,
.dropdown-item.active,
.dropdown-item:active {
  background-color: var(--green);
}
.navbar-text {
  display: flex;
  align-items: center;
  text-align: center;
  background-color: var(--light);
  color: var(--dark);
  opacity: 0.9;
  padding: 0.5rem;
  border: 1px solid var(--green);
  border-radius: 0.25rem;
  font-family: "Lobster", cursive;
  font-size: 1.1rem;
}
</style>