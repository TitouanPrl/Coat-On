import axios from "axios";

const apiUrl = "http://localhost:8080/api/recommendation";
const apiUrlLocal = "http://localhost:8085/api/recommendation";

export default {
  getRecommendedActivity(longitude, latitude, radius, startTime, endTime) {
    const encodedStartTime = encodeURIComponent(startTime);
    const encodedEndTime = encodeURIComponent(endTime);
    return axios.get(
      `${apiUrlLocal}/activity?lat=${latitude}&long=${longitude}&range=${radius}&start=${encodedStartTime}&end=${encodedEndTime}`
    );
  },
  getRecommendedClothing(longitude, latitude, userId) {
    return axios.get(
      `${apiUrlLocal}/clothing/current?lat=${latitude}&long=${longitude}&userId=${userId}`
    );
  },
};
