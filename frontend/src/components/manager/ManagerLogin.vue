<template>
  <div>
    <div class="item row items-center justify-center" style="margin-top: 70px">
      <q-img
        class="q-ma-lg"
        :src="require('../../assets/logo_manager.png')"
        alt="image"
        style="width: 100%; max-width: 300px"
      />
    </div>
    <!-- Content -->
    <!-- Login -->
    <div>
      <section class="basic-container">
        <q-form @submit="onSubmit" class="q-mt-sm row justify-center">
          <section class="q-gutter-md" style="width: 85%">
            <q-input
              label="이메일"
              outlined
              type="email"
              v-model="loginInfo.email"
              bg-color="white"
              maxlength="30"
              required
            />
            <q-input
              label="비밀번호"
              outlined
              type="password"
              v-model="loginInfo.password"
              bg-color="white"
              maxlength="20"
              required
            />
            <div>
              <q-btn
                label="로그인"
                size="lg"
                type="submit"
                class="full-width primary"
              />
            </div>
          </section>
        </q-form>
      </section>
      <ConfirmModal
        v-model="this.showModal"
        @close="movePage"
        :modalContent="this.modalContent"
      />
    </div>
  </div>
</template>

<script>
import { ref } from 'vue'
// import { kakaoLogin } from '@/api/user'
import { managerLogin } from '@/api/manager'
import ConfirmModal from '../ConfirmModal.vue'

export default {
  setup() {
    const loginInfo = ref({ email: null, password: null })
    const showModal = ref(false)
    const willPageMove = ref(false)
    const path = ref(null)
    const modalContent = 'ID 또는 PW가 일치하지 않습니다'
    return {
      loginInfo,
      showModal,
      modalContent,
      willPageMove,
      path
    }
  },
  components: {
    ConfirmModal
  },
  methods: {
    async onSubmit() {
      managerLogin(
        this.loginInfo,
        ({ data }) => {
          const token = data.authorization
          sessionStorage.setItem('Authorization', token)
          this.$router.push({ name: 'managerMain' })
        },
        ({ response }) => {
          if (response.status === 401) {
            this.modalContent = 'EMAIL과 PW가 일치하지 않습니다.'
          } else if (
            response.data &&
            response.data.message &&
            response.data.message === 'Login Prohibited'
          ) {
            this.modalContent =
              '로그인을 5회 이상 실패하여 5분간 로그인이 제한됩니다.'
          } else if (
            response.data &&
            response.data.message &&
            response.data.message === 'Not Manager'
          ) {
            this.modalContent = '관리자 계정이 아닙니다.'
          } else if (response.status === 403) {
            this.modalContent = '회원가입하지 않은 계정입니다.'
          } else {
            this.modalContent = '에러가 발생했습니다. 다시 시도해보세요.'
          }
          this.showModal = true
          this.willPageMove = false
        }
      )
    },
    movePage() {
      if (this.willPageMove) {
        this.$router.push(this.path)
      }
    }
  }
}
</script>

<style scoped>
.basic-container {
  width: 400px;
}
</style>
