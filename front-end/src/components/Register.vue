<script>
export default {
  data() {
    return {
      firstName: '',
      lastName: '',
      email: '',
      password: '',
      isAdmin: false,
      errorMessage: null
    }
  },
  methods: {
    async register() {
      if (this.firstName && this.lastName && this.email && this.password) {
        const response = await fetch("http://localhost:8080/users", {
          method: "POST",
          headers: { "Content-Type": "application/json" },
          body: JSON.stringify({
            firstName: this.firstName, lastName: this.lastName,
            email: this.email, password: this.password, isAdmin: this.isAdmin
          }),
        })
          .then((response) => response.text())
          .then((data) => {
            data = JSON.parse(data)
            if (data.status == 500) {
              this.errorMessage = data.message
            } else {
              sessionStorage.setItem("user", JSON.stringify(data))
              this.$router.push('/')
            }
          })
      } else {
        this.errorMessage = "Fill in all fields."
      }

    },
    login() {
      this.$router.push('/')
    }
  }
}
</script>
<template>
  <div id="container">
    <form id="chatForm" @submit.prevent="register">
      <img src="../../public/images/logo-ehb-small.png" alt="" id="logo">
      <div class="form-group">
        <label for="firstName">First Name</label>
        <input type="text" class="form-control" id="firstName" v-model="firstName">
      </div><br>
      <div class="form-group">
        <label for="lastName">Last Name</label>
        <input type="text" class="form-control" id="lastName" v-model="lastName">
      </div><br>
      <div class="form-group">
        <label for="email">E-mail</label>
        <input type="email" class="form-control" id="email" v-model="email">
      </div><br>
      <div class="form-group">
        <label for="password">Password</label>
        <input type="password" class="form-control" id="password" v-model="password">
      </div><br>
      <button type="submit" class="btn btn-primary" id="login">Register</button>
      <button @click="login" class="btn btn-primary" id="register">Login</button>
      <br><br>
      <div v-if="errorMessage" class="alert alert-danger">{{ this.errorMessage }}</div>
    </form> <br>
  </div>
</template>