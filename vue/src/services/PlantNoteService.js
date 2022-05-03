import axios from 'axios';

export default {

    getNotes() {
        return axios.get('/plants/notes');
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