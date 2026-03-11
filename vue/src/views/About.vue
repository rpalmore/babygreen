<template>
  <b-container id="about" fluid>

    <Login v-show="this.$route.name == 'login'"/>
    <Register v-show="this.$route.name == 'register'"/>
    <p class="section-header-about-how">How it works ...</p>

    <b-card-group deck>
      <b-card
        title="list"
        :img-src="require('../assets/severin-candrian-unsplash-300.jpg')"
        img-alt="(Photo by Severin Candrian / Unsplash)"
        img-top
      >
        <b-card-text>
          Your site. Your plants. We create a dedicated page for each of them,
          where you can add photos, notes, keep a water log and track other
          care.
          <!-- We also keep tabs on each plant’s location&mdash;indoors or outside&mdash;and how long it’s been in your care. -->
        </b-card-text>
      </b-card>

      <b-card
        title="log"
        :img-src="
          require('../assets/severin-candrian-watering-unsplash-300.jpg')
        "
        img-alt="(Photo by Severin Candrian / Unsplash)"
        img-top
      >
        <b-card-text>
          With a quick click, you can record waterings and other treatments,
          such as repotting and feeding, so you never lose track of their
          special needs.
        </b-card-text>
      </b-card>

      <b-card
        title="love"
        :img-src="require('../assets/ranurte-unsplash-300.jpg')"
        img-alt="(Photo by Ranurte / Unsplash)"
        img-top
      >
        <b-card-text>
          Congrats&mdash;you’ve just upped your green game! Now you can embrace
          the benefits of being a plant parent without fretting over their
          survival.
        </b-card-text>
      </b-card>
    </b-card-group>
  </b-container>
</template>

<script>
import Login from "./Login.vue";
import Register from "./Register.vue";
import Vue from "vue";
import { CardPlugin } from "bootstrap-vue";
export default {
  components: { Login, Register },
  name: "about",
  created() {
    Vue.use(CardPlugin);
  }
};
</script>

<style>
.section-header-about {
  margin-top: 20px;
  font-size: 1.5rem;
  font-weight: 300;
  color: var(--dark);
}
.section-header-about-how {
  margin-top: 20px;
  font-family: "Lobster", cursive;
  font-size: 2rem;
}
#about .card-title {
  padding-bottom: 0.3rem;
  border-bottom: 1px solid var(--light);
  font-family: "Lobster", cursive;
  font-size: 2rem;
}
</style>