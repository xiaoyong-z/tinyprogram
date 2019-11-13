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
    <div class="accusation">
      <div class="accusation-title">罪名</div>
      <ul class="accusation-text"><!-- v-if="article.meta" 解决accusation渲染失败的情况 -->
        <router-link
          class="accusation-li"
          tag="li"
          v-if="newItem && newItem.meta && newItem.meta.accusation"
          v-for="(item, index) of newItem.meta.accusation"
          :key="index"
          :to="'/crimeDetail/' + item"
        >
          <Icon type="ios-link" />
          {{item}}
        </router-link>
      </ul>
    </div>
    <div class="law">
      <div class="law-title">相关法条</div>
      <Collapse simple class="law-text"> <!-- 折叠面板 -->
        <Panel
          v-if="newItem && newItem.meta"
          v-for="(item, index) of newItem.meta.relevant_articles"
          :key="index"
        ><!-- class="law-text" -->
          <div class="law-text-title">
            <!-- <Icon type="ios-link" /> -->
            《中华人民共和国刑法》第{{item}}条
          </div>
          <p slot="content" class="law-down">
            <!-- <Divider></Divider> -->
            <span v-if="contentList">{{contentList[index]}}</span>
           <!--  <div v-if="contentList"> --><!--  class="law-text-content" -->
             <!--  <p>{{contentList[index]}}</p> --> <!-- class="law-down" -->
            <!-- </div> -->
          </p>
        </Panel>
      </Collapse>
    </div>
    <!-- <div class="law">
      <div class="law-title">相关法条</div>
      <ul class="law-text"> --><!-- 解决accusation渲染失败的情况 -->
       <!--  <li
          v-if="article && article.meta"
          v-for="(item, index) of article.meta.relevant_articles"
          :key="index"
        >
          <p class="law-text-title">
            <Icon type="ios-link" />
            《中华人民共和国刑法》第{{item}}条
          </p>
          <div v-if="contentList" class="law-text-content">
            <p class="law-down">{{contentList[index]}}</p>
          </div>
        </li>
      </ul>
    </div> -->
    <div v-if="newItem && newItem.meta" class="judge">
      <div class="judge-title">判决详情</div>
      <div class="judge-text">
        <div class="judge-content">
          <span v-show="newItem.meta.term_of_imprisonment.death_penalty">死刑 <br></span>
          <span v-show="newItem.meta.term_of_imprisonment.life_imprisonment">无期徒刑 <br></span>
          <span v-show="!death && !life">刑期： {{newItem.meta.term_of_imprisonment.imprisonment}}  个月 <br></span>
          罚款金额： {{newItem.meta.punish_of_money}}
          <span v-show="newItem.meta.punish_of_money > 0">人民币</span>
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
  name: 'CaseDetail',
  components: {
    CaseHeader
  },
  data () {
    return {
      death: false,
      life: false,
      list: {type: Object},
      imprisonment: '',
      contentList: [],
      money: 0,
      lastItem: {},
      newItem: {}
    }
  },
  // computed: {
  //   ...mapState(['article']) // 将vuex公用数据映射给计算属性并命名为city,用this.city取代html中this.$store.state.city
  // },
  // watch: { // 本来是解决vuex读取问题的，但傻逼的我忘记前面detail的方法了
  //   article () {
  //     // if (this.article && this.article.meta) {
  //     //   // console.log(this.article.meta)
  //     //   this.lastItem = this.article
  //     //   const newMeta = this.article.meta
  //     //   // newMeta.accusation.forEach((item, index) => { // 为了能正确给casedetail发送数据
  //     //   //   this.article.meta.accusation[index] = item + '罪'
  //     //   // })
  //     //   newMeta.relevant_articles.forEach((item, index) => {
  //     //     axios.request({ // 向django发送请求,获取推荐内容
  //     //       url: 'http://47.101.221.46:8050/xingfa',
  //     //       method: 'post',
  //     //       data: item
  //     //     }).then((res) => {
  //     //       // console.log(res)
  //     //       // this.contentList[index] = []
  //     //       this.contentList.push(res.data[0].content)
  //     //       // console.log(this.contentList[index])
  //     //     })
  //     //       .catch((response) => {
  //     //         console.log(response)
  //     //       })
  //     //   })
  //     // }
  //   }
  // },
  methods: {
    getData () {
      this.newItem = JSON.parse(sessionStorage.getItem('art')) // 转化为对象，否则是数组
      this.lastItem = this.newItem
      const newMeta = this.newItem.meta
      newMeta.relevant_articles.forEach((item, index) => {
        axios.request({ // 向django发送请求,获取推荐内容
          url: 'http://47.101.221.46:8050/xingfa',
          method: 'post',
          data: item
        }).then((res) => {
          // console.log(res)
          // this.contentList[index] = []
          const data = res.data[0]
          this.contentList.push(data.content) // 用push的话要在初始时置空
          // console.log(this.contentList[index])
        })
          .catch((response) => {
            console.log(response)
          })
      })
      this.money = newMeta.punish_of_money
      this.death = newMeta.term_of_imprisonment.death_penalty
      this.life = newMeta.term_of_imprisonment.life_imprisonment
      this.imprisonment = newMeta.term_of_imprisonment.imprisonment
    }
  },
  mounted () {
    // console.log('moun')
    // this.getData()
    // if (this.article && this.article.meta) {
    //   console.log(this.article + 'mounted')
    //   const newMeta = this.article.meta
    //   // newMeta.accusation.forEach((item, index) => { // 为了能正确给casedetail发送数据
    //   //   this.article.meta.accusation[index] = item + '罪'
    //   // })
    //   newMeta.relevant_articles.forEach((item, index) => {
    //     axios.request({ // 向django发送请求,获取推荐内容
    //       url: 'http://47.101.221.46:8050/xingfa',
    //       method: 'post',
    //       data: item
    //     }).then((res) => {
    //       // console.log(res)
    //       this.contentList[index] = res.data[0]
    //       console.log(this.contentList[index])
    //     })
    //       .catch((response) => {
    //         console.log(response)
    //       })
    //   })
    // }
  },
  activated () { // 因为使用了keep-alive，所以要使用这个钩子取代上面的
    this.contentList = []
    this.getData()
    // if (this.lastItem !== JSON.parse(sessionStorage.getItem('art'))) {
    //   console.log('act')
    //   console.log(this.lastItem)
    //   console.log(JSON.parse(sessionStorage.getItem('art')))
    //   this.getData()
    // }
    // console.log(this.article)
    // this.list = JSON.parse(sessionStorage.getItem('obbj')) // 转化为对象，否则是数组
    //
    // if (this.lastItem !== this.article) {
    //   console.log(this.lastItem)
    //   console.log('hi')
    // }
    //
    // this.article.meta.relevant_articles.forEach((item, index) => {
    //   axios.request({ // 向django发送请求,获取推荐内容
    //     url: 'http://47.101.221.46:8050/xingfa',
    //     method: 'post',
    //     data: item
    //   }).then((res) => {
    //     console.log(res)
    //     this.contentList[index] = []
    //     this.contentList[index].push(res.data[0].content)
    //     console.log(this.contentList[index])
    //   })
    //     .catch((response) => {
    //       console.log(response)
    //     })
    // })
    // this.money = this.article.meta.punish_of_money
    // this.death = this.article.meta.term_of_imprisonment.death_penalty
    // this.life = this.article.meta.term_of_imprisonment.life_imprisonment
    // this.imprisonment = this.article.meta.term_of_imprisonment.imprisonment
  }
  // computed: {
  //   // ...mapState(['item']) // 将vuex公用数据映射给计算属性并命名为city,用this.city取代html中this.$store.state.city
  // }
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
    .accusation
    .law
    .judge
      margin-top .3rem
      .case-title
      .accusation-title
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
      // .case-text
      //   text-indent 2em
      .case-title
      .accusation-title
        width 1.5rem
      .law-title
      .judge-title
        width 2rem
      .case-text
      .accusation-text
      .law-text
      .judge-text
        background-color: #FFF
        font-size .29rem
        padding .25rem .35rem
        margin .1rem
        // margin-top .1rem
        line-height .53rem
        textJustify()
        border()
    .accusation-li
      margin .1rem 0
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
        color #999
        text-indent 2em
</style>
