<template>
  <div class="composition">
    <ul>
      <li
        :class="{title: item.length <= 10 , text: item.length > 10}"
        v-for="(item, index) of composition"
        :key="index"
      >
        {{item}}
      </li>
    </ul>
  </div>
</template>

<script>
export default {
  name: 'CrimeComposition',
  props: {
    composition: Array
  }
}
</script>

<style lang="stylus" scoped>
.composition
  padding .4rem
  line-height .58rem
  font-size .3rem
  text-indent 2em
  .title
    font-weight 600
    padding .4rem 0
  .text
    margin-bottom .3rem
    color #666
</style>
