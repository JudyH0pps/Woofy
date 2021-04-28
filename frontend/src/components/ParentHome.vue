<template>
  <div class="parentHome">
    <div class="ParentAccountSummary">
      <p style="text-align: left">{{ user }}님의 계좌</p>
      <p style="text-align: right; font-size: 45px; margin: 15% 0">
        5,000,000 원
      </p>
    </div>
    <div class="ChildAccountSummary" @click="moveTo('ParentChildDetail')">
      <p style="text-align: left">Woori 아들</p>
      <p style="text-align: right; font-size: 25px">40,000 원</p>
    </div>
    <button class="plusButton">+</button>
  </div>
</template>

<script>
export default {
  data() {
    return {
      user: "김시영",
    };
  },
  methods: {
    moveTo(componentName) {
      this.$router.push({ name: componentName });
    },
  },
};
</script>

<style scoped>
.parentHome {
  width: 95%;
  height: 100%;
  margin: 0 auto;
}

.ParentAccountSummary {
  width: 100%;
  height: 25%;
  margin: 10px auto;
  box-shadow: 2px 2px 2px gray;
  background-color: rgb(177, 201, 255);
  display: flex;
  flex-direction: column;
}

.ParentAccountSummary > p {
  margin: 8px;
  color: rgb(255, 255, 255);
  font-size: bold;
}

.ChildAccountSummary {
  width: 100%;
  height: 15%;
  margin: 10px auto;
  box-shadow: 2px 2px 2px gray;
  background-color: rgb(232, 239, 255);
  display: flex;
  flex-direction: column;
}

.ChildAccountSummary > p {
  margin: 8px;
  color: rgb(63, 62, 62);
  font-size: bold;
}

.plusButton {
  width: 100%;
  height: 5%;
  border: 0;
  border-radius: 0 0 10px 10px;
  font-size: 30px;
  color: white;
  background-color: gray;
}
</style>
