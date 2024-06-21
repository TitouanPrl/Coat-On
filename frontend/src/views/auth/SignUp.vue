<template>
    <div class="user-container">
      <sign-up-form></sign-up-form>
    </div>
  </template>
  
  <script>
  import SignUpForm from '../../components/auth/SignUpForm.vue';
  
  export default {
    components: {
      SignUpForm
    }
  };
  </script>
  
  <style scoped>
  @import "../../assets/css/style.css";
  </style>
  