import axios from 'axios'
var config = require('../../../config')


var backendConfigurer = function () {
  switch (process.env.NODE_ENV) {
    case 'development':
      return 'http://' + config.dev.backendHost + ':' + config.dev.backendPort;
    case 'production':
      return 'https://' + config.build.backendHost + ':' + config.build.backendPort;
  }
};

var backendUrl = backendConfigurer();

var AXIOS = axios.create({
  baseURL: backendUrl,
  // headers: { 'Access-Control-Allow-Origin': frontendUrl }
})

import Logout from "@/components/Logout";

export default {
  name: 'manager-profile',
  components: {
    Logout
  },
  data() {
    return {
      manager: {
        name: '',
        email: this.$store.state.email,
        password: this.$store.state.password,
        status: 'Active',
      },
      input: {
        newName: '',
        oldPassword: '',
        newPassword: ''
      },
      allproducts: [],
      productMsg: '',
      removedProduct: '',

      sellers: [],
      sellerMsg: [],
      customers: [],
      customerMsg: [],
      removedAccount: '',
      isDisabled: false,
      message: 'Welcome'
    }
  },
  created: function () {
    AXIOS.get('/find-manager?email=' + this.$store.state.email)
      .then(response => {
        this.message = "find the a manager: " + response.data
        this.manager.name = response.data.name
        this.manager.email = response.data.email
        this.manager.password = response.data.password
      })
      .catch(e => {
        this.message = e
      });
    AXIOS.get('/products')
      .then(response => {
        this.allproducts = response.data
        this.productMsg = "Find all products"
      })
      .catch(e => {
        this.productMsg = e
      });
    AXIOS.get('/sellers')
      .then(response => {
        this.sellers = response.data
        this.sellerMsg = "Find all sellers"

      })
      .catch(e => {
        this.sellerMsg = e
      });
    AXIOS.get('/customers')
      .then(response => {
        this.customers = response.data
        this.customerMsg = " Find all customers "

      })
      .catch(e => {
        this.customerMsg = e
      })
  },


  methods: {
    changePassword: function () {
      AXIOS.put('/manager-changePassword?email=' + this.manager.email +
        '&password=' + this.input.oldPassword +
        '&newPassword=' + this.input.newPassword)
        .then(response => {
          this.manager.password = this.input.newPassword
          this.$store.commit('password', this.input.newPassword)
          this.message = 'Password has been putdate'
        }).catch(e => {
          this.message = 'Fail to change password'
        })
    },
    changeName: function () {
      AXIOS.put('/manager-changeName?email=' + this.manager.email +
        '&newName=' + this.input.newName)
        .then(response => {
          this.manager.name = this.input.newName
          this.message = 'Password has been putdate'
        }).catch(e => {
          this.message = 'Fail to change name'
        })
    },
    suspendAccount: function () {
      AXIOS.put('/suspend-account?managerEmail=' + this.manager.email + "&email=" + this.removedAccount)
        .then(response => {
          this.customerMsg = "account removed "
          this.sellerMsg = "account removed "
        })
        .catch(e => {
          this.customerMsg = e
          this.sellerMsg = e
        })
    },
    approveAccount: function(){
      AXIOS.put('/approve-account?managerEmail=' + this.manager.email + "&email=" + this.removedAccount)
      .then(response => {
        this.customerMsg = "account activated "
        this.sellerMsg = "account activated"
      })
      .catch(e => {
        this.customerMsg = e
        this.sellerMsg = e
      })

    },
    suspendProduct: function () {
      AXIOS.put('/suspend-product?managerEmail=' + this.$store.state.email + "&productID=" + this.removedProduct)
        .then(response => {
          this.productMsg = "product removed"
        })
        .catch(e => {
          this.productMsg = e
        })
    },
    approveProduct: function() {
      AXIOS.put('/approve-product?managerEmail=' + this.$store.state.email + "&productID=" + this.removedProduct)
      .then(response => {
        this.productMsg = "product removed"
      })
      .catch(e => {
        this.productMsg = e
      })
    },
    createPromotion: function() {
      this.$router.push('/MakePromotion')
    }
  }


} 