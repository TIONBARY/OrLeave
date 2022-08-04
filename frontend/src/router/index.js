import { createRouter, createWebHistory } from 'vue-router'
import MainView from '../views/MainView.vue'
import UserView from '../views/UserView.vue'
import MeetingView from '../views/MeetingView.vue'

import UserLogin from '../components/users/UserLogin.vue'
import UserAccountRegist from '../components/users/UserAccountRegist.vue'
import userProfileRegist from '../components/users/UserProfileRegist.vue'
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
        component: userProfileRegist
      }
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
