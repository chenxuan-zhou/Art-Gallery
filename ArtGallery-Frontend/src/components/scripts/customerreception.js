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
	name: 'customer-Reception',
	data() {
		return {
			input: {
				orderId: '',
			},
			errorOrder: '',
		}
	},
	methods: {
		received: function () {
			if (this.input.orderId == null || this.input.orderId == "") {
				this.errorOrder = 'orderId cannot be empty'
			} else {
				AXIOS.put('/customer-receive?orderID=' + this.input.orderId + "&customer=" + this.$store.state.email).then(response => {
					this.errorOrder = 'Order ' + this.input.orderId + ' marked as received!'
				}).catch(e => {
					this.errorOrder = 'you don\'t have order with Id: ' + this.input.orderId
				})
			}


		}
	}

}