<!-- author:Yutian Fu-->
<template>
  <div id="makePromotion">
    <center>
      <div class="container">
        <h6><span class="badge badge-secondary"> Make Promotion</span></h6>

        <div class="form-group">
          <label for="input-start"> Start Date </label>
          <input
            type="date"
            v-model="startDate"
            class="form-control"
            id="input-start"
            placeholder="enter startDate"
          />
        </div>

        <div class="form-group">
          <label for="input-end">End Date</label>
          <input
            type="date"
            v-model="endDate"
            class="form-control"
            id="input-end"
            placeholder="enter endDtae"
          />
        </div>

        <div class="form-group">
          <label for="input-product">Promotional products</label>
          <input
            type="text"
            v-model="productID"
            class="form-control"
            id="input-product"
            placeholder="enter a promotional productID here"
          />
        </div>

        <div class="form-group">
          <label for="input-promotionID">Create a tracking number</label>
          <input
            type="text"
            v-model="promotionID"
            class="form-control"
            id="input-promotionID"
            placeholder="please remember this number in the future"
          />
        </div>

        <div class="form-group">
          <label for="input-managerEmail">Your Email</label>
          <input
            type="text"
            v-model="managerEmail"
            class="form-control"
            id="input-managerEmail"
            placeholder="your email"
          />
        </div>

        <div class="btn-group">
          <button class="btn btn-primary" >
            <a onclick="document.getElementById('light').style.display='block';document.getElementById('fade').style.display='block'" @click.prevent="makePromotion()">Make a Promotion </a>
          </button>
        </div>

        <div class="btn-group">
          <button class="btn btn-success" v-on:click="login()">
            Back To Account
          </button>
        </div>



      </div>
      <div id="light" class="white_content">

        <a href="javascript:location.reload();" onclick="document.getElementById('light').style.display='none';document.getElementById('fade').style.display='none'">
          {{promotionMsg}}</a>

      </div>
    </center>
  </div>

</template>

<script src="./scripts/makePromotion.js">
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
.black_overlay {
  display: none;
  position: absolute;
  top: 0%;
  left: 0%;
  width: 100%;
  height: 100%;
  background-color: black;
  z-index: 1;
  -moz-opacity: 0.8;
  opacity: .80;
  filter: alpha(opacity=88);
}
.white_content {
  display: none;
  position: absolute;
  top: 25%;
  left: 25%;
  width: 25%;
  height: 25%;
  padding: 20px;
  border: 10px solid lightskyblue;
  background-color: white;
  z-index: 2;
  overflow: auto;

}

</style>
