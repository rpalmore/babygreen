<template>
  <div id="plant-view">
    <h1>My plants</h1>
    <div
      v-bind:plant="plant"
      v-for="plant in plants"
      v-bind:key="plant.plantId"
    >
      {{ plant.plantName }}
      <a v-on:click="deletePlant(plant.plantId)">&#10006;</a> | 
      <a v-on:click.prevent="showForm = true">edit</a>
      <form v-on:submit.prevent="editPlant(plant)" v-show="showForm === true">
        <label for="newName">New name:</label>
        <input type="text" v-model="plant.plantName" />
        <button id="save">Save</button>
      </form>
    </div>
    <h1>Add a plant</h1>
    <form v-on:submit.prevent="addPlant" id="plant-form">
      <label for="plantName">Name of plant:</label>
      <input type="text" class="plant-form" v-model="plant.plantName" />
      <button id="submit">Save</button>
    </form>
  </div>
</template>

<script>
import plantService from "../services/PlantService";
export default {
  name: "plants",
  data() {
    return {
      plant: {
        userId: this.$store.state.user.id,
      },
      showForm: false,
    };
  },
  computed: {
    plants() {
      return this.$store.state.plants;
    },
  },
  methods: {
    addPlant() {
      plantService
        .createPlant(this.plant)
        .then((response) => {
          if (response.status == 201) {
            this.$store.commit("ADD_PLANT", response.data);
          }
          this.plant = {};
        })
        .catch((err) => {
          alert(err + " problem creating plant!");
        });
    },
    deletePlant(plantId) {
      if (confirm("Are you sure you want to delete this plant?")) {
        plantService
          .deletePlant(plantId)
          .then((response) => {
            if (response.status == 204) {
              this.$store.commit("DELETE_PLANT", plantId);
            }
          })
          .catch((err) => {
            alert(err + " problem deleting plant!");
          });
      }
    },
    editPlant(plant) {
        // eslint-disable-next-line no-console
        console.log(plant);
      plantService
        .editPlant(plant)
        .then((response) => {
          if (response.status == 200) {
            this.$store.commit("EDIT_PLANT", this.plant);
            this.showForm = false;
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
#plant-form {
  background-color: lightcoral;
}
</style>