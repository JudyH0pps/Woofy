<template>
  <div>
    <div class="alignCenter" style="width:100%; height:25%; padding: 5% 5% 1% 5%;">
      <div>
      <div style="text-align:center; font-size: x-large;
    font-weight: 500;">
        {{mission.title}}
      </div>
      <div style="text-align:center;    color: gray;
    font-size: small;">
        {{mission.content}}
      </div>
      <div style="text-align:center; font-size: x-large;
    color: #4292e4;
    font-weight: 500;">
        + {{addComma(mission.prize)}}원
      </div>
      </div>
    </div>
    <div v-if="user!='child'&&mission.missionStatus!='REQUEST'" style="width:100%; height:75%;">
      <div class="shadow" v-for="(submission,index) in subMissions" :style="{'background-color':bgColor(submission.missionState), 'color':textColor(submission.missionState)}" style="width:90%; overflow:scroll; height:13%; margin-left:5%; margin-bottom: 16px;" :key="index">
        <div class="float subtitle">{{submission.subtitle}}</div>
        <div v-if="submission.missionState=='Ongoing'" style="float:right;width:50%; height:100%">
          <div class="float subprize">{{addComma(submission.subprize)}}</div>
          <div style="margin:3%; background-color: #a7a7a7;
    color: white;" class="shadow subButton">미완료</div>
        </div>
        <div  v-else style="float:right;width:50%; height:100%">
          <div class="float subprize" >{{addComma(submission.subprize)}}</div>
          <div @click="$router.push('/ConfirmMission')" style="margin:3%;    background-color: #1053de;
    color: white;" class="shadow subButton">검사</div>
        </div>
      </div>
    </div>

    <div v-if="user!='parent'&&mission.missionStatus=='REQUEST'" style="width:100%; height:75%;">
      <div class="shadow" v-for="(submission,index) in subMissions" :style="{'background-color':bgColor(submission.missionState), 'color':textColor(submission.missionState)}" style="width:90%; overflow:scroll; height:13%; margin-left:5%; margin-bottom: 16px;" :key="index">
        <div class="float subtitle">{{submission.subtitle}}</div>
        <div v-if="submission.missionState=='Ongoing'" style="float:right;width:50%; height:100%">
          <div class="float subprize">{{addComma(submission.subprize)}}</div>
          <!-- <div style="margin:3%; background-color: #a7a7a7;
    color: white;" class="shadow subButton">미완료</div> -->
        </div>
        <div  v-else style="float:right;width:50%; height:100%">
          <div class="float subprize" >{{addComma(submission.subprize)}}</div>
          <!-- <div style="margin:3%;    background-color: #1053de;
    color: white;" class="shadow subButton">검사</div> -->
        </div>
      </div>
      
      <div style="position:absolute; bottom:0; height:10%; width:100%;">
        <div @click="closeModal" class="alignCenter" style="color:white; float:left; width:50%; height:80%; background-color:red;">
          삭제하기
        </div>
        <div class="alignCenter" style="color:white; float:right; width:50%; height:80%; background-color:#4d72d0;">
          등록하기
        </div>
        <div></div>
      </div>
    </div>

    <div v-if="user=='child'" style="width:100%; height:75%;">
      <div class="shadow" v-for="(submission,index) in subMissions" :style="{'background-color':bgColor(submission.missionState), 'color':textColor(submission.missionState)}" style="width:90%; overflow:scroll; height:13%; margin-left:5%; margin-bottom: 16px;" :key="index">
        <div class="float subtitle">{{submission.subtitle}}</div>
        <div v-if="submission.missionState=='Ongoing'" style="float:right;width:50%; height:100%">
          <div class="float subprize">{{addComma(submission.subprize)}}</div>
          <div style="margin:3%; background-color: #1053de;
    color: white;" class="shadow subButton">제출</div>
        </div>
        <div  v-else style="float:right;width:50%; height:100%">
          <div style="margin:3%; background-color: #a7a7a7;  
    color: white;" class="shadow subButton">완료</div>
        </div>
      </div>
    </div>

    
  </div>
</template>

<script>
export default {
props:['mission','user'],
methods:{
  addComma(num){
      var regexp = /\B(?=(\d{3})+(?!\d))/g;
      return num.toString().replace(regexp,',');
    },
    bgColor(mstate){
      if(this.user=='child'){
        if(mstate=='Ongoing'){
          return 'transparent';
        }else{
          return '#0067ac';
        }
      }else{
        if(mstate=='Ongoing'){
          return 'transparent';
        }else{
          return '#eaf6ff';
        }
      }
    },
    textColor(mstate){
      if(this.user=='child'){
        if(mstate=='Ongoing'){
          return 'black';
        }else{
          return 'white';
        }
      }else{
        if(mstate=='Ongoing'){
          return 'gray';
        }else{
          return 'black';
        }
      }
    },
    closeModal(){
      this.$emit('close');
    }
},
data() {
    return {
      subMissions:[
        {
          subtitle: '국어',
          subprize:10000,
          missionState:'Ongoing'
        },
        {
          subtitle: '수학',
          subprize:10000,
          missionState:'Ongoing'
        },
        {
          subtitle: '영어',
          subprize:10000,
          missionState:'Success'
        },
        {
          subtitle: '한국사',
          subprize:8000,
          missionState:'Ongoing'
        },
      ]
    };
}

}

</script>

<style>
.subtitle{
    width: 50%;
    height: 100%;
    display: flex;
    align-content: center;
    align-items: center;
    padding-left: 19px;
}

.subprize{
  display: flex;
    align-items: center;
    align-content: center;
    justify-content: flex-end;
    width: 50%;
    height: 100%;
}

.subButton{
  width: 40%;
    float: right;
    height: 80%;
    display: flex;
    align-items: center;
    justify-content: center;
}

.shadow{
    box-shadow: 0px 0px 6px #00000024;
    border-radius: 8px;
}
</style>