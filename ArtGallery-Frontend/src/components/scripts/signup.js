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
  name: 'signup',

  data() {
    return {
      input: {
        name: '',
        email: '',
        password: '',
        profile: '' // seller optional param
      },
      seller: false,
      error: ''
    }
  },

  methods: {
    createCustomer: function () {
      AXIOS.post('/create-customer?name=' + this.input.name + '&email=' + this.input.email + '&password=' + this.input.password)
        .then(response => {
          this.error = "created customer"
          this.$router.push('/login');
        })
        .catch(e => {
          this.error = 'cannot create this customer';
        })

    },

    createSeller: function () {
      AXIOS.post('/create-seller?name=' + this.input.name + '&email=' + this.input.email + '&password=' + this.input.password + '&profile=' + this.input.profile)
        .then(response => {
          this.error = "created seller"
          this.$router.push('/login');
        })
        .catch(e => {
          this.error = 'cannot create this seller';
        })
    },
  }
}