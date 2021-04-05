<template>
  <div id="navbar">
    <b-navbar type="dark" variant="dark" id="navbar" toggleable="lg">
      <router-link to="/">
        <b-navbar-brand class="title">
          Art Gallery
        </b-navbar-brand></router-link
      >

      <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>

      <b-collapse id="nav-collapse" is-nav>
        <b-navbar-nav>
          <b-navbar-brand href="/AllProducts"> Products </b-navbar-brand>
          <b-navbar-brand href="/AllSellers"> Artists </b-navbar-brand>
          <b-navbar-brand href="/AllPromotions"> Promotions </b-navbar-brand>

          <!-- test purpose -->

          <!-- test purpose -->
         
          <b-navbar-brand href="/Seller"> Search Artist </b-navbar-brand>
          <b-navbar-brand href="/product"> Search Product </b-navbar-brand>
        </b-navbar-nav>

        <b-navbar-nav class="ml-auto">
          <div class="btn-group" v-if="$store.state.type === ''">
            <b-button type="primary" @click="$router.push('login')">
              Log In
            </b-button>
            <b-button type="primary" @click="$router.push('signup')">
              Sign Up
            </b-button>
          </div>
          <router-link to="/account" v-if="$store.state.type !== ''">
            <b-navbar-brand> Account </b-navbar-brand>
          </router-link>
        </b-navbar-nav>

      </b-collapse>
    </b-navbar>
  </div>
</template>

<style scoped>
@import url("https://fonts.googleapis.com/css2?family=Big+Shoulders+Stencil+Text:wght@700&display=swap");
@import url("https://fonts.googleapis.com/css2?family=Pacifico&display=swap");
.title {
  font-size: 150%;
  font-family: "Pacifico", "Big Shoulders Stencil Text", cursive;
}
</style>
