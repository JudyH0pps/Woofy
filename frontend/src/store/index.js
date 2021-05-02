import Vue from "vue";
import Vuex from "vuex";
import router from "../router";
import axios from "axios";

Vue.use(Vuex);

const SERVER_URL =
  "http://ec2-13-230-29-11.ap-northeast-1.compute.amazonaws.com/api/v1/";

export default new Vuex.Store({
  state: {
    username: "",
    isParent: null,
    waitingMissions: [],
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
      router.push({ name: componentName });
    },
    login({ commit }, requestBody) {
      axios
        .post(SERVER_URL + "signin", { dataBody: requestBody })
        .then(({ data }) => {
          if (data.dataBody.role === "ROLE_PARENT") {
            commit("setParent", true);
            router.push({ name: "ParentHome" });
          } else if (data.dataBody.role === "ROLE_CHILD") {
            commit("setParent", false);
            router.push({ name: "childHome" });
          }
        });
    },
  },
  modules: {},
});
