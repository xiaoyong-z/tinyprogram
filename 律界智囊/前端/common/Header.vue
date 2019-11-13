<template>
  <div class="header"> <!-- 为了页面向下滚动时header部分不动 -->
    <!-- <div class="header-abs" v-show="showfixed" @click="handleClick">
      <div class="iconfont header-abs-back">&#xe622;</div>
    </div> -->
    <!-- <div class="header-show" -->  <!-- v-show="!showfixed" :style="opacityStyle" -->
    <div class="header-left">
      <div class="iconfont back-icon" @click="handleClick">&#xe622;</div>
    </div>
    <div class="header-middle">{{title}}</div>
    <router-link
      tag="div"
      class="header-right"
      to="/"
     >
      <div class="iconfont home-icon">&#xe61e;</div>
    </router-link>
    <!-- </div> -->
  </div>
</template>

<script> // 除主页的header
export default{
  name: 'Header',
  props: {
    title: String
  },
  methods: {
    handleClick () {
      this.$router.go(-1) // 返回上一页
    }
    // handleScroll () {
    //   const top = document.documentElement.scrollTop // 获得距离页面顶部的距离
    //   if (top > 56) {
    //     let opacity = top / 140 // 通过修改透明度增加渐隐渐现的效果
    //     opacity = opacity > 1 ? 1 : opacity
    //     this.opacityStyle = { opacity }
    //     this.showfixed = false
    //   } else {
    //     this.showfixed = true
    //   }
    // }
  }
  // activated () { // keep-alive组件激活时调用，并在加载页面时绑定
  //   window.addEventListener('scroll', this.handleScroll) // 当滑动到一定距离时使header-abs隐藏
  // },
  // deactivated () { // 页面更新时解绑,防止所有页面都添加scroll事件
  //   window.removeEventListener('scroll', this.handleScroll)
  // }
}
</script>

<style lang="stylus" scoped>
  @import '~styles/variables.styl'
  // .headerfixed
  //   position fixed
  //   top 0
  //   width 100%
  //   z-index 10
  .header
    display flex
    position fixed
    top 0
    width 100%
    z-index 100
    height $headerHeight
    line-height: $headerHeight
    background-color: $bgColor
    color: #fff
    box-shadow 0 .05rem .1rem #8b8b8bd6
  // .header-abs
  //   z-index 10
  //   position absolute
  //   left .2rem
  //   top .2rem
  //   width .8rem
  //   height .8rem
  //   line-height .8rem
  //   text-align center
  //   border-radius .4rem
  //   background rgba(0, 0, 0, .8)
  //   .header-abs-back
  //     color #fff
  //     font-size .4rem
  // .header-show
  //   z-index 2
  //   display flex
  //   overflow hidden
  //   position fixed
  //   top 0
  //   left 0
  //   right 0
  //   background-color: $bgColor
  //   height $headerHeight
  //   line-height: $headerHeight
  //   color: #fff
    .header-left
      width: .86rem
      float: left
      .back-icon
        font-size: .42rem
        text-align: center
    .header-middle
      flex: 1
      font-size: .42rem
      font-weight: 400
      text-align: center
    .header-right
      width: .86rem
      padding 0 .1rem
      float: right
      text-align center
      color #fff // 去除使用router-link后字体颜色的变化
      .home-icon
        font-size: .6rem
</style>
