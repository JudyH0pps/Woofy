<template>
  <div style="content">
      <div class="float date" >
           {{date}}
      </div>
      <div class="float locate">
            <div>
                  {{payment.location}}
            </div>
            <div class="time">
                  {{time}}
            </div>
      </div>
      <div class="float price" >
            {{addComma(payment.price)}} 원
      </div>
      
  </div>
</template>

<script>
export default {
      data() {
            return {
               date:'',
               time:''   
            };
      },
props: ['payment'],
mounted(){
      this.splitTime();
},
methods:{
      addComma(num){
            var regexp = /\B(?=(\d{3})+(?!\d))/g;
            return num.toString().replace(regexp,',');
      },
      splitTime(){
            let timeSplit = this.payment.time.split(' ');
            let tempdate = timeSplit[0].split('-');
            let tempTime = timeSplit[1].split(':');

            this.date =  tempdate[1]+'.'+tempdate[2];
            this.time = tempTime[0]+":"+tempTime[1];
            
      }
}
}
</script>

<style>
.date{
      height: 100%;
      width: 20%;
      font-size: smaller;
      font-weight: 600;
}

.locate{
      height: 100%;
    width: 50%;
    font-size: medium;
    font-weight: 500;
}

.price{
      height:100%;
      width:30%;
      font-size: medium;
      font-weight: 500;
      text-align:right;
}
.time{
      font-size: 12px;
      color: gray;
      font-weight: 400;
}
</style>