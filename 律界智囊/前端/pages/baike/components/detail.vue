<template>
  <div class="container">
    <law-header :title="articleTitle"></law-header>
    <div class="baike">
      <div class="baike-title">{{this.articleTitle}}</div>
      <div
        class="baike-labels"
        v-for="(item, index) of contentLabels"
        :key="index"
        :name="item"
      >
        <Tag class="lables-tag" color="primary">{{item.value}}</Tag>
      </div>
      <div class="baike-date">
        <!-- <p class="baike-hint">{{this.hitResult}}</p> -->
        <p class="date">{{this.publishDate}}</p>
      </div>
      <div
        class="baike-content"
        v-for="item of content"
        :key="item.articleId"
      >
        <!-- <p>&nbsp;</p> -->
        {{item}}
      </div>
      <!-- <div class="article-url">
        本文从无讼网站爬取，原文链接
        <a :href="this.articleUrl">{{this.articleUrl}}</a>
      </div> -->
    </div>
    <baike-recommend :recommendList="recommendList" @changeTitle="change"></baike-recommend>
    <BackTop :height="100" :bottom="50" :right="15"></BackTop>
    <loading :isSpinShow="isSpinShow"></loading>
  </div>
</template>

<script> // 二级标题
import { mapState, mapMutations } from 'vuex' // vuex高级一些的API
import axios from 'axios'
import LawHeader from 'common/NewHeader'
import BaikeRecommend from './recommend'
import Loading from 'common/Loading'
import Qs from 'qs'
export default {
  name: 'BaikeDetail',
  components: {
    LawHeader,
    Loading,
    BaikeRecommend
  },
  data () {
    return {
      headerTitle: '干货',
      lastId: '',
      isSpinShow: false,
      articleTitle: '',
      articleUrl: '',
      // imageUrl: '',
      contentLabels: [], // 标签
      publishDate: '',
      hitResult: '', // 出处
      content: [],
      recommendList: []
    }
  },
  methods: {
    change (Title, Index) {
      this.lastId = Title
      this.$route.params.articleTitle = Title
      this.setIndex(Index) // 设置vuex中的index
      this.getDetailInfo()
    },
    getDetailInfo () { // 获取具体干货
      if (this.isSpinShow === false) {
        this.isSpinShow = true
        axios.request({ // 向django发送请求,获取文章内容
          url: 'http://47.101.221.46:8050/baike',
          method: 'post',
          data: this.lastId
        }).then(this.getDetailInfoSucc)
          .catch((response) => {
            console.log(response)
          })
        axios.request({ // 向django发送请求,获取推荐内容,当需要用另一个axios获取的数据去获取axios时，会出现刷新页面数据加载不出来的情况，目前用的vux
          url: 'http://47.101.221.46:8050/recommend',
          method: 'post',
          data: this.articleIndex
        }).then(this.getRecommendSucc)
          .catch((response) => {
            console.log(response)
          })
        // axios.get('/api/getBaikeDetail/' + this.$route.params.articleTitle
        // ).then(this.getDetailInfoSucc)
      }
    },
    getDetailInfoSucc (res) {
      if (res && res.data) {
        const data = res.data[0]
        this.articleTitle = data.articleTitle
        this.publishDate = data.publishDate
        this.articleUrl = data.articleUrl
        this.contentLabels = data.contentLabels
        this.hitResult = data.hitResult
        this.content = data.content
        this.setIndex(data.index) // 设置vuex中的index
        if (this.userName.length > 0) { // 如果登陆就设置历史
          // axios.post('/api/setHistory', { // 在数据库中添加历史纪录
          //   userName: this.userName,
          //   history: res.data
          // })
          // const param = new URLSearchParams()
          // param.append('userName', this.userName)
          // param.append('history', this.articleIndex)
          const param = {
            'userName': this.userName,
            'history': this.articleIndex
          }
          axios.request({ // 向django发送请求,获取推荐内容
            // headers: {
            //   'deviceCode': 'A95ZEF1-47B5-AC90BF3'
            // },
            url: 'http://47.101.221.46:8050/add_history',
            method: 'post',
            data: Qs.stringify(param)
          }).then((res) => {
            this.setHistory(res.data.result.history) // vuex
          })
        }
        this.isSpinShow = false
      }
    },
    getRecommendSucc (res) {
      if (res && res.data) {
        this.recommendList = res.data
      }
    },
    ...mapMutations(['setHistory']), // 该方法相当于commit一个请求
    ...mapMutations(['setIndex']) // 该方法相当于commit一个请求
  },
  computed: {
    ...mapState(['userName']), // 将vuex公用数据映射给计算属性并命名为city,用this.city取代html中this.$store.state.city
    ...mapState(['articleIndex'])
  },
  updated () { // 滚动到顶部
    window.scroll(0, 0)
  },
  mounted () {
    this.lastId = this.$route.params.articleTitle
    this.getDetailInfo()
  },
  activated () { // 当内容发生变化时要重新发送ajax请求
    console.log('activated')
    if (this.lastId !== this.$route.params.articleTitle) {
      this.lastId = this.$route.params.articleTitle
      this.getDetailInfo()
    }
  }
}
</script>

<style lang="stylus" scoped>
  @import '~styles/variables.styl'
  @import '~styles/mixins.styl'
  .container
    margin-top $headerHeight
    .baike-title
      margin-top $headerHeight + .1rem
      padding .2rem .3rem
      min-height 1rem
      line-height .6rem
      // text-align center
      font-size .35rem
      color #4C4D4E
    .baike-labels
      display inline-block
      padding-left .2rem
      .lables-tag
        margin-right .1rem
    // .baike-img
    //   width 100%
    //   height 3rem
    //   padding 0 .35rem 0 .35rem
    //   .img-content
    //     width 100%
    .baike-date
      margin-top .2rem
      position relative
      height .3rem
      padding 0 .2rem
      // .baike-hint
      //   display inline
      .date
        position absolute
        top 0
        right .35rem
        color #B3B4B5
    .baike-content
      margin .35rem 0
      padding 0 .35rem
      font-size .3rem
      line-height .6rem
      text-align justify
      text-indent 2em
      textJustify()
</style>
