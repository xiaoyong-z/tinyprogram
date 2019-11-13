<template>
  <div class="container"> <!-- 使用组件时最外层必须包裹一个div -->
    <div class="accusation">
      主要罪名
    </div>
    <div class="wrapper">
      <swiper :options="swiperOptions">
        <!-- slides -->
        <swiper-slide v-for="(page, index) of pages" :key="index">
          <router-link
            tag="div"
            class="wrapper-item border-bottom"
            v-for='item of page'
            :key=' item._id'
            :to="'/accusation/' + item._id"
          >
            <div class="item-title">
              {{item._id}}
              <p class="item-count">
                共{{item.count}}个
              </p>
            </div>
          </router-link>
        </swiper-slide>
        <!-- Optional controls -->
        <div class="swiper-pagination"  slot="pagination"></div>
      </swiper>
    </div>
  </div>
</template>

<script> // 一级标题
// import { mapMutations } from 'vuex' // vuex高级一些的API
export default {
  name: 'CaseClassify',
  props: {
    classifyList: Array
  },
  data () {
    return {
      swiperOptions: {
        pagination: '.swiper-pagination', // 配置swiper,详情见swiper官网
        paginationType: 'fraction', // 使滚动形式变成1/30的类型
        observeParents: true, // 下面两行解决加载时尺寸出错的问题
        observer: true,
        initialSlide: 0
      }
    }
  },
  computed: { // 实现多余的icon出现在第二张page上
    pages () {
      const pages = []
      this.classifyList.forEach((item, index) => {
        const page = Math.floor(index / 10)
        if (!pages[page]) {
          pages[page] = []
        }
        pages[page].push(item)
      })
      return pages
    }
  }
  // data () {
  //   return { // 决定高度
  //     isActivated: true // 决定箭头方向
  //   }
  // }
  // methods: {
  //   handleMoreClick (index) {
  //     this.classifyList[index].isActivated = !this.classifyList[index].isActivated
  //     this.classifyList.splice(index, 1, this.classifyList[index]) // 更新数组
  //   }
  //   // handleCaseClick (id, bigTitle, smallTitle, list) {
  //   //   this.$router.push({path: '/caseDetail/' + id, query: {bigTitle: bigTitle, smallTitle: smallTitle}}) // 用编程式导航，传递数据
  //   //   this.pushCaseList(list) // 借助vuex实现案例数据的传递
  //   // },
  //   // ...mapMutations(['pushCaseList']) // 该方法相当于commit一个请求
  // },
  // mounted () { // 映射每个item
  //   this.classifyList.map(el => {
  //     el.isActivated = true
  //     return el
  //   })
  // }
}

</script>

<style lang="stylus" scoped>
  @import '~styles/variables.styl'
  .container >>> .swiper-container // 解决1/30被hidden的问题
        overflow: inherit
  .container
    background-color: #eee
    .accusation
      // margin-top .2rem
      height .8rem
      line-height .63rem
      padding .1rem .4rem
      font-size $titleSize
      background-color: #FFF
    .wrapper
      margin-top .2rem
      background-color: #FFF
      .wrapper-item
        position relative
        width 100%
        height 1rem
        line-height 1rem
        padding 0 .4rem 0 .4rem
        font-size .3rem
      .item-count
        display inline
        padding-top .2rem
        height .5rem
        // line-height .8rem
        float right
        // position absolute
        // top .2rem
        // bottom 0
        // right .7rem
        font-size .2rem
        color #aaa
      .swiper-pagination
        color #bbb
        bottom -.5rem // 使1/30在图片下方显示
  // .changeHeight
  //   height 3rem !important
  // .item-title
  //   margin-top .2rem
  //   height 1.3rem
  //   line-height 1.3rem
  //   padding 0 .25rem
  //   font-size .42rem
  //   background-color: #FFF
  // .item-info
  //   overflow hidden
  //   height 2rem
  //   padding .3rem .3rem
  //   background-color: #FFF
  //   .item-info-li
  //     float: left
  //     box-sizing: border-box
  //     width 25%
  //     .item-info-title
  //       height 1rem
  //       line-height 1rem
  //       font-size .3rem
  //       text-align center
  //       color #666
  // .item-more
  //     height .8rem
  //     line-height .8rem
  //     text-align center
  //     font-size .3rem
  //     color #666
  //     background-color: #FFF
</style>
