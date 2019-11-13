<template>
  <div class="wrapper border-top">
    <!-- <Tabs value="name1">
        <TabPane label="标签一" name="name1">
          <crime-composition
            :composition="accu_goucheng"
          >
          </crime-composition>
        </TabPane>
        <TabPane label="标签二" name="name2">标签二的内容</TabPane>
        <TabPane label="标签三" name="name3">标签三的内容</TabPane>
        <TabPane label="标签s" name="name4">标签二的内容</TabPane>
        <TabPane label="标签w" name="name5">标签三的内容</TabPane>
    </Tabs> -->
    <ul class="myTab" :class="{tabfixed: showfixed}">
      <li
        class="normal border-right"
        :class="{'active': isActive === index}"
        v-for="(item,index) in items"
        :key="item.id"
        @click="change(index)"
      >
        {{item}}
      </li>
    </ul>
    <!-- <div :class="{spacefixed: showfixed}"></div> --> <!-- 防止tab在fixed之后下面的内容会有跳动的效果 -->
    <div class="tab-con">
      <crime-composition
        :composition="accu_goucheng"
        v-show="isActive===0"
      >
      </crime-composition>
      <crime-identification
        :list="accu_rending"
        v-show="isActive===1">
      </crime-identification>
      <crime-filing
        :list="accu_lian"
        v-show="isActive===2"
      >
      </crime-filing>
      <crime-filing
        :list="accu_jieshi"
        v-show="isActive===3"
      >   <!-- 法条，有待改进 -->
      </crime-filing>
      <case-list
        :list="list"
        v-if="list"
        v-show="isActive===4"
      >
      </case-list>
    </div>
  </div>
</template>

<script>
import CrimeComposition from '@/pages/case/components/crimeComposition'
import CrimeIdentification from '@/pages/case/components/crimeIdentification'
import CrimeFiling from '@/pages/case/components/crimeFiling'
import CaseList from 'common/Case'
export default {
  name: 'Tab2',
  components: {
    CrimeComposition,
    CrimeIdentification,
    CrimeFiling,
    CaseList
  },
  props: {
    accu_goucheng: Array,
    accu_jieshi: Array,
    accu_lian: Array,
    accu_rending: Array,
    list: Array,
    topHeight: Number
  },
  data () {
    return {
      items: [
        '构成条件',
        '认定条件',
        '立案标准',
        '相关法条',
        '相关案例'
      ],
      showfixed: false,
      isActive: 0, // 索引值默认为0，即item1为默认激活的选项卡
      inheritHeight: 300
    }
  },
  watch: {
    topHeight () { // 监听topHeight才可以实现数据实时更新
      this.$nextTick(function () {
        this.getHeight()
      })
    }
  },
  methods: {
    change (index) {
      this.isActive = index
    },
    handleScroll () {
      // const top = document.documentElement.scrollTop // 获得距离页面顶部的距离
      // // console.log(this.inheritHeight)
      // if (top > this.inheritHeight) {
      //   this.showfixed = true
      // } else {
      //   this.showfixed = false
      // }
    },
    getHeight () {
      this.inheritHeight = this.topHeight
    }
  },
  activated () { // 使tab初始为0
    this.showfixed = false // 防止缓存将showfixed保留为true
    this.isActive = 0
    window.addEventListener('scroll', this.handleScroll)
  },
  deactivated () { // 页面更新时解绑,防止所有页面都添加scroll事件
    window.removeEventListener('scroll', this.handleScroll)
  }
}
</script>

<style lang="stylus" scoped>
  .tabfixed
    position fixed
    top 1.12rem
    width 100%
    z-index 9
  .spacefixed
    height 1rem
  .myTab
    width 100%
    height .9rem
    line-height .9rem
    background-color: #F4F4F4
    // border-top .01rem solid #ccc
    // border-bottom .01rem solid #ccc
    // border-radius .15rem
    .normal
      display inline-block
      font-size .28rem
      color #333
      width 20%
      text-align center
    .active
      color #1750AD
  .tab-con
    background-color: #FFF
</style>
