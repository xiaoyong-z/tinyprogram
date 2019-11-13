<template>
  <div class="login">
    <login-header :title="title"></login-header>
    <div class="login-content">
      <div class="login-img">
        <img src="./login.png"/>
      </div>
      <tab></tab>
      <!-- <div class="login-name">
         <Input type="text" size="large" v-model="userName" placeholder="用户名" clearable/>
      </div>
      <div class="login-psw">
        <Input type="password" size="large" v-model="userPsw" placeholder="密码" clearable/>
      </div>
      <div class="login-second-psw" v-if="!showLogin">
        <Input type="password" size="large" v-model="confirmPsw" placeholder="确认密码" clearable/>
      </div>
      <Alert class="err" type="error" show-icon v-if="loginErrTip">用户名或密码错误</Alert>
      <Alert class="err" type="error" show-icon v-if="registerErrTip">用户名已存在</Alert>
      <Alert class="err" type="error" show-icon v-show="pswErrTip">两次密码不同</Alert>
      <Alert class="err" type="success" show-icon v-show="loginSucc">登陆成功</Alert>
      <Alert class="err" type="success" show-icon v-show="registerSucc">注册成功</Alert>
      <div class="login-btn" v-if="showLogin">
        <i-button type="success" class="btn" :disabled="userName.length <= 0 || userPsw.length <= 0" long @click="login">登   录</i-button>
      </div>
      <div class="register-btn" v-if="!showLogin">
        <i-button type="success" class="btn" :disabled="userName.length <= 0 || userPsw.length <= 0 || confirmPsw.length <= 0" long @click="register">注   册</i-button>
      </div>
      <div class="loginNotice" v-if="showLogin" @click="trunToRegister">没有账号？立即注册</div>
      <div class="registerNotice" v-if="!showLogin" @click="trunToLogin">已有账号？立即登陆</div> -->
    </div>
  </div>
</template>

