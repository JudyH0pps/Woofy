<template>
  <section>
    <h1 style="margin: 20px">부모님 회원 가입</h1>
    <p style="margin: 20px">
      우리은행 계좌가 있으신 부모 고객님은 <br />본인 인증을 통해 가입하실 수
      있습니다.
    </p>
    <div class="inputField">
      <div class="input_title"><span>휴대폰 본인 인증</span></div>
      <div style="display: flex; flex-direction: row; height: 50px">
        <input v-model="phoneNumber" />
        <v-btn @click="phoneAuth(phoneNumber)" plain>인증 요청</v-btn>
      </div>
    </div>
  </section>
</template>

<script>
import { mapActions } from "vuex";

export default {
  data() {
    return {
      phoneNumber: "",
    };
  },
  methods: {
    ...mapActions(["phoneAuth"]),
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
  height: 100px;
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
</style>