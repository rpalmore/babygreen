<template>
  <b-container fluid id="plant-view">
    <b-row align-h="center">
      <p class="section-header">{{ name }}&#8217;s plants</p>
    </b-row>
    <!-- <p>
      <b-button id="toggleFormBtn" size="sm" v-b-toggle.collapse-form
        ><span class="when-open">Close form</span
        ><span class="when-closed">Add a plant</span>
        <b-avatar
          class="avatar-add-plant"
          :src="require('@/assets/leaf.png')"
        ></b-avatar
      ></b-button>
    </p> -->
    <b-collapse id="collapse-form" class="mt-2">
      <AddPlant />
    </b-collapse>

    <!-- plants table -->
    <b-table
      id="plant-table"
      hover
      responsive
      outlined
      sort-icon-left
      :items="combinedDataTables"
      :fields="fields"
    >
      <template #head(selectAll)="data">
        <b-form-checkbox
          v-bind:value="data.checkAll"
          v-model="checkAll"
          v-on:change="selectAll"
        >
        </b-form-checkbox>
      </template>
      <template #cell(selectAll)="data">
        <b-form-checkbox
          v-bind:value="data.item.plantId"
          v-model="selectedPlantIds"
          v-on:change="checkSelectedIds"
        ></b-form-checkbox>
      </template>
      <template #cell(plantName)="data">
        <router-link
          :to="{ name: 'plant-detail', params: { plantId: data.item.plantId } }"
        >
          {{ data.item.plantName }}
        </router-link>
      </template>
      <template #cell(indoor)="data">
        {{ data.item.indoor === true ? "indoor" : "outdoor" }}
      </template>
      <template #cell(careDate)="data">
        {{
          !!data.item.careDate
            ? formatDateDay(data.item.careDate.replace(/-/g, "\/"))
            : ""
        }}
      </template>
      <template #cell(plantImg)="data">
        <b-avatar
          class="avatar-custom"
          size="lg"
          v-bind:src="selectImg(data.item.plantImg)"
        ></b-avatar>
      </template>
    </b-table>
    <LogCare
      v-bind:selectedPlantIds="selectedPlantIds"
      @form-sent="updateSelectedIds"
    />
  </b-container>
</template>

<script>
import AddPlant from "../components/PlantForm.vue";
import LogCare from "../components/LogCare.vue";
import plantService from "../services/PlantService";
import treatmentService from "../services/TreatmentService";
import profileService from "../services/ProfileService";
import { BTable } from 'bootstrap-vue';
export default {
  components: { AddPlant, LogCare, BTable},
  name: "plants",
  data() {
    return {
      fields: [
        {
          key: "selectAll",
          label: "",
        },
        {
          key: "plantName",
          sortable: true,
        },
        {
          key: "indoor",
          label: "Location",
          sortable: true,
        },
        {
          key: "careDate",
          label: "Last Watered",
          sortable: true,
        },
        {
          key: "plantImg",
          label: "",
        },
      ],
      showForm: false,
      selectedPlantIds: [],
      checkAll: false,
      modal: "",
    };
  },
  computed: {
    name() {
      return this.$store.state.profile.displayName === undefined ||
        this.$store.state.profile.displayName === null
        ? this.$store.state.user.username
        : this.$store.state.profile.displayName;
    },
    plants() {
      return this.$store.state.plants;
    },
    latestWatering() {
      return this.$store.state.latestWatering;
    },
    combinedDataTables() {
      return Array.from(this.plants).map((p) => {
        let obj = Object.assign({}, p);
        let item = this.latestWatering.find((w) => w.plantId === obj.plantId);
        if (item) {
          Object.assign(obj, item);
        }
        return obj;
      });
    }
  },
  methods: {
    selectImg(plantImg) {
      return plantImg === null ? require("@/assets/leaf.png") : plantImg;
    },
    checkSelectedIds() {
      this.selectedPlantIds.length === this.$store.state.plants.length
        ? (this.checkAll = true)
        : (this.checkAll = false);
    },
    updateSelectedIds() {
      this.checkAll = false;
      this.selectedPlantIds = [];
    },
    selectAll() {
      if (this.checkAll === true) {
        for (let i = 0; i < this.combinedDataTables.length; i++) {
          if (!this.selectedPlantIds.includes(this.combinedDataTables[i].plantId)) {
            this.selectedPlantIds.push(this.combinedDataTables[i].plantId);
          }
        }
      } else {
        this.selectedPlantIds = [];
        this.checkAll = false;
      }
    },
    toggleForm(plant) {
      this.newPlant = plant;
      this.showForm === true ? (this.showForm = false) : (this.showForm = true);
    },
    formatDateDay(date) {
      const options = {
        weekday: "long",
        //year: "numeric",
        month: "long",
        day: "numeric",
      };
      return new Date(date).toLocaleDateString("en-US", options);
    },
  },
  created() {
    treatmentService
      .getLatestWaterings()
      .then((response) => {
        if (response.status == 200) {
          this.$store.commit("SET_LATEST_WATERING", response.data);
        }
      })
      .catch((err) => {
        alert(err + " problem getting latest waterings!");
      });
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
    plantService
      .getAllPlants()
      .then((response) => {
        if (response.status == 200) {
          this.$store.commit("SET_PLANTS", response.data);
        }
      })
      .catch((err) => {
        alert(err + " problem getting plants!");
      });
    profileService
      .getProfile()
      .then((response) => {
        if (response.status == 200) {
          this.$store.commit("SET_PROFILE", response.data);
        }
      })
      .catch((err) => {
        alert(err + " problem getting profile!");
      });
  },
};
</script>

<style>
#toggleFormBtn {
  background-color: var(--green);
  color: var(--platinum);
  border: 1px solid var(--orange);
  font-size: 1rem;
  min-width: 142px;
}
.collapsed > .when-open,
.not-collapsed > .when-closed {
  display: none;
}
.avatar-custom {
  background-color: var(--green);
  border: 1px solid var(--orange);
}
.avatar-add-plant {
  background-color: var(--yellow);
  border: 1px solid var(--orange);
  margin-left: 3px;
}
#plant-table {
  margin-top: 1rem;
}
.table td {
  vertical-align: middle;
}
</style>