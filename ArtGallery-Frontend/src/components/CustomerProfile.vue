<template>
  <div>
    <div id="customer-profile">
      <center>
        <h1>Welcome Customer</h1>
        <p1>Email: {{$store.state.email}}</p1>
        <p>Name: {{info.name}}</p>

        <p>Account Status: {{info.accountStatus}}</p>
        <button class="btn btn-success" v-on:click="$router.push('makeorder')">Make Order</button>
        <div class="PersonalInfo">
          <button class="btn btn-dark" v-on:click="$router.push('customerinfo')">Edit Info</button>
        </div>
        <div class="PersonalInfo">
          <button class="btn btn-warning" v-on:click="$router.push('customerreception')">Confirm Reception of Order</button>
        </div>

        <Logout />

        <div class="orderTitle">
          <br />
          <p2>Number of Orders: {{orderSize}}</p2>
        </div>
        <div v-for="order in info.order" v-bind:key="order.name">
          <div class="order">
            <img v-bind:src="order.picture" />
            <p>Order Id: {{order.id}}</p>
            <p>Delivery Method: {{order.deliveryMethod}}</p>
            <p>Order Status: {{order.orderStatus}}</p>
            <p>Address: {{order.address}}</p>
            <p>Ordered Time: {{order.ordered}}</p>
            <p v-if="order.orderStatus==='Shipped'">Shipped Time: {{order.shipped}}</p>
            <p v-if="order.orderStatus==='Delivered'">Shipped Time: {{order.shipped}}</p>
            <p>Price: {{order.price}}</p>
            <p>Product Id: {{order.product}}</p>
            <p>Seller: {{order.seller}}</p>
            <p>Picutre Url: {{order.picture}}</p>
          </div>
        </div>
      </center>
    </div>
  </div>
</template>



<script src="./scripts/customer-Profile.js">
//<img v-bind:src="product.picutreUrl">
</script> 

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
h1 {
  margin-bottom: 80px;
}

p1 {
  font-size: 200%;
}

.order {
  margin-bottom: 5%;
}
p {
  font-size: 130%;
}
p2 {
  font-size: 150%;
  font-weight: bold;
}

p3 {
  font-size: 150%;
  font-weight: bold;
}
.PersonalInfo {
  margin-top: 1%;
  margin-bottom: 1%;
}
.logout {
  margin-top: 1%;
  margin-bottom: 1%;
}

.orderTitle {
  margin-top: 1%;
  margin-bottom: 1%;
}
</style>