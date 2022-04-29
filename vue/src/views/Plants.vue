<template>
  <b-container id="plant-view">
    <b-row align-h="center">
      <p class="section-header">
        {{
          this.$store.state.profile.displayName === undefined
            ? this.$store.state.user.username
            : this.$store.state.profile.displayName
        }}&#8217;s plants
      </p>
    </b-row>
    <p>
      This page lists all plants associated with user. Features to add: sort by
      indoor/outdoor; select plants to record that you watered them, defaulting
      to today but with the option to select a different date. List could show
      thumbnail of each plant at left (in circle) or a placeholder image if no
      photo has been uploaded -- including for plants just added, for example.
    </p>
    <p>
      To do: Filter by indoor/outdoor and do a text search for plants by name.
      <!-- {{ plants }} -->
      {{ latestWatering }}
    </p>
    <AddPlant />
    <!-- plants table -->
    <b-table striped hover :items="plants" :fields="fields">
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
        <span
          v-for="treatment in latestWatering"
          v-bind:key="treatment.treatmentId"
          >{{
            data.item.plantId === treatment.plantId
              ? formatDateDay(treatment.careDate.replace(/-/g, "\/"))
              : " "
          }}</span
        >
      </template>
      <template #cell(plantImg)="data">
        <b-avatar id="avatar-custom" v-bind:src="selectImg(data.item.plantImg)"></b-avatar>
      </template>
    </b-table>

    <!-- leaving for reference in case I decide to add back to Plants.vue -->
    <b-container id="plant-list">
        <b-col>
          <a v-on:click="deletePlant(plant.plantId)">&#10006;</a> |
          <a v-on:click.prevent="toggleForm(plant)">{{
            showForm === true ? "cancel" : "edit"
          }}</a>
        </b-col>
    </b-container>

    <!-- edit a plant -->
    <!-- <div id="form-container">
      <form
        v-on:submit.prevent="editPlant(newPlant)"
        v-show="showForm === true"
      >
        <label for="newName">New name:</label>
        <input type="text" v-model="newPlant.plantName" />
        <input
          type="radio"
          name="indoor"
          value="true"
          v-model="newPlant.indoor"
        />
        <label for="indoor">Indoor</label>
        <input
          type="radio"
          name="indoor"
          value="false"
          v-model="newPlant.indoor"
        />
        <label for="outdoor">Outdoor</label>
        <button id="submit">Save</button>
      </form>
    </div> -->

    <LogCare
      v-bind:selectedPlantIds="selectedPlantIds"
      @form-sent="updateSelectedIds"
    />
  </b-container>
</template>

<script>
import AddPlant from "../components/AddPlant.vue";
import LogCare from "../components/LogCare.vue";
import plantService from "../services/PlantService";
import treatmentService from "../services/TreatmentService";
export default {
  components: { AddPlant, LogCare },
  name: "plants",
  data() {
    return {
      // Not sure if I need this plant object now that I've moved "add plant" form. Need to test!
      // plant: {
      //   userId: this.$store.state.user.id,
      // },
      fields: [
        {
          key: "selectAll",
          label: "",
          // need to add a checkbox here somehow
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
          label: "Image",
        },
      ],
      // newPlant: {},
      showForm: false,
      selectedPlantIds: [],
      checkAll: false,
      // treatment: {
      //   plantId: [],
      // },
      modal: "",
    };
  },
  computed: {
    plants() {
      return this.$store.state.plants;
    },
    latestWatering() {
      return this.$store.state.latestWatering;
    },
    // this is now handled in child component: logCare
    // btnDisabled() {
    //   return this.selectedPlantIds.length === 0 ? true : false;
    // },
  },
  methods: {
    selectImg(plantImg) {
      // to do: conditionally render avatar image URL instead of variant

      return plantImg === null ? require('@/assets/leaf.png') : plantImg;
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
        for (let i = 0; i < this.plants.length; i++) {
          if (!this.selectedPlantIds.includes(this.plants[i].plantId)) {
            this.selectedPlantIds.push(this.plants[i].plantId);
          }
        }
        // eslint-disable-next-line no-console
        console.log(this.selectedPlantIds);
      } else {
        this.selectedPlantIds = [];
        this.checkAll = false;
      }
    },
    toggleForm(plant) {
      this.newPlant = plant;
      this.showForm === true ? (this.showForm = false) : (this.showForm = true);
    },
    deletePlant(plantId) {
      this.modal = "";
      this.$bvModal
        .msgBoxConfirm("Are you sure you want to delete this plant?")
        .then((value) => {
          this.modal = value;
          if (value === true) {
            plantService
              .deletePlant(plantId)
              .then((response) => {
                if (response.status == 204) {
                  this.$store.commit("DELETE_PLANT", plantId);
                }
              })
              .catch((err) => {
                alert(err + " problem deleting plant!");
              });
          }
        });
    },
    // editPlant(newPlant) {
    //   if (newPlant.plantAge === null) {
    //     let today = new Date();
    //     const dd = String(today.getDate()).padStart(2, "0");
    //     const mm = String(today.getMonth() + 1).padStart(2, "0");
    //     const yyyy = today.getFullYear();
    //     today = yyyy + "-" + mm + "-" + dd;
    //     newPlant.plantAge = today;
    //   }
    //   plantService
    //     .editPlant(newPlant)
    //     .then((response) => {
    //       if (response.status == 200) {
    //         this.$store.commit("EDIT_PLANT", newPlant);
    //         this.showForm = false;
    //       }
    //     })
    //     .catch((err) => {
    //       alert(err + " problem editing plant!");
    //     });
    // },
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
          //eslint-disable-next-line no-console
          console.log(response.data);
        }
      })
      .catch((err) => {
        alert(err + " problem getting latest waterings!");
      });

      // Best to get plants at login. Otherwise, list of plants is tied to 
      // most recent user. Even if you clear the list at logout via the store, it *might* mean that 
      // a user's plant list won't appear until they get to this page -- problematic if 
      // you are directed *first* to the profile page.

    // plantService.getAllPlants().then((response) => {
    //   if (response.status == 200) {
    //     this.$store.commit("SET_PLANTS", response.data);
    //   }
    // });
  },
};
</script>

<style>
.section-header {
  border-top: 3px solid var(--orange);
  border-bottom: 3px solid var(--orange);
}
#avatar-custom {
  background-color: var(--green);
  border: 1px solid var(--orange);
}
</style>