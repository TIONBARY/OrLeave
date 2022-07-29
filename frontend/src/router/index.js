import { createRouter, createWebHistory } from 'vue-router'
import MainView from '../views/MainView.vue'
import UserView from '../views/UserView.vue'

import UserLogin from '../components/users/UserLogin.vue'
import UserAccountRegist from '../components/users/UserAccountRegist.vue'
import userProfileRegist from '../components/users/UserProfileRegist.vue'
import userProfileRegist1 from '../components/users/UserProfileRegist copy.vue'
import userProfileRegist2 from '../components/users/UserProfileRegist copy 2.vue'

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
      },
      {
        path: 'signup/profile1',
        component: userProfileRegist1
      },
      {
        path: 'signup/profile2',
        component: userProfileRegist2
      }
    ]
  }
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
