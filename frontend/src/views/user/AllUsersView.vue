<template>
  <div class="AllUsers">
    <div id="users-list">
    <h1>All Users</h1>
      <ul>
        <div class="user" v-for="user in users" :key="user.id">
          <div class="singleuser">
            <table class="center-table">
              <tr>
                <th class="label">id</th>
                <th class="value">{{ user.id }}</th>
              </tr>
              <tr>
                <th class="label">Username</th>
                <th class="value">{{ user.username }}</th>
              </tr>
              <tr>
                <th class="label">Email</th>
                <th class="value">{{ user.email }}</th>
              </tr>
              <tr >
                <th class="label">Name</th>
                <th class="value">{{ user.name }}</th>
              </tr>
            </table>
          </div>
            <button @click="editUser(user.id)" class="editUser" >Edit</button>
            <button @click="deleteUser(user.id)" class="deleteUser">Delete</button>
        </div>
      </ul>
    </div>
  </div>
</template>


<script>
export default {
  name: "AllUsersView",
  data() {
    return {
      users: [],
    };
  },
  methods: {
    fetchUsers() {
      fetch(`http://localhost:8086/api/user`)
        .then((response) => response.json())
        .then((data) => (this.users = data))
        .catch((err) => console.log(err.message));
    },
    editUser(userId){
      this.$router.push(`/user/${userId}`);
    },
    deleteUser(userId){
      fetch(`http://localhost:8086/api/user/${userId}`,
      {
        method: "DELETE"
      }
      )
      .then((response) => {
        console.log(response.data);
        this.$router.push("/user/all");
      })
      .catch((e) => {
        console.log(e);
        }
      )
    },
  },
  mounted() {
    this.fetchUsers();
    console.log("mounted");
  },
};
</script>

<style>
.label {
  text-align: right; 
}
.value {
  text-align: left;
  padding-left: 10%;
  width: max-content; 
}
.center-table {
  margin: 0 auto;
}
th .label {
  width: 20%;
}
th .value {
  width: 80%;
}
.AllUsers {
  display: flex;
  justify-content: center; /* horizontal centering */
  align-items: center; /* vertical centering */
}
.user {
  background: rgb(189, 212, 199);
  margin-bottom: 5px;
  padding: 3px 5px;
  border-radius: 10px;
  width: 500px;
}
#post-list {
  background: #6e8b97;
  box-shadow: 1px 2px 3px rgba(0, 0, 0, 0.2);
  margin-bottom: 30px;
  padding: 10px 20px;
  margin: auto;
  width: 50%;
  border-radius: 20px;
}
#post-list ul {
  padding: 0;
  align-items: center;
}
#post-list li {
  display: inline-block;
  margin-right: 10px;
  margin-top: 10px;
  padding: 20px;
  background: rgba(255, 255, 255, 0.7);
}
</style>