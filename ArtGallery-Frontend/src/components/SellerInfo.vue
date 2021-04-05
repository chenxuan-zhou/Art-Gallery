<template>
<div id="sellerinfo">
<center>
    
    <div class="sellerInfo">
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
          <label for="input-profile">New Profile</label>
          <input
            type="text"
            v-model="input.profile"
            class="form-control"
            id="input-profile"
            placeholder="New Profile"
          />
         </div>

        <div class="btn-group">
          <button class="btn btn-primary" v-on:click="changeProfile()">
            Change Profile
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
            placeholder="New Profile"
          />
         </div>

        <div class="btn-group">
          <button class="btn btn-primary" v-on:click="changePassword()">
            Change Password
          </button>
        </div>  

          <div class="btn-group">
          <button class="btn btn-success" v-on:click="$router.push('/SellerProfile'); ">
            Back To Account
          </button>
        </div> 
        <div class="err">{{ error }}</div>



    </div>


</center>
</div>
</template>

<script src="./scripts/sellerinfo.js">
</script>

<style scoped>

.sellerInfo {
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