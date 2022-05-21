<template>
  <b-container id="profile-view">
    <b-row align-h="center">
      <p class="section-header">
        {{ selectGreeting() }}
        {{ name }}!
      </p>
    </b-row>

    <p>{{ imgUrl }}</p>

    <!-- Profile card -->
    <b-row class="plant-card" align-h="center">
      <b-card no-body class="overflow-hidden" style="max-width: 540px">
        <b-row no-gutters align-v="center">
          <b-col md="6">
            <b-card-img
              fluid-grow
              id="b-card-img"
              :src="selectImg"
              alt="Plant image"
              class="rounded-0"
            ></b-card-img>
          </b-col>
          <b-col md="6">
            <b-list-group flush>
              <b-list-group-item>
                <span>My favorite plant: {{ profile.favePlant }}</span>
              </b-list-group-item>
              <b-list-group-item>
                <span>My skill level: {{ profile.skillLevel }}</span>
              </b-list-group-item>
              <b-list-group-item>
                <span
                  >I am tracking
                  <span class="profileData"
                    ><router-link :to="{ name: 'plants' }">{{
                      numPlants
                    }}</router-link></span
                  >!
                </span>
              </b-list-group-item>
              <b-list-group-item v-if="treatments != ''">
                <span
                  >I most recently
                  {{ latestTreatment.careType }}
                  {{ latestTreatment.numPlants }}
                  {{
                    latestTreatment.numPlants == 1
                      ? " plant on "
                      : "plants on "
                  }}<router-link :to="{ name: 'plant-care' }">
                    <span class="profileData">{{
                      formatDateMonth(
                        latestTreatment.careDate.replace(/-/g, "\/")
                      )
                    }}</span></router-link
                  >
                </span>
              </b-list-group-item>
            </b-list-group>
          </b-col>
        </b-row>
        <b-card-footer>
          <b-row align-v="center">
            <b-col class="text-center">
              <b-button
                id="profile"
                class="card-footer-btn"
                size="sm"
                @click="useCloudinary($event)"
                >{{
                  profile.profileImg === undefined
                    ? "Add a photo"
                    : "Swap photo"
                }}
                <b-avatar
                  size="sm"
                  icon="camera-fill"
                  class="avatar-icon-camera"
                ></b-avatar
              ></b-button>
            </b-col>
            <b-col class="text-center middle">
              <b-button class="card-footer-btn" size="sm" @click="toggleForm()"
                >{{
                  profile.favePlant === undefined
                    ? "Create profile"
                    : "Edit profile"
                }}
                <b-avatar
                  size="sm"
                  icon="pencil-fill"
                  class="avatar-icon-pencil"
                ></b-avatar
              ></b-button>
            </b-col>
            <b-col>
              <b-button
                class="card-footer-btn"
                size="sm"
                @click="deleteProfile(profile.userId)"
              >
                Delete
                <b-avatar
                  size="sm"
                  icon="trash-fill"
                  class="avatar-icon-trash"
                ></b-avatar>
              </b-button>
            </b-col>
          </b-row>
        </b-card-footer>
      </b-card>
    </b-row>
    <!-- End profile card -->

    <b-container v-show="showProfileForm" fluid id="profile-form">
      <b-form @submit.prevent="saveProfile()" id="profile-form">
        <b-form-group
          id="input-group-1"
          label="Display name:"
          label-for="input-1"
        >
          <b-form-input
            required
            type="text"
            id="input-1"
            placeholder="Enter display name"
            v-model="newProfile.displayName"
          ></b-form-input>
        </b-form-group>
        <b-form-group
          id="input-group-2"
          label="Favorite plant:"
          label-for="input-2"
        >
          <b-form-input
            required
            type="text"
            id="input-2"
            placeholder="Your favorite plant"
            v-model="newProfile.favePlant"
          ></b-form-input>
        </b-form-group>
        <b-form-group
          id="input-group-3"
          label="Skill level:"
          label-for="input-3"
        >
          <b-form-select
            id="input-3"
            v-model="newProfile.skillLevel"
            :options="skill"
            required
          ></b-form-select>
        </b-form-group>
        <b-button size="sm" id="cancel" @click="cancel" class="default"
          >Cancel</b-button
        >
        <b-button size="sm" type="submit" class="default">Save</b-button>
      </b-form>
    </b-container>
  </b-container>
</template>

