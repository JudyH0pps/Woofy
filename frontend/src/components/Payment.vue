<template>
  <div>
      <div class="float">
            {{payment.location}}
      </div>
      <div class="float">
            {{payment.price}}
      </div>
      <div class="float">
            {{payment.time}}
      </div>
  </div>
</template>

<script>
export default {
props: ['payment'],
}
</script>

<style>

</style>