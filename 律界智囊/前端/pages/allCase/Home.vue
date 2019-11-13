<template>
  <div class="case"> <!-- 使用组件时最外层必须包裹一个div -->
    <case-header :title="title" @show="changeFlag"></case-header>
    <case-init v-if="!showSearch"></case-init>
    <!-- <case-pictures :imgUrl="imgUrl"></case-pictures> -->
    <!-- <case-search></case-search>
    <case-classify :classifyList="classifyList"></case-classify>
    <loading :isSpinShow="isSpinShow"></loading> -->
  </div>
</template>

<script>
// import axios from 'axios'
import CaseHeader from './components/Header'
import CaseInit from './components/caseList'
// import CasePictures from 'common/Pictures'
// import CaseSearch from './components/caseSearch'
// import CaseClassify from './components/classify'
// import Loading from 'common/Loading'
// import { mapState } from 'vuex'
export default {
  name: 'AllCase',
  components: {
    CaseHeader,
    CaseInit
    // CasePictures,
    // CaseSearch,
    // CaseClassify,
    // Loading
  },
  data () {
    return {
      title: '罪名库',
      classifyList: [],
      isSpinShow: false,
      showSearch: false
    }
  },
  methods: {
    changeFlag (flag) {
      this.showSearch = flag
    }
  },
  activated () {
    this.showSearch = false
  }
  // methods: {
  //   getCaseInfo () {
  //     if (this.isSpinShow === false) {
  //       this.isSpinShow = true
  //       axios.request({ // 向django发送请求,获取推荐内容
  //         url: 'http://47.101.221.46:8050/new_classify2',
  //         method: 'post'
  //       }).then(this.getCaseInfoSucc)
  //         .catch((response) => {
  //           console.log(response)
  //         })
  //     }
  //   },
  //   getCaseInfoSucc (res) { // 数据的获取
  //     if (res && res.data) {
  //       this.classifyList = res.data
  //       this.isSpinShow = false
  //     }
  //     // res = res.data
  //     // if (res.ret && res.data) {
  //     //   const data = res.data
  //     //   this.imgUrl = data.imgUrl
  //     //   this.classifyList = data.classifyList
  //     // }
  //   }
  // // res => {
  // //    this.classifyList = res.data
  // //     }
  // //   getCrimeName () {
  // //     axios.get('/api/crimeName.json') // 获取202个罪名
  // //       .then(this.getCrimeNameSucc)
  // //   },
  // //   getCrimeNameSucc (res) {
  // //     res = res.data
  // //     if (res.ret && res.data) {
  // //       const data = res.data
  // //       this.nameList = data.nameList
  // //     }
  // //   }
  // },
  // mounted () {
  //   this.getCaseInfo()
  // }
}
</script>

<style lang="stylus" scoped>
  .case
    background-color: #f7f7f7
</style>
