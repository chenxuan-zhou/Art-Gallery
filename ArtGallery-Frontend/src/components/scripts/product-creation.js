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
                name: '',
                price: '',
                status: '',
                delivery: '',
                picture: ''
            },
            error: ''
        }
    },

    methods: {
        login: function () {
            AXIOS.put('/login?email=' + this.$store.state.email + '&password=' + this.$store.state.password)
              .then(response => {
                // this.$store.commit('email', this.input.email);
                // this.$store.commit('password', this.input.password);
                // this.$store.commit('type', 'seller');
                this.$router.push('/SellerProfile'); // redirect to profile page
                // external redirection :
                // window.location.href = 'https://www.google.com';
              })
              .catch(e => {
                this.error = 'seller invalid combination';
              })
          },
        createProduct: function () {
            if (this.$store.state.email) {
                AXIOS.post('/create-product?status=' + this.input.status +
                    '&delivery=' + this.input.delivery + '&name=' + this.input.name +
                    '&seller=' + this.$store.state.email + '&price=' + this.input.price +
                    '&picture=' + this.input.picture)
                    .then(response => {
                        let tmp = this.input.name;
                        this.error = "created product " + tmp + " for seller " + this.$store.state.email;
                        this.input.name = null;
                        this.input.price = null;
                        this.input.status = null;
                        this.input.delivery = null;
                        this.input.picture = null;
                    })
                    .catch(e => {
                        this.error = 'cannot create this product';
                    })
            } else {
                this.error = 'log in to your seller account first'
            }

        },

    }

}