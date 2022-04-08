<template>
  <b-container id="footer" fluid>
    Footer content and links
  </b-container>
</template>

<script>
export default {
  name: "site-footer",
};
</script>

<style>
#footer {
  /* background-color: var(--grey); */
  background-color: var(--dark);
  border-top: 1px solid var(--orange);
  border-bottom: 4px solid var(--green);
  text-align: center;
  color: var(--light);
  font-weight: 300;
}
</style>