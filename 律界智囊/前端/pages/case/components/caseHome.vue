<template>
  <div class="wraper">
    <case-header :title="accu_name"></case-header>
    <!-- <case-banner :title= "bannerTitle" :smallTitle="accu_name" :bigTitle="bigTitle"></case-banner> -->
    <div v-if="!emptyFlag">
      <crime-concept ref="cont" :name="accu_name" :concept="accu_gainian"></crime-concept>
      <case-tab
        ref="tab"
        :accu_goucheng="accu_goucheng"
        :accu_jieshi="accu_jieshi"
        :accu_lian="accu_lian"
        :accu_rending="accu_rending"
        :list="list"
        :topHeight="topHeight"
      ><!-- :accu_gainian="accu_gainian" :accu_name="accu_name" -->
      </case-tab>
      <BackTop :height="100" :bottom="50" :right="15"></BackTop>
    </div>
    <div v-if="emptyFlag" class="search">
      <div class="empty">
        <div>
          <div class="iconfont empty-icon">&#xe6b2;</div>
          <p class="empty-text">未找到相关罪名...</p>
        </div>
      </div>
    </div>
    <loading :isSpinShow="isSpinShow"></loading>
  </div>
</template>

<script> // 罪名详情页
// import { mapState } from 'vuex' // vuex高级一些的API
import axios from 'axios'
import CaseHeader from 'common/NewHeader'
import CrimeConcept from './crimeConcept'
import CaseTab from 'common/Tab2'
import Loading from 'common/Loading'
import CaseBanner from './caseBanner'
export default {
  name: 'CaseHome',
  components: {
    CaseHeader,
    CrimeConcept,
    CaseTab,
    Loading,
    CaseBanner
  },
  data () {
    return {
      headerTitle: '罪名库',
      bannerTitle: '罪名',
      accu_name: '',
      accu_gainian: [],
      accu_goucheng: [],
      accu_jieshi: [],
      accu_lian: [],
      accu_rending: [],
      list: [],
      accu_law: [],
      lastId: '',
      topHeight: 0,
      isSpinShow: false,
      bigTitle: '',
      emptyFlag: false
    }
  },
  watch: {
    list () { // 在数据加载完成后再获取高度
      this.$nextTick(function () {
        this.getHeight()
      })
    }
  },
  methods: {
    getDetailInfo () {
      if (this.isSpinShow === false) {
        this.isSpinShow = true
        axios.request({ // 向django发送请求,获取二级罪名
          url: 'http://47.101.221.46:8050/final',
          method: 'post',
          data: this.$route.params.accu_name
        }).then(this.getDetailInfoSucc)
          .catch((response) => {
            console.log(response)
          })
        // axios.get('/api/getAccusationDeatil/' + this.$route.params.accu_name)
        // // { // 这里用动态路由，获取不同案例，取代之前所有案例与罪名放在同一文件下，
        // //   params: {
        // //     id: this.$route.params.id
        // //   }
        // // })
        //   .then(this.getDetailInfoSucc)
      }
    },
    getDetailInfoSucc (res) {
      if (res && res.data.length > 0) {
        const data = res.data[0] // 由于数据是个只包含一个对象的数组，所以加【0】
        this.bigTitle = data.accu_belong_to
        this.accu_name = data.accu_name
        this.accu_gainian = data.accu_gainian
        this.accu_goucheng = data.accu_goucheng
        this.accu_jieshi = data.accu_jieshi
        this.accu_lian = data.accu_lian
        this.accu_rending = data.accu_rending
        this.list = data.accu_fact
        this.accu_law = data.accu_law
        data.accu_fact.forEach((item, index) => {
          if (item.summary.length <= 5) {
            this.list[index].summary = item.fact.slice(0, 10)
          }
        })
        this.isSpinShow = false
      } else {
        this.emptyFlag = true
        this.isSpinShow = false
      }
    },
    getHeight () {
      this.topHeight = this.$refs.cont.$el.offsetHeight // 获取concept的高度
    }
  },
  mounted () {
    this.emptyFlag = false
    this.getDetailInfo()
    this.lastId = this.$route.params.accu_name
  },
  activated () { // 当城市发生变化时要重新发送ajax请求
    this.emptyFlag = false
    if (this.lastId !== this.$route.params.accu_name) {
      this.getDetailInfo()
      this.lastId = this.$route.params.accu_name
    }
  }
}
</script>

<style lang="stylus" scoped>
  @import '~styles/variables.styl'
  @import '~styles/mixins.styl'
  .wraper
    background-color: #fff
    .search
      overflow hidden
      position absolute
      top $headerHeight
      bottom 0
      left 0
      right 0
      z-index 10
    .empty
      height 100%
      text-align center
      vertical()
      .empty-icon
        font-size 1.2rem
        color #666
      .empty-text
        margin-top .2rem
        font-size .27rem
        color #999
    /* 旋转效果 */
    .demo-spin-icon-load
      animation: ani-demo-spin 1s linear infinite
    @keyframes ani-demo-spin
      from { transform: rotate(0deg)}
      50% { transform: rotate(180deg)}
      to { transform: rotate(360deg)}
</style>
