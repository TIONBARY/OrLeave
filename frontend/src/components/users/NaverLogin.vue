<template>
  <div>
    <ConfirmModal
      v-model="this.showModal"
      @close="movePage"
      :modalContent="this.modalContent"
    />
  </div>
</template>

<script>
import { ref } from 'vue'
import { naverLogin } from '@/api/user'
import { uuid } from 'vue-uuid'
import { mapActions } from 'vuex'
import ConfirmModal from '../ConfirmModal.vue'
import jwtDecode from 'jwt-decode'
const userStore = 'userStore'

export default {
  name: 'NaverLogin',
  setup() {
    const showModal = ref(false)
    const willPageMove = ref(false)
    const path = ref(null)
    const modalContent = ref(null)
    return {
      showModal,
      modalContent,
      willPageMove,
      path
    }
  },
  components: {
    ConfirmModal
  },
  created() {
    const [temp] = this.$route.hash.split('&')
    const accessToken = temp.split('=')[1]
    naverLogin(
      accessToken,
      ({ data }) => {
        const token = data.authorization
        const userType = jwtDecode(token).userType
        if (userType === 'Banned') {
          this.showModal = true
          this.modalContent = '해당 계정은 정지되어 사용할 수 없습니다.'
          this.willPageMove = true
          this.path = '/user/login'
          return
        }
        if (data.statusCode === 200) {
          sessionStorage.setItem('Authorization', data.authorization)
          this.$router.push('/')
        }
      },
      ({ response: { data } }) => {
        if (data.message === 'Signup Required') {
          this.modalContent = '회원가입이 필요합니다.'
          this.path = '/user/signup/profile'
          this.saveSignupInfo({
            email: data.authorization,
            password: uuid.v1()
          })
        } else {
          this.modalContent = '에러가 발생했습니다. 다시 시도해 주세요.'
          this.path = '/user/login'
        }
        this.showModal = true
        this.willPageMove = true
      }
    )
  },
  methods: {
    ...mapActions(userStore, ['saveSignupInfo']),
    movePage() {
      if (this.willPageMove) {
        this.$router.push(this.path)
      }
    }
  }
}
</script>
