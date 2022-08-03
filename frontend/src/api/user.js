import { apiInstance } from './index.js'

const api = apiInstance()

async function login(loginInfo, success, fail) {
  await api
    .post('/users/login', JSON.stringify(loginInfo))
    .then((success) => console.log(success))
    .catch((fail) => console.log(fail))
}

async function signup(signupInfo, success, fail) {
  console.log('hi')
  console.log(JSON.stringify(signupInfo))
  await api
    .post('/users', JSON.stringify(signupInfo))
    .then((success) => console.log(success))
    .catch((fail) => console.log(fail))
}

export { login, signup }
