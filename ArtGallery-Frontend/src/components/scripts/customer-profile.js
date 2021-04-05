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

import Logout from "@/components/Logout";

export default {
    name: 'customer-Profile',
    components: {
        Logout
    },
    data() {
        return {
            info: '',
            orderSize: '',
            errorProducts: ''
        }
    },
    created: function () {
        AXIOS.get('/customer?email=' + this.$store.state.email + '&password=' + this.$store.state.password)
            .then(response => {
                this.info = response.data
                this.orderSize = response.data.order.length
            })
            .catch(e => {
                this.errorCustomer = e
            })
    },
    methods: {
        logout: function () {
            AXIOS.put('/customer-logout?customer=' + this.info.email).then(response => {
                this.$store.commit('email', '');
                this.$store.commit('password', '');
                this.$router.push('/login');
                this.message = 'successfully log out'
            }).catch(e => {
                this.message = 'fail to logout'
            })


        }
    }

}
//my question is about how to get a single customer to display
//AXIOS.get('/customer?email='.contact(this.$route.params.email))