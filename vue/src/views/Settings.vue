<template>
  <b-container fluid id="settings">
    <b-row align-h="center">
      <p class="section-header">Settings</p>
    </b-row>
    <p class="subsection-header">
      {{ emailValidationErrors ? emailValidationErrorMsg : "Email" }}
    </p>
    <b-container class="list-container">
      <b-list-group>
        <b-list-group-item
          v-if="!this.isClickedEmailForm"
          class="settings-list"
        >
          <span class="list-data">{{ this.user.email }}</span>
          <b-button
            class="card-footer-btn"
            size="sm"
            @click="toggleForm('email')"
            >Edit email
            <b-avatar
              size="sm"
              icon="pencil-fill"
              class="avatar-icon-pencil"
            ></b-avatar
          ></b-button>
        </b-list-group-item>
        <b-list-group-item v-if="this.isClickedEmailForm" class="settings-list">
          <b-form
            id="email-reset"
            inline
            @submit.prevent="updateUserInfo('email')"
            v-if="this.isClickedEmailForm"
          >
            <label class="sr-only" for="email">Email</label>
            <b-form-input
              v-model="email"
              id="email"
              class="mb-2 mr-sm-2 mb-sm-0"
              placeholder="Updated email"
              type="email"
              required
            ></b-form-input>
            <b-button class="default mb-2 mr-sm-2 mb-sm-0" type="submit"
              >Update</b-button
            >
            <b-button
              class="default mb-2 mr-sm-2 mb-sm-0"
              type="cancel"
              id="cancel"
              @click="toggleForm('email')"
              >Cancel</b-button
            >
          </b-form>
        </b-list-group-item>
      </b-list-group>
    </b-container>
    <p class="subsection-header">Username</p>
    <b-container class="list-container">
      <b-list-group>
        <b-list-group-item class="settings-list">
          <span v-b-tooltip title="Username cannot be changed">{{
            user.username
          }}</span>
        </b-list-group-item>
      </b-list-group>
    </b-container>
    <p class="subsection-header">
      {{ passwordValidationErrors ? passwordValidationErrorMsg : "Password" }}
    </p>
    <b-container class="list-container">
      <b-list-group>
        <b-list-group-item v-if="!this.isClickedPWForm" class="settings-list">
          <span
            class="list-data"
            id="password-placeholder"
            v-b-tooltip 
            title="Not for display"
            >&bull;&bull;&bull;&bull;&bull;&bull;&bull;&bull;&bull;&bull;&bull;&bull;&bull;&bull;&bull;&bull;&bull;</span
          >
          <b-button
            class="card-footer-btn"
            size="sm"
            @click="toggleForm('password')"
            >Update password
            <b-avatar
              size="sm"
              icon="pencil-fill"
              class="avatar-icon-pencil"
            ></b-avatar
          ></b-button>
        </b-list-group-item>
        <b-list-group-item v-if="this.isClickedPWForm" class="settings-list">
          <b-form
            id="password-reset"
            inline
            @submit.prevent="updateUserInfo('password')"
            v-if="this.isClickedPWForm"
          >
            <label class="sr-only" for="password">Password</label>
            <b-form-input
              v-model="password"
              id="password"
              class="mb-2 mr-sm-2 mb-sm-0"
              placeholder="Password"
              type="password"
              required
            ></b-form-input>
            <label class="sr-only" for="email">Confirm password</label>
            <b-form-input
              v-model="confirmPassword"
              id="confirmPassword"
              class="mb-2 mr-sm-2 mb-sm-0"
              placeholder="Confirm password"
              type="password"
              required
            ></b-form-input>
            <b-button class="default mb-2 mr-sm-2 mb-sm-0" type="submit"
              >Update</b-button
            >
            <b-button
              class="default mb-2 mr-sm-2 mb-sm-0"
              type="cancel"
              id="cancel"
              @click="toggleForm('password')"
              >Cancel</b-button
            >
          </b-form>
        </b-list-group-item>
      </b-list-group>
    </b-container>
  </b-container>
</template>

<script>
import authService from "../services/AuthService";
import Vue from 'vue';
import { ModalPlugin, VBTooltip } from 'bootstrap-vue';
export default {
  name: "settings",
  components: {},
  data() {
    return {
      modal: "",
      isClickedEmailForm: false,
      isClickedPWForm: false,
      passwordValidationErrors: false,
      password: "",
      confirmPassword: "",
      emailValidationErrors: false,
      email: "",
      editedUser: {
        authorities:
          this.$store.state.user.authorities[0]?.name ||
          this.$store.state.user.authorities,
        email: this.email,
        password: this.password,
        username: this.$store.state.user.username,
        id: this.$store.state.user.id,
      },
    };
  },
  computed: {
    user() {
      return this.$store.state.user;
    },
  },
  methods: {
    toggleForm(event) {
      event == "email"
        ? (this.isClickedEmailForm = !this.isClickedEmailForm)
        : (this.isClickedPWForm = !this.isClickedPWForm);
      this.resetForm(event);
    },
    resetForm(event) {
      event == "email"
        ? ((this.email = ""), (this.emailValidationErrors = false))
        : (this.password = ""),
        (this.confirmPassword = ""),
        (this.passwordValidationErrors = false);
    },
    updateUserInfo(event) {
      event == "password"
        ? this.checkPasswordValidation()
        : this.checkEmailValidation();
      if (
        (event == "password" && !this.passwordValidationErrors) ||
        (event == "email" && !this.emailValidationErrors)
      ) {
        this.proceedWithRequest(event);
      }
    },
    proceedWithRequest(event) {
      authService
        .updateUser(this.editedUser)
        .then((response) => {
          if (response.status == 200) {
            if (event == "email") {
              this.$store.commit("SET_USER", this.editedUser);
            }
            this.modal = "";
            this.$bvModal.msgBoxOk("Your " + event + " has been updated.");
            this.toggleForm(event);
          }
        })
        .catch((err) => {
          /* eslint no-console: ["error", { allow: ["error"] }] */
          console.error(err + " problem updating " + event + ".");
          //this.$router.push("/oops"); DO WE NEED TO REROUTE?
          this.toggleForm(event);
        });
    },
    checkPasswordValidation() {
      if (this.password != this.confirmPassword) {
        this.passwordValidationErrors = true;
        this.passwordValidationErrorMsg =
          "Password and Confirm Password do not match.";
      } else if (this.user.username == "guest") {
        this.passwordValidationErrors = true;
        this.passwordValidationErrorMsg =
          "Password update feature is disabled for " + this.user.username + " user.";
      } else {
        this.passwordValidationErrors = false;
        this.editedUser.password = this.password;
        this.editedUser.email = this.user.email;
      }
    },
    checkEmailValidation() {
      if (this.email === this.$store.state.user.email) {
        this.emailValidationErrors = true;
        this.emailValidationErrorMsg = "New email cannot match previous email.";
      } else {
        this.emailValidationErrors = false;
        this.editedUser.email = this.email;
        this.editedUser.password = this.user.password;
      }
    },
  },
  created() {
    Vue.use(ModalPlugin);
    Vue.directive('b-tooltip', VBTooltip);
  }
};
</script>

<style scoped>
.settings-list {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
.list-data {
  margin-right: 1rem;
}
#email-reset,
#password-reset {
  gap: 0.3rem;
}
#password-placeholder {
  color: var(--grey);
}
.card-footer-btn {
  width: auto !important;
}
.btn#cancel {
  background-color: gray;
}
</style>