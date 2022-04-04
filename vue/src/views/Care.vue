<template>
  <div id="plant-care">
    <h2>Plant care</h2>
    <p>
      This page should list, by a heading of most recent date (Monday, March
      28), which plants were watered. You can click on any plant in the list to
      go to its detail page, much like the "My plants" page. The purpose of the
      page is to offer a quick snapshot of which plants were last treated.
    </p>
    <div
      v-bind:treatment="treatment"
      v-for="treatment in treatments"
      v-bind:key="treatment.treatmentId"
    >
      {{ treatment.careDate }}
      {{ treatment.careType }}
      {{ treatment.plantName }}

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
  methods: {},
  created() {
    treatmentService
      .getAllTreatments()
      .then((response) => {
        if (response.status == 200) {
          this.$store.commit("SET_TREATMENTS", response.data);
          // eslint-disable-next-line no-console
          console.log(response.data);
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