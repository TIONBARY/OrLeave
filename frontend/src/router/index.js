import { createRouter, createWebHistory } from 'vue-router'
import MainView from '../views/MainView.vue'
import UserView from '../views/UserView.vue'
import MeetingView from '../views/MeetingView.vue'

import UserLogin from '../components/users/UserLogin.vue'
import UserAccountRegist from '../components/users/UserAccountRegist.vue'
import UserProfileRegist from '../components/users/UserProfileRegist.vue'
import UserAccountModify from '../components/users/UserAccountModify.vue'
import UserProfileModify from '../components/users/UserProfileModify.vue'
import MeetingLobby from '../components/meeting/MeetingLobby.vue'
import MeetingRoom from '../components/meeting/MeetingRoom.vue'

const routes = [
  {
    path: '/',
    component: MainView
  },
  {
    path: '/user',
    component: UserView,
    children: [
      {
        path: 'login',
        component: UserLogin
      },
      {
        path: 'signup/account',
        component: UserAccountRegist
      },
      {
        path: 'signup/profile',
        component: UserProfileRegist
      },

      // 추가
      {
        path: 'modify1',
        component: UserAccountModify
      },
      {
        path: 'modify2',
        component: UserProfileModify
      }
      // 추가
    ]
  },
  {
    path: '/meeting',
    component: MeetingView,
    children: [
      { path: 'lobby', component: MeetingLobby },
      { path: 'room', component: MeetingRoom }
    ]
  }
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
