<template>
  <div id="login">
    <center>
      <div class="container">
        <h6><span class="badge badge-secondary"> Log In </span></h6>

        <div class="form-group">
          <label for="inputEmail"> Email </label>
          <input
            type="email"
            v-model="input.email"
            class="form-control"
            id="inputEmail"
            aria-describedby="emailHelp"
            placeholder="example@mail.com"
            required
          />
        </div>

        <div class="form-group">
          <label for="inputPassword">Password</label>
          <input
            type="password"
            v-model="input.password"
            class="form-control"
            id="inputPassword"
            placeholder="password"
            required
          />
        </div>

        <div class="btn-group">
          <button class="btn btn-primary" v-on:click="customerLogin()">
            Customer
          </button>
          <button class="btn btn-primary" v-on:click="sellerLogin()">
            Seller
          </button>
          <button class="btn btn-primary" v-on:click="managerLogin()">
            Manager
          </button>
          <button class="btn btn-success" v-on:click="$router.push('signup')">
            Sign Up
          </button>
        </div>

        <br />
        <div class="normal">{{ error }}</div>
      </div>
    </center>
  </div>
</template>

<script src="./scripts/login.js"></script>

<style scoped>
.container {
  margin: 7.5%;
  max-width: 400px;
  font-weight: bold;
}
.btn-group {
  margin-top: 2%;
  width: 100%;
}

.btn-group .btn {
  width: 25%;
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
</style>