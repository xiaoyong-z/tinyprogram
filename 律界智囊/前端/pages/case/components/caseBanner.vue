<template>
  <div class="banner border-bottom">
    <Breadcrumb separator=">">
      <BreadcrumbItem to="/detail/0002">
        <Icon type="md-book" />
        {{title}}
      </BreadcrumbItem>
      <BreadcrumbItem>
        <span @click="front">{{bigTitle}}</span>
      </BreadcrumbItem>
      <BreadcrumbItem v-if="smallTitle">{{smallTitle}}</BreadcrumbItem>
    </Breadcrumb>
    <!-- <i>{{this.title}}</i>
    <i class="iconfont banner-icon">&#xe7ab;</i>
    <router-link
      tag="i"
      class="banner-bigTitle"
      to="/detail/0002"
     >
      {{bigTitle}}
   </router-link> -->
    <!-- <i class="iconfont banner-icon">&#xe7ab;</i>
    <i>{{smallTitle}}</i> -->
  </div>
</template>

<script> // 该部分实现类似导航功能
export default {
  name: 'CaseBanner',
  props: {
    title: String,
    bigTitle: String,
    smallTitle: {
      type: String,
      default: ''
    }
  },
  methods: {
    front () {
      this.$router.push('/accusation/' + this.bigTitle)
    }
  }
}
</script>

<style lang="stylus" scoped>
  @import '~styles/variables.styl'
  @import '~styles/mixins.styl'
  .banner
    width 100%
    margin-top $headerHeight
    height 1rem
    line-height 1rem
    padding .1rem .32rem
    font-size .32rem
    background-color: #FFF
    ellipsis()
    // .banner-icon
    //   height .4rem
    //   line-height .4rem
    //   margin .05rem
    //   color #666
    //   text-align center
    //   font-size .38rem
    // .banner-bigTitle
    //   color #c0b5b5f5
</style>
