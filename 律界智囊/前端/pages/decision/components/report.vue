<template>
  <div class="report"> <!-- 使用组件时最外层必须包裹一个div -->
    <report-header :title="title"></report-header>
    <!-- <report-name
      v-if="accuSucc"
      :accu="accu"
      :accu_prob="accu_prob"
      :accu_rele="accu_rele"
    >
    </report-name>
    <report-punishment :list="impr"></report-punishment> -->
    <loading :isSpinShow="isSpinShow"></loading>
    <!-- <ul>
      <li
        class="item"
        v-for="item of list"
        :key="item.id"
      > --> <!-- 跳转到detail页面，并防止router-link使字体变色 -->
        <!-- <div class="item-info">
          <p class="item-title">{{item.title}}</p>
          <div class="item-border"></div>
          <p class="item-desc">{{item.desc}}</p>
        </div>
      </li>
    </ul> -->
    <decision-tab
      :accu_rele="accu_rele"
      :impr="impr"
      :contentList="contentList"
      :tiaoli="tiaoli"
      :oneCase="oneCase"
    >
    </decision-tab>
      <!-- :accu="accu"
      :accu_prob="accu_prob"
      :accu_rele="accu_rele"
      :seriesData="seriesData" -->
    <!-- <report-law :lawList="tiaoli" :probList="tiaoli_prob"></report-law> -->
    <!-- <report-case :caseList="caseList"></report-case> -->
  </div>
</template>

