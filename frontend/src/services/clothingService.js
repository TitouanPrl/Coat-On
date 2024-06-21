// src/services/clothingService.js
import axios from 'axios';

const apiUrl = 'http://localhost:8080/api/user';
const apiUrlLocal = 'http://localhost:8083/api/user';

export default {
  getAllClothing(userId) {
    return axios.get(`${apiUrlLocal}/${userId}/clothing`);
  },
  getClothing(userId, clothingId) {
    return axios.get(`${apiUrlLocal}/${userId}/clothing/${clothingId}`);
  },
  addClothing(userId, clothing) {
    return axios.post(`${apiUrlLocal}/${userId}/clothing`, clothing);
  },
  updateClothing(userId, clothingId, clothing) {
    return axios.put(`${apiUrlLocal}/${userId}/clothing/${clothingId}`, clothing);
  },
  deleteClothing(userId, clothingId) {
    return axios.delete(`${apiUrlLocal}/${userId}/clothing/${clothingId}`);
  }
}
