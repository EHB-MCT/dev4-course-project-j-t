<script>
import Nav from './Nav.vue'

export default {

  data() {
    return {
      items: [],
      isAdmin: JSON.parse(sessionStorage.getItem("user")),
      searchText: '',
      showAvailableItems: false,
      successMessage: null,
    }
  },
  methods: {
    async getData() {
      const res = await fetch("http://localhost:8080/items");
      const finalRes = await res.json();
      this.items = finalRes.map(m => m);
      console.log(this.isAdmin.isAdmin)
    },

    async deleteItem(itemId) {
      const confirmed = window.confirm('Weet u zeker dat u dit item wilt verwijderen?');
      if (confirmed) {
        await fetch(`http://localhost:8080/items/${itemId}`, {
          method: "DELETE"
        });
        this.items = this.items.filter(item => item.id !== itemId);
        this.showSuccessMessage('Item is succesvol verwijderd!');
      }

    }, navigateToDetail(itemId) {
      this.$router.push({ name: 'itemDetails', params: { id: itemId } });
    }, toggleAvailableItems() {
      this.showAvailableItems = !this.showAvailableItems;
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
    filteredItems() {
      if (this.searchText.trim() === '') {
        if (this.showAvailableItems) {
          return this.items.filter(item => item.availability);
        } else {
          return this.items;
        }
      } else {
        const searchQuery = this.searchText.trim().toLowerCase();
        if (this.showAvailableItems) {
          return this.items.filter(item => item.name.toLowerCase().includes(searchQuery) && item.availability);
        } else {
          return this.items.filter(item => item.name.toLowerCase().includes(searchQuery));
        }
      }
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
    <div id="filter">
      <input class="form-control mb-4" id="search-input" type="text" v-model="searchText" @keyup="searchItems"
        placeholder="Zoek op artikelnaam">
      <button class="btn btn-primary mb-4" @click="toggleAvailableItems">
        {{ showAvailableItems ? 'Alles tonen' : 'Alleen beschikbaar tonen' }}
      </button>

    </div>
    <div class="items" id="items">
      <div class="row" id="row" v-for="item in this.filteredItems" :key="item.id">
        <div class="col-md-4" v-if="item.img" id="img-item">
          <img class="img-fluid" :src="item.img">
        </div>
        <div class="col-md-8" id="tekst-container">
          <h5 class="card-title" id="card-title">{{ item.name }}</h5>
          <p class="card-text" id="card-description">{{ item.description }}</p>
          <p class="card-text" id="availability-text"
            :class="{ 'available': item.availability, 'not-available': !item.availability }">{{ item.availability ?
              'Beschikbaar' : 'Niet beschikbaar' }}</p>
          <div class="btn-group" id="btn">
            <button @click="navigateToDetail(item.id)" class="btn btn-info" id="btn-details">
              <i class="fa fa-info-circle"></i>
            </button>
            <button v-if="this.isAdmin.isAdmin" @click="deleteItem(item.id)" class="btn btn-danger" id="btn-delete">
              <i class="fa fa-trash"></i>
            </button>
          </div>
        </div>
      </div>
    </div>
  </main>
</template>

<style>
.items {
  display: flex;
  justify-content: center;
  align-items: center;
  flex-wrap: wrap;
  width: 100%;
  position: relative;
}

.row {
  width: 100%;
  background-color: rgb(242, 242, 242);
  margin: 1%;
  padding: 0;
  position: relative;
  height: 275px;
}

#img-item {
  width: 30%;
  padding: 0;
  height: 275px;
}

#img-item img {
  object-fit: cover;
  object-position: center;
  width: 100%;
  height: 100%;
}

#tekst-container {
  width: 70%;
  padding: 40px;
}

.available {
  color: rgb(0, 194, 0);
  font-weight: bold;
}


.not-available {
  color: red;
  font-weight: bold;
}

#card-description {
  overflow: hidden;
  min-height: 20px;
  max-height: 47px;
}

#card-title {
  margin-bottom: 30px;
}

#btn {
  position: absolute;
  right: 30px;
  bottom: 30px;
}

#btn-delete {
  margin-left: 10px;
}

#availability-text {
  position: absolute;
  bottom: 30px;
}

#filter {
  width: 92%;
  margin: auto;
  margin-top: 2%;
  display: flex;
  justify-content: space-between;
}

#search-input {
  width: 85%;
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

@media only screen and (min-width: 1000px) {
  .row {
    width: 45%;
  }
}
</style>