<template>
<div id="customerinfo">
<center>
    
    <div class="customerInfo">
    <h6><span class="badge badge-secondary"> Change Information </span></h6>

         <div class="form-group">
          <label for="input-name">New Name</label>
          <input
            type="text"
            v-model="input.name"
            class="form-control"
            id="input-name"
            placeholder="New Name"
          />
         </div>

        <div class="btn-group">
          <button class="btn btn-primary" v-on:click="changeName()">
            Change Name
          </button>
        </div>
       
        <!-- -->

        <div class="form-group">
          <label for="input-password">New Password</label>
          <input
            type="password"
            v-model="input.password"
            class="form-control"
            id="input-password"
            placeholder="New Password"
          />
         </div>
		 
        <div class="form-group">
          <label for="input-confirmpassword">Confrim Password</label>
          <input
            type="password"
            v-model="input.confirmPassword"
            class="form-control"
            id="input-confirmpassword"
            placeholder="Confirm Password"
          />
         </div>

        <div class="btn-group">
          <button class="btn btn-primary" v-on:click="changePassword()">
            Change Password
          </button>
        </div>  

          <div class="btn-group">
          <button class="btn btn-success" v-on:click="$router.push('/CustomerProfile'); ">
            Back To Account
          </button>
        </div> 
        <div class="err">{{ error }}</div>



    </div>


</center>
</div>
</template>

<script src="./scripts/customerinfo.js">
</script>

<style scoped>

.customerInfo {
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