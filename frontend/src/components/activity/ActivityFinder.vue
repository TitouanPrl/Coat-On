<template>
  <h2>Activity recommendation</h2>
  <form @submit.prevent="handleSubmit">
    <div>
      <label for="city">City</label>
      <select id="city" @change="updateLocation($event.target.value)">
        <option v-for="city in cities" :key="city.name" :value="city.name">
          {{ city.name }}
        </option>
      </select>
    </div>
    <div>
      <label for="startTime">Start Time</label>
      <input
        id="startTime"
        type="datetime-local"
        v-model="location.startTime"
        required
      />
    </div>
    <div>
      <label for="endTime">End Time</label>
      <input
        id="endTime"
        type="datetime-local"
        v-model="location.endTime"
        required
      />
    </div>
    <button type="submit">Find an activity</button>
  </form>
</template>

<script>
import recommendationService from "@/services/recommendationService";
export default {
  name: "ActivityFinder",
  data() {
    return {
      location: {
        longitude: 24.7536, // Tallinn
        latitude: 59.437,
        radius: 15,
        startTime: "",
        endTime: "",
      },
      cities: [
        { name: "Tallinn", longitude: 24.7536, latitude: 59.437 },
        { name: "Tartu", longitude: 26.715, latitude: 58.3776 },
        { name: "Tokyo", longitude: 139.6917, latitude: 35.6895 },
        { name: "Seoul", longitude: 126.978, latitude: 37.5665 },
        { name: "Los Angeles", longitude: -118.2437, latitude: 34.0522 },
        { name: "London", longitude: -0.1276, latitude: 51.5074 },
        { name: "Anadyr", longitude: 177.5103, latitude: 64.73424 },
        { name: "Managua", longitude: -86.251389, latitude: 12.136389 },
      ],
    };
  },
  methods: {
    updateLocation(selectedCity) {
      const city = this.cities.find((c) => c.name === selectedCity);
      if (city) {
        this.location.longitude = city.longitude;
        this.location.latitude = city.latitude;
      }
    },
    handleSubmit() {
      // Format the date-time strings
      const formatDateTime = (datetime) => {
        if (!datetime) return ""; // Check for empty or undefined datetime values
        let formattedDateTime = datetime.replace("T", " ");
        if (formattedDateTime.length === 16) {
          // "yyyy-MM-dd HH:mm" length is 16
          formattedDateTime += ":00"; // Append seconds if they are missing
        }
        return formattedDateTime;
      };

      this.location.startTime = formatDateTime(this.location.startTime);
      this.location.endTime = formatDateTime(this.location.endTime);
      recommendationService["getRecommendedActivity"](
        this.location.longitude,
        this.location.latitude,
        this.location.radius,
        this.location.startTime,
        this.location.endTime
      )
        .then((response) => {
          console.log("This is the data", response.data);
          this.$emit("recommendActivity", response.data.activities);
        })
        .catch((error) => {
          console.error("Error finding activity:", error);
          alert("Failed to find an activity");
        });
    },
  },
};
</script>
