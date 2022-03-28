<template>
  <div id="plant-view">
    <h1>{{ this.$store.state.user.username }}&#8217;s plants</h1>
    <p>
      This page lists all plants associated with user. Features to add: sort by
      indoor/outdoor; select plants to record that you watered them, defaulting
      to today but with the option to select a different date. List could show
      thumbnail of each plant at left (in circle) or a placeholder image if no
      photo has been uploaded -- including for plants just added, for example.
    </p>
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
      <a v-on:click.prevent="toggleForm(plant)">{{
        showForm === true ? "cancel" : "edit"
      }}</a>
      |
      {{ plant.indoor == "true" ? "indoor" : "outdoor" }}
    </div>
    <div id="form-container">
      <form
        v-on:submit.prevent="editPlant(newPlant)"
        v-show="showForm === true"
      >
        <label for="newName">New name:</label>
        <input type="text" v-model="newPlant.plantName" />
        <input
          type="radio"
          name="indoor"
          value="true"
          v-model="newPlant.indoor"
        />
        <label for="indoor">Indoor</label>
        <input
          type="radio"
          name="indoor"
          value="false"
          v-model="newPlant.indoor"
        />
        <label for="outdoor">Outdoor</label>
        <button id="submit">Save</button>
      </form>
    </div>
    <button v-bind:disabled="btnDisabled" v-on:click="logAction" id="submit">
      Watered
    </button>
    <h1>Add a plant</h1>
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
      newPlant: {},
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
    toggleForm(plant) {
      this.newPlant = plant;
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
          this.plant = {
            userId: this.$store.state.user.id,
          };
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
    editPlant(newPlant) {
      plantService
        .editPlant(newPlant)
        .then((response) => {
          if (response.status == 200) {
            this.$store.commit("EDIT_PLANT", newPlant);
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