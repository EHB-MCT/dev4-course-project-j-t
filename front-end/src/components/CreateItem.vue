<script>
import Nav from "./Nav.vue"

export default {
    data() {
        return {
            name: '',
            description: '',
            imageUrl: '',
            availability: true,
            errorMessage: null,
            classAlert: null
        }
    },
    methods: {
        async createItem() {
            if (this.name && this.description) {
                const response = await fetch("http://localhost:8080/items", {
                    method: "POST",
                    headers: { "Content-Type": "application/json" },
                    body: JSON.stringify({ name: this.name, description: this.description, img: this.imageUrl, availability: this.availability }),
                }).then((response) => response.text())
                    .then((data) => {
                        console.log(data)
                    })
                this.classAlert = true
                this.errorMessage = "Item created successfully"
                setTimeout(() => {
                    this.errorMessage = null
                    this.description = ""
                    this.imageUrl = ""
                    this.name = ""
                }, 5000);
            } else {
                this.errorMessage = "Fill in all fields."
                this.classAlert = false
                setTimeout(() => {
                    this.errorMessage = null

                }, 5000);
            }
        },
    },
    mounted() {

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
            <form>
                <div class="form-group">
                    <h2 class="h2">Create new item</h2>
                    <label for="name">Name:</label>
                    <input type="text" class="form-control" id="name" v-model="name" required>
                </div>
                <div class="form-group">
                    <label for="description">Description:</label>
                    <textarea class="form-control" id="description" v-model="description" required></textarea>
                </div>
                <div class="form-group">
                    <label for="url">URL-image:</label>
                    <input type="url" class="form-control" id="url" v-model="imageUrl" required>
                </div><br>
                <button type="submit" class="btn btn-primary" @click.prevent="createItem">Create new item</button>
            </form>
        </div><br><br>
        <div id="err-message" v-if="errorMessage" :class="['alert', classAlert ? 'alert-success' : 'alert-danger']">{{
            this.errorMessage }}</div>
    </main>
</template>

<style>
#err-message {
    width: 90%;
    margin: auto;
}
</style>