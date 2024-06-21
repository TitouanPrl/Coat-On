<template>
  <div class="user-container">
    <div class="A User">
      <div id="form">
        <h3>User</h3>

        <label for="username">Username: </label>
        <input
          name="username"
          type="text"
          id="username"
          required
          v-model="user.username"
        />

        <label for="email">Email: </label>
        <input
          name="email"
          type="text"
          id="name"
          required
          v-model="user.email"
        />

        <label for="name">Name: </label>
        <input name="name" type="text" id="name" required v-model="user.name" />

        <label for="password">Password: </label>
        <input
          name="password"
          type="text"
          id="password"
          required
          v-model="user.password"
        />
      </div>
      <div>
        <button @click="updateUser" class="updateUser">Update User</button>
        <button @click="deleteUser" class="deleteUser">Delete User</button>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "AProduct",
  data() {
    return {
      user: {
        username: "",
        email: "",
        name: "",
        password: "",
      },
    };
  },
  methods: {
    fetchUser(id) {
      // fetch one product with the specified  id (id)
      fetch(`http://localhost:8086/api/user/${id}`)
        .then((response) => {
          if (!response.ok) {
            alert("No user found");
            this.$router.push(`/user/all`);
          }
          return response.json();
        })
        .then((data) => (this.user = data))
        .catch((err) => {
          this.notFound = true;
          console.log(err.message);
        });
    },
    updateUser() {
      fetch(`http://localhost:8086/api/user/${this.user.id}`, {
        method: "PUT",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(this.user),
      })
        .then((response) => {
          console.log(response.data);
          this.$router.push("/user/all");
        })
        .catch((e) => {
          console.log(e);
        });
    },
    deleteUser() {
      fetch(`http://localhost:8086/api/user/${this.user.id}`, {
        method: "DELETE",
      })
        .then((response) => {
          console.log(response.data);
          this.$router.push(`/user/all`);
        })
        .catch((e) => {
          console.log(e);
        });
    },
  },
  mounted() {
    this.fetchUser(this.$route.params.id);
  },
};
</script>
