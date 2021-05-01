<template>
  <div class="content" style="overflow:hidden">
    <v-icon @click="$router.push('/ParentHome')" size="40px" style="position: absolute; margin-top: 3px;">mdi-chevron-left</v-icon>
    <v-icon size="23px" color="#a2a2a2" style="position: absolute; right:10px; top:13px">mdi-cog</v-icon>
    <div class="ParantChildInform">
        <div style="width:100%; height:25%; text-align:center; font-weight: 500;">{{child.name}}</div>
        <div class="parentChildDetail">
            <div style="height:30%; width:100%; margin: 6px 0px; padding: 0px 13px;"><span style="color:#5d5d5d;font-size: smaller;">용돈</span><span style=" float:right;">{{addComma(child.pocketMoney)}}원</span></div>
            <div style="width:100%; height:30%; margin: 6px 0px; padding: 0px 13px;"><span style="color:#5d5d5d;font-size: smaller;">잔고</span><span style="float:right;">{{addComma(child.money)}}원</span></div>   
            <!-- <v-progress-linear
            
            :value="(child.money/child.pocketMoney)*100" height="20px">
             <div style="font-size: 80%; color: #505050; font-weight: 500;">{{Math.round((child.money/child.pocketMoney)*100)}}%</div></v-progress-linear>    -->
            <div style="width:100%; height:20px; background-color:#e0dede;"></div>
      </div>
    </div>
    <div style="height:75%; width:100%;">
      <v-tabs v-model="tab" background-color="#4285f4" dark color="white" grow>
        <v-tab style="font-size:15px;"
          v-for="tab in tabs"
          :key="tab" >
          {{ tab }}
        </v-tab>
        <v-tabs-slider color="rgb(141 200 239)"></v-tabs-slider>
      </v-tabs>

      <v-tabs-items style="height:100%; width:100%; overflow:scroll; padding-bottom:10%;" v-model="tab">
        <v-tab-item style="height:100%; width:100%; overflow:scroll;">
          <payment class="ParentChildPayment" v-for="(pay,index) in child.payment" :key="index" :payment="pay"></payment>
        </v-tab-item>

        <v-tab-item style="height:100%; width:100%; overflow:scroll; padding-bottom:10%;">
          <missoin class="ParentChildMission" v-for="(missions,index) in child.missions" :key="index" :payment="pay"></missoin>
        </v-tab-item>
      </v-tabs-items>

    </div>

  </div>
</template>

<script>
import Payment from './Payment.vue';
export default {
  components: { Payment },
  computed:{
    childMoney(){
      var regexp = /\B(?=(\d{3})+(?!\d))/g;
      return this.child.money.toString().replace(regexp,',');
    },
  },
  data() {
    return {
      child:{

            name : 'Woori 아들',
            money : 20000,
            pocketMoney:50000,
            mission:[
              
            ],
            payment:[
              {
                location : 'OOpc방',
                price : 5000,
                time : '2021-03-21 16:03:54'
              },
              {
                location : '김밥천국',
                price : 4500,
                time : '2021-03-21 18:44:07'
              },
              {
                location : 'OOpc방',
                price : 5000,
                time : '2021-03-21 16:03:54'
              },
              {
                location : '김밥천국',
                price : 4500,
                time : '2021-03-21 18:44:07'
              },
              {
                location : 'OOpc방',
                price : 5000,
                time : '2021-03-21 16:03:54'
              },
              {
                location : '김밥천국',
                price : 4500,
                time : '2021-03-21 18:44:07'
              },
              {
                location : '김밥천국',
                price : 4500,
                time : '2021-03-21 18:44:07'
              }
            ]
      },
      tab: null,
      tabs: ['결제 내역', '미션 관리'], 
    };
  },
  methods:{
    addComma(num){
      var regexp = /\B(?=(\d{3})+(?!\d))/g;
      return num.toString().replace(regexp,',');
    },
    rateColor(num){
      if(num<0.3){
        return 'red';
      }else if(num<0.6){
        return 'orange';
      }else{
        return 'green';
      }
    }
  }
};
</script>

<style scoped>
.parentChildDetail{
  position: absolute;
    bottom: 0px;
    width: 100%;
    height: 60%;
    display: flex;
    flex-direction: column;
    justify-content: flex-end;
}
.ParantChildInform{
    padding-top: 12px;
    height: 25%;
    width: 100%;
    position: relative;
    /* box-shadow: 0px 0px 4px #00000036; */
}

.ParentChildPayment{
    height: 14%;
    width: 100%;
    border-bottom: 1px rgb(95, 95, 95);
    margin-bottom: 12px;
    box-shadow: 0px 1px 0px #0000001f;
    padding: 10px;
}

.ParentChildMission{

}
</style>
