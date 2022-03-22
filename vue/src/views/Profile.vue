<template>
  <div id="profile-form">
    <h1>Create a profile</h1>
    <form v-on:submit.prevent="createProfile" id="profile-form">
      <label for="favePlant" class="profile-form">Favorite plant</label>
      <input type="text" class="profile-form" v-model="profile.favePlant" />
      <label for="skillLevel" class="profile-form">Skill level</label>
      <input type="text" class="profile-form" v-model="profile.skillLevel" />
      <label for="numPlants" class="profile-form">Number of plants</label>
      <input type="text" class="profile-form" v-model="profile.numPlants" />
    </form>
  </div>
</template>

<script>
export default {
  name: "profile"
};
</script>

<style>
</style>