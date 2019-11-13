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
      </AutoComplete>
      <!-- <i class="iconfont search-icon" v-show="!keyword">&#xe632;</i> -->
      <!-- <input class="search-input" v-model="keyword" type="text" placeholder="输入条文关键词"> -->
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
        class="search-content"
        ref="search"
        v-show="keyword"
      > <!-- 当输入后才会显示该列表 -->
        <ul>
          <router-link
            tag="li"
            class="search-item border-bottom"
            v-for="item of list"
            :key="item.id"
            :to="'/lawDetail/' + item.chinese_name"
          >
            {{item.chinese_name}}
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
const options = {
  scrollY: true,
  scrollbar: true,
  click: true // BetterScroll阻止了点击事件！！
}
export default {
  name: 'LawSearch',
  props: {
    nameList: Array
  },
  data () {
    return {
      keyword: '',
      list: [],
      timer: null,
      // nameList: [],
      data4: ['中华人民共和国刑法', '中华人民共和国民法总则', '中华人民共和国劳动法']
    }
  },
  methods: {
    filterMethod (value, option) { // 解决输入后下拉框不消失
      return option.toUpperCase().indexOf(value.toUpperCase()) !== -1
    },
    getNameinfo () {
      axios.request({ // 向django发送请求,获取法律具体内容
        url: 'http://47.101.221.46:8050/tiaoli2_classify2',
        method: 'post'
      }).then(this.getNameInfoSucc)
        .catch((response) => {
          console.log(response)
        })
      // axios.get('/api/getAllLaw')
      //   .then(this.getNameInfoSucc)
    },
    getNameInfoSucc (res) {
      if (res && res.data) {
        console.log(res.data)
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
          if (value.chinese_name && value.chinese_name.indexOf(this.keyword) > -1) {
            result.push(value)
          }
        })
        // }
        this.list = result
      }, 100)
    }
  },
  mounted () {
    this.scroll = new Bscroll(this.$refs.search, options) // 加载搜索列表中的滑动条
    // this.getNameinfo()
  },
  activated () { // 防止回到该页面时留有搜索字
    this.keyword = ''
  }
}
</script>

<style lang="stylus" scoped>
  @import '~styles/mixins.styl'
  .search
    margin-top 1.12rem
    height 1rem
    line-height 1rem
    padding 0 .2rem
    background-color #1da7ba
    // position relative
    // top -2rem
    // left .25rem
    // right .25rem
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
    background-color: #ccc
    .search-item
      height 1rem
      line-height 1rem
      padding 0 .4rem
      color #666
      background-color: #FFF
      ellipsis()
</style>
