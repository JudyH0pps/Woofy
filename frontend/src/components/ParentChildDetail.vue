<template>
  <div class="content">
    <div class="ParantChildInform">
        <div>{{child.name}}</div>
        <div>{{childMoney}}</div>      
    </div>

  </div>
</template>

<script>
export default {
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
            rate: 60

     },
    };
  },
};
</script>

<style scoped>
.ParantChildInform{
  height: 30%;
    width: 100%;
    box-shadow: 0px 0px 4px #00000036;
}
</style>
