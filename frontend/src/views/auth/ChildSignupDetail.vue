<template>
  <section>
    <p style="margin-top: 20px">
      {{ $store.state.username }}님의 자녀분 환영합니다!
    </p>

    <div class="inputField">
      <div class="input_title"><span>아이디</span></div>
      <div
        style="
          display: flex;
          flex-direction: row;
          height: 50px;
          margin-bottom: 30px;
        "
      >
        <input placeholder="사용하실 아이디를 입력해주세요" v-model="id" />
      </div>
      <div class="input_title"><span>패스워드</span></div>
      <div
        style="
          display: flex;
          flex-direction: row;
          height: 50px;
          margin-bottom: 30px;
        "
      >
        <input
          type="password"
          placeholder="패스워드를 입력해 주세요"
          v-model="password1"
        />
      </div>
      <div class="input_title"><span>패스워드 확인</span></div>
      <div
        style="
          display: flex;
          flex-direction: row;
          height: 50px;
          margin-bottom: 30px;
        "
      >
        <input
          type="password"
          placeholder="패스워드를 다시 입력해 주세요"
          v-model="password2"
        />
      </div>
    </div>
    <button class="ok-btn" @click="complete">회원가입</button>
    <v-progress-circular
      v-show="showLoading"
      indeterminate
      size="50"
      style="
        position: fixed;
        left: 50%;
        top: 50%;
        transform: translate(-50%, -50%);
      "
      color="primary"
    ></v-progress-circular>
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
      showLoading: false,
      id: "",
      password1: "",
      password2: "",
    };
  },
  methods: {
    ...mapActions(["phoneAuth", "phoneCerti"]),
    requestPhoneAuth() {
      this.viewCerti = true;
      this.phoneAuth(this.phoneNumber);
      this.certiNum = "";
    },
    complete() {
      this.showLoading = true;
      setTimeout(() => {
        this.$router.push({ name: "ChildSignupWaiting" });
      }, 2500);
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