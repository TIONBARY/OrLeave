import { apiInstance } from './index.js'

const api = apiInstance()

function getMeetingSetting(success, fail) {
  const authorization = 'Bearer ' + sessionStorage.getItem('Authorization')
  return api.get('/meeting/setting', {
    headers: {
      authorization: authorization
    }
  }).then(success).catch(fail)
}

function modifyMeetingSetting(meetingSetting, success, fail) {
  const authorization = 'Bearer ' + sessionStorage.getItem('Authorization')
  return api.put('/meeting/setting', JSON.stringify(meetingSetting), {
    headers: {
      authorization: authorization
    }
  }).then(success).catch(fail)
}

function enterMeeting(sessionId) {
  const authorization = 'Bearer ' + sessionStorage.getItem('Authorization')
  console.log(authorization)
  const sessionObject = { sessionName: sessionId }
  console.log(sessionId)
  return api.post('/session/enter', JSON.stringify(sessionObject), {
    headers: {
      authorization: authorization
    }
  })
}

export { getMeetingSetting, enterMeeting, modifyMeetingSetting }
