<template>
  <section class="Remittance">
    <div v-if="beforeAccountInput" class="accountInput">
      <div class="inputField">
        <p>{{ selectedBank }}</p>
        <input
          placeholder="입금할 계좌번호 입력"
          disabled
          v-model="accountSlash"
        />
        <div></div>
      </div>
    </div>
    <div v-if="!beforeAccountInput" class="moneyInput">
      <div class="inputField">
        <p>보낼 금액</p>
        <input disabled v-model="moneyComma" />
        <div></div>
      </div>
    </div>
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
        <button class="OK_button" @click="OK()">확인</button>
      </div>
    </v-item-group>
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
export default {
  data() {
    return {
      accountNumber: "",
      beforeAccountInput: true,
      selectedBank: "",
      money: "",
      showLoading: false,
      keys: [1, 2, 3, 4, 5, 6, 7, 8, 9, "", 0, "del"],
    };
  },
  components: {},
  computed: {
    moneyComma() {
      if (this.money === "") {
        return "0";
      }
      let comma = "";
      let t = this.money.length % 3;
      for (let idx = 0; idx < this.money.length; idx++) {
        let char = this.money.charAt(idx);
        if (idx !== 0 && idx % 3 === t) comma += ",";
        comma += char;
      }
      return comma + " 원";
    },
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
      if (this.beforeAccountInput) {
        if (this.accountNumber.length >= 13) return;
        if (key === "del") {
          this.accountNumber = this.accountNumber.slice(0, -1);
        } else {
          this.accountNumber += key;
        }
      } else {
        if (this.money.length >= 13) return;
        if (key === "del") {
          this.money = "";
        } else if (this.money == "" && (key === "00" || key === 0)) {
          return;
        } else {
          this.money += key;
        }
      }
    },
    OK() {
      if (this.beforeAccountInput) {
        this.beforeAccountInput = false;
        this.keys[9] = "00";
      } else {
        this.showLoading = true;
        this.$store.commit("setChildMoney", 10000);
        this.$store.commit("addChildPayment");
        setTimeout(() => {
          this.$router.push({ name: "ChildHome" });
        }, 2500);
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
.moneyInput {
  input {
    border-bottom: 1px solid black;
    border-radius: 0;
    background: white;
    text-align: right;
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
