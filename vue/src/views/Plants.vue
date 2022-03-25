<template>
  <div id="plant-view">
    <h1>My plants</h1>
    <p id="select-all">
      <input type="checkbox" v-model="checkAll" v-on:change="selectAll" />
      Select all
    </p>
    <div
      v-bind:plant="plant"
      v-for="plant in plants"
      v-bind:key="plant.plantId"
    >
      <input
        type="checkbox"
        v-bind:value="plant.plantId"
        v-model="selectedPlantIds"
      />
      <router-link
        :to="{ name: 'plant-detail', params: { plantId: plant.plantId } }"
      >
        {{ plant.plantName }}
      </router-link>
      <a v-on:click="deletePlant(plant.plantId)">&#10006;</a> |
      <a v-on:click.prevent="toggleForm">{{
        showForm === true ? "cancel" : "edit"
      }}</a> |
      {{ plant.indoor === true ? "indoor" : "outdoor" }}
      <form v-on:submit.prevent="editPlant(plant)" v-show="showForm === true">
        <label for="newName">New name:</label>
        <input type="text" v-model="plant.plantName" />
        <input type="radio" name="indoor" value="true" v-model="plant.indoor" />
        <label for="indoor">Indoor</label>
        <input
          type="radio"
          name="indoor"
          value="false"
          v-model="plant.indoor"
        />
        <label for="indoor">Outdoor</label>
        <button id="save">Save</button>
      </form>
    </div>
    <button v-bind:disabled="btnDisabled" v-on:click="logAction" id="submit">
      Watered
    </button>
    <h1>Add a plant</h1>
    <form v-on:submit.prevent="addPlant" id="plant-form">
      <label for="plantName">Name of plant:</label>
      <input type="text" class="plant-form" v-model="plant.plantName" />
      <input required type="radio" name="indoor" value="true" v-model="plant.indoor" />
      <label for="indoor">Indoor</label>
      <input required type="radio" name="indoor" value="false" v-model="plant.indoor" />
      <label for="indoor">Outdoor</label>
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
      selectedPlantIds: [],
      checkAll: false,
    };
  },
  computed: {
    plants() {
      return this.$store.state.plants;
    },
    btnDisabled() {
      if (this.selectedPlantIds.length === 0) {
        return true;
      } else {
        return false;
      }
    },
  },
  methods: {
    logAction() {
      alert("Watered " + this.selectedPlantIds.length + " plants!");
      // todo: write method for getting this data back to db
      this.selectedPlantIds = [];
      this.checkAll = false;
    },
    toggleForm() {
      if (this.showForm === true) {
        this.showForm = false;
      } else {
        this.showForm = true;
      }
    },
    selectAll() {
      if (this.checkAll === true) {
        for (let i = 0; i < this.plants.length; i++) {
          if (!this.selectedPlantIds.includes(this.plants[i].plantId)) {
            this.selectedPlantIds.push(this.plants[i].plantId);
          }
        }
      } else {
        this.selectedPlantIds = [];
        this.checkAll = false;
      }
    },
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