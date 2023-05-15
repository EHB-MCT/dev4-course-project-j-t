<script>
import Nav from './Nav.vue'
export default {
    data() {
        return {
            users: [],
            sessionStorage: JSON.parse(sessionStorage.getItem("user"))
        }
    },
    methods: {
        async getData() {
            const res = await fetch("http://localhost:8080/users");
            const final = await res.json();
            this.users = final.map(m => m);
        },
        makeAdmin(user) {
            // Implementeer de logica om een gebruiker tot admin te maken
            console.log('Make admin:', user);
        },
        removeAdmin(user) {
            // Implementeer de logica om de admin-status van een gebruiker te verwijderen
            console.log('Remove admin:', user);
        },
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
        <div class="container">
            <table class="table table-striped">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Voornaam</th>
                        <th>Achternaam</th>
                        <th>E-mail</th>
                        <th>Admin</th>
                    </tr>
                </thead>
                <tbody>
                    <tr v-for="user in users" :key="user.id">
                        <td v-if="user.email !== this.sessionStorage.email">{{ user.id }}</td>
                        <td v-if="user.email !== this.sessionStorage.email">{{ user.firstName }}</td>
                        <td v-if="user.email !== this.sessionStorage.email">{{ user.lastName }}</td>
                        <td v-if="user.email !== this.sessionStorage.email">{{ user.email }}</td>
                        <td v-if="user.email !== this.sessionStorage.email">
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