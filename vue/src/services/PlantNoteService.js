import axios from 'axios';

export default {

    getNotes(plantId) {
        return axios.get(`/plants/${plantId}/notes`);
    },

    createNote(note) {
        return axios.post('/createNote', note);
    },

    editNote(note) {
        return axios.put('/editNote', note);
    },

    deleteNote(noteId) {
        return axios.delete('/deleteNote/' + noteId);
    }
}