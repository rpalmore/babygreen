<template>
  <b-container id="add-plant">
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
  margin-top: 35px;
}
</style>