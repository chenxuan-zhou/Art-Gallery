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

export default{
    name: 'seller-profile',
    components: {
      Logout
    },
    data(){
        return {
            info:'',
            errorProducts: '',
            productsize:'',
            orderSize:''
        }  
    },
    created: function(){
        //Initializing seller from backend
        AXIOS.get('/seller?email=' + this.$store.state.email + '&password=' + this.$store.state.password )
        .then(response => {
          //JSON responses are automatically parsed.
         this.info = response.data
         this.productsize=response.data.products.length
         this.orderSize=response.data.orders.length
         })
         .catch(e => {
          this.errorSellers = e
        })
     },
     methods: {
        logout: function(){
            AXIOS.put('/logout?email=' + this.info.email).then(response => {
                this.$store.commit('email', '');
                this.$store.commit('password', '');
                this.$router.push('/login');
                this.message = 'successfully log out';
              }).catch(e => {
                this.message = 'fail to logout'
              })


        }
     }

}
