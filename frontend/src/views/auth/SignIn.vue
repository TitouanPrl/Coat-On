<template>
    <div class="signin-container">
      <h1>Welcome to Coat On</h1>
      <sign-in-form />
      <p class="tagline">No account yet? <router-link to="/signup">Sign up!</router-link></p>
    </div>
  </template>
  
  <script>
  import SignInForm from '../../components/auth/SignInForm.vue';
  
  export default {
    components: {
      SignInForm
    }
  };
  </script>
  
  <style scoped>
  @import "../../assets/css/style.css";
  </style>
  