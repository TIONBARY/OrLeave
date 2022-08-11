import { apiInstance } from './index.js'

const api = apiInstance()

async function trySignup(signupInfo, success, fail) {
  await api.post('/users', JSON.stringify(signupInfo)).then(success).catch(fail)
}
async function setConfirmKey(email, success, fail) {
  await api
    .post('/users/email', JSON.stringify({ email: email }))
    .then(success)
    .catch(fail)
}
// @@@@@ 이메일 인증코드 확인 @@@@@
async function confirmEmailCode(REQUEST, success, fail) {
  await api
    .post('users/code', JSON.stringify(REQUEST))
    .then(success)
    .catch(fail)
}
async function checkEmailExist(email, success, fail) {
  await api
    .get('/users/email?email=' + email.replaceAll('@', '%40'))
    .then(success)
    .catch(fail)
}
// @@@@@ 닉네임 중복여부 확인 @@@@@
async function checkNicknameExist(nickname, success, fail) {
  await api
    .get('/users/nickname?nickname=' + nickname)
    .then(success)
    .catch(fail)
}
async function tryLogin(loginInfo, success, fail) {
  await api
    .post('/users/login', JSON.stringify(loginInfo))
    .then(success)
    .catch(fail)
}

async function kakaoLogin(token, success, fail) {
  await api
    .get('/users/login-kakao', {
      headers: {
        token: token
      }
    })
    .then(success)
    .catch(fail)
}

async function naverLogin(token, success, fail) {
  await api
    .get('/users/login-naver', {
      headers: {
        token: token
      }
    })
    .then(success)
    .catch(fail)
}

async function googleLogin(code, success, fail) {
  await api
    .get(`/users/login-google?code=${code}`)
    .then(success)
    .catch(fail)
}
// ? 로그아웃 필요한지?
// function
//
// @@@@@ 회원탈퇴 @@@@@
async function withdrawal(success, fail) {
  const Authorization = 'Bearer ' + sessionStorage.getItem('Authorization')
  await api
    .delete('/users', {
      headers: {
        Authorization: Authorization
      }
    })
    .then(success)
    .catch(fail)
}
// @@@@@ 비밀번호 재설정 @@@@@
async function modifyPassword(REQUEST, success, fail) {
  const Authorization = 'Bearer ' + sessionStorage.getItem('Authorization')
  await api
    .put('/users/password', JSON.stringify(REQUEST), {
      headers: {
        Authorization: Authorization
      }
    })
    .then(success)
    .catch(fail)
}

async function modifyPasswordWithoutAuth(REQUEST, success, fail) {
  await api
    .put('/users/password/noAuth', JSON.stringify(REQUEST))
    .then(success)
    .catch(fail)
}
// ? 비밀번호 검사 무슨 용도였지?
async function checkPassword(REQUEST, success, fail) {
  const Authorization = 'Bearer ' + sessionStorage.getItem('Authorization')
  await api
    .post('/users/password', JSON.stringify(REQUEST), {
      headers: {
        Authorization: Authorization
      }
    })
    .then(success)
    .catch(fail)
}
//
// 테스트 안해봄
async function requestModifyProfile(profile, success, fail) {
  const Authorization = 'Bearer ' + sessionStorage.getItem('Authorization')
  await api
    .put('/users/profile', JSON.stringify(profile), {
      headers: {
        Authorization: Authorization
      }
    })
    .then(success)
    .catch(fail)
}
async function requestProfile(success, fail) {
  const Authorization = 'Bearer ' + sessionStorage.getItem('Authorization')
  await api
    .get('/users/profile', {
      headers: {
        Authorization: Authorization
      }
    })
    .then(success)
    .catch(fail)
}

async function getEmail(success, fail) {
  const Authorization = 'Bearer ' + sessionStorage.getItem('Authorization')
  await api
    .get('/users/userInfo', {
      headers: {
        Authorization: Authorization
      }
    })
    .then(success)
    .catch(fail)
}

export {
  trySignup,
  setConfirmKey,
  confirmEmailCode, // 이메일 인증코드 확인
  checkEmailExist,
  checkNicknameExist, // 닉네임 중복여부 확인
  tryLogin,
  kakaoLogin,
  naverLogin,
  googleLogin,
  // 로그아웃
  withdrawal, // 회원탈퇴
  modifyPassword, // 비밀번호 재설정
  modifyPasswordWithoutAuth,
  checkPassword, // 비밀번호 검사
  requestModifyProfile,
  requestProfile,
  getEmail
}
