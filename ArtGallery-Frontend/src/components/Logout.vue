<template>
  <div id="logout" >
    <button class="btn btn-primary" v-on:click="logout()" data-dismiss="alert">
      Log Out
    </button>
    <b-alert show v-if="clicked" v-bind:variant="variant">
      {{ msg }}
    </b-alert>
  </div>
</template>

<script src="./scripts/logout.js"></script>