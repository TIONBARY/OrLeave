import { apiInstance } from './index.js'

const api = apiInstance()

async function managerLogin(loginInfo, success, fail) {
  await api
    .post('/manager/login', JSON.stringify(loginInfo))
    .then(success)
    .catch(fail)
}

async function getAllUsers(page, size, success, fail) {
  const Authorization = 'Bearer ' + sessionStorage.getItem('Authorization')
  await api
    .get('/manager/users', {
      headers: { Authorization },
      params: { page, size }
    })
    .then(success)
    .catch(fail)
}

async function getUserReportList(page, size, userNo, success, fail) {
  const Authorization = 'Bearer ' + sessionStorage.getItem('Authorization')
  await api
    .get(`/manager/users/${userNo}`, {
      headers: { Authorization },
      params: { page, size }
    })
    .then(success)
    .catch(fail)
}

async function getReportDetail(reportNo, success, fail) {
  const Authorization = 'Bearer ' + sessionStorage.getItem('Authorization')
  await api
    .get(`/manager/reports/${reportNo}`, { headers: { Authorization } })
    .then(success)
    .catch(fail)
}

async function BanUser(no, success, fail) {
  const Authorization = 'Bearer ' + sessionStorage.getItem('Authorization')
  await api
    .put('/manager/ban', JSON.stringify({ no }), { headers: { Authorization } })
    .then(success)
    .catch(fail)
}

async function ModifyNickname(no, nickname, success, fail) {
  const Authorization = 'Bearer ' + sessionStorage.getItem('Authorization')
  await api
    .put('/manager/nickname', JSON.stringify({ no, nickname }), {
      headers: { Authorization }
    })
    .then(success)
    .catch(fail)
}

async function getInquiries(page, size, success, fail) {
  const Authorization = 'Bearer ' + sessionStorage.getItem('Authorization')
  await api
    .get('/manager/inquiries', {
      headers: { Authorization },
      params: { page, size }
    })
    .then(success)
    .catch(fail)
}

async function answerInquiry(inquiry, success, fail) {
  const Authorization = 'Bearer ' + sessionStorage.getItem('Authorization')
  await api
    .put('/manager/inquiry/answer', JSON.stringify(inquiry), {
      headers: { Authorization }
    })
    .then(success)
    .catch(fail)
}

function inquiryDetail(inquiryNo, success, fail) {
  const Authorization = 'Bearer ' + sessionStorage.getItem('Authorization')
  return api
    .get(`/manager/inquiries/${inquiryNo}`, {
      headers: {
        Authorization: Authorization
      }
    })
    .then(success)
    .catch(fail)
}

export {
  managerLogin,
  getAllUsers,
  getUserReportList,
  getReportDetail,
  BanUser,
  ModifyNickname,
  getInquiries,
  answerInquiry,
  inquiryDetail
}
