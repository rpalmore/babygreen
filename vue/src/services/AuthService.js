import axios from 'axios';

export default {
  login(user) {
    return axios.post('/login', user)
  },
  register(user) {
    return axios.post('/register', user)
  },
  updateUser(user) {
    return axios.put('/updateUser', user);
  },
  updatePassword(user) {
    return axios.put('/updatePassword', user);
  }
}
