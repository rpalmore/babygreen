<template>
  <b-container fluid id="register">
    <p class="section-header-about">
      {{ registrationErrors ? registrationErrorMsg : "Create Account" }}
    </p>
    <b-form inline @submit.prevent="register">
      <label for="username" class="sr-only">Username</label>
      <b-form-input
        type="text"
        id="username"
        class="mb-2 mr-sm-2 mb-sm-0"
        placeholder="Username"
        v-model="user.username"
        required
      ></b-form-input>
      <label for="username" class="sr-only">Email</label>
      <b-form-input
        type="email"
        id="email"
        class="mb-2 mr-sm-2 mb-sm-0"
        placeholder="you@email.com"
        v-model="user.email"
        required
      ></b-form-input>
      <label for="password" class="sr-only">Password</label>
      <b-form-input
        type="password"
        id="password"
        class="mb-2 mr-sm-2 mb-sm-0"
        placeholder="Password"
        v-model="user.password"
        required
      ></b-form-input>
      <b-form-input
        type="password"
        id="confirmPassword"
        class="mb-2 mr-sm-2 mb-sm-0"
        placeholder="Confirm Password"
        v-model="user.confirmPassword"
        required
      ></b-form-input>
      <b-button class="default" type="submit"> Go </b-button>
    </b-form>
    <About />
  </b-container>
</template>

<script>
import authService from "../services/AuthService";
import About from "./About.vue";

export default {
  name: "register",
  components: { About },
  data() {
    return {
      user: {
        username: "",
        email: "",
        password: "",
        confirmPassword: "",
        role: "user",
      },
      registrationErrors: false,
      registrationErrorMsg: "There were problems registering this user.",
    };
  },
  methods: {
    register() {
      if (this.user.password != this.user.confirmPassword) {
        this.registrationErrors = true;
        this.registrationErrorMsg = "Password and Confirm Password do not match.";
      } else {
        authService
          .register(this.user)
          .then((response) => {
            if (response.status == 201) {
              this.$router.push({
                path: "/login",
                query: { registration: "success" },
              });
            }
          })
          .catch((error) => {
            const response = error.response;
            this.registrationErrors = true;
            if (response.status === 400) {
              if (response.data.message == "User Already Exists.") {
                this.registrationErrorMsg = "Account already exists. Please sign in.";
              } else {
                this.registrationErrorMsg = "Bad Request: Validation Errors";
              }
            }
          });
      }
    },
    clearErrors() {
      this.registrationErrors = false;
      this.registrationErrorMsg = "There were problems registering this user.";
    },
  },
};
</script>