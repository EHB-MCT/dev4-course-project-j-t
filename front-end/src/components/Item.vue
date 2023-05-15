<script>
import Nav from './Nav.vue'

    export default {
        
        data() {
            return {
                items: [],
                isAdmin:JSON.parse(sessionStorage.getItem("user"))
            }
        },
        methods: {
            async getData() {
                const res = await fetch("http://localhost:8080/items");
                const finalRes = await res.json();
                this.items = finalRes.map(m => m);
                console.log(this.isAdmin.isAdmin)
            },
            
            async deleteItem(itemId){
                const confirmed = window.confirm('Weet u zeker dat u dit item wilt verwijderen?');
                if(confirmed){
                    await fetch(`http://localhost:8080/items/${itemId}`, {
                    method: "DELETE"
                });
                this.items = this.items.filter(item => item.id !== itemId);
                }
                
            }
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
    <div class="items">
      <div class="row" v-for="item in this.items" :key="item.id">
        <div class="col-md-4" v-if="item.img">
          <img  class="img-fluid" :src="item.img">
        </div>
        <div class="col-md-8">
          <h5 class="card-title">{{ item.name }}</h5>
          <p class="card-text" id="card-description">{{ item.description }}</p>
          <p class="card-text" id="availability-text" :class="{ 'available': item.availability, 'not-available': !item.availability }">{{ item.availability? 'Beschikbaar' : 'Niet beschikbaar' }}</p>
          <button v-if="this.isAdmin.isAdmin" @click="deleteItem(item.id)" class="btn btn-danger" id="btn-delete">
        <i class="fa fa-trash"></i>
      </button> </div>
      </div>
    </div>
</main>
</template>
<style>
.logout{
  font-weight: 300;
  text-decoration:underline;
}
.navbar-nav{
  margin-left:3%;
}
.items{
  display: flex;
  justify-content: center;
  align-items: center;
  flex-wrap: wrap;
  width: 100%;
  margin-top: 3%;
}

.row {
  width: 45%;
  background-color: rgb(246, 246, 246);
  padding: 10px;
  margin: 1%;
  height: 40vh;
  position: relative;
}
.available {
  color: rgb(0, 194, 0);
  font-weight: bold;
}
.nav-link{
  font-weight: bold;
}
.not-available {
  color: red;
  font-weight: bold;

}
#card-description{
  overflow: hidden;
  min-height: 20px;
  max-height: 47px;
}
#btn-delete{
  position: absolute;
  bottom: 10px;
  right: 10px;
}
#availability-text{
  position: absolute;
  bottom: 3px;
  left: 20px;
}
@media only screen and (min-width: 760px) {
  .row {
  height: 25vh;
}
}
</style>