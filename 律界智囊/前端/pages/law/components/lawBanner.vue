<template>
  <div class="banner border-bottom">
    <i>{{this.title}}</i>
    <i class="iconfont banner-icon">&#xe7ab;</i>
    <router-link
      tag="i"
      class="banner-bigTitle"
      to="/detail/0003"
     >
      {{bigTitle}}
   </router-link>
  </div>
</template>

<script> // 该部分实现类似导航功能
export default {
  name: 'LaWBanner',
  props: {
    title: String,
    bigTitle: String
  }
}
</script>

<style lang="stylus" scoped>
  @import '~styles/variables.styl'
  @import '~styles/mixins.styl'
  .banner
    margin-top $headerHeight
    height 1rem
    line-height 1rem
    padding .1rem .32rem
    font-size .32rem
    background-color: #FFF
    ellipsis()
    .banner-icon
      height .4rem
      line-height .4rem
      margin .05rem
      color #666
      text-align center
      font-size .38rem
    .banner-bigTitle
      color #c0b5b5f5
</style>
