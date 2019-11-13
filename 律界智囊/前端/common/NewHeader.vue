<template>
  <div class="header border-bottom"> <!-- 为了页面向下滚动时header部分不动 -->
    <div class="header-left">
      <div class="iconfont back-icon" @click="handleClick">&#xe62c;</div>
    </div>
    <div class="header-middle" v-show="!showAbs" :style="opacityStyle">{{title}}</div>
    <router-link
      tag="div"
      class="header-right"
      to="/"
      v-show="!showAbs"
      :style="opacityStyle"
     >
      <div class="iconfont home-icon">&#xe61e;</div>
    </router-link>
    <!-- </div> -->
  </div>
</template>

<script> // 除主页的header
export default{
  name: 'DetailHeader',
  props: {
    title: String
  },
  data () {
    return {
      showAbs: true,
      opacityStyle: {
        opacity: 0
      }
    }
  },
  methods: {
    handleClick () {
      this.$router.go(-1) // 返回上一页
    },
    handleScroll () {
      const top = document.documentElement.scrollTop // 获得距离页面顶部的距离
      if (top > 60) {
        let opacity = top / 140 // 通过修改透明度增加渐隐渐现的效果
        opacity = opacity > 1 ? 1 : opacity
        this.opacityStyle = { opacity }
        this.showAbs = false
      } else {
        this.showAbs = true
      }
    }
  },
  mounted () { // 由于在App.vue中的keep-alive组件中排除了Detail部分，导致上面的两个钩子不再该组件存在，所以要借助这两个钩子实现同样的效果
    window.addEventListener('scroll', this.handleScroll)
  },
  unmounted () {
    window.removeEventListener('scroll', this.handleScroll)
  }
}
</script>

<style lang="stylus" scoped>
  @import '~styles/variables.styl'
  @import '~styles/mixins.styl'
  .header
    display flex
    position fixed
    top 0
    width 100%
    z-index 100
    height $headerHeight
    line-height $headerHeight
    // background-color: $bgColor
    background-color: #FFF
    color: #fff
    // box-shadow 0 .05rem .1rem #8b8b8bd6
    .header-left
      width: .86rem
      float: left
      color #333
      .back-icon
        font-size: .48rem
        text-align: center
    .header-middle
      flex: 1
      font-size: .3rem
      // font-weight: 400
      padding-left .2rem
      color #333
      ellipsis()
    .header-right
      width: 1rem
      padding 0 .1rem
      float: right
      text-align center
      color #333 // 去除使用router-link后字体颜色的变化
      .home-icon
        font-size: .6rem
</style>
