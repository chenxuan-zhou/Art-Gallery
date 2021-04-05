<template>
    <div>

    <div id="seller-profile">
    <center>
        <h1> Welcome Artist!</h1>
        <p1>Email: {{ $store.state.email }}</p1><!--{{ $store.state.password }} -->
       <p>Name: {{info.name}}</p>
       <p>Profile: {{info.profile}}</p>
       <p>Income: {{info.income}}</p>
       <p>Account Status: {{info.accountStatus}}</p>
       
            <div class="button_in">
          <button class="btn btn-success" v-on:click="$router.push('createproduct')">
            Create Product
          </button>
          <button class="btn btn-success" v-on:click="$router.push('sellerinfo')">
            Change Personal Information
          </button>
          <button class="btn btn-success" v-on:click="$router.push('sellerproductinfo')">
            Update Product/Order
          </button>
          <button class="btn btn-success" v-on:click="$router.push('withdrawl')">
            Withdrawl Money
          </button>
            </div>

          <Logout/>

        <div class="button1">
       <br><p2>Number of Products: {{productsize}}</p2>
        </div>

        

        <div v-for="product in info.products" v-bind:key="product.name">
            <div class="product_in">
            <img v-bind:src="product.picutreUrl" class = "image_seller">
            <p>Id: {{product.id}}</p>
            <p>Name: {{product.name}}</p>
            <p>Price: {{ product.price }}</p>
            <p>Status: {{product.productStatus}}, Delivery Method: {{product.supportedDelivery}}</p>
            <p>Picture URL: {{product.picutreUrl}}</p>
            </div>
        </div>

        <br><p3>Number of Orders: {{orderSize}}</p3>
        <div v-for="order in info.orders" v-bind:key="order.name">
            <div class="order_in">
                <p>Order Id: {{order.id}}</p>
                <p>Delivery Method: {{order.deliveryMethod}}</p>
                <p>Order Status: {{order.orderStatus}}</p>
                <p>Address: {{order.address}}</p>
                <p>Ordered Time: {{order.ordered}}</p>
                <p>Shipped Time: {{order.shipped}}</p>
                <p>Price: {{order.price}}</p>
                <p>Product Id: {{order.product}}</p>
                <p>Customer: {{order.customer}}</p>
            </div>

        </div>

    </center>
    </div>
    </div>
</template>


<script src="./scripts/seller-profile.js">


</script>

<style scoped>
h1 {
    margin-bottom: 80px
}

p1{
    font-size: 200%
}

.product_in{
    margin-bottom: 5%
}
.order_in{
    margin-bottom:5%
}
p{
    font-size:130%
}
p2{
    font-size: 150%;
    font-weight: bold;
}

p3{
    font-size: 150%;
    font-weight: bold;
}

.button_in{
    margin-top:1%;
    margin-bottom:1%
}

.image_seller{
  max-width:600px;
  max-height:600px;
}

</style>