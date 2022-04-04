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
    treatments: JSON.parse(localStorage.getItem('treatments') || '{}'),
    notes: JSON.parse(localStorage.getItem('notes') || '{}'),
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
    ADD_PLANT(state, plant) {
      state.plants.unshift(plant);
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
    ADD_TREATMENT(state, treatment) {
      state.treatments.unshift(treatment);
      localStorage.setItem('treatments', JSON.stringify(state.treatments));
    },
    SET_TREATMENTS(state, payload) {
      state.treatments = payload;
      localStorage.setItem('treatments', JSON.stringify(state.treatments));
    },
    ADD_NOTE(state, note) {
      state.notes.unshift(note);
      localStorage.setItem('notes', JSON.stringify(state.notes));
    },
    SET_NOTES(state, notes) {
      state.notes = notes;
      localStorage.setItem("notes", JSON.stringify(state.notes));
    },
    EDIT_NOTE(state, note) {
      state.notes = state.notes.map(n => n.noteId != note.noteId ? n : note);
      localStorage.setItem('notes', JSON.stringify(state.notes));
    },
    DELETE_NOTE(state, noteId) {
      state.notes = state.notes.filter((note) => note.noteId != noteId);
      localStorage.setItem('notes', JSON.stringify(state.notes));
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
