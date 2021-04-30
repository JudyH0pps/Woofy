<template>
  <section class="childMissionRequest">
    <div class="inputField">
      <div class="input_title"><span>미션 이름</span></div>
      <input
        type="mission-title"
        placeholder="요청할 미션 이름을 입력해주세요"
        id="mssion-title"
        v-model="missionTitle"
        autofocus
      />
      <div class="input_title"><span>미션 요청 메시지</span></div>
      <textarea
        type="mission-message"
        placeholder="미션 요청을 위한 메시지를 남겨주세요"
        v-model="missionMessage"
      />
      <div class="input_title"><span>요청 금액</span></div>
      <input
        type="price"
        placeholder="요청할 금액을 입력해주세요"
        id="price"
        v-model="price"
        autofocus
      />
      <div class="btn">
        <button>요청하기</button>
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
    };
  },
  components: {},
};
</script>

<style lang="scss" scoped>
.childMissionRequest {
  display: flex;
  flex-direction: column;
  align-items: center;
  height: 100%;
}
.inputField {
  width: 90%;
  height: 80%;
  margin: 100px;
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
  }
  input {
    height: 35px;
  }
  textarea {
    height: 50%;
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
