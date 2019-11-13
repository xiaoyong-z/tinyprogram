<template>
  <div class="container">
    <ul>
      <li
        v-for="(item, index) of accu_rele"
        :key="index"
      >
        <ul class="wrapper-border">
          <li
            class="item border-bottom"
            v-for="(smallItem, index) of item"
            :key="index"
          >
            <div class="item-info">
              <p
                class="item-desc"
                @click="handleCaseDetail(index, smallItem)"
              >
                {{smallItem.summary}}
              </p>
              <div
                class="item-labels"
                v-if="smallItem.meta && smallItem.meta.accusation"
                v-for="(labelsItem, labelsIndex) of smallItem.meta.accusation"
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
              <p class="item-init">{{smallItem.fact2}}</p>
            </div>
          </li>
        </ul>
      </li>
    </ul>
  </div>
</template>

<script> // 案例
export default {
  name: 'ReportCase',
  props: {
    accu_rele: Array
  },
  methods: {
    handleCaseDetail (index, item) {
      let newMeta = item.meta
      newMeta.accusation.forEach((item, index) => { // 为了能正确给casedetail发送数据
        if (item.indexOf('罪') < 0) { // 有的有罪，有的没有
          newMeta.accusation[index] = item + '罪'
        }
      })
      item.meta = newMeta
      let str = JSON.stringify(item)// 把对象转化为字符串（stringify）存放进sessionStorage
      sessionStorage.setItem('art', str) // 使用localStorage没起作用，待解
      this.$router.push({path: '/caseDetail/' + index, query: {item: item}})
    }
  }
}
</script>

<style lang="stylus" scoped>
  @import '~styles/mixins.styl'
  @import '~styles/variables.styl'
  // .container
  //   margin-top $headerHeight
    .wrapper-border
      margin-top .05rem
      // border-top .01rem solid #ccc
      // border-bottom .01rem solid #ccc
      // border-radius .15rem
      padding .1rem 0
      .item
        padding .1rem 0 .2rem 0
        margin .15rem .1rem 0 .1rem
        background-color: #fff
        border .01rem solid #ccc
        border-bottom .01rem solid #ccc
        border-radius .15rem
        .item-info
          min-height 1rem
          padding .15rem .12rem
          // .item-title
          //   color #414141
          line-height .45rem
          //   height .56rem
          font-size .3rem
          //   ellipsis()
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
</style>
