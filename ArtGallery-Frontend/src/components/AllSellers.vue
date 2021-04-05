<template>
  <div id="allsellers">
    <center>

    <div>
    <h1> All Artists </h1>
    </div>
      <div class="detail">
        <div v-for="seller in sellers" :key="seller.email">
            <div class="info">
            <p>Email: {{seller.email}}</p>
            <p>Name: {{seller.name}}</p>
            <p>Profile: {{seller.profile}}</p>
            
            </div>
        </div>
        </div>
        {{ errorSellers }}


    </center>

    <div></div>
  </div>
</template>

<script src="./scripts/allsellers.js">
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
.detail{
  margin-top: 5%;
}
.info{
  margin-bottom: 5%;
}
</style>