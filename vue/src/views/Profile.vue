<template>
  <div id="profile">
    <h1>Welcome, {{ profile.username }}</h1>
    <div class="profile-details">
    <p>{{ profile.username }}</p>
    <p>My id: {{ this.$store.state.user.id }}</p>
    <p>My favorite plant: {{ this.$store.state.profile.favePlant }}</p>
    <p>My skill level: {{ this.$store.state.profile.skillLevel }}</p>
    <p>I have {{ this.$store.state.plants.length }} plants!</p>
    </div>
    <h1>Create a profile</h1>
    <form v-on:submit.prevent="saveProfile" id="profile-form">
      <label for="favePlant" class="profile-form">Favorite plant</label>
      <input type="text" class="profile-form" v-model="profile.favePlant" />
      <label for="skillLevel" class="profile-form">Skill level</label>
      <input type="text" class="profile-form" v-model="profile.skillLevel" />
      <button class="submit">Save</button>
    </form>
    <button v-on:click="deleteProfile(profile.username)" class="delete">Delete</button>
  </div>
</template>

<script>
import profileService from "../services/ProfileService";
export default {
  name: "profile",
  data() {
    return {
      profile: {
        username: this.$store.state.user.username,
      },
    };
  },
  methods: {
    saveProfile() {
      if (this.$store.state.profile.favePlant === undefined) {
        profileService
          .createProfile(this.profile)
          .then((response) => {
            if (response.status === 201) {
              this.$store.commit("SET_PROFILE", this.profile);
            }
            this.profile = {};
          })
          .catch((err) => {
            alert(err + " problem creating profile!");
          });
      } else {
        profileService
          .editProfile(this.profile)
          .then((response) => {
            if (response.status === 200) {
              this.$store.commit("SET_PROFILE", this.profile);
            }
          })
          .catch((err) => {
            alert(err + " problem updating profile!");
          });
      }
    },
    deleteProfile(username) {
      if (confirm("Are you sure you want to delete this profile?")) {
        profileService.deleteProfile(username).then((response) => {
          if (response.status == 204) {
            this.$store.commit("SET_PROFILE", username);
          }
        });
      }
    },
  },
};
</script>

<style>
#profile-form {
  background-color: pink;
}
.profile-details {
  background-color: lightblue;
}
</style>