import { apiInstance } from './index.js'

const api = apiInstance()

async function noticeList(page, success, fail) {
  const params = { page: page, size: 3 }
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

export {
  noticeList,
  noticeDetail
}
