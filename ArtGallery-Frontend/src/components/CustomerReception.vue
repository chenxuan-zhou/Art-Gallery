<template>
  <div id="customerreception">
    <center>
      <div class="order">
        <h>
          <span class="badge badge-secondary">Confirm Received</span>
        </h>
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

      <div class="btn-group">
        <button class="btn btn-primary" v-on:click="received()">Confirm Received</button>
      </div>
      <div class="btn-group">
        <button
          class="btn btn-success"
          v-on:click="$router.push('/CustomerProfile'); "
        >Back To Account</button>
      </div>
    </center>
  </div>
</template>

<script src="./scripts/customerreception.js">
</script>

<style scoped>
.customerreception {
  margin: 7.5%;
  max-width: 400px;
  font-weight: bold;
}

.btn-group {
  margin-top: 2%;
  margin-bottom: 5%;
  width: 100%;
}

label {
  float: left;
  font-size: 125%;
  margin-left: 1%;
}

.btn-group .btn {
  width: 100%;
  font-size: 110%;
  font-weight: bold;
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