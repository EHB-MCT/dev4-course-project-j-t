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
            searchQuery: '',
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
                            this.errorMessage = "Item created successfully"
                            setTimeout(() => {
                                this.errorMessage = null
                                this.selectedUser = null
                                this.selectedItem = null
                                this.startDate = null
                                this.endDate = null
                            }, 5000);
                        }
                    })
            } else {
                this.errorMessage = "Fill in all fields."
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
            return this.items.filter(item => item.availability);
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
        <div class="container">
            <div id="err-message" v-if="errorMessage" :class="['alert', classAlert ? 'alert-success' : 'alert-danger']">{{
                this.errorMessage }}</div>
            <h1>Create new loan</h1>
            <form @submit.prevent="createLoan">
                <div class="form-group">
                    <label for="selectUser">Select user::</label>
                    <div class="select-wrapper">
                        <select id="selectUser" class="form-control" v-model="selectedUser" data-live-search="true">
                            <option v-for="user in users" :key="user.id" :value="user.id">{{ user.firstName }} {{
                                user.lastName }}</option>
                        </select>
                    </div>
                </div>
                <div class="form-group">
                    <label for="item">Item</label>
                    <input type="hidden" id="item" class="form-control" v-model="selectedItem.id" v-if="selectedItem">
                    <p v-if="selectedItem">{{ selectedItem.name }}</p>
                </div>
                <div class="form-group">
                    <label for="description">Beschrijving:</label>
                    <textarea id="description" class="form-control" v-model="description"></textarea>
                </div>
                <div class="form-group">
                    <label for="startDate">Startdatum:</label>
                    <input type="date" id="startDate" class="form-control" v-model="startDate">
                </div>
                <div class="form-group">
                    <label for="endDate">Einddatum:</label>
                    <input type="date" id="endDate" class="form-control" v-model="endDate">
                </div><br>
                <button type="submit" class="btn btn-primary">Verzenden</button>
            </form><br><br><br>

            <div class="form-group">
                <input type="text" class="form-control" v-model="searchQuery" @keyup="searchItems"
                    placeholder="Search items">
            </div><br><br>

            <div class="items">
                <div class="row" v-for="item in filteredItems" :key="item.id" @click="selectItem(item)"
                    :class="{ 'selected': item === selectedItem }">
                    <div class="col-md-4" v-if="item.img">
                        <img class="img-fluid" :src="item.img">
                    </div>
                    <div class="col-md-8">
                        <h5 class="card-title">{{ item.name }}</h5>
                        <p class="card-text" id="card-description">{{ item.description }}</p>
                        <p class="card-text" id="availability-text"
                            :class="{ 'available': item.availability, 'not-available': !item.availability }">
                            {{ item.availability ? 'Beschikbaar' : 'Niet beschikbaar' }}
                        </p>
                    </div>
                </div>
            </div>
        </div>
    </main>
</template>

<style>
.selected {
    border: 2px solid green;
}

.items {
    display: flex;
    flex-wrap: wrap;
    justify-content: space-between;
    width: 100%;
    gap: 10px;
}

.row {
    width: 50%;
    background-color: rgb(246, 246, 246);

}

.available {
    color: rgb(0, 194, 0);
    font-weight: bold;
}

.nav-link {
    font-weight: bold;
}

.not-available {
    color: red;
    font-weight: bold;

}
</style>