<template>
  <div class="content" style="overflow: hidden">
    <p style="text-align: center; font-size: 20px">자녀 가입 승인 대기중</p>
    <p style="">child_test1님이 가입을 요청했습니다. <br /></p>
    <p style="margin: 50px 0 0; font-size: 18px">가입 요청 시각 :</p>
    <p style="text-align: center; color: #ff6666">2021년 5월 2일 17:43:23</p>
    <p style="margin: 50px 0">자녀분의 요청이 맞을 때만 승인해주세요!</p>
    <button
      @click="$router.push({ name: 'ParentChildDetail' })"
      class="OK_button"
      style="background: #ff6666"
    >
      승인
    </button>
  </div>
</template>

<script>
export default {
  components: {},
  computed: {},
  data() {
    return {
      radioButtons: ["전체 알람 받기", "지정 알람 받기"],
      switch1: "유해 장소 알람 받기",
      switch2: "시간 알람 받기",
      value: "17:00 - 19:00",
      radioGroup: "",
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
    openModal(mission) {
      console.log(mission);
      this.detailedMission = mission;
      this.showModal = true;
    },
  },
};
</script>

<style scoped>
* {
  font-weight: bold;
}
.content {
  height: 100%;
  width: 100%;
  padding: 50px;
}
.v-input {
  width: 100%;
  text-align: left;
}
</style>
