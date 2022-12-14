import {
  tryLogin,
  checkEmailExist,
  setConfirmKey,
  trySignup,
  requestProfile,
  requestModifyProfile,
  getBanuser,
  deleteBanuser,
  getmeetinglog,
  addBanuser
} from '@/api/user.js'

const userStore = {
  namespaced: true,
  state: {
    isLogin: false,
    signupInfo: null,
    profile: null,
    banuserlist: [],
    meetinguserlist: []
  },
  getters: {},
  mutations: {
    SET_IS_LOGIN: (state, isLogin) => {
      state.isLogin = isLogin
    },
    SET_SIGNUP_INFO: (state, signupInfo) => {
      state.signupInfo = signupInfo
    },
    SET_PROFILE: (state, profile) => {
      state.profile = profile
    },
    SET_DUPLICATED: (state, value) => {
      state.isDuplicated = value
    },
    SET_BANUSER_LIST: (state, payload) => {
      state.banuserlist = payload
    },
    SET_MEETINGUSER_LIST: (state, payload) => {
      state.meetinguserlist = payload
    }
  },
  actions: {
    async login({ commit }, loginInfo) {
      await tryLogin(
        loginInfo,
        (response) => {
          if (response.data.statusCode === 200) {
            const token = response.data.authorization
            commit('SET_IS_LOGIN', true)
            sessionStorage.setItem('Authorization', token)
          } else {
            commit('SET_IS_LOGIN', false)
          }
        },
        (error) => {
          console.warn(error)
        }
      )
    },

    // 메뉴바에 버튼을 만들어서 연결해야 함
    async logout({ commit }) {
      commit('SET_LOGOUT')
      commit('SET_IS_LOGIN', false)
      await sessionStorage.removeItem('Authorization')
      location.reload()
    },

    // 버튼 연결 아직 안함...
    async checkEmail({ commit }, email) {
      await checkEmailExist(
        email,
        () => {},
        (error) => console.warn(error)
      )
    },

    // 버튼 연결 아직 안함...
    async sendConfirmKey({ commit }, email) {
      await setConfirmKey(
        email,
        () => {},
        (error) => console.warn(error)
      )
    },

    async saveSignupInfo({ commit }, signupInfo) {
      await commit('SET_SIGNUP_INFO', signupInfo)
    },

    async signup({ commit }, signupInfo) {
      await trySignup(
        signupInfo,
        () => {},
        (error) => {
          console.warn(error)
        }
      )
    },

    // 버튼 연결 아직 안함...
    async getProfile({ commit }) {
      await requestProfile(
        (response) => {
          commit('SET_PROFILE', response.data.profile)
        },
        (error) => console.warn(error)
      )
    },

    async modifyProfile({ commit }) {
      await requestModifyProfile(
        this.profile,
        (response) => {
          if (response.data.statusCode === 200) {
            const token = response.data.authorization
            sessionStorage.setItem('Authorization', token)
          }
        },
        (error) => {
          console.warn(error)
        }
      )
    },

    async getBanuser({ commit }) {
      await getBanuser(
        (response) => {
          commit('SET_BANUSER_LIST', response.data.banList)
        },
        (error) => {
          console.warn(error)
        }
      )
    },

    async addBanuser({ commit }, bannedNo) {
      await addBanuser(
        bannedNo,
        (response) => {},
        (error) => {
          if (error.response.data.statusCode === 400) {
            console.warn('이미 차단된 사용자 입니다.')
          }
        }
      )
    },

    async deleteBanuser({ commit }, no) {
      await deleteBanuser(
        no,
        (response) => {},
        (error) => {
          console.warn(error)
        }
      )
    },

    async getmeetinglog({ commit }) {
      await getmeetinglog(
        (response) => {
          commit('SET_MEETINGUSER_LIST', response.data.meetingLogList)
        },
        (error) => {
          console.warn(error)
        }
      )
    }
  }
}

export default userStore
