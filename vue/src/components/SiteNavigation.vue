<template>
  <b-card-header id="site-nav" header-tag="nav" v-if="$store.state.token != ''">
    <b-nav pills>
      <!-- <b-nav-text>baby green</b-nav-text> -->
      <b-nav-item-dropdown
        id="nav-dropdown"
        text="My Plants"
        toggle-class="nav-link-custom"
        left
      >
        <b-dropdown-item
          :to="{ name: 'plants' }"
          exact
          exact-active-class="active"
          >All Plants</b-dropdown-item
        >
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
      <b-nav-item :to="{ name: 'plant-care' }" exact exact-active-class="active"
        >Plant Care</b-nav-item
      >
      <b-nav-item :to="{ name: 'profile' }" exact exact-active-class="active"
        >Profile</b-nav-item
      >
      <b-nav-item-dropdown
        id="nav-dropdown"
        text="Account"
        toggle-class="nav-link-custom"
        left
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
  background: url(../assets/avinash-kumar-1-unsplash-nav.webp) center center;
  background-size: cover;
  height: 100%;
  border-bottom: 3px solid var(--orange);
}
.nav-pills .nav-link.active,
.nav-pills .show > .nav-link,
.dropdown-item.active,
.dropdown-item:active {
  background-color: var(--green);
}
</style>