<template>
  <b-container fluid id="reset">
    <p class="password-reset-prompt">
      {{
        this.clicked
          ? "Thank you. An email from baby green has been sent to this address with further instructions."
          : "Enter the email you used to create your baby green account:"
      }}
    </p>
    <b-form inline @submit.prevent="passwordReset" v-if="!this.clicked">
      <label class="sr-only" for="email">Email</label>
      <b-form-input
        id="email"
        class="mb-2 mr-sm-2 mb-sm-0"
        placeholder="Email"
        type="email"
        required
      ></b-form-input>
      <b-button class="default" type="submit">Submit</b-button>
    </b-form>
  </b-container>
</template>

<script>
export default {
  name: "reset",
  data() {
    return {
      clicked: false,
    };
  },
  methods: {
    passwordReset() {
      this.clicked = true;
    },
  },
};
</script>

<style>
.password-reset-prompt {
  margin-top: 20px;
  font-weight: 300;
  color: var(--dark);
}
</style>
