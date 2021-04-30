import Vue from 'vue';
import Vuex from 'vuex';
import router from '../router';

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    username: '',
    isParent: null,
  },
  mutations: {
    setParent(state, isParent) {
      state.isParent = isParent;
    }
  },
  actions: {
    moveTo(_, componentName) {
      router.push({name: componentName});
    },
    login({commit}, isParent) {
      commit('setParent', isParent);
    }
  },
  modules: {
  }
})
