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
            <router-link to="/user/signup/account">비밀번호찾기</router-link>
            <q-btn
              label="Google Auth Login"
              class="full-width google"
              @click="printLog('google')"
            />
            <q-btn
              label="Naver Auth Login"
              class="full-width naver"
              @click="printLog('naver')"
            />
            <q-btn
              label="Kakao Auth Login"
              class="full-width kakao"
              @click="printLog('kakao')"
            />
          </div>
        </div>
      </section>
    </div>
  </div>
</template>

<script>
import { ref } from 'vue'

import { mapState, mapActions } from 'vuex'
const userStore = 'userStore'

export default {
  setup() {
    const loginInfo = ref({ email: null, password: null })
    return {
      loginInfo
    }
  },
  computed: {
    ...mapState(userStore, ['isLogin'])
  },
  methods: {
    ...mapActions(userStore, ['login', 'getUserInfo', 'logout']),
    async onSubmit() {
      await this.login(this.loginInfo)
      if (this.isLogin) {
        console.log('로그인 성공')
        this.$router.push({ path: '/' })
      } else {
        console.log('로그인 실패...ㅜㅜ')
      }
    },
    printLog(msg) {
      console.log(msg)
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
.naver {
  background: #00c43b;
  color: white;
}
.kakao {
  background: #ffe812;
  color: black;
}
</style>
