<template>
  <b-container id="footer" fluid>
    Created and maintained by Rebecca Palmore
    <b-link target="_blank" href="https://github.com/rpalmore/babygreen"
      ><b-avatar icon="github" class="avatar-icon-github"> </b-avatar
    ></b-link>
  </b-container>
</template>

<script>
export default {
  name: "site-footer"
};
</script>

<style>
#footer {
  background-color: var(--dark);
  border-top: 5px solid var(--green);
  border-bottom: 3px solid var(--green);
  text-align: center;
  color: var(--light);
  font-weight: 300;
}
.avatar-icon-github {
  background-color: var(--dark);
  border: 2px solid var(--green);
}
</style>