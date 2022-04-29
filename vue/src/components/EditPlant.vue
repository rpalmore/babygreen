<template>
  <b-container fluid id="edit-plant">
    <!-- <form v-on:submit.prevent="editPlant(newPlant)" v-show="showForm === true"> -->
    <b-form
      v-show="showForm === true"
      inline
      @submit.prevent="editPlant(newPlant)"
      id="edit-plant-form"
    >
      <label for="newName">New name:</label>
      <b-form-input
        id="plantName"
        class="mb-2 mr-sm-2 mb-sm-0"
        type="text"
        v-model="newPlant.plantName"
        required
      ></b-form-input>
      <!-- <input type="text" v-model="newPlant.plantName" /> -->
      <b-form-radio-group
        required
        v-model="newPlant.indoor"
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
  name: "edit-plant",
  data() {
    return {
      newPlant: {},
      options: [
        { text: "Indoor", value: true },
        { text: "Outdoor", value: false },
      ],
    };
  },
  methods: {
    editPlant(newPlant) {
      if (newPlant.plantAge === null) {
        let today = new Date();
        const dd = String(today.getDate()).padStart(2, "0");
        const mm = String(today.getMonth() + 1).padStart(2, "0");
        const yyyy = today.getFullYear();
        today = yyyy + "-" + mm + "-" + dd;
        newPlant.plantAge = today;
      }
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
</style>