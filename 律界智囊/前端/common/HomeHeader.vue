<template>
  <div class="header border-bottom">
    <Icon type="md-menu" class="header-left" size="35" @click="showMenu = true"/>
    <Drawer placement="left" :closable="false" v-model="showMenu">
        <div class="user-info">
          <div class="user-img">
            <img src="//bpic.588ku.com/back_pic/05/76/07/395be2cb7588f2a.jpg!/fh/300/quality/90/unsharp/true/compress/true">
          </div>
          <div class="user-up">
            <div class="user-avatar">
              <Icon class="user-icon"  type="md-person" size="58"/>
            </div>
            <div class="user-login" v-show="this.userName.length <= 0" @click="turnToLogin">点击登录
            </div>
            <div class="user-name" v-show="this.userName.length > 0">{{this.userName}}</div>
          </div>
        </div>
        <div v-show="this.userName.length > 0">
          <div class="user-history border-bottom">
            <Icon class="history-icon" size="30" type="ios-time-outline" />
            <div
              class="history-text"
              @click="turnToHistory"
            >
              我的足迹
            </div>
          </div>
          <div class="user-logout border-bottom">
            <Icon class="logout-icon" type="ios-power-outline" size="30" />
            <div class="logout-text" @click="logout = true">退出登录</div>
          </div>
        </div>
    </Drawer>
    <div class="header-middle">{{title}}</div>
    <router-link
      tag="div"
      class="header-right"
      to="/"
     >
      <div class="iconfont home-icon">&#xe61e;</div>
    </router-link>
    <Modal
      v-model="logout"
      class-name="vertical-center-modal"
      @on-ok="ok"
    > <!-- 会话框 -->
      <p>确认要退出登录吗？</p>
    </Modal>
  </div>
</template>

<script> // 主页部分的个人主页
import { mapState, mapMutations } from 'vuex' // vuex高级一些的API
export default{
  name: 'HomeHeader',
  props: {
    title: String
  },
  data () {
    return {
      showMenu: false,
      logout: false
    }
  },
  methods: {
    turnToLogin () {
      this.showMenu = false
      this.$router.push('/login')
    },
    turnToHistory () {
      this.showMenu = false
      this.$router.push('/history')
    },
    ok () { // 退出
      this.setName('')
      this.setHistory([])
      this.showMenu = false
    },
    ...mapMutations(['setName']), // 该方法相当于commit一个请求
    ...mapMutations(['setHistory']) // 该方法相当于commit一个请求
  },
  computed: {
    ...mapState(['userName']) // 将vuex公用数据映射给计算属性并命名为city,用this.city取代html中this.$store.state.city
  }
}
</script>

<style lang="stylus" scoped>
  @import '~styles/variables.styl'
  .header
    display flex
    position fixed
    top 0
    width 100%
    z-index 100
    height $headerHeight
    line-height: $headerHeight
    background-color: $bgColor
    color #fff
    box-shadow 0 .05rem .1rem #8b8b8bd6
    .header-left
      width: .86rem
      float: left
      padding .2rem .1rem
      text-align: center
    .header-middle
      flex: 1
      font-size: .46rem
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
