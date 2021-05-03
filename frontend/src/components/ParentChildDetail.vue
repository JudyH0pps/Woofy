<template>
  <div class="content" style="overflow: hidden">
    <v-icon
      size="23px"
      color="#a2a2a2"
      style="position: absolute; right: 10px; top: 13px; z-index: 1"
      @click="$router.push({ name: 'ParentChildAlert' })"
      >mdi-cog</v-icon
    >
    <div v-if="showModal" class="modalBackground">
      <div style="margin: 5% 4%; width: 90%; height: 10%">
        <v-icon @click="closeModal" style="float: right; z-index: 6"
          >mdi-close</v-icon
        >
      </div>
      <MissionDetail
        class="modal"
        :mission="detailedMission"
        :user="'parent'"
        @close="closeModal"
      ></MissionDetail>
    </div>
    <div class="ParantChildInform">
      <div
        style="width: 100%; height: 25%; text-align: center; font-weight: 500"
      >
        {{ child.name }}
      </div>
      <div class="parentChildDetail">
        <div
          style="height: 30%; width: 100%; margin: 6px 0px; padding: 0px 13px"
        >
          <span style="color: #5d5d5d; font-size: smaller">용돈</span
          ><span style="float: right">{{ addComma(child.pocketMoney) }}원</span>
        </div>
        <div
          style="width: 100%; height: 30%; margin: 6px 0px; padding: 0px 13px"
        >
          <span style="color: #5d5d5d; font-size: smaller">잔고</span
          ><span style="float: right">{{ addComma(child.money) }}원</span>
        </div>
        <!-- <v-progress-linear
            
            :value="(child.money/child.pocketMoney)*100" height="20px">
             <div style="font-size: 80%; color: #505050; font-weight: 500;">{{Math.round((child.money/child.pocketMoney)*100)}}%</div></v-progress-linear>    -->
        <div style="width: 100%; height: 20px; background-color: #e0dede"></div>
      </div>
    </div>
    <div style="height: 75%; width: 100%; overflow: hidden">
      <v-tabs v-model="tab" background-color="#4285f4" dark color="white" grow>
        <v-tab style="font-size: 15px" v-for="tab in tabs" :key="tab">
          {{ tab }}
        </v-tab>
        <v-tabs-slider color="rgb(141 200 239)"></v-tabs-slider>
      </v-tabs>

      <v-tabs-items
        style="height: 100%; width: 100%; padding-bottom: 10%"
        v-model="tab"
      >
        <v-tab-item style="height: 100%; width: 100%; overflow: scroll">
          <payment
            class="ParentChildPayment"
            v-for="(pay, index) in child.payment"
            :key="index"
            :payment="pay"
          ></payment>
        </v-tab-item>

        <v-tab-item
          style="
            height: 100%;
            width: 100%;
            overflow: scroll;
            padding-bottom: 10%;
          "
        >
          <div
            @click="$router.push('/WaitingMission')"
            class="ParentChildMission alignCenter float"
            style="
              font-size: small;
              height: 11%;
              background-color: rgb(224 238 249);
              width: 45%;
            "
          >
            <v-badge
              color="red"
              :content="waitingMission.length"
              style="width: 90%; text-align: center"
            >
              등록 요청 된 미션
            </v-badge>
          </div>
          <div
            @click="$router.push('/CreateMission')"
            class="ParentChildMission alignCenter"
            style="
              font-size: small;
              height: 11%;
              background-color: rgb(224 238 249);
              width: 40%;
            "
          >
            미션 추가
          </div>
          <div>
            매일 미션
            <Mission
              class="ParentChildMission"
              v-for="(mission, index) in dailyMission"
              :arrow="'right'"
              :key="index"
              @missionClick="openModal(mission)"
              :mission="mission"
            ></Mission>
          </div>
          등록된 미션
          <!-- <v-badge  color="green" :content="1" style="width:90%; margin:0 5%; padding-top:2px; text-align:center" >
              <Mission style="width:100%; margin:0; background: #caf5c975;" class="ParentChildMission" :arrow="'right'" :mission="generalMission[0]"></Mission>         
            </v-badge> -->
          <Mission
            class="ParentChildMission"
            v-for="(mission, index) in generalMission"
            :arrow="'right'"
            :key="index"
            @missionClick="openModal(mission)"
            :mission="mission"
          ></Mission>
          <div></div>
        </v-tab-item>
      </v-tabs-items>
    </div>
  </div>
</template>

<script>
import Payment from "./Payment.vue";
import Mission from "./Mission.vue";
import MissionDetail from "./MissionDetail.vue";
// import WaitingMission from '@/components/WaitingMissions.vue';
// import $ from 'jquery';

export default {
  components: {
    Payment,
    Mission,
    MissionDetail,
    // WaitingMission
  },
  computed: {},
  mounted() {
    this.child.missions.forEach((mission) => {
      if (mission.missionStatus == "REQUEST") {
        this.waitingMission.push(mission);
      } else {
        if (mission.missionType == 0) {
          this.generalMission.push(mission);
        } else {
          this.dailyMission.push(mission);
        }
      }
    });
    this.$store.commit("setWaitingMissions", this.waitingMission);
  },
  data() {
    return {
      showModal: false,
      detailedMission: "",
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
            title: "시험 성적 95점 이상",
            content: "미션 설명",
            prize: 5000,
            missionStatus: "REQUEST",
            missionType: 0,
          },
          {
            title: "할머니 안마해드리기",
            content: "미션 설명",
            prize: 3000,
            missionStatus: "ONGOING",
            missionType: 0,
          },
          {
            title: "시험 성적 95점 이상",
            content: "미션 설명",
            prize: 50000,
            missionStatus: "REQUEST",
            missionType: 0,
          },
          {
            title: "시험 성적 95점 이상",
            content: "미션 설명",
            prize: 50000,
            missionStatus: "ONGOING",
            missionType: 0,
          },
          {
            title: "콩이 산책시키기",
            content: "미션 설명",
            prize: 2000,
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
            location: "CU장덕점",
            price: 2300,
            time: "2021-05-02 16:03:54",
          },
          {
            location: "딜리버리히어로",
            price: 12000,
            time: "2021-04-27 18:44:07",
          },
          {
            location: "지바이크",
            price: 1350,
            time: "2021-04-21 16:03:54",
          },
          {
            location: "미용실",
            price: 12000,
            time: "2021-03-21 18:44:07",
          },
          {
            location: "PC방",
            price: 2000,
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
    openModal(mission) {
      console.log(mission);
      this.detailedMission = mission;
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
