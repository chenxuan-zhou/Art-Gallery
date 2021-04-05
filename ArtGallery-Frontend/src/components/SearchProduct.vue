
<template>
<div id='sellerDisplay'>
    
    <center>
    
    <div class="search">
    <h><span class="badge badge-secondary"> Search Product By Id </span></h>
    <input type="number" v-model="id" class="form-control" id="inputnumber" 
    aria-describedby="emailHelp" placeholder="Product Id">
     
      <div class="btn-group">
        <button class="btn btn-primary" v-on:click="getProduct()">
            Search Product
          </button>
        </div>
      <p>
        <span v-if="error" style="color:red"> Error: {{error}}</span>
      </p>

    </div>

        <div class="product">
  
            <p>Product Id: {{info.id}}</p>
            <p>Status: {{info.productStatus}}</p> 
            <p>Delivery Method: {{info.supportedDelivery}}</p>
            <p>Product Price: {{ info.price }}</p>
            <p>Product Name: {{info.name}}</p>
            <p>Seller Email: {{info.seller}}</p>
            <p>Picture URL: {{info.picutreUrl}}</p>

            <img v-bind:src="info.picutreUrl">
        </div>
   
   
    
    </center>
</div>
</template>


<script src="./scripts/searchproduct.js">
    
</script>


<style scoped>
.search{
    margin-top:2%;
    
    margin-top:10px;
    max-width: 400px;
    font-weight: bold;

   
}


.seller{
   margin-top:5%; 
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
