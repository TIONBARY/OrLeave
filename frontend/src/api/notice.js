import { apiInstance } from './index.js'

const api = apiInstance()

async function noticeList(page, size, success, fail) {
  await api
    .get('/notices', page, size)
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
