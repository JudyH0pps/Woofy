<template>
  <div class="content">
    <v-bottom-sheet v-model="sheet">
      <v-sheet
        class="text-center"
        style="border-radius:20px 20px 0px 0px; background-color:#4285f4;"
        height="80vh"
      >
      <div style="height: 20%;
    position: absolute;
    width: 100%;
    padding: 18%;">
      <div style="    font-size: x-large;
    font-weight: 600;
    color: white;">WOORI i</div>
      <div style="font-weight: 900;
    font-size: x-large;
    color: white;">결제 하기</div>
      </div>
    <div class="alignCenter content" style="padding:5%;">
      <div class="alignCenter" style="width:98%; height:32%; background-color:white;">
      <VueBarcode
        :value="barcodeValue"
        height="90"
        width="2"
        displayValue="false"
        lineColor="black"
      >
        Show this if the rendering fails.
      </VueBarcode>

      </div>
        </div>
      </v-sheet>
    </v-bottom-sheet>
    <div v-if="showModal" class="modalBackground">
      <div style="margin:5% 4%; width:90%; height:10%; "><v-icon @click="closeModal" style="float:right; z-index:6;">mdi-close</v-icon></div>
      <MissionDetail class="modal" :user="'child'" :mission="detailedMission" @close="closeModal"></MissionDetail>
    </div>
      <div class="ParentAccountSummary">
      <div class="ParentAccountSummaryCard">
        <div
          style="border-bottom: 1px solid #00000017; width: 100%; height: 40%"
        >
          <div
            style="
              height: 40%;
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

        <div class="aligncenter" style="width: 100%; height: 60%">
          <div>
            <div class="userMoney">
              <span style="font-size: 34px"> {{ addComma(user.money) }} </span
              >원
            </div>
            
          </div>
          
        </div>
      </div>
      <div>
      <v-btn @click="sheet = !sheet" class="paymentBtn" dark color="white">
            <v-icon style="position:absolute; left:3px;" size="25px"
                >mdi-barcode-scan</v-icon >
              결제 하기
            </v-btn>
    </div>
    </div>
    <div style="width:100%; height:58vh; overflow:hidden;">
    <v-tabs v-model="tab" background-color="#4285f4" dark color="white" grow>
        <v-tab style="font-size:15px;"
          v-for="tab in tabs"
          :key="tab" >
          {{ tab }}
        </v-tab>
        <v-tabs-slider color="rgb(141 200 239)"></v-tabs-slider>
      </v-tabs>

      <v-tabs-items style="height:100%; width:100%;padding-bottom:10%; overflow:hidden;" v-model="tab">
        <v-tab-item style="height:100%; width:100%; overflow:scroll;">
        <ChildPayment></ChildPayment>
        </v-tab-item>

        <v-tab-item style="height:100%; width:100%; overflow:scroll; padding-bottom:10%;">
    <ChildBankSelection ></ChildBankSelection>
        </v-tab-item>
    <!-- <ChildMission ></ChildMission> -->

        <v-tab-item style="height:100%; width:100%; overflow:scroll; padding-bottom:10%;">
        <div>매일 미션
              <Mission class="ParentChildMission" v-for="(mission,index) in dailyMission" :arrow="'right'" :key="index" @missionClick="openModal(mission)" :mission="mission"></Mission>
          </div>
            등록된 미션
             <!-- <v-badge  color="green" :content="1" style="width:90%; margin:0 5%; padding-top:2px; text-align:center" >
              <Mission style="width:100%; margin:0; background: #caf5c975;" class="ParentChildMission" :arrow="'right'" :mission="generalMission[0]"></Mission>         
            </v-badge> -->
          <Mission class="ParentChildMission" v-for="(mission,index) in generalMission" :arrow="'right'" :key="index" @missionClick="openModal(mission)" :mission="mission"></Mission>
          <div>


          </div>
        </v-tab-item>
      </v-tabs-items>
    </div>
  </div>
</template>

<script>
import ChildPayment from "@/components/ChildPayment.vue";
// import ChildRemittance from "@/components/ChildRemittance.vue";
// import ChildMission from "@/components/ChildMission.vue";
import ChildBankSelection from "./ChildBankSelection.vue";
import VueBarcode from "vue-barcode";
import Mission from './Mission.vue';
import MissionDetail from './MissionDetail.vue'

