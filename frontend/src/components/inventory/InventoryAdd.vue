<template>
  <div class="inventory-add-container">
    <h2>Add to wardrobe</h2>
    <form @submit.prevent="handleSubmit">
      <div>
        <label for="name">Name:</label>
        <input id="name" type="text" v-model="clothing.name" required />
      </div>
      <div>
        <label for="type">Type:</label>
        <select v-model="clothing.type">
          <option v-for="type in clothingTypes" :key="type" :value="type">
            {{ type }}
          </option>
        </select>
      </div>
      <!-- Dropdown for Temperature -->
      <div>
        <label for="temperature">Temperature:</label>
        <select v-model="clothing.tags.temperature">
          <option v-for="temp in temperatureEnums" :key="temp" :value="temp">
            {{ temp }}
          </option>
        </select>
      </div>
      <!-- Dropdown for Precipitation Protection -->
      <div>
        <label for="precipitation">Precipitation Protection:</label>
        <select v-model="clothing.tags.precipitationProtection">
          <option v-for="prec in precipitationEnums" :key="prec" :value="prec">
            {{ prec }}
          </option>
        </select>
      </div>
      <!-- Dropdown for Water Resistance -->
      <div>
        <label for="waterResistance">Water Resistance:</label>
        <select v-model="clothing.tags.waterResistance">
          <option
            v-for="water in waterResistanceEnums"
            :key="water"
            :value="water"
          >
            {{ water }}
          </option>
        </select>
      </div>
      <!-- Dropdown for Wind Resistance -->
      <div>
        <label for="windResistance">Wind Resistance:</label>
        <select v-model="clothing.tags.windResistance">
          <option v-for="wind in windResistanceEnums" :key="wind" :value="wind">
            {{ wind }}
          </option>
        </select>
      </div>
      <!-- Dropdown for Body Section -->
      <div>
        <label for="bodySection">Body Section:</label>
        <select v-model="clothing.tags.bodySection">
          <option v-for="body in bodySectionEnums" :key="body" :value="body">
            {{ body }}
          </option>
        </select>
      </div>
      <button type="submit">Submit</button>
    </form>
  </div>
</template>

<script>
import clothingService from "../../services/clothingService";
import jwt_decode from "jwt-decode";

export default {
  name: "InventoryAdd",
  props: {
    userId: {
      type: String,
      required: true,
    },
    clothingId: String,
    initialData: Object,
  },
  inject: ["updateInventoryView"],
  data() {
    return {
      clothing: this.initialData || {
        name: "",
        type: "",
        tags: {
          temperature: "",
          precipitationProtection: "",
          waterResistance: "",
          windResistance: "",
          bodySection: "",
        },
      },
      userId: jwt_decode(localStorage.getItem("jwtToken")).userId,
      clothingTypes: [
        "SHIRT",
        "T_SHIRT",
        "PANTS",
        "SHORTS",
        "SKIRT",
        "DRESS",
        "SUIT",
        "COAT",
        "JACKET",
        "HOODIE",
        "SWEATER",
        "BLAZER",
        "JEANS",
        "LEGGINGS",
        "JUMPSUIT",
        "ROMPER",
        "TANK_TOP",
        "POLO_SHIRT",
        "CARDIGAN",
        "SWEATSHIRT",
        "TRENCH_COAT",
        "OVERALLS",
        "FORMAL_WEAR",
        "ATHLETIC_WEAR",
        "SWIMWEAR",
        "UNDERWEAR",
        "SLEEPWEAR",
        "OUTERWEAR",
        "ACCESSORY",
        "OTHER",
      ],
      temperatureEnums: ["TEMPERATE", "COLD", "CHILLY", "WARM", "HOT"],
      precipitationEnums: [
        "CLEAR",
        "LIGHT_RAIN",
        "HEAVY_RAIN",
        "SNOWFALL",
        "HAIL",
      ],
      waterResistanceEnums: ["NONE", "SOME", "NORMAL", "HIGH", "HIGHEST"],
      windResistanceEnums: ["CALM", "BREEZE", "WINDY", "GALE", "STORMY"],
      bodySectionEnums: [
        "HEAD",
        "FEET",
        "HANDS",
        "TORSO_INNER",
        "TORSO_OUTER",
        "LEGS",
        "TORSO_AND_LEGS",
      ],
    };
  },
  methods: {
    handleSubmit() {
      const action = "addClothing";
      clothingService[action](this.userId, this.clothing)
        .then(() => {
          this.updateInventoryView();
          alert("Clothing saved successfully");
        })
        .catch((error) => {
          console.error("Error saving clothing:", error);
          alert("Failed to save clothing");
        });
    },
  },
};
</script>
