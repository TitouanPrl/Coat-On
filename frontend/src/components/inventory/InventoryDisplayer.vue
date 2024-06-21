<template>
  <div>
    <h1>Your Wardrobe</h1>
    <div class="inventory-container">
      <inventory-item
        v-for="item in clothingItems"
        :key="item.id"
        :data="item"
        :userId="userId"
      />
    </div>
  </div>
</template>

<script>
import InventoryItem from "./InventoryItem.vue";
import jwt_decode from "jwt-decode";

export default {
  name: "InventoryDisplayer",
  components: {
    InventoryItem,
  },
  data() {
    return {
      userId: jwt_decode(localStorage.getItem("jwtToken")).userId,
    };
  },
  props: {
    clothingItems: Array,
  },
};
</script>
