<template>
  <div class="header">
    <div class="header-left">
      <div class="iconfont back-icon" @click="handleClick">&#xe622;</div>
    </div>
    <div class="header-middle">{{title}}</div>
    <div class="header-right">
      <div class="iconfont del-icon" @click="delTag = true">&#xe63c;</div>
    </div>
    <Modal
      v-model="delTag"
      class-name="vertical-center-modal"
      @on-ok="delHistory"
    >
      <p>确认要清除全部内容吗？</p>
    </Modal>
  </div>
</template>

<script> // header部分，fixed
import axios from 'axios'
import { mapState, mapMutations } from 'vuex' // vuex高级一些的API
export default{
  name: 'Header',
  props: {
    title: String
  },
  data () {
    return {
      delTag: false
    }
  },
  methods: {
    handleClick () { // 返回首页
      this.$router.go(-1)
    },
    delHistory () {
      // axios.post('/api/delHistory', { // 删除历史纪录
      //   userName: this.userName,
      //   history: []
      // })
      axios.request({ // 向django发送请求,获取推荐内容
        // headers: {
        //   'deviceCode': 'A95ZEF1-47B5-AC90BF3'
        // },
        url: 'http://47.101.221.46:8050/delete_history',
        method: 'post',
        data: this.userName
      }).then((res) => {
        this.setHistory([])
      })
    },
    ...mapMutations(['setHistory']) // 该方法相当于commit一个请求
  },
  computed: {
    ...mapState(['userName']) // 将vuex公用数据映射给计算属性并命名为city,用this.city取代html中this.$store.state.city
  }
}
</script>

<style lang="stylus" scoped>
  @import '~styles/variables.styl'
  .header
    display flex
    position fixed
    top 0
    width 100%
    z-index 100
    height $headerHeight
    line-height: $headerHeight
    background-color: $bgColor
    color: #fff
    box-shadow 0 .05rem .1rem #8b8b8bd6
    .header-left
      width: .86rem
      float: left
      .back-icon
        font-size: .46rem
        text-align: center
    .header-middle
      flex: 1
      font-size: .46rem
      font-weight: 400
      text-align: center
    .header-right
      width: .86rem
      padding 0 .1rem
      float: right
      text-align center
      color #fff // 去除使用router-link后字体颜色的变化
      .del-icon
        font-size: .55rem
</style>
