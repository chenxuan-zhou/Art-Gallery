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
    name: 'customerinfo',

    data() {
        return {
            input: {
                name: '',
                password: '',
                confirmPassword: ''
            },
            error: ''
        }
    },

    methods: {
        changeName: function () {
            if (this.input.name != null && this.input.name != '') {
                //this.error = this.input.name
                AXIOS.put('/change-customer-name?customer=' + this.$store.state.email + '&new_name=' + this.input.name)
                    .then(response => {
                        this.error = 'Change Name to: ' + this.input.name
                    })
                    .catch(e => {
                        this.error = 'Failed to change Name. Try Log in again';
                    })
            } else {
                this.error = 'New name cannot be Null!'
            }

        },
        changePassword: function () {
            if (this.input.password == null || this.input.password == '') {
                //this.error = this.input.name
                this.error = 'New password cannot be Null!'
            } else if (this.input.confirmPassword.trim() !== this.input.password.trim()) {
                this.error = 'Password does not match!'
            }
            else {
                AXIOS.put('/change-customer-password?customer=' + this.$store.state.email + '&newPassword=' + this.input.password + '&confirmPassword=' + this.input.confirmPassword)
                    .then(response => {
                        this.error = 'Change Password to: ' + this.input.password
                    })
                    .catch(e => {
                        this.error = 'Failed to change Password. Try Log in again';
                    })
            }
        },


    }

}