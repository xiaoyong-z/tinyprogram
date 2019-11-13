<template>
  <div class="composition">
    <ul>
      <li
        :class="{title: item.indexOf('（') != -1 || item.indexOf('界限') != -1,
          text: item.length > 25}"
        v-for="(item, index) of list"
        :key="index"
      >
        {{item}}
      </li>
    </ul>
  </div>
</template>

<script>
export default {
  name: 'CrimeIdentification',
  props: {
    list: Array
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
