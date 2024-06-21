<template>
  <div class="inventory-item">
    <h3>{{ data.name }}</h3>
    <p>Type: {{ data.type }}</p>
    <!-- Display other clothing properties if needed -->
    <button @click="deleteItem">Delete</button>
  </div>
</template>

<script>
import clothingService from "../../services/clothingService";

export default {
  props: {
    data: {
      type: Object,
      required: true,
    },
    userId: {
      type: String,
      required: true,
    },
  },
  inject: ["updateInventoryView"],
  methods: {
    deleteItem() {
      if (confirm(`Are you sure you want to delete "${this.data.name}"?`)) {
        clothingService
          .deleteClothing(this.userId, this.data.id)
          .then(() => {
            alert("Item deleted successfully.");
            this.$emit("deleteClothing"); // Notify the parent to refresh the list
            this.updateInventoryView();
          })
          .catch((error) => {
            console.error("Error deleting clothing item:", error);
            alert("Failed to delete the item.");
          });
      }
    },
  },
};
</script>
