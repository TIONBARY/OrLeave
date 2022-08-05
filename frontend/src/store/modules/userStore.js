import { login, signup } from '@/api/user.js'

const userStore = {
  namespaced: true,
  state: {
    isLogin: false,
    isLoginError: false,
    isRegistError: false,
    userInfo: null
  },
  getters: {},
  mutations: {
    SET_IS_LOGIN: (state, isLogin) => {
      state.isLogin = isLogin
    },
    SET_USER_INFO: (state, userInfo) => {
      state.userInfo = userInfo
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
            console.log('로그인 성공!')
            //
            // 내 정보를 vuex에 저장하는 함수를 여기서 호출해야
            //
            sessionStorage.setItem('access-token', token)
          } else {
            commit('SET_IS_LOGIN', false)
            console.log('로그인 실패!')
          }
        },
        (error) => {
          console.log(error)
        }
      )
    },
    async trySignup({ commit }, signupInfo) {
      await signup(
        signupInfo,
        (response) => {
          if (response.data.message === 'success') {
            console.log('회원가입 성공!')
          } else {
            console.log('회원가입 실패!')
          }
        },
        (error) => {
          console.log(error)
        }
      )
    },
    async saveAccountInfo({ commit }, userInfo) {
      await commit('SET_USER_INFO', userInfo)
    }
  }
}

export default userStore
