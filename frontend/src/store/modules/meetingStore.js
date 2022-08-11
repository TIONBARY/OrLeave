import { enterMeeting } from '@/api/meeting.js'

const meetingStore = {
  namespaced: true,
  state: {},
  getters: {},
  mutations: {},
  actions: {
    enterSession({ commit }, sessionId) {
      return enterMeeting(sessionId)
    }
  }
}

export default meetingStore
