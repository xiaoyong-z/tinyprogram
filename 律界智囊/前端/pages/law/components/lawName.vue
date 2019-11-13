<template>
  <div class="container">
    <!-- <law-header :title="headerTitle"></law-header>
    <law-banner :bigTitle="bigTitle" :title="lawTitle"></law-banner> --> <!-- :smallTitle="smallTitle" -->
    <div class="wrapper">
      <swiper :options="swiperOptions"> <!--  v-if="showSwiper" -->
        <!-- slides -->
        <swiper-slide v-for="(page, index) of pages" :key="index">
          <router-link
            tag="div"
            class="wrapper-item border-bottom"
            v-for='item of page'
            :key='item.id'
            :to="'/lawDetail/' + item.chinese_name"
          >
            <div class="item-title">
             {{item.chinese_name}}
            </div>
            <Tag
              class="item-lables"
              color="success"
            >
              {{item.effective}}
            </Tag>
            <div class="item-init">
              <span>{{item.level}}</span>
              <Divider type="vertical" />
              <span>{{item.department}}</span>
              <Divider type="vertical" />
              <span>{{item.store_issue}}</span>
              <Divider type="vertical" />
              <span>{{item.start_date}} 发布</span>
              <Divider type="vertical" />
              <span>{{item.exec_date}} 实施</span>
              <Divider type="vertical" />
            </div>
          </router-link>
        </swiper-slide>
        <!-- Optional controls -->
        <div class="swiper-pagination"  slot="pagination"></div>
      </swiper>
    </div>
    <!-- <loading :isSpinShow="isSpinShow"></loading> -->
  </div>
</template>

<script> // 二级标题
import axios from 'axios'
// import LawHeader from 'common/Header'
// import LawBanner from './lawBanner'
// import Loading from 'common/Loading'
export default {
  name: 'LawName',
  // components: {
  //   // LawHeader,
  //   // LawBanner,
  //   Loading
  // },
  props: {
    lawList: Array
  },
  data () {
    return {
      swiperOptions: {
        pagination: '.swiper-pagination', // 配置swiper,详情见swiper官网
        paginationType: 'fraction', // 使滚动形式变成1/30的类型
        observeParents: true, // 下面两行解决加载时尺寸出错的问题
        observer: true
      },
      // lawList: [],
      headerTitle: '法条库',
      lawTitle: '条例',
      bigTitle: ''
      // lastId: '',
      // isSpinShow: false
    }
  },
  methods: {
    getDetailInfo () {
      if (this.isSpinShow === false) {
        this.isSpinShow = true
        axios.request({ // 向django发送请求,获取推荐内容
          url: 'http://47.101.221.46:8050/tiaoli2_classify2',
          method: 'post'
        }).then(this.getDetailInfoSucc)
          .catch((response) => {
            console.log(response)
          })
        // axios.get('/api/getLaw/' + this.$route.params._id // 这里用动态路由，获取不同案例，取代之前所有案例与罪名放在同一文件下，不要使用下面注释的内容！！否则mongoose无法正确查找！！
        //   // params: {
        //   //   name: this.$route.params._id
        //   // }
        // ).then(this.getDetailInfoSucc)
      }
    },
    getDetailInfoSucc (res) {
      if (res && res.data) {
        console.log(res.data)
        const data = res.data
        this.lawList = data.slice(0, 30)
        // data.forEach((item, index) => {
        //   if (index <= 100)
        //   {
        //     this.lawList[index] = item
        //   }
        // })
        // for (var i = 0; i <= 50; i++) {
        //   this.lawList[i] = data[i]
        // }
        // for (var t = 0; t <= 50; t++) {
        //   console.log(this.lawList[t])
        // }
        this.isSpinShow = false
      }
      // res = res.data
      // if (res.ret && res.data) {
      //   const data = res.data
      //   this.accusationList = data.accusationList
      //   this.bigTitle = data.upperclass
      //   this.smallTitle = data.title
      // }
    }
  },
  computed: { // 实现多余的icon出现在第二张page上
    pages () {
      const pages = []
      this.lawList.forEach((item, index) => {
        const page = Math.floor(index / 5)
        if (!pages[page]) {
          pages[page] = []
        }
        pages[page].push(item)
      })
      return pages
    }
  },
  mounted () {
  //   this.bigTitle = this.$route.params._id
  //   this.lastId = this.$route.params._id
    // this.getDetailInfo()
  }
  // activated () { // 当城市发生变化时要重新发送ajax请求
  //   // if (this.lastId !== this.$route.params._id) {
  //   //   this.bigTitle = this.$route.params._id
  //   //   this.lastId = this.$route.params._id
  //   //   this.getDetailInfo()
  //   // }
  // }
}
</script>

<style lang="stylus" scoped>
  @import '~styles/mixins.styl'
  .container >>> .swiper-container // 解决1/30被hidden的问题
      overflow: inherit
  .container
    background-color: #eee
    .wrapper
      margin-top .2rem
      background-color: #FFF
      .wrapper-item
        // position relative
        // width 100%
        min-height 1rem
        line-height .45rem
        // padding-left .4rem
        // padding-right .1rem
        font-size .3rem
        padding .1rem
        margin .15rem .1rem 0 .1rem
        background-color: #fff
        border .01rem solid #ccc
        border-bottom .01rem solid #ccc
        border-radius .15rem
      // .item-title
        // position absolute // 解决轮播中长度超过屏幕的问题
        // left .4rem
        // right 1rem
        // top 0
        .item-lables
          margin-top .1rem
          margin-right .08rem
        .item-init
          margin .05rem 0
          font-size .26rem
          padding-left .1rem
          color #999
      .swiper-pagination
        color #bbb
        bottom -.3rem // 使1/30在图片下方显示
</style>
