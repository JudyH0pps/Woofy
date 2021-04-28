<template>
  <section>
    <header>
      <p class="login_intro">로그인시 사용할 비밀번호를 입력해주세요</p>
    </header>
    <article>
      <div>
        <div class="input_title"><span>E-mail</span></div>
        <input
          type="email"
          placeholder="이메일 주소를 입력해주세요."
          id="email"
          autofocus
        />
        <p class="error_message">잘못된 이메일 양식입니다.</p>
      </div>
      <br />
      <div>
        <div class="input_title"><span>Password</span></div>
        <input
          type="password"
          placeholder="비밀번호를 입력해주세요."
          v-model="password"
        />
      </div>
    </article>
    <div class="login_btn">
      <button class="login_button" @click="login()">LOG IN</button>
    </div>
    <!-- <div class="login_signup-btn">
      <button class="login_signup-button">SIGN UP</button>
    </div> -->
  </section>
</template>

<script>
// import { validateEmail, validatePwd } from "@/utils/validation";
export default {
  data() {
    return {
      email: "",
      password: "",
    };
  },
  methods: {
    login() {
      this.$router.push({ name: "ParentHome" });
    },
  },
};
</script>

<style lang="scss" scoped>
@import "@/assets/scss/auth/LoginPage";
</style>
