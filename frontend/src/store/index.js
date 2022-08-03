import createPersistedState from 'vuex-persistedstate'

import { createStore } from 'vuex'
import userStore from '@/store/modules/userStore.js'

export default createStore({
  modules: { userStore },

  plugins: [
    createPersistedState({
      storage: sessionStorage
    })
  ]
})
