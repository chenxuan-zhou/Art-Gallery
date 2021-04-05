<template>
  <div class="make-order">
    <center>
      <div class="container">
        <h6><span class="badge badge-secondary"> Make Order</span></h6>

        <div class="form-group">
          <label for="input-address"> Address </label>
          <input
            type="text"
            v-model="address"
            class="form-control"
            id="input-address"
            placeholder="enter address"
          />
        </div>

        <div class="form-group">
          <label for="input-productID">Prodct ID</label>
          <input
            type="text"
            v-model="productID"
            class="form-control"
            id="input-productID"
            placeholder="product ID"
          />
        </div>

        <div class="btn-group">
          <button class="btn btn-primary" v-on:click="findProduct()">
            Check the product
          </button>
        </div>

        <h4 v-if="error ">{{ error }}. {{isChecked}}</h4>

        <!-- <div class="form-group">
          <label for="input-price">Prodct Price</label>
          <input
            type="text"
            v-model="price"
            class="form-control"
            id="input-price"
            placeholder="product price"
          />
        </div>

        <div class="form-group">
          <label for="input-sellerEmail">Seller(Artist) Email</label>
          <input
            type="text"
            v-model="sellerEmail"
            class="form-control"
            id="input-sellerEmail"
            placeholder="Artist's email"
          />
        </div> -->
         <h7><span class="badge badge-secondary"> Payment Info</span></h7>
        <div class="form-group">
          <label for="input-card">Credit/Debit Card No.</label>
          <input
            type="text"
            v-model="card"
            class="form-control"
            id="input-card"
            placeholder="Bank Card Number"
          />
        </div>

        <div class="form-group">
          <label for="input-cvv">CVV Code</label>
          <input
            type="text"
            v-model="cvv"
            class="form-control"
            id="input-cvv"
            placeholder="3 digits Card Verification Value"
          />
        </div>

        <div class="form-group">
          <label for="input-dm">Choose delivery method: </label>

            <select name="cars" id="cars" v-model="dm">
            <option v-if="isMail" value="Mail">Mail</option>
            <option v-if="isPickup" value="Pickup">Pickup</option>

          </select>
        </div>

        

        <div class="btn-group">
          <button class="btn btn-success" v-on:click="makeOrder()">
            Place the Order
          </button>
        </div>

        <div class="btn-group">
          <button class="btn btn-primary" v-on:click="$router.push('/CustomerProfile'); ">
            Back To Account
          </button>
        </div>

        <h4>Order message: {{ orderMsg }}</h4>
      </div>
    </center>
  </div>
</template>

<script src="./scripts/makeorder.js">
</script>


<style scoped>
.container {
  margin: 7.5%;
  max-width: 400px;
  font-weight: bold;
}

.image {
  width: 100%;
  margin-top: 5%;
  max-height: 75%;
  text-align: center;
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

.normal {
  font-weight: 500;
}

label {
  float: left;
  font-size: 125%;
  margin-left: 1%;
}

.badge {
  font-weight: bold;
  font-size: 110%;
  width: 100%;
  margin-bottom: 5%;
  padding: 3%;
}

.err {
  font-weight: 500;
  font-size: 110%;
}

</style>