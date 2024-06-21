<template>
  <div class="rec-activity-container">
    <div class="activity-finder">
      <ActivityFinder @recommendActivity="recommendActivity" />
    </div>
    <div class="activity-displayer">
      <ActivityDisplayer :activities="activities" :fetched="fetched" />
    </div>
  </div>
</template>

<script>
import ActivityFinder from "@/components/activity/ActivityFinder";
import ActivityDisplayer from "@/components/activity/ActivityDisplayer";
export default {
  name: "ActivityView",
  components: { ActivityDisplayer, ActivityFinder },
  data() {
    return {
      activities: [],
      fetched: false
    };
  },
  methods: {
    recommendActivity(activities) {
      console.log("Am i called", activities);
      this.activities = activities;
      this.fetched = true;
    },
  },
};
</script>
