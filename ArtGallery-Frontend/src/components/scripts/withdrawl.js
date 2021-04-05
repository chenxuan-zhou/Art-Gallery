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
    name: 'sellerbuttons',

    data() {
        return {
            input: {
                accountNo:'',
                transit:'',
                institution:'',
                name: '',
                price: '',
                status: '',
                delivery: '',
                profile:'',
                password:'',
                picture: ''
            },
            error: ''
        }
    },

    methods: {
        changeName: function(){

        },
        changeProfile: function(){
            
        },
        changePassword: function(){
            
        },
        
        withdrawl: function () {
            if (this.input.accountNo !=null && this.input.accountNo!=''&&this.input.transit!=null && this.input.transit!=''
                && this.input.institution!=null &&this.input.institution!='') {
                AXIOS.put('withdrawl-seller?seller=' + this.$store.state.email)
                    .then(response => {
                        this.error = "Withdrawl money successfully"//"created product " + tmp + " for seller " + this.$store.state.email;
                    })
                    .catch(e => {
                        this.error = 'Cannot withdrawl money';
                    })
            } else {
                this.error = 'Incorrect bank information'+this.input.accountNo+'///'
            }

        },

    }

}