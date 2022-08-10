import {
  noticeList,
  noticeDetail
} from '@/api/notice.js'

const noticeStore = {
  namespaced: true,
  state: { notices: [], notice: {} },
  getters: {},
  mutations: {
    setNotices(state, payload) {
      state.notices = payload
    },
    setNotice(state, payload) {
      state.notice = payload
    }
  },
  actions: {
    noticeList: ({ commit }, page, size) => {
      return noticeList(
        console.log(page),
        page,
        size,
        ({ data }) => {
          commit('setNotices', data)
        },
        (error) => {
          console.log(error)
        }
      )
    },
    noticeDetail: ({ commit }, no) => {
      return noticeDetail(
        no,
        ({ data }) => {
          commit('setNotice', data)
        },
        (error) => {
          console.log(error)
        }
      )
    }
  }
}

export default noticeStore
