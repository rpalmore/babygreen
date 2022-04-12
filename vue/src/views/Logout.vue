<template>
  <h1>Logout</h1>
</template>

<script>
export default {
  created() {
    this.$store.commit("LOGOUT");
    if (this.$route != "/") {
    this.$router.push("/");
    }
  }
};
</script>
