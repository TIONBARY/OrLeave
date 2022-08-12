<template>
  <div>
    <div id="naverIdLogin" style="display: none"></div>
  </div>
</template>

<script>
import { naverLogin } from '@/api/user'
import { uuid } from 'vue-uuid'
import { mapActions } from 'vuex'
const userStore = 'userStore'

export default {
  name: 'AuthView',
  created() {
    const [temp] = this.$route.hash.split('&')
    const accessToken = temp.split('=')[1]
    naverLogin(
      accessToken,
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
  },
  methods: {
    ...mapActions(userStore, [
      'saveSignupInfo'
    ])
  }
}
</script>
