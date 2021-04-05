<template>
  <div id="signup">
    <center>
      <div class="container">
        <div class="btn-group">
          <button
            class="btn btn-secondary"
            v-on:click="seller = true"
            v-show="!seller"
          >
            Customer
          </button>
          <button
            class="btn btn-secondary"
            v-on:click="seller = false"
            v-show="seller"
          >
            Seller
          </button>
        </div>

        <div class="form-group">
          <label for="input-name">Name</label>
          <input
            type="text"
            v-model="input.name"
            class="form-control"
            id="input-name"
            placeholder="your name"
            required
          />
        </div>

        <div class="form-group">
          <label for="input-email"> Email </label>
          <input
            type="email"
            v-model="input.email"
            class="form-control"
            id="input-email"
            aria-describedby="emailHelp"
            placeholder="example@mail.com"
            required
          />
        </div>

        <div class="form-group">
          <label for="input-password">Password</label>
          <input
            type="password"
            v-model="input.password"
            class="form-control"
            id="input-password"
            placeholder="password"
            required
          />
        </div>

        <div class="form-group" v-show="seller">
          <label for="input-profile">Profile</label>
          <input
            type="text"
            v-model="input.profile"
            class="form-control"
            id="input-profile"
            placeholder="something about you"
          />
        </div>

        <div class="btn-toolbar" v-show="!seller">
          <button class="btn btn-success" v-on:click="createCustomer()">
            Sign Up
          </button>
        </div>

        <div class="btn-toolbar" v-show="seller">
          <button class="btn btn-success" v-on:click="createSeller()">
            Sign Up
          </button>
        </div>

        <div class="err">{{ error }}</div>
      </div>
    </center>
  </div>
</template>

<script src="./scripts/signup.js"></script>

<style scoped>
.container {
  margin: 7.5%;
  max-width: 400px;
  font-weight: bold;
}

.btn-toolbar,
.btn-group {
  width: 100%;
  margin-bottom: 5%;
}

.btn-group .btn {
  width: 50%;
  font-weight: bold;
}

.btn-toolbar .btn {
  width: 100%;
  font-weight: bold;
}

.btn {
  font-size: 110%;
}

.err {
  font-weight: 500;
  font-size: 110%;
}

label {
  float: left;
  font-size: 125%;
  margin-left: 1%;
}
</style>