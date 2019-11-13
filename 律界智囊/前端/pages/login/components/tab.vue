<template>
  <div class="wrapper">
    <!-- <div class="home-border"></div> -->
    <div class="wrapper-bgc">
      <Tabs size="default" class="tab" v-model="tabName">
        <TabPane label="登录" name="login">
          <login></login>
        </TabPane>
        <TabPane label="注册" name="register">
          <register @changeTab="updateTab"></register>
        </TabPane>
      </Tabs>
    </div>
  </div>
</template>

<script>
import Login from './login'
import Register from './register'
export default {
  name: 'LoginTab',
  components: {
    Login,
    Register
  },
  data () {
    return {
      tabName: ''
    }
  },
  methods: {
    updateTab () {
      this.tabName = 'login'
    }
  }
}
</script>

<style lang="stylus" scoped>
  .wrapper >>> .ivu-tabs-tab // tab
    color #9a9a9b
    font-size .28rem
  .wrapper >>> .ivu-tabs-tab-active // activetab
    font-size .36rem
    color #333
  .wrapper >>> .ivu-tabs-bar // 除去下划线
    border-bottom 0
    margin-left .2rem
  .wrapper
    height 100%
    margin-top .3rem
    // .home-border
    //   width 100%
    //   height 1rem
    //   border-top .02rem solid #ccc
    //   border-radius .4rem
    .wrapper-bgc
      background-color: #FFF
      .tab
        background-color: #FFF
        border-top .02rem solid #ccc
        border-radius .4rem
        margin -.8rem .1rem .1rem .1rem
    .myTab
      width 100%
      height 1.2rem
      line-height 1.2rem
      background-color: #f4f4f4ba
      .normal
        display inline-block
        font-size .28rem
        color #999
        width 20%
        text-align center
      .active
        font-size .4rem
        color #333
        font-weight 530
    .tab-con
      background-color: #FFF
</style>
