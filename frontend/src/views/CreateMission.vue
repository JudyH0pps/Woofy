<template>
  <div class="content">
      <v-icon @click="$router.push('/ParentChildDetail')" size="40px" style="position: absolute; margin-top: 3px; z-index:1;">mdi-chevron-left</v-icon>
   <div class="alignCenter" style="height:8%; width:100%;">미션 등록</div>
   <div class="textArea">
       <input placeholder="제목" class="missionTitle"/>
        <input placeholder="부가 설명을 입력해주세요." class="missionContent"/>
   </div>
    <div style="height:48%; width:100%;padding:0 5%; overflow-y:scroll">
        <div style="color: #464646;font-size: smaller;">세부 미션</div>
        <div style="height:90%;">
            <div v-for="(sm,index) in submission" :key="index" class="alignCenter" style="padding: 2% 5%;height:20%;">
                <input v-model="sm.subtitle" placeholder="미션 제목" style="float:left; width:50%; margin-right:5%; border-bottom: 1px solid #61616133;"/>
                <div style="width:40%;float:right;">
                <input type='number' v-model="sm.subprize" placeholder="0" style="width:80%; border-bottom: 1px solid #61616133; text-align:right;"/>원
                </div>
                <div v-if="submission.length>1" style="width:5%;"><v-icon @click="removeSubMission(index)" color="red">mdi-minus</v-icon></div>
            </div>
            <div @click="addSubmission" class="alignCenter" style="width:100%; height:10%;font-size: small; font-weight: 500;">
                <v-icon size="18px">mdi-plus</v-icon>세부미션 추가하기
            </div>
        </div>
    </div>
    <div style="position:absolute; bottom:0;width:100%;height:20%;">
        <div style="padding: 5%;">총 <span style="float:right;"><span>{{totalPrize}}</span> 원</span></div>
        <div @click="applymission" class="alignCenter" style="position:absolute; bottom:0; width:100%; height:40%; background-color: #4285f4; font-weight: 600; color: white; ">
            등록하기
        </div>
   </div>
  </div>
</template>

<script>
export default {
    computed:{
        totalPrize(){
            let total=0;
            this.submission.forEach(sm => {
                total += Number(sm.subprize);
            });
            return total;
        }
    },
    data() {
        return {
            submission:[
                {
                    subtitle:'',
                    subprize:''
                }
            ],
        };
    },
    methods: {
        addComma(num){
        var regexp = /\B(?=(\d{3})+(?!\d))/g;
        return num.toString().replace(regexp,',');
        },
        addSubmission(){
            this.submission.push({subtitle:'',subprize:''});
        },
        removeSubMission(i){
            this.submission.splice(i,1);
        },
        applymission(){
            this.$router.push('/ParentChildDetail')
        }
    },
}
</script>

<style>
.textArea{
    /* border-top: 1px solid #e4e4e4;
    border-bottom: 1px solid #e4e4e4; */
    width:100%;
    height:25%;
    padding: 5%;
}

.missionTitle{
    width:100%;
    height:30%;
    border-bottom: 1px solid #e4e4e4;
}

.missionTitle::placeholder{
    font-size: 20px;
}
.missionContent{
    width: 100%;
    height:70%;
    border-bottom: 1px solid #e4e4e4;
}

.missionContent::placeholder{
    font-size: 15px;
}
</style>