import axios from 'axios';

export default {
  wakeUp() {
    return axios.get('/wake')
  }
}