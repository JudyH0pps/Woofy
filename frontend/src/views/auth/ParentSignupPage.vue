<template>
  <section>
    <h1 style="margin: 20px">부모님 회원 가입</h1>
    <p style="margin: 20px">
      우리은행 계좌가 있으신 부모 고객님은 <br />본인 인증을 통해 가입하실 수
      있습니다.
    </p>
    <div class="inputField">
      <div class="input_title"><span>휴대폰 본인 인증</span></div>
      <div
        style="
          display: flex;
          flex-direction: row;
          height: 50px;
          margin-bottom: 30px;
        "
      >
        <input placeholder="휴대폰 번호 입력" v-model="phoneNumber" />
        <v-btn @click="requestPhoneAuth" plain>인증 요청</v-btn>
      </div>
      <p
        v-show="viewCerti"
        style="font-size: 12px; text-align: left; margin-rigth: auto"
      >
        입력하신 휴대폰 번호로 전송한 인증 번호를 입력해주세요
      </p>

      <div
        v-show="viewCerti"
        style="display: flex; flex-direction: row; height: 50px"
      >
        <input placeholder="인증 번호 입력" v-model="certiNum" />
      </div>
    </div>

    <button
      v-show="viewCerti"
      class="ok-btn"
      @click="phoneCerti({ phoneNumber, certiNum })"
    >
      인증
    </button>
  </section>
</template>

<script>
import { mapActions } from "vuex";

export default {
  data() {
    return {
      phoneNumber: "",
      certiNum: "",
      viewCerti: false,
    };
  },
  methods: {
    ...mapActions(["phoneAuth", "phoneCerti"]),
    requestPhoneAuth() {
      this.viewCerti = true;
      this.phoneAuth(this.phoneNumber);
      this.certiNum = "";
    },
  },
};
</script>

<style lang="scss" scoped>
* {
  font-weight: bold;
}
section {
  height: 100%;
  width: 100%;
  display: flex;
  flex-direction: column;
  align-items: center;
}
.inputField {
  width: 100%;
  padding: 0 20px;
  text-align: center;
  margin: 40px 0;
  .input_title {
    text-align: left;
  }
  input {
    border: 1px solid #eee;
    background: #eee;
    height: 50px;
    width: 100%;
    margin: 0 auto;
    padding: 10px;
  }
  button {
    height: 100% !important;
    background: #8ac4ff;
    color: rgb(0, 17, 255);
  }
}
.v-btn {
  height: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
}
.ok-btn {
  background-color: #3c3eca;
  width: 90%;
  height: 50px;
  border-radius: 8px;
  color: white;
  font-weight: bold;
  border: none;
}
</style>