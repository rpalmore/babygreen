<template>
  <b-container id="about" fluid>
    <p class="section-header-about">How it works ...</p>

    <b-card-group deck>
      <b-card
        title="Create"
        :img-src="require('../assets/ranurte-unsplash-300.webp')"
        img-alt="Image"
        img-top
      >
        <b-card-text>
          A dedicated page for each of your plants can include photos,
          notes about their growth and a link to your favorite site for
          reminders about their care. We'll also track how long each plant has
          been in your care, and whether it lives indoors or outside.
        </b-card-text>
      </b-card>

      <b-card
        title="Treat"
        :img-src="
          require('../assets/severin-candrian-watering-unsplash-300.webp')
        "
        img-alt="Image"
        img-top
      >
        <b-card-text>
          Log every treatment with the click of a button so you never lose track
          of each plant's special needs. Whether you watered, sprayed, repotted,
          fertilized or treated for pests, we've got you&mdash;and your plant
          baby&mdash;covered.
        </b-card-text>
      </b-card>

      <b-card
        title="Track"
        :img-src="require('../assets/severin-candrian-unsplash-300.webp')"
        img-alt="Image"
        img-top
      >
        <b-card-text>
          Your plant care page offers an at-a-glance list of recent treatments.
          Your own profile page includes quick-hit data on your plant family,
          too. Is your polka dot plant looking extra perky? Snap a pic and add a
          note for future reference.
        </b-card-text>
      </b-card>
    </b-card-group>

    <!-- <b-card
        title="Admire"
        :img-src="require('../assets/cleo-stracuzza-unsplash_300.webp')"
        img-alt="Image"
        img-top
        style="max-width: 640px"
      >
        <b-card-text>
          Now that you've got a handle on your plant care&mdash;and everyone has been watered and fed&mdash;sit back and enjoy your lush life!
        </b-card-text>
      </b-card> -->

    <!-- <div class="section-text">
      <b-row>
        <b-col class="left-col">
          <b-carousel id="carousel-1" v-model="slide" :interval="4000" controls indicators background="#ababab" style="text-shadow: 1px 1px 2px #333;" @sliding-start="onSlideStart" @sliding-end="onSlideEnd"
    >
      <b-carousel-slide :img-src="require('../assets/aj-green-1024_480.webp')"></b-carousel-slide>
      <b-carousel-slide :img-src="require('../assets/huy-phan-1024_480-unsplash.webp')"></b-carousel-slide>
      <b-carousel-slide :img-src="require('../assets/leonardo-iheme-1024_480-unsplash.webp')"></b-carousel-slide>
        </b-col>
        <b-col class="right-col"> 
          Euismod nisi porta lorem mollis aliquam ut porttitor leo.
          Ac tortor vitae purus faucibus ornare suspendisse sed nisi lacus. Duis
          at tellus at urna. Non blandit massa enim nec dui nunc mattis enim ut.
          Ipsum suspendisse ultrices gravida dictum fusce ut placerat orci.
        </b-col>
      </b-row>
    </div> -->
  </b-container>
</template>

<script>
export default {
  name: "about",
  // data() {
  //   return {
  //     mainProps: { blank: true, width: 75, height: 75, class: 'm1' },
  //   }
  // }
};
</script>

<style>
.section-header-about {
  margin-top: 20px;
  font-size: 1.5rem;
  font-weight: 300;
  color: var(--light);
  color: var(--dark);
}
#about .section-text {
  padding: 20px;
  background-color: var(--green);
  border-radius: 0.25rem;
  /* border: 1px solid var(--dark); */
  border-right: 5px solid var(--grey);
  border-left: 5px solid var(--grey);
  /* border: 3px solid var(--orange); */
  margin-bottom: 35px;
}
.section-text .row {
  align-items: center;
}
.left-col {
  border-right: 1px solid var(--light);
}
.right-col {
  /* border-left: 4px solid var(--grey); */
}
#about img {
  /* border: 1px solid var(--dark); */
}
</style>