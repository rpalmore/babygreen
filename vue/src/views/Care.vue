<template>
  <b-container fluid id="plant-care">
    <b-row align-h="center">
      <p class="section-header">Recent treatments</p>
    </b-row>

    <p class="default-message" v-if="this.$store.state.treatments.length == 0">
      You have not logged any treatments. Start by adding a plant!
    </p>

    <b-list-group
      v-for="uniqueDate in formatTreatment().slice(0,5)"
      v-bind:key="uniqueDate.itemId"
    >
      <p class="subsection-header">
        {{ formatDateDay(uniqueDate.replace(/-/g, "\/")) }}
      </p>
      <b-container class="list-container">
        <b-list-group
          v-bind:treatment="treatment"
          v-for="treatment in treatments"
          v-bind:key="treatment.treatmentId"
        >
          <b-list-group-item v-if="uniqueDate == treatment.careDate" class="d-flex align-items-center">
            <b-col>
              <b-avatar
                class="avatar-custom"
                v-bind:id="treatment.careType"
                v-bind:src="selectImg(treatment.careType)"
              ></b-avatar>
            </b-col>
            <b-col>
              {{
                treatment.careType.substring(0, 1).toUpperCase() +
                treatment.careType.substring(1)
              }}
              <b-link
                :to="{
                  name: 'plant-detail',
                  params: { plantId: treatment.plantId },
                }"
              >
                {{
                  treatment.plantName
                }}
              </b-link>
            </b-col>
            <b-col class="delete">
              <b-avatar
                button
                icon="trash-fill"
                id="deleteTreatment"
                v-b-tooltip
                title="Delete treatment"
                size="sm"
                @click="deleteTreatment(treatment)"
              ></b-avatar>
            </b-col>
          </b-list-group-item>
        </b-list-group>
      </b-container>
    </b-list-group>
     <a v-b-toggle href="#treatment-dates" @click.prevent>
      <p class="subsection-header" v-if="formatTreatment().length > 5">
        <span class="when-closed">See </span>
        <span class="when-open">Hide </span>
        <b-badge id="treatmentDateCount">{{ formatTreatment().length - 5 }}</b-badge> 
        more treatment dates and details.
      </p>
     </a>
    <b-collapse id="treatment-dates" class="mt-2">
      <b-list-group
      v-for="uniqueDate in formatTreatment().slice(5)"
      v-bind:key="uniqueDate.itemId"
    >
      <p class="subsection-header additional-treatments">
        {{ formatDateDay(uniqueDate.replace(/-/g, "\/")) }}
              <b-avatar
                button
                icon="trash-fill"
                id="deleteTreatmentByDate"
                v-b-tooltip
                title="Delete all treatments by date"
                size="sm"
                @click="deleteTreatmentByDate(uniqueDate)"
              ></b-avatar>
      </p>
      <b-container class="list-container">
        <b-list-group
          v-bind:treatment="treatment"
          v-for="treatment in treatments"
          v-bind:key="treatment.treatmentId"
        >
          <b-list-group-item v-if="uniqueDate == treatment.careDate" class="d-flex align-items-center">
            <b-col>
              <b-avatar
                class="avatar-custom"
                v-bind:id="treatment.careType"
                v-bind:src="selectImg(treatment.careType)"
              ></b-avatar>
            </b-col>
            <b-col>
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
                  treatment.plantName
                }}
              </router-link>
            </b-col>
            <b-col class="delete">
             <b-avatar
                button
                v-b-tooltip
                title="Delete treatment"
                icon="trash-fill"
                id="deleteTreatment"
                size="sm"
                @click="deleteTreatment(treatment)"
              ></b-avatar>
            </b-col>
          </b-list-group-item>
        </b-list-group>
      </b-container>
    </b-collapse>
  </b-container>
</template>

<script>
import treatmentService from "../services/TreatmentService";
import Vue from 'vue';
import { BadgePlugin, CollapsePlugin, ModalPlugin, VBTooltip, LinkPlugin } from 'bootstrap-vue';
export default {
  name: "care",
  components: {},
  data() {
    return {
      modal: "",
    };
  },
  computed: {
    treatments() {
      return this.$store.state.treatments;
    },
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
                /* eslint no-console: ["error", { allow: ["error"] }] */
                console.error(err + " problem deleting treatment!");
                this.$router.push("/oops");
              });
          }
        });
    },
    deleteTreatmentByDate(date) {
      this.modal = "";
      this.$bvModal
        .msgBoxConfirm(
          "Are you sure you want to delete all treatments on this date?"
        )
        .then((value) => {
          this.modal = value;
          if (value === true) {
            treatmentService
              .deleteTreatmentByDate(date)
              .then((response) => {
                if (response.status == 204) {
                  this.$store.commit("DELETE_TREATMENT_BY_DATE", date);
                }
              })
              .catch((err) => {
                /* eslint no-console: ["error", { allow: ["error"] }] */
                console.error(err + " problem deleting treatment!");
                this.$router.push("/oops");
              });
          }
        });
    },
  },
  created() {
    Vue.use(BadgePlugin);
    Vue.use(CollapsePlugin);
    Vue.use(ModalPlugin);
    Vue.use(LinkPlugin);
    Vue.directive('b-tooltip', VBTooltip);
    treatmentService
      .getAllTreatments()
      .then((response) => {
        if (response.status == 200) {
          this.$store.commit("SET_TREATMENTS", response.data);
        }
      })
      .catch((err) => {
        /* eslint no-console: ["error", { allow: ["error"] }] */
        console.error(err + " problem deleting treatment!");
        this.$router.push("/oops");
      });
  },
};
</script>

<style>
#treatmentDateCount {
  background-color: var(--orange);
}
.treatmentDetails {
  width: 90%;
}
#deleteTreatment {
  background-color: var(--orange);
}
#deleteTreatmentByDate {
  background-color: var(--grey);
  border: 1px solid var(--orange);
  margin-right: 2%;
}
.additional-treatments {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
.collapsed .when-open,
.not-collapsed .when-closed {
  display: none;
}
.avatar-custom#misted {
  background-color: var(--light);
  border: 1px solid var(--orange);
}
.avatar-custom#pest-treated {
  background-color: var(--orange);
  border: 1px solid var(--green);
}
.avatar-custom#fertilized {
  background-color: var(--yellow);
  border: 1px solid var(--green);
}
.avatar-custom#repotted {
  background-color: var(--platinum);
  border: 1px solid var(--green);
}
</style>