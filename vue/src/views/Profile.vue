<template>
  <!-- <b-container id="profile" class="text-center"> -->
  <!-- Above: quick and easy way to center all text -->
  <b-container id="profile">
    <b-row>
      <p class="section-header">
        You look great in green,
        {{
          this.$store.state.profile.displayName === undefined
            ? this.$store.state.user.username
            : this.$store.state.profile.displayName
        }}!
      </p>
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
      <b-row
        v-if="
          this.$store.state.treatments != ''"
      >
        <b-col><span class="profile-question">I most recently ...</span></b-col>
        <b-col
          ><span class="profile-answer">
            {{ this.$store.state.latestTreatment.careType }}
            {{ this.$store.state.latestTreatment.numPlants }}
            {{
              this.$store.state.latestTreatment.numPlants == 1
                ? " plant on "
                : "plants on "
            }}
            {{ this.$store.state.latestTreatment.careDate }}
          </span>
        </b-col>
      </b-row>
    </b-container>
    <p class="section-header">
      {{
        this.$store.state.profile.favePlant === undefined
          ? "Create a profile"
          : "Edit your profile"
      }}
    </p>

    <!-- Create or edit your profile -->
    <b-form @submit.prevent="saveProfile" id="profile-form">
      <b-form-group
        id="input-group-1"
        label="Display name:"
        label-for="input-1"
      >
        <b-form-input
          type="text"
          id="input-1"
          placeholder="Enter display name"
          v-model="profile.displayName"
        ></b-form-input>
      </b-form-group>
      <b-form-group
        id="input-group-2"
        label="Favorite plant:"
        label-for="input-2"
      >
        <b-form-input
          type="text"
          id="input-2"
          placeholder="Your favorite plant"
          v-model="profile.favePlant"
        ></b-form-input>
      </b-form-group>
      <b-form-group id="input-group-3" label="Skill level:" label-for="input-3">
        <b-form-select
          id="input-3"
          v-model="profile.skillLevel"
          :options="skill"
          required
        ></b-form-select>
      </b-form-group>
      <b-button type="submit" class="default">Save</b-button>&nbsp;
      <b-button @click="cancel" class="default">Cancel</b-button>
    </b-form>
    <b-button v-on:click="deleteProfile(profile.userId)" class="delete">
      Delete
    </b-button>
  </b-container>
</template>

<script>
import profileService from "../services/ProfileService";
import treatmentService from "../services/TreatmentService";
export default {
  name: "profile",
  data() {
    return {
      profile: {
        userId: this.$store.state.user.id,
      },
      modal: "",
      skill: [
        { text: "Select One", value: null },
        "Seedling (Beginner)",
        "Intermediate",
        "Advanced",
      ],
    };
  },
  computed: {
    treatments() {
      return this.$store.state.treatments;
    },
  },
  methods: {
    cancel() {
      this.profile = {
        userId: this.$store.state.user.id,
      };
    },
    saveProfile() {
      // eslint-disable-next-line no-console
      console.log(this.$store.state.profile.favePlant);
      if (this.$store.state.profile.favePlant == undefined) {
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
            this.profile = {
              userId: this.$store.state.user.id,
            };
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
  created() {
    treatmentService
      .getLatestTreatment()
      .then((response) => {
        if (response.status == 200) {
          this.$store.commit("SET_LATEST_TREATMENT", response.data);
        }
      })
      .catch((err) => {
        alert(err + " problem getting latest treatment!");
      });
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