<template>
  <!-- <b-container id="profile" class="text-center"> -->
  <!-- Above: quick and easy way to center all text -->
  <b-container id="profile-view">
    <b-row align-h="center">
      <p class="section-header">
        You look great in green,
        {{
          this.$store.state.profile.displayName === undefined
            ? this.$store.state.user.username
            : this.$store.state.profile.displayName
        }}!
      </p>
    </b-row>

    <!-- Profile card -->
    <b-row class="plant-card" align-h="center">
      <b-card no-body class="overflow-hidden" style="max-width: 540px">
        <b-row no-gutters>
          <b-col md="6">
            <b-card-img
              fluid-grow
              id="b-card-img"
              :src="require('@/assets/plant-placeholder.png')"
              alt="Plant image"
              class="rounded-0"
            ></b-card-img>
          </b-col>
          <b-col md="6">
            <b-list-group flush>
              <b-list-group-item>
                <span
                  >My favorite plant:
                  {{ this.$store.state.profile.favePlant }}</span
                >
              </b-list-group-item>
              <b-list-group-item>
                <span
                  >My skill level:
                  {{ this.$store.state.profile.skillLevel }}</span
                >
              </b-list-group-item>
              <b-list-group-item>
                <span
                  >I am tracking
                  {{
                    this.$store.state.plants.length === 1
                      ? this.$store.state.plants.length + " plant"
                      : this.$store.state.plants.length + " plants"
                  }}!</span
                >
              </b-list-group-item>
              <b-list-group-item v-if="treatments != ''">
                <span
                  >I most recently
                  {{ latestTreatment.careType }}
                  {{ latestTreatment.numPlants }}
                  {{
                    latestTreatment.numPlants == 1 ? " plant on " : "plants on "
                  }}
                  {{
                    formatDateMonth(
                      latestTreatment.careDate.replace(/-/g, "\/")
                    )
                  }}
                </span>
              </b-list-group-item>
            </b-list-group>
          </b-col>
        </b-row>
        <b-card-footer>
          <b-row align-v="center">
            <b-col class="text-center">
              <b-button id="addPhotoBtn" size="sm"
                >Add a photo
                <b-avatar
                  icon="camera-fill"
                  class="avatar-icon-camera"
                ></b-avatar
              ></b-button>
            </b-col>
            <b-col class="text-center middle">
              <b-button id="addPhotoBtn" size="sm" @click="toggleForm()"
                >Edit profile
                <b-avatar
                  icon="pencil-fill"
                  class="avatar-icon-camera"
                ></b-avatar
              ></b-button>
            </b-col>
            <b-col>
              <b-button
                id="addPhotoBtn"
                size="sm"
                @click="deleteProfile(profile.userId)"
              >
                Delete
                <b-avatar
                  icon="trash-fill"
                  class="avatar-icon-camera"
                ></b-avatar>
              </b-button>
            </b-col>
          </b-row>
        </b-card-footer>
      </b-card>
    </b-row>
    <!-- End profile card -->

    <!-- Create or edit your profile -->
    <b-container v-show="showProfileForm === true" fluid id="profile-form">
      <b-row>
        <span class="form-title">
          {{
            this.$store.state.profile.favePlant === undefined
              ? "Create a profile"
              : "Edit your profile"
          }}
        </span>
      </b-row>

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
        <b-form-group
          id="input-group-3"
          label="Skill level:"
          label-for="input-3"
        >
          <b-form-select
            id="input-3"
            v-model="profile.skillLevel"
            :options="skill"
            required
          ></b-form-select>
        </b-form-group>
        <b-button size="sm" type="submit" class="default">Save</b-button>
        <b-button size="sm" id="cancel" @click="cancel" class="default"
          >Cancel</b-button
        >
      </b-form>
    </b-container>
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
      showProfileForm: false,
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
    latestTreatment() {
      return this.$store.state.latestTreatment;
    },
  },
  methods: {
    toggleForm() {
      this.showProfileForm === true ? (this.showProfileForm = false) : (this.showProfileForm = true);
    },
    formatDateMonth(date) {
      const options = {
        // weekday: "long",
        // year: "numeric",
        month: "long",
        day: "numeric",
      };
      return new Date(date).toLocaleDateString("en-US", options);
    },
    cancel() {
      this.profile = {
        userId: this.$store.state.user.id,
      };
      this.toggleForm();
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
.card-footer > .row {
  /* background-color: var(--green);
  background-color: var(--orange); */
}
.card-footer .btn {
  width: 100%;
}
.text-center.middle.col {
  border-right: 1px solid var(--light);
  border-left: 1px solid var(--light);
}
#profile-form {
  margin-top: 1rem;
  margin-bottom: 1rem;
}
.form-title {
  font-size: 1.3rem;
}
.btn#cancel {
  margin-left: 1%;
}
</style>