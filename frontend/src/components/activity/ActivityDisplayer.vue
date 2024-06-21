<template>
  <div>
    <template v-if="fetched && activities.length">
      We recommend you this activity:
      <div class="activity-container">
        <activity-item
          v-for="activity in activities"
          :key="activity.id"
          :activity="activity"
        />
      </div>
    </template>
    <p v-else-if="fetched && !activities.length">
      Sorry, no activities available
    </p>
  </div>
</template>

<script>
import ActivityItem from "@/components/activity/ActivityItem";

export default {
  name: "ActivityDisplayer",
  components: { ActivityItem },
  props: {
    activities: Array,
    fetched: Boolean
  },
};
</script>
