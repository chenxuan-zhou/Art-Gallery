import { data } from 'autoprefixer';
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
    name: 'sellerproductinfo',

    data(){
        return {
            input:{
                productId:'',
                name:'',
                status:'',
                delivery:'',
                price:'',
                orderId:'',
                shipped:'',
            },
            error:'',
            errorOrder:'',
        }

    },
    
    methods:{
        changeName: function(){
            if(this.input.productId!=null && this.input.productId!=''){

                if(this.input.name!=null && this.input.name!=''){
                       //this.error = this.input.name
                      AXIOS.put('/update-product-name?seller=' + this.$store.state.email + '&product-id='+this.input.productId+  
                      '&new-name=' + this.input.name)
                         .then(response => {
                         this.error ='Change Name to: ' + this.input.name
                         })
                         .catch(e => {
                         this.error = 'No prodcut with such Id or Product has been sold or suspended';
                        })
                    
                }else{
                    this.error = 'New name cannot be Null'
                }


            }else{
                this.error = 'Product Id can not be Null'
            }

        },

        changePrice: function(){
            if(this.input.productId!=null && this.input.productId!=''){

                if(this.input.price!=null && this.input.price!=''){
                       //this.error = this.input.name
                      AXIOS.put('/update-product-price?seller=' + this.$store.state.email + '&product-id='+this.input.productId+  
                      '&new-price=' + this.input.price)
                         .then(response => {
                         this.error ='Change Price to: ' + this.input.price
                         })
                         .catch(e => {
                         this.error = 'No prodcut with such Id or Product has been sold or suspended';
                        })
                    
                }else{
                    this.error = 'New price cannot be Null'
                }


            }else{
                this.error = 'Product Id can not be Null'
            }
        },

        changeStatus: function(){
            if(this.input.productId!=null && this.input.productId!=''){

                if(this.input.status!=null && this.input.status!=''){
                       //this.error = this.input.name
                      AXIOS.put('/update-product-status?seller=' + this.$store.state.email + '&product-id='+this.input.productId+  
                      '&new-status=' + this.input.status)
                         .then(response => {
                         this.error ='Change Status to: ' + this.input.status
                         })
                         .catch(e => {
                         this.error = 'No prodcut with such Id or Product has been sold or suspended';
                        })
                    
                }else{
                    this.error = 'New status cannot be Null'
                }


            }else{
                this.error = 'Product Id can not be Null'
            }
        },

        changeDelivery: function(){
            if(this.input.productId!=null && this.input.productId!=''){

                if(this.input.delivery!=null && this.input.delivery!=''){
                       //this.error = this.input.name
                      AXIOS.put('/update-product-delivery?seller=' + this.$store.state.email + '&product-id='+this.input.productId+  
                      '&new-delivery=' + this.input.delivery)
                         .then(response => {
                         this.error ='Change delivery to: ' + this.input.delivery
                         })
                         .catch(e => {
                         this.error = 'No prodcut with such Id or Product has been sold or suspended';
                        })
                    
                }else{
                    this.error = 'New delivery cannot be Null'
                }


            }else{
                this.error = 'Product Id can not be Null'
            }

        },

//http://localhost:8080/delete-product?id=13
        deleteProduct: function(){
            if(this.input.productId!=null && this.input.productId!=''){
                       //this.error = this.input.name
                 AXIOS.delete('/delete-product?id='+this.input.productId)
                    .then(response => {
                     this.error ='Delete product with Id: ' + this.input.productId
                     })
                     .catch(e => {
                     this.error = 'No product with such Id or it is in an order or in promotions';
                     })
            }else{
                this.error = 'Product Id can not be Null'
            }
        },
        shipping: function(){
            if(this.input.orderId!=null && this.input.orderId!=''){

                AXIOS.put('/shipping-date?id=' + this.input.orderId + '&shipped='+this.input.shipped)
                         .then(response => {
                         this.errorOrder ='Update shipping date successfully' 
                         })
                         .catch(e => {
                         this.errorOrder = 'No order with such Id or incorrect date';
                        })

            }else{
            this.errorOrder = 'Order Id can not be Null'
            }


        }
    }

}