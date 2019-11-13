<template>
  <div class="all">
    <div class="header border-bottom"> <!-- 为了页面向下滚动时header部分不动 -->
      <div class="header-left">
        <div class="iconfont back-icon" @click="handleClick">&#xe62c;</div>
      </div>
      <div class="header-middle">
        <i-input
          search
          v-model="keyword"
          placeholder="输入罪名关键词"
          @on-focus="handleFocus"
          @on-search="handleSearch"
        >
        </i-input>
      </div>
      <router-link
        v-if="!showSearch"
        tag="div"
        class="header-right"
        to="/"
      >
        <div class="iconfont home-icon">&#xe61e;</div>
      </router-link>
      <div v-if="showSearch" class="header-right" @click="handleQuit">取消</div>
      <!-- </div> -->
    </div>
    <search-list v-show="showSearchList" :searchList="searchList"></search-list>
    <transition
      mode="out-in"
      enter-active-class="animated bounceInRight"
      leave-active-class="animated bounceOutRight"
    >
      <div v-show="showSearch" class="search">
        <div v-show="showIcon" class="empty">
          <div>
            <div class="iconfont empty-icon">&#xe6b2;</div>
            <p class="empty-text">未找到相关案例...</p>
          </div>
        </div>
        <Tag
          v-for="item of tagList"
          :key="item"
          :name="item"
          closable
          @on-close="handleClose"
        >
          {{item}}
        </Tag>
        <div class="btn" v-show="showBtn">
          <i-button class="clear-btn" :size="buttonSize" type="primary" icon="md-close" @click="handleClearAll">清除</i-button>
          <i-button class="search-btn" :size="buttonSize" type="primary" icon="ios-search" @click="handleSearchAll">搜索</i-button>
        </div>
        <loading :isSpinShow="isSpinShow"></loading>
      </div>
    </transition>
  </div>
</template>

<script> // 除主页的header
// import Search from './search'
import SearchList from './searchList'
import axios from 'axios'
import Qs from 'qs'
import Loading from 'common/Loading'
export default{
  name: 'CaseHeader',
  components: {
    SearchList,
    Loading
  },
  props: {
    title: String
  },
  data () {
    return {
      keyword: '',
      showSearch: false,
      showSearchList: false,
      searchList: [],
      data4: ['中华人民共和国刑法', '中华人民共和国民法总则', '中华人民共和国劳动法'],
      tagList: [],
      buttonSize: 'large',
      showBtn: true,
      showIcon: false,
      isSpinShow: false
    }
  },
  methods: {
    handleClick () {
      this.tagList = []
      this.showSearch = false
      this.searchList = []
      this.showSearchList = false
      this.showBtn = true
      this.showIcon = false
      this.$router.go(-1) // 返回上一页
    },
    handleFocus () {
      this.showSearch = true
      this.searchList = []
      this.showSearchList = false
      this.showBtn = true
      this.showIcon = false
      this.$emit('show', this.showSearch)
    },
    handleSearch () {
      if (this.keyword) {
        this.tagList.push(this.keyword)
        this.keyword = ''
      } else {
        this.getData()
      }
    },
    handleClose (event, name) {
      const index = this.tagList.indexOf(name)
      this.tagList.splice(index, 1)
    },
    handleQuit () {
      this.showSearch = false
      this.tagList = []
      this.showSearchList = []
      this.showSearchList = false
      this.showBtn = true
      this.showIcon = false
      this.$emit('show', this.showSearch)
    },
    handleClearAll () {
      this.tagList = []
    },
    handleSearchAll () {
      if (this.keyword) {
        this.handleSearch()
      } else {
        this.getData()
      }
    },
    getData () {
      if (this.isSpinShow === false) {
        this.isSpinShow = true
        axios.request({ // 向django发送请求,获取推荐内容
          url: 'http://47.101.221.46:8050/anli_find',
          method: 'post',
          data: Qs.stringify(this.tagList)
        }).then(this.getDataSucc)
          .catch((response) => {
            console.log(response)
          })
      }
    },
    getDataSucc (res) {
      if (res && res.data) {
        this.tagList = []
        this.showBtn = false
        if (res.data.length > 0) {
          console.log(res)
          this.searchList = res.data
          this.showSearchList = true
          this.showSearch = false
        } else {
          console.log(res)
          this.showIcon = true
        }
        this.isSpinShow = false
      }
    }
  }
}
</script>

<style lang="stylus" scoped>
  @import '~styles/variables.styl'
  @import '~styles/mixins.styl'
  .all > .search >>> .ivu-tag // 局部改变tag样式
    margin .2rem .1rem .1rem .2rem
    height .75rem
    line-height .75rem
    font-size .28rem
  .all > .search >>> .ivu-btn // 搜索和清除按钮
    height .85rem
    min-width 1.8rem
    width 2rem
    font-size .28rem
  .header-middle >>> .ivu-input // input
    background-color: #f7f7f7
  .header
    display flex
    position fixed
    top 0
    width 100%
    z-index 100
    height $headerHeight
    line-height $headerHeight
    // background-color: $bgColor
    background-color: #FFF
    color: #fff
    // box-shadow 0 .05rem .1rem #8b8b8bd6
    .header-left
      width: .86rem
      float: left
      color #333
      .back-icon
        font-size: .48rem
        text-align: center
    .header-middle
      flex: 1
      font-size: .3rem
      // font-weight: 400
      padding .1rem 0
      color #333
      vertical()
    .header-right
      width: 1rem
      padding 0 .1rem
      float: right
      text-align center
      color #333 // 去除使用router-link后字体颜色的变化
      .home-icon
        font-size: .6rem
  .search
    overflow hidden
    position absolute
    top $headerHeight
    bottom 0
    left 0
    right 0
    z-index 10
    .btn
      position absolute
      left 1.5rem
      right 1.5rem
      bottom 1rem
      .clear-btn
        float left
      .search-btn
        float right
    .empty
      height 100%
      text-align center
      vertical()
      .empty-icon
        font-size 1.2rem
        color #666
      .empty-text
        margin-top .2rem
        font-size .27rem
        color #999
</style>
