import Vue from 'vue'
import Vuex from 'vuex'

import createPersistedState from "vuex-persistedstate";

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    email: '',
    password: '',
    type: ''
  },
  mutations: {
    email(state, input_email) {
      state.email = input_email
    }, // store.commit('email', input_email)
    password(state, input_password) {
      state.password = input_password
    },
    type(state, input_type) {
      state.type = input_type
    }
  },
  plugins: [createPersistedState()],
})
