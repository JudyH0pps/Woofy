<template>
  <div class="content">
      <v-icon @click="$router.push('/ParentChildDetail')" size="40px" style="position: absolute; margin-top: 3px; z-index:1;">mdi-chevron-left</v-icon>
   <div class="alignCenter" style="height:8%; width:100%;">미션 검사</div>
       <div class="alignCenter missionTitle">{{mission.title}}</div>
    <v-carousel style="height:40vh" v-model="model">
    <v-carousel-item
    style="height:100%;"
      v-for="(color, i) in colors"
      :key="i"
      :style="{color}"
    >
        <img style="width:100%;" :src="color"/>
    </v-carousel-item>
  </v-carousel>

  <div style="width: 90%; height: 25%; border: 1px solid #8080803d;
    margin: 5%; maring-rop: 5%; border-radius: 6px; padding: 5%;">
    {{mission.content}}
  </div>

    <div style="position:absolute; bottom:0;width:100%;height:20%;">
        <div @click="$router.push('/ParentChildDetail')" class="alignCenter" style="position:absolute; bottom:0; width:50%; height:40%; background-color: red; font-weight: 600; color: white; ">
            반려하기
        </div>
        <div @click="$router.push('/ParentChildDetail')" class="alignCenter" style="position:absolute; bottom:0; right:0; width:50%; height:40%; background-color: #4285f4; font-weight: 600; color: white; ">
            검사 완료
        </div>
   </div>
  </div>
</template>

<script>
export default {
    computed:{
    },
    data() {
        return {
            mission:{
                title:'영어',
                content:'열심히 풀었어요'
            },
            model: 0,
      colors: [
          'https://lh3.googleusercontent.com/proxy/trDuINXY4VKjRZKFGPdWSXZUDjiJOwm5apkjgVh0gGAOyfnQWarQ4hVRyEMHXyzj0XyXa_RVjzy85cfXZhWMY83ywkEMq3hgRX7BRNrNPdZdYOAlwIeWzeBB15jngGE7vswa5AnAodoR_Mr8e5njtVvbYbro56odx4kUYPDYE8OXpF2r-hU_wK1dZ6rGuco24IvEbUAz2PIulGyYSXmPH5k3kdF84mpcSUPBzyffV6DbihJ6-oQ-IkNatjWOo0sYfl6slZzPGIRkiA_KC8z7QbDpE8Qk0Xb8DgmBmEk',
        'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQP8SukdK41MecC4fD3-0Y1xmSYw5FzcDtE1w&usqp=CAU',
        'https://lh3.googleusercontent.com/proxy/BlLZBXAl6bTFCaB0EUAEjNm2XDte3rWDPUJG1QNsXpAEDgXnRa-pjC7V8qozOD2MiFbfJXkB8Q2I2aqLbA1TbW5lwQSSgck2Igz751FeJ1md3vOX0Hylx_R2_OX4JzG41AB6b-sCrsbvyPEtc95tdZFuDAZC70FdEt-w_F_n_zwubfn5jQDzAJoDkOwtatBrPZv6w3b7WibDXzb3gJcTPFMJoBeDGMnqeY9Ic3dmqXKmgQk-v6L6wjtiFbD0plVTjTIjYTPjd-IuDPJ8cMEL-669IakiZQh8LimP6aA_'
      ],
        };
    },
    methods: {
        addComma(num){
        var regexp = /\B(?=(\d{3})+(?!\d))/g;
        return num.toString().replace(regexp,',');
        },
    },
}
</script>

<style>
.textArea{
    /* border-top: 1px solid #e4e4e4;
    border-bottom: 1px solid #e4e4e4; */
    width:100%;
    height:10%;
}

.missionTitle{
    width: 100%;
    height: 8%;
    border-top: 1px solid #e4e4e4;
    font-size: larger;
    font-weight: 600;
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