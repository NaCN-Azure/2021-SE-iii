import Vue from 'vue'
import Vuex from 'vuex'
import getters from './getters'
import editor from "./modules/editor";
import user from "./modules/user";
Vue.use(Vuex)

export default new Vuex.Store({
  modules: {
    editor,
    user,
  },
  state: {
  },
  mutations: {
  },
  actions: {
  },
  getters
})
