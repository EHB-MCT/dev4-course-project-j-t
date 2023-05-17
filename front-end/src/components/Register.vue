<script>
export default {
  data() {
    return {
      firstName: '',
      lastName: '',
      email: '',
      password: '',
      isAdmin: false,
      errorMessage: null,
      successMessage: null,
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
              this.showSuccessMessage('Registratie succesvol!');
              setTimeout(() => {
                sessionStorage.setItem("user", JSON.stringify(data))
                this.$router.push('/')
              }, 5000);

            }
          })
      } else {
        this.errorMessage = "Vul alle velden in."
      }

    },
    login() {
      this.$router.push('/')
    },
    showSuccessMessage(message) {
      this.successMessage = message;
      setTimeout(() => {
        this.successMessage = null;
      }, 4000);
    },
  }
}
</script>
<template>
  <div class="shade" v-if="successMessage"> </div>
  <div v-if="successMessage" class="success-message">
    {{ successMessage }}
    <i class="fas fa-check-circle success-icon"></i>

  </div>
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

<style>
.success-message {
  width: 50%;
  height: 300px;
  position: fixed;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  padding: 20px;
  background-color: lightgreen;
  border-radius: 4px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.15);
  animation: fade-in 0.3s ease-out;
  z-index: 55;
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
  font-size: 40px;
  color: green;
}

.success-message i {
  margin-top: 50px;
  font-size: 50px;
}

.shade {
  width: 100%;
  height: 100vh;
  position: fixed;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  padding: 20px;
  background-color: rgba(0, 0, 0, 0.7);
  animation: fade-in 0.3s ease-out;
  z-index: 50;
}

@keyframes fade-in {
  0% {
    opacity: 0;
  }

  100% {
    opacity: 1;
  }
}
</style>