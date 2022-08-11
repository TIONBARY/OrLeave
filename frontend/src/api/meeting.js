import { apiInstance } from './index.js'

const api = apiInstance()

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

export { enterMeeting }
