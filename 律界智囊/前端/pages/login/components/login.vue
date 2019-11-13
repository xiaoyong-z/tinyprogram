<template>
  <div class="login">
    <Alert class="err" type="error" show-icon v-if="loginErrTip">用户名或密码错误</Alert>
    <Alert class="err" type="success" show-icon v-show="loginSucc">登陆成功</Alert>
    <div class="login-name">
       <Input type="text" size="large" v-model="userName" placeholder="用户名" clearable/>
    </div>
    <div class="login-psw">
      <Input type="password" size="large" v-model="userPsw" placeholder="密码" clearable/>
    </div>
    <div class="login-btn">
      <i-button type="primary" class="btn" :disabled="userName.length <= 0 || userPsw.length <= 0" long @click="login">登   录</i-button>
    </div>
  </div>
</template>

<script>
import axios from 'axios'
import { mapMutations } from 'vuex' // vuex高级一些的API
import Qs from 'qs' // 这种方法只能在chrome上有效果
export default {
  name: 'Login',
  data () {
    return {
      userName: '',
      userPsw: '',
      loginSucc: false,
      loginErrTip: false,
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
    login () { // 登录
      // axios.post('http://148.70.210.143:8050/login', {
      //   userName: this.userName,
      //   userPsw: this.userPsw
      // }).
      // 使用上面这种方式会导致axios触发一个函数导致后端获取不到数据，这种也不起作用
      // const param = {
      //   'userName': this.userName,
      //   'userPsw': this.userPsw
      // }
      // if (this.userName.length <= 0 || this.userPsw.length <= 0) {
      //   this.clickErrTip = true
      // } else {
      // this.clickErrTip = false
      // const param = new URLSearchParams()
      // param.append('userName', this.userName)
      // param.append('userPsw', this.userPsw)
      axios.request({ // 向django发送请求,获取推荐内容
        // headers: {
        //   'deviceCode': 'A95ZEF1-47B5-AC90BF3',
        //   'Content-Type': 'application/x-www-form-urlencoded'
        // },
        // transformRequest: [function (data) {
        //   // 对 data 进行任意转换处理
        //   return Qs.stringify(data)
        // }],
        url: 'http://47.101.221.46:8050/login',
        method: 'post',
        data: Qs.stringify(this.param)
      }).then(this.loginSuccessful)
        .catch((response) => {
          console.log(response)
        })
      // }
    },
    loginSuccessful (res) {
      const data = res.data
      if (data.status === 1) {
        const userName = data.result.userName
        const history = data.result.history
        this.loginSucc = true // 提示登陆成功，该处有待改进
        this.loginErrTip = false
        this.setName(userName) // vuex
        this.setHistory(history) // vuex
        if (this.timer) { // 做一个节流处理,提高性能
          clearTimeout(this.timer)
        }
        this.timer = setTimeout(() => {
          this.$router.push('/') // 返回首页
        }, 500)
        this.timer = setTimeout(() => {
          this.loginSucc = false // 防止再次登陆时会有短暂的成功提示视觉效果
        }, 500)
      } else {
        this.loginSucc = false
        this.loginErrTip = true
      }
    },
    ...mapMutations(['setName']), // 该方法相当于commit一个请求
    ...mapMutations(['setHistory']) // 该方法相当于commit一个请求
  },
  mounted () {
    this.userName = ''
    this.userPsw = ''
  },
  activated () { // 页面缓存后清空
    this.userName = ''
    this.userPsw = ''
    this.loginSucc = false
    this.timer = null
  }
}
</script>

<style lang="stylus" scoped>
  @import '~styles/variables.styl'
  @import '~styles/mixins.styl'
  .login
    padding .7rem
    .login-name
      width 100%
    .login-psw
      margin-top .8rem
    .login-btn
      margin-top 1rem
      .btn
        height .77rem
        font-size .33rem
        text-align center
    .err
      width 6rem
    .loginNotice
      height 1rem
      line-height 1rem
      text-align center
      font-size .25rem
    // .loginNotice
    //   top 7.6rem
</style>
