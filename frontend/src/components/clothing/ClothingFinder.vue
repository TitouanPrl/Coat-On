<template>
  <h2>Clothing recommendation</h2>
  <form @submit.prevent="handleSubmit">
    <div>
      <label for="city">City</label>
      <select id="city" @change="updateLocation($event.target.value)">
        <option v-for="city in cities" :key="city.name" :value="city.name">
          {{ city.name }}
        </option>
      </select>
    </div>
    <button type="submit">Find an outfit!</button>
  </form>
</template>

<script>
import recommendationService from "@/services/recommendationService";
import jwt_decode from "jwt-decode";
export default {
  name: "ClothingFinder",
  data() {
    return {
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
      location: {
        longitude: 24.7536, // Tallinn
        latitude: 59.437,
      },
      userId: jwt_decode(localStorage.getItem("jwtToken")).userId,
    };
  },
  props: {
    userId: String,
  },
  methods: {
    updateLocation(selectedCity) {
      const city = this.cities.find((c) => c.name === selectedCity);
      if (city) {
        this.location.longitude = city.longitude;
        this.location.latitude = city.latitude;
      }
      console.log("I was called", this.location.longitude);
    },
    handleSubmit() {
      recommendationService["getRecommendedClothing"](
        this.location.longitude,
        this.location.latitude,
        this.userId
      )
        .then((response) => {
          console.log("This is the data", response.data);
          this.$emit("recommendClothing", response.data.clothes);
        })
        .catch((error) => {
          console.error("Error saving clothing:", error);
          alert("Failed to find clothing");
        });
    },
  },
};
</script>
