<template>
  <div id="plant-detail">
      <h2>{{ plant.plantName }}</h2>
      <p>Enhance this listing by adding a link, age of plant.</p>
      <p>[Plant image placeholder with option to add/edit.]</p>
      <h3>About this plant</h3>
      <p>I am an {{ plant.indoor === true ? "indoor" : "outdoor" }} plant.</p>
      <p>Add notes about this plant.</p>

  </div>
</template>

<script>
export default {
    name: "plant-detail",
    computed: {
        plants() {
            return this.$store.state.plants;
        },
        plantId() {
            return this.$route.params.plantId;
        },
        plant() {
            return this.plants.find((p) => p.plantId == this.$route.params.plantId);
        }
    }

}
</script>

<style>

</style>