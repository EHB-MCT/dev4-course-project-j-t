<script>
import Nav from './Nav.vue'
export default {
  data() {
    return {
      loans: [],
      users: [],
      items: [],
      isAdmin: JSON.parse(sessionStorage.getItem("user")),
      searchText: '',
      successMessage: null,
    }
  },
  methods: {
    async getData() {
      const resLoans = await fetch("http://localhost:8080/loans");
      const finalLoans = await resLoans.json();
      this.loans = finalLoans.map(m => m);
    },
    async deleteLoan(loanId) {
      const confirmed = window.confirm('Weet u zeker dat u deze lening wilt verwijderen?');
      if (confirmed) {
        await fetch(`http://localhost:8080/loans/${loanId}`, {
          method: "DELETE"
        });
        this.loans = this.loans.filter(loan => loan.id !== loanId);
        this.showSuccessMessage('De lening is succesvol verwijderd!');
      }

    },
    sortLoans(column) {
      if (column === 'valid') {
        if (this.sortColumn === column) {
          // Reverse the sort direction if the same column header is clicked
          this.sortDirection = this.sortDirection === 'asc' ? 'desc' : 'asc';
        } else {
          // Set the sort column and default sort direction for a new column header
          this.sortColumn = column;
          this.sortDirection = 'asc';
        }

        // Sort the loans array based on the selected column and direction
        this.loans.sort((a, b) => {
          if (this.sortDirection === 'asc') {
            return a.endDate.localeCompare(b.endDate);
          } else {
            return b.endDate.localeCompare(a.endDate);
          }
        });
      }else if (column == "expired"){
        if (this.sortColumn === column) {
          // Reverse the sort direction if the same column header is clicked
          this.sortDirection = this.sortDirection === 'asc' ? 'desc' : 'asc';
        } else {
          // Set the sort column and default sort direction for a new column header
          this.sortColumn = column;
          this.sortDirection = 'asc';
        }

        // Sort the loans array based on the selected column and direction
        this.loans.sort((a, b) => {
          if (this.sortDirection === 'asc') {
            return a.endDate.localeCompare(b.endDate);
          } else {
            return b.endDate.localeCompare(a.endDate);
          }
        });
      }
    }, isEndDateWithinOneDay(endDate) {
      const oneDay = 24 * 60 * 60 * 1000; // One day in milliseconds
      const today = new Date();
      const loanEndDate = new Date(endDate);

      // Calculate the difference in days
      const diffDays = Math.round(Math.abs((today - loanEndDate) / oneDay));

      // Return true if the difference is less than or equal to 1 day
      return diffDays <= 1;
    },
    isEndDateOverdue(endDate) {
      const today = new Date();
      const loanEndDate = new Date(endDate);

      // Compare the end date with today's date
      return loanEndDate < today;
    },
    showSuccessMessage(message) {
      this.successMessage = message;
      setTimeout(() => {
        this.successMessage = null;
      }, 5000); // Verwijder het bericht na 3 seconden (3000 milliseconden)
    },
  },
  mounted() {
    this.getData();
  },
  computed: {
    filteredLoan() {
      const searchQuery = this.searchText.trim().toLowerCase();
      return this.loans.filter(loan => loan.item.name.toLowerCase().includes(searchQuery));
    }
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
    <div class="shade" v-if="successMessage"> </div>
    <h3>Welkom {{ this.isAdmin.firstName }}</h3>
    <div v-if="successMessage" class="success-message">
      {{ successMessage }}
      <i class="fas fa-check-circle success-icon"></i>

    </div>

    <div id="container-loans">
      <div id="filter">
        <input class="form-control mb-4" id="search-input" type="text" v-model="searchText" @keyup="searchItems"
          placeholder="Zoek op artikelnaam">

        <div id="sort-options">
          <label for="sort-end-date">Sorteer op:</label>
          <select id="sort-end-date" v-model="sortColumn" @change="sortLoans(sortColumn)" >
  
            <option value="expired">Verlopen eerst</option>
            <option value="valid">Nog geldig eerst</option>
          </select>
        </div>

      </div>

      <table class="table">
        <thead>
          <tr>
            <th>ID</th>
            <th>Gebruikers</th>
            <th>Items</th>
            <th>Beschrijving</th>
            <th>Begin datum</th>
            <th>Eind datum</th>
            <th></th>
          </tr>
        </thead>
        <tbody>
          <tr id="green" v-for="loan in this.filteredLoan" :key="loan.id" :class="{
            'red-background': isEndDateOverdue(loan.endDate),
            'orange-background': isEndDateWithinOneDay(loan.endDate)
          }">
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
    </div>
  </main>
</template>
<style>
#container-loans {
  width: 90%;
  margin: auto;
  margin-top: 2%;
}

.table {
  width: 100%;
}

#filter {
  display: flex;
  justify-content: space-between;
}

#search-input {
  width: 75%;
}

.orange-background {
  animation: colorFade 2s infinite alternate;
}

.red-background {
  background-color: rgba(255, 0, 0, 0.381) !important
}

#green {
  background-color: lightgreen;
}

#sort-end-date{
  margin-left: 15px;
  height: 40px;
}


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

@keyframes colorFade {
  0% {
    background-color: rgba(255, 166, 0, 0.5);
  }

  50% {
    background-color: white;
  }

  100% {
    background-color: rgba(255, 166, 0, 0.5);
  }
}
</style>