import { apiInstance } from './index.js'

const api = apiInstance()

async function noticeList(page, success, fail) {
  const params = { page: page, size: 5 }
  await api
    .get('/notices', { params })
    .then(success)
    .catch(fail)
}

async function noticeDetail(no, success, fail) {
  await api
    .get(`/notices/${no}`)
    .then(success)
    .catch(fail)
}

function noticeRegist(notice, success, fail) {
  const Authorization = 'Bearer ' + sessionStorage.getItem('Authorization')
  return api.post('/manager/notices', JSON.stringify(notice), {
    headers: {
      Authorization: Authorization
    }
  }).then(success).catch(fail)
}

export {
  noticeList,
  noticeDetail,
  noticeRegist
}
