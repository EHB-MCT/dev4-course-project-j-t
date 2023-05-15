
<script>
export default {
  name: "Nav"
};
</script>

<template>
  <nav class="navbar navbar-expand navbar-light bg-light">
    <div class="collapse navbar-collapse" id="navbarNav">
      <ul class="navbar-nav">
        <li class="nav-item">
          <a class="nav-link" href="http://localhost:5173/item">Items</a>
        </li>
        <li class="nav-item">
          <a class="nav-link" href="http://localhost:5173/loans">Loans</a>
        </li>
        <li class="nav-item">
          <a class="nav-link" href="http://localhost:5173/createitem">Create item</a>
        </li>
        <li class="nav-item">
          <a class="nav-link" href="http://localhost:5173/createLoan">Create Loan</a>
        </li>
        <li class="nav-item">
          <a class="nav-link" href="http://localhost:5173/GetAllUsers">Users</a>
        </li>
      </ul>
      <ul class="navbar-nav ml-auto logout">
        <li class="nav-item">
          <a class="nav-link" href="http://localhost:5173/">Log out</a>
        </li>
      </ul>
    </div>
  </nav>
</template>

<style>
.logout {
  font-weight: 300;
  text-decoration: underline;
}

.navbar-nav {
  margin-left: 3%;
}


.nav-link {
  font-weight: bold;
}
</style>
