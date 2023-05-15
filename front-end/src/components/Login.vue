

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
      if(this.email && this.password){
        const response = await fetch("http://localhost:8080/users/login", {
          method: "POST",
          headers: { "Content-Type": "application/json" },
          body: JSON.stringify({ email: this.email, password: this.password }),
        })
        .then((response) => response.text())
          .then((data) => {
            data = JSON.parse(data)
          if(data.status == 500){
            this.errorMessage = data.message
          }else{
              sessionStorage.setItem("user",JSON.stringify(data))
              this.$router.push('/Item')
          }
          })   
      }else{
        this.errorMessage ="Fill in all fields."
      }
    },
    async register(){
      this.$router.push('/Register')
    }
  },
};

</script>
  
<template>
    <div class="container">
      <div v-if="errorMessage" class="alert alert-danger">{{ this.errorMessage }}</div>
      <form id="chatForm" @submit.prevent="login">
        <div class="form-group">
          <label for="email">E-mail</label>
          <input type="email" class="form-control" id="email" v-model="email">
        </div>
        <div class="form-group">
          <label for="password">Password</label>
          <input type="password" class="form-control" id="password" v-model="password">
        </div><br>
        <button type="submit" class="btn btn-primary" >login</button>
      </form>  <br>
        <button @click="register" type="submit" class="btn btn-primary" >Register</button>
    </div>
  </template>
  
  <style>
 
  </style>
  