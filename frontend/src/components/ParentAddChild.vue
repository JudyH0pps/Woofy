<template>
  <section>
    <vue-qrcode style="height: 200px" value="gogosing" />
    <p style="margin-bottom: 50px">CODE: MG-29348362922</p>
    <p>
      자녀분이 QR코드를 스캔하거나 회원가입 페이지에서 아래의 코드를 입력하여
      회원가입 할 수 있습니다.
    </p>
  </section>
</template>

<script>
import VueQrcode from "vue-qrcode";

export default {
  components: {
    VueQrcode,
  },
  computed: {},
  mounted() {},
  data() {
    return {};
  },
  methods: {},
};
</script>

<style scoped>
* {
  font-weight: bold;
}
section {
  width: 100%;
  height: 100%;
  padding: 25px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}
</style>
