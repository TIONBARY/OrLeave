<template>
  <div>
    <div id="naverIdLogin" style="display: none"></div>
  </div>
</template>

<script>
import { getKakaoToken } from '@/api/auth'
import { kakaoLogin } from '@/api/user'
import { uuid } from 'vue-uuid'
import { mapActions } from 'vuex'
const userStore = 'userStore'

export default {
  name: 'AuthView',
  created() {
    if (this.$route.query.code) {
      this.setKakaoToken()
    }
  },
  // mounted() {
  //   if (this.$route.hash) {
  //     naverService().getUserInfo()
  //   }
  // },
  methods: {
    ...mapActions(userStore, [
      'checkEmail',
      'sendConfirmKey',
      'saveSignupInfo'
    ]),
    async setKakaoToken() {
      const { data } = await getKakaoToken(this.$route.query.code)
      if (data.error) {
        this.$router.replace('/user/login')
        return
      }
      kakaoLogin(
        data.access_token,
        ({ data }) => {
          if (data.statusCode === 200) {
            sessionStorage.setItem('Authorization', data.authorization)
            this.$router.push('/')
          }
        },
        ({ response: { data } }) => {
          if (data.message === 'Signup Required') {
            console.log('회원가입이 필요합니다.')
            this.saveSignupInfo({
              email: data.authorization,
              password: uuid.v1()
            })
            this.$router.push('/user/signup/profile')
          } else {
            console.log('에러가 발생했습니다. 다시 시도해 주세요.')
          }
        }
      )
    }
    // getInfo() {
    //   naverService().getUserInfo()
    // }
  }
}
</script>
