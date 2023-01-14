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
                v-b-tooltip.hover
                title="Edit date"
                v-b-toggle.collapse-date-form
                @click="$emit('toggleDate', plant)"
                >{{ formatDate(plant.plantAge.replace(/-/g, "\/")) }}</a
              >.
            </b-card-text>
            <!-- FORM: edit plantAge -->
            <b-collapse id="collapse-date-form" class="mt-2">
              <b-form @submit.prevent="editPlant()">
                <label class="sr-only" for="plantAge"
                  >Plant cared for since:
                </label>
                <b-form-input
                  type="date"
                  v-model="plant.plantAge"
                ></b-form-input>
                <b-row id="dateFormBtnGroup" no-gutters>
                  <b-button
                    size="sm"
                    id="cancelDateForm"
                    @click="$emit('cancelDateForm', $event, plant)"
                    class="default"
                    >Cancel</b-button
                  >
                  <b-button size="sm" type="submit" class="default"
                    >Update</b-button
                  >
                </b-row>
              </b-form>
            </b-collapse>
            <!-- end plant age edit form -->
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
              v-b-tooltip.hover
              title="Delete this plant"
              class="card-footer-btn"
              size="sm"
              @click="deletePlant()"
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
export default {
  name: "plant-card",
  props: ["plantId", "isEditingPlant"],
  data() {
    return {
      savedDate: "",
    };
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
        ? require("@/assets/CandaceStone_Pixabay.png")
        : plantImg;
    },
  },
};
</script>

<style>
</style>