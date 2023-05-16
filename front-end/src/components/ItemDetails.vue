<script>
import Nav from './Nav.vue'

export default {

    data() {
        return {
            item: "",
            itemId: this.$route.params.id,
            isAdmin: JSON.parse(sessionStorage.getItem("user"))
        }
    },
    methods: {
        async getData() {
            const res = await fetch(`http://localhost:8080/items/${this.itemId}`);
            const finalRes = await res.json();
            this.item = finalRes;
        },
        async deleteItem(itemId) {
            const confirmed = window.confirm('Weet u zeker dat u dit item wilt verwijderen?');
            if (confirmed) {
                await fetch(`http://localhost:8080/items/${itemId}`, {
                    method: "DELETE"
                });
                this.items = this.items.filter(item => item.id !== itemId);
            }

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
        <h3>Welkom {{ this.isAdmin.firstName }}</h3>

        <div class="items">
            <div class="row" id="row" :key="item.id">
                <div class="col-md-4" v-if="item.img" id="img-item">
                    <img class="img-fluid" :src="item.img">
                </div>
                <div class="col-md-8" id="tekst-container">
                    <h5 class="card-title" id="card-title">{{ item.name }}</h5>
                    <p class="card-text" id="card-description">{{ item.description }}</p>
                    <p class="card-text" id="availability-text"
                        :class="{ 'available': item.availability, 'not-available': !item.availability }">{{
                            item.availability ?
                            'Beschikbaar' : 'Niet beschikbaar' }}
                    </p>
                    <div class="btn-group" v-if="this.isAdmin.isAdmin" id="btn">
                        <button @click="deleteItem(item.id)" class="btn btn-danger" id="btn-delete">
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
    width: 90%;
    position: relative;
    margin: 2% auto;
    background-color: rgb(231, 229, 229);
}

.row {
    width: 100%;
    background-color: rgb(231, 229, 229);
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
</style>