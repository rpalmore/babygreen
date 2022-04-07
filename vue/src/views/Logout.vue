<template>
  <h1>Logout</h1>
</template>

<script>
export default {
  created() {
    this.$store.commit("LOGOUT");
    this.$router.push("/");
    // todo: redirect to another page where login component will load
  }
};
</script>
