<template>
  <form @submit.prevent="LogIn">
    <div>
      <label for="login">Login</label>
      <input id="login" type="text" v-model="username" required />
    </div>
    <div>
      <label for="password">Password</label>
      <input id="password" type="password" v-model="password" required />
    </div>
    <button type="submit">Sign in</button>
  </form>
</template>

<script>
import jwt_decode from "jwt-decode";
import auth from "../../auth";

export default {
  data() {
    return {
      username: "",
      password: "",
    };
  },
  methods: {
    async LogIn() {
      const data = {
        username: this.username,
        password: this.password,
      };

      try {
        const response = await fetch("http://localhost:8086/api/login", {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
          credentials: "include",
          body: JSON.stringify(data),
        });

        if (!response.ok) {
          throw new Error("Network response was not ok");
        }

        const token = await response.text();
        this.token = token;

        if (this.token.startsWith("ey")) {
          this.decodedToken = jwt_decode(this.token);
          this.roles = this.decodedToken.roles;
          localStorage.setItem("jwtToken", this.token);
          console.log(localStorage.getItem("jwtToken"));

          // Update authentication status
          await auth.authenticated();

          // Check if the user is authenticated
          if (auth.user.authenticated) {
            this.$router.push("/home");
          } else {
            throw new Error("Authentication failed");
          }
        } else {
          throw new Error("Invalid token received");
        }
      } catch (error) {
        console.error("Error during sign in:", error);
        alert("Sign in failed. Please check your login and password.");
      }
    },
  },
};
</script>
