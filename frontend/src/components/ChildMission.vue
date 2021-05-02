<template>
  <section class="content">
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
    <!-- <div class="mission">
      <div style="width: 85%">
        <p>설거지 하기</p>
        <p class="amount">+500</p>
      </div>
      <v-btn color="primary" fab small>></v-btn>
    </div>
    <div class="mission">
      <div style="width: 85%">
        <p>시험 100점</p>
        <p class="amount">+50000</p>
      </div>
      <v-btn color="primary" fab small>></v-btn>
    </div>
    <div
      @click="moveTo('ChildMissionRequest')"
      class="ChildAccountSummary aligncenter"
      style="color: gray"
    >
      미션 추가 요청<v-icon>mdi-plus-circle-outline</v-icon>
    </div> -->
  </section>
</template>

<script>
import { mapActions } from "vuex";
import Mission from './Mission.vue';

export default {
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
  data() {
    return {
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
  components: {Mission},
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

.ParentChildMission{
    height: 10vh;
    width: 90%;
    box-shadow: 0px 0px 4px #00000030;
    margin: 10px 5%;
    border-radius: 4px;
}
</style>
