import Vue from 'vue'
import Vuex from 'vuex'
import getters from './getters'
import editor from "./modules/editor";
Vue.use(Vuex)

export default new Vuex.Store({
  modules: {
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
