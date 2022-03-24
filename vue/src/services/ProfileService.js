import axios from 'axios';

export default {
    getProfile() {
        return axios.get('/profile');
    },
    createProfile(profile) {
        return axios.post('/createProfile', profile);
    },
    editProfile(profile) {
        return axios.put('/editProfile', profile);
    },
    deleteProfile(username) {
        return axios.delete('/deleteProfile/' + username);
    }
}