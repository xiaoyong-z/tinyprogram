<template>
  <div class="wraper">
    <div class="punishment">
      <!-- <div class="punishment-title border-bottom">刑期预测</div> -->
      <div class="punishment-content">
        <p v-if="this.list[1]">死刑</p>
        <p v-if="this.list[2]">无期徒刑</p>
        <p>{{this.list[0]}}  个月</p>
      </div>
    </div>
  </div>
</template>

<script> // 刑期和条例
export default {
  name: 'reportPunishment',
  props: {
    list: Array
  }
  // data () {
  //   return {
  //     death: false,
  //     life: false,
  //     punishment: 0
  //   }
  // },
  // watch: { // 获取刑期
  //   list () {
  //     if (this.list[0] > 0) {
  //       this.punishment = this.list[0]
  //       // this.punishment = Math.round(this.punishment)
  //     } else if (this.list[1] === true) { // 死刑
  //       this.death = this.list[1]
  //     } else {
  //       this.life = this.list[2]
  //     }
  //   }
  // }
}
</script>

<style lang="stylus" scoped>
  @import '~styles/mixins.styl'
  // .wraper
  //   background-color: #eee
  //   .punishment
  //     background-color: #FFF
  //     margin-top .2rem
      .punishment-title
        height 1.2rem
        line-height 1.2rem
        font-size .4rem
        font-weight 400
        text-align center
      .punishment-content
        padding .1rem .1rem .1rem .2rem
        font-size .28rem
        line-height .55rem
</style>
