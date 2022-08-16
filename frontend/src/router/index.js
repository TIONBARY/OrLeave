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
import ManagerNoticeList from '../components/manager/ManagerNoticeList.vue'
import ManagerNoticeRegist from '../components/manager/ManagerNoticeRegist.vue'
import ManagerNoticeDetail from '../components/manager/ManagerNoticeDetail.vue'
import ManagerNoticeModify from '../components/manager/ManagerNoticeModify.vue'
import ManagerInquiries from '../components/manager/ManagerInquiries.vue'
import ManagerInquiryDetail from '../components/manager/ManagerInquiryDetail.vue'

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

const onlyNotUser = async (to, from, next) => {
  const token = sessionStorage.getItem('Authorization')
  if (token) {
    alert(' 비정상적인 접근입니다.')
    next({ name: 'main' })
  }
}

const onlyUser = async (to, from, next) => {
  const token = sessionStorage.getItem('Authorization')
  if (!token) {
    alert(' 회원가입을 한 유저만 사용할 수 있습니다.')
    next({ name: 'main' })
  }
  const userType = jwtDecode(token).userType
  if (userType === 'BANNED') {
    alert('금지당한 유저입니다.')
    next({ name: 'main' })
  } else if (userType === 'USER' || userType === 'MANAGER') {
    next()
  } else {
    alert('권한이 없습니다 다시 확인해주세요')
    next({ name: 'main' })
  }
}

const onlyfromAccount = async (to, from, next) => {
  if (from.name !== 'UserAccountRegist') {
    alert('계정 정보를 먼저 입력해주세요')
    next({ name: 'UserAccountRegist' })
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
        beforeEnter: onlyNotUser,
        component: UserLogin
      },
      {
        path: 'login-kakao',
        name: 'kakaoAuth',
        beforeEnter: onlyNotUser,
        component: KakaoLogin
      },
      {
        path: 'login-naver',
        name: 'NaverAuth',
        beforeEnter: onlyNotUser,
        component: NaverLogin
      },
      {
        path: 'login-google',
        name: 'GoogleAuth',
        beforeEnter: onlyNotUser,
        component: GoogleLogin
      },
      {
        path: 'signup/account',
        name: 'UserAccountRegist',
        beforeEnter: onlyNotUser,
        component: UserAccountRegist
      },
      {
        path: 'signup/profile',
        name: 'UserProfileRegist',
        beforeEnter: onlyfromAccount,
        component: UserProfileRegist
      },
      {
        path: 'modify/account',
        name: 'UserAccountModify',
        beforeEnter: onlyUser,
        component: UserAccountModify
      },
      {
        path: 'modify/profile',
        name: 'UserProfileModify',
        beforeEnter: onlyUser,
        component: UserProfileModify
      },
      {
        path: 'forget/password',
        beforeEnter: onlyUser,
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
      },
      {
        path: 'notice/list',
        name: 'managerNotice',
        beforeEnter: onlyAuthManager,
        component: ManagerNoticeList
      },
      {
        path: 'notice/regist',
        name: 'managerNoticeRegist',
        component: ManagerNoticeRegist
      },
      {
        path: 'notice/:noticeNo',
        name: 'managerNoticeDetail',
        component: ManagerNoticeDetail
      },
      {
        path: 'notice/modify/:noticeNo',
        name: 'managerNoticeModify',
        component: ManagerNoticeModify
      },
      {
        path: 'inquiries',
        name: 'managerInquiries',
        beforeEnter: onlyAuthManager,
        component: ManagerInquiries
      },
      {
        path: 'inquiry/:inquiryNo',
        name: 'managerInquiryDetail',
        beforeEnter: onlyAuthManager,
        component: ManagerInquiryDetail
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
