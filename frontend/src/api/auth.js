import axios from 'axios'
import { KAKAO_CLIENT_ID, KAKAO_ADMIN_ID, KAKAO_REDIRECT_URI } from '@/config/index.js'

const kakaoHeader = {
  Authorization: KAKAO_ADMIN_ID,
  'Content-type': 'application/x-www-form-urlencoded;charset=utf-8'
}
const getKakaoToken = async (code) => {
  try {
    const data = {
      grant_type: 'authorization_code',
      client_id: KAKAO_CLIENT_ID,
      redirect_uri: KAKAO_REDIRECT_URI,
      code: code
    }
    const queryString = Object.keys(data)
      .map((k) => encodeURIComponent(k) + '=' + encodeURIComponent(data[k]))
      .join('&')
    const result = await axios.post(
      'https://kauth.kakao.com/oauth/token',
      queryString,
      { headers: kakaoHeader }
    )
    return result
  } catch (e) {
    return e
  }
}

export { getKakaoToken }
