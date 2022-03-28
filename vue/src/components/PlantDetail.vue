<template>
  <div id="plant-detail">
    <h2>{{ plant.plantName }}</h2>
    <p>
      This page offers more details about a plant and the option to add dated
      notes and additional information, such as a link to resources and a field
      to enter the age of the plant (i.e., when you first started caring for the
      plant, or when you planted it, if it's an outdoor plant). You can also add
      a plant image here via Cloudinary, which will be the main art on the page.
      You can also add photos to your notes.
    </p>
    <p>Enhance this listing by adding a link, age of plant.</p>
    <p>[Plant image placeholder with option to add/edit.]</p>
    <h3>About this plant</h3>
    <p>I am an {{ plant.indoor == "true" ? "indoor" : "outdoor" }} plant.</p>
    <p>Add notes about this plant via some kind of notes form.</p>
    <form v-on:submit.prevent="saveNote" id="note-form">
      <label for="note">Notes:</label>
      <input type="text" class="note-form" v-model="note.note" />
      <button id="submit">Save</button>
    </form>
    <div id="note-container" v-for="note in notes" v-bind:key="note.noteId">
      {{ note.note }}
      {{ note.createdOn }}&nbsp;
      <a v-on:click="deleteNote(note.noteId)">&#10006;</a>&nbsp;
      <a v-on:click.prevent="toggleForm(note)">{{
        showForm === true ? "cancel" : "edit"
      }}</a>
    </div>
    <div id="form-container">
      <form v-on:submit.prevent="editNote(newNote)" v-show="showForm === true">
        <label for="note">Edited note:</label>
        <input type="text" v-model="newNote.note" />
        <button id="submit">Save</button>
      </form>
    </div>
  </div>
</template>

<script>
import plantNoteService from "../services/PlantNoteService";
export default {
  name: "plant-detail",
  data() {
    return {
      note: {
        plantId: this.$route.params.plantId,
      },
      // this new object might not be needed if the update function is moved into a new component.
      newNote: {},
      showForm: false,
    };
  },
  computed: {
    plants() {
      return this.$store.state.plants;
    },
    plantId() {
      return this.$route.params.plantId;
    },
    plant() {
      return this.plants.find((p) => p.plantId == this.$route.params.plantId);
    },
    notes() {
      return this.$store.state.notes;
    },
  },
  methods: {
    toggleForm(note) {
      this.newNote = note;
      if (this.showForm === true) {
        this.showForm = false;
      } else {
        this.showForm = true;
      }
    },
    saveNote() {
      plantNoteService
        .createNote(this.note)
        .then((response) => {
          if (response.status == 201) {
            this.$store.commit("ADD_NOTE", response.data);
          }
          this.note = {
            plantId: this.$route.params.plantId,
          };
        })
        .catch((err) => {
          alert(err + " problem creating note!");
        });
    },
    editNote(newNote) {
      plantNoteService
        .editNote(newNote)
        .then((response) => {
          if (response.status == 200) {
            this.$store.commit("EDIT_NOTE", newNote);
            this.showForm = false;
          }
          this.newNote = {};
        })
        .catch((err) => {
          alert(err + " problem updating note!");
        });
    },
    deleteNote(noteId) {
      if (confirm("Are you sure you want to delete this note?")) {
        plantNoteService
          .deleteNote(noteId)
          .then((response) => {
            if (response.status == 204) {
              this.$store.commit("DELETE_NOTE", noteId);
            }
          })
          .catch((err) => {
            alert(err + " problem deleting note!");
          });
      }
    },
  },
  created() {
    plantNoteService.getNotes(this.plantId).then((response) => {
      this.$store.commit("SET_NOTES", response.data);
    });
  },
};
</script>

<style>
</style>