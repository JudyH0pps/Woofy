import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    username: '',
    isParent: null,
  },
  mutations: {
    setParent(isParent) {
      this.isParent = isParent;
    }
  },
  actions: {
    login({commit}, isParent) {
      commit('setParent', isParent);
    }
  },
  modules: {
  }
})
