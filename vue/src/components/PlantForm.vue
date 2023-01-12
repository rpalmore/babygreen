<template>
<b-container id="add-plant-container">
  <b-form inline @submit.prevent="addPlant" id="add-plant-form">
    <label class="sr-only" for="plantName">Plant Name</label>
    <b-form-input
      id="plantName"
      class="mb-2 mr-sm-2 mb-sm-0"
      placeholder="Plant name"
      type="text"
      v-model="plant.plantName"
      required
    ></b-form-input>
    <b-form-radio-group
      required
      v-model="plant.indoor"
      :options="options"
      :aria-describedby="ariaDescribedby"
      name="radio-inline"
    ></b-form-radio-group>
    <b-row id="btn-group">
    <b-button size="sm" id="cancel-plant" @click="cancel" class="default"
      >Cancel</b-button
    >
    <b-button size="sm" type="submit" class="default">Save</b-button>
    </b-row>
  </b-form>
</b-container>
</template>

<script>
import plantService from "../services/PlantService";
export default {
  name: "plant-form",
  data() {
    return {
      plant: {
        userId: this.$store.state.user.id,
      },
      today: new Date(),
      options: [
        { text: "Indoor", value: true },
        { text: "Outdoor", value: false },
      ],
    };
  },
  methods: {
    formatDateUI(date) {
      const options = {
        year: "numeric",
        month: "long",
        day: "numeric",
      };
      return new Date(date).toLocaleDateString("en-US", options);
    },
    formatDate() {
        const dd = String(this.today.getDate()).padStart(2, "0");
        const mm = String(this.today.getMonth() + 1).padStart(2, "0");
        const yyyy = this.today.getFullYear();
        this.today = yyyy + "-" + mm + "-" + dd;
    },
    addPlant() {
      this.formatDate();
        this.plant.plantAge = this.today;
      plantService
        .createPlant(this.plant)
        .then((response) => {
          if (response.status == 201) {
            this.$store.commit("ADD_PLANT", response.data);
          }
          this.plant = {
            userId: this.$store.state.user.id,
          };
          if (this.$route.name === 'add-plant') {
            this.$router.push("/plants");
          }
        })
        .catch((err) => {
          alert(err + " problem creating plant!");
        });
    },
    cancel() {
      this.plant = {
        userId: this.$store.state.user.id,
      };
      this.$root.$emit('bv::toggle::collapse', 'collapse-form')
    },
  },
};
</script>

<style>
#add-plant-container {
  background-color: var(--light-shade1);
  border-left: 5px solid var(--green);
}
#add-plant-form {
  padding: 15px 0 15px 0;
  gap: 0.3rem;
}
#cancel-plant {
  margin-right: 1%;
  background-color: var(--gray);
}
#btn-group {
  width: 100%;
  margin-top: 0.3rem;
  margin-left: 0;
}
</style>