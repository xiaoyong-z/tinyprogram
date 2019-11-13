<template>
  <div class="wrapper"> <!-- 使用组件时最外层必须包裹一个div -->
    <ul class="wrapper-border">
      <li
        class="item border-bottom"
        v-for="(item, index) of list"
        :key="index"
      > <!-- 跳转到detail页面，并防止router-link使字体变色 -->
        <div class="item-info">
         <!--  <p class="item-title">{{item.title}}</p> -->
          <p
            class="item-desc"
            @click="handleCaseDetail(index, item)"
          >
            {{item.summary}}
          </p>
          <div
            class="item-labels"
            v-for="(labelsItem, labelsIndex) of item.label"
            :key="labelsIndex"
          >
            <Tag
              class="lables-tag"
              color="blue"
              :name="labelsItem"
            >
              刑法第{{labelsItem}}条
            </Tag>
          </div>
          <p class="item-init">{{item.fact2}}</p>
        </div>
      </li>
    </ul>
  </div>
</template>

<script> // 案例
// import { mapMutations } from 'vuex' // vuex高级一些的API
export default {
  name: 'CaseList',
  props: {
    list: Array
  },
  // data () {
  //   return {
  //     lastFact: ''
  //   }
  // },
  methods: {
    handleCaseDetail (index, item) { // 使用动态路由的时候使用query要写成 path: '/caseDetail/' + index的形式。而且传递item对象时也不要使用router-link
      // this.lastFact = item.fact
      // let fact = JSON.stringify(item.fact)
      let str = JSON.stringify(item)// 把对象转化为字符串（stringify）存放进sessionStorage
      sessionStorage.setItem('obbj', str) // 使用localStorage没起作用，待解
      // sessionStorage.setItem('fact', fact)
      // if (this.lastFact !== JSON.parse(sessionStorage.getItem('fact'))) {
      //   sessionStorage.clear('obbj')
      //   let str = JSON.stringify(item)
      //   sessionStorage.setItem('obbj', str)
      // }
      this.$router.push({path: '/accuCaseDetail/' + index, query: {item: item}})
    }
    // ...mapMutations(['pushCaseList']) // 该方法相当于commit一个请求
  }
}
</script>

<style lang="stylus" scoped>
  @import '~styles/mixins.styl'
  @import '~styles/variables.styl'
  .wrapper-border
    margin-top .05rem
    // border-top .01rem solid #ccc
    // border-bottom .01rem solid #ccc
    // border-radius .15rem
    padding .1rem 0
    .item
      padding .1rem 0
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
