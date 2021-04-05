<template>
  <div id="app">
    <Navbar />
    <router-view></router-view>
  </div>
</template>

<script>
import axios from "axios";
var config = require("../config");

var backendConfigurer = function () {
  switch (process.env.NODE_ENV) {
    case "development":
      return "http://" + config.dev.backendHost + ":" + config.dev.backendPort;
    case "production":
      return (
        "https://" + config.build.backendHost + ":" + config.build.backendPort
      );
  }
};

var backendUrl = backendConfigurer();

var AXIOS = axios.create({
  baseURL: backendUrl,
  // headers: { 'Access-Control-Allow-Origin': frontendUrl }
});

import Navbar from "@/components/Navbar";
export default {
  name: "app",
  components: {
    Navbar,
  },
  mounted: function () {
    this.$store.state.email = "";
    this.$store.state.password = "";
    this.$store.state.type = "";
    document.addEventListener("beforeunload", this.handler); // doesn't work
  },
  method: {
    handler: function () {
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
          .then((response) => {
            this.$store.state.email = "";
            this.$store.state.password = "";
            this.$store.state.type = "";
          })
          .catch((e) => {});
      }
    },
  },
};
</script>

<style>
</style>
