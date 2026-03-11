<template>
  <b-container fluid id="log-care-container">
    <b-row>
      <b-col>
        <b-form class="log-care-form" @submit.prevent="logCare">
          <label>Select a treatment:</label>
          <b-form-radio-group stacked required v-model="treatment.careType" :options="options"
            :aria-describedby="ariaDescribedby" name="radio-inline">
          </b-form-radio-group>
          <label class="sr-only" for="careDate">Date</label>
          <b-form-input required class="form-gap" name="careDate" id="careDate" type="date" v-model="myDate">
          </b-form-input>
          <b-button size="sm" v-bind:disabled="btnDisabled" id="logCareBtn" class="default" type="submit">Log</b-button>
        </b-form>
      </b-col>
      <b-col>
        <b-form class="log-care-form">
          <label>Show:</label>
          <b-form-radio-group stacked v-model="selected" :options="types"
            @change="handleRadioChange"></b-form-radio-group>
        </b-form>
      </b-col>
    </b-row>
    <ErrorModal />
  </b-container>
</template>

<script>
import treatmentService from "../services/TreatmentService";
import ErrorModal from "./ErrorModal.vue";
export default {
  name: "log-care",
  components: { ErrorModal },
  props: ["selectedPlantIds"],
  data() {
    return {
      treatment: {
        plantId: [],
      },
      options: [
        { text: "Watered", value: "watered" },
        { text: "Misted", value: "misted" },
        { text: "Repotted", value: "repotted" },
        { text: "Pest-treated", value: "pest-treated" },
        { text: "Fertilized", value: "fertilized" },
      ],
      selected: 'all',
      types: [
        { text: "All plants", value: "all" },
        { text: "Indoor plants", value: "indoor" },
        { text: "Outdoor plants", value: "outdoor" },
        { text: "Landscape plants", value: "landscape" }
      ],
      myDate: new Date(Date.now() - new Date().getTimezoneOffset() * 60000).toISOString().slice(0, 10)
    };
  },
  computed: {
    btnDisabled() {
      return this.selectedPlantIds.length === 0 ? true : false;
    },
  },
  methods: {
    formatDateDay(date) {
      const options = {
        weekday: "long",
        year: "numeric",
        month: "long",
        day: "numeric",
      };
      return new Date(date).toLocaleDateString("en-US", options);
    },
    checkForDuplicates() {
      const dateAndType = this.$store.state.treatments.filter(x => x.careDate === this.treatment.careDate && x.careType === this.treatment.careType);
      if (dateAndType.length > 0) {
        let indexesToCut = [];
        for (let i = 0; i < this.treatment.plantId.length; i++) {
          const duplicate = dateAndType.find(x => x.plantId === this.treatment.plantId[i]);
          if (duplicate) {
            const index = this.treatment.plantId.indexOf(duplicate.plantId);
            indexesToCut.push(index);
          }
        }
        return this.treatment.plantId = this.treatment.plantId.filter((value, index) => !indexesToCut.includes(index));
      }
    },
    logCare() {
      this.treatment.careDate = this.myDate;
      this.treatment.plantId = this.selectedPlantIds;
      this.$emit("form-sent");
      this.checkForDuplicates(this.treatment);
      if (this.treatment.plantId.length > 0) {
        treatmentService
          .createTreatment(this.treatment)
          .then((response) => {
            if (response.status == 201) {
              let newTreatment = response.data;
              for (let i = 0; i < newTreatment.plantId.length; i++) {
                let payload =
                {
                  careDate: newTreatment.careDate,
                  careType: newTreatment.careType,
                  careId: newTreatment.careId,
                  plantId: newTreatment.plantId[i],

                };
                this.$store.commit("ADD_TREATMENT", payload);
              }
              let addedTreatment = response.data;
              if (addedTreatment.careType === "watered") {
                for (let i = 0; i < addedTreatment.plantId.length; i++) {
                  let payload = [
                    {
                      careDate: addedTreatment.careDate,
                      careType: "watered",
                      plantId: addedTreatment.plantId[i],
                    },
                  ];
                  let idExists = this.$store.state.latestWatering.some(
                    (plant) => plant.plantId == addedTreatment.plantId[i]
                  );
                  if (idExists) {
                    let existingPlant = this.$store.state.latestWatering.find(
                      (e) => e.plantId == addedTreatment.plantId[i]
                    );
                    let dateValid =
                      existingPlant.careDate < addedTreatment.careDate;
                    if (dateValid) {
                      this.$store.commit("UPDATE_LATEST_WATERING", payload[0]);
                    }
                  } else {
                    this.$store.commit("ADD_LATEST_WATERING", payload[0]);
                  }
                }
              }
              this.treatment = {};
            }
          })
          .catch((err) => {
            this.$bvModal.show('error-modal');
            /* eslint no-console: ["error", { allow: ["error"] }] */
            console.error(err + " problem creating treatment!");
          });
      }
      this.treatment = {};
    },
    handleRadioChange(value) {
      this.$emit("type-changed", value);
    }
  },
};
</script>

<style>
#log-care-container {
  margin: 1rem 0;
  background-color: var(--light-shade1);
  border-left: 5px solid var(--green);
}

.log-care-form {
  padding: 15px 0 15px 0;
}

#logCareBtn,
.form-gap {
  margin-top: 0.5rem;
}
</style>