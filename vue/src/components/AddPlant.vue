<template>
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
    <b-button size="sm" id="cancel" @click="cancel" class="default"
      >Cancel</b-button
    >
    <b-button size="sm" type="submit" class="default">Save</b-button>
  </b-form>
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
    cancel() {
      this.plant = {
        userId: this.$store.state.user.id,
      };
      this.$root.$emit('bv::toggle::collapse', 'collapse-form')
    },
  },
};
</script>

<style>
#add-plant-form {
  margin-bottom: 1rem;
}
</style>