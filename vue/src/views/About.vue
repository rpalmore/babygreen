<template>
  <b-container id="about" fluid>
    <p class="section-header-about">Tools for a lush life</p>
  
  <b-card-group deck>
    <b-card title="Title" :img-src="require('../assets/cleo-stracuzza-unsplash.webp')" img-alt="Image" img-top>
      <b-card-text>
        This is a wider card with supporting text below as a natural lead-in to additional content.
        This content is a little bit longer.
      </b-card-text>
      <!-- <template #footer>
        <small class="text-muted">Last updated 3 mins ago</small>
      </template> -->
    </b-card>

    <b-card title="Title" img-src="https://picsum.photos/300/300/?image=41" img-alt="Image" img-top>
      <b-card-text>
        This card has supporting text below as a natural lead-in to additional content.
      </b-card-text>
      <!-- <template #footer>
        <small class="text-muted">Last updated 3 mins ago</small>
      </template> -->
    </b-card>

    <b-card title="Title" img-src="https://picsum.photos/300/300/?image=41" img-alt="Image" img-top>
      <b-card-text>
        This is a wider card with supporting text below as a natural lead-in to additional content.
        This card has even longer content than the first to show that equal height action.
      </b-card-text>
      <!-- <template #footer>
        <small class="text-muted">Last updated 3 mins ago</small>
      </template> -->
    </b-card>
  </b-card-group>

    <div class="section-text">
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
    </div>
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
  border: 1px solid var(--dark);
}
</style>