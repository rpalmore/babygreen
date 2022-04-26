/* eslint-disable no-console */
<template>
  <div id="plant-care">
    <h2>Plant care</h2>

    <!-- <h3>Monday, April 4</h3>
    <p>Watered Croton, Gardenia, Rubber tree</p> -->

    <!-- {{ treatments }} -->

    <div v-for="uniqueDate in formatTreatment()" v-bind:key="uniqueDate.itemId">
      <h4>{{ formatDateDay(uniqueDate.replace(/-/g, "\/")) }}</h4>
      <div v-for="treatment in treatments" v-bind:key="treatment.treatmentId">
        {{
          uniqueDate === treatment.careDate
            ? treatment.careType.substring(0, 1).toUpperCase() +
              treatment.careType.substring(1)
            : " "
        }}
        <router-link
          :to="{ name: 'plant-detail', params: { plantId: treatment.plantId } }"
        >
          {{ uniqueDate === treatment.careDate ? treatment.plantName : " " }}
        </router-link>
        <a
          v-if="uniqueDate === treatment.careDate"
          v-on:click="deleteTreatment(treatment)"
          >&#10006;</a
        >
      </div>
    </div>
  </div>
</template>

<script>
import treatmentService from "../services/TreatmentService";
export default {
  name: "care",
  computed: {
    treatments() {
      return this.$store.state.treatments;
    },
  },
  data() {
    return {
      modal: "",
    };
  },
  methods: {
    formatTreatment() {
      let uniqueDates = [];
      for (let i = 0; i < this.treatments.length; i++) {
        if (!uniqueDates.includes(this.treatments[i].careDate)) {
          uniqueDates.push(this.treatments[i].careDate);
        }
      }
      return uniqueDates;
    },
    formatDateDay(date) {
      const options = {
        weekday: "long",
        year: "numeric",
        month: "long",
        day: "numeric",
      };
      return new Date(date).toLocaleDateString("en-US", options);
    },
    deleteTreatment(treatment) {
      this.modal = "";
      this.$bvModal
        .msgBoxConfirm("Are you sure you want to delete this treatment?")
        .then((value) => {
          this.modal = value;
          if (value === true) {
            treatmentService
              .deleteTreatment(treatment.plantId, treatment.careId)
              .then((response) => {
                if (response.status == 204) {
                  this.$store.commit("DELETE_TREATMENT", treatment);
                }
              })
              .catch((err) => {
                alert(err + " problem deleting treatment!");
              });
          }
        });
    },
  },
  created() {
    treatmentService
      .getAllTreatments()
      .then((response) => {
        if (response.status == 200) {
          this.$store.commit("SET_TREATMENTS", response.data);
        }
      })
      .catch((err) => {
        alert(err + " problem getting treatments!");
      });
  },
};
</script>

<style>
</style>