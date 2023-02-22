<template>
  <b-row class="plant-card" align-h="center">
    <b-card no-body class="overflow-hidden" style="max-width: 640px">
      <b-row no-gutters align-v="center">
        <b-col md="6">
          <b-card-img
            id="b-card-img"
            :src="selectPlantImg(plant.plantImg)"
            alt="Plant image"
            class="rounded-0"
          ></b-card-img>
        </b-col>
        <b-col md="6">
          <b-card-body title="About me">
            <b-card-text>
              I am an {{ plant.indoor == true ? "indoor" : "outdoor" }} plant
              and have been in {{ name }}&#8217;s care since
              <a
                id="editDate"
                v-b-tooltip
                title="Edit date"
                v-b-toggle.collapse-edit-form
                @click="$emit('toggleEdit', plant)"
                >{{ formatDate(plant.plantAge.replace(/-/g, "\/")) }}</a
              >.
              <p class="plant-preference" v-if="!!plant.plantSchedule">
                {{
                  "I like to be watered every " + determineSchedule(plant) + "."
                }}
              </p>

              <p class="plant-preference" v-else>
                <a
                  id="addSchedule"
                  v-b-tooltip
                  title="Add schedule"
                  @click="$emit('toggleEdit', plant)"
                  v-b-toggle.collapse-edit-form
                  >{{ "Add a custom watering schedule" }}</a
                >.
              </p>
            </b-card-text>

            <b-card-text v-if="plant.infoUrl != null">
              <b-link target="_blank" :href="plant.infoUrl"
                >Learn more about me here.<b-avatar
                  size="sm"
                  icon="link45deg"
                  class="avatar-icon-link"
                ></b-avatar
              ></b-link>
            </b-card-text>
          </b-card-body>
        </b-col>
      </b-row>
      <b-card-footer>
        <b-row align-v="center">
          <b-col class="text-center">
            <b-button
              id="plant"
              class="card-footer-btn"
              size="sm"
              @click="useCloudinary($event, plant)"
              >{{ plant.plantImg === null ? "Add photo" : "Swap photo" }}
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
              :disabled="isEditingPlant"
              @click="$emit('toggleEdit', plant)"
              v-b-toggle.collapse-edit-form
              >Edit plant
              <b-avatar
                size="sm"
                icon="pencil-fill"
                class="avatar-icon-pencil"
              ></b-avatar
            ></b-button>
          </b-col>
          <b-col>
            <b-button
              id="deletePlant"
              class="card-footer-btn"
              v-b-tooltip
              title="Delete this plant"
              size="sm"
              @click="$emit('deletePlant')"
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
</template>

<script>
import photoService from "../services/PhotoService";
import Vue from 'vue';
import { CollapsePlugin, VBTooltip, CardPlugin, LinkPlugin } from 'bootstrap-vue';
export default {
  name: "plant-card",
  props: ["plantId", "isEditingPlant"],
  data() {
    return {};
  },
  computed: {
    plants() {
      return this.$store.state.plants;
    },
    plant() {
      return this.plants.find((p) => p.plantId == this.plantId);
    },
    name() {
      return this.$store.state.profile.displayName === undefined ||
        this.$store.state.profile.displayName === null
        ? this.$store.state.user.username
        : this.$store.state.profile.displayName;
    },
  },
  methods: {
    addSchedule() {
      return "Add a custom schedule.";
    },
    determineSchedule(plant) {
      let result = "";
      if (plant.plantSchedule % 30 === 0) {
        plant.plantSchedule / 30 > 1
          ? (result = plant.plantSchedule / 30 + " months")
          : (result = " month");
      } else if (plant.plantSchedule % 7 === 0) {
        plant.plantSchedule / 7 > 1
          ? (result = plant.plantSchedule / 7 + " weeks")
          : (result = " week");
      } else if (plant.plantSchedule > 1) {
        result = plant.plantSchedule + " days";
      } else {
        result = " day";
      }
      return result;
    },
    formatDate(date) {
      const options = {
        year: "numeric",
        month: "long",
        day: "numeric",
      };
      return new Date(date).toLocaleDateString("en-US", options);
    },
    useCloudinary(event, item) {
      photoService.myWidget.open();
      this.$store.commit("SET_CLOUDINARY_SOURCE", event.target.id);
      this.$store.commit("SET_OBJECT", item);
    },
    selectPlantImg(plantImg) {
      return plantImg === null
        ? require("@/assets/CandaceStone_Pixabay400.png")
        : plantImg;
    },
  },
  created() {
    Vue.use(CollapsePlugin);
    Vue.use(CardPlugin);
    Vue.use(LinkPlugin);
    Vue.directive('b-tooltip', VBTooltip);
  }
};
</script>

<style>
.plant-preference {
  margin-top: 1rem;
}
</style>