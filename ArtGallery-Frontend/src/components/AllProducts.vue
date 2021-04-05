<template>

   <div id="product">
   <center>
   <div class="titile">
  <h1>All Products</h1>
    </div>
        <div class="detail">
        <div v-for="product in products" :key="product.id">
            <div v-if="product.productStatus==='Selling' || product.productStatus==='DisplayOnly' ">
            
            <div class="image">
            <img v-bind:src="product.picutreUrl" class= "image_p">
            </div>

            <div class="info">
            <p>Product Id: {{product.id}}</p>
            <p>Status: {{product.productStatus}}, Delivery Method: {{product.supportedDelivery}}</p>
            <p>Product Price: {{ product.price }}</p>
            <p>Product Name: {{product.name}}</p>
            <p>Seller Email: {{product.seller}}</p>
            <p>Picture URL: {{product.picutreUrl}}</p>
            </div>
            </div>
        
        </div>
        </div>
        {{ errorProduct }}
      </center>
      
    </div>
</template>

<script src="./scripts/product.js"></script> 

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

h1,
h2 {
  font-weight: normal;
}


.detail{
  margin-top: 10%;
}

.info{
  margin-bottom: 5%;
}

.image_p{
  max-width:800px;
  max-height: 700px;
}

</style>