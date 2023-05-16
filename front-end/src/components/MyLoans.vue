<script>
import Nav from './Nav.vue'
export default {
    data() {
        return {
            loans: [],
            isAdmin: JSON.parse(sessionStorage.getItem("user")),
            searchText: '',
        }
    },
    methods: {
        async getData() {
            const resLoans = await fetch("http://localhost:8080/loans/user/1");
            const finalLoans = await resLoans.json();
            this.loans = finalLoans.map(m => m);
        },
        sortLoans(column) {
            if (column === 'endDate') {
                if (this.sortColumn === column) {
                    this.sortDirection = this.sortDirection === 'asc' ? 'desc' : 'asc';
                } else {
                    this.sortColumn = column;
                    this.sortDirection = 'asc';
                }
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
            return diffDays <= 1;
        },
        isEndDateOverdue(endDate) {
            const today = new Date();
            const loanEndDate = new Date(endDate);
            return loanEndDate < today;
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
        <h3>Welkom {{ this.isAdmin.firstName }}</h3>
        <div id="container-loans">
            <div id="filter">
                <input class="form-control mb-4" id="search-input" type="text" v-model="searchText" @keyup="searchItems"
                    placeholder="Zoek op artikelnaam">

                <div id="sort-options">
                    <label for="sort-end-date">Sorteer op einddatum:</label>
                    <select id="sort-end-date" v-model="sortColumn" @change="sortLoans(sortColumn)">
                        <option value="">Geen</option>
                        <option value="endDate">Oplopend</option>
                        <option value="endDate">Aflopend</option>
                    </select>
                </div>

            </div>

            <table class="table">
                <thead>
                    <tr>
                        <th>ID</th>
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
                        <td>{{ loan.item.name }}</td>
                        <td>{{ loan.description }}</td>
                        <td>{{ loan.startDate }}</td>
                        <td>{{ loan.endDate }}</td>
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