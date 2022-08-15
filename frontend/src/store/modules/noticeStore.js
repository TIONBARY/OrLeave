import {
  noticeList,
  noticeDetail,
  noticeRegist
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
      state.notice = payload.notice
    }
  },
  actions: {
    noticeList: ({ commit }, page) => {
      return noticeList(
        page,
        (response) => {
          commit('setNotices', response.data.noticeList)
          console.log(response.data)
        },
        (error) => {
          console.log(error)
        }
      )
    },
    noticeDetail: ({ commit }, no) => {
      return noticeDetail(
        no,
        (response) => {
          commit('setNotice', response.data)
          console.log(response)
        },
        (error) => {
          commit('setNotice', null)
          console.log(error)
        }
      )
    },
    noticeRegist: (context, notice) => {
      return noticeRegist(
        notice,
        () => {},
        (error) => {
          console.log(error)
        }
      )
    }
  }
}

export default noticeStore
