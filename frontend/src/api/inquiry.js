import { apiInstance } from './index.js'

const api = apiInstance()

function inquiryList(page, success, fail) {
  const params = { page: page, size: 5 }
  const Authorization = 'Bearer ' + sessionStorage.getItem('Authorization')
  return api.get('/inquiries', {
    params,
    headers: {
      Authorization: Authorization
    }
  }).then(success).catch(fail)
}

function inquiryRegist(inquiry, success, fail) {
  const Authorization = 'Bearer ' + sessionStorage.getItem('Authorization')
  return api.post('/inquiries', JSON.stringify(inquiry), {
    headers: {
      Authorization: Authorization
    }
  }).then(success).catch(fail)
}

function inquiryDetail(inquiryNo, success, fail) {
  const Authorization = 'Bearer ' + sessionStorage.getItem('Authorization')
  return api.get(`/inquiries/${inquiryNo}`, {
    headers: {
      Authorization: Authorization
    }
  }).then(success).catch(fail)
}

function inquiryModify(inquiryNo, inquiry, success, fail) {
  const Authorization = 'Bearer ' + sessionStorage.getItem('Authorization')
  return api.put(`inquiries/${inquiryNo}`, JSON.stringify(inquiry), {
    headers: {
      Authorization: Authorization
    }
  }).then(success).catch(fail)
}

function inquiryDelete(inquiryNo, success, fail) {
  const Authorization = 'Bearer ' + sessionStorage.getItem('Authorization')
  return api.delete(`inquiries/${inquiryNo}`, {
    headers: {
      Authorization: Authorization
    }
  }).then(success).catch(fail)
}

export {
  inquiryList,
  inquiryRegist,
  inquiryDetail,
  inquiryModify,
  inquiryDelete
}
