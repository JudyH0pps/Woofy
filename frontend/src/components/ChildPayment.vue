<template>
  <section class="childPayment">
    <div class="barcode">
      <VueBarcode
        :value="barcodeValue"
        height="80"
        width="3"
        displayValue="false"
        lineColor="#0aa"
      >
        Show this if the rendering fails.
      </VueBarcode>
    </div>
    <p style="text-align: center">결제내역</p>
    <div class="paymentDetail">
      <div
        class="payment"
        v-for="(item, i) in $store.state.childPayment"
        :key="i"
      >
        <v-list-item-content>
          <v-list-item-title> {{ item.location }} </v-list-item-title>
          <v-list-item-subtitle style="text-align: right"
            >{{ item.price }}원</v-list-item-subtitle
          >
        </v-list-item-content>
      </div>
    </div>
  </section>
</template>

<script>
import VueBarcode from "vue-barcode";

export default {
  data() {
    return {
      barcodeValue: "string",
    };
  },
  components: {
    VueBarcode,
  },
};
</script>

<style lang="scss" scoped>
.childPayment {
  width: 100%;
  height: 60%;
}
.barcode {
  display: flex;
  flex-direction: column;
  width: 100%;
  align-items: center;
  justify-content: center;
  margin: 20px auto;
}

.userMoney {
  text-align: right;
}

.paymentDetail {
  width: 95%;
  height: 100%;
  margin: 0 auto 0;
  overflow-y: scroll;
}
</style>
