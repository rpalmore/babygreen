<template>
  <b-row class="note-card" align-h="center">
    <b-card
      id="note-card"
      style="max-width: 640px"
      v-for="note in notes"
      v-bind:key="note.noteId"
      no-body
      class="overflow-hidden"
      :header="formatDateDay(note.createdOn.replace(/-/g, '\/'))"
    >
      <b-row no-gutters>
        <b-col md="6" v-if="note.noteImg != null">
          <b-card-body>
            <b-card-text>
              {{ note.note }}
            </b-card-text>
          </b-card-body>
        </b-col>
        <b-col v-else>
          <b-card-body>
            <b-card-text>
              {{ note.note }}
            </b-card-text>
          </b-card-body>
        </b-col>
        <b-col md="6" v-if="note.noteImg != null">
          <b-card-img
            id="notePhoto"
            :src="note.noteImg"
            alt="Note image"
            class="rounded-0"
          ></b-card-img>
        </b-col>
      </b-row>
      <b-card-footer>
        <b-row align-v="center">
          <b-col class="text-center">
            <b-button
              id="note"
              class="card-footer-btn"
              size="sm"
              @click="useCloudinary($event, note)"
              >{{ note.noteImg == null ? "Add photo" : "Swap photo" }}
              <b-avatar
                size="sm"
                icon="camera-fill"
                class="avatar-icon-camera"
              ></b-avatar
            ></b-button>
          </b-col>
          <b-col class="text-center middle">
            <b-button
              class="card-footer-btn"
              size="sm"
              @click="passData(note)"
              v-b-toggle.collapse-note-edit-form
              :disabled="showNoteForm == note.noteId ? isEditingNote : null"
              >Edit note
              <b-avatar
                size="sm"
                icon="pencil-fill"
                class="avatar-icon-pencil"
              ></b-avatar
            ></b-button>
          </b-col>
          <b-col>
            <b-button
              class="card-footer-btn"
              size="sm"
              @click="deleteNote(note.noteId)"
            >
              Delete
              <b-avatar
                size="sm"
                icon="trash-fill"
                class="avatar-icon-trash"
              ></b-avatar>
            </b-button>
          </b-col>
        </b-row>
      </b-card-footer>

      <b-collapse
        v-if="showNoteForm == note.noteId"
        id="collapse-note-edit-form"
        class="mt-2"
      >
        <b-card-body>
          <b-form v-on:submit.prevent="editNote(note)">
            <b-form-group>
              <b-form-textarea v-model="note.note" autofocus="true">
              </b-form-textarea>
            </b-form-group>
            <b-button
              size="sm"
              id="cancelEdit"
              @click="cancelForm(note)"
              class="default"
              >Cancel</b-button
            >
            <b-button size="sm" type="submit" class="default">Update</b-button>
          </b-form>
        </b-card-body>
      </b-collapse>
    </b-card>
  </b-row>
</template>

<script>
import photoService from "../services/PhotoService";
import plantNoteService from "../services/PlantNoteService";
import Vue from 'vue';
import { CollapsePlugin, ModalPlugin, CardPlugin } from 'bootstrap-vue';
export default {
  name: "note-card",
  props: ["notes", "isEditingNote"],
  data() {
    return {
      showNoteForm: false,
      savedNote: "",
      savedDate: "",
    };
  },
  methods: {
    passData(note) {
      this.showNoteForm = note.noteId;
      this.savedNote = note.note;
      this.savedDate = note.createdOn;
      this.$emit(this.showNoteForm);
    },
    cancelForm(note) {
      this.$root.$emit(
        "bv::toggle::collapse",
        "collapse-edit-note-form",
        (note.note = this.savedNote),
        (this.showNoteForm = false)
      );
    },
    editNote(editedNote) {
      if (editedNote.note != this.savedNote) {
        plantNoteService
          .editNote(editedNote)
          .then((response) => {
            editedNote.createdOn = this.savedDate;
            if (response.status == 200) {
              this.$store.commit("EDIT_NOTE", editedNote);
              this.showNoteForm = false;
            }
          })
          .catch((err) => {
            /* eslint no-console: ["error", { allow: ["error"] }] */
            console.error(err + " problem updating note!");
            this.$router.push("/oops");
          });
      } else {
        this.showNoteForm = false;
      }
    },
    formatDateDay(date) {
      const options = {
        weekday: "long",
        year: "numeric",
        month: "long",
        day: "numeric",
      };
      return new Date(date).toLocaleDateString("en-US", options);
    },
    useCloudinary(event, item) {
      photoService.myWidget.open();
      this.$store.commit("SET_CLOUDINARY_SOURCE", event.target.id);
      this.$store.commit("SET_OBJECT", item);
    },
    deleteNote(noteId) {
      this.modal = "";
      this.$bvModal
        .msgBoxConfirm("Are you sure you want to delete this note?")
        .then((value) => {
          this.modal = value;
          if (value === true) {
            plantNoteService
              .deleteNote(noteId)
              .then((response) => {
                if (response.status == 204) {
                  this.$store.commit("DELETE_NOTE", noteId);
                }
              })
              .catch((err) => {
                /* eslint no-console: ["error", { allow: ["error"] }] */
                console.error(err + " problem deleting note!");
                this.$router.push("/oops");
              });
          }
        });
    },
  },
  created() {
    Vue.use(CollapsePlugin);
    Vue.use(ModalPlugin);
    Vue.use(CardPlugin);
  }
};
</script>

<style>
#notePhoto {
  padding: 1.25rem;
}
</style>