<template>
  <div class="content">
    <div>
      <v-icon @click="$router.push('/ParentHome')" size="40px">mdi-chevron-left</v-icon>
    </div>
    <div class="ParantChildInform">
        <div>{{child.name}}</div>
        <div>{{childMoney}}</div>   
        <v-progress-linear style="position:absolute; bottom:0;"
        background-color="rgb(66 133 244 / 21%)"
      color="#4285f4" height="20px" :value="child.rate"></v-progress-linear>   
    </div>

    <div style="height:80%; width:100%;">
      <v-tabs v-model="tab" background-color="white" color="#4285f4" grow >
        <v-tab
          v-for="tab in tabs"
          :key="tab" >
          {{ tab }}
        </v-tab>
        <v-tabs-slider color="#0067ac"></v-tabs-slider>
      </v-tabs>

      <v-tabs-items style="height:100%; width:100%;" v-model="tab">
        <v-tab-item style="height:100%; width:100%;">
          <payment class="ParentChildPayment" v-for="(pay,index) in child.payment" :key="index" :payment="pay"></payment>
        </v-tab-item>

        <v-tab-item style="height:100%; width:100%;">
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
            money : 40000,
            rate: 60,
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
              }
            ]
      },
      tab: null,
      tabs: ['결제 내역', '미션 관리'], 
    };
  },
};
</script>

<style scoped>
.ParantChildInform{
    height: 20%;
    width: 100%;
    text-align: center;
    position: relative;
    /* box-shadow: 0px 0px 4px #00000036; */
}

.ParentChildPayment{
    height: 20%;
    width: 100%;
    border-bottom: 1px rgb(95, 95, 95);
    margin-bottom: 12px;
    box-shadow: 0px 1px 0px #0000001f;
    margin: 10px;
}
</style>
