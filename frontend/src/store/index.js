import createPersistedState from 'vuex-persistedstate'

import { createStore } from 'vuex'
import userStore from '@/store/modules/userStore.js'
import noticeStore from '@/store/modules/noticeStore.js'
import meetingStore from '@/store/modules/meetingStore.js'
import inquiryStore from '@/store/modules/inquiryStore.js'

export default createStore({
  modules: { userStore, noticeStore, meetingStore, inquiryStore },

  plugins: [
    createPersistedState({
      storage: sessionStorage
    })
  ]
})
