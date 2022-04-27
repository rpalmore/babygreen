<template>
  <b-container id="add-plant">
    <h3>Add a plant</h3>
    <form v-on:submit.prevent="addPlant" id="plant-form">
      <label for="plantName">Name of plant:</label>
      <input type="text" class="plant-form" v-model="plant.plantName" />
      <input
        required
        type="radio"
        name="indoor"
        value="true"
        v-model="plant.indoor"
      />
      <label for="indoor">Indoor</label>
      <input
        required
        type="radio"
        name="indoor"
        value="false"
        v-model="plant.indoor"
      />
      <label for="outdoor">Outdoor</label>
      <button id="submit">Save</button>
    </form>
  </b-container>
</template>

<script>
import plantService from "../services/PlantService";
export default {
  name: "add-plant",
  data() {
    return {
      plant: {
        userId: this.$store.state.user.id,
      },
    };
  },
  methods: {
    addPlant() {
      plantService
        .createPlant(this.plant)
        .then((response) => {
          if (response.status == 201) {
            this.$store.commit("ADD_PLANT", response.data);
          }
          this.plant = {
            userId: this.$store.state.user.id,
          };
        })
        .catch((err) => {
          alert(err + " problem creating plant!");
        });
    },
  },
};
</script>

<style>
</style>