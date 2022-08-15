import { apiInstance } from './index.js'

const api = apiInstance()

function getMeetingSetting(success, fail) {
  const Authorization = 'Bearer ' + sessionStorage.getItem('Authorization')
  return api
    .get('/meeting/setting', {
      headers: {
        Authorization: Authorization
      }
    })
    .then(success)
    .catch(fail)
}

async function setMeetingSetting(setting, success, fail) {
  const Authorization = 'Bearer ' + sessionStorage.getItem('Authorization')
  await api
    .put('/meeting/setting', JSON.stringify(setting), {
      headers: {
        Authorization: Authorization
      }
    })
    .then(success)
    .catch(fail)
}

function startMatching(location, success, fail) {
  const Authorization = 'Bearer ' + sessionStorage.getItem('Authorization')
  api
    .post('/matching', JSON.stringify(location), {
      headers: {
        Authorization: Authorization
      }
    })
    .then(success)
    .catch(fail)
}

function checkMatching(success, fail) {
  const Authorization = 'Bearer ' + sessionStorage.getItem('Authorization')
  return api
    .get('/matching', {
      headers: {
        Authorization: Authorization
      }
    })
    .then(success)
    .catch(fail)
}

function successMatching(userNo, success, fail) {
  const Authorization = 'Bearer ' + sessionStorage.getItem('Authorization')
  return api
    .get('/matching/success/' + userNo, {
      headers: {
        Authorization: Authorization
      }
    })
    .then(success)
    .catch(fail)
}

async function stopMatching(success, fail) {
  const Authorization = 'Bearer ' + sessionStorage.getItem('Authorization')
  await api
    .get('/matching/stop', {
      headers: {
        Authorization: Authorization
      }
    })
    .then(success)
    .catch(fail)
}

function enterMeeting(sessionId) {
  const authorization = 'Bearer ' + sessionStorage.getItem('Authorization')
  const sessionObject = { sessionName: sessionId }
  return api.post('/session/enter', JSON.stringify(sessionObject), {
    headers: {
      authorization: authorization
    }
  })
}

async function leaveMeeting(sessionId, sessionToken, success, fail) {
  const Authorization = 'Bearer ' + sessionStorage.getItem('Authorization')

  const requestBody = {
    sessionName: sessionId,
    token: sessionToken
  }
  await api
    .post('/session/leave', JSON.stringify(requestBody), {
      headers: {
        Authorization: Authorization
      }
    })
    .then(success)
    .catch(fail)
}

async function getBanList(success, fail) {
  const Authorization = 'Bearer ' + sessionStorage.getItem('Authorization')
  await api
    .get('/ban', {
      headers: {
        Authorization: Authorization
      }
    })
    .then(success)
    .catch(fail)
}

async function getRecentCall(success, fail) {
  const Authorization = 'Bearer ' + sessionStorage.getItem('Authorization')
  await api
    .get('/meeting/recent-call', {
      headers: {
        Authorization: Authorization
      }
    })
    .then(success)
    .catch(fail)
}

async function addBanList(bannedNo, success, fail) {
  const Authorization = 'Bearer ' + sessionStorage.getItem('Authorization')
  const requestBody = {
    bannedNo: bannedNo
  }
  await api
    .post('/ban', JSON.stringify(requestBody), {
      headers: {
        Authorization: Authorization
      }
    })
    .then(success)
    .catch(fail)
}

async function deleteBanUser(bannedNo, success, fail) {
  const Authorization = 'Bearer ' + sessionStorage.getItem('Authorization')
  await api
    .delete('/ban/' + bannedNo, {
      headers: {
        Authorization: Authorization
      }
    })
    .then(success)
    .catch(fail)
}

// report: {
//   reported_no: num,
//   category: num,
//   content: 'msg'
// }
async function reportUser(report, success, fail) {
  const Authorization = 'Bearer ' + sessionStorage.getItem('Authorization')
  await api
    .post('/meeting/recent-call', JSON.stringify(report), {
      headers: {
        Authorization: Authorization
      }
    })
    .then(success)
    .catch(fail)
}

export {
  enterMeeting,
  getMeetingSetting,
  setMeetingSetting,
  startMatching,
  checkMatching,
  successMatching,
  stopMatching,
  leaveMeeting,
  getBanList,
  getRecentCall,
  addBanList,
  deleteBanUser,
  reportUser
}
