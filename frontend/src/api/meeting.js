import { apiInstance } from './index.js'

const api = apiInstance()

function getMeetingSetting(success, fail) {
  const Authorization = 'Bearer ' + sessionStorage.getItem('Authorization')
  api
    .get('/meeting/setting', {
      headers: {
        Authorization: Authorization
      }
    })
    .then(success)
    .catch(fail)
}

function setMeetingSetting(setting, success, fail) {
  const Authorization = 'Bearer ' + sessionStorage.getItem('Authorization')
  api
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
  api
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
  api
    .get('/matching/success/' + userNo, {
      headers: {
        Authorization: Authorization
      }
    })
    .then(success)
    .catch(fail)
}

function stopMatching(success, fail) {
  const Authorization = 'Bearer ' + sessionStorage.getItem('Authorization')
  api
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
  console.log('세션: ' + sessionId)
  return api.post('/session/enter', JSON.stringify(sessionObject), {
    headers: {
      authorization: authorization
    }
  })
}

function leaveMeeting(sessionId, sessionToken, success, fail) {
  const Authorization = 'Bearer ' + sessionStorage.getItem('Authorization')

  const requestBody = {
    sessionName: sessionId,
    token: sessionToken
  }
  api
    .post('/session/leave', JSON.stringify(requestBody), {
      headers: {
        Authorization: Authorization
      }
    })
    .then(success)
    .catch(fail)
}

function getBanList(success, fail) {
  const Authorization = 'Bearer ' + sessionStorage.getItem('Authorization')
  api
    .get('/ban', {
      headers: {
        Authorization: Authorization
      }
    })
    .then(success)
    .catch(fail)
}

function getRecentCall(success, fail) {
  const Authorization = 'Bearer ' + sessionStorage.getItem('Authorization')
  api
    .get('/meeting/recent-call', {
      headers: {
        Authorization: Authorization
      }
    })
    .then(success)
    .catch(fail)
}

function addBanList(bannedNo, success, fail) {
  const Authorization = 'Bearer ' + sessionStorage.getItem('Authorization')
  const requestBody = {
    bannedNo: bannedNo
  }
  api
    .post('/ban', JSON.stringify(requestBody), {
      headers: {
        Authorization: Authorization
      }
    })
    .then(success)
    .catch(fail)
}

function deleteBanUser(bannedNo, success, fail) {
  const Authorization = 'Bearer ' + sessionStorage.getItem('Authorization')
  api
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
function reportUser(report, success, fail) {
  const Authorization = 'Bearer ' + sessionStorage.getItem('Authorization')
  api
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
