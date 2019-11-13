export default {
  setName (state, userName) {
    state.userName = userName
    try {
      localStorage.userName = userName // 借助localStorage本地存储数据，防止页面刷新时城市出错
    } catch (e) {}
  },
  setHistory (state, historyList) {
    state.historyList = historyList
    try {
      localStorage.setItem('historyList', JSON.stringify(historyList))
    } catch (e) {}
  },
  setIndex (state, articleIndex) {
    state.articleIndex = articleIndex
    try {
      localStorage.setItem('articleIndex', JSON.stringify(articleIndex))
    } catch (e) {}
  },
  setCase (state, article) {
    state.article = article
    try {
      localStorage.setItem('article', JSON.stringify(article))
    } catch (e) {}
  },
  setContentList (state, contentList) {
    state.contentList = contentList
    try {
      localStorage.setItem('contentList', JSON.stringify(contentList))
    } catch (e) {}
  }
}
