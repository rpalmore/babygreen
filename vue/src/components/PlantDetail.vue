<template>
  <b-container fluid id="plant-detail">
    <b-row align-h="center">
      <p class="section-header">
        {{ plant.plantName }}
      </p>
    </b-row>

    <PlantCard
      v-bind:plantId="plantId"
      v-bind:isEditingPlant="isEditingPlant"
      @cancelDateForm="cancelForm($event, plant)"
      @toggleDate="toggleDateForm(plant)"
      @toggleEdit="toggleEditForm(plant)"
    />

    <b-collapse id="collapse-edit-form" class="mt-2">
      <EditPlant @cancelEditForm="cancelForm($event, plant)" />
    </b-collapse>

    <p class="subsection-header">Recent waterings:</p>
    <b-container v-show="waterings.length === 0">
      You have no waterings to display.
    </b-container>
    <b-container>
      <b-list-group
        v-bind:treatment="treatment"
        v-for="treatment in waterings.slice(0, 5)"
        v-bind:key="treatment.treatmentId"
        v-show="waterings.length > 0"
      >
        <b-list-group-item class="d-flex align-items-center">
          <b-col>
            <b-avatar
              class="avatar-custom"
              id="watered"
              v-bind:src="require('@/assets/watering-can.png')"
            ></b-avatar>
          </b-col>
          <b-col>
            {{ formatDateDay(treatment.careDate.replace(/-/g, "\/")) }}
          </b-col>
          <b-col class="delete">
            <b-avatar
              v-b-tooltip.hover
              title="Delete this treatment"
              icon="trash-fill"
              id="deleteTreatment"
              size="sm"
              v-on:click="deleteTreatment(treatment)"
              href="#"
            ></b-avatar>
          </b-col>
        </b-list-group-item>
      </b-list-group>

      <!-- TOGGLE: see more watering treatments -->
      <p>
        <b-button
          v-if="waterings.length > 5"
          id="toggleBtn"
          size="sm"
          v-b-toggle.collapse-1
        >
          <span class="when-closed">See </span>
          <span class="when-open">Hide </span>
          <b-badge>{{ waterings.length - 5 }}</b-badge> more</b-button
        >
      </p>
      <b-collapse id="collapse-1" class="mt-2">
        <b-list-group
          v-for="treatment in waterings.slice(5)"
          v-bind:key="treatment.treatmentId"
        >
          <b-list-group-item class="d-flex align-items-center">
            <b-col>
              <b-avatar
                class="avatar-custom"
                id="watered"
                v-bind:src="require('@/assets/watering-can.png')"
              ></b-avatar>
            </b-col>
            <b-col>
              {{ formatDateDay(treatment.careDate.replace(/-/g, "\/")) }}
            </b-col>
            <b-col class="delete">
              <b-avatar
                v-b-tooltip.hover
                title="Delete this treatment"
                icon="trash-fill"
                id="deleteTreatment"
                size="sm"
                v-on:click="deleteTreatment(treatment)"
                href="#"
              ></b-avatar>
            </b-col>
          </b-list-group-item>
        </b-list-group>
      </b-collapse>
    </b-container>

    <p class="subsection-header">Other recent treatments:</p>
    <b-container v-show="otherTreatments.length === 0">
      You have no other treatments to display.
    </b-container>
    <b-container>
      <b-list-group
        v-bind:treatment="treatment"
        v-for="treatment in otherTreatments.slice(0, 5)"
        v-bind:key="treatment.treatmentId"
        v-show="otherTreatments.length > 0"
      >
        <b-list-group-item class="d-flex align-items-center">
          <b-col>
            <b-avatar
              class="avatar-custom"
              v-bind:id="treatment.careType"
              v-bind:src="selectImg(treatment.careType)"
            ></b-avatar>
          </b-col>
          <b-col>
            {{ formatDateDay(treatment.careDate.replace(/-/g, "\/")) + ":" }}
            {{ treatment.careType }}
          </b-col>
          <b-col class="delete">
            <b-avatar
              v-b-tooltip.hover
              title="Delete this treatment"
              icon="trash-fill"
              id="deleteTreatment"
              size="sm"
              v-on:click="deleteTreatment(treatment)"
              href="#"
            ></b-avatar>
          </b-col>
        </b-list-group-item>
      </b-list-group>

      <!-- TOGGLE: see additional treatments -->
      <p>
        <b-button
          v-if="otherTreatments.length > 5"
          id="toggleBtn"
          size="sm"
          v-b-toggle.collapse-2
        >
          <span class="when-closed">See </span>
          <span class="when-open">Hide </span>
          <b-badge>{{ otherTreatments.length - 5 }}</b-badge> more</b-button
        >
      </p>
      <b-collapse id="collapse-2" class="mt-2">
        <b-list-group
          v-for="treatment in otherTreatments.slice(5)"
          v-bind:key="treatment.treatmentId"
        >
          <b-list-group-item class="d-flex align-items-center">
            <b-col>
              <b-avatar
                class="avatar-custom"
                v-bind:id="treatment.careType"
                v-bind:src="selectImg(treatment.careType)"
              ></b-avatar>
            </b-col>
            <b-col>
              {{ formatDateDay(treatment.careDate.replace(/-/g, "\/")) + ": " }}
              {{ treatment.careType }}
            </b-col>
            <b-col class="delete">
              <b-avatar
                v-b-tooltip.hover
                title="Delete this treatment"
                icon="trash-fill"
                id="deleteTreatment"
                size="sm"
                v-on:click="deleteTreatment(treatment)"
                href="#"
              ></b-avatar>
            </b-col>
          </b-list-group-item>
        </b-list-group>
      </b-collapse>
    </b-container>

    <!-- Notes section -->

    <p class="subsection-header">Notes on {{ plant.plantName }}:</p>
    <b-container v-show="notes.length === 0">
      You have no notes to display.
    </b-container>

    <!-- Add a note with collapse -->
    <div id="add-note-btn">
      <b-button id="toggleFormBtn" size="sm" v-b-toggle.collapse-note-form
        ><span class="when-open">Close form</span
        ><span class="when-closed">Add a note</span>
        <b-avatar
          size="sm"
          class="avatar-custom-note"
          icon="file-post"
        ></b-avatar
      ></b-button>
    </div>

    <!-- Add a note -->
    <b-collapse id="collapse-note-form" class="mt-2">
      <NoteForm v-bind:plantId="plantId" />
    </b-collapse>
    <!-- End add a note -->

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
                @click="toggleNoteForm(note)"
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
                @click="cancelForm($event, note)"
                class="default"
                >Cancel</b-button
              >
              <b-button size="sm" type="submit" class="default"
                >Update</b-button
              >
            </b-form>
          </b-card-body>
        </b-collapse>
      </b-card>
    </b-row>
  </b-container>
