import axios from 'axios'
import {
  KAKAO_CLIENT_ID,
  KAKAO_ADMIN_ID,
  KAKAO_REDIRECT_URI,
  NAVER_CLIENT_ID,
  NAVER_REDIRECT_URI
} from '@/config/index.js'

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

const naverService = () => {
  const naverLogin = new window.naver.LoginWithNaverId({
    clientId: NAVER_CLIENT_ID,
    callbackUrl: NAVER_REDIRECT_URI,
    isPopup: false /* 팝업을 통한 연동처리 여부 */,
    loginButton: {
      color: 'green',
      type: 3,
      height: 60
    } /* 로그인 버튼의 타입을 지정 */
  })
  const setNaver = () => {
    naverLogin.init()
  }
  return {
    setNaver
  }
}

export { getKakaoToken, naverService }
