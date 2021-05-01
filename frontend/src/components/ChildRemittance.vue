<template>
  <section class="Remittance">
    <div v-if="beforeAccountInput" class="accountInput">
      <div class="inputField">
        <p>{{ selectedBank }}</p>
        <input placeholder="입금할 계좌번호 입력" v-model="accountSlash" />
        <div></div>
      </div>
    </div>
    <div v-if="!beforeAccountInput" class="moneyInput"></div>
    <v-item-group class="keypad">
      <v-row>
        <v-col
          @click="inputKey(key)"
          v-for="(key, i) in keys"
          :key="i"
          cols="4"
        >
          <v-item>
            <v-btn plain>{{ key }}</v-btn>
          </v-item>
        </v-col>
      </v-row>
      <div class="">
        <button class="OK_button">확인</button>
      </div>
    </v-item-group>
  </section>
</template>

<script>
export default {
  data() {
    return {
      accountNumber: "",
      beforeAccountInput: true,
      selectedBank: "",
      keys: [1, 2, 3, 4, 5, 6, 7, 8, 9, "", 0, "del"],
    };
  },
  components: {},
  computed: {
    accountSlash() {
      if (this.accountNumber.length <= 3) {
        return this.accountNumber;
      } else if (this.accountNumber.length <= 6) {
        return (
          this.accountNumber.slice(0, 3) + "-" + this.accountNumber.slice(3)
        );
      }
      return (
        this.accountNumber.slice(0, 3) +
        "-" +
        this.accountNumber.slice(3, 6) +
        "-" +
        this.accountNumber.slice(6, 13)
      );
    },
  },
  methods: {
    inputKey(key) {
      if (key === "del") {
        this.accountNumber = this.accountNumber.slice(0, -1);
      } else {
        this.accountNumber += key;
      }
    },
  },
  created() {
    this.selectedBank = this.$route.query.bankname;
  },
};
</script>

<style lang="scss" scoped>
* {
  font-weight: bold;
}
section {
  width: 100%;
  height: 100%;
}
.accountInput,
.moneyInput {
  height: 40%;
}
.inputField {
  width: 100%;
  padding: 100px 20px;
  input {
    height: 60px;
    width: 100%;
    padding: 20px;
    background: rgb(233, 233, 233);
    border-radius: 10px;
    font-size: 22px;
  }
}
.keypad {
  margin: 20% auto 0;
  height: 40%;
  width: 90%;
  * {
    text-align: center;
    font-size: 20px;
  }
}
.OK_button {
  margin: 50px 0;
  background-color: #3c3eca;
  width: 100%;
  height: 60px;
  border-radius: 8px;
  color: white;
  font-weight: bold;
  border: none;
}
</style>
