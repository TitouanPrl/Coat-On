<template>
  <div class="rec-clothing-container">
    <div class="clothing-finder">
      <InventoryDisplayer :clothingItems="clothingItems" />
    </div>
    <div class="clothing-displayer">
      <InventoryAdd @deleteClothing="fetchClothingItems" />
    </div>
  </div>
  <div v-if="showNotif" class="notification">
    {{ notification }}
  </div>
</template>

<script>
import InventoryDisplayer from "@/components/inventory/InventoryDisplayer";
import InventoryAdd from "@/components/inventory/InventoryAdd";
import clothingService from "../services/clothingService";
import ClothingItem from "@/components/clothing/ClothingItem.vue";
import jwt_decode from "jwt-decode";

export default {
  components: { InventoryAdd, InventoryDisplayer, ClothingItem },
  data() {
    return {
      userId: jwt_decode(localStorage.getItem("jwtToken")).userId,
      clothingItems: [],
      showNotif: false,
      notification: "",
    };
  },
  provide() {
    return {
      updateInventoryView: this.fetchClothingItems,
    };
  },
  mounted() {
    setTimeout(() => {
      this.showNotif = true;
      this.notification =
        "Today, the weather is sunny ! We recommand you to wear: t-shirt and short, and you could do: football"; //SHould fetch dynamically infos from the backend
      setTimeout(() => {
        this.showNotif = false;
      }, 10000);
    }, 10000);
    this.fetchClothingItems();
  },
  methods: {
    fetchClothingItems() {
      clothingService
        .getAllClothing(this.userId)
        .then((response) => {
          this.clothingItems = response.data;
          console.log("Got the clothes", response);
        })
        .catch((error) => {
          console.error("Failed to load clothing items:", error);
        });
    },
  },
};
</script>

<style>
.notification {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  background-color: #f8d7da;
  color: #721c24;
  padding: 10px;
  text-align: center;
  z-index: 9999;
}
</style>
