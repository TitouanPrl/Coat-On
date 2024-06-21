<template>
  <form @submit.prevent="signUp">
    <div>
      <label for="username">Username</label>
      <input id="username" type="text" v-model="user.username" required />
    </div>
    <div>
      <label for="email">Email</label>
      <input id="email" type="email" v-model="user.email" required />
    </div>
    <div>
      <label for="name">Name</label>
      <input id="name" v-model="user.name" required />
    </div>
    <div>
      <label for="password">Password</label>
      <input id="password" type="password" v-model="user.password" required />
    </div>
    <div>
      <label for="confirmPassword">Confirm Password</label>
      <input
        id="confirmPassword"
        type="password"
        v-model="confirmPassword"
        required
      />
    </div>
    <button @click="signUp" type="submit">Sign up</button>
  </form>
</template>

<script>
import jwt_decode from "jwt-decode";

export default {
  name: "signup",
  data() {
    return {
      user: {
        username: "",
        email: "",
        name: "",
        password: "",
      },
      confirmPassword: "",
    };
  },
  methods: {
    async signUp() {
      var data = {
        username: this.user.username,
        password: this.user.password,
        name: this.user.name,
        email: this.user.email,
      };

      if (this.user.password !== this.confirmPassword) {
        alert('Passwords do not match.');
        return;
      }
      // using Fetch - post method - send an HTTP post request to the specified URI with the defined body
      await fetch("http://localhost:8086/api/signup", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        credentials: "include",
        body: JSON.stringify(data),
      })
        .then((response) => response.text())
        .then((response) => {
          //saving the jwt in the token variable
          this.token = response;
          if (this.token.startsWith("ey")) {
            //decoding the jwt and save it in the decodedToken variable
            this.decodedToken = jwt_decode(this.token);
            // saving the returned user role into the roles variable
            this.roles = this.decodedToken.roles;
            console.log(this.decodedToken.roles);
            // saving the token into the windows local storage
            localStorage.setItem("jwtToken", this.token);
            console.log(localStorage.getItem("jwtToken"));
            this.$router.push("/");
          }
        })
        .catch((e) => {
          console.log(e);
          console.log("error");
        });
    },
  },
};
</script>
