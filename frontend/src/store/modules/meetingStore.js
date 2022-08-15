import { enterMeeting, leaveMeeting } from '@/api/meeting.js'

const meetingStore = {
  namespaced: true,
  state: {},
  getters: {},
  mutations: {},
  actions: {
    enterSession({ commit }, sessionId) {
      return enterMeeting(sessionId)
    },
    leaveSession({ commit }, { sessionId, token }) {
      return leaveMeeting(sessionId, token)
    }
  }
}

export default meetingStore
