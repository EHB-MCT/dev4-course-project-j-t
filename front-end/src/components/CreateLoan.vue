<script>
import Nav from "./Nav.vue"

export default {
    data() {
        return {
            users: [],
            items: [],
            selectedUser: null,
            selectedItem: null,
            startDate: null,
            endDate: null,
            description: null,
            errorMessage: null,
            classAlert: null,
            searchText: '',
        }
    },
    methods: {
        async getData() {
            const resUsers = await fetch("http://localhost:8080/users");
            const finalUsers = await resUsers.json();
            const resItems = await fetch("http://localhost:8080/items");
            const finalItems = await resItems.json();
            this.users = finalUsers.map(m => m);
            this.items = finalItems.map(m => m);

        },
        async createLoan() {
            if (this.selectedUser && this.selectedItem && this.startDate && this.endDate) {
                const loanData = {
                    user: this.selectedUser,
                    item: this.selectedItem.id,
                    description: this.description,
                    startDate: this.startDate,
                    endDate: this.endDate
                };
                const response = await fetch("http://localhost:8080/loans", {
                    method: "POST",
                    headers: { "Content-Type": "application/json" },
                    body: JSON.stringify(loanData),
                }).then((response) => response.text())
                    .then((data) => {
                        if (data.status == 500) {
                            this.errorMessage = data.message
                        } else {
                            this.classAlert = true
                            this.errorMessage = "De lening is succesvol aangemaakt"
                            setTimeout(() => {
                                this.errorMessage = null
                                this.selectedUser = null
                                this.selectedItem = null
                                this.startDate = null
                                this.endDate = null
                            }, 3500);
                        }
                    })
            } else {
                this.errorMessage = "Vul alle velden in!"
                setTimeout(() => {
                    this.errorMessage = null
                }, 3500);

            }
        },
        selectItem(item) {
            if (this.selectedItem === item) {
                this.selectedItem = null; // Deselect the item if it's already selected
            } else {
                this.selectedItem = item; // Select the clicked item
            }
        },

    },
    mounted() {
        this.getData();
    },
    computed: {
        filteredItems() {
            const searchQuery = this.searchText.trim().toLowerCase();
            return this.items.filter(item => item.name.toLowerCase().includes(searchQuery) && item.availability);
        },
    },
    components: {
        Nav
    },
}

</script>

<template>
    <header>
        <Nav />
    </header>
    <main>
        <div class="shade" v-if="errorMessage"></div>
        <div id="container-createLoan">
            <div id="err-message" v-if="errorMessage" :class="['alert', classAlert ? 'alert-success' : 'alert-danger']">{{
                this.errorMessage }}
            </div>
            <h1>Create new loan</h1><br>
            <form @submit.prevent="createLoan">
                <div class="form-group">
                    <label for="selectUser">Selecteer gebruiker:</label>
                    <div class="select-wrapper">
                        <select id="selectUser" class="form-control" v-model="selectedUser" data-live-search="true">
                            <option v-for="user in users" :key="user.id" :value="user.id">{{ user.firstName }} {{
                                user.lastName }}</option>
                        </select>
                    </div>
                </div><br>
                <div class="form-group">
                    <label for="item">Selecteer een item uit de lijst:</label>
                    <input type="hidden" id="item" class="form-control" v-model="selectedItem.id" v-if="selectedItem">
                    <!-- <input v-if="selectedItem">{{ selectedItem.name }}> -->
                    <input v-if="selectedItem" type="text" class="form-control" v-model="selectedItem.name">
                </div><br>
                <div class="form-group">
                    <label for="description">Beschrijving:</label>
                    <textarea id="description" class="form-control" v-model="description"></textarea>
                </div><br>
                <div class="form-group">
                    <label for="startDate">Begin datum:</label>
                    <input type="date" id="startDate" class="form-control" v-model="startDate">
                </div><br>
                <div class="form-group">
                    <label for="endDate">Einddatum:</label>
                    <input type="date" id="endDate" class="form-control" v-model="endDate">
                </div><br>
                <button type="submit" class="btn btn-primary">Nieuwe lening maken</button>
            </form><br>
            <hr>
            <h4>Selecteer een item uit de lijst</h4> <br>
            <div id="filter">
                <input class="form-control mb-4" id="search-input" type="text" v-model="searchText" @keyup="searchItems"
                    placeholder="Zoek op artikelnaam">
            </div>
            <div class="items">
                <div class="row" id="row" v-for="item in filteredItems" :key="item.id" @click="selectItem(item)"
                    :class="{ 'selected': item === selectedItem }">
                    <div class="col-md-4" v-if="item.img" id="img-item">
                        <img class="img-fluid" :src="item.img">
                    </div>
                    <div class="col-md-8" id="tekst-container">
                        <h5 class="card-title" id="card-title">{{ item.name }}</h5>
                        <p class="card-text" id="card-description">{{ item.description }}</p>
                        <p class="card-text" id="availability-text"
                            :class="{ 'available': item.availability, 'not-available': !item.availability }">
                            {{ item.availability ? 'Beschikbaar' : 'Niet beschikbaar' }}
                        </p>
                    </div>
                </div>
            </div>
        </div>
        <br>
    </main>
</template>

<style>
#container-createLoan {
    width: 90%;
    margin: auto;
    margin-top: 2%;
    background-color: rgb(242, 242, 242);
    padding: 50px;
    border-radius: 30px;
    font-size: 1.2em;

}

.selected {
    border: 5px solid green;
    overflow: hidden;
}

.items {
    display: flex;
    justify-content: space-between;
    align-items: center;
    flex-wrap: wrap;
    width: 100%;
    margin: auto;
}

#row {
    width: 100%;
    background-color: rgb(231, 229, 229);
    margin: 1%;
    padding: 0;
    position: relative;
    height: 280px;
}

#img-item {
    width: 30%;
    padding: 0;
    height: 280px;
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

#card-title {
    margin-bottom: 20px;
}

#availability-text {
    position: absolute;
    bottom: 10px;
}

#err-message {
    width: 50%;
    height: 300px;
    position: fixed;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
    padding: 20px;
    border-radius: 4px;
    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.15);
    animation: fade-in 0.3s ease-out;
    z-index: 55;
    display: flex;
    justify-content: center;
    align-items: center;
    flex-direction: column;
    font-size: 40px;
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

@media only screen and (min-width: 1000px) {
    #row {
        width: 45%;
    }
}
</style>