<script>
import axios from 'axios'
import ReportHeader from 'common/fixHeader'
import ReportLaw from './law'
import ReportName from './nameAndCase'
import ReportPunishment from './reportPunishment'
import DecisionTab from './tab'
import Loading from 'common/Loading'
export default {
  name: 'DecisionReport',
  components: {
    ReportHeader,
    ReportLaw,
    ReportName,
    ReportPunishment,
    Loading,
    DecisionTab
  },
  data () {
    return {
      fact: '',
      title: '分析报告',
      isSpinShow: false,
      accu: [], // 罪名
      accu_prob: [], // 罪名概率
      accu_rele: [], // 每个罪名相关案例
      impr: [], // 刑期
      tiaoli: [], // 法条
      // tiaoli_prob: [], // 法条概率
      // count: 0,
      // accuSucc: false,
      seriesData: [],
      oneCase: {}, // 报告中的预览
      contentList: [] // 法条具体内容
    }
  },
  methods: {
    getReportInfo () {
      if (this.isSpinShow === false) {
        this.isSpinShow = true
        this.getAccusation()
        // this.getImpr()
        // this.getLaw()
        // if (this.count === 3) {
        //   this.isSpinShow = false
        // }
        // axios.request({ // 向django发送请求
        //   url: 'http://148.70.210.143:8050/predict',
        //   method: 'post',
        //   data: this.$route.params.fact
        // }).then(this.getReportInfoSuc)
        //   .catch((response) => {
        //     console.log(response)
        //   })
      }
    },
    // getLaw () {
    //   axios.request({ // 向django发送请求
    //     url: 'http://47.101.221.46:8050/predict',
    //     method: 'post',
    //     data: this.$route.params.fact
    //   }).then(this.getLawSuc)
    //     .catch((response) => {
    //       console.log(response)
    //     })
    // },
    // getLawSuc (res) {
    //   if (res && res.data) {
    //     const data = res.data
    //     this.tiaoli = data.tiaoli
    //     this.getLawContent()
    //     // this.isSpinShow = false
    //     // this.count = this.count + 1
    //   }
    // },
    getLawContent () {
      this.tiaoli.forEach((item, index) => {
        axios.request({ // 向django发送请求,获取推荐内容
          url: 'http://47.101.221.46:8050/xingfa',
          method: 'post',
          data: item
        }).then((res) => {
          const data = res.data[0]
          this.contentList.push(data.content) // 用push的话要在初始时置空
          // console.log(this.contentList[index])
        })
          .catch((response) => {
            console.log(response)
          })
      })
    },
    getAccusation () {
      axios.request({ // 向django发送请求
        url: 'http://47.101.221.46:8050/predict',
        method: 'post',
        data: this.$route.params.fact
      }).then(this.getAccusationSuc)
        .catch((response) => {
          console.log(response)
        })
    },
    getAccusationSuc (res) {
      if (res && res.data) {
        const data = res.data
        this.accu_rele = data.accu_rele // 获取accu
        this.oneCase = this.accu_rele[0][0]
        this.accu_rele.forEach((item, index) => {
          item.forEach((littleItem, littleIndex) => {
            littleItem['fact2'] = littleItem.fact.slice(0, 50)
          })
        })
        this.accu = []
        data.accu.forEach((item, index) => {
          this.accu[index] = item + '罪'
        })
        let accuStr = JSON.stringify(this.accu)
        sessionStorage.setItem('accu', accuStr)
        this.seriesData = []
        if (data.accu_prob) { // 先判断是否存在，否则会出现无法读取未定义的accu_prob
          data.accu_prob.forEach((item, index) => {
            this.seriesData.push({
              value: parseFloat((item * 100).toFixed(1)),
              name: this.accu[index]
            })
          }) // 对概率做数据操作
        }
        let probStr = JSON.stringify(this.seriesData)
        sessionStorage.setItem('prob', probStr)
        this.tiaoli = data.tiaoli // 获取law
        this.getLawContent()
        this.impr = data.impr // 获取刑期
        this.impr[0] = Math.ceil(this.impr[0])
        // this.accu = data.accu
        // this.seriesData = []
        // if (data.accu_prob) { // 先判断是否存在，否则会出现无法读取未定义的accu_prob
        //   data.accu_prob.forEach((item, index) => {
        //     this.seriesData.push({
        //       value: parseFloat((item * 100).toFixed(1)),
        //       name: this.accu[index]
        //     })
        //   }) // 对概率做数据操作
        // }
        // this.accu_rele = data.accu_rele
        // this.accuSucc = true
        this.isSpinShow = false
      }
    }
    // getImpr () {
    //   axios.request({ // 向django发送请求 3.16.128.130:8050
    //     url: 'http://35.201.136.253:8000/predict',
    //     method: 'post',
    //     data: this.$route.params.fact
    //   }).then(this.getImprSuc)
    //     .catch((response) => {
    //       console.log(response)
    //     })
    // },
    // getImprSuc (res) {
    //   if (res && res.data) {
    //     console.log(res.data)
    //     const data = res.data
    //     this.impr = data.impr
    //     // this.count = this.count + 1
    //     this.isSpinShow = false
    //   }
    // }
    // getReportInfoSuc (res) {
    //   console.log(res)
    //   // if (res.status === 200) {
    //   //   const data = res.data
    //   //   this.accu = data.accu
    //   //   data.accu_prob.forEach((item, index) => {
    //   //     this.accu_prob[index] = parseFloat((item * 100).toFixed(1))
    //   //   }) // 对概率做数据操作
    //   //   this.accu_rele = data.accu_rele
    //   //   this.impr = data.impr
    //   //   this.tiaoli = data.tiaoli
    //   //   data.tiaoli_prob.forEach((item, index) => {
    //   //     this.tiaoli_prob[index] = parseInt((item * 100))
    //   //   }) // 对概率做数据操作
    //   //   this.isSpinShow = false
    //   // }
    // },
    // test () {
    //   if (this.isSpinShow === false) {
    //     // this.isSpinShow = true
    //     axios.request({ // 向django发送请求
    //       url: 'http://148.70.210.143:8049/predict',
    //       method: 'post',
    //       data: this.$route.params.fact
    //     }).then(this.getReport)
    //       .catch((response) => {
    //         console.log(response)
    //       })
    //   }
    // },
    // getReport (res) {
    //   console.log(res)
    // }
  },
  mounted () {
    this.contentList = []
    this.isSpinShow = false
    this.fact = this.$route.params.fact
    // this.count = 0
    this.getReportInfo()
  },
  activated () { // 防止缓存后无法重新发送ajax
    // this.isSpinShow = false
    if (this.fact !== this.$route.params.fact) {
      this.fact = this.$route.params.fact
      // this.count = 0
      this.getReportInfo()
    }
  }
}
</script>

<style lang="stylus" scoped>
  @import '~styles/mixins.styl'
  /* 旋转效果 */
  .demo-spin-icon-load
    animation: ani-demo-spin 1s linear infinite
  @keyframes ani-demo-spin
    from { transform: rotate(0deg);}
    50% { transform: rotate(180deg);}
    to { transform: rotate(360deg);}
  .report
    background-color: #eee
    .item-title
      height .8rem
      line-height .8rem
      font-size .4rem
      font-weight 400
      padding-left .2rem
    .item-border
      itemborder()
    .item-desc
      margin .1rem 0
      padding 0 .2rem
      font-size .3rem
      line-height .5rem
  </style>
