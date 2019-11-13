<template>
  <div class="wrapper"> <!-- 使用组件时最外层必须包裹一个div -->
    <div class="law">
      法条分类
    </div>
    <swiper :options="swiperOptions" class="classify"> <!--  v-if="showSwiper" -->
        <!-- slides -->
        <swiper-slide v-for="(page, index) of pages" :key="index">
          <router-link
            tag="div"
            class="wrapper-item border-bottom"
            v-for='(item, index) of page'
            :key='index'
            :to="'/lawName/' + item._id"
          >
            <div class="item-title">
             {{item._id}}
            </div>
          </router-link>
        </swiper-slide>
        <!-- Optional controls -->
      <div class="swiper-pagination"  slot="pagination"></div>
    </swiper>
    <loading :isSpinShow="isSpinShow"></loading>
  </div>
</template>

<script>
import axios from 'axios'
import Loading from 'common/Loading'
export default {
  name: 'Title',
  components: {
    Loading
  },
  data () {
    return {
      swiperOptions: {
        pagination: '.swiper-pagination', // 配置swiper,详情见swiper官网
        paginationType: 'fraction', // 使滚动形式变成1/30的类型
        observeParents: true, // 下面两行解决加载时尺寸出错的问题
        observer: true
      },
      list: [],
      isSpinShow: false
    }
  },
  methods: {
    getDetailInfo () {
      if (this.isSpinShow === false) {
        this.isSpinShow = true
        axios.request({ // 向django发送请求,获取推荐内容
          url: 'http://47.101.221.46:8050/tiaoli2_classify_sum',
          method: 'post'
        }).then(this.getDetailInfoSucc)
          .catch((response) => {
            console.log(response)
          })
        // axios.get('/api/getTotalLaw')
        //   .then(this.getDetailInfoSucc)
      }
    },
    getDetailInfoSucc (res) {
      if (res && res.data) {
        this.list = res.data
        this.isSpinShow = false
      }
    }
  },
  computed: { // 实现多余的icon出现在第二张page上
    pages () {
      const pages = []
      this.list.forEach((item, index) => {
        const page = Math.floor(index / 10)
        if (!pages[page]) {
          pages[page] = []
        }
        pages[page].push(item)
      })
      return pages
    }
  },
  mounted () {
    this.getDetailInfo()
  }
}
</script>

<style lang="stylus" scoped>
  @import '~styles/variables.styl'
  // .wrapper
  //   margin-top .2rem
  .wrapper >>> .swiper-container // 解决1/30被hidden的问题
      overflow: inherit
  .wrapper
      background-color: #eee
      .law
        height 1rem
        line-height 1rem
        padding .1rem .4rem
        font-size $titleSize
        background-color: #FFF
      .classify
        margin-top .2rem
        background-color: #FFF
      .wrapper-item
        position relative
        width 100%
        height 1rem
        line-height 1rem
        padding-left .4rem
        padding-right .1rem
        font-size .3rem
      .item-title
        position absolute // 解决轮播中长度超过屏幕的问题
        left .4rem
        right 1rem
        top 0
        ellipsis()
      .swiper-pagination
        color #bbb
        bottom -.5rem // 使1/30在图片下方显示
</style>
