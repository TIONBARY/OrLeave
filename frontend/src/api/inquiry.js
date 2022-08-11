import { apiInstance } from './index.js'

const api = apiInstance()

function inquiryList(page, size, success, fail) {
  const Authorization = 'Bearer ' + sessionStorage.getItem('Authorization')
  console.log(Authorization)
  return api.get('/inquiries', page, size
  // {
  //   headers: {
  //     Authorization: Authorization
  //   }
  // }
  ).then(success).catch(fail)
}

function inquiryRegist(inquiry, success, fail) {
  const Authorization = 'Bearer ' + sessionStorage.getItem('Authorization')
  return api.post('/inquiries', JSON.stringify(inquiry), {
    headers: {
      Authorization: Authorization
    }
  })
    .then(success)
    .catch(fail)
}

function inquiryDetail(inquiryNo, success, fail) {
  return api.get(`/inquiries/${inquiryNo}`).then(success).catch(fail)
}

function inquiryModify(inquiry, inquiryNo, success, fail) {
  return api
    .put(`inquiries/${inquiryNo}`, JSON.stringify(inquiry))
    .then(success)
    .catch(fail)
}

function inquiryDelete(inquiryNo, success, fail) {
  return api.delete(`inquiryies/${inquiryNo}`).then(success).catch(fail)
}

export {
  inquiryList,
  inquiryRegist,
  inquiryDetail,
  inquiryModify,
  inquiryDelete
}