<script>
import profileService from "../services/ProfileService";
import treatmentService from "../services/TreatmentService";
import photoService from "../services/PhotoService.js";
export default {
  name: "profile",
  data() {
    return {
      greeting: [
        "You look great in green, ",
        "Happy planting, ",
        "Nice thumbs, ",
      ],
      newProfile: {
        userId: this.$store.state.user.id,
      },
      savedName: "",
      savedFavePlant: "",
      savedSkillLevel: "",
      showProfileForm: false,
      modal: "",
      skill: [
        { text: "Select One", value: null },
        "Seedling (Beginner)",
        "Sapling (Intermediate)",
        "Sequoia (Advanced)",
      ],
    };
  },
  computed: {
    name() {
      return this.profile.displayName === undefined ||
        this.profile.displayName === null
        ? this.$store.state.user.username
        : this.profile.displayName;
    },
    numPlants() {
      return this.$store.state.plants.length === 1
        ? this.$store.state.plants.length + " plant"
        : this.$store.state.plants.length + " plants";
    },
    profile() {
      return this.$store.state.profile;
    },
    selectImg() {
      return this.profile.profileImg === undefined ||
        this.profile.profileImg === null
        ? require("@/assets/plant-placeholder.png")
        : this.profile.profileImg;
    },
    treatments() {
      return this.$store.state.treatments;
    },
    latestTreatment() {
      return this.$store.state.latestTreatment;
    },
  },
  methods: {
    selectGreeting() {
      return this.greeting[Math.floor(Math.random() * this.greeting.length)];
    },
    useCloudinary(event) {
      photoService.myWidget.open();
      this.$store.commit("SET_CLOUDINARY_SOURCE", event.target.id);
    },
    toggleForm() {
      this.newProfile.profileImg = this.profile.profileImg;
      this.profile.favePlant != undefined
        ? (this.newProfile = this.profile)
        : this.newProfile;
      this.showProfileForm = !this.showProfileForm;
      // capturing existing data in order to revert back on cancel()
      this.savedName = this.profile.displayName;
      this.savedFavePlant = this.profile.favePlant;
      this.savedSkillLevel = this.profile.skillLevel;
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
      this.newProfile = {
        userId: this.$store.state.user.id,
        profileImg: this.$store.state.profile.profileImg,
      };
      // reset data to saved values
      if (this.profile.favePlant != undefined) {
        this.profile.displayName = this.savedName;
        this.profile.favePlant = this.savedFavePlant;
        this.profile.skillLevel = this.savedSkillLevel;
      }
      this.showProfileForm = !this.showProfileForm;
    },
    saveProfile() {
      if (
        this.profile.favePlant == undefined &&
        this.profile.profileImg == undefined
      ) {
        profileService
          .createProfile(this.newProfile)
          .then((response) => {
            if (response.status === 201) {
              this.$store.commit("SET_PROFILE", this.newProfile);
            }
            this.newProfile = {
              userId: this.$store.state.user.id,
            };
            this.showProfileForm = !this.showProfileForm;
          })
          .catch((err) => {
            alert(err + " problem creating profile!");
          });
      } else {
        profileService
          .editProfile(this.newProfile)
          .then((response) => {
            if (response.status === 200) {
              this.$store.commit("SET_PROFILE", this.newProfile);
            }
            this.showProfileForm = !this.showProfileForm;
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
                  this.$store.commit("DELETE_PROFILE");
                  this.showProfileForm = false;
                  this.newProfile = {
                    userId: this.$store.state.user.id,
                  };
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
.plant-card {
  margin-top: 1rem;
}
.card-footer .btn {
  width: 100%;
}
.text-center.middle.col {
  border-right: 1px solid var(--light);
  border-left: 1px solid var(--light);
}
.avatar-icon-camera {
  background-color: var(--yellow);
}
.avatar-icon-pencil {
  border: 1px solid var(--orange);
  background-color: var(--platinum);
  color: var(--dark);
}
.avatar-icon-trash {
  border: 1px solid var(--green);
  border: 1px solid var(--platinum);
  background-color: var(--orange);
}
#profile-form {
  margin-top: 1rem;
  margin-bottom: 1rem;
}
.form-title {
  font-size: 1.3rem;
  border-top: 1px solid var(--orange);
  border-bottom: 1px solid var(--orange);
}
.btn#cancel {
  margin-right: 1%;
  background-color: var(--gray);
}
</style>