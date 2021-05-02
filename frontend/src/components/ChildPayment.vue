<template>
  <section class="content">
    <!-- <div class="barcode">
      <VueBarcode
        :value="barcodeValue"
        height="80"
        width="3"
        displayValue="false"
        lineColor="#0aa"
      >
        Show this if the rendering fails.
      </VueBarcode>
    </div> -->
    <!-- <p style="text-align: center">결제내역</p> -->
    <!-- <div class="paymentDetail"> -->
      <Payment
      class="Payment"
      :payment="payment"
        v-for="(payment, i) in payments"
        :key="i"
      >
       
      </Payment>
    <!-- </div> -->
  </section>
</template>

<script>
// import VueBarcode from "vue-barcode";
import Payment from './Payment.vue';

export default {
  data() {
    return {
      barcodeValue: "string",
      payments:[
              {
                location : 'OOpc방',
                price : 5000,
                time : '2021-03-21 16:03:54'
              },
              {
                location : '김밥천국',
                price : 4500,
                time : '2021-03-21 18:44:07'
              },
              {
                location : 'OOpc방',
                price : 5000,
                time : '2021-03-21 16:03:54'
              },
              {
                location : '김밥천국',
                price : 4500,
                time : '2021-03-21 18:44:07'
              },
              {
                location : 'OOpc방',
                price : 5000,
                time : '2021-03-21 16:03:54'
              },
              {
                location : '김밥천국',
                price : 4500,
                time : '2021-03-21 18:44:07'
              },
              {
                location : '김밥천국',
                price : 4500,
                time : '2021-03-21 18:44:07'
              }
            ]
    };
  },
  components: {
    // VueBarcode,
    Payment
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
.Payment{
    height: 18%;
    width: 100%;
    border-bottom: 1px rgb(95, 95, 95);
    margin-bottom: 12px;
    box-shadow: 0px 1px 0px #0000001f;
    padding: 10px;
}
</style>
