import {
  noticeList,
  noticeDetail,
  noticeRegist,
  noticeModify,
  noticeDelete
} from '@/api/notice.js'

const noticeStore = {
  namespaced: true,
  state: { notices: [], notice: {}, showModal: false, modalContent: null },
  getters: {},
  mutations: {
    setNotices(state, payload) {
      state.notices = payload
    },
    setNotice(state, payload) {
      state.notice = payload.notice
    },
    SetshowModal(state, value) {
      state.showModal = value
    },
    SetmodalContent(state, payload) {
      state.modalContent = payload
    }
  },
  actions: {
    noticeList: ({ commit }, page) => {
      return noticeList(
        page,
        (response) => {
          commit('setNotices', response.data.noticeList)
        },
        (error) => {
          console.warn(error)
        }
      )
    },
    noticeDetail: ({ commit }, no) => {
      return noticeDetail(
        no,
        (response) => {
          commit('setNotice', response.data)
        },
        (error) => {
          commit('setNotice', null)
          console.warn(error)
        }
      )
    },
    noticeRegist: ({ commit }, notice) => {
      return noticeRegist(
        notice,
        () => {
          commit('SetshowModal', true)
          commit('SetmodalContent', '공지사항이 작성되었습니다.')
        },
        () => {
          commit('SetshowModal', true)
          commit('SetmodalContent', '작성에 실패 하였습니다.')
        }
      )
    },
    noticeModify: ({ commit }, notice) => {
      return noticeModify(
        notice,
        () => {
          commit('SetshowModal', true)
          commit('SetmodalContent', '공지사항이 수정되었습니다.')
        },
        () => {
          commit('SetshowModal', true)
          commit('SetmodalContent', '수정에 실패 하였습니다.')
        }
      )
    },
    noticeDelete: ({ commit }, no) => {
      return noticeDelete(
        no,
        () => {
          commit('SetshowModal', true)
          commit('SetmodalContent', '공지사항이 삭제되었습니다.')
        },
        () => {
          commit('SetshowModal', true)
          commit('SetmodalContent', '삭제에 실패 하였습니다.')
        }
      )
    }
  }
}

export default noticeStore
