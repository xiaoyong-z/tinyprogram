<template>
  <div class="wrapper">
    <!-- <div class="home-border"></div> -->
    <Tabs size="default" class="tab" v-model="tabName" :animated="false">
      <TabPane label="分析报告" name="report">
        <report-name
          :impr="impr"
          :tiaoli="tiaoli"
          :contentList="contentList"
          :oneCase="oneCase"
          @changeTab="updateTab"
        >
        </report-name>
          <!-- :accu="accu"
          :accu_prob="accu_prob"
          :accu_rele="accu_rele"
          :seriesData="seriesData" -->
      </TabPane>
      <!-- <TabPane label="刑期预测">
        <report-punishment :list="impr"></report-punishment>
      </TabPane> -->
      <TabPane label="相关法规" name="law">
        <report-law :tiaoli="tiaoli" :contentList="contentList"></report-law>
      </TabPane>
      <TabPane label="相似案例" name="case">
        <case-list :accu_rele="accu_rele"></case-list>
      </TabPane>
    </Tabs>
  </div>
</template>

<script>
import ReportName from './nameAndCase'
import ReportLaw from './law'
// import axios from 'axios'
import CaseList from './caseList'
import ReportPunishment from './reportPunishment'
export default {
  name: 'DecisionTab',
  components: {
    ReportName,
    ReportLaw,
    CaseList,
    ReportPunishment
    // Login,
    // Register
  },
  props: {
    accu_rele: Array,
    impr: Array,
    contentList: Array,
    tiaoli: Array,
    oneCase: Object
  },
  data () {
    return {
      // fact: '',
      // accu: [],
      // // accu_rele: [],
      // seriesData: [],
      tabName: ''
    }
  },
  methods: {
    updateTab (name) {
      this.tabName = name
      this.toTop()
    },
    toTop () {
      document.body.scrollTop = 0 // 实现tab切换时自动在页面顶部
      document.documentElement.scrollTop = 0
    }
    // getAccusation () {
    //   axios.request({ // 向django发送请求
    //     url: 'http://35.226.111.16:8000/predict',
    //     method: 'post',
    //     data: this.fact
    //   }).then(this.getAccusationSuc)
    //     .catch((response) => {
    //       console.log(response)
    //     })
    // },
    // getAccusationSuc (res) {
    //   if (res && res.data) {
    //     // console.log('hi')
    //     // console.log(res.data)
    //     const data = res.data
    //     this.accu_rele = data.accu_rele
    //     this.accu = []
    //     data.accu.forEach((item, index) => {
    //       this.accu[index] = item + '罪'
    //     })
    //     let accuStr = JSON.stringify(this.accu)
    //     sessionStorage.setItem('accu', accuStr)
    //     this.seriesData = []
    //     if (data.accu_prob) { // 先判断是否存在，否则会出现无法读取未定义的accu_prob
    //       data.accu_prob.forEach((item, index) => {
    //         this.seriesData.push({
    //           value: parseFloat((item * 100).toFixed(1)),
    //           name: this.accu[index]
    //         })
    //       }) // 对概率做数据操作
    //     }
    //     let probStr = JSON.stringify(this.seriesData)
    //     sessionStorage.setItem('prob', probStr)
    //   }
    // }
  },
  // mounted () {
  //   this.fact = JSON.parse(sessionStorage.getItem('decisionFact'))
  //   // this.accu_rele = JSON.parse(sessionStorage.getItem('accu_rele'))
  //   // console.log(this.accu_rele)
  //   // console.log('t')
  // //   this.getAccusation()
  // },
  activated () {
    window.addEventListener('scroll', this.scrollToTop)
  },
  deactivated () {
    window.removeEventListener('scroll', this.scrollToTop)
  }
  // props: {
  //   accu: Array,
  //   accu_prob: Array,
  //   accu_rele: Array,
  //   seriesData: Array
  // }
}
</script>

<style lang="stylus" scoped>
  @import '~styles/variables.styl'
  .wrapper >>> .ivu-tabs-tab
    width 30%
    text-align center
    padding 8px 0
    color #666
  .wrapper >>> .ivu-tabs-tab-active
    color #333
    font-weight bold
  // .wrapper >>> .ivu-tabs-tab-focused
  //   width 20%
  //   float left
  .wrapper >>> .ivu-tabs-nav
    width 100%
    padding .1rem
  //   margin-top $headerHeight
  // .wrapper >>> .ivu-tabs-bar
  // .wrapper >>> .ivu-tabs-nav-container
  // .wrapper >>> .ivu-tabs-nav-scroll
  // .wrapper >>> .ivu-tabs-nav-wrap
  //   width 100%
  // .wrapper >>> .ivu-tabs-tab // tab
  .wrapper >>> .ivu-tabs-ink-bar
    width 30% !important
    // left .5%
  //   color #9a9a9b
  //   width 20% !important
  //   width 25%
  // .wrapper >>> .ivu-tabs-tab-active // activetab
  //   font-size .36rem
  //   color #333
  .wrapper >>> .ivu-tabs-bar // 除去下划线
    border-bottom 0
    margin-bottom 0
    // margin-left .2rem
  .wrapper
    margin-top $headerHeight
    background-color: #FFF
    .home-border
      width 100%
      height 1rem
    // .tab
    //   margin-top -.8rem
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
