<template>
  <div class="content" style="overflow: hidden">
    <p style="text-align: center">Woori 아들 알람 설정</p>
    <v-radio-group v-model="radioGroup">
      <v-radio
        v-for="(item, i) in radioButtons"
        :key="i"
        :label="item"
      ></v-radio>
    </v-radio-group>
    <div
      style="
        display: flex;
        flex-direction: column;
        align-items: center;
        padding-left: 10px;
        width: 100%;
      "
    >
      <v-switch v-model="switch1" label="유해 장소 알람 받기"></v-switch>
      <v-switch v-model="switch2" label="시간 알람 받기"></v-switch>
      <div style="margin: 0 auto">
        <v-progress-circular
          :rotate="360"
          :size="200"
          :width="30"
          :value="value"
          color="primary"
          center
        >
          {{ value }}
        </v-progress-circular>
      </div>
    </div>
    <button
      @click="$router.push({ name: 'ParentChildDetail' })"
      class="OK_button"
    >
      설정 완료
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
