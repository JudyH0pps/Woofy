<template>
  <div class="content">
    <div class="ParentAccountSummary">
      <div class="ParentAccountSummaryCard">
        
        <div style="border-bottom: 1px solid #00000017; width:100%; height:30%;">
          <div style="text-align: left; font-size: 80%; float: left; color: #424242; font-weight: 800; margin: 4.1% 7%;">
            <span style="font-size:15px">{{ user.name }}</span>님, <br>오늘도 행복한 하루 보내세요!
          </div>
          <div class="aligncenter" style="float:right; height:100%;"><v-icon>mdi-refresh</v-icon> <v-icon>mdi-dots-vertical</v-icon> </div>
        </div>
        
        <div class="aligncenter" style="width:100%; height:70%;">
          <div>
              <div style="text-align: center; font-size: 17px; font-weight: 600; color: gray;">{{user.account}}</div>
              <div class="userMoney">
              <span style="font-size: 39px;"> {{userMoney}} </span>원</div>
              <div style="text-align: center; font-size: small; font-weight: 700; color: #717070;">
                계좌 관리 <v-icon>mdi-arrow-right-thin-circle-outline</v-icon></div>
          </div>
        </div>
      
      </div>
    </div>
      <p style=" height: 10%;margin: 3% 4% 0% 4%; font-weight: bold; color: rgb(97 95 95); font-size: larger;height: 3%;
    background-color: white;">자녀 관리</p>
    <div class="ChildList">
    <div v-for="(child,index) in user.childs" :key="index" class="ChildAccountSummary" @click="moveTo('ParentChildDetail')">
        <div style="width:100%; height:70%;">
            <div style="float: left">{{child.name}}</div>
            <div style="float: right; font-size: 25px">{{child.money}} 원</div>
        </div>
        <div>
            <div style="float:right; font-size:12px">{{child.rate}}%</div>
            <v-progress-linear :value="child.rate"></v-progress-linear>
        </div>
    </div>
    <div class="ChildAccountSummary aligncenter" style="color: gray;">자녀 추가하기<v-icon>mdi-plus-circle-outline</v-icon></div>
    
  </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      user:{
        account: '123-4566-713-45',
        name: "김시영",
        money:5003000,
        childs:[
          {
            name : 'Woori 아들',
            money : 40000,
            rate: 60

          },
           {
            name : 'Woori 딸',
            money : 45000,
            rate: 30

          },
          {
            name : 'Woori 막내',
            money : 7000,
            rate: 80

          }
        ]
      },
    };
  },
  computed:{
    userMoney(){
      var regexp = /\B(?=(\d{3})+(?!\d))/g;
      return this.user.money.toString().replace(regexp,',');
    },
  },
  mounted(){
  },
  methods: {
    moveTo(componentName) {
      this.$router.push({ name: componentName });
    },
    converComma(num){
       var regexp = /\B(?=(\d{3})+(?!\d))/g;
      return num.money.toString().replace(regexp,',');
    }
  },
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
  flex-direction: column;
}

.ChildList{
    width: 100%;
    height: 50%;
    padding: 4%;
    overflow: scroll;
}

.ParentAccountSummary > p {
  margin: 8px;
  color: rgb(255, 255, 255);
  font-size: bold;
}

.ChildAccountSummary {
  width: 100%;
    height: 25%;
    margin: 10px auto;
    box-shadow: 0px 0px 6px rgb(0 0 0 / 26%);
    border-radius: 4px;
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

.userMoney{
  text-align: right;
  font-size: 23px;
  font-weight: 500;
}

.aligncenter{
    display: flex;
    align-items: center;
    justify-content: center;
}
</style>
