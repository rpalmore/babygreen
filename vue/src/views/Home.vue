<template>
  <div class="home">
    <h1>Home</h1>
    <p>You must be authenticated to see this!</p>
    <p>
      Go to
      <router-link v-bind:to="{ name: 'profile' }">profile page</router-link>
    </p>
  </div>
</template>

<script>
export default {
  name: "home",
};
</script>
