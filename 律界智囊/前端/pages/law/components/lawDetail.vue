<template>
  <div class="container">
    <!-- @touchstart="handleTouchStart"
    @touchmove="handleTouchMove"
    @touchend="handleTouchEnd" -->
    <law-header :title="chinese_name"></law-header>
    <div class="law">
      <div class="law-special">
        <div class="law-title">{{this.chinese_name}}</div>
        <div class="law-info">
            <div class="info-department">
              <span>【发布部门】</span> {{this.department}}
            </div>
            <div class="info-issue">
              <span>【发文字号】</span> {{this.store_issue}}
            </div>
            <div class="info-time">
              <div class="time-start">
                <span>【发布日期】</span>  {{this.start_date}}
              </div>
              <div class="time-exec"><span>【实施日期】</span>  {{this.exec_date}}</div>
            </div>
            <div class="info-eff">
              <div class="effective"><span>【时效性】</span>  {{this.effective}}</div>
              <div class="level"><span>【效力级别】</span>  {{this.level}}</div>
            </div>
        </div>
      </div>
      <div class="law-text">
        <div class="law-detail" v-for="(value, key, index) of article" :key="index">
          <!-- <div class="law-detail-title">正文</div> v-for="(value, key, index) in article"-->
          <div v-if="article" class="law-one">
            <span>第{{index+1}}条</span>    &ensp; {{article[key]}} <!-- 顺序有问题 -->
          </div>
        </div>
      </div>
    </div>
    <BackTop :height="100" :bottom="50" :right="15"></BackTop>
    <loading :isSpinShow="isSpinShow"></loading>
  </div>
</template>

<script> // 二级标题
import axios from 'axios'
import LawHeader from 'common/NewHeader'
import Loading from 'common/Loading'
export default {
  name: 'LawDetail',
  components: {
    LawHeader,
    Loading
  },
  data () {
    return {
      headerTitle: '法条库',
      lastId: '',
      isSpinShow: false,
      chinese_name: '',
      department: '',
      store_issue: '',
      start_date: '',
      exec_date: '',
      effective: '',
      level: '',
      article: {},
      articleArr: []
      // touchStatus: false,
      // startY: 0
    }
  },
  methods: {
    // handleTouchStart () { // 以下三个函数借助字母距离顶部的位置判断字母并定位到该字母对应的list
    //   this.touchStatus = true
    // },
    // handleTouchMove (e) {
    //   if (this.touchStatus) {
    //     console.log(e.touches[0].clientY)
    //   //   if (this.timer) {
    //   //     clearTimeout(this.timer)
    //   //   }
    //   //   this.timer = setTimeout(() => {
    //   //     const touchY = e.touches[0].clientY - 79
    //   //     const index = Math.floor((touchY - this.startY) / 20)
    //   //     if (index >= 0 && index < this.letters.length) {
    //   //       this.$emit('change', this.letters[index])
    //   //     }
    //   //   }, 16)
    //   }
    // },
    // handleTouchEnd () {
    //   this.touchStatus = false
    // },
    getDetailInfo () { // 获取具体法条
      if (this.isSpinShow === false) {
        this.isSpinShow = true
        axios.request({ // 向django发送请求,获取推荐内容
          url: 'http://47.101.221.46:8050/tiaoli2',
          method: 'post',
          data: this.$route.params.chinese_name
        }).then(this.getDetailInfoSucc)
          .catch((response) => {
            console.log(response)
          })
        // axios.get('/api/getLawDetail/' + this.$route.params.chinese_name
        // ).then(this.getDetailInfoSucc)
      }
    },
    getDetailInfoSucc (res) {
      if (res && res.data) {
        // console.log(res.data)
        const data = res.data[0]
        this.chinese_name = data.chinese_name
        this.department = data.department // 发布部门
        this.store_issue = data.store_issue // 发文字号
        this.start_date = data.start_date // 发布日期
        this.exec_date = data.exec_date // 实施日期
        this.effective = data.effective // 时效性
        this.level = data.level // 效力级别
        this.article = data.article // 法条
        this.articleArr = Object.keys(this.article)
        // this.articleArr.sort()
        // console.log(this.article)
        // console.log(this.articleArr)
        this.isSpinShow = false
      }
    }
  },
  mounted () {
    this.lastId = this.$route.params.chinese_name
    this.getDetailInfo()
  },
  activated () { // 当内容发生变化时要重新发送ajax请求
    if (this.lastId !== this.$route.params.chinese_name) {
      this.lastId = this.$route.params.chinese_name
      this.getDetailInfo()
    }
  }
}
</script>

<style lang="stylus" scoped>
  @import '~styles/variables.styl'
  @import '~styles/mixins.styl'
  .law span
    font-weight bold
  .container
    margin-top $headerHeight
    .law-special
      // border-top .02rem solid #999
      // // border-bottom .02rem solid #999
      // border-radius .2rem
      padding .1rem 0 .3rem 0
      .law-title
        padding-top .2rem
        min-height 1rem
        line-height .6rem
        text-align center
        font-size .33rem
        font-weight 550
      .law-info
        font-size $detailSize
        color #666
        // text-align center
        .info-department
        .info-issue
        .info-time
        .info-eff
          margin-top .05rem
          padding-left .2rem
          height .5rem
          line-height .5rem
        .info-time
        .info-eff
          display flex
          .time-start
          .time-exec
          .effective
          .level
            float left
            flex 1
    .law-text
      padding 0 .25rem
      border-top .02rem solid #999
      border-radius .2rem
      box-shadow 0 -.01rem .015rem #8b8b8bd6
      textJustify()
      .law-detail
        font-size .3rem
        text-indent 2em
        margin .3rem 0
        span
          font-weight bold
        .law-one
          line-height .53rem
  //   .item
  //     padding: .4rem
  //     line-height .5rem
</style>
