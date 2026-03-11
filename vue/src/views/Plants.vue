<template>
  <b-container fluid id="plant-view">
    <b-row align-h="center">
      <p class="section-header">{{ name }}&#8217;s plants</p>
    </b-row>
    <LogCare v-bind:selectedPlantIds="selectedPlantIds" @form-sent="updateSelectedIds" @type-changed="filterTypes" />
    <!-- plants table -->
    <b-table id="plant-table" hover responsive outlined sort-icon-left :sort-by.sync="sortBy" :sort-desc.sync="sortDesc"
      :items="filteredData" :fields="fields" :tbody-tr-class="rowClass">
      <template #head(selectAll)="data">
        <b-form-checkbox v-bind:value="data.checkAll" v-model="checkAll" v-on:change="selectAll">
        </b-form-checkbox>
      </template>

      <template #cell(selectAll)="data">
        <b-form-checkbox v-bind:value="data.item.plantId" v-model="selectedPlantIds"
          v-on:change="checkSelectedIds"></b-form-checkbox>
      </template>

      <template #cell(plantName)="data">
        <router-link :to="{ name: 'plant-detail', params: { plantId: data.item.plantId } }">
          {{ data.item.plantName }}
        </router-link>
      </template>

      <template #cell(careDate)="data">
        {{
        data.item.careDate
          ? formatDateDay(data.item.careDate.replace(/-/g, "\/"))
          : ""
      }}
      </template>

      <template #cell(nextWatering)="data">
        {{
        data.item.nextWatering == futureDateA
          ? ''
          : data.item.nextWatering == futureDateB
            ? ''
            : formatDateDay(data.item.nextWatering) ==
              formatDateDay(new Date(Date.now()))
              ? "Today!"
              : compareDates(data.item.nextWatering)
      }}
      </template>

      <template #cell(plantImg)="data">
        <b-avatar class="avatar-custom" size="lg" v-bind:src="selectImg(data.item.plantImg)"></b-avatar>
      </template>
    </b-table>
  </b-container>
</template>

