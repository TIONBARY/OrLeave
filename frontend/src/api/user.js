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
async function checkNicknameExist(REQUEST, success, fail) {
  await api
    .get('/users/nickname?nickname=' + REQUEST)
    .then(success)
    .catch(fail)
}
async function tryLogin(loginInfo, success, fail) {
  await api
    .post('/users/login', JSON.stringify(loginInfo))
    .then(success)
    .catch(fail)
}
// ? 로그아웃 필요한지?
// function
//
// @@@@@ 회원탈퇴 @@@@@
async function withdrawal(success, fail) {
  const Authorization = 'Bearer ' + sessionStorage.getItem('Authorization')
  console.log(Authorization)
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
  console.log(Authorization)
  await api
    .put('/users/password'.JSON.stringify(REQUEST), {
      headers: {
        Authorization: Authorization
      }
    })
    .then(success)
    .catch(fail)
}
// ? 비밀번호 검사 무슨 용도였지?
async function checkPassword(REQUEST, success, fail) {
  const Authorization = 'Bearer ' + sessionStorage.getItem('Authorization')
  console.log(Authorization)
  await api
    .post('/users/password'.JSON.stringify(REQUEST), {
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
  console.log(Authorization)
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
  console.log(Authorization)
  await api
    .get('/users/profile', {
      headers: {
        Authorization: Authorization
      }
    })
    .then(success)
    .catch(fail)
}
// ? 토큰확인 이거 무슨 용도지?
// function
//

export {
  trySignup,
  setConfirmKey,
  confirmEmailCode, // 이메일 인증코드 확인
  checkEmailExist,
  checkNicknameExist, // 닉네임 중복여부 확인
  tryLogin,
  // 로그아웃
  withdrawal, // 회원탈퇴
  modifyPassword, // 비밀번호 재설정
  checkPassword, // 비밀번호 검사
  requestModifyProfile,
  requestProfile
  // 토큰확인
}
