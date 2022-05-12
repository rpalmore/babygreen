<template>
  <b-container fluid id="plant-detail">
    <b-row align-h="center">
      <p class="section-header">
        {{ plant.plantName }}
      </p>
    </b-row>

    <b-row class="plant-card" align-h="center">
      <b-card no-body class="overflow-hidden" style="max-width: 540px">
        <b-row no-gutters align-v="center">
          <b-col md="6">
            <b-card-img
              id="b-card-img"
              v-bind:src="selectPlantImg(plant.plantImg)"
              alt="Plant image"
              class="rounded-0"
            ></b-card-img>
          </b-col>
          <b-col md="6">
            <b-card-body title="About me">
              <b-card-text>
                I am an {{ plant.indoor == true ? "indoor" : "outdoor" }} plant
                and have been in
                {{
                  this.$store.state.profile.displayName === undefined
                    ? this.$store.state.user.username
                    : this.$store.state.profile.displayName
                }}&#8217;s care since
                <a v-on:click="toggleDateForm">{{
                  plant.plantAge == null
                    ? "today"
                    : formatDate(plant.plantAge.replace(/-/g, "\/"))
                }}</a
                >.
                <!-- Todo: Test that date display is correct without day of week -->
              </b-card-text>
              <b-button id="addPhotoBtn" size="sm"
                >Add a photo
                <b-avatar
                  size="sm"
                  icon="camera-fill"
                  class="avatar-icon-camera"
                ></b-avatar
              ></b-button>
            </b-card-body>
          </b-col>
        </b-row>
      </b-card>
    </b-row>

    <!-- todo: style this form and place it -->
    <form v-on:submit.prevent="editPlant" v-show="showDateForm === true">
      <label for="plantAge">Plant cared for since: </label>
      <input type="date" v-model="plant.plantAge" />
      <button id="submit">Update</button>
      <button v-on:click.prevent="cancelForm($event)" id="cancelDateForm">
        Cancel
      </button>
    </form>

    <!-- Todo: figure our where "add info" feature lives -->
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
    <!-- End todo -->

    <p class="subsection-header">Recent waterings:</p>
    <p class="no-content" v-show="waterings.length === 0">
      You have no waterings to display.
    </p>
    <b-list-group
      v-bind:treatment="treatment"
      v-for="treatment in waterings.slice(0, 5)"
      v-bind:key="treatment.treatmentId"
      v-show="waterings.length > 0"
    >
      <b-list-group-item
        class="d-flex justify-content-between align-items-center"
      >
        <span>
          <b-avatar
            class="avatar-custom"
            id="watered"
            v-bind:src="require('@/assets/watering-can.png')"
          ></b-avatar>

          {{ formatDateDay(treatment.careDate.replace(/-/g, "\/")) }}
        </span>
        <b-avatar
          v-b-tooltip.hover
          title="Delete this treatment"
          icon="trash-fill"
          id="deleteTreatment"
          size="sm"
          v-on:click="deleteTreatment(treatment)"
          href="#"
        ></b-avatar>
      </b-list-group-item>
    </b-list-group>

    <!-- Toggle to see more watering treatments -->
    <p>
      <b-button
        v-if="waterings.length > 5"
        id="toggleBtn"
        size="sm"
        v-b-toggle.collapse-1
        >See <b-badge>{{ waterings.length - 5 }}</b-badge> more</b-button
      >
    </p>
    <b-collapse id="collapse-1" class="mt-2">
      <b-list-group
        v-for="treatment in waterings.slice(5)"
        v-bind:key="treatment.treatmentId"
      >
        <b-list-group-item
          class="d-flex justify-content-between align-items-center"
        >
          <span>
            <b-avatar
              class="avatar-custom"
              id="watered"
              v-bind:src="require('@/assets/watering-can.png')"
            ></b-avatar>

            {{ formatDateDay(treatment.careDate.replace(/-/g, "\/")) }}
          </span>
          <b-avatar
            v-b-tooltip.hover
            title="Delete this treatment"
            icon="trash-fill"
            id="deleteTreatment"
            size="sm"
            v-on:click="deleteTreatment(treatment)"
            href="#"
          ></b-avatar>
        </b-list-group-item>
      </b-list-group>
    </b-collapse>

    <p class="subsection-header">Other recent treatments:</p>
    <p class="no-content" v-show="otherTreatments.length === 0">
      You have no other treatments to display.
    </p>
    <b-list-group
      v-bind:treatment="treatment"
      v-for="treatment in otherTreatments.slice(0, 5)"
      v-bind:key="treatment.treatmentId"
      v-show="otherTreatments.length > 0"
    >
      <b-list-group-item
        class="d-flex justify-content-between align-items-center"
      >
        <span>
          <b-avatar
            class="avatar-custom"
            v-bind:id="treatment.careType"
            v-bind:src="selectImg(treatment.careType)"
          ></b-avatar>

          {{
            formatDateDay(treatment.careDate.replace(/-/g, "\/")) +
            ": " +
            treatment.careType
          }}
        </span>
        <b-avatar
          v-b-tooltip.hover
          title="Delete this treatment"
          icon="trash-fill"
          id="deleteTreatment"
          size="sm"
          v-on:click="deleteTreatment(treatment)"
          href="#"
        ></b-avatar>
      </b-list-group-item>
    </b-list-group>

    <!-- Toggle to see additional treatments -->
    <p>
      <b-button
        v-if="otherTreatments.length > 5"
        id="toggleBtn"
        size="sm"
        v-b-toggle.collapse-2
        >See <b-badge>{{ otherTreatments.length - 5 }}</b-badge> more</b-button
      >
    </p>
    <b-collapse id="collapse-2" class="mt-2">
      <b-list-group
        v-for="treatment in otherTreatments.slice(5)"
        v-bind:key="treatment.treatmentId"
      >
        <b-list-group-item
          class="d-flex justify-content-between align-items-center"
        >
          <span>
            <b-avatar
              class="avatar-custom"
              v-bind:id="treatment.careType"
              v-bind:src="selectImg(treatment.careType)"
            ></b-avatar>

            {{
              formatDateDay(treatment.careDate.replace(/-/g, "\/")) +
              ": " +
              treatment.careType
            }}
          </span>
          <b-avatar
            v-b-tooltip.hover
            title="Delete this treatment"
            icon="trash-fill"
            id="deleteTreatment"
            size="sm"
            v-on:click="deleteTreatment(treatment)"
            href="#"
          ></b-avatar>
        </b-list-group-item>
      </b-list-group>
    </b-collapse>

    <!-- Notes section -->
    <p class="subsection-header">Notes on {{ plant.plantName }}:</p>
    <p class="no-content" v-show="notes.length === 0">
      You have no notes to display.
    </p>

    <!-- Add a note with collapse -->
    <p>
      <b-button id="toggleFormBtn" size="sm" v-b-toggle.collapse-note-form
        ><span class="when-open">Close form</span
        ><span class="when-closed">Add a note</span>
        <b-avatar
          size="sm"
          class="avatar-custom-note"
          icon="file-post"
        ></b-avatar
      ></b-button>
    </p>

    <b-collapse id="collapse-note-form" class="mt-2">
      <b-form id="add-note-form" @submit.prevent="saveNote(note.note)">
        <b-form-group>
          <label for="note">My note:</label>
          <b-form-textarea required v-model="note.note"></b-form-textarea>
        </b-form-group>
        <b-button
          size="sm"
          id="cancel"
          @click="cancelForm($event)"
          class="default"
          >Cancel</b-button
        >
        <b-button size="sm" type="submit" class="default">Save</b-button>
      </b-form>
    </b-collapse>
    <!-- End add a note -->

    <b-card
      id="note-card"
      v-for="note in notes"
      v-bind:key="note.noteId"
      no-body
      :header="formatDateDay(note.createdOn.replace(/-/g, '\/'))"
    >
      <b-card-body>
        {{ note.note }}
      </b-card-body>
      <b-card-footer>
        <b-row align-v="center">
          <b-col class="text-center">
            <b-button id="addPhotoBtn" size="sm"
              >Add a photo
              <b-avatar
                size="sm"
                icon="camera-fill"
                class="avatar-icon-camera"
              ></b-avatar
            ></b-button>
          </b-col>
          <b-col class="text-center middle">
            <b-button id="addPhotoBtn" size="sm" @click="toggleNoteForm(note)"
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
              id="addPhotoBtn"
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

      <b-card-body v-if="showNoteForm == note.noteId">
        <b-form v-on:submit.prevent="editNote(note)">
          <b-form-group>
            <b-form-textarea v-model="note.note" autofocus="true">
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
    </b-card>

    <p>
      Todo: Add Cloudinary. Add link for add/update info URL. Form for updating
      plant age.
    </p>
  </b-container>
