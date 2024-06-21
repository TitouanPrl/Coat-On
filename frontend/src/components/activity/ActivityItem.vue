<template>
  <div class="activity-item">
    <h3>{{ activity.name }}</h3>
    <p>
      {{ activity.description }}
    </p>
  </div>
</template>

<script>
export default {
  name: "ActivityItem",
  props: {
    activity: {
      type: Object,
    },
  },
  data() {
    return {
      activity: this.activity || {
        name: "",
        description: "",
      },
    };
  },
};
</script>
