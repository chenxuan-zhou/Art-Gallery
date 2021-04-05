<template>
<div id="sellerproductinfo">
<center>
    

  <div class="productinfo">
    <h><span class="badge badge-secondary"> Update Product Information </span></h>
        
        <div class="btn-group">
          <button class="btn btn-success" v-on:click="$router.push('/SellerProfile'); ">
            Back To Account
          </button>
        </div>

        <div class="err">{{ error }}</div>
        <!-- -->
        <div class="form-group">
          <label for="input-productId">Product Id</label>
          <input
            type="number"
            v-model="input.productId"
            class="form-control"
            id="input-productId"
            placeholder="Product Id"
          />
         </div>

         <div class="btn-group">
          <button class="btn btn-warning" v-on:click="deleteProduct()">
            Delete Product
          </button>
        </div>

         <div class="form-group">
          <label for="input-name">New Name</label>
          <input
            type="text"
            v-model="input.name"
            class="form-control"
            id="input-name"
            placeholder="New Product Name"
          />
         </div>

        <div class="btn-group">
          <button class="btn btn-primary" v-on:click="changeName()">
            Change Product Name
          </button>
        </div>

        <!-- -->

        <div class="form-group">
          <label for="input-price">New Price</label>
          <input
            type="number"
            v-model="input.price"
            class="form-control"
            id="input-price"
            placeholder="New Product Price"
          />
         </div>

        <div class="btn-group">
          <button class="btn btn-primary" v-on:click="changePrice()">
            Change Product Price
          </button>
        </div>

        <!-- -->
        <div class="form-group">
          <label for="input-status"> New Status </label>
          <select
            class="form-control"
            id="input-status"
            v-model="input.status"
            required
          >
            <option>Selling</option>
            <option>DisplayOnly</option>
          </select>
        </div>

        <div class="btn-group">
          <button class="btn btn-primary" v-on:click="changeStatus()">
            Change Product Status
          </button>
        </div>

        <!-- -->
        <div class="form-group">
          <label for="input-delivery"> New Delivery </label>
          <select
            class="form-control"
            id="input-delivery"
            v-model="input.delivery"
            required
          >
            <option>Mail</option>
            <option>Pickup</option>
            <option>Both</option>
          </select>
        </div>

        <div class="btn-group">
          <button class="btn btn-primary" v-on:click="changeDelivery()">
            Change Product Delivery Method
          </button>
        </div>


    <div class="order">
    <h><span class="badge badge-secondary"> Update Shipping Information </span></h>
    </div>
    <div class="err">{{ errorOrder }}</div>
    <!-- -->
        <div class="form-group">
          <label for="input-orderId">Order Id</label>
          <input
            type="number"
            v-model="input.orderId"
            class="form-control"
            id="input-orderId"
            placeholder="Order Id"
          />
         </div>

         <div class="form-group">
          <label for="input-shipped">Shipped Date</label>
          <input
            type="date"
            v-model="input.shipped"
            class="form-control"
            id="input-shipped"
            placeholder="YYYY-MM-DD"
          />
         </div>

        <div class="btn-group">
          <button class="btn btn-primary" v-on:click="shipping()">
            Update Shipping
          </button>
        </div>


 
  </div>

</center>
</div>
</template>

<script src="./scripts/sellerproductinfo.js">
//{{ $store.state.email }}
//    {{ $store.state.password }} 
//
</script>

<style scoped>

.productinfo {
  margin-top:10px;
  max-width: 400px;
  font-weight: bold;
}

.badge {
  font-weight: bold;
  font-size: 110%;
  width: 100%;
  margin-bottom: 5%;
  padding: 3%;
}

.btn-group {
  margin-top: 2%;
  margin-bottom: 5%;
  width: 100%;
}

.btn-group .btn {
  width: 100%;
  font-size: 110%;
  font-weight: bold;
}

label {
  float: left;
  font-size: 125%;
  margin-left: 1%;
}

.order{
  margin-top:20%;
}

.err {
  font-weight: 500;
  font-size: 110%;
}

</style>