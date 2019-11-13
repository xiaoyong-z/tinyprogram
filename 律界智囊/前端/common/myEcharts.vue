<template>
  <div>
  <!-- echart表格 使用ref获取dom元素 避免在一个组件中重复使用覆盖的问题-->
    <div ref="myChart" id="chart"></div>
  </div>
</template>

<script>
import echarts from 'echarts'
// import axios from 'axios'
export default {
  name: 'MyEchart',
  data () {
    return {
      fact: '',
      accu: [],
      seriesData: []
    }
  },
  props: {
    titleText: { // 标题文本
      type: String,
      default: ''
    }
    // opinion: { // 扇形区域名称
    //   type: Array
    // },
    // opinionData: { // 扇形区域数据
    //   type: Array
    // }
  },
  mounted () {
    this.accu = JSON.parse(sessionStorage.getItem('accu'))
    this.seriesData = JSON.parse(sessionStorage.getItem('prob'))
    this.Pie()
    // this.$nextTick(function () {
    //   this.Pie()
    // })
  },
  activated () {
    // this.fact = JSON.parse(sessionStorage.getItem('decisionFact')) // 转化为对象，否则是数组
    // this.getAccusation()
    this.accu = JSON.parse(sessionStorage.getItem('accu'))
    this.seriesData = JSON.parse(sessionStorage.getItem('prob'))
    this.Pie()
  },
  methods: {
    push (i) {
      this.$router.push({path: '/crimeDetail/' + i})
    },
    // getAccusation () {
    //   axios.request({ // 向django发送请求
    //     url: 'http://35.226.111.16:8000/predict',
    //     method: 'post',
    //     data: this.fact
    //   }).then(this.getAccusationSuc)
    //     .catch((response) => {
    //       console.log(response)
    //     })
    // },
    // getAccusationSuc (res) {
    //   if (res && res.data) {
    //     const data = res.data
    //     this.accu = []
    //     data.accu.forEach((item, index) => {
    //       this.accu[index] = item + '罪'
    //     })
    //     this.seriesData = []
    //     if (data.accu_prob) { // 先判断是否存在，否则会出现无法读取未定义的accu_prob
    //       data.accu_prob.forEach((item, index) => {
    //         this.seriesData.push({
    //           value: parseFloat((item * 100).toFixed(1)),
    //           name: this.accu[index]
    //         })
    //       }) // 对概率做数据操作
    //     }
    //     // this.accu_rele = data.accu_rele
    //     // let str = JSON.stringify(this.accu_rele)
    //     // sessionStorage.setItem('accu_rele', str)
    //     this.Pie()
    //     // this.accuSucc = true
    //     // this.isSpinShow = false
    //   }
    // },
    eConsole (param) { // 监听扇形图点击
      console.log(param)
      // 向父组件传值 看需求
      // this.$emit("currentEchartData", param.name);
    },
    Pie () { // 饼状图
      this.charts = echarts.init(this.$refs.myChart)
      const this_ = this // 先保存，否则进入on之后this就是echarts里的了
      this.charts.on('click', function (params) {
        this_.push(params.data.name)
      })
      // this.charts.on('legendselectchanged', function (obj) {
      //   var selected = obj.selected
      //   for (var i in selected) {
      //     if (selected[i] === false) {
      //       this_.push(i)
      //     }
      //   }
      //   // this.$router.push('/crimeDetail/' + item)
      // })
      this.charts.setOption({
        title: {
          x: 'center',
          // 标题文本
          text: this.titleText,
          bottom: '28%',
          textStyle: {
            fontFamily: 'Arial',
            fontWeight: 'normal',
            fontSize: 12.5
          }
        },
        tooltip: {
          trigger: 'item',
          formatter: '{d}%'
        },
        legend: {
          left: 'center',
          textStyle: {
            fontFamily: 'Arial',
            fontWeight: 'normal',
            fontSize: 14
          },
          selectedMode: false, // 组织legend点击事件
          orient: 'vertical',
          padding: [10, 23, 10, 23],
          // itemGap: 8,
          bottom: '10%', // 数值越小距离越大
          icon: 'circle',
          itemWidth: 14,
          itemHeight: 14,
          // 扇形区域名称
          data: this.accu
        },
        series: [{
          name: 'law', // 必须加了才能获取后台数据,待解
          type: 'pie',
          color: ['#6ec290', '#da6184', '#f09542', '#42a3d8', '#d17b20', '#3d20d1', '#7620d1cf', '#209fd1cf', '#d16320f2', '#d12058f2', '#d15b20cc', '#b4d120f2'],
          center: ['50%', '35%'],
          radius: '55%',
          avoidLabelOverlap: false, // 默认防止标签重叠
          startAngle: 80,
          label: {
            normal: {
              formatter: '{d}%\n\n', // \n\n让文字再视觉引导线的上面
              padding: [15, -35, 5, -35],
              position: 'outer',
              fontSize: '1rem'
            }
          },
          labelLine: {
            normal: {
              show: true,
              length: 10,
              length2: 30
            }
          },
          data: this.seriesData // 扇形区域数据
        }]
      })
    }
  }
}
</script>
<style lang="stylus" scoped>
#chart
  width: 100%
  min-height: 8rem
</style>
