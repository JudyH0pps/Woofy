<template>
  <div class="content">
    <div class="ParentAccountSummary">
      <div class="ParentAccountSummaryCard">
        <div
          style="border-bottom: 1px solid #00000017; width: 100%; height: 30%"
        >
          <div
            style="
              height: 33%;
              text-align: left;
              font-size: 11px;
              float: left;
              color: #424242;
              font-weight: 800;
              margin: 4.1% 7%;
            "
          >
            <span style="font-size: 13px">{{ user.name }}</span
            >님, <br />오늘도 행복한 하루 보내세요!
          </div>
          <div class="aligncenter" style="float: right; height: 100%">
            <v-icon>mdi-refresh</v-icon> <v-icon>mdi-dots-vertical</v-icon>
          </div>
        </div>

        <div class="aligncenter" style="width: 100%; height: 70%">
          <div>
            <div
              style="
                text-align: center;
                font-size: 13px;
                font-weight: 500;
                color: gray;
              "
            >
              {{ user.account }}
            </div>
            <div class="userMoney">
              <span style="font-size: 34px"> {{ addComma(user.money) }} </span
              >원
            </div>
            <div
              style="
                text-align: center;
                font-size: 12px;
                font-weight: 700;
                color: #717070;
              "
            >
              계좌 관리<v-icon size="16px"
                >mdi-arrow-right-thin-circle-outline</v-icon
              >
            </div>
          </div>
        </div>
      </div>
    </div>
    <!-- <p style=" height: 10%;margin: 3% 4% 0% 4%; font-weight: bold; color: rgb(97 95 95); font-size: larger;height: 3%;
    background-color: white;">자녀 관리</p> -->
    <div class="ChildList">
      <div
        v-for="(child, index) in user.childs"
        :key="index"
        class="ChildAccountSummary"
        @click="moveTo('ParentChildDetail')"
      >
        <div style="width: 100%; height: 100%">
          <div class="monthlymoney">{{ addComma(child.pocketMoney) }}원</div>
          <div
            class="alignCenter"
            style="float: left; font-weight: 600; width: 50%; height: 100%"
          >
            {{ child.name }}
          </div>
          <div class="parentChild">{{ addComma(child.money) }} 원</div>
          <div
            style="
              font-size: 10px;
              bottom: 2px;
              position: absolute;
              right: 1px;
              font-weight: 500;
            "
          >
            {{ rate(child.money, child.pocketMoney) }}%
          </div>
        </div>
        <v-progress-linear
          style="position: absolute; bottom: 0"
          :color="rateColor(child.money / child.pocketMoney)"
          :value="(child.money / child.pocketMoney) * 100"
        ></v-progress-linear>
      </div>
      <div
        class="ChildAccountSummary aligncenter"
        style="color: gray; background-color: #f8fbff"
        @click="$router.push({ name: 'ParentAddChild' })"
      >
        자녀 추가하기<v-icon>mdi-plus-circle-outline</v-icon>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      user: {
        account: "123-4566-713-45",
        name: "김시영",
        money: 5003000,
        childs: [
          {
            name: "Woori 아들",
            money: 20000,
            pocketMoney: 50000,
          },
          {
            name: "Woori 딸",
            money: 45331,
            pocketMoney: 30000,
          },
          {
            name: "승인 대기중",
            money: "0",
            pocketMoney: 0,
          },
        ],
      },
    };
  },
  computed: {},
  mounted() {},
  methods: {
    rate(money, pocketMoney) {
      if (pocketMoney == 0) {
        return 0;
      }
      return Math.round((money / pocketMoney) * 100);
    },
    moveTo(componentName) {
      this.$router.push({ name: componentName });
    },
    converComma(num) {
      var regexp = /\B(?=(\d{3})+(?!\d))/g;
      return num.money.toString().replace(regexp, ",");
    },
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
  },
  created() {},
};
</script>

<style lang="scss" scoped>
@import "@/assets/scss/app/App.scss";

.ParentAccountSummary {
  width: 100%;
  height: 40%;
  /* box-shadow: 2px 2px 2px gray; */
  background-color: $wooriAppColor;
  display: flex;
  align-items: center;
}

.parentChild {
  display: flex;
  align-items: center;
  justify-content: center;
  float: right;
  font-size: 21px;
  height: 100%;
  margin: 0px 16px;
  font-weight: 500;
}

.ChildList {
  width: 100%;
  height: 60%;
  padding: 2%;
  overflow: scroll;
}

.ParentAccountSummary > p {
  margin: 8px;
  color: rgb(255, 255, 255);
  font-size: bold;
}

.ChildAccountSummary {
  width: 100%;
  height: 22%;
  margin: 10px auto;
  box-shadow: 0px 0px 6px rgb(0 0 0 / 26%);
  border-radius: 4px;
  position: relative;
}

.monthlymoney {
  position: absolute;
  right: 16px;
  top: 11px;
  font-size: 12px;
  color: #357dd6;
  font-weight: 600;
}

.ParentAccountSummaryCard {
  width: 90%;
  height: 80%;
  background: white;
  margin: 5%;
  border-radius: 6px;
  box-shadow: 0px 0px 7px #00000047;
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

.userMoney {
  text-align: right;
  font-size: 23px;
  font-weight: 500;
}

.aligncenter {
  display: flex;
  align-items: center;
  justify-content: center;
}
</style>
