<template>
  <b-container fluid id="plant-care">
    <b-row align-h="center">
      <p class="section-header">Recent treatments</p>
    </b-row>

    <!-- TODO: let user bulk delete care treatments by date? -->

    <p v-if="this.$store.state.treatments.length == 0">
      You have not logged any treatments.
    </p>

    <b-list-group
      v-for="uniqueDate in formatTreatment()"
      v-bind:key="uniqueDate.itemId"
    >
      <p class="subsection-header">
        {{ formatDateDay(uniqueDate.replace(/-/g, "\/")) }}
      </p>

      <!-- WATERING GROUP -->
      <b-container>
        <b-list-group
          v-bind:treatment="treatment"
          v-for="treatment in waterings"
          v-bind:key="treatment.treatmentId"
          v-show="uniqueDate == treatment.careDate"
        >
          <b-list-group-item
            class="d-flex justify-content-between align-items-center"
          >
            <span>
              <b-avatar
                class="avatar-custom"
                v-bind:id="treatment.careType"
                v-bind:src="selectImg(treatment.careType)"
              ></b-avatar>

              {{
                treatment.careType.substring(0, 1).toUpperCase() +
                treatment.careType.substring(1)
              }}
              <router-link
                :to="{
                  name: 'plant-detail',
                  params: { plantId: treatment.plantId },
                }"
              >
                {{
                  uniqueDate === treatment.careDate ? treatment.plantName : " "
                }}
              </router-link>
            </span>
            <b-avatar
              v-b-tooltip.hover
              title="Delete treatment"
              icon="trash-fill"
              id="deleteTreatment"
              size="sm"
              v-on:click="deleteTreatment(treatment)"
              href="#"
            ></b-avatar>
          </b-list-group-item>
        </b-list-group>
      </b-container>

      <!-- MISTED GROUP -->
      <b-container>
        <b-list-group
          v-bind:treatment="treatment"
          v-for="treatment in mistings"
          v-bind:key="treatment.treatmentId"
          v-show="uniqueDate == treatment.careDate"
        >
          <b-list-group-item
            class="d-flex justify-content-between align-items-center"
          >
            <span>
              <b-avatar
                class="avatar-custom"
                v-bind:id="treatment.careType"
                v-bind:src="selectImg(treatment.careType)"
              ></b-avatar>

              {{
                treatment.careType.substring(0, 1).toUpperCase() +
                treatment.careType.substring(1)
              }}
              <router-link
                :to="{
                  name: 'plant-detail',
                  params: { plantId: treatment.plantId },
                }"
              >
                {{
                  uniqueDate === treatment.careDate ? treatment.plantName : " "
                }}
              </router-link>
            </span>
            <b-avatar
              v-b-tooltip.hover
              title="Delete treatment"
              icon="trash-fill"
              id="deleteTreatment"
              size="sm"
              v-on:click="deleteTreatment(treatment)"
              href="#"
            ></b-avatar>
          </b-list-group-item>
        </b-list-group>
      </b-container>

      <!-- FERTILIZED GROUP -->
      <b-container>
        <b-list-group
          v-bind:treatment="treatment"
          v-for="treatment in fertilized"
          v-bind:key="treatment.treatmentId"
          v-show="uniqueDate == treatment.careDate"
        >
          <b-list-group-item
            class="d-flex justify-content-between align-items-center"
          >
            <span>
              <b-avatar
                class="avatar-custom"
                v-bind:id="treatment.careType"
                v-bind:src="selectImg(treatment.careType)"
              ></b-avatar>

              {{
                treatment.careType.substring(0, 1).toUpperCase() +
                treatment.careType.substring(1)
              }}
              <router-link
                :to="{
                  name: 'plant-detail',
                  params: { plantId: treatment.plantId },
                }"
              >
                {{
                  uniqueDate === treatment.careDate ? treatment.plantName : " "
                }}
              </router-link>
            </span>
            <b-avatar
              v-b-tooltip.hover
              title="Delete treatment"
              icon="trash-fill"
              id="deleteTreatment"
              size="sm"
              v-on:click="deleteTreatment(treatment)"
              href="#"
            ></b-avatar>
          </b-list-group-item>
        </b-list-group>
      </b-container>

      <!-- PEST GROUP -->
      <b-container>
        <b-list-group
          v-bind:treatment="treatment"
          v-for="treatment in debugged"
          v-bind:key="treatment.treatmentId"
          v-show="uniqueDate == treatment.careDate"
        >
          <b-list-group-item
            class="d-flex justify-content-between align-items-center"
          >
            <span>
              <b-avatar
                class="avatar-custom"
                v-bind:id="treatment.careType"
                v-bind:src="selectImg(treatment.careType)"
              ></b-avatar>

              {{
                treatment.careType.substring(0, 1).toUpperCase() +
                treatment.careType.substring(1)
              }}
              <router-link
                :to="{
                  name: 'plant-detail',
                  params: { plantId: treatment.plantId },
                }"
              >
                {{
                  uniqueDate === treatment.careDate ? treatment.plantName : " "
                }}
              </router-link>
            </span>
            <b-avatar
              v-b-tooltip.hover
              title="Delete treatment"
              icon="trash-fill"
              id="deleteTreatment"
              size="sm"
              v-on:click="deleteTreatment(treatment)"
              href="#"
            ></b-avatar>
          </b-list-group-item>
        </b-list-group>
      </b-container>

      <!-- REPOTTED GROUP -->
      <b-container>
        <b-list-group
          v-bind:treatment="treatment"
          v-for="treatment in repotted"
          v-bind:key="treatment.treatmentId"
          v-show="uniqueDate == treatment.careDate"
        >
          <b-list-group-item
            class="d-flex justify-content-between align-items-center"
          >
            <span>
              <b-avatar
                class="avatar-custom"
                v-bind:id="treatment.careType"
                v-bind:src="selectImg(treatment.careType)"
              ></b-avatar>

              {{
                treatment.careType.substring(0, 1).toUpperCase() +
                treatment.careType.substring(1)
              }}
              <router-link
                :to="{
                  name: 'plant-detail',
                  params: { plantId: treatment.plantId },
                }"
              >
                {{
                  uniqueDate === treatment.careDate ? treatment.plantName : " "
                }}
              </router-link>
            </span>
            <b-avatar
              icon="trash-fill"
              id="deleteTreatment"
              size="sm"
              v-on:click="deleteTreatment(treatment)"
              href="#"
            ></b-avatar>
          </b-list-group-item>
        </b-list-group>
      </b-container>
    </b-list-group>
  </b-container>
</template>

<script>
import treatmentService from "../services/TreatmentService";
export default {
  name: "care",
  computed: {
    treatments() {
      return this.$store.state.treatments;
    },
    waterings() {
      return this.treatments.filter(
        (treatment) => treatment.careType == "watered"
      );
    },
    mistings() {
      return this.treatments.filter(
        (treatment) => treatment.careType == "misted"
      );
    },
    fertilized() {
      return this.treatments.filter(
        (treatment) => treatment.careType == "fertilized"
      );
    },
    debugged() {
      return this.treatments.filter(
        (treatment) => treatment.careType == "pest-treated"
      );
    },
    repotted() {
      return this.treatments.filter(
        (treatment) => treatment.careType == "repotted"
      );
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
    selectImg(careType) {
      return careType === "watered"
        ? require("@/assets/watering-can.png")
        : careType === "misted"
        ? require("@/assets/spray-bottle.png")
        : careType === "repotted"
        ? require("@/assets/plant-pot.png")
        : careType === "fertilized"
        ? require("@/assets/eyedropper.png")
        : require("@/assets/bug.png");
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
  mounted() {
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