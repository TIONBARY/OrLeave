<template>
  <div>
    <br />
    <br />
    <h1>로그인</h1>
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

        <!-- Signup & Find PW -->
        <br />
        <div class="q-gutter-md">
          <!-- Auth Login -->
          <div class="q-gutter-md">
            <router-link to="/user/signup/account">회원가입</router-link>
            <br />
            <router-link to="/user/forget/password">비밀번호찾기</router-link>
            <div @click="googleLoginBtn">
              <img style="width: 285px" src="@/assets/main/google_login.png" />
            </div>
            <div id="my-signin2" style="display: none"></div>
            <div id="naverIdLogin"></div>
            <!-- <q-btn @click="kakaoLogin()"> -->
            <img
              class="kakao"
              :src="require('../../assets/main/kakao_login_large_narrow.png')"
              alt="kakao_login"
              style="width: 277px"
              @click="kakaoLogin()"
            />
            <!-- </q-btn> -->
          </div>
        </div>
      </section>
      <ConfirmModal v-model="this.showModal" @close="movePage" :modalContent="this.modalContent" />
    </div>
  </div>
</template>

<script>
import { ref } from 'vue'
// import { kakaoLogin } from '@/api/user'
import { tryLogin } from '@/api/user'
import { GOOGLE_CLIENT_ID, GOOGLE_REDIRECT_URI, KAKAO_REDIRECT_URI } from '@/config'
import { naverService } from '@/api/auth'
import jwtDecode from 'jwt-decode'
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
  mounted() {
    naverService().setNaver()
  },
  methods: {
    async onSubmit() {
      tryLogin(this.loginInfo, ({ data }) => {
        const token = data.authorization
        const userType = jwtDecode(token).userType
        if (userType === 'Banned') {
          this.showModal = true
          this.modalContent = '해당 계정은 정지되어 사용할 수 없습니다.'
          this.willPageMove = false
          return
        }
        sessionStorage.setItem('Authorization', token)
        this.$router.push({ path: '/' })
      }, ({ response }) => {
        if (response.status === 401) {
          this.modalContent = 'EMAIL과 PW가 일치하지 않습니다.'
        } else if (response.data && response.data.message && response.data.message === 'Login Prohibited') {
          this.modalContent = '로그인을 5회 이상 실패하여 5분간 로그인이 제한됩니다.'
        } else if (response.status === 403) {
          this.modalContent = '회원가입하지 않은 계정입니다.'
        } else {
          this.modalContent = '에러가 발생했습니다. 다시 시도해보세요.'
        }
        this.showModal = true
        this.willPageMove = false
      })
    },
    printLog(msg) {
      console.log(msg)
    },
    kakaoLogin() {
      window.Kakao.Auth.authorize({
        scope: 'account_email',
        redirectUri: KAKAO_REDIRECT_URI
      })
    },
    async googleLoginBtn() {
      window.location.href = `https://accounts.google.com/o/oauth2/v2/auth?scope=https://www.googleapis.com/auth/userinfo.email&response_type=code&client_id=${GOOGLE_CLIENT_ID}&redirect_uri=${GOOGLE_REDIRECT_URI}`
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
.google {
  background: #4285f4;
  color: white;
}

img:hover {
  cursor: pointer;
}
</style>
