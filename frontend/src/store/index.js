import Vue from 'vue';
import Vuex from 'vuex';
import router from '../router';

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    username: '',
    isParent: null,
    waitingMissions:[]
  },
  mutations: {
    setParent(state, isParent) {
      state.isParent = isParent;
    },
    setWaitingMissions(state, value) {
      state.waitingMissions = value;
    },
  },
  actions: {
    moveTo(_, componentName) {
      router.push({name: componentName});
    },
    login({commit}, isParent) {
      commit('setParent', isParent);
    },
  },
  modules: {
  }
})
