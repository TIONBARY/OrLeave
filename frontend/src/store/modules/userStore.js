import {
  tryLogin,
  checkEmailExist,
  setConfirmKey,
  trySignup,
  requestProfile
} from '@/api/user.js'

const userStore = {
  namespaced: true,
  state: {
    isLogin: false,
    signupInfo: null,
    profile: null
  },
  getters: {},
  mutations: {
    SET_IS_LOGIN: (state, isLogin) => {
      state.isLogin = isLogin
    },
    SET_LOGOUT: (state) => {
      state.isLogin = false
    },
    SET_SIGNUP_INFO: (state, signupInfo) => {
      state.signupInfo = signupInfo
    },
    SET_PROFILE: (state, profile) => {
      state.profile = profile
    }
  },
  actions: {
    async login({ commit }, loginInfo) {
      await tryLogin(
        loginInfo,
        (response) => {
          if (response.data.statusCode === 200) {
            const token = response.data.accessToken // 이걸 Authorization으로 바꿔주면 다됨!
            commit('SET_IS_LOGIN', true)
            console.log('로그인 성공!')
            sessionStorage.setItem('Authorization', token)
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

    // 메뉴바에 버튼을 만들어서 연결해야 함
    logout({ commit }) {
      commit('SET_LOGOUT')
      sessionStorage.removeItem('Authorization')
      location.reload()
    },

    // 버튼 연결 아직 안함...
    checkEmail({ commit }, email) {
      return checkEmailExist(
        email,
        (response) => {
          if (response.data.statusCode === 200) {
            console.log("email doesn't exist, SUCCESS!")
          } else {
            console.log('email exists, FAIL!')
          }
        },
        (error) => console.log(error)
      )
    },

    // 버튼 연결 아직 안함...
    async sendConfirmKey({ commit }, email) {
      await setConfirmKey(
        email,
        (response) => {
          if (response.data.statusCode === 200) {
            console.log('succeeded to send confirm key')
          } else {
            console.log('failed to send confirm key')
          }
        },
        (error) => console.log(error)
      )
    },

    async saveSignupInfo({ commit }, signupInfo) {
      await commit('SET_SIGNUP_INFO', signupInfo)
    },

    async signup({ commit }, signupInfo) {
      await trySignup(
        signupInfo,
        (response) => {
          if (response.data.statusCode === 200) {
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

    // 버튼 연결 아직 안함...
    async getProfile({ commit }) {
      await requestProfile(
        (response) => {
          commit('SET_PROFILE', response.data.profile)
          console.log(response.data.profile)
          console.log('is it undefined?' + this.profile)
        },
        (error) => console.log(error)
      )
    }
  }
}

export default userStore
