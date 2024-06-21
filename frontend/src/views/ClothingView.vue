<template>
  <div class="rec-clothing-container">
    <div class="clothing-finder">
      <ClothingFinder @recommendClothing="recommendClothing" />
    </div>
    <div class="clothing-displayer">
      <ClothingDisplayer :clothingItems="clothingItems" :fetched="fetched"/>
    </div>
  </div>
</template>

<script>
import ClothingFinder from "@/components/clothing/ClothingFinder";
import ClothingDisplayer from "@/components/clothing/ClothingDisplayer";
export default {
  components: {ClothingDisplayer, ClothingFinder},
  data() {
    return {
      clothingItems: [],
      fetched: false
    };
  },
  methods: {
    recommendClothing(clothingItems) {
      console.log("Am i called", clothingItems);
      this.clothingItems = clothingItems;
      this.fetched = true;
    },
  },
}
</script>