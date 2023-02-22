<template>
  <b-container fluid id="plant-view">
    <b-row align-h="center">
      <p class="section-header">Family portrait</p>
    </b-row>

    <b-card-group deck id="gallery">
      <b-card
        class="gallery-wall"
        v-bind:plant="plant"
        v-for="plant in plants"
        v-bind:key="plant.plantId"
        :img-src="selectPlantImg(plant.plantImg)"
        img-alt="Plant image"
        img-top
      >
        <b-card-text>
          <router-link
            :to="{ name: 'plant-detail', params: { plantId: plant.plantId } }"
          >
            {{ plant.plantName }}
          </router-link></b-card-text
        >
      </b-card>
    </b-card-group>
  </b-container>
</template>

<script>
import Vue from 'vue';
import { CardPlugin } from 'bootstrap-vue';
export default {
  name: "gallery",
  computed: {
    plants() {
      return this.$store.state.plants;
    },
  },
  methods: {
    getThirds() {
      let thirds = [];
      
      return thirds;
    },
    selectPlantImg(plantImg) {
      return plantImg === null
        ? require("@/assets/CandaceStone_Pixabay400.png")
        : plantImg;
    },
  },
  created() {
    Vue.use(CardPlugin);
  }
};
</script>

<style>
.gallery-wall {
  text-align: center;
  margin-top: 1rem;
  border: 1px solid var(--dark);
}
.gallery-wall > img {
  background-color: var(--orange);
}
.gallery-wall .card-body {
  border-top: 1px solid var(--dark);
}
.card {
  min-width: 23%;
}
</style>