</template>

<script>
import plantNoteService from "../services/PlantNoteService";
import plantService from "../services/PlantService";
import treatmentService from "../services/TreatmentService";
// import EditNote from "./EditNote.vue";
export default {
  name: "plant-detail",
  // components: { EditNote },
  data() {
    return {
      note: {},
      savedNote: "",
      showNoteForm: false,
      showDateForm: false,
      showInfoForm: false,
      modal: "",
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
      return this.plants.find((p) => p.plantId == this.plantId);
    },
    notes() {
      return this.$store.state.notes.filter((n) => n.plantId == this.plantId);
    },
    treatments() {
      return this.$store.state.treatments.filter(
        (t) => t.plantId == this.plantId
      );
    },
    waterings() {
      return this.treatments.filter(
        (treatment) => treatment.careType == "watered"
      );
    },
    otherTreatments() {
      return this.treatments.filter(
        (treatment) => treatment.careType != "watered"
      );
    },
  },
  methods: {
    selectPlantImg(plantImg) {
      return plantImg === null
        ? require("@/assets/plant-placeholder.png")
        : plantImg;
    },
    selectImg(careType) {
      return careType === "sprayed"
        ? require("@/assets/spray-bottle.png")
        : careType === "repotted"
        ? require("@/assets/plant-pot.png")
        : careType === "fertilized"
        ? require("@/assets/eyedropper.png")
        : require("@/assets/bug.png");
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
    formatDate(date) {
      const options = {
        year: "numeric",
        month: "long",
        day: "numeric",
      };
      return new Date(date).toLocaleDateString("en-US", options);
    },
    toggleInfoForm() {
      this.showInfoForm = !this.showInfoForm;
    },
    toggleDateForm() {
      this.showDateForm = !this.showDateForm;
    },
    toggleNoteForm(note) {
      this.savedNote = note.note;
      this.showNoteForm = note.noteId;
    },
    cancelForm(event, note) {
      event.target.id === "cancelInfoForm"
        ? this.toggleInfoForm()
        : event.target.id === "cancel"
        ? this.$root.$emit(
            "bv::toggle::collapse",
            "collapse-note-form",
            (this.note = {})
          )
        : event.target.id === "cancelEdit"
        ? ((this.showNoteForm = false), (note.note = this.savedNote))
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
    saveNote(note) {
      this.note = {
        plantId: this.plantId,
        note: note,
        createdOn: new Date(),
      };
      plantNoteService
        .createNote(this.note)
        .then((response) => {
          if (response.status == 201) {
            this.$store.commit("ADD_NOTE", response.data);
          }
          this.note = {};
          // Close the 'add note form' after saving a note:
          this.$root.$emit(
            "bv::toggle::collapse",
            "collapse-note-form",
            (this.note = {})
          );
        })
        .catch((err) => {
          alert(err + " problem creating note!");
        });
    },
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
                alert(err + " problem deleting note!");
              });
          }
        });
    },
    deleteTreatment(treatment) {
      this.modal = "";
      this.$bvModal
        .msgBoxConfirm("Are you sure you want to delete this treatment?")
        .then((value) => {
          this.modal = value;
          if (value === true) {
            treatment.plantId = this.$route.params.plantId;
            treatmentService
              .deleteTreatment(treatment.plantId, treatment.careId)
              .then((response) => {
                if (response.status == 204) {
                  this.$store.commit("DELETE_TREATMENT", treatment);
                }
              })
              .catch((err) => {
                alert(err + " problem deleting treatment!");
              });
          }
        });
    },
  },
  created() {
    treatmentService
      .getAllTreatments()
      .then((response) => {
        if (response.status == 200) {
          this.$store.commit("SET_TREATMENTS", response.data);
        }
      })
      .catch((err) => {
        alert(err + " problem getting treatments!");
      });
    plantNoteService.getNotes().then((response) => {
      this.$store.commit("SET_NOTES", response.data);
    });
  },
};
</script>

