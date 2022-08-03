import axios from 'axios'
// import { API_BASE_URL } from '@/config'

function apiInstance() {
  const instance = axios.create({
    baseURL: 'http://localhost:8080/api/v1',
    headers: {
      'Content-type': 'application/json'
    }
  })
  return instance
}

export { apiInstance }
