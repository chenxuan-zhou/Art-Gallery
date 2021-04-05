<template>
  <div class="withdrawl">
    <center>
     
      <div class="container">
        <h6><span class="badge badge-secondary"> Withdrawl Money </span></h6>

        <div class="form-group">
          <label for="input-accountNo">Banck Account Number</label>
          <input
            type="text"
            v-model="input.accountNo"
            class="form-control"
            id="input-accountNo"
            placeholder="Account No."
          />
        </div>

        <div class="form-group">
          <label for="input-transit">Transit Number</label>
          <input
            type="text"
            v-model="input.transit"
            class="form-control"
            id="input-transit"
            placeholder="Transit No."
          />
        </div>

        <div class="form-group">
          <label for="input-institution">Institution Number</label>
          <input
            type="text"
            v-model="input.institution"
            class="form-control"
            id="input-institution"
            placeholder="Institution No."
          />
        </div>        


        <div class="btn-group">
          <button class="btn btn-primary" v-on:click="withdrawl()">
            Withdrawl Money
          </button>
        </div>

        <div class="btn-group">
          <button class="btn btn-success" v-on:click="$router.push('/SellerProfile'); ">
            Back To Account
          </button>
        </div>

        <div class="err">{{ error }}</div>

        <img v-bind:src="input.picture" class="image" />
      </div>
    </center>
  </div>
</template>

<script src="./scripts/withdrawl.js">
// {{ $store.state.email }}
//    {{ $store.state.password }}
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