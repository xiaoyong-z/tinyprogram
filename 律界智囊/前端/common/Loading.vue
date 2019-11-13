<template>
  <div class="wraper">
    <Spin fix v-show="isSpinShow">
      <Icon type="ios-loading" size=30 class="demo-spin-icon-load"></Icon>
      <div>Loading...</div>
    </Spin>
  </div>
</template>

<script>
export default {
  name: 'Loading',
  props: {
    isSpinShow: Boolean
  }
}
</script>

<style lang="stylus" scoped>
  .wraper
    background-color: #eee
    /* 旋转效果 */
    .demo-spin-icon-load
      animation: ani-demo-spin 1s linear infinite
    @keyframes ani-demo-spin
      from { transform: rotate(0deg)}
      50% { transform: rotate(180deg)}
      to { transform: rotate(360deg)}
</style>
