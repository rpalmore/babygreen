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
    <!-- <h3>Monday, April 4</h3>
    <p>Watered Croton, Gardenia, Rubber tree</p> -->
    {{ testing() }}

    <!-- <div
      v-bind:treatment="treatment"
      v-for="treatment in treatments"
      v-bind:key="treatment.treatmentId"
    >
      <h3>{{ formatDateDay(treatment.careDate.replace(/-/g, "\/")) }}</h3>
      
      {{ treatment.careType }}
      <router-link
        :to="{ name: 'plant-detail', params: { plantId: treatment.plantId } }"
      >
        {{ treatment.plantName }}
      </router-link>
      {{ treatment.careId }}
      <a v-on:click="deleteTreatment(treatment)">&#10006;</a>
    </div> -->
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
    // plants() {
    //   return this.$store.state.plants;
    // },
    data() {
      return {
        modal: "",
      };
    },
  },
  methods: {
    // trying a method to get the results I want to display
    
    testing() {
      const distinct = [];
      for (let i = 0; i < this.treatments.length -1; i++) {
        if (this.treatments[i].careDate != this.treatments[i+1].careDate) {
          distinct.push(this.treatments[i].careDate)
        }
      }
    

        // eslint-disable-next-line no-console
        console.log(distinct);
 
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
          // eslint-disable-next-line no-console
          console.log(response.data);
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