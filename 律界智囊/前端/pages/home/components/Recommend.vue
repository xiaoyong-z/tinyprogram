<template>
  <div class="recommend"> <!-- 使用组件时最外层必须包裹一个div -->
    <div class="title">法律知识</div>
    <div class="wrapper">
      <ul class="wrapper-border">
        <router-link
          tag="li"
          class="wrapper-item border-bottom"
          v-for='(item, index) of list'
          :key='index'
          :to="'/baikeDetail/' + item.articleTitle"
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
        </router-link>
      </ul>
    </div>
    <div class="item-more" @click="change">换一批</div>
    <loading :isSpinShow="isSpinShow"></loading>
  </div>
</template>

<script>
import axios from 'axios'
import Loading from 'common/Loading'
export default {
  name: 'HomeRecommend',
  data () {
    return {
      list: [],
      contentLabels: [],
      isSpinShow: false
    }
  },
  components: {
    Loading
  },
  methods: {
    change () {
      this.getRecommend()
    },
    getRecommend () {
      if (this.isSpinShow === false) {
        this.isSpinShow = true
        axios.request({ // 向django发送请求,获取推荐内容
          url: 'http://47.101.221.46:8050/recommend',
          method: 'post',
          data: 0
        }).then(this.getRecommendSucc)
          .catch((response) => {
            console.log(response)
          })
      }
    },
    getRecommendSucc (res) {
      if (res && res.data) {
        this.list = res.data
        this.isSpinShow = false
      }
    }
  },
  mounted () {
    this.getRecommend()
  }
}
</script>

<style lang="stylus" scoped>
  @import '~styles/mixins.styl'
  @import '~styles/variables.styl'
  .recommend
    margin-top .2rem
    position relative
    height 13rem
    // border-top .02rem solid #ccc
    // border-radius .4rem
    .title
      margin-top .1rem
      height .7rem
      line-height .7rem
      font-size .355rem
      text-align left
      padding-left .2rem
      color: #2c3e50
      font-weight: bold
      // background-color: #eee
    .wrapper
      margin-top .13rem
      background-color: #FFF
      .wrapper-border
        border-top .01rem solid #ccc
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
    .item-more
      margin-top .1rem
      height .9rem
      line-height .9rem
      text-align center
      font-size .3rem
      color #666
</style>
