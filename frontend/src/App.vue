<template>
  <nav>
    <router-link v-if="!isAuthenticated" to="/signin">Sign In</router-link> |
    <router-link to="/home">Home</router-link> |
    <router-link to="/activity">Activity</router-link> |
    <router-link v-if="checkRoles('USER')" to="/clothing">Clothing</router-link>
    <span v-if="checkRoles('USER')"> | </span>
    <router-link v-if="checkRoles('USER')" to="/inventory"
      >Inventory</router-link
    >
    <span v-if="checkRoles('USER')"> | </span>
    <router-link v-if="checkRoles('ADMIN')" to="/user/all"
      >All User</router-link
    >
    <span v-if="checkRoles('ADMIN')"> |</span>
    <button v-if="isAuthenticated" @click="Logout" class="logout-button">
      Log out
    </button>
  </nav>
  <router-view />
</template>

<script>
import { watchEffect, ref, onMounted } from "vue";
import auth from "./auth";

export default {
  setup() {
    const isAuthenticated = ref(auth.user.authenticated);

    // Watch for changes in the reactive auth.user object
    watchEffect(() => {
      isAuthenticated.value = auth.user.authenticated;
    });

    onMounted(async () => {
      await auth.authenticated();
    });

    const Logout = () => {
      auth.logout();
      location.assign("/");
    };

    const checkRoles = (roles) => {
      return auth.hasAnyOf(roles);
    };

    return {
      isAuthenticated,
      Logout,
      checkRoles,
    };
  },
};
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}

nav {
  padding: 30px;
}

nav a {
  font-weight: bold;
  color: #2c3e50;
}

nav a.router-link-exact-active {
  color: #42b983;
}

.logout-button {
  background-color: transparent;
  font-weight: bold;
  color: #2c3e50;
  border: none;
  border-radius: 3px;
  cursor: pointer;
  max-width: 65px;
}

.logout-button:hover {
  background-color: darkgrey;
}
</style>
