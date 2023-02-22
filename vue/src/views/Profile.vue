<template>
  <b-container id="profile-view">
    <b-row align-h="center">
      <p class="section-header">
        {{ selectGreeting() }}
        {{ name }}!
      </p>
    </b-row>

    <ProfileCard
      v-bind:profile="profile"
      v-bind:isEditing="isEditing"
      @toggle="toggleForm()"
      @delete="deleteProfile(profile.userId)"
    />

    <!-- Edit or create profile -->
    <b-collapse id="collapse-edit-form" class="mt-2">
      <b-row align-h="center">
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
          <b-button size="sm" type="submit" class="default">{{
            profile.favePlant === undefined ? "Save" : "Update"
          }}</b-button>
        </b-form>
      </b-row>
    </b-collapse>
    <!-- End edit profile -->
  </b-container>
</template>

<script>
import profileService from "../services/ProfileService";
import treatmentService from "../services/TreatmentService";
import ProfileCard from "../components/ProfileCard.vue";
import Vue from 'vue';
import { CollapsePlugin, ModalPlugin } from 'bootstrap-vue';
export default {
  name: "profile",
  components: { ProfileCard },
  data() {
    return {
      greeting: [
        "You look great in green, ",
        "Happy planting, ",
        "Nice thumbs, ",
      ],
      isEditing: false,
      newProfile: {
        userId: this.$store.state.user.id,
      },
      savedName: "",
      savedFavePlant: "",
      savedSkillLevel: "",
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
    profile() {
      return this.$store.state.profile;
    },
  },
  methods: {
    selectGreeting() {
      return this.greeting[Math.floor(Math.random() * this.greeting.length)];
    },
    toggleForm() {
      this.newProfile.profileImg = this.profile.profileImg;
      this.profile.favePlant != undefined
        ? (this.newProfile = this.profile)
        : this.newProfile;
      // capturing existing data in order to revert back on cancel()
      this.savedName = this.profile.displayName;
      this.savedFavePlant = this.profile.favePlant;
      this.savedSkillLevel = this.profile.skillLevel;
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
      this.$root.$emit("bv::toggle::collapse", "collapse-edit-form");
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
              this.$root.$emit("bv::toggle::collapse", "collapse-edit-form");
            }
            this.newProfile = {
              userId: this.$store.state.user.id,
            };
          })
          .catch((err) => {
            /* eslint no-console: ["error", { allow: ["error"] }] */
            console.error(err + " problem creating profile!");
            this.$router.push("/oops");
          });
      } else {
        profileService
          .editProfile(this.newProfile)
          .then((response) => {
            if (response.status === 200) {
              this.$store.commit("SET_PROFILE", this.newProfile);
              this.$root.$emit("bv::toggle::collapse", "collapse-edit-form");
            }
          })
          .catch((err) => {
            /* eslint no-console: ["error", { allow: ["error"] }] */
            console.error(err + " problem updating profile!");
            this.$router.push("/oops");
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
                  this.$root.$emit(
                    "bv::toggle::collapse",
                    "collapse-edit-form"
                  );
                  this.newProfile = {
                    userId: this.$store.state.user.id,
                  };
                }
              })
              .catch((err) => {
                /* eslint no-console: ["error", { allow: ["error"] }] */
                console.error(err + " problem deleting profile!");
                this.$router.push("/oops");
              });
          }
        });
    },
  },
  created() {
    Vue.use(CollapsePlugin);
    Vue.use(ModalPlugin);
    profileService
      .getProfile()
      .then((response) => {
        if (response.status == 200) {
          this.$store.commit("SET_PROFILE", response.data);
        }
      })
      .catch((err) => {
        /* eslint no-console: ["error", { allow: ["error"] }] */
        console.error(err + " problem getting profile!");
        this.$router.push("/oops");
      });
    treatmentService
      .getLatestTreatment()
      .then((response) => {
        if (response.status == 200) {
          this.$store.commit("SET_LATEST_TREATMENT", response.data);
        }
      })
      .catch((err) => {
        /* eslint no-console: ["error", { allow: ["error"] }] */
        console.error(err + " problem getting latest treatment!");
        this.$router.push("/oops");
      });
  },
  mounted() {
    this.$root.$on("bv::collapse::state", (collapseId, isJustShown) => {
      if (collapseId == "collapse-edit-form" && isJustShown) {
        this.isEditing = true;
      } else {
        this.isEditing = false;
      }
    });
  },
};
</script>

<style>
.plant-card {
  margin-top: 1rem;
}
.card-footer-row {
  height: 100%;
}
.text-center.middle.col {
  border-right: 1px solid var(--light);
  border-left: 1px solid var(--light);
}
.card-footer-btn.delete-profile {
  display: flex;
  flex-wrap: wrap;
  justify-content: center;
  align-items: center;
}
.avatar-icon-trash.delete-profile {
  margin: 3px;
}
#profile-form {
  margin-top: 1rem;
  margin-bottom: 1rem;
  max-width: 540px;
  width: 100%;
  background-color: var(--light-shade1);
  padding: 0.5rem;
}
.form-title {
  font-size: 1.3rem;
  border-top: 1px solid var(--orange);
  border-bottom: 1px solid var(--orange);
}
.btn#cancel {
  background-color: gray;
  margin-right: 1%;
} 
</style>