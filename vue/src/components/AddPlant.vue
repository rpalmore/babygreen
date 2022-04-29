<template>
  <b-container fluid id="add-plant">
    <label id="add-plant-label">
      {{
        this.$store.state.plants.length == 0
          ? "Let&#8217;s Get Started!"
          : "Add a plant:"
      }}
    </label>
      <b-form inline @submit.prevent="addPlant" id="add-plant-form">
        <label class="sr-only" for="plantName">Plant Name</label>
        <b-form-input
          id="plantName"
          class="mb-2 mr-sm-2 mb-sm-0"
          placeholder="Plant name"
          type="text"
          v-model="plant.plantName"
          required
        ></b-form-input>
        <b-form-radio-group
          required
          v-model="plant.indoor"
          :options="options"
          :aria-describedby="ariaDescribedby"
          name="radio-inline"
        ></b-form-radio-group>
        <b-button size="sm" class="default" type="submit">Save</b-button>
      </b-form>
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
      options: [
        { text: "Indoor", value: true },
        { text: "Outdoor", value: false },
      ],
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
#add-plant {
  margin-bottom: 10px;
}
</style>