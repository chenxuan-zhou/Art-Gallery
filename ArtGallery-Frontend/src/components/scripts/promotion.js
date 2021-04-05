//author:Yutian Fu
import axios from 'axios'
var config = require('../../../config')

var backendConfigurer = function(){
  switch(process.env.NODE_ENV){
    case 'development':
      return 'http://' + config.dev.backendHost + ':' + config.dev.backendPort;
    case 'production':
      return 'https://' + config.build.backendHost + ':' + config.build.backendPort ;
  }
};

var backendUrl = backendConfigurer();

var AXIOS = axios.create({
  baseURL: backendUrl
  // headers: { 'Access-Control-Allow-Origin': frontendUrl }
})

export default {
  name: 'promotion',

  data () {
    return {
      promotions:[],
      startDate:'',
      endDate:'',
      error:'',
      id:'',
      isShow: false,
      isDisabled:true,
      searchPromotion:{
        id:'',
        start:'',
        end:''
      }
    }
  },
methods: {
  toggle(){
    this.isShow = !this.isShow;
  },
  deleteCell:function(index,promotion){

    this.promotions.splice(index, 1);

    this.backendDelete(promotion.id)



  },
  backendDelete(index) {
    AXIOS.delete('/delete-promotion?id=' + index)
      .then(response => {
        // JSON responses are automatically parsed.
        this.promotions = response.data
      })
      .catch(e => {
        this.error = e
      })
  },
  created: function () {
    // Initializing persons from backend
    AXIOS.get('/promotions/')
      .then(response => {
        // JSON responses are automatically parsed.
        this.promotions = response.data
      })
      .catch(e => {
        this.error = e
      })
  },
  filter1(){
    let newPromotions = this.promotions.filter((promotion) => {

      if(promotion.id == this.searchPromotion.id){
        return true;
      }
    });

    this.promotions = newPromotions;
  },
  filter2(){
    let newPromotions = this.promotions.filter((promotion) => {

      if(promotion.startDate == this.searchPromotion.start){
        return true;
      }
    });

    this.promotions = newPromotions;
  },
  filter3(){
    let newPromotions = this.promotions.filter((promotion) => {

      if(promotion.endDate == this.searchPromotion.end){
        return true;
      }
    });

    this.promotions = newPromotions;
  },

  getPromotionS:function (){
    AXIOS.get('/promotion/start_date?date='+this.startDate)
      .then(response => {

       this.promotions=response.data

      })
      .catch(e => {
        this.error = e
      })
  },
  getPromotionE:function (){
    AXIOS.get('/promotion/end_date?date='+this.endDate)
      .then(response => {
        // JSON responses are automatically parsed.
        this.promotions=response.data
      })
      .catch(e => {
        this.error = e
      })

  },
  getPromotionID:function(){
    AXIOS.get('/promotion/id?id='+this.id)
      .then(response => {
        // JSON responses are automatically parsed.
        this.promotions=response.data
      })
      .catch(e => {
        this.error = e
      })
  },

}

}
