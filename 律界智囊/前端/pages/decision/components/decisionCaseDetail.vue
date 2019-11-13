<template>
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
        罚款金额： {{this.list.meta.punish_of_money}} 元
      </div>
    </div>
    <div class="law">
      <div class="law-title border-bottom">相关法条</div>
      <div class="law-content">
        刑法: 第 {{this.list.meta.relevant_articles[0]}}  条
      </div>
    </div>
  </div>
</template>

<script>
import CaseHeader from 'common/Header'
export default {
  name: 'DecisionCaseDetail',
  components: {
    CaseHeader
  },
  data () {
    return {
      headerTitle: '案例库',
      death: '',
      life: '',
      list: {},
      imprisonment: '',
      lastList: {type: Object}
    }
  },
  methods: {
    getData () {
      this.list = JSON.parse(sessionStorage.getItem('case')) // 转化为对象，否则是数组
      // console.log(this.list.meta.punish_of_money)
      this.death = this.list.meta.term_of_imprisonment.death_penalty ? '是' : '否'
      this.life = this.list.meta.term_of_imprisonment.life_imprisonment ? '是' : '否'
      this.imprisonment = this.list.meta.term_of_imprisonment.imprisonment
      this.lastList = this.list
    }
  },
  mounted () { // 因为使用了keep-alive
    this.getData()
  },
  activated () {
    if (this.lastList !== JSON.parse(sessionStorage.getItem('case'))) {
      this.getData()
    }
  }
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
    .fine
    .law
      background-color: #FFF
      margin-top .2rem
      .prison-title
      .fine-title
      .law-title
        casetitle()
      .prison-content
      .fine-content
      .law-content
        padding .4rem
        font-size .3rem
        line-height .58rem
    // .fine
    //   background-color: #FFF
    //   margin-top .2rem
    //   .fine-title
    //     casetitle()
    //   .fine-content
    //     padding .4rem
    //     font-size .3rem
    //     line-height .58rem

</style>
