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
    name: 'logout',
    data() {
        return {
            variant: '',
            msg: '',
            clicked: false
        }
    },
    methods: {
        logout: function () {
            if (this.$store.state.email) {
                let baseURL = "";
                switch (this.$store.state.type) {
                    case "seller":
                        baseURL = "/logout?email=";
                        break;
                    case "customer":
                        baseURL = "/customer-logout?customer=";
                        break;
                    case "manager":
                        baseURL = "/manager-logout?email=";
                        break;
                }
                AXIOS.put(baseURL + this.$store.state.email)
                    .then(response => {
                        // "logged out"
                        this.$store.state.email = '';
                        this.$store.state.password = '';
                        this.$store.state.type = '';
                        this.variant = "primary";
                        this.msg = "logged out, redirecting to home page...";
                        setTimeout( () => this.$router.push({ path: '/'}), 2000);
                    })
                    .catch(e => {
                        // "logout failed"
                        this.variant = "danger";
                        this.msg = "logout failed";
                    });
            } else {
                // "not logged in"
                this.variant = "warning";
                this.msg = "not logged in, redirecting to login page...";
                setTimeout( () => this.$router.push({ path: '/login'}), 2000);
            }
            this.clicked = true;
        },
    }

}