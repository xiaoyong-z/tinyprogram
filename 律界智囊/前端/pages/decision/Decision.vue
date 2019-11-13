<template>
  <div class="decision">
    <decision-header :title="title"></decision-header>
    <!-- <div class="decision-input border-bottom">
        <textarea v-model="fact" class="decision-input-textarea" placeholder="请详细描述案情..."></textarea>
    </div> -->
    <div class="decision-wrapper">
      <div class="wrapper">
        <Input
          class="decision-input-textarea"
          v-model="fact"
          type="textarea"
          :rows="10"
          placeholder="请详细描述案情经过，以获得更位准确的预测结果"
        />
        <Alert type="error" show-icon v-if="showErr">请输入长度大于5</Alert>
        <p class="decision-notice">
          <span style="color: #5151f7">输入示例:   </span>
          <span style="color: #4e4d4df0">绍兴市越城区人民检察院指控：行贿2012年年初，被告人金2某欲以虚假外资企业、虚假高新技术项目的名义，向绍兴市袍某经济技术开发区管委会申请工业用地的土地使用权，在取得土地使用权后建造部分厂房用于非法转让获利。</span>
        </p>
      </div>
      <!-- <Spin fix v-show="isSpinShow">
        <Icon type="load-c" size="30" class="demo-spin-icon-load"></Icon>
        <div>Loading...</div>
      </Spin>
      <div class="decision-submit" @click="sendRequest">发送请求</div> -->
      <!-- <router-link
        tag="div"
        class="decision-submit"
        :to="'/report/' + this.fact"
      >
        提交
      </router-link> -->
      <!-- <div class="login-btn" v-if="showLogin">
        <i-button type="success" class="btn" :disabled="userName.length <= 0 || userPsw.length <= 0" long @click="login">登   录</i-button>
      </div> -->
      <div class="decision-submit">
        <i-button type="primary" class="btn" :disabled="fact.length <= 5" long @click="handleSubmit">提   交</i-button>
      </div>
    </div>
    <!-- <Alert type="error" show-icon >
      An error prompt
      <span slot="desc">
          Custom error description copywriting.
      </span>
    </Alert> -->
  </div>
</template>

<script>
// import axios from 'axios'
import DecisionHeader from 'common/fixHeader'
export default{
  name: 'Decision',
  components: {
    DecisionHeader
  },
  data () {
    return {
      title: '案情描述',
      fact: '',
      changeColor: false,
      showErr: false
      // isSpinShow: false,
      // accu: [], // 罪名
      // accu_prob: [], // 罪名概率
      // accu_rele: [], // 每个罪名相关案例
      // impr: [], // 刑期
      // tiaoli: [], // 法条
      // tiaoli_prob: [] // 法条概率
    }
  },
  methods: {
    handleSubmit () {
      let str = JSON.stringify(this.fact)// 把对象转化为字符串（stringify）存放进sessionStorage
      sessionStorage.setItem('decisionFact', str) // 使用localStorage没起作用，待解
      this.$router.push({path: '/report/' + this.fact})
    }
  }
}
</script>

<style lang="stylus" scoped>
  @import '~styles/variables.styl'
  .decision >>> .ivu_input
    background-color: #f3f3f3
  .decision >>> textarea.ivu-input
    font-family: Arial, "Microsoft Yahei", "Helvetica Neue", Helvetica, sans-serif
  .changeColor
    background-color: #19be6b
  .decision
    background-color: #f7f7f7c2
    height 100%
    .decision-wrapper
      height 100%
      // background-color: red
      .wrapper
        width 95%
        margin 0 auto
        margin-top $headerHeight
        // border 1px solid #dcdee2c2
        // border-radius .12rem
        padding .25rem
        .decision-input-textarea
          width 100%
          line-height .44rem
          font-size .3rem
        .decision-notice
          padding 0 .1rem
          letter-spacing .012rem
          line-height .38rem
          margin .4rem 0 .28rem 0
      .decision-submit
        position absolute
        bottom 1rem
        margin 0 .4rem
        width 90%
        height .95rem
        line-height .95rem
        .btn
          height .77rem
          font-size .33rem
          text-align center
          border-radius .12rem
  /* 旋转效果 */
  .demo-spin-icon-load
    animation: ani-demo-spin 1s linear infinite
  @keyframes ani-demo-spin
    from { transform: rotate(0deg)}
    50% { transform: rotate(180deg)}
    to { transform: rotate(360deg)}
</style>
