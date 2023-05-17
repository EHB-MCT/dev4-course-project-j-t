

<script>
import 'bootstrap/dist/css/bootstrap.css'
export default {
  data() {
    return {
      email: "",
      password: "",
      errorMessage: null
    };
  },
  methods: {
    async login() {
      if (this.email && this.password) {
        const response = await fetch("http://localhost:8080/users/login", {
          method: "POST",
          headers: { "Content-Type": "application/json" },
          body: JSON.stringify({ email: this.email, password: this.password }),
        })
          .then((response) => response.text())
          .then((data) => {
            data = JSON.parse(data)
            if (data.status == 500) {
              this.errorMessage = data.message
            } else {
              sessionStorage.setItem("user", JSON.stringify(data))
              this.$router.push('/Item')
            }
          })
      } else {
        this.errorMessage = "Fill in all fields."
      }
    },
    async register() {
      this.$router.push('/Register')
    }
  },
};

</script>
  
<template>
  <div id="container">

    <form id="chatForm" @submit.prevent="login">
      <img src="../../public/images/logo-ehb-small.png" alt="" id="logo">
      <div class="form-group">
        <label for="email">E-mail</label>
        <input type="email" class="form-control" id="email" v-model="email">
      </div><br>
      <div class="form-group">
        <label for="password">Password</label>
        <input type="password" class="form-control" id="password" v-model="password">
      </div><br>
      <button type="submit" class="btn btn-primary" id="login">login</button>
      <button @click="register" type="submit" class="btn btn-primary" id="register">Register</button>
      <br><br>
      <div v-if="errorMessage" class="alert alert-danger">{{ this.errorMessage }}</div>
    </form> <br>

  </div>
</template>
  
<style>
#logo {
  width: 50%;
  margin-bottom: 50px;
}

#container {
  background-image: url("../../public/images/background-blue.min.svg");
  background-position: top;
  background-size: cover;
  width: 100%;
  height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
}

#chatForm {
  width: 40%;
  background-color: rgba(205, 205, 205, 0.5);
  padding: 80px 50px;
  color: black;
  font-size: 1.5rem;
  border-radius: 30px;
}

#login,
#register {
  width: 40%;
}

#login {
  margin-right: 20%;
}
</style>