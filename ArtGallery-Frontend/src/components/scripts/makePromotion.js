//author:Yutian Fu
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
      startDate:'',
      endDate:'',
      productID:'',
      managerEmail:'',
      promotionID:'',
      promotionMsg: "Please Try again",
      promotions:[]
    }
  },


  methods: {
    login: function () {

      this.$router.push('/login')
    },
//http://localhost:8080/promotion/create?manager=1234567@mail&id=1&start=2020-09-01&end=2020-10-01&product=1
    makePromotion: function () {
      AXIOS.post('/promotion/create?=' + this.managerEmail + '&id=' + this.promotionID + '&start=' + this.startDate
        + '&end=' + this.endDate+ '&product=' + this.productID)
        .then(response => {
          this.promotions=response.data
          this.promotionMsg = "A promotion has been created"


        })
        .catch(e => {
          this.orderMsg= e
        })
    }

  }


}
