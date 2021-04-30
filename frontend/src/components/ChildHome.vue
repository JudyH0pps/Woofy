<template>
  <section>
    <div class="aligncenter" style="width: 100%; height: 70%">
      <div>
        <div
          style="
            text-align: center;
            font-size: 17px;
            font-weight: 600;
            color: gray;
            margin-top: 20px;
          "
        >
          {{ user }} 님의 잔액
        </div>
        <div class="userMoney">
          <span style="font-size: 39px"> {{ money }} </span>원
        </div>
      </div>
    </div>
    <div class="buttons">
      <v-btn @click="changeViewingContent(0)" plain>결제</v-btn>
      <v-btn @click="moveTo('ChildRemittance')" plain>송금</v-btn>
      <v-btn @click="changeViewingContent(2)" plain>미션</v-btn>
    </div>
    <ChildPayment v-if="viweingContent === 0"></ChildPayment>
    <!-- <ChildRemittance v-if="viweingContent === 1"></ChildRemittance> -->
    <ChildMission v-if="viweingContent === 2"></ChildMission>
  </section>
</template>

<script>
import ChildPayment from "@/components/ChildPayment.vue";
// import ChildRemittance from "@/components/ChildRemittance.vue";
import ChildMission from "@/components/ChildMission.vue";
export default {
  data() {
    return {
      user: "Woori아들",
      money: 4800,
      viweingContent: 0,
      payments: [],
    };
  },
  components: {
    ChildPayment,
    // ChildRemittance,
    ChildMission,
  },
  methods: {
    moveTo(component) {
      this.$router.push({ name: component });
    },
    changeViewingContent(no) {
      this.viweingContent = no;
    },
  },
};
</script>

<style lang="scss" scoped>
.barcode {
  display: flex;
  flex-direction: column;
  width: 100%;
  align-items: center;
  justify-content: center;
  margin: 20px auto;
}

.userMoney {
  text-align: right;
}
.buttons {
  display: flex;
  justify-content: space-around;
}
</style>
