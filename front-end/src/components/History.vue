<script>
import Nav from './Nav.vue'
export default {
    data() {
        return {
            history: []
        }
    },
    methods: {
        async getData() {
            const res = await fetch("http://localhost:8080/loans/history");
            const final = await res.json();
            this.history = final.map(m => m);
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
        <h3>Geschiedenis</h3>
        <div id="container-history">
            <table class="table table-striped">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Gebruikers</th>
                        <th>Item</th>
                        <th>Beschrijving</th>
                        <th>Begonnen datum</th>
                        <th>Eind datum</th>
                    </tr>
                </thead>
                <tbody>
                    <tr v-for="history in this.history" :key="history.id">
                        <td>{{ history.id }}</td>
                        <td>{{ history.userId }}</td>
                        <td>{{ history.itemId }}</td>
                        <td>{{ history.description }}</td>
                        <td>{{ history.startDate }}</td>
                        <td>{{ history.endDate }}</td>
                    </tr>
                </tbody>
            </table>
        </div>
    </main>
</template>

<style>
#container-history {
    width: 90%;
    margin: auto;
    margin-top: 2%;
}
</style>