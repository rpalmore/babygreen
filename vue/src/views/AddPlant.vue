<template>
  <b-container id="add-plant">
    <b-row align-h="center">
      <p class="section-header">Add a plant</p>
    </b-row>
    <PlantForm />
  </b-container>
</template>

<script>
import PlantForm from "../components/PlantForm.vue";
export default {
  components: { PlantForm },
  name: "add-plant",
};
</script>

<style>
#add-plant {
  justify-content: center;
}
#add-plant .section-header {
  margin-bottom: 2rem;
}
</style>