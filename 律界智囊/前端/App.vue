<template>
  <div id="app">
    <transition
      name="fade"
      :duration="{enter: 100, leave: 50}"
      mode="out-in"
      enter-active-class="animated fadeIn fade-enter-active"
    >
      <!-- leave-active-class="animated fadeOutLeft fade-leave-active" -->
      <!-- :duration="{enter: 1000, leave: 50}" -->
      <!-- name="fade" -->
      <keep-alive exclude="BaikeDetail"> <!-- 和router-view之间不能隔任何东西 -->
      <router-view></router-view>
      </keep-alive>
    </transition>
  </div>
</template>

<script>
export default {
  name: 'App'
}
</script>

<style lang="stylus">
  @import './assets/styles/animate.css'
  @import '~styles/mixins.styl'
  // #app
  //   height 100%
  button
    font-family: Arial, "Microsoft Yahei", "Helvetica Neue", Helvetica, sans-serif !important
  input // 改变组件中的字体
    font-family: Arial, "Microsoft Yahei", "Helvetica Neue", Helvetica, sans-serif !important
    height .72rem !important
  .ivu-divider-horizontal // divider
    margin-top .22rem !important
  .ivu-divider
  .ivu-divider-vertical
    margin 0 .06rem
  .ivu-breadcrumb
    ellipsis()
  .ivu-input // 搜索框字体
    font-size .28rem
  .ivu-input-icon
    width .72rem
    height .72rem
    line-height .72rem
  .ivu-select-item // 搜索框下拉字体
    font-size .265rem !important
  .ivu-btn-success // 按钮可点击状态的颜色
    background-color: #5db1ff
    border-color #5db1ff
  // .ivu-input // 决策部分的输入框
  //   background-color: #f8f8f896
  .ivu-back-top-inner // 回到顶部的样式
    height .8rem
    width .8rem
    border-radius 50% !important
    i
      padding .16rem !important
  .fade-enter, // 过渡动画
  .fade-leave-to
    opacity 0
  .fade-enter-active,
  .fade-leave-active
    transition: opacity .5s
  .vertical-center-modal // 会话框居中
    display: flex
    align-items: center
    justify-content: center
    .ivu-modal-footer
      height 1rem
      padding .2rem
    .ivu-modal
      top 0
      .ivu-modal-body
        height 1.6rem
        font-size .3rem
        line-height 1rem
  .ivu-collapse-header // 修改折叠板样式
    height 1.3rem !important
    line-height 1.3rem !important
  .ivu-drawer-header // 修改菜单栏中的样式
    height 1.2rem
    line-height 1.2rem
    padding .15rem .4rem
    .ivu-drawer-header-inner
      font-size .38rem
  .ivu-drawer-body // 个人主页样式
    padding 0
    .user-info
      position relative
      height 2.6rem
      .user-img
        width 100%
        height 0
        padding-bottom 58.6%
        overflow hidden
        z-index 1
        img
          opacity .8
          width 100%
      .user-up
        width 100%
        position absolute
        top 0
        left 0
        right 0
        display flex
        .user-avatar
          position relative
          z-index 2
          width 1.6rem
          height 1.6rem
          border-radius 50%
          overflow hidden
          margin .2rem
          float left
          background-color: #f6e2dcd1
          .user-icon
            position absolute
            top .22rem
            left .22rem
            color #666565
        .user-login
        .user-name
          z-index 2
          flex 1
          padding .6rem .44rem
          font-size .35rem
          height 1rem
          line-height 1rem
          color #333
    .user-history
    .user-logout
      display flex
      margin-top .3rem
      height 1rem
      padding .2rem
      background-color: #fcf6f485
      .history-icon
      .logout-icon
        float left
        width 1rem
      .history-text
      .logout-text
        flex 1
        padding-left .2rem
        font-size .3rem
        height .6rem
        line-height .6rem
</style>
