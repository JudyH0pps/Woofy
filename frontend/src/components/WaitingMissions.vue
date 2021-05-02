<template>
  <div class="content">
    <v-icon @click="$router.push('/ParentChildDetail')" size="40px" style="position: absolute; margin-top: 3px; z-index:1;">mdi-chevron-left</v-icon>
   <div v-if="showModal" class="modalBackground">
      <div style="margin:5% 4%; width:90%; height:10%; "><v-icon @click="closeModal" style="float:right; z-index:6;">mdi-close</v-icon></div>
      <MissionDetail class="modal" :mission="detailedMission"></MissionDetail>
    </div> 
    <div class="alignCenter" style="width:100%; height:8%;">등록 요청된 미션</div>
    <div style="width:100%; height:20%; padding-left:5%;">매일 미션
        <div v-if="dailyMissions.length==0" class="alignCenter WaitingMission" style="font-size:small">등록된 매일 미션이 없습니다.</div>
            <Mission class="WaitingMission"  @click="openModal" v-for="(mission,index) in dailyMissions" @missionClick="openModal(mission)" :arrow="'right'" :key="index" :mission="mission"></Mission>
          </div>
          <div style="width:100%; height:70%; padding-left:5%;">
            등록된 미션
             <!-- <v-badge  color="green" :content="1" style="width:90%; margin:0 5%; padding-top:2px; text-align:center" >
              <Mission style="width:100%; margin:0; background: #caf5c975;" class="ParentChildMission" :arrow="'right'" :mission="generalMission[0]"></Mission>         
            </v-badge> -->
              <Mission class="WaitingMission"  v-for="(mission,index) in generalMissions" @missionClick="openModal(mission)" :arrow="'right'" :key="index" :mission="mission"></Mission>
          </div>
    <!-- <Mission class="WaitingMission" v-for="(mission,index) in watingMission" :key="index" :arrow="'right'" :mission="mission"></Mission> -->
  </div>
</template>

<script>
import Mission from './Mission.vue';
import MissionDetail from './MissionDetail.vue';

export default {
components:{Mission,MissionDetail},
props:['waiting'],
computed:{
  watingMission(){
    return this.$store.state.waitingMissions;
  }
},
mounted(){
  this.watingMission.forEach(mission => {
    if(mission.missionType==0){
          this.generalMissions.push(mission);
        }else{
          this.dailyMissions.push(mission);
        }
  });
},
data() {
    return {
      dailyMissions:[],
      generalMissions:[],
      showModal:false,
      detailedMission:''
    };
},
methods:{
   closeModal(){
      this.showModal = false;
    },
    openModal(m){
      console.log(m);
      this.detailedMission = m;
      this.showModal = true;
    }
}
}
</script>

<style>
.WaitingMission{
    width: 90%;
    height: 8vh;
    margin: 10px 0px;
    box-shadow: 0px 0px 3px #00000052;
    border-radius: 7px;
}
.modal{
  transform: scale(0.8); /* 0.5 초 동안 애니메이션을 실행, 단 0.8초 지연시켜 0.8초 후에 애니메이션을 실행 */ 
  animation: zoomIn .4s forwards;
  /* animation: zoomOut 0.5s cubic-bezier(0.165, 0.84, 0.44, 1) forwards; */
}
</style>