<script>
// import axios from 'axios'
import LoginHeader from 'common/Header'
import Tab from './components/tab'
// import { mapMutations } from 'vuex' // vuex高级一些的API
// import Qs from 'qs' // 这种方法只能在chrome上有效果
export default {
  name: 'LoginHome',
  components: {
    LoginHeader,
    Tab
  },
  data () {
    return {
      title: '欢迎'
      // userName: '',
      // userPsw: '',
      // confirmPsw: '',
      // loginSucc: false,
      // registerSucc: false,
      // loginErrTip: false,
      // registerErrTip: false,
      // pswErrTip: false,
      // showLogin: true,
      // timer: null
      // clickErrTip: false
    }
  }
  // computed: {
  //   param () {
  //     return {
  //       'userName': this.userName,
  //       'userPsw': this.userPsw
  //     }
  //   }
  // }
  // methods: {
  //   login () { // 登录
  //     // axios.post('http://148.70.210.143:8050/login', {
  //     //   userName: this.userName,
  //     //   userPsw: this.userPsw
  //     // }).
  //     // 使用上面这种方式会导致axios触发一个函数导致后端获取不到数据，这种也不起作用
  //     // const param = {
  //     //   'userName': this.userName,
  //     //   'userPsw': this.userPsw
  //     // }
  //     // if (this.userName.length <= 0 || this.userPsw.length <= 0) {
  //     //   this.clickErrTip = true
  //     // } else {
  //     // this.clickErrTip = false
  //     // const param = new URLSearchParams()
  //     // param.append('userName', this.userName)
  //     // param.append('userPsw', this.userPsw)
  //     axios.request({ // 向django发送请求,获取推荐内容
  //       // headers: {
  //       //   'deviceCode': 'A95ZEF1-47B5-AC90BF3',
  //       //   'Content-Type': 'application/x-www-form-urlencoded'
  //       // },
  //       // transformRequest: [function (data) {
  //       //   // 对 data 进行任意转换处理
  //       //   return Qs.stringify(data)
  //       // }],
  //       url: 'http://148.70.210.143:8050/login',
  //       method: 'post',
  //       data: Qs.stringify(this.param)
  //     }).then(this.loginSuccessful)
  //       .catch((response) => {
  //         console.log(response)
  //       })
  //     // }
  //   },
  //   loginSuccessful (res) {
  //     const data = res.data
  //     if (data.status === 1) {
  //       const userName = data.result.userName
  //       const history = data.result.history
  //       this.loginSucc = true // 提示登陆成功，该处有待改进
  //       this.loginErrTip = false
  //       this.setName(userName) // vuex
  //       this.setHistory(history) // vuex
  //       if (this.timer) { // 做一个节流处理,提高性能
  //         clearTimeout(this.timer)
  //       }
  //       this.timer = setTimeout(() => {
  //         this.$router.push('/') // 返回首页
  //       }, 500)
  //       this.timer = setTimeout(() => {
  //         this.loginSucc = false // 防止再次登陆时会有短暂的成功提示视觉效果
  //       }, 500)
  //     } else {
  //       this.loginSucc = false
  //       this.loginErrTip = true
  //     }
  //   },
  //   ...mapMutations(['setName']), // 该方法相当于commit一个请求
  //   ...mapMutations(['setHistory']), // 该方法相当于commit一个请求
  //   trunToRegister () {
  //     this.userName = ''
  //     this.userPsw = ''
  //     this.confirmPsw = ''
  //     this.showLogin = false
  //     this.loginErrTip = false
  //     this.title = '注册界面'
  //   },
  //   register () { // 注册
  //     // const params = {
  //     //   'userName': this.userName,
  //     //   'userPsw': this.userPsw
  //     // }
  //     // if (this.userName.length <= 0 || this.userPsw.length <= 0 || this.confirmPsw.length <= 0) {
  //     //   this.clickErrTip = true
  //     // } else
  //     if (this.userPsw !== this.confirmPsw) {
  //       this.pswErrTip = true
  //     } else {
  //       // axios.post('/api/register', {
  //       //   userName: this.userName,
  //       //   userPsw: this.userPsw
  //       // })
  //       this.pswErrTip = false
  //       // const param = new URLSearchParams()
  //       // param.append('username', this.userName)
  //       // param.append('userPsw', this.userPsw)
  //       axios.request({ // 向django发送请求,获取推荐内容
  //         // headers: {
  //         //   'deviceCode': 'A95ZEF1-47B5-AC90BF3'
  //         // }, 不添加该语句直接用Qs是可以成功的
  //         url: 'http://148.70.210.143:8050/register',
  //         method: 'post',
  //         data: Qs.stringify(this.param)
  //       }).then(this.registerSuccessful)
  //         .catch((response) => {
  //           console.log(response)
  //         })
  //     }
  //   },
  //   registerSuccessful (res) {
  //     const data = res.data
  //     if (data.status === 1) {
  //       this.registerSucc = true
  //       this.registerErrTip = false
  //       if (this.timer) { // 做一个节流处理,提高性能
  //         clearTimeout(this.timer)
  //       }
  //       this.timer = setTimeout(() => {
  //         this.trunToLogin() // 转到登陆界面
  //       }, 500)
  //     } else {
  //       this.registerSucc = false
  //       this.registerErrTip = true
  //     }
  //   },
  //   trunToLogin () {
  //     this.userName = ''
  //     this.userPsw = ''
  //     this.showLogin = true
  //     this.pswErrTip = false
  //     this.registerErrTip = false
  //     this.registerSucc = false
  //     this.title = '登录界面'
  //   }
  // },
  // mounted () {
  //   this.userName = ''
  //   this.userPsw = ''
  //   this.confirmPsw = ''
  // },
  // activated () { // 页面缓存后清空
  //   this.userName = ''
  //   this.userPsw = ''
  //   this.confirmPsw = ''
  //   this.loginSucc = false
  //   this.registerSucc = false
  //   this.timer = null
  //   // this.clickErrTip = false
  // }
}
</script>

<style lang="stylus" scoped>
  @import '~styles/variables.styl'
  @import '~styles/mixins.styl'
  .login
    background-color: #fff
    height 100%
    .login-content
      position relative
      margin-top 1.5rem
      height 8rem
      .login-img
        margin-top -.5rem
        width 100%
        height 0
        padding-left 1.7rem
        padding-bottom 50%
        overflow hidden
        img
          width 70%
    // .login-name
    //   position absolute
    //   top 4rem
    //   left .6rem
    //   right .6rem
    // .login-psw
    //   position absolute
    //   top 5.3rem
    //   left .6rem
    //   right .6rem
    // .login-second-psw
    //   position absolute
    //   top 6.6rem
    //   left .6rem
    //   right .6rem
    // .login-btn
    // .register-btn
    //   position absolute
    //   left .6rem
    //   right .6rem
    //   height 1rem
    //   .btn
    //     height .77rem
    //     font-size .33rem
    //     text-align center
    // .login-btn
    //   top 6.8rem
    // .register-btn
    //   top 8.1rem
    // .err
    //   position absolute
    //   top 3rem
    //   left .6rem
    //   width 6rem
    // .loginNotice
    // .registerNotice
    //   position absolute
    //   left .6rem
    //   right .6rem
    //   height 1rem
    //   line-height 1rem
    //   text-align center
    //   font-size .25rem
    // .loginNotice
    //   top 7.6rem
    // .registerNotice
    //   top 8.9rem
</style>
