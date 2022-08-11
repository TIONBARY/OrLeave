<template>
  <div>
    <div id="naverIdLogin" style="display: none"></div>
  </div>
</template>

<script>
import { googleLogin } from '@/api/user'
import { uuid } from 'vue-uuid'
import { mapActions } from 'vuex'
const userStore = 'userStore'

export default {
  name: 'GoogleLogin',
  created() {
    if (this.$route.query.code) {
      this.googleLogIn()
    }
  },
  methods: {
    ...mapActions(userStore, [
      'saveSignupInfo'
    ]),
    async googleLogIn() {
      googleLogin(
        this.$route.query.code,
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
            this.$router.push('/user/login')
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
