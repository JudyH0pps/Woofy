<template>
  <section>
    <article>
      <p style="margin: 0 auto; text-align: center">
        <span class="logo"
          ><span style="font-style: oblique">WOORI</span> i</span
        >
      </p>
      <div>
        <div class="input_title"><span>ID</span></div>
        <input
          type="ID"
          placeholder="아이디를 입력해주세요."
          id="id"
          autofocus
        />
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
      <button class="login_button" @click="parentLogin()">LOG IN</button>
    </div>
    <button class="login_button" @click="childLogin()">자녀페이지</button>
    <div class="login_signup-btn">
      <button class="login_signup-button" @click="signup()">SIGN UP</button>
    </div>
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
    parentLogin() {
      this.$store.dispatch("login", true);
      this.$router.push({ name: "ParentHome" });
    },
    childLogin() {
      this.$store.dispatch("login", false);
      this.$router.push({ name: "ChildHome" });
    },
    signup() {
      this.$router.push({ name: "Signup" });
    },
  },
};
</script>

<style lang="scss" scoped>
section {
  padding: 45px;
  background-color: #ffffff;
  height: 100%;
}

article {
  padding-left: 0px;
  margin-bottom: 51%;
}

.logo {
  margin: 0 auto 0;
}

.login_intro {
  font-size: 12px;
  color: #a7d7ff;
  margin-bottom: 55px;
}

input {
  width: 100%;
  min-height: 50px;
  margin-bottom: 10px;
  padding: 15px;
}

article div {
  margin-bottom: 15px;
  position: relative;
  width: 100%;
  input {
    height: 40px;
    background-color: white;
    border: 1px solid #eeeeee;
  }
}

button {
  width: 100px;
}

.login_title {
  font-size: 30px;
  font-weight: bold;
  color: white;
  margin-bottom: 10px;
  margin-top: 40px;
}

.login_title2 {
  font-size: 30px;
  font-weight: bold;
  color: white;
  margin-bottom: 25px;
}

.error_message {
  position: absolute;
  font-size: 11px;
  color: #fcfcfc;
  margin: 5px 0 0 3px;
}

.input_title {
  color: #2c4e8d;
}

.login_btn {
  display: flex;
  justify-content: center;
  margin-bottom: 15px;
}
.login_button {
  background-color: #3c3eca;
  width: 100%;
  height: 50px;
  border-radius: 8px;
  color: white;
  font-weight: bold;
  border: none;
}
.login_btn {
  display: flex;
  justify-content: center;
}

.login_signup-button {
  background-color: #414141;
  width: 100%;
  height: 50px;
  border-radius: 8px;
  font-weight: bold;
  color: #ff7373;
  border: none;
}
</style>
