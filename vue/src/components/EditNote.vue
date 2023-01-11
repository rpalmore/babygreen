<template>
<div>
  {{ this.$store.state.notes }}
  <b-card-body>
    <b-form v-on:submit.prevent="editNote(note)">
      <b-form-group>
        <b-form-textarea v-model.lazy.trim="note.note" autofocus="true">
        </b-form-textarea>
      </b-form-group>
      <b-button
        size="sm"
        id="cancelEdit"
        @click="cancelForm($event, note)"
        class="default"
        >Cancel</b-button
      >
      <b-button size="sm" type="submit" class="default">Update</b-button>
    </b-form>
  </b-card-body>
  </div>
</template>

<script>
import plantNoteService from "../services/PlantNoteService";
export default {
  name: "edit-note",
  // props: ["notes"],
  data() {
    return {
      note: {},
      // savedNote: "",
    };
  },
  // computed: {
  //   note() {
  //     return this.$store.state.notes.find((n) => n.noteId == this.noteId);
  //   }
  // },
  methods: {
    editNote(editedNote) {
      if (editedNote.note != this.savedNote) {
        plantNoteService
          .editNote(editedNote)
          .then((response) => {
            if (response.status == 200) {
              this.$store.commit("EDIT_NOTE", editedNote);
              this.showNoteForm = false;
            }
          })
          .catch((err) => {
            alert(err + " problem updating note!");
          });
      } else {
        this.showNoteForm = false;
      }
    },
  },
};
</script>