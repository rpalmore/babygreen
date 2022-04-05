<template>
  <div id="plant-view">
    <h2>
      {{
        this.$store.state.profile.displayName === undefined
          ? this.$store.state.user.username
          : this.$store.state.profile.displayName
      }}&#8217;s plants
    </h2>
    <p>
      This page lists all plants associated with user. Features to add: sort by
      indoor/outdoor; select plants to record that you watered them, defaulting
      to today but with the option to select a different date. List could show
      thumbnail of each plant at left (in circle) or a placeholder image if no
      photo has been uploaded -- including for plants just added, for example.
    </p>
    <p>To do: Filter by indoor/outdoor.</p>
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
      {{ plant.indoor == true ? "indoor" : "outdoor" }}
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
    <form v-on:submit.prevent="logCare">
      <label for="watered">Watered</label>
      <input
        v-bind:disabled="btnDisabled"
        required
        type="radio"
        name="type"
        id="watered"
        value="watered"
        v-model="treatment.careType"
      />
      <label for="sprayed">Sprayed</label>
      <input
        v-bind:disabled="btnDisabled"
        required
        type="radio"
        name="type"
        id="sprayed"
        value="sprayed"
        v-model="treatment.careType"
      />
      <label for="repotted">Repotted</label>
      <input
        v-bind:disabled="btnDisabled"
        required
        type="radio"
        name="type"
        id="repotted"
        value="repotted"
        v-model="treatment.careType"
      />
      <label for="treated">Pest Treatment</label>
      <input
        v-bind:disabled="btnDisabled"
        required
        type="radio"
        name="type"
        id="pest-treatment"
        value="pest-treatment"
        v-model="treatment.careType"
      />
      <label for="treated">Fertilized</label>
      <input
        v-bind:disabled="btnDisabled"
        required
        type="radio"
        name="type"
        id="fertilized"
        value="fertilized"
        v-model="treatment.careType"
      />
      <p>
        <label for="careDate">Date of care: </label>
        <input
          v-bind:disabled="btnDisabled"
          required
          name="careDate"
          id="careDate"
          type="date"
          v-model="treatment.careDate"
        />
      </p>
      <button id="submit">Log</button>
    </form>
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
  </div>
</template>

<script>
import plantService from "../services/PlantService";
import treatmentService from "../services/TreatmentService";
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
      treatment: {
        plantId: [],
      },
      modal: '',
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
    logCare() {
      this.treatment.plantId = this.selectedPlantIds;
      treatmentService
        .createTreatment(this.treatment)
        .then((response) => {
          if (response.status == 201) {
            this.$store.commit("ADD_TREATMENT", response.data);
          }
          this.selectedPlantIds = [];
          this.checkAll = false;
          this.treatment = {};
        })
        .catch((err) => {
          alert(err + " problem creating treatment!");
        });
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
      this.modal = '';
      this.$bvModal
        .msgBoxConfirm("Are you sure you want to delete this plant?")
        .then((value) => {
          this.modal = value;
          if (value === true) {
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
        });
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