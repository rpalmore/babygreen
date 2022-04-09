<template>
  <b-container id="about" fluid>
    <p class="section-header">Tools for a lush life</p>
    <div class="section-text">
      <b-row>
        <b-col class="left-col">
          <b-carousel
      id="carousel-1"
      v-model="slide"
      :interval="4000"
      controls
      indicators
      background="#ababab"
      style="text-shadow: 1px 1px 2px #333;"
      @sliding-start="onSlideStart"
      @sliding-end="onSlideEnd"
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
    </div>
    <!-- <b-img
      :src="require('../assets/avinash-kumar-1-unsplash.webp')"
      fluid
      rounded
    >
    </b-img> -->
  </b-container>
</template>

<script>
export default {
  name: "about",
};
</script>

<style>
.section-header {
  /* text-align: center; */
  padding-top: 20px;
  font-size: 1.5rem;
  font-weight: 300;
  color: var(--light);
}
#about .section-text {
  padding: 20px;
  background-color: var(--green);
  border-radius: 0.25rem;
  border: 1px solid var(--dark);
  border-right: 5px solid var(--dark);
  border-left: 5px solid var(--dark);
  margin-bottom: 35px;
}
.section-text .row {
  align-items: center;
}
.left-col {
  border-right: 1px solid var(--grey);
}
.right-col {
  /* border-left: 4px solid var(--grey); */
}
#about img {
  border: 1px solid var(--dark);
}
</style>