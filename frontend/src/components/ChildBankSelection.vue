<template>
  <section class="ChildBankSelection">
    <p style="text-align: center; margin: 20px">송금할 은행 선택</p>
    <v-item-group>
      <v-row>
        <v-col
          @click="moveToRemittance(bankname)"
          v-for="(bankname, i) in banks"
          :key="i"
          cols="6"
          md="6"
        >
          <v-item>
            <v-btn plain>{{ bankname }}</v-btn>
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
      banks: [
        "우리은행",
        "ABC은행",
        "가나다은행",
        "EDF은행",
        "아야어은행",
        "새싹은행",
        "JBK은행",
      ],
    };
  },
  components: {},
  methods: {
    moveToRemittance(bankname) {
      this.$router.push({
        name: "ChildRemittance",
        query: { bankname: bankname },
      });
    },
  },
};
</script>

<style lang="scss" scoped>
.ChildBankSelection {
  width: 100%;
  height: 100%;
}

.userMoney {
  text-align: right;
}
</style>
