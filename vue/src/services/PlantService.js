import axios from 'axios';

export default {
    getPlant(plantId) {
        return axios.get('/plants/' + plantId);
    },

    getAllPlants() {
        return axios.get('/plants');
    }, 

    createPlant(plant) {
        return axios.post('/createPlant', plant);
    },

    editPlant(plant) {
        return axios.put('/editPlant', plant);
    },

    deletePlant(plantId) {
        return axios.delete('/deletePlant/' + plantId);
    }

}