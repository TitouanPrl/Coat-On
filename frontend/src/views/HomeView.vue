<template>
  <div class="home-container">
    <h1>Welcome to CoatOn!</h1>
    <h2>What would you like to do today?</h2>

    <div class="button-container">
      <router-link to="/clothing" class="btn"
        >Find a suitable outfit for the weather</router-link
      >
      <router-link to="/activity" class="btn">Find an activity</router-link>
    </div>
  </div>
</template>

<script>
export default {
  name: "HomePage",
};
</script>

<style scoped>
.home-container {
  text-align: center;
  padding: 50px;
}

.button-container {
  display: flex;
  justify-content: center;
  gap: 20px; 
}

.btn {
  background-color: #007bff;
  color: white;
  border: none;
  cursor: pointer;
  padding: 15px 30px;
  text-decoration: none; 
  font-size: 16px;
  border-radius: 5px; 
  transition: background-color 0.3s; 
  font-weight: bold;
}

.btn:hover {
  background-color: #0056b3;
}

h1,
h2 {
  margin-bottom: 20px;
}
</style>
