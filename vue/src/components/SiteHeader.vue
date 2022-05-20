<template>
<div>
  <b-jumbotron
    header="baby green"
    lead="Tools for a lush life"
  >
  <hr class="my-4">
    <b-button class="default" :to="{ name: 'login' }">Sign In</b-button>
    <b-button id="btnRight" class="default" :to="{ name: 'register' }">Register</b-button>
  </b-jumbotron>
  </div>
</template>

<script>
export default {
  name: "site-header",
};
</script>

<style scoped>
.jumbotron {
background: url(../assets/scott-webb-unsplash-3.webp) center center;
background-size: cover;
height: 100%;
/* border-bottom: 2px solid var(--orange); */
/* border-bottom: 1px solid var(--dark); */
}
.my-4 {
  width: 50%;
  margin-left: 0;
}
#btnRight {
  margin-left: 0.3rem;
}
/* .btn {
  background-color: var(--green);
  background-color: var(--grey);
  background-color: var(--dark);
  border-right: 3px solid var(--orange);
  border-left: 3px solid var(--orange);
} */
h1, .lead {
  color: var(--dark);
}

</style>