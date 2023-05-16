
<script>
export default {
  data() {
    return {
      isAdmin: JSON.parse(sessionStorage.getItem("user"))
    }
  },
  name: "Nav"
};
</script>

<template>
  <nav class="navbar navbar-expand navbar-light bg-light" v-if="this.isAdmin.isAdmin">
    <div class="collapse navbar-collapse" id="navbarNav">
      <ul class="navbar-nav">
        <li class="nav-item">
          <a class="nav-link" href="http://localhost:5173/item">Items</a>
        </li>
        <li class="nav-item">
          <a class="nav-link" href="http://localhost:5173/loans">Leningen</a>
        </li>
        <li class="nav-item">
          <a class="nav-link" href="http://localhost:5173/createitem">Item maken</a>
        </li>
        <li class="nav-item">
          <a class="nav-link" href="http://localhost:5173/createLoan">Lening maken</a>
        </li>
        <li class="nav-item">
          <a class="nav-link" href="http://localhost:5173/GetAllUsers">Gebruikers</a>
        </li>
        <li class="nav-item">
          <a class="nav-link" href="http://localhost:5173/History">Geschiedenis</a>
        </li>
      </ul>

    </div>
    <ul class="navbar-nav ml-auto logout">
      <li class="nav-item">
        <a class="nav-link" href="http://localhost:5173/">Uitloggen</a>
      </li>
    </ul>
  </nav>
  <nav class="navbar navbar-expand navbar-light bg-light" v-else="this.isAdmin.isAdmin">
    <div class="collapse navbar-collapse" id="navbarNav">
      <ul class="navbar-nav">
        <li class="nav-item">
          <a class="nav-link" href="http://localhost:5173/item">Items</a>
        </li>
        <li class="nav-item">
          <a class="nav-link" href="http://localhost:5173/MyLoans">Mijn Leningen</a>
        </li>
      </ul>

    </div>
    <ul class="navbar-nav ml-auto logout">
      <li class="nav-item">
        <a class="nav-link" href="http://localhost:5173/">Uitloggen</a>
      </li>
    </ul>
  </nav>
</template>

<style>
.logout {
  font-weight: 300;
  text-decoration: underline;
}

.navbar-nav {
  margin-right: 5%;
  font-size: 1.1em;
}


.nav-link {
  font-weight: bold;
}

#navbarNav {
  margin-left: 5%;
}

h3 {
  margin-left: 5%;
  margin-top: 2%;
}

#navbarNav li {
  margin-right: 30px;
}
</style>
