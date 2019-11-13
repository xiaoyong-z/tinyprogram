import Vue from 'vue'
import Router from 'vue-router'
const LoginHome = r => require.ensure([], () => r(require('@/pages/login/LoginHome')), 'group-login')
const History = r => require.ensure([], () => r(require('@/pages/history/History')), 'group-user')
const Home = r => require.ensure([], () => r(require('@/pages/home/Home')), 'group-home')
const Decision = r => require.ensure([], () => r(require('@/pages/decision/Decision')), 'group-decision')
const DecisionReport = r => require.ensure([], () => r(require('@/pages/decision/components/report')), 'group-decision')
const DecisionCaseDetail = r => require.ensure([], () => r(require('@/pages/decision/components/decisionCaseDetail')), 'group-decision')
const Case = r => require.ensure([], () => r(require('@/pages/case/Case')), 'group-crime')
const AccuCaseDetail = r => require.ensure([], () => r(require('@/pages/case/components/accuCaseDetail')), 'group-crime')
const CaseHome = r => require.ensure([], () => r(require('@/pages/case/components/caseHome')), 'group-crime')
const CaseAccusation = r => require.ensure([], () => r(require('@/pages/case/components/secondClassify')), 'group-crime')
const LawHome = r => require.ensure([], () => r(require('@/pages/law/Home')), 'group-law')
const LawName = r => require.ensure([], () => r(require('@/pages/law/components/lawName')), 'group-law')
const LawDetail = r => require.ensure([], () => r(require('@/pages/law/components/lawDetail')), 'group-law')
// const BaikeHome = r => require.ensure([], () => r(require('@/pages/baike/BaikeHome')), 'group-baike')
const BaikeDetail = r => require.ensure([], () => r(require('@/pages/baike/components/detail')), 'group-baike')
const AllCase = r => require.ensure([], () => r(require('@/pages/allCase/Home')), 'group-case')
const CaseDetail = r => require.ensure([], () => r(require('@/pages/allCase/components/caseDetail')), 'group-case')

Vue.use(Router)

export default new Router({
  routes: [{
    path: '/login',
    name: 'LoginHome',
    component: LoginHome
  }, {
    path: '/history',
    name: 'History',
    component: History
  }, {
    path: '/',
    name: 'Home',
    component: Home
  }, {
    path: '/detail/0001',
    name: 'Decision',
    component: Decision
  }, {
    path: '/detail/0002',
    name: 'Case',
    component: Case
  }, {
    path: '/detail/0003',
    name: 'LawHome',
    component: LawHome
  }, {
    path: '/detail/0004',
    name: 'AllCase',
    component: AllCase
  }, { // 智能决策
    path: '/report/:fact',
    name: 'DecisionReport',
    component: DecisionReport
  }, { // 罪名解释主页
    path: '/crimeDetail/:accu_name',
    name: 'CaseHome',
    component: CaseHome
  }, { // 二级罪名
    path: '/accusation/:_id',
    name: 'CaseAccusation',
    component: CaseAccusation
  }, { // 罪名库中的案例内容
    path: '/accuCaseDetail/:index',
    name: 'AccuCaseDetail',
    component: AccuCaseDetail
  }, { // 智能决策中的案例内容
    path: '/decisionCaseDetail/:index',
    name: 'DecisionCaseDetail',
    component: DecisionCaseDetail
  }, { // 二级法条
    path: '/lawName/:_id',
    name: 'LawName',
    component: LawName
  }, { // 法条内容
    path: '/lawDetail/:chinese_name',
    name: 'LawDetail',
    component: LawDetail
  }, { // 干货内容
    path: '/baikeDetail/:articleTitle',
    name: 'BaikeDetail',
    component: BaikeDetail
  }, { // 干货内容
    path: '/caseDetail/:index',
    name: 'CaseDetail',
    component: CaseDetail
  }],
  scrollBehavior (to, from, savedPosition) { // 使页面跳转时每次都在顶部出现
    return { x: 0, y: 0 }
  }
})
