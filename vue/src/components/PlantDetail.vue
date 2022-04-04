<template>
  <div id="plant-detail">
    <h2>{{ plant.plantName }}</h2>
    {{ plant.infoUrl }}
    <a v-on:click="toggleInfoForm">{{
      plant.infoUrl == null ? "Add link" : "(edit link)"
    }}</a>
    <form v-on:submit.prevent="editPlant" v-show="showInfoForm === true">
      <label for="infoUrl">Add an external link: </label>
      <input type="text" v-model="plant.infoUrl" />
      <button id="submit">Save</button>
      <button v-on:click.prevent="cancelForm($event)" id="cancelInfoForm">
        Cancel
      </button>
    </form>
    <div
    v-bind:treatment="treatment"
    v-for="treatment in treatments"
    v-bind:key="treatment.treatmentId"
    >
      I was {{ treatment.careType }} on {{ treatment.careDate }}
      <a v-on:click="deleteTreatment(treatment)">&#10006;</a>
    </div>
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
    <p>
      I am an {{ plant.indoor == true ? "indoor" : "outdoor" }} plant and have
      been in {{ this.$store.state.user.username }}&#8217;s care since
      <a v-on:click="toggleDateForm">{{
        plant.plantAge == null ? "today" : plant.plantAge
      }}</a
      >.
    </p>
    <form v-on:submit.prevent="editPlant" v-show="showDateForm === true">
      <label for="plantAge">Plant cared for since: </label>
      <input type="date" v-model="plant.plantAge" />
      <button id="submit">Update</button>
      <button v-on:click.prevent="cancelForm($event)" id="cancelDateForm">
        Cancel
      </button>
    </form>

    <p>Add notes about this plant via some kind of notes form.</p>
    <form v-on:submit.prevent="saveNote" id="note-form">
      <label for="note">Notes:</label>
      <input required type="text" class="note-form" v-model="note.note" />
      <button id="submit">Save</button>
    </form>
    <div id="note-container" v-for="note in notes" v-bind:key="note.noteId">
      {{ note.note }}
      {{ note.createdOn }}&nbsp;
      <a v-on:click="deleteNote(note.noteId)">&#10006;</a>&nbsp;
      <a v-on:click.prevent="toggleNoteForm(note)">{{
        showNoteForm === true ? "cancel" : "edit"
      }}</a>
    </div>
    <div id="form-container">
      <form
        v-on:submit.prevent="editNote(editedNote)"
        v-show="showNoteForm === true"
      >
        <label for="note">Edited note:</label>
        <input type="text" v-model="editedNote.note" />
        <button id="submit">Update</button>
      </form>
    </div>
  </div>
</template>

<script>
import plantNoteService from "../services/PlantNoteService";
import plantService from "../services/PlantService";
import treatmentService from "../services/TreatmentService";
export default {
  name: "plant-detail",
  data() {
    return {
      // todo: set the date of the note object to "today" so it is sent to store and immediately available
      note: {
        plantId: this.$route.params.plantId,
      },
      //treatments: {},
      // note: this new object "editedNote" might not be needed if the update function is moved into a new component.
      editedNote: {},
      showNoteForm: false,
      showDateForm: false,
      showInfoForm: false,
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
    treatments() {
      return this.$store.state.treatments;
    }
  },
  methods: {
    toggleInfoForm() {
      this.showInfoForm === true
        ? (this.showInfoForm = false)
        : (this.showInfoForm = true);
    },
    toggleDateForm() {
      this.showDateForm === true
        ? (this.showDateForm = false)
        : (this.showDateForm = true);
    },
    toggleNoteForm(note) {
      this.editedNote = note;
      this.showNoteForm === true
        ? (this.showNoteForm = false)
        : (this.showNoteForm = true);
    },
    cancelForm(event) {
      event.target.id === "cancelInfoForm"
        ? this.toggleInfoForm()
        : this.toggleDateForm();
    },
    editPlant() {
      plantService
        .editPlant(this.plant)
        .then((response) => {
          if (response.status == 200) {
            this.$store.commit("EDIT_PLANT", this.plant);
            this.showDateForm = false;
            this.showInfoForm = false;
          }
        })
        .catch((err) => {
          alert(err + " problem editing plant!");
        });
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
    editNote(editedNote) {
      plantNoteService
        .editNote(editedNote)
        .then((response) => {
          if (response.status == 200) {
            this.$store.commit("EDIT_NOTE", editedNote);
            this.showNoteForm = false;
          }
          this.editedNote = {};
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
    deleteTreatment(treatment) {
      treatment.plantId = this.$route.params.plantId;
      if(confirm("Are you sure you want to delete this treatment?")) {
        treatmentService.deleteTreatment(treatment.plantId, treatment.careId).then((response) => {
          if (response.status == 204) {
            this.$store.commit("DELETE_TREATMENT", treatment);
          }
        }).catch((err) => {
          alert(err + " problem deleting treatment!")
        });
      }
    },
  },
  created() {
    plantNoteService.getNotes(this.plantId).then((response) => {
      this.$store.commit("SET_NOTES", response.data);
    });
    treatmentService
      .getSinglePlantTreatments(this.plantId)
      .then((response) => {
        if (response.status == 200) {
          //this.treatments = response.data;
          this.$store.commit("SET_TREATMENTS", response.data);
        }
      })
      .catch((err) => {
        alert(err + " problem retrieving treatments!");
      });
  },
};
</script>

<style>
</style>