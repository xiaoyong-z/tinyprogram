<template>
  <div>
    <home-header :title="title"></home-header>
    <!-- <home-pictures :imgUrl="imgUrl"></home-pictures> -->
    <home-search :nameList="nameList"></home-search>
    <home-law :lawList="lawList"></home-law>
    <loading :isSpinShow="isSpinShow"></loading>
  </div>
</template>

<script>
import HomeHeader from 'common/Header'
// import HomePictures from 'common/Pictures'
import HomeSearch from './components/search'
import HomeLaw from './components/lawName'
import axios from 'axios'
import Loading from 'common/Loading'
export default{
  name: 'LawHome',
  components: {
    HomeHeader,
    // HomePictures,
    HomeSearch,
    HomeLaw,
    Loading
  },
  data () {
    return {
      title: '法条查询',
      lawList: [],
      nameList: [],
      isSpinShow: false
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
      }
    },
    getDetailInfoSucc (res) {
      if (res && res.data) {
        // console.log(res.data)
        const data = res.data
        this.lawList = data.slice(0, 30)
        this.nameList = data
        this.isSpinShow = false
      }
    }
  },
  mounted () {
    this.getDetailInfo()
  }
}
</script>

<style lang="stylus" scoped>
</style>
