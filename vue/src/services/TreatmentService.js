import axios from 'axios';

export default {
    createTreatment(treatment) {
        return axios.post('/createTreatment', treatment)
    },

    getAllTreatments() {
        return axios.get('/care')
    },

    getLatestWaterings() {
        return axios.get('/care/watered');
    },

    getSinglePlantTreatments(plantId) {
        return axios.get('/plants/' + plantId + '/care');
    },

    getLatestTreatment() {
        return axios.get('/care/latest');
    },

    deleteTreatment(plantId, careId) {
        return axios.delete('/deleteTreatment/' + plantId + '/' + careId)
    },

    deleteTreatmentByDate(date) {
        return axios.delete('/deleteTreatmentByDate/' + date)
    }

}