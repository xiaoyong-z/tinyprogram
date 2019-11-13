<template>
  <div class="wraper">
    <img class="swiper-img" :src=imgUrl>
  </div>
</template>

<script> // 首页图
export default {
  name: 'Pictures',
  props: {
    imgUrl: String
  }

}
</script>

<style lang="stylus" scoped>
  @import '~styles/variables.styl'
  .wraper // 防止页面加载时有明显跳动
    margin-top $headerHeight
    overflow hidden
    width 100%
    height 0
    padding-bottom 57%
    background-color #ccc
    .swiper-img
      width 100%
</style>