</template>

<script>
import plantNoteService from "../services/PlantNoteService";
import plantService from "../services/PlantService";
import treatmentService from "../services/TreatmentService";
import photoService from "../services/PhotoService";
// import EditNote from "./EditNote.vue";
import EditPlant from "./EditPlant.vue";
import NoteForm from "./NoteForm.vue";
import PlantCard from "./PlantCard.vue";
export default {
  name: "plant-detail",
  components: { EditPlant, NoteForm, PlantCard },
  data() {
    return {
      note: {},
      savedNote: "",
      savedDate: "",
      savedName: "",
      savedUrl: "",
      savedLocation: "",
      showNoteForm: false,
      isEditingNote: false,
      isEditingPlant: false,
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
    selectImg(careType) {
      return careType === "misted"
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
    toggleInfoForm() {
      this.showInfoForm = !this.showInfoForm;
    },
    toggleDateForm(plant) {
      this.savedDate = plant.plantAge;
    },
    toggleNoteForm(note) {
      this.savedNote = note.note;
      this.savedDate = note.createdOn;
      this.showNoteForm = note.noteId;
    },
    toggleEditForm(plant) {
      this.savedDate = plant.plantAge;
      this.savedName = plant.plantName;
      this.savedUrl = plant.infoUrl;
      this.savedLocation = plant.indoor;
    },
    cancelForm(event, object) {
      event.target.id === "cancelDateForm"
        ? this.$root.$emit(
            "bv::toggle::collapse",
            "collapse-date-form",
            (object.plantAge = this.savedDate)
          )
        : event.target.id === "cancelEdit"
        ? this.$root.$emit(
            "bv::toggle::collapse",
            "collapse-edit-note-form",
            (object.note = this.savedNote),
            (this.showNoteForm = false)
          )
        : event.target.id === "cancelEditForm"
        ? this.$root.$emit(
            "bv::toggle::collapse",
            "collapse-edit-form",
            ((object.plantAge = this.savedDate),
            (object.plantName = this.savedName),
            (object.infoUrl = this.savedUrl),
            (object.indoor = this.savedLocation))
          )
        : alert(event.target.id);
    },
    editPlant() {
      plantService
        .editPlant(this.plant)
        .then((response) => {
          if (response.status == 200) {
            this.$store.commit("EDIT_PLANT", this.plant);
            this.showInfoForm = false;
            // Close the 'edit date form' after updating:
            this.$root.$emit("bv::toggle::collapse", "collapse-date-form");
          }
        })
        .catch((err) => {
          alert(err + " problem editing plant!");
        });
    },
    deletePlant() {
      this.modal = "";
      this.$bvModal
        .msgBoxConfirm(
          "Are you sure you want to delete this plant and all associated treatments?"
        )
        .then((value) => {
          this.modal = value;
          if (value === true) {
            plantService
              .deletePlant(this.plantId)
              .then((response) => {
                if (response.status == 204) {
                  this.$store.commit("DELETE_PLANT", this.plantId);
                  this.$router.push("/plants");
                }
              })
              .catch((err) => {
                alert(err + " problem deleting plant!");
              });
          }
        });
    },
    useCloudinary(event, item) {
      photoService.myWidget.open();
      this.$store.commit("SET_CLOUDINARY_SOURCE", event.target.id);
      this.$store.commit("SET_OBJECT", item);
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
    plantNoteService
      .getNotes()
      .then((response) => {
        if (response.status == 200) {
          this.$store.commit("SET_NOTES", response.data);
        }
      })
      .catch((err) => {
        alert(err + " problem getting notes!");
      });
  },
  mounted() {
    this.$root.$on("bv::collapse::state", (collapseId, isJustShown) => {
      if (collapseId == "collapse-note-edit-form" && isJustShown) {
        this.isEditingNote = true;
      } else if (collapseId == "collapse-edit-form" && isJustShown) {
        this.isEditingPlant = true;
      } else {
        this.isEditingNote = false;
        this.isEditingPlant = false;
      }
    });
  },
};
</script>

<style>
#plant-detail > .row.plant-card {
  margin-top: 1rem;
}
#toggleBtn {
  background-color: var(--orange);
  margin-top: 1rem;
}
#toggleBtn .badge {
  background-color: var(--light);
  color: var(--dark);
}
#toggleFormBtn {
  background-color: var(--green);
  color: var(--platinum);
  border: 1px solid var(--orange);
  font-size: 1rem;
  min-width: 142px;
}
#deleteTreatment {
  background-color: var(--orange);
}
.delete {
  display: flex;
  justify-content: end;
}
.avatar-icon-link {
  margin-left: 0.3rem;
  background-color: var(--green);
  border: 1px solid var(--orange);
}
.card#note-card {
  margin-bottom: 1rem;
  width: 100%;
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
#add-note-btn {
  margin-top: 1rem;
  margin-bottom: 1rem;
}
#add-note-form {
  margin-bottom: 1rem;
  padding-top: 0.3rem;
  padding-bottom: 0.6rem;
}
#collapse-note-form {
  background-color: var(--light-shade1);
  border-left: 5px solid var(--green);
}
.btn#cancelEdit,
.btn#cancelNote,
.btn#cancelDateForm,
#cancelEditForm {
  margin-right: 1%;
  background-color: var(--gray);
}
#dateFormBtnGroup {
  margin-top: 0.3rem;
}
</style>