import { createRouter, createWebHistory } from 'vue-router'
import MainView from '../views/MainView.vue'
import UserView from '../views/UserView.vue'
import MeetingView from '../views/MeetingView.vue'
import NoticeView from '../views/NoticeView.vue'
import InquiryView from '../views/InquiryView.vue'

import UserLogin from '../components/users/UserLogin.vue'
import UserAccountRegist from '../components/users/UserAccountRegist.vue'
import UserProfileRegist from '../components/users/UserProfileRegist.vue'
import UserAccountModify from '../components/users/UserAccountModify.vue'
import UserProfileModify from '../components/users/UserProfileModify.vue'
import MeetingLobby from '../components/meeting/MeetingLobby.vue'
import MeetingRoom from '../components/meeting/MeetingRoom.vue'
import NoticeList from '../components/notices/NoticeList.vue'
import NoticeDetail from '../components/notices/NoticeDetail.vue'
import InquiryList from '../components/inquiries/InquiryList.vue'
import InquiryDetail from '../components/inquiries/InquiryDetail.vue'
import InquiryRegist from '../components/inquiries/InquiryRegist.vue'

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
      {
        path: 'lobby',
        component: MeetingLobby
      },
      {
        path: 'room',
        component: MeetingRoom
      },
      {
        path: 'test',
        component: MeetingTest
      }
    ]
  },
  {
    path: '/notice',
    component: NoticeView,
    children: [
      {
        path: 'list',
        component: NoticeList
      },
      {
        path: ':articleno',
        component: NoticeDetail
      }
    ]
  },
  {
    path: '/inquiry',
    component: InquiryView,
    children: [
      {
        path: 'list',
        component: InquiryList
      },
      {
        path: ':articleno',
        component: InquiryDetail
      },
      {
        path: 'regist',
        component: InquiryRegist
      }
    ]
  }
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
