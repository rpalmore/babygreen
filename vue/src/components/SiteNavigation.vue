<template>
  <b-card-header header-tag="nav" v-if="$store.state.token != ''">
    <b-nav pills>
      <b-nav-item :to="{ name: 'plants' }" exact exact-active-class="active"
        >My Plants</b-nav-item
      >
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
        right
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
};
</script>

<style>
.card-header {
  background: url(../assets/avinash-kumar-1-unsplash-nav.webp) center center;
  background-size: cover;
  height: 100%;
}
.nav-pills .nav-link.active,
.nav-pills .show > .nav-link,
.dropdown-item.active,
.dropdown-item:active {
  background-color: var(--green);
}
</style>