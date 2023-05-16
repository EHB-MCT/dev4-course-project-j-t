<script>
import Nav from './Nav.vue'
export default {
    data() {
        return {
            users: [],
            sessionStorage: JSON.parse(sessionStorage.getItem("user")),
            searchText: '',
        }
    },
    methods: {
        async getData() {
            const res = await fetch("http://localhost:8080/users");
            const final = await res.json();
            this.users = final.map(m => m);
        },
        makeAdmin(user) {
            fetch(`http://localhost:8080/users/${user.id}/admin?isAdmin=${true}`, {
                method: 'PUT'
            })
                .then(response => {
                    if (response.ok) {
                        console.log('Make admin:', user.id);
                        this.getData();
                    } else {
                        console.error('Failed to make admin:', response.status);
                    }
                })
                .catch(error => {
                    console.error('Error making admin:', error);
                });
        },
        removeAdmin(user) {
            fetch(`http://localhost:8080/users/${user.id}/admin?isAdmin=${false}`, {
                method: 'PUT'
            })
                .then(response => {
                    if (response.ok) {
                        console.log('Make admin:', user.id);
                        this.getData();
                    } else {
                        console.error('Failed to make admin:', response.status);
                    }
                })
                .catch(error => {
                    console.error('Error making admin:', error);
                });
        },
    },
    mounted() {
        this.getData();
    },
    computed: {
        filteredUsers() {
            const searchQuery = this.searchText.trim().toLowerCase();
            if (searchQuery === '') {
                return this.users;
            } else {
                return this.users.filter(user => user.email.toLowerCase().includes(searchQuery));
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
        <h3>Alle gebruikers</h3>
        <div id="container-users">
            <div id="filter">
                <input class="form-control mb-4" id="search-input" type="text" v-model="searchText" @keyup="searchUser"
                    placeholder="Zoek op email">
            </div>
            <table class="table table-striped">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Voornaam</th>
                        <th>Achternaam</th>
                        <th>E-mail</th>
                        <th class="td-btn">Admin</th>
                    </tr>
                </thead>
                <tbody>
                    <tr v-for="user in this.filteredUsers" :key="user.id">
                        <td v-if="user.email !== this.sessionStorage.email">{{ user.id }}</td>
                        <td v-if="user.email !== this.sessionStorage.email">{{ user.firstName }}</td>
                        <td v-if="user.email !== this.sessionStorage.email">{{ user.lastName }}</td>
                        <td v-if="user.email !== this.sessionStorage.email">{{ user.email }}</td>
                        <td class="td-btn" v-if="user.email !== this.sessionStorage.email">
                            <button v-if="!user.isAdmin" @click="makeAdmin(user)" class="btn btn-primary">Maak
                                admin</button>
                            <button v-else @click="removeAdmin(user)" class="btn btn-secondary">Verwijder admin</button>
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>
    </main>
</template>

<style>
#container-users {
    width: 90%;
    margin: auto;
    margin-top: 2%;
}

.td-btn {
    text-align: right;
}
</style>