<template>
  <div class="register">
    <!-- <login-header :title="title"></login-header> -->
    <Alert class="err" type="error" show-icon v-if="registerErrTip">用户名已存在</Alert>
    <Alert class="err" type="error" show-icon v-show="pswErrTip">两次密码不同</Alert>
    <Alert class="err" type="success" show-icon v-show="registerSucc">注册成功</Alert>
    <div class="register-name">
       <Input type="text" size="large" v-model="userName" placeholder="用户名" clearable/>
    </div>
    <div class="register-psw">
      <Input type="password" size="large" v-model="userPsw" placeholder="密码" clearable/>
    </div>
    <div class="register-second-psw">
      <Input type="password" size="large" v-model="confirmPsw" placeholder="确认密码" clearable/>
    </div>
    <div class="register-btn">
      <i-button type="primary" class="btn" :disabled="userName.length <= 0 || userPsw.length <= 0 || confirmPsw.length <= 0" long @click="register">注   册</i-button>
    </div>
  </div>
</template>

<script>
import axios from 'axios'
import Qs from 'qs' // 这种方法只能在chrome上有效果
export default {
  name: 'Register',
  data () {
    return {
      // title: '欢迎',
      userName: '',
      userPsw: '',
      confirmPsw: '',
      registerSucc: false,
      registerErrTip: false,
      pswErrTip: false,
      timer: null
    }
  },
  computed: {
    param () {
      return {
        'userName': this.userName,
        'userPsw': this.userPsw
      }
    }
  },
  methods: {
    register () { // 注册
      // const params = {
      //   'userName': this.userName,
      //   'userPsw': this.userPsw
      // }
      // if (this.userName.length <= 0 || this.userPsw.length <= 0 || this.confirmPsw.length <= 0) {
      //   this.clickErrTip = true
      // } else
      if (this.userPsw !== this.confirmPsw) {
        this.pswErrTip = true
      } else {
        // axios.post('/api/register', {
        //   userName: this.userName,
        //   userPsw: this.userPsw
        // })
        this.pswErrTip = false
        // const param = new URLSearchParams()
        // param.append('username', this.userName)
        // param.append('userPsw', this.userPsw)
        axios.request({ // 向django发送请求,获取推荐内容
          // headers: {
          //   'deviceCode': 'A95ZEF1-47B5-AC90BF3'
          // }, 不添加该语句直接用Qs是可以成功的
          url: 'http://47.101.221.46:8050/register',
          method: 'post',
          data: Qs.stringify(this.param)
        }).then(this.registerSuccessful)
          .catch((response) => {
            console.log(response)
          })
      }
    },
    registerSuccessful (res) {
      const data = res.data
      if (data.status === 1) {
        this.registerSucc = true
        this.registerErrTip = false
        if (this.timer) { // 做一个节流处理,提高性能
          clearTimeout(this.timer)
        }
        this.timer = setTimeout(() => {
          this.$emit('changeTab') // 转到登陆界面
        }, 500)
      } else {
        this.registerSucc = false
        this.registerErrTip = true
      }
    }
  },
  mounted () {
    this.userName = ''
    this.userPsw = ''
    this.confirmPsw = ''
  },
  activated () { // 页面缓存后清空
    this.userName = ''
    this.userPsw = ''
    this.confirmPsw = ''
    this.registerSucc = false
    this.timer = null
  }
}
</script>

<style lang="stylus" scoped>
  @import '~styles/variables.styl'
  @import '~styles/mixins.styl'
  .register
    padding .7rem
    .register-name
      width 100%
    .register-psw
      margin-top .8rem
    .register-second-psw
      margin-top .8rem
    .register-btn
      height 1rem
      margin-top 1rem
      .btn
        height .77rem
        font-size .33rem
        text-align center
    .register-btn
      top 8.1rem
    .err
      width 6rem
    .registerNotice
      height 1rem
      line-height 1rem
      text-align center
      font-size .25rem
    .registerNotice
      top 8.9rem
</style>
