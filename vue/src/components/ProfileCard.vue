<template>
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
            <b-list-group-item v-if="treatments.length > 0">
              <span
                >I most recently
                {{ latestTreatment.careType }}
                {{ latestTreatment.numPlants }}
                {{ latestTreatment.numPlants == 1 ? " plant on " : "plants on "
                }}<router-link :to="{ name: 'care' }">
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
        <b-row class="card-footer-row">
          <b-col class="text-center">
            <b-button
              id="profile"
              class="card-footer-btn"
              size="sm"
              @click="useCloudinary($event)"
              >{{ !!profile.profileImg ? "Swap photo" : "Add a photo" }}
              <b-avatar
                size="sm"
                icon="camera-fill"
                class="avatar-icon-camera"
              ></b-avatar
            ></b-button>
          </b-col>
          <b-col class="text-center middle">
            <b-button
              class="card-footer-btn"
              size="sm"
              :disabled="isEditing"
              @click="$emit('toggle')"
              v-b-toggle.collapse-edit-form
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
              class="card-footer-btn delete-profile"
              size="sm"
              @click="$emit('delete', profile.userId)"
            >
              Delete
              <b-avatar
                size="sm"
                icon="trash-fill"
                class="avatar-icon-trash delete-profile"
              ></b-avatar>
            </b-button>
          </b-col>
        </b-row>
      </b-card-footer>
    </b-card>
  </b-row>
</template>

<script>
import photoService from "../services/PhotoService";
export default {
  name: "profile-card",
  props: ["profile", "isEditing"],
  data() {
    return {};
  },
  computed: {
    selectImg() {
      return this.profile.profileImg === undefined ||
        this.profile.profileImg === null
        ? require("@/assets/CandaceStone_Pixabay.png")
        : this.profile.profileImg;
    },
    treatments() {
      return this.$store.state.treatments;
    },
    latestTreatment() {
      return this.$store.state.latestTreatment;
    },
    numPlants() {
      return this.$store.state.plants.length === 1
        ? this.$store.state.plants.length + " plant"
        : this.$store.state.plants.length + " plants";
    },
  },
  methods: {
    useCloudinary(event) {
      photoService.myWidget.open();
      this.$store.commit("SET_CLOUDINARY_SOURCE", event.target.id);
    },
    formatDateMonth(date) {
      if (date) {
        const options = {
          month: "long",
          day: "numeric",
        };
        return new Date(date).toLocaleDateString("en-US", options);
      }
    },
  },
};
</script>

<style>
</style>