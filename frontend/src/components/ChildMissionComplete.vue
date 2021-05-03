<template>
  <section class="childMissionRequest">
    <div class="inputField">
      <div class="input_title"><span>미션 이름</span></div>
      <input
        type="mission-title"
        placeholder="시험 100점"
        id="mssion-title"
        disabled
      />
      <div class="input_title"><span>미션 인증 사진</span></div>
      <div id="preview">
        <img v-if="url" :src="url" />
      </div>
      <input
        id="fileInput"
        type="file"
        accept="image/png, image/jpeg, image/bmp"
        @change="onFileChange"
      />
      <div class="input_title"><span>요청 금액</span></div>
      <input type="price" placeholder="50000원" id="price" disabled />
      <div class="btn">
        <button>완료요청하기</button>
      </div>
    </div>
  </section>
</template>

<script>
export default {
  data() {
    return {
      missionTtitle: "",
      missionMessage: "",
      url: null,
    };
  },
  methods: {
    onFileChange(e) {
      const file = e.target.files[0];
      this.url = URL.createObjectURL(file);
    },
  },
  components: {},
};
</script>

<style lang="scss" scoped>
* {
  font-weight: bold;
}
.childMissionRequest {
  display: flex;
  flex-direction: column;
  align-items: center;
  height: 100%;
}
#preview {
  width: 100%;
  height: 30%;
  padding: 20px;
  overflow: hidden;
  img {
    width: 100%;
    margin: 0 auto;
  }
}
.inputField {
  width: 90%;
  height: 80%;
  padding-top: 50px;
  .input_title {
    font-size: 20px;
    font-weight: bold;
    margin-bottom: 5px;
  }
  input,
  textarea {
    width: 100%;
    border: 1px solid rgb(163, 163, 163);
    margin-bottom: 15px;
    padding: 10px;
    color: black;
  }
  input {
    height: 35px;
    font-size: 18px;
  }
  textarea {
    height: 50%;
    font-size: 18px;
  }
  #fileInput {
    height: 50px;
    border: 0;
    border-bottom: 1px solid gray;
  }
}
#price {
  border: 0;
  border-bottom: 1px solid gray;
  text-align: right;
}
.btn {
  background: skyblue;
  button {
    background-color: #3c3eca;
    width: 100%;
    height: 50px;
    border-radius: 8px;
    color: white;
    font-weight: bold;
    border: none;
  }
}
</style>
