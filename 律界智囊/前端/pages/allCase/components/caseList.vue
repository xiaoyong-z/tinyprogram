<template>
  <div class="container">
    <!-- <law-header :title="headerTitle"></law-header>
    <law-banner :bigTitle="bigTitle" :title="lawTitle"></law-banner> --> <!-- :smallTitle="smallTitle" -->
    <div class="wrapper">
      <swiper :options="swiperOptions"> <!--  v-if="showSwiper" -->
        <!-- slides -->
        <swiper-slide v-for="(page, index) of pages" :key="index">
          <ul class="wrapper-border">
          <li
            class="item border-bottom"
            v-for="(item, index) of page"
            :key="index"
          > <!-- 跳转到detail页面，并防止router-link使字体变色 -->
            <div class="item-info">
             <!--  <p class="item-title">{{item.title}}</p> -->
              <p
                class="item-desc"
                @click="handleCaseDetail(index, item)"
              >
                {{item.summary}}
              </p>
              <div
                class="item-labels"
                v-for="(labelsItem, labelsIndex) of item.meta.accusation"
                :key="labelsIndex"
              >
                <Tag
                  class="lables-tag"
                  color="blue"
                  :name="labelsItem"
                >
                  <!-- 刑法第{{labelsItem}}条 -->
                  {{labelsItem}}
                </Tag>
              </div>
              <p class="item-init">{{item.fact2}}</p>
            </div>
          </li>
        </ul>
        </swiper-slide>
        <!-- Optional controls -->
        <div class="swiper-pagination"  slot="pagination"></div>
      </swiper>
    </div>
    <loading :isSpinShow="isSpinShow"></loading>
  </div>
</template>

<script> // 二级标题
import axios from 'axios'
// import { mapMutations } from 'vuex' // vuex高级一些的API
// import LawHeader from 'common/Header'
// import LawBanner from './lawBanner'
import Loading from 'common/Loading'
export default {
  name: 'CaseList',
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
      caseList: [],
      isSpinShow: false,
      contentList: [[]],
      ok: false,
      lastItem: {}
    }
  },
  methods: {
    handleCaseDetail (index, item) { // 使用动态路由的时候使用query要写成 path: '/caseDetail/' + index的形式。而且传递item对象时也不要使用router-link
      // this.lastFact = item.fact
      // let fact = JSON.stringify(item.fact)
      let newMeta = item.meta
      // console.log(newMeta.accusation)
      newMeta.accusation.forEach((item, index) => { // 为了能正确给casedetail发送数据
        if (item.indexOf('罪') < 0) {
          newMeta.accusation[index] = item + '罪'
        }
      })
      item.meta = newMeta
      // newMeta.relevant_articles.forEach((item, index) => {
      //   axios.request({ // 向django发送请求,获取推荐内容
      //     url: 'http://47.101.221.46:8050/xingfa',
      //     method: 'post',
      //     data: item
      //   }).then((res) => {
      //     // console.log(res)
      //     this.contentList[index] = []
      //     this.contentList[index] = res.data[0]
      //   }).catch((response) => {
      //     console.log(response)
      //   })
      //   console.log('axios')
      // })
      // this.setContentList(this.contentList)
      // console.log('list')
      // this.setCase(item)
      this.lastItem = item
      let str = JSON.stringify(item)// 把对象转化为字符串（stringify）存放进sessionStorage
      sessionStorage.setItem('art', str) // 使用localStorage没起作用，待解
      // if (this.lastItem !== JSON.parse(sessionStorage.getItem('art'))) {
      //   sessionStorage.clear('art')
      //   let str = JSON.stringify(item)
      //   sessionStorage.setItem('art', str)
      // }
      this.$router.push({path: '/caseDetail/' + index, query: {item: item}})
    },
    getDetailInfo () {
      if (this.isSpinShow === false) {
        this.isSpinShow = true
        axios.request({ // 向django发送请求,获取推荐内容
          url: 'http://47.101.221.46:8050/anli_list',
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
        // console.log(res.data)
        const data = res.data
        this.caseList = data
        this.isSpinShow = false
      }
    }
    // ...mapMutations(['setCase']), // 该方法相当于commit一个请求
    // ...mapMutations(['setContentList']) // 该方法相当于commit一个请求
  },
  computed: { // 实现多余的icon出现在第二张page上
    pages () {
      const pages = []
      this.caseList.forEach((item, index) => {
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
    this.getDetailInfo()
  }
}
</script>

<style lang="stylus" scoped>
  @import '~styles/mixins.styl'
  @import '~styles/variables.styl'
  .container >>> .swiper-container // 解决1/30被hidden的问题
      overflow: inherit
  .container
    margin-top $headerHeight
    .wrapper-border
      margin-top .05rem
      // border-top .01rem solid #ccc
      // border-bottom .01rem solid #ccc
      // border-radius .15rem
      // padding .1rem 0
      .item
        padding .1rem 0 .2rem 0
        margin .15rem .1rem 0 .1rem
        background-color: #fff
        border .01rem solid #ccc
        border-bottom .01rem solid #ccc
        border-radius .15rem
        .item-info
          min-height 1rem
          padding .15rem .12rem
          // .item-title
          //   color #414141
          line-height .45rem
          //   height .56rem
          font-size .3rem
          //   ellipsis()
          .item-desc
            // margin-top .17rem
            color #414141
            // line-height 1.1rem
            // height 1.1rem
            // ellipsis()
          .item-labels
            margin-top .1rem
            display inline-block
            .lables-tag
              margin-right .08rem
    .item-init
      padding 0 .05rem
      margin-top .15rem
      font-size .26rem
      color #999
      line-height .4rem
    .swiper-pagination
      background-color: #f7f7f7
      color #bbb
      bottom -.3rem // 使1/30在图片下方显示
    // .wrapper
    //   margin-top .2rem
    //   background-color: #FFF
    //   .wrapper-item
    //     // position relative
    //     // width 100%
    //     min-height 1rem
    //     line-height .45rem
    //     // padding-left .4rem
    //     // padding-right .1rem
    //     font-size .3rem
    //     padding .1rem
    //     margin .15rem .1rem 0 .1rem
    //     background-color: #fff
    //     border .01rem solid #ccc
    //     border-bottom .01rem solid #ccc
    //     border-radius .15rem
    //   // .item-title
    //     // position absolute // 解决轮播中长度超过屏幕的问题
    //     // left .4rem
    //     // right 1rem
    //     // top 0
    //     .item-lables
    //       margin-top .1rem
    //       margin-right .08rem
    //     .item-init
    //       margin .05rem 0
    //       font-size .26rem
    //       padding-left .1rem
    //       color #999
</style>
