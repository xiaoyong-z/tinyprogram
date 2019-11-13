<template>
  <div class="case"> <!-- 使用组件时最外层必须包裹一个div -->
    <case-header :title="title"></case-header>
    <!-- <case-pictures :imgUrl="imgUrl"></case-pictures> -->
    <case-search></case-search>
    <case-classify :classifyList="classifyList"></case-classify>
    <loading :isSpinShow="isSpinShow"></loading>
  </div>
</template>

<script>
import axios from 'axios'
import CaseHeader from 'common/Header'
// import CasePictures from 'common/Pictures'
import CaseSearch from './components/caseSearch'
import CaseClassify from './components/classify'
import Loading from 'common/Loading'
// import { mapState } from 'vuex'
export default {
  name: 'Case',
  components: {
    CaseHeader,
    // CasePictures,
    CaseSearch,
    CaseClassify,
    Loading
  },
  data () {
    return {
      title: '罪名查询',
      classifyList: [],
      isSpinShow: false
    }
  },
  methods: {
    getCaseInfo () {
      if (this.isSpinShow === false) {
        this.isSpinShow = true
        axios.request({ // 向django发送请求,获取推荐内容
          url: 'http://47.101.221.46:8050/new_classify2',
          method: 'post'
        }).then(this.getCaseInfoSucc)
          .catch((response) => {
            console.log(response)
          })
      }
    },
    getCaseInfoSucc (res) { // 数据的获取
      if (res && res.data) {
        res.data.forEach((item, index) => {
          if (index !== 4) { // 去掉那个null
            this.classifyList.push(item)
          }
        })
        this.isSpinShow = false
      }
      // res = res.data
      // if (res.ret && res.data) {
      //   const data = res.data
      //   this.imgUrl = data.imgUrl
      //   this.classifyList = data.classifyList
      // }
    }
  // res => {
  //    this.classifyList = res.data
  //     }
  //   getCrimeName () {
  //     axios.get('/api/crimeName.json') // 获取202个罪名
  //       .then(this.getCrimeNameSucc)
  //   },
  //   getCrimeNameSucc (res) {
  //     res = res.data
  //     if (res.ret && res.data) {
  //       const data = res.data
  //       this.nameList = data.nameList
  //     }
  //   }
  },
  mounted () {
    this.getCaseInfo()
  }
  // computed: {
  //   ...mapState(['list'])
  // },
  // mounted () {
  //   this.lastList = this.list
  //   this.getCaseInfo()
  // }
  // activated () { // 当城市发生变化时要重新发送ajax请求
  //   if (this.lastList !== this.list) {
  //     this.lastList = this.list
  //     this.getCaseInfo()
  //   }
  // }
}

</script>

<style lang="stylus" scoped>
  .case
    background-color: #eee
</style>
