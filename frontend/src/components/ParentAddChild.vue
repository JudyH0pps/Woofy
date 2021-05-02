<template>
  <div class="content" style="overflow: hidden"></div>
</template>

<script>
export default {
  components: {},
  computed: {},
  mounted() {},
  data() {
    return {
      showModal: false,
      child: {
        name: "Woori 아들",
        money: 20000,
        pocketMoney: 50000,
        missions: [
          {
            title: "설거지하기",
            content: "미션 설명",
            prize: 500,
            missionStatus: "SUCCESS",
            missionType: 1,
          },
          {
            title: "미션 2",
            content: "미션 설명",
            prize: 5000,
            missionStatus: "REQUEST",
            missionType: 0,
          },
          {
            title: "미션 3",
            content: "미션 설명",
            prize: 3000,
            missionStatus: "ONGOING",
            missionType: 0,
          },
          {
            title: "미션 2",
            content: "미션 설명",
            prize: 5000,
            missionStatus: "REQUEST",
            missionType: 0,
          },
          {
            title: "미션 3",
            content: "미션 설명",
            prize: 3000,
            missionStatus: "ONGOING",
            missionType: 0,
          },
          {
            title: "미션 2",
            content: "미션 설명",
            prize: 5000,
            missionStatus: "SUCCESS",
            missionType: 0,
          },
          {
            title: "미션 3",
            content: "미션 설명",
            prize: 3000,
            missionStatus: "ONGOING",
            missionType: 0,
          },
        ],
        payment: [
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
      dailyMission: [],
      generalMission: [],
      waitingMission: [],
      tab: null,
      tabs: ["결제 내역", "미션 관리"],
    };
  },
  methods: {
    addComma(num) {
      var regexp = /\B(?=(\d{3})+(?!\d))/g;
      return num.toString().replace(regexp, ",");
    },
    rateColor(num) {
      if (num < 0.3) {
        return "red";
      } else if (num < 0.6) {
        return "orange";
      } else {
        return "green";
      }
    },
    closeModal() {
      this.showModal = false;
    },
    openModal() {
      this.showModal = true;
    },
  },
};
</script>

<style scoped>
.parentChildDetail {
  position: absolute;
  bottom: 0px;
  width: 100%;
  height: 60%;
  display: flex;
  flex-direction: column;
  justify-content: flex-end;
}
.ParantChildInform {
  padding-top: 12px;
  height: 25%;
  width: 100%;
  position: relative;
  /* box-shadow: 0px 0px 4px #00000036; */
}

.ParentChildPayment {
  height: 14%;
  width: 100%;
  border-bottom: 1px rgb(95, 95, 95);
  margin-bottom: 12px;
  box-shadow: 0px 1px 0px #0000001f;
  padding: 10px;
}

.ParentChildMission {
  height: 10vh;
  width: 90%;
  box-shadow: 0px 0px 4px #00000030;
  margin: 10px 5%;
  border-radius: 4px;
}

.modal {
  transform: scale(
    0.8
  ); /* 0.5 초 동안 애니메이션을 실행, 단 0.8초 지연시켜 0.8초 후에 애니메이션을 실행 */
  animation: zoomIn 0.4s forwards;
  /* animation: zoomOut 0.5s cubic-bezier(0.165, 0.84, 0.44, 1) forwards; */
}
</style>
