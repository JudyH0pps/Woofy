<template>
  <section class="ChildRemittance">
    <p style="text-align: center; margin: 20px">은행 선택</p>
    <v-item-group>
    <v-row>
      <v-col v-for="(item, i) in items" :key="i" cols="6" md="6">
        <v-item>
          <v-btn plain>{{ item }}</v-btn>
        </v-item>
      </v-col>
    </v-row>
    </v-item-group>
  </section>
</template>

<script>
export default {
  data() {
    return {
      items: [
        "우리은행",
        "ABC은행",
        "가나다은행",
        "EDF은행",
        "아야어은행",
        "ㅇㅇ은행",
        "JBK은행",
      ],
    };
  },
  components: {},
};
</script>

<style lang="scss" scoped>
.ChildRemittance {
  width: 100%;
  height: 100%;
}

.userMoney {
  text-align: right;
}
</style>
