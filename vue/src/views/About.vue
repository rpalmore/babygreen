<template>
  <b-container id="about" fluid>
    <p class="section-header-about">How it works ...</p>

    <b-card-group deck>
      <b-card
        title="list"
        :img-src="require('../assets/severin-candrian-unsplash-300.webp')"
        img-alt="(Photo by Severin Candrian / Unsplash)"
        img-top
      >
        <b-card-text>
          Your site. Your plants. We’ll create a dedicated page for each of
          them, where you can add photos, notes and a link to your favorite
          external resource for tips. We'll even track how long each plant has
          been in your care, and whether it’s living indoors or outside.
        </b-card-text>
      </b-card>

      <b-card
        title="log"
        :img-src="
          require('../assets/severin-candrian-watering-unsplash-300.webp')
        "
        img-alt="(Photo by Severin Candrian / Unsplash)"
        img-top
      >
        <b-card-text>
          With the click of a button, you can record every plant treatment, such
          as watering, misting and feeding, so you never lost track of their
          care and special needs&mdash;however temperamental they may be.
        </b-card-text>
      </b-card>

      <b-card
        title="love"
        :img-src="require('../assets/ranurte-unsplash-300.webp')"
        img-alt="(Photo by Ranurte / Unsplash)"
        img-top
      >
        <b-card-text>
          Congrats&mdash;you’ve just upped your plant parent powers! Now you can
          embrace the benefits of your green space without guilty feelings
          taking root over the last time you gave your cactus a drink.
        </b-card-text>
      </b-card>
    </b-card-group>
  </b-container>
</template>

<script>
export default {
  name: "about",
};
</script>

<style>
.section-header-about {
  margin-top: 20px;
  font-size: 1.5rem;
  font-weight: 300;
  color: var(--dark);
}

#about .section-header-about {
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