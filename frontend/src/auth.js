import jwt_decode from "jwt-decode";
import { reactive } from "vue";

const user = reactive({
  authenticated: false,
  roles: "",
  username: "",
});

export default {
  user,
  authenticated: async function () {
    const token = localStorage.getItem("jwtToken");
    console.log("Authenticated? token ", token);
    // if there is a token, get the user roles, and try to authenticate
    if (token) {
      this.user.roles = jwt_decode(token).roles;
      console.log("roles " + this.user.roles);
      // Set the Authorization header with the token value
      const headers = {
        "Content-Type": "application/json",
        Authorization: `Bearer ${token}`,
      };
      try {
        const response = await fetch("http://localhost:8086/api/authenticate", {
          headers,
        });
        const data = await response.json();
        this.user.authenticated = data;
        console.log(data);
        return this.user.authenticated; // returns a boolean value
      } catch (err) {
        console.log(err.message);
        this.user.authenticated = false;
        return false;
      }
    } else {
      // there is no token, we cannot authenticate
      this.user.authenticated = false;
      return false;
    }
  },

  hasAnyOf: function (roles) {
    if (this.user.roles) return this.user.roles === roles;
  },

  hasIdOf: function (id) {
    if (this.user.id) return this.user.id === id;
  },

  logout: function () {
    localStorage.removeItem("jwtToken");
    this.user.roles = "";
    this.user.username = "";
    this.user.authenticated = false;
  },
};
