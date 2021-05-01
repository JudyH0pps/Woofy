<template>
  <section class="signup">
    <p>
      <span class="logo"><span style="font-style: oblique">WOORI</span> i</span>
      회원가입을 환영합니다!
    </p>
    <button class="parentSignup" @click="moveTo('ParentSignupPage')">
      <svg
        xmlns="http://www.w3.org/2000/svg"
        enable-background="new 0 0 24 24"
        height="80px"
        viewBox="0 0 24 24"
        width="80px"
        fill="#0066cc"
      >
        <g>
          <rect fill="none" height="50" width="50" />
          <path
            d="M6.5,2c1.1,0,2,0.9,2,2s-0.9,2-2,2s-2-0.9-2-2S5.4,2,6.5,2z M15.5,9.5c0,0.83,0.67,1.5,1.5,1.5s1.5-0.67,1.5-1.5 S17.83,8,17,8S15.5,8.67,15.5,9.5z M18.5,12h-2.84c-0.58,0.01-1.14,0.32-1.45,0.86l-0.92,1.32L9.72,8C9.35,7.37,8.69,7.01,8.01,7H5 C3.9,7,3,7.9,3,9v6h1.5v7h5V11.61L12.03,16h2.2L15,14.9V22h4v-5h1v-3.5C20,12.68,19.33,12,18.5,12z"
          />
        </g>
      </svg>
      부모 회원가입
      <small>우리은행 계좌가 있으신 부모님</small>
    </button>
    <button class="childSignup" @click="moveTo('ChildSignupPage')">
      <svg
        xmlns="http://www.w3.org/2000/svg"
        height="80px"
        viewBox="0 0 24 24"
        width="80px"
        fill="#ff6666"
      >
        <path d="M0 0h24v24H0z" fill="none" />
        <circle cx="14.5" cy="10.5" r="1.25" />
        <circle cx="9.5" cy="10.5" r="1.25" />
        <path
          d="M22.94 12.66c.04-.21.06-.43.06-.66s-.02-.45-.06-.66c-.25-1.51-1.36-2.74-2.81-3.17-.53-1.12-1.28-2.1-2.19-2.91C16.36 3.85 14.28 3 12 3s-4.36.85-5.94 2.26c-.92.81-1.67 1.8-2.19 2.91-1.45.43-2.56 1.65-2.81 3.17-.04.21-.06.43-.06.66s.02.45.06.66c.25 1.51 1.36 2.74 2.81 3.17.52 1.11 1.27 2.09 2.17 2.89C7.62 20.14 9.71 21 12 21s4.38-.86 5.97-2.28c.9-.8 1.65-1.79 2.17-2.89 1.44-.43 2.55-1.65 2.8-3.17zM19 14c-.1 0-.19-.02-.29-.03-.2.67-.49 1.29-.86 1.86C16.6 17.74 14.45 19 12 19s-4.6-1.26-5.85-3.17c-.37-.57-.66-1.19-.86-1.86-.1.01-.19.03-.29.03-1.1 0-2-.9-2-2s.9-2 2-2c.1 0 .19.02.29.03.2-.67.49-1.29.86-1.86C7.4 6.26 9.55 5 12 5s4.6 1.26 5.85 3.17c.37.57.66 1.19.86 1.86.1-.01.19-.03.29-.03 1.1 0 2 .9 2 2s-.9 2-2 2zM7.5 14c.76 1.77 2.49 3 4.5 3s3.74-1.23 4.5-3h-9z"
        /></svg
      >자녀 회원가입
      <small>용돈 관리를 하고싶은 자녀분</small>
    </button>
  </section>
</template>

<script>
// import { validateEmail, validatePwd } from "@/utils/validation";
import { mapActions } from "vuex";

export default {
  data() {
    return {
      email: "",
      password: "",
    };
  },
  methods: {
    ...mapActions(["moveTo"]),
    login() {
      this.$router.push({ name: "ParentHome" });
    },
    login2() {
      this.$router.push({ name: "ChildHome" });
    },
  },
};
</script>

<style lang="scss" scoped>
.signup {
  display: flex;
  flex-direction: column;
  align-items: center;
  height: 100%;
  width: 100%;
  padding: 50px;
}

.parentSignup,
.childSignup {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  height: 230px;
  width: 230px;
  border: 2px solid;
  margin: 15px;
  border-radius: 15px;
  font-weight: bold;
  font-size: 18px;
  small {
    color: rgb(0, 0, 0);
    font-weight: 400;
  }
}
.parentSignup {
  border-color: #0066cc;
  color: #0066cc;
}
.childSignup {
  border-color: #ff6666;
  color: #ff6666;
}
</style>
