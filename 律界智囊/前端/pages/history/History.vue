<template>
  <div class="wrapper">
    <home-header :title="title"></home-header>
    <div class="wrapper">
      <ul>
        <router-link
          tag="li"
          class="wrapper-item border-bottom"
          v-for='(item, index) of reverseList'
          :key='index'
          :to="'/baikeDetail/' + item.articleTitle"
        >
          <div class="iconfont item-icon">&#xe6a2;</div>
          <div class="item-info">
            <p class="item-title">{{item.articleTitle}}</p>
            <!-- <p class="item-label">{{item.contentLabels[0]}}{{item.contentLabels[1]}}</p> -->
            <p class="item-desc">{{item.hitResult}}</p>
          </div>
        </router-link>
      </ul>
    </div>
  </div>
</template>

<script>
import { mapState } from 'vuex' // vuex高级一些的API , mapMutations
import HomeHeader from './components/Header'
export default{
  name: 'History',
  components: {
    HomeHeader
  },
  data () {
    return {
      title: '我的足迹'
    }
  },
  computed: {
    reverseList () { // 直接return this.historyList.reverse()不安全
      return Array.prototype.reverse.call(this.historyList)
    },
    ...mapState(['historyList']) // 将vuex公用数据映射给计算属性并命名为city,用this.city取代html中this.$store.state.city
  }
}
</script>

<style lang="stylus" scoped>
  @import '~styles/variables.styl'
  @import '~styles/mixins.styl'
  .wrapper
    margin-top $headerHeight + .1rem
    background-color: #FFF
    .wrapper-item
      padding .2rem 0 .1rem 0
      height 1.6rem
      display flex
      .item-icon
        padding .2rem
        width 1rem
        height 1rem
        font-size .75rem
        color #1d774a
      .item-info
        flex 1
        padding .1rem 0 .1rem .2rem
        min-width 0 // 使省略号正常显示
        .item-title
          line-height .56rem
          font-size .32rem
          ellipsis()
        .item-desc
          line-height .44rem
          color #ccc
          ellipsis()
</style>
