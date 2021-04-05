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
    name: 'searchproduct',
  
    data() {
      return {
        id:'',
        info: '',
        error:''
  
      }
    },
  
    
    methods: {
      
      getProduct: function () {
        // Initializing persons from backend
        
        AXIOS.get('/product?id=' + this.id)
          .then(response => {
            // JSON responses are automatically parsed.
            this.info = response.data
            this.error = ''
          })
          .catch(e => {
            this.error = "Id cannot be empty Or no product with this id"
          })
      }
  
    }
   
  } 