<style>
#plant-detail > .row.plant-card {
  margin-top: 1rem;
}
.no-content {
  padding: 10px;
  border: 2px solid var(--green);
  border-radius: 0.2rem;
}
.card {
  border: 1px solid var(--gray);
}
.card-title {
  font-weight: 350;
}
#b-card-img {
  background-color: var(--orange);
}
#addPhotoBtn,
.avatar-icon-camera {
  background-color: var(--light);
  border: 1px solid var(--orange);
  color: var(--dark);
}
.avatar-icon-camera {
  background-color: var(--green);
}
#toggleBtn {
  background-color: var(--orange);
  margin-top: 1rem;
}
#toggleBtn .badge {
  background-color: var(--light);
  color: var(--dark);
}
#deleteTreatment {
  background-color: var(--orange);
  /* background-color: white;
  border: 2px solid var(--orange); */
}
.subsection-header {
  font-size: 1.3rem;
  font-weight: 400;
  margin-top: 1rem;
}
.avatar-custom#watered,
.avatar-custom#sprayed,
.avatar-custom#repotted,
.avatar-custom#fertilized,
.avatar-custom#pest-treated {
  margin-right: 1rem;
}
.avatar-custom#sprayed {
  background-color: var(--light);
  border: 1px solid var(--orange);
}
.avatar-custom#pest-treated {
  background-color: var(--orange);
  border: 1px solid var(--green);
}
.avatar-custom#fertilized {
  background-color: var(--yellow);
  border: 1px solid var(--green);
}
.avatar-custom#repotted {
  background-color: var(--platinum);
  border: 1px solid var(--green);
  /* border: 1px solid var(--orange); */
}
.card#note-card {
  margin-bottom: 1rem;
}
.avatar-custom-note {
  color: var(--dark);
  background-color: var(--platinum);
  border: 1px solid var(--orange);
  margin-left: 3px;
}
.collapsed > .when-open,
.not-collapsed > .when-closed {
  display: none;
}
#add-note-form {
  margin-bottom: 1rem;
}
.btn#cancelEdit {
  margin-right: 1%;
  background-color: var(--gray);
}
</style>