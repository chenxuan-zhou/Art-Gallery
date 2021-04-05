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
    name: 'sellerinfo',

    data() {
        return {
            input: {
                name: '',
                profile:'',
                password:'',
            },
            error: ''
        }
    },

    methods: {
        changeName: function(){
            if(this.input.name!=null && this.input.name!=''){
                //this.error = this.input.name
            AXIOS.put('/change-seller-name?seller=' + this.$store.state.email + '&new_name=' + this.input.name)
              .then(response => {
                this.error ='Change Name to: ' + this.input.name
              })
              .catch(e => {
                this.error = 'Failed to change Name. Try Log in again';
              })
            }else{
            this.error = 'New name cannot be Null!'
            }

        },
        changeProfile: function(){
            if(this.input.profile!=null && this.input.profile!=''){
                    //this.error = this.input.name
                AXIOS.put('/change-seller-profile?seller=' + this.$store.state.email + '&new_profile=' + this.input.profile)
                .then(response => {
                  this.error ='Change Profile to: ' + this.input.profile
               })
                .catch(e => {
                this.error = 'Failed to change Profile. Try Log in again';
             })
             }else{
             this.error = 'New profile cannot be Null!'
            }
        },
        changePassword: function(){
            if(this.input.password!=null && this.input.password!=''){
                     //this.error = this.input.name
                     AXIOS.put('/change-seller-password?seller=' + this.$store.state.email + '&new_password=' + this.input.password)
                     .then(response => {
                       this.error ='Change Password to: ' + this.input.password
                    })
                     .catch(e => {
                     this.error = 'Failed to change Password. Try Log in again';
                  })

            }else{
            this.error = 'New password cannot be Null!'
            }
        },
       
    
    }

}