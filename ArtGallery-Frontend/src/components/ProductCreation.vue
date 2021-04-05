<template>
  <div class="product-creation">
   
    <center>
      <div class="container">
        <h6><span class="badge badge-secondary"> Product Creation </span></h6>

        <div class="form-group">
          <label for="input-name">Name</label>
          <input
            type="text"
            v-model="input.name"
            class="form-control"
            id="input-name"
            placeholder="product name"
          />
        </div>

        <div class="form-group">
          <label for="input-price">Price</label>
          <input
            type="number"
            v-model="input.price"
            class="form-control"
            id="input-price"
            placeholder="product price"
          />
        </div>

        <div class="form-group">
          <label for="input-status"> Status </label>
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

        <div class="form-group">
          <label for="input-delivery"> Delivery </label>
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

        <div class="form-group">
          <label for="input-picture">Picture</label>
          <input
            type="url"
            v-model="input.picture"
            class="form-control"
            id="input-picture"
            placeholder="a link to a picture of your product"
          />
        </div>

        <div class="btn-group">
          <button class="btn btn-primary" v-on:click="createProduct()">
            Create Product
          </button>
        </div>

        <div class="btn-group">
        <button class="btn btn-success" v-on:click="login()">
            Back To Account
          </button>
        </div>

        <div class="err">{{ error }}</div>

        <img v-bind:src="input.picture" class="image" />
      </div>
    </center>
  </div>
</template>

<script src="./scripts/product-creation.js">
//  {{ $store.state.email }}
//     {{ $store.state.password }}
</script>


<style scoped>
.container {
  margin: 3%;
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