<template>
  <b-container id="login">
    <p class="section-header-about">
      {{
        invalidCredentials
          ? "Invalid username and/or password. Please try again:"
          : errorLoggingIn ? "Error logging in. Please try again:"
          : this.$route.query.registration == "success"
          ? "Thank you for registering! Please sign in:"
          : "Please sign in:"
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
      <b-button
        v-if="!loggingIn"
        class="default mb-2 mr-sm-2 mb-sm-0"
        type="submit"
        >Enter</b-button
      >
      <div v-if="loggingIn" class="spinner">
        <b-spinner label="Loading..."></b-spinner>
      </div>
    </b-form>
  </b-container>
</template>

<script>
import wakeService from "../services/WakeService";
import authService from "../services/AuthService";
import { BSpinner } from "bootstrap-vue";
export default {
  name: "login",
  components: { BSpinner },
  data() {
    return {
      user: {
        username: "",
        password: "",
      },
      loggingIn: false,
      invalidCredentials: false,
      errorLoggingIn: false
    };
  },
  methods: {
    login() {
      this.loggingIn = true;
      authService
        .login(this.user)
        .then((response) => {
          if (response.status == 200) {
            this.$store.commit("SET_AUTH_TOKEN", response.data.token);
            this.$store.commit("SET_USER", response.data.user);
            this.$router.push("/plants");
            this.loggingIn = false;
          }
        })
        .catch((error) => {
          const response = error?.response;
          const errorCodes = [401, 403];
          if (errorCodes.includes(response?.status)) {
            this.invalidCredentials = true;
            this.loggingIn = false;
          } else {
            this.errorLoggingIn = true;
            this.loggingIn = false;
          }
        });
    },
  },
  created() {
    wakeService.wakeUp()
    .then(this.$store.commit("SET_ACTIVE", true))
    .catch((err) => {
      /* eslint no-console: ["error", { allow: ["error"] }] */
      console.error(err + " problem waking backend!");
    });
  },
};
</script>

<style scoped>
#login {
  margin-left: -15px;
}
.forgot-password {
  font-weight: 300;
  color: var(--dark);
  padding: 6px 3px 6px 3px;
}
.forgot-password:hover {
  color: var(--dark);
  background-color: var(--green);
  border-radius: 0.25rem;
}
.spinner {
  padding-left: 2%;
}
</style>
