<template>
  <div class="container"> <!-- 使用组件时最外层必须包裹一个div -->
    <div class="wrapper">
      <swiper :options="swiperOptions">
        <!-- slides -->
        <swiper-slide v-for="(page, index) of pages" :key="index">
          <router-link
            tag="div"
            class="wrapper-item border-bottom"
            v-for='(item, index) of page'
            :key='index'
            :to="'/baikeDetail/' + item.articleTitle"
          >
            <img class="item-img" :src="item.imageUrl">
            <div class="item-info">
              <p class="item-title">{{item.articleTitle}}</p>
              <!-- <p class="item-label">{{item.contentLabels[0]}}{{item.contentLabels[1]}}</p> -->
              <p class="item-desc">{{item.publishDate}}</p>
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
}

</script>

<style lang="stylus" scoped>
  @import '~styles/variables.styl'
  .container >>> .swiper-container // 解决1/30被hidden的问题
        overflow: inherit
  .container
    background-color: #eee
    .wrapper
      margin-top .2rem
      background-color: #FFF
      .wrapper-item
        padding .1rem .1rem .05rem 0
        position relative
        margin-top .08rem
        width 100%
        height 2.05rem
        overflow hidden
        display flex
      .item-img
        height 1.8rem
        width 1.8rem
        padding .05rem
      .item-info
        flex 1
        padding 0 .1rem 0 .2rem
        min-width 0 // 使省略号正常显示
        .item-title
          line-height .45rem
          font-size .32rem
        .item-desc
          position absolute
          right .2rem
          bottom .3rem
          line-height .44rem
          color #ccc
      .swiper-pagination
        color #bbb
        bottom -.5rem // 使1/30在图片下方显示
</style>
