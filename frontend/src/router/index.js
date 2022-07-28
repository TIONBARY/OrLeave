import { createRouter, createWebHistory } from 'vue-router'
import MainView from '../views/MainView.vue'
import UserView from '../views/UserView.vue'

import UserLogin from '../components/users/UserLogin.vue'
import UserAccountRegist from '../components/users/UserAccountRegist.vue'
import userProfileRegist from '../components/users/UserProfileRegist.vue'

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
  }
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
