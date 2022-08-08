import { apiInstance } from './index.js'

const api = apiInstance()

async function tryLogin(loginInfo, success, fail) {
  await api
    .post('/users/login', JSON.stringify(loginInfo))
    .then(success)
    .catch(fail)
}

function checkEmailExist(email, success, fail) {
  api
    .get('/users/email?email=' + email)
    .then(success)
    .catch(fail)
}

async function setConfirmKey(email, success, fail) {
  api.post('/users/email', JSON.stringify(email)).then(success).catch(fail)
}

async function trySignup(signupInfo, success, fail) {
  await api.post('/users', JSON.stringify(signupInfo)).then(success).catch(fail)
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

export { tryLogin, checkEmailExist, setConfirmKey, trySignup, requestProfile }
