<script>
import Nav from './Nav.vue'
export default {
  data() {
    return {
      loans: [],
      users: [],
      items: [],
      isAdmin: JSON.parse(sessionStorage.getItem("user"))
    }
  },
  methods: {
    async getData() {
      const resLoans = await fetch("http://localhost:8080/loans");
      const finalLoans = await resLoans.json();
      this.loans = finalLoans.map(m => m);
    },
    async deleteLoan(loanId) {
      const confirmed = window.confirm('Are you sure you want to delete this loan?');
      if (confirmed) {
        await fetch(`http://localhost:8080/loans/${loanId}`, {
          method: "DELETE"
        });
        this.loans = this.loans.filter(loan => loan.id !== loanId);
      }

    }
  },
  mounted() {
    this.getData();
  },
  components: {
    Nav
  }

}
</script>
<template>
  <header>
    <Nav />
  </header>
  <main>
    <table class="table">
      <thead>
        <tr>
          <th>ID</th>
          <th>User</th>
          <th>Item</th>
          <th>Description</th>
          <th>Start datum</th>
          <th>Eind datum</th>
          <th></th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="loan in loans" :key="loan.id">
          <td>{{ loan.id }}</td>
          <td>{{ loan.user.email }}</td>
          <td>{{ loan.item.name }}</td>
          <td>{{ loan.description }}</td>
          <td>{{ loan.startDate }}</td>
          <td>{{ loan.endDate }}</td>
          <td>
            <button v-if="isAdmin.isAdmin" @click="deleteLoan(loan.id)" class="btn btn-danger">Verwijder</button>
          </td>
        </tr>
      </tbody>
    </table>
  </main>
</template>
<style>
.table {
  width: 93%;
  margin: auto;
  margin-top: 3%;
}
</style>