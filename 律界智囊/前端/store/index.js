import Vue from 'vue'
import Vuex from 'vuex'
import state from './state'
import mutations from './mutations'

Vue.use(Vuex) // 借助vuex实现案例部分的数据传递

export default new Vuex.Store({
  state,
  mutations
})