<script>
import LogCare from "../components/LogCare.vue";
import plantService from "../services/PlantService";
import treatmentService from "../services/TreatmentService";
import profileService from "../services/ProfileService";
import { BTable, ModalPlugin } from "bootstrap-vue";
import Vue from 'vue';
export default {
  components: { LogCare, BTable },
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
          key: "nextWatering",
          label: "Next Watering",
          sortable: true,
        },
        {
          key: "careDate",
          label: "Last Watered",
          sortable: true
        },
        {
          key: "plantImg",
          label: "Photo",
        },
      ],
      sortBy: 'nextWatering',
      sortDesc: false,
      showForm: false,
      selectedPlantIds: [],
      checkAll: false,
      modal: "",
      isToday: false,
      futureDateA: new Date(2099, 0, 1, 0, 0, 0), // Jan 1, 2099
      futureDateB: new Date(2099, 1, 1, 0, 0, 0), // Feb 1, 2099
      selected: 'all'
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
    // combine plant list with latest watering data
    // replace null values with future dates to preserve sort order
    combinedDataTables() {
      return Array.from(this.plants).map((p) => {
        let obj = Object.assign({}, p);
        // find plants that have been watered
        let wateredPlant = this.latestWatering.find((w) => w.plantId === obj.plantId);
        if (wateredPlant) {
          Object.assign(obj, wateredPlant);
          let target = new Date(obj.careDate);
          // calculate next watering date based on each plant's schedule
          if (obj.plantSchedule != 0) {
            target.setDate(target.getDate() + 1 + obj.plantSchedule);
            Object.assign(obj, { nextWatering: target });
          } else if (obj.plantSchedule == 0) {
            // if no schedule, set a future date of Jan 1, 2099
            // single empty cell sorts higher than double empty
            Object.assign(obj, { nextWatering: this.futureDateA });
          }
        } else {
          // if no watering data, set a future date of Feb 1, 2099
          // double empty cells will be sorted to bottom of table
          Object.assign(obj, { nextWatering: this.futureDateB });
        }
        return obj;
      });
    },
    filteredData() {
      return this.selected === 'all' ? this.combinedDataTables : this.combinedDataTables.filter(a => a.locationType === this.selected);
    }
  },
  methods: {
    rowClass(item, type) {
      if (item && type === "row") {
        if (
          this.formatDateDay(item.nextWatering) ==
          this.formatDateDay(new Date(Date.now()))
        ) {
          return "row-highlight";
        } else {
          return "";
        }
      } else {
        return null;
      }
    },
    compareDates(date) {
      let today = new Date();
      // normalize hours to focus on dates only
      today.setHours(0, 0, 0, 0);
      date.setHours(0, 0, 0, 0);
      // calculate dates in milliseconds
      let todayTime = today.getTime();
      let dateTime = date.getTime();
      if (todayTime > dateTime) {
        const difference_ms = Math.abs(todayTime - dateTime);
        const oneDay = 1000 * 60 * 60 * 24; // 86,400,000 milliseconds in a day
        let overdueDays = Math.floor(difference_ms / oneDay);
        return "Overdue by " + overdueDays + " days!"
      }
      const sevenDays = 7 * 24 * 60 * 60 * 1000; // 604,800,000 milliseconds in a week
      if (dateTime - sevenDays < todayTime) {
        return this.formatWithinWeek(date);
      }
      return this.formatDateDay(date);
    },
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
    filterTypes(value) {
      this.selected = value;
    },
    selectAll() {
      if (this.checkAll === true) {
        for (let i = 0; i < this.combinedDataTables.length; i++) {
          if (
            !this.selectedPlantIds.includes(this.combinedDataTables[i].plantId)
          ) {
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
    formatWithinWeek(date) {
      this.formatDateDay(date);
      const options = {
        weekday: "long",
      };
      return new Date(date).toLocaleString("en-US", options);
    },
    formatDateDay(date) {
      const options = {
        weekday: "long",
        month: "long",
        day: "numeric",
      };
      return new Date(date).toLocaleDateString("en-US", options);
    },
  },
  created() {
    Vue.use(ModalPlugin);
    treatmentService
      .getAllTreatments()
      .then((response) => {
        if (response.status == 200) {
          this.$store.commit("SET_TREATMENTS", response.data);
        }
      })
      .catch((err) => {
        /* eslint no-console: ["error", { allow: ["error"] }] */
        console.error(err + " problem getting treatments!");
      });
    treatmentService.getLatestWaterings()
      .then((response) => {
        if (response.status == 200) {
          this.$store.commit("SET_LATEST_WATERING", response.data);
        }
      })
      .catch((err) => {
        /* eslint no-console: ["error", { allow: ["error"] }] */
        console.error(err + " problem getting latest waterings!");
      });
    plantService
      .getAllPlants()
      .then((response) => {
        if (response.status == 200) {
          this.$store.commit("SET_PLANTS", response.data);
        }
      })
      .catch((err) => {
        /* eslint no-console: ["error", { allow: ["error"] }] */
        console.error(err + " problem getting plants!");
      });
    profileService
      .getProfile()
      .then((response) => {
        if (response.status == 200) {
          this.$store.commit("SET_PROFILE", response.data);
        }
      })
      .catch((err) => {
        /* eslint no-console: ["error", { allow: ["error"] }] */
        console.error(err + " problem getting profile!");
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

.row-highlight {
  border: 2px solid var(--green);
  /* -moz-box-shadow: inset 0px 0px 0px 11px var(--light-shade1);
  -webkit-box-shadow: inset 0px 0px 0px 11px var(--light-shade1); */
  -webkit-appearance: none;
  box-shadow: inset 0px 0px 0px 11px var(--light-shade1);
}
</style>