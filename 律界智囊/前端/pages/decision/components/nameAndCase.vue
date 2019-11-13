<template>
  <div class="report">
    <div class="title">案件分析</div>
    <div class="name">
      <p class="name-title">罪名预测</p>
      <Divider></Divider>
      <my-echart
        :titleText="titleText"
      >
      </my-echart>
    </div>
    <div class="punish">
      <p class="punish-title">刑期预测</p>
      <Divider></Divider>
      <report-punishment :list="impr"></report-punishment>
    </div>
    <div class="law">
      <div class="law-title">
        相关法规
        <p class="law-more" @click="handleMore('law')">更多</p>
      </div>
      <Divider></Divider>
      <div class="law-content">
        <p class="law-content-title">《中华人民共和国刑法》第{{tiaoli[0]}}条</p>
        <p class="law-content-text">{{contentList[0]}}</p>
      </div>
    </div>
    <div class="case">
      <div class="case-title">
        相似案例
        <p class="case-more" @click="handleMore('case')">更多</p>
      </div>
      <Divider></Divider>
      <div class="item">
        <div class="item-info">
          <p
            class="item-desc"
            @click="handleCaseDetail"
          >
            {{oneCase.summary}}
          </p>
          <div
            v-if="oneCase.meta"
            class="item-labels"
            v-for="(labelsItem, labelsIndex) of oneCase.meta.accusation"
            :key="labelsIndex"
          >
            <Tag
              class="lables-tag"
              color="blue"
              :name="labelsItem"
            >
              <!-- 刑法第{{labelsItem}}条 -->
              {{labelsItem}}
            </Tag>
          </div>
          <p class="item-init">{{oneCase.fact2}}</p>
        </div>
      </div>
    </div>
    <!-- <Collapse simple accordion> --> <!-- 折叠面板 -->
      <!-- <Panel
        v-for="(item, index) of accu_rele"
        :key="index"
      >
        <p
          class="test"
        >
          {{accu[index]}} --> <!-- 罪名改进 -->
          <!-- {{item[0].meta.accusation[0]}} --> <!-- 罪名 -->
        <!-- </p>
        <i-circle
          :size="24"
          :trail-width="4"
          :stroke-width="5"
          :percent="accu_prob[index]"
          stroke-linecap="square"
          stroke-color="#43a3fb"
        >
          <div class="demo-Circle-custom">
            <span>{{accu_prob[index] + "%"}}</span>
          </div>
        </i-circle>
        <p slot="content">
          <Divider orientation="center" size="small">
            相关案例
          </Divider>
          <report-case :list="item"></report-case> 案例
        </p>
      </Panel>
    </Collapse> -->
    <!-- <ul>
      <li
        class="accu-name"
        v-for="(item, index) of accu_rele"
        :key="index"
      >
      <div class="item-info">
        <p class="item-name">{{item[0].meta.accusation[0]}}</p>
      </div>
      <report-case :list="item"></report-case>
      </li>
    </ul> -->
    <!-- <div class="case-title border-bottom">
      <span class="iconfont case-title-cion">&#xe611;</span>
      相关案例
    </div>
    <ul>
      <li
        class="case-detail"
        v-for="item of caseList"
        :key="item.id"
      >
        <div class="case-detail-title">{{item.title}}</div>
        <div class="case-detail-border"></div>
      </li>
    </ul> -->
      <!-- :opinion="accu"
      :opinionData="seriesData" -->
  </div>
</template>

<script>
import ReportCase from './caseList'
import MyEchart from 'common/myEcharts'
import ReportPunishment from './reportPunishment'
export default {
  name: 'ReportName',
  props: {
    impr: Array,
    contentList: Array,
    tiaoli: Array,
    oneCase: Object
    // accu: Array,
    // accu_rele: Array,
    // seriesData: Array
  },
  components: {
    MyEchart,
    ReportCase,
    ReportPunishment
  },
  data () {
    return {
      titleText: '相关罪名概率'
    }
  },
  methods: {
    handleMore (name) {
      this.$emit('changeTab', name)
    },
    handleCaseDetail () {
      let newMeta = this.oneCase.meta
      newMeta.accusation.forEach((item, index) => { // 为了能正确给casedetail发送数据
        if (item.indexOf('罪') < 0) { // 有的有罪，有的没有
          newMeta.accusation[index] = item + '罪'
        }
      })
      this.oneCase.meta = newMeta
      let str = JSON.stringify(this.oneCase)// 把对象转化为字符串（stringify）存放进sessionStorage
      sessionStorage.setItem('art', str) // 使用localStorage没起作用，待解
      this.$router.push({path: '/caseDetail/' + 0, query: {item: this.oneCase}})
    }
  }
}
</script>

<style lang="stylus" scoped>
  @import '~styles/variables.styl'
  @import '~styles/mixins.styl'
  .report >>> .ivu-divider
    background-color #e3e5e899
  .report
    // margin-top $headerHeight
    background-color: #4a42d8bd
    padding-bottom .5rem
    .title
      padding-top .1rem
      height 1rem
      line-height 1rem
      font-size .4rem
      font-weight 400
      text-align center
      color #fff
    .name
    .punish
    .law
    .case
      background-color: #FFF
      margin .15rem
      padding .2rem
      border 20px solid red
      border-radius .15rem
      .name-title
      .punish-title
      .law-title
      .case-title
        font-weight bold
        font-size .32rem
        padding 0 .1rem
        height .4rem
        line-height .4rem
        margin .1rem 0 .15rem 0
        color #333
        .law-more
        .case-more
          font-weight normal
          font-size .28rem
          display inline
          float right
          color rgba(62, 53, 212, 0.99)
    .law-content
      padding .1rem .1rem .1rem .2rem
      line-height .55rem
      .law-content-title
        font-size .3rem
        color #333
      .law-content-text
        text-indent 2em
        font-size .28rem
        color #999
    .item
      padding .1rem 0
      // margin .15rem .1rem 0 .1rem
      .item-info
        min-height 1rem
        padding .15rem .12rem 0 .12rem
        line-height .45rem
        font-size .3rem
        .item-desc
          // margin-top .17rem
          color #414141
          // line-height 1.1rem
          // height 1.1rem
          // ellipsis()
        .item-labels
          margin-top .1rem
          display inline-block
          .lables-tag
            margin-right .08rem
    .item-init
      padding 0 .05rem
      margin-top .15rem
      font-size .26rem
      color #999
      line-height .4rem
  // @import '~styles/mixins.styl'
  // .case
  //   margin .37rem 0 1rem
  //   background-color: #fff
  //   .case-title
  //     padding .15rem $textPadding
  //     height .7rem
  //     line-height .7rem
  //     font-size .38rem
  //     font-weight 420
  //     color #666
  //     .case-title-cion
  //       font-size .42rem
  //   .case-detail
  //     padding .3rem $textPadding 0 $textPadding
  //     .case-detail-title
  //       ellipsis()
  //     .case-detail-border
  //       itemborder()
  //       height .1rem
  //       margin: 0 .1rem
  //       padding-top .2rem
</style>
