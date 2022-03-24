import Vue from 'vue'
import Vuex from 'vuex'
import axios from 'axios'

Vue.use(Vuex)
Vue.config.devtools = true

/*
 * The authorization header is set for axios when you login but what happens when you come back or
 * the page is refreshed. When that happens you need to check for the token in local storage and if it
 * exists you should set the header so that it will be attached to each request
 */
const currentToken = localStorage.getItem('token')
const currentUser = JSON.parse(localStorage.getItem('user'));

if(currentToken != null) {
  axios.defaults.headers.common['Authorization'] = `Bearer ${currentToken}`;
}

export default new Vuex.Store({
  state: {
    token: currentToken || '',
    user: currentUser || {},
    profile: JSON.parse(localStorage.getItem('profile') || '{}'),
    plants: JSON.parse(localStorage.getItem('plants') || '{}'),
  },
  mutations: {
    SET_AUTH_TOKEN(state, token) {
      state.token = token;
      localStorage.setItem('token', token);
      axios.defaults.headers.common['Authorization'] = `Bearer ${token}`
    },
    SET_USER(state, user) {
      state.user = user;
      localStorage.setItem('user',JSON.stringify(user));
    },
    SET_PROFILE(state, payload) {
      state.profile = payload;
      localStorage.setItem('profile', JSON.stringify(state.profile));
    },
    ADD_PLANT(state, list) {
      state.plants.push(list);
      localStorage.setItem('plants', JSON.stringify(state.plants));
    },
    SET_PLANTS(state, payload) {
      state.plants = payload;
      localStorage.setItem('plants', JSON.stringify(state.plants));
    },
    EDIT_PLANT(state, plant) {
      state.plants = state.plants.map(p => p.plantId != plant.plantId ? p : plant);
      localStorage.setItem('plants', JSON.stringify(state.plants));
    },
    DELETE_PLANT(state, plantId) {
      state.plants = state.plants.filter((plant) => plant.plantId != plantId);
      localStorage.setItem('plants', JSON.stringify(state.plants));
    },
    LOGOUT(state) {
      localStorage.removeItem('token');
      localStorage.removeItem('user');
      state.token = '';
      state.user = {};
      axios.defaults.headers.common = {};
    }
  }
})
