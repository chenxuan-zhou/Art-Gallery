<!-- author:Yutian Fu-->
<template>
  <div>
    <center>
      <div>
        <h1>All Promotions</h1>
      </div>
      <td>
        <a href="#" @click.prevent="created">ClickToBrowsePromotions</a>
      </td>

    </center>


  <div id="search">
    <form v-show="isShow">
      <input type="text" placeholder="Enter promotionID" v-model="searchPromotion.id">
      <input type="submit" value="Search" @click.prevent="filter1()">
      <input type="text" placeholder="Enter startDate" v-model="searchPromotion.start">
      <input type="submit" value="Search" @click.prevent="filter2()">
      <input type="text" placeholder="Enter endDate" v-model="searchPromotion.end">
      <input type="submit" value="Search" @click.prevent="filter3()">
    </form>
    <table>
      <tr>
        <th>PromotionID</th>
        <th>ProductID</th>
        <th>Start Date</th>
        <th>End Date</th>
        <th>Created By</th>
        <th>Actions</th>
      </tr>


      <tr v-for="(promotion,index) in promotions" :key="promotion.id" v-on:click="onclick($index)">
        <td><input type="text" v-model="promotion.id" :disabled="isDisabled" ></td>
        <td><input type="text" v-model="promotion.productDto" :disabled="isDisabled"></td>
        <td><input type="text" v-model="promotion.startDate" :disabled="isDisabled"></td>
        <td><input type="text" v-model="promotion.endDate" :disabled="isDisabled"></td>
        <td><input type="text" v-model="promotion.manager.email" :disabled="isDisabled"></td>



        <td>

          <a href="#" @click.prevent="toggle()">Search</a>
          <br/>

          <a href="#" @click.prevent="deleteCell(index,promotion)">Delete</a>



        </td>



      </tr>

    </table>
  </div>
  </div>
</template>

<script src="./scripts/promotion.js">
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

h1,
h2 {
  font-weight: normal;
}

ul {
  list-style-type: none;
  padding: 0;
}

li {
  display: inline-block;
  margin: 0 10px;
}

a {
  color: #42b983;
}
table{
  width: 100%;
  background: #000;

}
table tr{
  background: #fff;
  border: 1px darkgrey;
  width: 100px;
}
#search{
  width: 800px;
  margin: 60px auto;
  background: #42b983;
}
form{
  width: 100%;
  display: flex;
  border-style: ridge;
  justify-content: space-between;
}

</style>

