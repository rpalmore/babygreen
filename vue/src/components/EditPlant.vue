<template>
  <b-row align-h="center">
    <b-form @submit.prevent="editPlant(plant)" id="edit-plant-form">
      <b-form-group>
        <label for="plantName">Plant name:</label>
        <b-form-input
          id="plantName"
          class="mb-2 mr-sm-2 mb-sm-0"
          placeholder="Plant name"
          type="text"
          v-model="plant.plantName"
          required
        ></b-form-input>
      </b-form-group>
      <b-form-group>
        <label for="plantName">Plant cared for since: </label>
        <b-form-input type="date" v-model="plant.plantAge"></b-form-input>
      </b-form-group>
      <b-form-group>
        <label for="plantName">I like to be watered every: </label>
        <b-row id="schedule">
          <b-col>
            <b-form-input
              type="number"
              min="0"
              max="30"
              v-model="plant.plantSchedule"
              placeholder="Select number"
            ></b-form-input>
          </b-col>
          <b-col>
            <b-form-select
              :options="frequency"
              v-model="selectedFrequency"
            ></b-form-select>
          </b-col>
        </b-row>
      </b-form-group>
      <b-form-group>
        <label for="infoUrl">Link to external site:</label>
        <b-form-input
          id="infoUrl"
          class="mb-2 mr-sm-2 mb-sm-0"
          placeholder="Add a link to an external site: https://www.thespruce.com/my-plant"
          type="text"
          v-model="plant.infoUrl"
        ></b-form-input>
      </b-form-group>
      <b-form-group>
        <b-form-radio-group
          required
          v-model="plant.indoor"
          :options="location"
          :aria-describedby="ariaDescribedby"
          name="radio-inline"
        ></b-form-radio-group>
      </b-form-group>
      <b-button
        size="sm"
        id="cancelEditForm"
        @click="$emit('cancelEditForm', $event)"
        class="default"
        >Cancel</b-button
      >
      <b-button size="sm" type="submit" class="default">Update</b-button>
    </b-form>
  </b-row>
</template>

<script>
import plantService from "../services/PlantService";
export default {
  name: "edit-plant",
  data() {
    return {
      location: [
        { text: "Indoor", value: true },
        { text: "Outdoor", value: false },
      ],
      frequency: [
        { text: "Select frequency", value: null },
        { text: "Days", value: 1 },
        { text: "Week", value: 7 },
        { text: "Month", value: 30 },
      ],
      selectedFrequency: 1,
    };
  },
  computed: {
    plants() {
      return this.$store.state.plants;
    },
    plantId() {
      return this.$route.params.plantId;
    },
    plant() {
      return this.plants.find((p) => p.plantId == this.plantId);
    },
    numDays() {
      let result = null;
      this.selectedFrequency != null ? 
      result = this.plant.plantSchedule * this.selectedFrequency 
      : result = this.plant.plantSchedule;
      return result;
    },
  },
  methods: {
    editPlant(plant) {
      plant.plantSchedule = this.numDays;
      if (plant.plantAge === null) {
        let today = new Date();
        const dd = String(today.getDate()).padStart(2, "0");
        const mm = String(today.getMonth() + 1).padStart(2, "0");
        const yyyy = today.getFullYear();
        today = yyyy + "-" + mm + "-" + dd;
        plant.plantAge = today;
      }
      plantService
        .editPlant(plant)
        .then((response) => {
          if (response.status == 200) {
            this.$store.commit("EDIT_PLANT", plant);
            this.$root.$emit("bv::toggle::collapse", "collapse-edit-form");
            this.selectedFrequency = 1;
          }
        })
        .catch((err) => {
          alert(err + " problem editing plant!");
        });
    },
  },
};
</script>

<style>
#edit-plant-form {
  max-width: 640px;
  width: 100%;
  background-color: var(--light-shade1);
  padding: 0.5rem;
}
</style>