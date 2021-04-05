import axios from 'axios'
var config = require('../../../config')

var backendConfigurer = function(){
  switch(process.env.NODE_ENV){
      case 'development':
          return 'http://' + config.dev.backendHost + ':' + config.dev.backendPort;
      case 'production':
          return 'https://' + config.build.backendHost + ':' + config.build.backendPort ;
  }
};

var backendUrl = backendConfigurer();

var AXIOS = axios.create({
  baseURL: backendUrl
  // headers: { 'Access-Control-Allow-Origin': frontendUrl }
})

export default {
  name: 'product',

  data () {
    return {
      products:[],
      errorProducts: ''
    }
  },

  created: function () {
     // Initializing persons from backend
     AXIOS.get('/products')
     .then(response => {
       // JSON responses are automatically parsed.
       this.products = response.data
     })
     .catch(e => {
       this.errorProduct = e
     })
  }
} 