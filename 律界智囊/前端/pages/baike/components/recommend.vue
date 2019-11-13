<template>
  <div class="recommend"> <!-- 使用组件时最外层必须包裹一个div -->
    <div class="bank"></div>
    <div class="title border-bottom">
      <span class="iconfont case-title-icon">&#xe611;</span>
      相关法律知识
    </div>
    <div class="wrapper">
      <ul class="wrapper-border">
        <li
          class="wrapper-item border-bottom"
          v-for='item of recommendList'
          :key='item.index'
          @click="handleRouter(item)"
        >
          <img class="item-img" :src="item.imageUrl">
          <div class="item-info">
            <p class="item-title">{{item.articleTitle}}</p>
            <div
              class="baike-labels"
              v-for="(labelsItem, labelsIndex) of item.contentLabels"
              :key="labelsIndex"
            >
              <Tag class="lables-tag" color="primary"
              :name="labelsItem.value">{{labelsItem.value}}</Tag>
            </div>
            <!-- <p class="item-label">{{item.contentLabels[0]}}{{item.contentLabels[1]}}</p> -->
            <p class="item-desc">{{item.publishDate}}</p>
          </div>
        </li>
      </ul>
    </div>
    <!-- <div class="item-more" @click="change">换一批</div> -->
    <!-- <loading :isSpinShow="isSpinShow"></loading> -->
  </div>
</template>

<script>
// import axios from 'axios'
// import Loading from 'common/Loading'
export default {
  name: 'BaikeRecommend',
  props: {
    recommendList: Array
  },
  methods: {
    handleRouter (item) { // 由于使用router-link转到相同界面不知道为什么没有刷新数据，所以先用了改变articleTitle的方法， 这会导致界面url不会刷新
      this.$emit('changeTitle', item.articleTitle, item.index)
    }
  }
  // components: {
  //   Loading
  // },
  // methods: {
  //   getRecommend () {
  //     if (this.isSpinShow === false) {
  //       this.isSpinShow = true
  //       axios.request({ // 向django发送请求,获取推荐内容
  //         url: 'http://148.70.210.143:8050/recommend',
  //         method: 'post',
  //         data: this.articleIndex
  //       }).then(this.getRecommendSucc)
  //         .catch((response) => {
  //           console.log(response)
  //         })
  //     }
  //   },
  //   getRecommendSucc (res) {
  //     if (res && res.data) {
  //       console.log(res)
  //       this.list = res.data
  //       this.isSpinShow = false
  //     }
  //   }
  // },
  // activated () {
  //   this.getRecommend()
  // }
}
</script>

<style lang="stylus" scoped>
  @import '~styles/mixins.styl'
  @import '~styles/variables.styl'
  .recommend
    background-color: #FFF
    // position relative
    height 13.5rem
    .bank
      height .3rem
      background-color: #eee
    .title
      // margin-top .3rem
      height 1rem
      line-height 1rem
      font-size .38rem
      padding-left .3rem
      color #666
      .case-title-icon
        font-size .42rem
    .wrapper
      margin-top .13rem
      background-color: #FFF
      .wrapper-border
        // border-top .01rem solid #ccc
        border-bottom .01rem solid #ccc
        border-radius .15rem
        .wrapper-item
          padding .12rem .1rem .08rem .08rem
          position relative
          margin-top .08rem
          width 100%
          height 2.15rem
          overflow hidden
          display flex
        .baike-labels
          margin-top .1rem
          display inline-block
          .lables-tag
            margin-right .08rem
        .item-img
          height 1.8rem
          width 1.8rem
          padding .05rem
          border-radius .2rem
        .item-info
          flex 1
          padding .04rem .1rem 0 .2rem
          min-width 0 // 使省略号正常显示
          .item-title
            line-height .45rem
            font-size .31rem
          .item-desc
            position absolute
            right .2rem
            bottom 0
            line-height .44rem
            color #ccc
</style>