export default {
  data() {
    return {
       sheet: true,
      user: {
        name:"Woori아들",
        money: 4800,
      },
      viweingContent: 0,
      payments: [],
      tab: null,
      tabs: ['내역', '송금', '미션'], 
      showModal:false,
      detailedMission:'',
      dailyMission:[
      ],
      generalMission:[
      ],
      waitingMission:[
      ],
      missions:[
              {
                title:'설거지하기',
                content : '미션 설명',
                prize : 500,
                missionStatus:'SUCCESS',
                missionType:1,
              },
              {
                title:'미션 2',
                content : '미션 설명',
                prize : 5000,
                missionStatus:'REQUEST',
                missionType:0,
              },
              {
                title:'미션 3',
                content : '미션 설명',
                prize : 3000,
                missionStatus:'ONGOING',
                missionType:0,
              },
              {
                title:'미션 2',
                content : '미션 설명',
                prize : 5000,
                missionStatus:'REQUEST',
                missionType:0,
              },
              {
                title:'미션 3',
                content : '미션 설명',
                prize : 3000,
                missionStatus:'ONGOING',
                missionType:0,
              },
              {
                title:'미션 2',
                content : '미션 설명',
                prize : 5000,
                missionStatus:'SUCCESS',
                missionType:0,

              },
              {
                title:'미션 3',
                content : '미션 설명',
                prize : 3000,
                missionStatus:'ONGOING',
                missionType:0,
              },
            ],
    };
  },
  mounted(){
    this.missions.forEach(mission => {
      if(mission.missionStatus == 'REQUEST'){
        this.waitingMission.push(mission);
      }else{
        if(mission.missionType==0){
          this.generalMission.push(mission);
        }else{
          this.dailyMission.push(mission);
        }
      }
    });
  },
  components: {
    ChildPayment,
    // ChildRemittance,
    // ChildMission,
    ChildBankSelection,
    Mission,
    MissionDetail,
    VueBarcode
  },
  methods: {
    moveTo(component) {
      this.$router.push({ name: component });
    },
    changeViewingContent(no) {
      this.viweingContent = no;
    },
    addComma(num) {
      var regexp = /\B(?=(\d{3})+(?!\d))/g;
      return num.toString().replace(regexp, ",");
    },
  
  closeModal(){
      this.showModal = false;
    },
  openModal(mission){
      console.log(mission);
      this.detailedMission = mission;
      this.showModal = true;
   },
   },
};
</script>

<style lang="scss" scoped>
@import "@/assets/scss/app/App.scss";
.childHome {
  height: 80vh;
}
.barcode {
  display: flex;
  flex-direction: column;
  width: 100%;
  align-items: center;
  justify-content: center;
  margin: 20px auto;
}

.buttons {
  position:absolute;
  bottom: 0;
  display: flex;
  width:100%;
  justify-content: space-around;
}
.v-progress-linear {
  width: 95%;
  margin: 0 auto 0;
}

.ParentAccountSummary {
  width: 100%;
  height: 35vh;
  /* box-shadow: 2px 2px 2px gray; */
  background-color: $wooriAppColor;
  display: flex;
  flex-direction: column;
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
  text-align: center;
  font-size: 23px;
  font-weight: 500;
}

.aligncenter {
  display: flex;
  align-items: center;
  justify-content: center;
}

.paymentBtn{
  width: 100vw;
    text-align: center;
    font-size: 12px;
    font-weight: 700;
    color: black;
    margin-bottom : 10px;
    font-size: large;
    font-weight: 600;
}
.ParentChildMission{
    height: 10vh;
    width: 90%;
    box-shadow: 0px 0px 4px #00000030;
    margin: 10px 5%;
    border-radius: 4px;
}
.modal{
  transform: scale(0.8); /* 0.5 초 동안 애니메이션을 실행, 단 0.8초 지연시켜 0.8초 후에 애니메이션을 실행 */ 
  animation: zoomIn .4s forwards;
  /* animation: zoomOut 0.5s cubic-bezier(0.165, 0.84, 0.44, 1) forwards; */
}
</style>
