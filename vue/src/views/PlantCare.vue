/* eslint-disable no-console */
<template>
  <div id="plant-care">
    <h2>Plant care</h2>
    <p>
      This page should list, by a heading of most recent date (Monday, March
      28), which plants were watered. You can click on any plant in the list to
      go to its detail page, much like the "My plants" page. The purpose of the
      page is to offer a quick snapshot of which plants were last treated.
    </p>
    <h3>Monday, April 4</h3>
    <p>Watered Croton, Gardenia, Rubber tree</p>

    <div
      v-bind:treatment="treatment"
      v-for="treatment in treatments"
      v-bind:key="treatment.treatmentId"
    >
      {{ treatment.careDate }}
      {{ treatment.careType }}
      <router-link
        :to="{ name: 'plant-detail', params: { plantId: treatment.plantId } }"
      >
        {{ treatment.plantName }}
      </router-link>
      <a v-on:click="deleteTreatment(treatment)">&#10006;</a>
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
  methods: {
    deleteTreatment(treatment) {
      // // eslint-disable-next-line no-console
      // console.log(treatment);
      if(confirm("Are you sure you want to delete this treatment?")) {
        treatmentService.deleteTreatment(treatment.plantId, treatment.careId).then((response) => {
          if (response.status == 204) {
            this.$store.commit("DELETE_TREATMENT", treatment);
            // // eslint-disable-next-line no-console
            // console.log(treatment.plantId);
            // // eslint-disable-next-line no-console
            // console.log(treatment.careId);
          }
        }).catch((err) => {
          alert(err + " problem deleting treatment!")
        });
      }
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