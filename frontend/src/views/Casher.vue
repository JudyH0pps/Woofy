<template>
  <div id="casher" class="content">
      <v-text-field
            style="width:90%; margin-left:5%;"
            label="가격을 적어주세요."
            placeholder="가격을 적어주세요."
            solo
            type="number"
          ></v-text-field>
          <div @click="click" class="alignCenter paymentBtn">
              결제하기
          </div>
          <div style="width:100%;" class="alignCenter">
            <img style="width:50vh; opacity:1; position:absolute;bottom:0;" src="@/assets/images/casher.png"/>
          </div>
  </div>
</template>

<script>
export default {
methods:{
    click(){
        console.log("결제")
    }
}
}
</script>

<style scoped>
#casher {
    background-image: url(/img/casherBG.69df66bb.jpg);
    background-size: cover;
    background-position-x: right;
    padding-top:30%;
}
.paymentBtn{
    width:80%;
    z-index:3;
    margin-left:10%;
    height:8%;
    color:white;
    background-color: green;
    border-radius: 5px;
    font-weight: 00;
}

.paymentBtn :focus{
    width:80%;
    z-index:3;
    margin-left:10%;
    height:8%;
    color:white;
    background-color: yellow;
    border-radius: 5px;
    font-weight: 00;
}
</style>