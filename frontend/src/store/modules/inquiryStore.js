import {
  inquiryList,
  inquiryRegist,
  inquiryDetail
  // inquiryModify,
  // inquiryDelete
} from '@/api/inquiry.js'

const inquiryStore = {
  namespaced: true,
  state: { inquiries: [], inquiry: {} },
  getters: {},
  mutations: {
    setInquiries(state, payload) {
      state.inquiries = payload
    },
    setInquiry(state, payload) {
      state.inquiry = payload
    }
  },
  actions: {
    inquiryList: ({ commit }, page, size) => {
      return inquiryList(
        page,
        size,
        ({ data }) => {
          commit('setInquiries', data)
        },
        (error) => {
          console.log(error)
        }
      )
    },
    inquiryDetail: ({ commit }, inquiryNo) => {
      return inquiryDetail(
        inquiryNo,
        ({ data }) => {
          commit('setInquiry', data)
        },
        (error) => {
          console.log(error)
        }
      )
    },
    inquiryRegist: (context, inquiry) => {
      return inquiryRegist(
        inquiry,
        () => {},
        (error) => {
          console.log(error)
        }
      )
    }
  }
}

export default inquiryStore
