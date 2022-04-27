<template>
  <b-container id="log-care">
    <b-form @submit.prevent="logCare">
      <b-form-group label="Select a treatment" v-slot="{ ariaDescribedby }">
        <b-form-radio-group
          v-bind:disabled="btnDisabled"
          v-model="treatment.careType"
          :options="options"
          :aria-describedby="ariaDescribedby"
          name="radio-inline"
        ></b-form-radio-group>
      </b-form-group>

      <!-- <label for="care-datepicker">Date of care:</label>
      <b-form-datepicker
        v-bind:disabled="btnDisabled"
        name="careDate"
        id="care-datepicker"
        v-model="treatment.careDate"
        class="mb-2"
      ></b-form-datepicker> -->

      <label for="careDate">Date of care: </label>
        <input
          v-bind:disabled="btnDisabled"
          required
          name="careDate"
          id="careDate"
          type="date"
          v-model="treatment.careDate"
        />

      <button v-bind:disabled="btnDisabled" id="submit">Log</button>
    </b-form>
  </b-container>
</template>

<script>
import treatmentService from "../services/TreatmentService";
export default {
  name: "log-care",
  props: ["selectedPlantIds"],
  data() {
    return {
      treatment: {
        plantId: [],
      },
      selected: "",
      options: [
        { text: "Watered", value: "watered" },
        { text: "Sprayed", value: "sprayed" },
        { text: "Repotted", value: "repotted" },
        { text: "Pest-treated", value: "pest-treated" },
        { text: "Fertilized", value: "fertilized" },
      ],
    };
  },
  computed: {
    btnDisabled() {
      return this.selectedPlantIds.length === 0 ? true : false;
    },
  },
  methods: {
    // btnDisabled() {
    //   return this.selectedPlantIds.length === 0 ? true : false;
    // },
    logCare() {
      this.treatment.plantId = this.selectedPlantIds;
      this.$emit("form-sent");
      treatmentService
        .createTreatment(this.treatment)
        .then((response) => {
          if (response.status == 201) {
            this.$store.commit("ADD_TREATMENT", response.data);
          }
          this.treatment = {};
        })
        .catch((err) => {
          alert(err + " problem creating treatment!");
        });
    },
  },
};
</script>

<style>
</style>