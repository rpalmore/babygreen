<template>
  <b-container fluid id="login">
    <p class="section-header">
      {{
        invalidCredentials
          ? "Invalid username and password!"
          : this.$route.query.registration
          ? "Thank you for registering, please sign in."
          : "Please sign in"
      }}
    </p>
    <b-form inline @submit.prevent="login">
      <label class="sr-only" for="username">Username</label>
      <b-form-input
        id="username"
        class="mb-2 mr-sm-2 mb-sm-0"
        placeholder="Username"
        type="text"
        v-model="user.username"
        required
      ></b-form-input>

      <label class="sr-only" for="password">Password</label>

      <b-form-input
        id="password"
        class="mb-2 mr-sm-2 mb-sm-0"
        placeholder="Password"
        type="password"
        v-model="user.password"
        required
      >
      </b-form-input>
      <b-button class="default" type="submit">Submit</b-button>
    </b-form>
  </b-container>
</template>

<script>
import authService from "../services/AuthService";
import profileService from "../services/ProfileService";
import plantService from "../services/PlantService";

export default {
  name: "login",
  components: {},
  data() {
    return {
      user: {
        username: "",
        password: "",
      },
      invalidCredentials: false,
    };
  },
  methods: {
    login() {
      authService
        .login(this.user)
        .then((response) => {
          if (response.status == 200) {
            this.$store.commit("SET_AUTH_TOKEN", response.data.token);
            this.$store.commit("SET_USER", response.data.user);
            profileService.getProfile().then((response) => {
              if (response.status == 200) {
                this.$store.commit("SET_PROFILE", response.data);
              }
            });
            plantService.getAllPlants().then((response) => {
              if (response.status == 200) {
                this.$store.commit("SET_PLANTS", response.data);
              }
            });
            if (this.$store.state.profile.favePlant != undefined) {
              this.$router.push("/plants");
            } else {
              this.$router.push("/profile");
            }
          }
        })
        .catch((error) => {
          const response = error.response;
          if (response.status === 401) {
            this.invalidCredentials = true;
          }
        });
    },
  },
};
</script>
