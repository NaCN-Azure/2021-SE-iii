import Vue from 'vue'
import Vuex from 'vuex'
import admin from './modules/admin'
import getters from './getters'
import graph from "./modules/graph";
Vue.use(Vuex)

export default new Vuex.Store({
  modules: {
    admin,
    graph,
  },
  state: {
  },
  mutations: {
  },
  actions: {
  },
  getters
})
