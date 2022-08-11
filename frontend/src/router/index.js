import { createRouter, createWebHistory } from 'vue-router'
import MainView from '../views/MainView.vue'
import UserView from '../views/UserView.vue'
import MeetingView from '../views/MeetingView.vue'
import NoticeView from '../views/NoticeView.vue'
import InquiryView from '../views/InquiryView.vue'
import ErrorView from '../views/ErrorView.vue'

import UserLogin from '../components/users/UserLogin.vue'
import KakaoLogin from '../components/users/KakaoLogin.vue'
import NaverLogin from '../components/users/NaverLogin.vue'
import GoogleLogin from '../components/users/GoogleLogin.vue'
import UserAccountRegist from '../components/users/UserAccountRegist.vue'
import UserProfileRegist from '../components/users/UserProfileRegist.vue'
import UserAccountModify from '../components/users/UserAccountModify.vue'
import UserPasswordForget from '../components/users/UserPasswordForget.vue'
import UserProfileModify from '../components/users/UserProfileModify.vue'
import MeetingLobby from '../components/meeting/MeetingLobby.vue'
import MeetingRoom from '../components/meeting/MeetingRoom.vue'
import MeetingTest from '../components/meeting/MeetingTest.vue'
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
        path: 'login-kakao',
        name: 'kakaoAuth',
        component: KakaoLogin
      },
      {
        path: 'login-naver',
        name: 'NaverAuth',
        component: NaverLogin
      },
      {
        path: 'login-google',
        name: 'GoogleAuth',
        component: GoogleLogin
      },
      {
        path: 'signup/account',
        component: UserAccountRegist
      },
      {
        path: 'signup/profile',
        component: UserProfileRegist
      },
      {
        path: 'modify/account',
        component: UserAccountModify
      },
      {
        path: 'modify/profile',
        component: UserProfileModify
      },
      {
        path: 'forget/password',
        component: UserPasswordForget
      }
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
  },
  {
    path: '/404',
    component: ErrorView
  },
  {
    path: '/:pathMatch(.*)*',
    redirect: '/404'
  }
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
