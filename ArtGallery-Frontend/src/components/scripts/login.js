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
  name: 'login',

  data() {
    return {
      input: {
        email: '',
        password: '',
      },
      error: ''
    }
  },

  methods: {

    sellerLogin: function () {
      AXIOS.put('/login?email=' + this.input.email + '&password=' + this.input.password)
        .then(response => {
          this.$store.commit('email', this.input.email);
          this.$store.commit('password', this.input.password);
          this.$store.commit('type', 'seller');
          this.$router.push('/account'); // redirect to profile page
          // external redirection :
          // window.location.href = 'https://www.google.com';
        })
        .catch(e => {
          this.error = 'seller invalid combination';
        })
    },

    customerLogin: function () {
      AXIOS.put('/customer-login?customer=' + this.input.email + '&password=' + this.input.password)
        .then(response => {
          this.$store.commit('email', this.input.email);
          this.$store.commit('password', this.input.password);
          this.$store.commit('type', 'customer');
          this.$router.push('/account'); // redirect to profile page
        })
        .catch(e => {
          this.error = 'customer invalid combination';
        })
    },

    managerLogin: function () {
      AXIOS.put('/manager-login?email=' + this.input.email + '&password=' + this.input.password)
        .then(response => {
          this.$store.commit('email', this.input.email);
          this.$store.commit('password', this.input.password);
          this.$store.commit('type', 'manager');
          this.$router.push('/account'); // redirect to profile page
        })
        .catch(e => {
          this.error = 'manager invalid combination';
        })
    }

  }

}