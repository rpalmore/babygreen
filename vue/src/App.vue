<template>
  <div id="app">
    <div id="nav">
      <router-link v-bind:to="{ name: 'home' }">Home</router-link>&nbsp;
      <router-link
        v-bind:to="{ name: 'logout' }"
        v-if="$store.state.token != ''"
        >&nbsp;Logout</router-link
      >&nbsp;
      <router-link
        v-bind:to="{ name: 'profile' }"
        v-if="$store.state.token != ''"
        >&nbsp;Profile</router-link
      >&nbsp;
      <router-link
        v-bind:to="{ name: 'plants' }"
        v-if="$store.state.token != ''"
        >&nbsp;My Plants</router-link
      >&nbsp;
      <router-link v-bind:to="{ name: 'care' }" v-if="$store.state.token != ''"
        >&nbsp;Plant Care</router-link
      >
    </div>
    <router-view />
  </div>
</template>
