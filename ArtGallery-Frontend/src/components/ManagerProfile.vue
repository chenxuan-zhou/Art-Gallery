<template>
  <div id="manager-profile">
    <h3>Welcome, Manager {{ manager.name }}</h3>
    <div>
      <p>new Message: {{ message }} <br /></p>
    </div>

    <div id="manager" class="name-card">
      Email: {{ manager.email }}<br />
      Status: {{ manager.status }}<br />
      Password: {{ manager.password }}<br />
    </div>

    <Logout />

    <div class="create-promotion">
      <button
        type="button"
        class="btn btn-primary"
        v-on:click="createPromotion()"
      >
        Create a promotion
      </button>
    </div>

    <form>
      <div class="changeSetting">
        <label for="old-password">Old Password</label>
        <input
          type="password"
          class="form-control"
          id="old-password"
          v-model="input.oldPassword"
          placeholder="Old Password"
        />
      </div>

      <div class="changeSetting">
        <label for="new-password">New Password</label>
        <input
          type="password"
          class="form-control"
          id="new-password"
          v-model="input.newPassword"
          placeholder="New Password"
        />
        <button
          type="submit"
          class="btn btn-primary"
          v-on:click="changePassword()"
        >
          Change Password
        </button>
      </div>
    </form>

    <form>
      <div class="changeSetting">
        <label for="newName">Name</label>
        <input
          type="name"
          class="form-control"
          id="newName"
          v-model="input.newName"
          placeholder="Enter new name"
        />
        <button type="submit" class="btn btn-primary" v-on:click="changeName()">
          Change name
        </button>
      </div>
    </form>

    <center>
      <h3>Manage Products</h3>

      <h5>Message: {{ this.productMsg }}</h5>
      <table>
        <tr>
          <th>ProductID</th>
          <th>Name</th>
          <th>Price</th>
          <th>Status</th>
          <th>Seller email</th>
          <th>Suspend</th>
        </tr>

        <tr v-for="product in allproducts" :key="product">
          <td>
            <input type="text" v-model="product.id" :disabled="isDisabled" />
          </td>
          <td>
            <input type="text" v-model="product.name" :disabled="isDisabled" />
          </td>
          <td>
            <input
              type="text"
              v-model="product.price"
              :disabled="isDisabled"
            />
          </td>
          <td>
            <input type="text" v-model="product.productStatus" :disabled="isDisabled" />
          </td>
          <td>
            <input
              type="text"
              v-model="product.seller"
              :disabled="isDisabled"
            />
          </td>

          <td>
            <a href="#" @click.prevent="removedProduct=product.id; suspendProduct()">Suspend product</a>
            <br/>
            <a href="#" @click.prevent="removedProduct=product.id; approveProduct()">Back to selling</a>
          </td>
        </tr>
      </table>

      <br />

      <h3>Manage Accounts</h3>
      <br />

      <h5>Customers, Message: {{ this.customerMsg }}</h5>
      <table>
        <tr>
          <th>Customer email</th>
          <th>Name</th>
          <th>Balance</th>
          <th>Status</th>
          <th>number of orders</th>
          <th>Suspend</th>
        </tr>

        <tr v-for="customer in customers" :key="customer">
          <td>
            <input
              type="text"
              v-model="customer.email"
              :disabled="isDisabled"
            />
          </td>
          <td>
            <input type="text" v-model="customer.name" :disabled="isDisabled" />
          </td>
          <td>
            <input
              type="text"
              v-model="customer.balance"
              :disabled="isDisabled"
            />
          </td>
          <td>
            <input
              type="text"
              v-model="customer.accountStatus"
              :disabled="isDisabled"
            />
          </td>
          <td>
            <input
              type="text"
              v-model="customer.order.length"
              :disabled="isDisabled"
            />
          </td>

          <td>
            <a href="#" @click.prevent="removedAccount=customer.email; suspendAccount()">Suspend Account</a>
            <br/>
            <a href="#" @click.prevent="removedAccount=customer.email; approveAccount()">Back to use</a>
          </td>
        </tr>
      </table>

      <h5>Sellers, Message: {{ this.sellerMsg }}</h5>
      <table>
        <tr>
          <th>Seller email</th>
          <th>Name</th>
          <th>Income</th>
          <th>Status</th>
          <th>number of products</th>
          <th>Suspend</th>
        </tr>

        <tr v-for="seller in sellers" :key="seller">
          <td>
            <input type="text" v-model="seller.email" :disabled="isDisabled" />
          </td>
          <td>
            <input type="text" v-model="seller.name" :disabled="isDisabled" />
          </td>
          <td>
            <input type="text" v-model="seller.income" :disabled="isDisabled" />
          </td>
          <td>
            <input
              type="text"
              v-model="seller.accountStatus"
              :disabled="isDisabled"
            />
          </td>
          <td>
            <input
              type="text"
              v-model="seller.products.length"
              :disabled="isDisabled"
            />
          </td>

          <td>
            <a href="#" @click.prevent="removedAccount=seller.email; suspendAccount()">Suspend Account</a>
            <br/>
            <a href="#" @click.prevent="removedAccount=seller.email; approveAccount()">Back to use</a>
          </td>
        </tr>
      </table>
    </center>
  </div>
</template>

 <script src="./scripts/manager-profile.js"></script>


<style scoped>
.changeSetting {
  text-align: left;
  margin: 50px;
  width: 30%;
}

table {
  width: 100%;
  background: #000;
}
table tr {
  background: #fff;
  border: 1px darkgrey;
  width: 100px;
}

.name-card {
  width: 30%;
  margin: 50px;
}
</style>