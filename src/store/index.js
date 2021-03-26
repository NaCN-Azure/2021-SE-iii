import Vue from 'vue'
import Vuex from 'vuex'
import admin from './modules/admin'
import getters from './getters'
import editor from "./modules/editor";
Vue.use(Vuex)

export default new Vuex.Store({
  modules: {
    admin,
    editor,
  },
  state: {
  },
  mutations: {
  },
  actions: {
  },
  getters
})
