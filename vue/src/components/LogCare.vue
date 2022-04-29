<template>
  <b-container id="log-care">
    <b-form @submit.prevent="logCare">
      <b-form-group label="Select a treatment:" v-slot="{ ariaDescribedby }">
        <b-form-radio-group
          required
          v-bind:disabled="btnDisabled"
          v-model="treatment.careType"
          :options="options"
          :aria-describedby="ariaDescribedby"
          name="radio-inline"
        ></b-form-radio-group>
      </b-form-group>
      <b-form-group label="Date of care:">
        <b-row align-v="center">
          <b-col sm="10">
            <b-form-input
              v-bind:disabled="btnDisabled"
              required
              name="careDate"
              id="careDate"
              type="date"
              v-model="treatment.careDate"
            >
            </b-form-input>
          </b-col>
          <b-col sm="2">
            <b-button
              size="sm"
              v-bind:disabled="btnDisabled"
              id="logCare"
              class="default"
              type="submit"
              >Log</b-button
            >
          </b-col>
        </b-row>
      </b-form-group>
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

            let addedTreatment = response.data;
            if (addedTreatment.careType === "watered") {
              
              for (let i = 0; i < addedTreatment.plantId.length; i++) {
                let payload = [
                  {
                    careDate: addedTreatment.careDate,
                    careType: "watered",
                    plantName: "", // I don't think I need to include the plant name
                    plantId: addedTreatment.plantId[i],
                    careId: 0,
                  },
                ];
                let idExists = this.$store.state.latestWatering.some(
                  (plant) => plant.plantId == addedTreatment.plantId[i]
                );

                if (idExists) {
                  // AND if date is greater than existing date???
                  // eslint-disable-next-line no-console
                  console.log("yes");
                  this.$store.commit("UPDATE_LATEST_WATERING", payload[0]);
                  
                } else {
                  // eslint-disable-next-line no-console
                  console.log("no");
                  this.$store.commit("ADD_LATEST_WATERING", payload[0]);
                }
              }
            }
            this.treatment = {};
          }
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