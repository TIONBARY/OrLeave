import { login, signup } from '@/api/user.js'

const userStore = {
  namespaced: true,
  state: {
    isLogin: false,
    isLoginError: false,
    isRegistError: false,
    accountInfo: null
  },
  getters: {},
  mutations: {
    SET_IS_LOGIN: (state, isLogin) => {
      state.isLogin = isLogin
    },
    SET_IS_LOGIN_ERROR: (state, isLoginError) => {
      state.isLoginError = isLoginError
    },
    SET_IS_REGIST_ERROR: (state, isRegistError) => {
      state.isRegistError = isRegistError
    },
    SET_ACCOUNT_INFO: (state, accountInfo) => {
      state.accountInfo = accountInfo
    }
  },
  actions: {
    async tryLogin({ commit }, loginInfo) {
      console.log('hello')
      await login(
        loginInfo,
        (response) => {
          console.log('ssjasdhqwehiqwfhi')
          if (response.data.message === 'Success') {
            const token = response.data['access-token']
            commit('SET_IS_LOGIN', true)
            commit('SET_IS_LOGIN_ERROR', false)
            sessionStorage.setItem('access-token', token)
          } else {
            commit('SET_IS_LOGIN', false)
            commit('SET_IS_LOGIN_ERROR', true)
          }
        },
        (error) => {
          console.log(error)
        }
      )
    },
    async saveAccountInfo({ commit }, accountInfo) {
      await commit('SET_ACCOUNT_INFO', accountInfo)
    },
    async trySignup({ commit }, signupInfo) {
      await signup(
        signupInfo,
        (response) => {
          if (response.data.message === 'success') {
            commit('SET_IS_REGIST_ERROR', false)
          } else {
            commit('SET_IS_REGIST_ERROR', true)
            console.log('회원가입 실패!')
          }
        },
        (error) => {
          console.log(error)
        }
      )
    }
  }
}

export default userStore
