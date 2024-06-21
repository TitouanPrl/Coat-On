import axios from 'axios';

const apiUrl = 'http://localhost:8080/api/user';
const apiUrlLocal = 'http://localhost:8086/api/user';

export default {
  getAllUsers() {
    return axios.get(`${apiUrlLocal}/`);
  },
  getUser(userId) {
    return axios.get(`${apiUrlLocal}/${userId}`);
  },
  addUser(user) {
    return axios.post(`${apiUrlLocal}/`, user);
  },
  updateUser(userId, user) {
    return axios.put(`${apiUrlLocal}/${userId}`, user);
  },
  deleteUser(userId) {
    return axios.delete(`${apiUrlLocal}/${userId}`);
  }
}