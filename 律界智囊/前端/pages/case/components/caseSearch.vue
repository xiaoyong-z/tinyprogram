<template>
  <div class="wraper">
    <div class="search">
      <AutoComplete
        v-model="keyword"
        placeholder="输入罪名关键词"
        icon="ios-search"
        :filter-method="filterMethod"
        :data="data4"
      >
        <!-- <div class="demo-auto-complete-item" v-for="(item, index) in data4" :key="index">
          <div class="demo-auto-complete-group">
            <span>{{ item.title }}</span>
          </div>
          <div v-if="!keyword">
            <Option v-for="option in item.children" :value="option.title" :key="option.title">
              <span class="demo-auto-complete-title">{{ option.title }}</span>
              <span class="demo-auto-complete-count">{{ option.count }} 人关注</span>
            </Option>
          </div>
        </div> -->
      </AutoComplete>
      <!-- <i class="iconfont search-icon" v-show="!keyword">&#xe632;</i> -->
      <!-- <input class="search-input" v-model="keyword" type="text" placeholder="输入罪名关键词"> -->
      <!-- <button class="search-btn">提交</button> -->
    </div>
    <transition
      :duration="{enter: 80, leave: 50}"
      name="fade"
      mode="out-in"
      enter-active-class="animated fadeIn fade-enter-active"
      leave-active-class="animated zoomOut fade-leave-active"
    >
      <div
        ref="search"
        class="search-content"
        v-show="keyword"
      > <!-- 当输入后才会显示该列表 -->
        <ul>
          <router-link
            tag="li"
            class="search-item border-bottom"
            v-for="item of list"
            :key="item.id"
            :to="'/crimeDetail/' + item.accu_name"
          >
            {{item.accu_name}}
          </router-link>
          <li class="search-item border-bottom" v-show="hasNoData">
            无匹配数据
          </li>
        </ul>
      </div>
    </transition>
  </div>
</template>

<script>
import Bscroll from 'better-scroll'
import axios from 'axios'
const options = { // BetterScroll阻止了点击事件！！
  scrollY: true,
  scrollbar: true,
  click: true
  // pullDownRefresh: {
  //   threshold: 50,
  //   stop: 20
  // },
  // pullUpLoad: {
  //   threshold: -20
  // }
}
export default {
  name: 'CaseSearch',
  data () {
    return {
      keyword: '',
      list: [],
      timer: null,
      nameList: [],
      data4: [
        // {
        // title: '常见罪名',
        // children: [{
        //   title: '诈骗罪',
        //   count: 10000
        // }, {
        //   title: '盗窃罪',
        //   count: 10600
        // }, {
        //   title: '走私罪',
        //   count: 10600
        // }
        // ]}
        '诈骗罪', '盗窃罪', '抢劫罪'
      ]}
  },
  methods: {
    filterMethod (value, option) { // 解决输入后下拉框不消失
      return option.toUpperCase().indexOf(value.toUpperCase()) !== -1
    },
    getNameinfo () {
      axios.request({ // 向django发送请求,获取法律具体内容
        url: 'http://47.101.221.46:8050/classify2',
        method: 'post'
      }).then(this.getNameInfoSucc)
        .catch((response) => {
          console.log(response)
        })
      // axios.get('/api/getAllAccusation')
      //   .then(this.getNameInfoSucc)
    },
    getNameInfoSucc (res) {
      if (res && res.data) {
        this.nameList = res.data
      }
    }
  },
  computed: {
    hasNoData () { // 当没找到匹配项时才显示
      return !this.list.length
    }
  },
  watch: { // 监听输入框的内容
    keyword () {
      if (this.timer) { // 做一个节流处理,提高性能
        clearTimeout(this.timer)
      }
      if (!this.keyword) { // 当没有输入搜索项时显示为空
        this.list = []
        return
      }
      this.timer = setTimeout(() => {
        const result = []
        this.nameList.forEach((value) => {
          // 下面要先判断accu_name是否存在，否则会使undefined，不知道为什么
          if (value.accu_name && value.accu_name.indexOf(this.keyword) > -1) {
            result.push(value)
          }
        })
        this.list = result
      }, 40)
    }
  },
  mounted () {
    this.scroll = new Bscroll(this.$refs.search, options) // 加载搜索列表中的滑动条
    this.getNameinfo()
  },
  activated () { // 防止回到该页面时留有搜索字
    this.keyword = ''
  }
}
</script>

<style lang="stylus" scoped>
  @import '~styles/mixins.styl'
  // .wraper // 防止搜索框抖动，并且搜索出来的内容可以正确滚动
  //   height 0
  .demo-auto-complete-item
    padding: 4px 0
    border-bottom: 1px solid #F6F6F6
  .demo-auto-complete-group
    font-size .27rem
    padding 4px 6px
  .demo-auto-complete-group span
    color: #666
    font-weight: bold
  .demo-auto-complete-group a
    float: right
  .demo-auto-complete-count
    float: right
    color: #999
  // .demo-auto-complete-more
  //   display: block;
  //   margin: 0 auto;
  //   padding: 4px;
  //   text-align: center;
  //   font-size: 12px;
  .search
    margin-top 1.12rem
    height 1rem
    line-height 1rem
    padding 0 .2rem
    background-color #1da7ba
    font-family: Arial, "Microsoft Yahei", "Helvetica Neue", Helvetica, sans-serif
    font-size .3rem
    .search-input
      box-sizing border-box
      padding  0 .1rem
      height .8rem
      line-height .8rem
      width 100%
      color #666
      border-radius .1rem
      opacity .8
      text-align center
  .search-content
    z-index 99 // 覆盖search区域
    overflow hidden
    position absolute
    top 2rem
    bottom 0
    left 0
    right 0
    background-color: #f8f8f9
    .search-item
      height 1rem
      line-height 1rem
      padding-left .4rem
      padding-right .1rem
      color #666
      background-color: #FFF
      ellipsis()
</style>
