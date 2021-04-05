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
  baseURL: backendUrl
  // headers: { 'Access-Control-Allow-Origin': frontendUrl }
})

export default {
  name: 'seller-display',

  data() {
    return {
      email: '',
      count:0,
      info: 'init info',
      errorSellers: ''

    }
  },

  // created: function(){
  // // Initializing persons from backend
  //    AXIOS.get('/seller?email=Gabriel@gmail.com')
  //    .then(response => {
  //      //JSON responses are automatically parsed.
  //      this.info = response.data
  //    })
  //    .catch(e => {
  //      this.errorSellers = e
  //    })
  // },
  
  //<button class="btn btn-primary" v-on:click="increase(10)"> test </button> 
  methods: {
    increase:function(number){
      this.count=number
    },
    getSeller: function () {
      // Initializing persons from backend
      
      AXIOS.get('/seller?email=' + this.email)
        .then(response => {
          // JSON responses are automatically parsed.
          this.info = response.data
          this.errorSellers = ''
        })
        .catch(e => {
          this.errorSellers = "Email cannot be empty Or no seller with this email"
        })
    }

  }
 
} 