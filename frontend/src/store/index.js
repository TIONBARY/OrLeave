import createPersistedState from 'vuex-persistedstate'

import { createStore } from 'vuex'
import userStore from '@/store/modules/userStore.js'
import noticeStore from '@/store/modules/noticeStore.js'

export default createStore({
  modules: { userStore, noticeStore },

  plugins: [
    createPersistedState({
      storage: sessionStorage
    })
  ]
})
