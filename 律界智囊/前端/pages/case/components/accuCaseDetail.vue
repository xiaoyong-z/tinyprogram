<template>
  <div class="wraper">
    <case-header :title="newItem.summary"></case-header>
    <div class="title">
      {{this.newItem.summary}}
    </div>
    <div class="case">
      <div class="case-title">案情</div>
      <div class="case-text">{{this.newItem.fact}}</div>
    </div>
    <div class="law">
      <div class="law-title">相关法条</div>
      <Collapse simple class="law-text"> <!-- 折叠面板 -->
        <Panel
          v-for="(item, index) of newItem.label"
          :key="index"
        ><!-- class="law-text" -->
          <div class="law-text-title">
            《中华人民共和国刑法》第{{item}}条
          </div>
          <p slot="content" class="law-down">
            <span v-if="contentList">{{contentList[index]}}</span>
          </p>
        </Panel>
      </Collapse>
    </div>
    <div class="judge">
      <div class="judge-title">判决详情</div>
      <div class="judge-text">
        <div class="judge-content">
          <span v-show="this.death">死刑 <br></span>
          <span v-show="this.life">无期徒刑 <br></span>
          <span v-show="!death && !life">刑期： {{this.imprisonment}}  个月 <br></span>
          罚款金额： {{this.money}}
          <span v-show="money > 0">人民币</span>
        </div>
      </div>
    </div>
    <BackTop :height="100" :bottom="50" :right="15"></BackTop>
  </div>
</template>

<script>
// import { mapState } from 'vuex' // vuex高级一些的API
import axios from 'axios'
import CaseHeader from 'common/NewHeader'
export default {
  name: 'AccuCaseDetail',
  components: {
    CaseHeader
  },
  data () {
    return {
      death: false,
      life: false,
      imprisonment: '',
      contentList: [],
      money: 0,
      newItem: {}
    }
  },
  methods: {
    getData () {
      this.newItem = JSON.parse(sessionStorage.getItem('obbj')) // 转化为对象，否则是数组
      this.newItem.label.forEach((item, index) => {
        axios.request({ // 向django发送请求,获取推荐内容
          url: 'http://47.101.221.46:8050/xingfa',
          method: 'post',
          data: item
        }).then((res) => {
          const data = res.data[0]
          this.contentList.push(data.content)
        })
          .catch((response) => {
            console.log(response)
          })
      })
      this.money = this.newItem.punish_of_money
      this.death = this.newItem.prison.death_penalty
      this.life = this.newItem.prison.life_imprisonment
      this.imprisonment = this.newItem.prison.imprisonment
    }
  },
  activated () { // 因为使用了keep-alive，所以要使用这个钩子取代上面的
    this.getData()
  }
}
</script>

<style lang="stylus" scoped>
  @import '~styles/mixins.styl'
  @import '~styles/variables.styl'
  .wraper >>> .ivu-collapse-header
    height 1rem !important
    line-height 1rem !important
  .wraper
    background-color: #fff
    padding-bottom .4rem
    .title
      margin-top $headerHeight + .1rem
      padding .1rem .2rem
      font-size .38rem
      color #333
      line-height .4rem
    .case
    .law
    .judge
      margin-top .3rem
      .case-title
      .law-title
      .judge-title
        height .65rem
        line-height .65rem
        font-size .32rem
        text-align center
        // padding-left .2rem
        color: #f2f2f2
        // font-weight: bold
        margin-left .2rem
        border-radius .1rem
        background-color: #2d8cf0
      .case-title
      .accusation-title
        width 1.5rem
      .law-title
      .judge-title
        width 2rem
      .case-text
      .law-text
      .judge-text
        font-size .3rem
        padding .25rem .35rem
        margin-top .1rem
        line-height .53rem
        textJustify()
        border()
    .law-text
      padding 0 !important
      .law-text-title
        display inline-block
        margin-left -.15rem
        font-size .3rem
        color #333
      .law-down
        line-height .5rem
        font-size .28rem
        color #666666e8
        text-indent 2em
</style>

<!-- <template>
  <div class="wraper">
    <case-header :title="headerTitle"></case-header>
    <div class="fact">
      <div class="fact-title border-bottom">案情描述</div>
      <div class="fact-content">{{this.list.fact}}</div>
    </div>
    <div class="prison">
      <div class="prison-title border-bottom">判刑详情</div>
      <div class="prison-content">
        是否死刑： {{this.death}} <br>
        是否无期徒刑： {{this.life}} <br>
        刑期： {{this.imprisonment}}  个月
      </div>
    </div>
    <div class="fine">
      <div class="fine-title border-bottom">罚金</div>
      <div class="fine-content">
        罚款金额： {{this.list.punish_of_money}}
      </div>
    </div>
  </div>
</template>

<script>
// import { mapState } from 'vuex' // vuex高级一些的API
import CaseHeader from 'common/Header'
export default {
  name: 'AccuCaseDetail',
  components: {
    CaseHeader
  },
  data () {
    return {
      headerTitle: '罪名库',
      death: '',
      life: '',
      list: {type: Object},
      imprisonment: ''
    }
  },
  // created () {
  //   this.list = JSON.parse(sessionStorage.getItem('obbj')) // 转化为对象，否则是数组
  //   this.death = this.list.prison.death_penalty ? '是' : '否'
  //   this.life = this.list.prison.life_imprisonment ? '是' : '否'
  // },
  activated () { // 因为使用了keep-alive，所以要使用这个钩子取代上面的
    this.list = JSON.parse(sessionStorage.getItem('obbj')) // 转化为对象，否则是数组
    this.death = this.list.prison.death_penalty ? '是' : '否'
    this.life = this.list.prison.life_imprisonment ? '是' : '否'
    this.imprisonment = this.list.prison.imprisonment
  }
  // computed: {
  //   // ...mapState(['item']) // 将vuex公用数据映射给计算属性并命名为city,用this.city取代html中this.$store.state.city
  // }
}
</script>

<style lang="stylus" scoped>
  @import '~styles/mixins.styl'
  .wraper
    background-color: #eee
    .fact
      background-color: #FFF
      margin-top 1.12rem
      .fact-title
        casetitle()
      .fact-content
        text-indent 2em
        padding .4rem
        font-size .3rem
        line-height .55rem
    .prison
      background-color: #FFF
      margin-top .2rem
      .prison-title
        casetitle()
      .prison-content
        padding .4rem
        font-size .3rem
        line-height .58rem
    .fine
      background-color: #FFF
      margin-top .2rem
      .fine-title
        casetitle()
      .fine-content
        padding .4rem
        font-size .3rem
        line-height .58rem
</style>
 -->
