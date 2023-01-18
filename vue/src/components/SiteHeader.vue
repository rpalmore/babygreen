<template>
  <div>
    <b-jumbotron
      header="baby green"
      lead="Tools for a lush life"
      title="(Photo by Scott Webb / Unsplash)"
    >
      <hr class="my-4" />
      <b-row no-gutters>
        <b-button id="btnRegister" class="default" :to="{ name: 'register' }"
          >Register</b-button
        >
        <b-button id="btnLogin" class="default" :to="{ name: 'login' }"
          >Sign In</b-button
        >
      </b-row>
    </b-jumbotron>
  </div>
</template>

<script>
export default {
  name: "site-header"
};
</script>

<style scoped>
.jumbotron {
  background: url(../assets/scott-webb-unsplash-3.webp) center center;
  background-size: cover;
  height: 100%;
}
.my-4 {
  width: 50%;
  margin-left: 0;
}
.jumbotron .row {
  gap: 0.3rem;
}
#btnLogin {
  min-width: 88px;
}
.lead {
  color: var(--dark);
}
</style>