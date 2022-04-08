<template>
  <!-- <b-container id="profile" class="text-center"> -->
  <!-- Above: quick and easy way to center all text -->
  <b-container id="profile">
    <b-row>
      <h2>
        You look great in green,
        {{
          this.$store.state.profile.displayName === undefined
            ? this.$store.state.user.username
            : this.$store.state.profile.displayName
        }}!
      </h2>
    </b-row>
    <b-container class="profile-details">
      <b-row>
        <b-col><span class="profile-question">My favorite plant</span> </b-col>
        <b-col
          ><span class="profile-answer">{{
            this.$store.state.profile.favePlant
          }}</span></b-col
        >
      </b-row>
      <b-row>
        <b-col><span class="profile-question">My skill level</span></b-col>
        <b-col
          ><span class="profile-answer">{{
            this.$store.state.profile.skillLevel
          }}</span></b-col
        >
      </b-row>
      <b-row>
        <b-col><span class="profile-question">I am tracking ...</span></b-col>
        <b-col
          ><span class="profile-answer">
            {{
              this.$store.state.plants.length === 1
                ? this.$store.state.plants.length + " plant baby"
                : this.$store.state.plants.length + " plant babies"
            }}!</span
          >
        </b-col>
      </b-row>
    </b-container>
    <!--<h2>
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
    </button> -->
  </b-container>
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
      modal: "",
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
      this.modal = "";
      this.$bvModal
        .msgBoxConfirm("Are you sure you want to delete this profile?")
        .then((value) => {
          this.modal = value;
          if (value === true) {
            profileService
              .deleteProfile(userId)
              .then((response) => {
                if (response.status == 204) {
                  this.$store.commit("SET_PROFILE", userId);
                }
              })
              .catch((err) => {
                alert(err + " problem deleting profile!");
              });
          }
        });
    },
  },
};
</script>

<style>
#profile > .row {
  justify-content: center;
  text-align: center;
}
.profile-details {
  background-color: var(--green);
  margin-top: 10px;
}
.profile-details > .row {
  /* border: 3px solid var(--light); */
}
.profile-details .col {
  border: 3px solid var(--light);
  padding: 10px;
}
.profile-details span {
  font-size: 1.2rem;
}
.profile-question {
  display: flex;
  justify-content: end;
}
</style>