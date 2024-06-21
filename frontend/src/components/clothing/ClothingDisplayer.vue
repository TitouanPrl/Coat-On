<template>
  <div>
    <template v-if="fetched && clothingItems.length">
      We recommend to you:
      <div class="clothing-container">
        <clothing-item
          v-for="item in clothingItems"
          :key="item.id"
          :item="item"
        />
      </div>
    </template>
    <p v-else-if="fetched && !clothingItems.length">
      Sorry, no clothes available
    </p>
  </div>
</template>

<script>
import ClothingItem from "./ClothingItem.vue";

export default {
  name: "ClothingDisplayer",
  components: { ClothingItem },
  props: {
    clothingItems: Array,
    fetched: Boolean
  },
};
</script>
