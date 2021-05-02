import Vue from "vue";
import Vuex from "vuex";
import router from "../router";
import axios from "axios";

const SERVER_URL =
  "http://ec2-13-230-29-11.ap-northeast-1.compute.amazonaws.com/api/v1/";

Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    username: "김시영",
    isParent: null,
    waitingMissions: [],
    childMoney: 14000,
    childPayment: [
      {
        location: "OOpc방",
        price: 5000,
        time: "2021-03-21 16:03:54",
      },
      {
        location: "김밥천국",
        price: 4500,
        time: "2021-03-21 18:44:07",
      },
      {
        location: "OOpc방",
        price: 5000,
        time: "2021-03-21 16:03:54",
      },
      {
        location: "김밥천국",
        price: 4500,
        time: "2021-03-21 18:44:07",
      },
      {
        location: "OOpc방",
        price: 5000,
        time: "2021-03-21 16:03:54",
      },
      {
        location: "김밥천국",
        price: 4500,
        time: "2021-03-21 18:44:07",
      },
      {
        location: "김밥천국",
        price: 4500,
        time: "2021-03-21 18:44:07",
      },
    ],
  },
  mutations: {
    setParent(state, isParent) {
      state.isParent = isParent;
    },
    setWaitingMissions(state, value) {
      state.waitingMissions = value;
    },
    setChildMoney(state, money) {
      state.childMoney = money;
    },
    addChildPayment(state) {
      let origin = state.childPayment;
      let newOne = [
        {
          location: "우리은행 송금",
          price: 4000,
          time: "",
        },
      ];
      state.childPayment = newOne.concat(origin);
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
            router.push({ name: "ChildHome" });
          }
        });
    },
    phoneAuth(_, phoneNumber) {
      const body = {
        type: "1", // "1": 부모, "2": 자녀. 여기에서는 반드시 구분할 필요 없음
        dataBody: {
          COMC_DIS: "1", // 통신사 번호. 해커톤에서는 미사용해도 무방
          HP_NO: phoneNumber, // 휴대폰 번호
          HP_CRTF_AGR_YN: "Y", // 동의 여부. Y, N 중의 하나를 넣어도 그냥 동작함.
          FNM: "김시영", // 휴대폰 번호 이름
          RRNO_BFNB: "900101", // 주민번호 앞자리
          ENCY_RRNO_LSNM: "1234567", // 주민번호 뒷자리. 원래는 프론트에서 INCA 기반의 키보드 보안으로 동작되어야 하나, 그냥 보내도 됨.
        },
      };
      axios.post(SERVER_URL + "auth/getCellCerti", body);
    },
    phoneCerti(_, { phoneNumber, certiNum }) {
      const body = {
        type: "1", // "1": 부모, "2": 자녀. 여기에서는 반드시 구분 필요
        dataBody: {
          RRNO_BFNB: "901121", // 주민번호 앞자리
          ENCY_RRNO_LSNM: "1234567", // 주민번호 뒷자리
          HP_NO: phoneNumber, // 휴대폰 번호
          ENCY_SMS_CRTF_NO: certiNum, // SMS 통해 발송된 6자리 숫자.
        },
      };
      axios
        .post(SERVER_URL + "auth/executeCellCerti", body)
        .then((data) => {
          console.log(data);
        })
        .catch((error) => console.log(error.response.data));
      router.push({ name: "ParentSignupDetail" });
    },
  },
  modules: {},
});
