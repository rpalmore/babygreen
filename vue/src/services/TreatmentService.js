import axios from 'axios';

export default {
    createTreatment(treatment) {
        return axios.post('/createTreatment', treatment)
    },

    getAllTreatments() {
        return axios.get('/care')
    },

    getSinglePlantTreatments(plantId) {
        return axios.get('/plants/' + plantId + '/care');
    },

    deleteTreatment(plantId, careId) {
        return axios.delete('/deleteTreatment/' + plantId + '/' + careId)
    }

}