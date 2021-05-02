<template>
  <section class="childMission">
    <div class="mission">
      <div style="width: 85%">
        <p>설거지 하기</p>
        <p class="amount">+500</p>
      </div>
      <v-btn
        @click="$router.push({ name: 'ChildMissionComplete' })"
        color="primary"
        fab
        small
        >></v-btn
      >
    </div>
    <div class="mission">
      <div style="width: 85%">
        <p>시험 100점</p>
        <p class="amount">+50000</p>
      </div>
      <v-btn
        @click="$router.push({ name: 'ChildMissionComplete' })"
        color="primary"
        fab
        small
        >></v-btn
      >
    </div>
    <div
      @click="moveTo('ChildMissionRequest')"
      class="ChildAccountSummary aligncenter"
      style="color: gray"
    >
      미션 추가 요청<v-icon>mdi-plus-circle-outline</v-icon>
    </div>
  </section>
</template>

<script>
import { mapActions } from "vuex";

export default {
  data() {
    return {};
  },
  components: {},
  methods: {
    ...mapActions(["moveTo"]),
  },
};
</script>

<style lang="scss" scoped>
.childMission {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
}
.mission {
  display: flex;
  flex-direction: row;
  align-items: center;
  width: 95%;
  border-radius: 5px;
  border: 3px solid #eee;
  margin-bottom: 5px;
  p {
    margin: 10px;
  }
  .amount {
    font-size: 24px;
    color: rgb(96, 165, 255);
    text-align: right;
    margin-right: 30px;
  }
}
</style>
