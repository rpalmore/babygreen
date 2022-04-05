<template>
  <div id="profile">
    <h1>
      You look great in green,
      {{
        this.$store.state.profile.displayName === undefined
          ? this.$store.state.user.username
          : this.$store.state.profile.displayName
      }}!
    </h1>
    <p>Consider adding a profile display name.</p>
    <div class="profile-details">
      <p>My id: {{ profile.userId }}</p>
      <p>My display name: {{ this.$store.state.profile.displayName }}</p>
      <p>My favorite plant: {{ this.$store.state.profile.favePlant }}</p>
      <p>My skill level: {{ this.$store.state.profile.skillLevel }}</p>
      <p>I have {{ this.$store.state.plants.length }} plants!</p>
    </div>
    <h2>
      {{
        this.$store.state.profile.favePlant === undefined
          ? "Create a profile"
          : "Edit your profile"
      }}
    </h2>
    <form v-on:submit.prevent="saveProfile" id="profile-form">
      <label for="displayName" class="profile-form">Display name</label>
      <input type="text" class="profile-form" v-model="profile.displayName" />
      <label for="favePlant" class="profile-form">Favorite plant</label>
      <input type="text" class="profile-form" v-model="profile.favePlant" />
      <label for="skillLevel" class="profile-form">Skill level</label>
      <input type="text" class="profile-form" v-model="profile.skillLevel" />
      <button class="submit">Save</button>
    </form>
    <button v-on:click="deleteProfile(profile.userId)" class="delete">
      Delete
    </button>
  </div>
</template>

<script>
import profileService from "../services/ProfileService";
export default {
  name: "profile",
  data() {
    return {
      profile: {
        userId: this.$store.state.user.id,
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
            this.profile = {
              userId: this.$store.state.user.id,
            };
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
    deleteProfile(userId) {
      if (confirm("Are you sure you want to delete this profile?")) {
        profileService.deleteProfile(userId).then((response) => {
          if (response.status == 204) {
            this.$store.commit("SET_PROFILE", userId);
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