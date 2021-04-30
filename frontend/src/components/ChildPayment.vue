<template>
  <section>
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
</style>
