<template>
  <div @click="missionClick">
    <div class="float alignCenter" style="height:100%; width:50%;">
      {{mission.title}}
    </div>

    <div class="float alignRight" style="height:100%; width:40%; font-size: larger; color: #4285f4; font-weight:500">
      +{{addComma(mission.prize)}}
    </div>

    <div class="alignCenter" style="float:right;height:100%; width:10%;">
     <v-icon>mdi-chevron-{{arrow}}</v-icon>
    </div>
  </div>
</template>

<script>

export default {
props: ['mission','arrow'],
methods:{
  addComma(num){
      var regexp = /\B(?=(\d{3})+(?!\d))/g;
      return num.toString().replace(regexp,',');
  },
  missionClick(){
      this.$emit('missionClick');
  }
}
}
</script>

<style>
.alignRight{
      display: flex;
    align-items: center;
    justify-content: flex-end;
}
</style>