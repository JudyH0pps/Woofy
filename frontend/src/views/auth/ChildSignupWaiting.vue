<template>
  <section>
    <p>회원가입 요청을 보냈습니다!</p>
    <p>부모님이 승인하시면 회원가입이 완료됩니다.</p>
    <button class="ok-btn" @click="$router.push({ name: 'Root' })">
      로그인페이지로 이동
    </button>
  </section>
</template>

<script>
export default {
  data() {
    return {};
  },
  methods: {},
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
  justify-content: center;
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