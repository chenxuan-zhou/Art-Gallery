
<template>
<div id='sellerDisplay'>
    
    <center>
    
    <div class="search">
    <h><span class="badge badge-secondary"> Search Artist By Email </span></h>
    <input type="email" v-model="email" class="form-control" id="inputEmail" 
    aria-describedby="emailHelp" placeholder="example@mail.com">
     
      <div class="btn-group">
        <button class="btn btn-primary" v-on:click="getSeller()">
            Search Artist
          </button>
        </div>
      <p>
        <span v-if="errorSellers" style="color:red"> Error: {{errorSellers}}</span>
      </p>

    </div>

        <div class="seller">
  
    <p>Name: {{info.name}} </p>
    <p>Email: {{info.email}} </p>
    <p>Profile: {{info.profile}}</p>
    <p>Products:</p>
   
        <div v-for="product in info.products" v-bind:key="product.name">
            <div class="product">
            <img v-bind:src="product.picutreUrl">
            <p>Id: {{product.id}}</p>
            <p>Name: {{product.name}}</p>
            <p>Price: {{ product.price }}</p>
            <p>Status: {{product.productStatus}}, Delivery Method: {{product.supportedDelivery}}</p>
            </div>
        </div>

        </div>
   
   
    
    </center>
</div>
</template>


<script src="./scripts/sellerDisplay.js">
    
</script>

<style scoped>
.search{
    margin-top:2%;
    
    margin-top:10px;
    max-width: 400px;
    font-weight: bold;

   
}

.product{
   margin-bottom:10%;  
}

.btn-group {
  margin-top: 2%;
  margin-bottom: 5%;
  width: 100%;
}


.badge {
  font-weight: bold;
  font-size: 110%;
  width: 100%;
  margin-bottom: 5%;
  padding: 3%;
}

label {
  font-size: 400%;
}

.btn {
  width: 100%;
  font-size: 110%;
  font-weight: bold;
}
</style>
