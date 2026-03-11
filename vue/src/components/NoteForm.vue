<template>
  <b-container>
    <b-form id="add-note-form" @submit.prevent="saveNote(note)">
      <b-form-group>
        <label for="note">My note:</label>
        <b-form-textarea required v-model="note.note"></b-form-textarea>
      </b-form-group>
      <b-form-group>
        <label for="createdOn">Date:</label>
        <b-form-input required name="createdOn" id="createdOn" type="date" v-model="myDate">
        </b-form-input>
      </b-form-group>
      <b-button size="sm" id="cancelNote" @click="cancelForm()" class="default">Cancel</b-button>
      <b-button size="sm" type="submit" class="default">Save</b-button>
    </b-form>
  </b-container>
</template>

<script>
import plantNoteService from "../services/PlantNoteService";

export default {
  name: "plant-detail",
  props: ["plantId"],
  components: {},
  data() {
    return {
      note: {},
      myDate: new Date(Date.now() - new Date().getTimezoneOffset() * 60000).toISOString().slice(0, 10)
    };
  },
  methods: {
    cancelForm() {
      this.$root.$emit(
        "bv::toggle::collapse",
        "collapse-note-form",
        (this.note = {})
      );
    },
    saveNote(note) {
      this.note.createdOn = this.myDate;
      this.note = {
        plantId: this.plantId,
        note: note.note,
        createdOn: note.createdOn
      };
      plantNoteService
        .createNote(this.note)
        .then((response) => {
          if (response.status == 201) {
            this.$store.commit("ADD_NOTE", response.data);
          }
          this.note = {};
          // Close 'add note form' after saving note:
          this.$root.$emit(
            "bv::toggle::collapse",
            "collapse-note-form",
            (this.note = {})
          );
        })
        .catch((err) => {
          this.$bvModal.show('error-modal');
          /* eslint no-console: ["error", { allow: ["error"] }] */
          console.error(err + " problem creating note!");
        });
    },
  },
};
</script>
