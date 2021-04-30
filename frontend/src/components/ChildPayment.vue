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
    <div class="paymentDetail">
      <p style="text-align: center">결제내역</p>
      <v-card outlined tile>
        <v-list-item-content>
          <v-list-item-title> 파리바게트 </v-list-item-title>
          <v-list-item-subtitle style="text-align: right"
            >5,000원</v-list-item-subtitle
          >
        </v-list-item-content>
      </v-card>
      <v-card outlined tile>
        <v-list-item-content>
          <v-list-item-title> 스타벅스 </v-list-item-title>
          <v-list-item-subtitle style="text-align: right"
            >3,000원</v-list-item-subtitle
          >
        </v-list-item-content>
      </v-card>
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
  margin: 0 auto 0;
}
</style>
