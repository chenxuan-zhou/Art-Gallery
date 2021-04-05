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
    name: 'Order',

    data() {
        return {
            address: '',
            productID: '',
            price: '',
            customerEmail: '',
            sellerEmail: '',
            ordered: '',
            shipped: '',
            dm:'',
            isMail: true,
            isPickup: true,

            isChecked: false,
            error:'',
            order: '',
            orderMsg: '',
            productStatus:'',

            card:'',
            cvv:'',
        }
    },
    created: function () {
        // initialize ordered date
        var today = new Date();
        var dd = String(today.getDate()).padStart(2, '0');
        var mm = String(today.getMonth() + 1).padStart(2, '0'); //January is 0!
        var yyyy = today.getFullYear();
        today = yyyy + '-' + mm + '-' + dd;
        this.ordered = today
        this.shipped = today
        this.orderMsg = today
        this.customerEmail= this.$store.state.email
    },

    methods: {
       
        makeOrder: function () {
            if(this.isChecked===false){
                this.orderMsg='Check product availability first'
                return
            }
            
            if(this.dm===null || this.dm===''){
                this.orderMsg='Choose delivery method'
                return
            }

            if(this.card!=null && this.card != '' && this.cvv.length===3){
                AXIOS.post('/create-order?dm=' + this.dm + '&address=' + this.address + '&ordered=' + this.ordered
            + '&shipped=' + this.shipped+ '&price=' + this.price + '&product=' + this.productID + 
            '&seller=' + this.sellerEmail + '&customer=' + this.customerEmail) 
                .then(response => {
                    this.order = response.data
                    this.orderMsg = 'Order has been successfully created'
                    //this.$router.push('/login')
                })
                .catch(e => {
                    this.orderMsg= 'Incorrect product Id or Product is not in Selling or invalide address'
                })
            }else{
                this.orderMsg = 'Invalide payment information!'
            }

        },
        findProduct: function() {
            AXIOS.get('/product?id=' + this.productID)
            .then(response => {
              // JSON responses are automatically parsed.
              this.price = response.data.price
              this.sellerEmail = response.data.seller
              this.productStatus = response.data.productStatus
              this.error = 'Find the product: ' + response.data.name + ' created by ' + this.sellerEmail + ', price=' + this.price 
              if(this.productStatus!='Selling'){
                this.error = 'This product is not in Selling'
                this.isPickup = false
                this.isMail = false
                return;
              }

              this.isChecked=true
              if(response.data.supportedDelivery === 'Both'){
                this.isMail = true
                this.isPickup=true
              }
              if (response.data.supportedDelivery === 'Mail'){
                this.isPickup = false
                this.isMail = true
              }else if(response.data.supportedDelivery === 'Pickup'){
                this.isMail = false
                this.isPickup=true
              }

            })
            .catch(e => {
              this.error = "Id cannot be empty Or no product with this id"
            })
        }

    }


} 