import { apiInstance } from './index.js'

const api = apiInstance()

function inquiryList(page, size, success, fail) {
  return api.get('/inquiries', page, size).success(success).fail(fail)
}

function inquiryRegist(inquiry, success, fail) {
  return api
    .post('/inquiries', JSON.stringify(inquiry))
    .success(success)
    .fail(fail)
}

function inquiryDetail(inquiryNo, success, fail) {
  return api.get('/inquiries/${inquiryNo}').success(success).fail(fail)
}

function inquiryModify(inquiry, inquiryNo, success, fail) {
  return api
    .put('inquiries/${inquiryNo}', JSON.stringify(inquiry))
    .success(success)
    .fail(fail)
}

function inquiryDelete(inquiryNo, success, fail) {
  return api.delete('inquiryies/${inquiryNo}').success(success).fail(fail)
}

export {
  inquiryList,
  inquiryRegist,
  inquiryDetail,
  inquiryModify,
  inquiryDelete
}
