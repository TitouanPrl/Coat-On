<template>
  <div class="clothing-item">
    <h3>{{ item.name }}</h3>
  </div>
</template>

<script>

export default {
  name: "ClothingItem",
  props: {
    item: {
      type: Object,
      required: true,
    },
    userId: {
      type: String,
      required: true,
    },
  },
};
</script>
