import { createRouter, createWebHistory } from 'vue-router'
import MainView from '../views/MainView.vue'
import UserView from '../views/UserView.vue'
import MeetingView from '../views/MeetingView.vue'
import NoticeView from '../views/NoticeView.vue'
import InquiryView from '../views/InquiryView.vue'
import ManagerView from '../views/ManagerView.vue'
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
import InquiryModify from '../components/inquiries/InquiryModify.vue'
import ManagerLogin from '../components/manager/ManagerLogin.vue'
import ManagerMain from '../components/manager/ManagerMain.vue'
import ManagerUsers from '../components/manager/ManagerUsers.vue'

import jwtDecode from 'jwt-decode'

const onlyAuthManager = async (to, from, next) => {
  const token = sessionStorage.getItem('Authorization')
  if (!token) {
    next({ name: 'managerLogin' })
  }
  const userType = jwtDecode(token).userType
  if (userType === 'MANAGER') {
    next()
  } else if (userType === 'USER') {
    alert('관리자만 접근할 수 있는 페이지입니다.')
    next({ name: 'main' })
  }
}

const routes = [
  {
    path: '/',
    component: MainView,
    name: 'main'
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
        path: ':inquiryNo',
        component: InquiryDetail
      },
      {
        path: 'regist',
        component: InquiryRegist
      },
      {
        path: 'modify/:inquiryNo',
        component: InquiryModify
      }
    ]
  },
  {
    path: '/manager',
    component: ManagerView,
    children: [
      {
        path: 'login',
        name: 'managerLogin',
        component: ManagerLogin
      },
      {
        path: 'main',
        name: 'managerMain',
        beforeEnter: onlyAuthManager,
        component: ManagerMain
      },
      {
        path: 'users',
        name: 'managerUsers',
        beforeEnter: onlyAuthManager,
        component: ManagerUsers
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
