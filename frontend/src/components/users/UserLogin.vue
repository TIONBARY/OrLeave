<template>
  <div>
    <h1>로그인</h1>
    <!-- Content -->
    <!-- Login -->
    <div class="row justify-center">
      <section class="basic-container">
        <q-form @submit="onSubmit" class="q-gutter-sm">
          <article class="row items-center q-gutter-sm">
            <div class="col-3 text-right">이메일</div>
            <div class="col-8">
              <q-input
                outlined
                type="email"
                v-model="id"
                dense
                bg-color="white"
                maxlength="30"
              />
            </div>
          </article>
          <article class="row items-center q-gutter-sm">
            <div class="col-3 text-right">비밀번호</div>
            <div class="col-8">
              <q-input
                outlined
                type="password"
                v-model="pw"
                dense
                bg-color="white"
                maxlength="20"
              />
            </div>
          </article>
          <article>
            <q-btn label="로그인" type="submit" color="primary" />
          </article>
        </q-form>

        <!-- Signup & Find PW -->
        <div class="q-ma-sm">
          <q-btn
            flat
            label="회원가입"
            color="primary"
            to="/user/signup/account"
          />
          |
          <q-btn
            flat
            label="비밀번호 찾기"
            color="primary"
            to="/user/login"
          /><br />
          <!-- 위 링크는 페이지를 만든 후 수정해야 함.-->
          <!-- Auth Login -->
          <div class="q-gutter-sm">
            <q-btn
              label="Google Auth Login"
              color="primary"
              @click="printLog('google')"
            /><br />
            <q-btn
              label="Naver Auth Login"
              color="secondary"
              @click="printLog('naver')"
            /><br />
            <q-btn
              label="Kakao Auth Login"
              color="amber"
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
export default {
  setup() {
    const id = ref(null)
    const pw = ref(null)
    return {
      id,
      pw
    }
  },
  methods: {
    onSubmit() {
      // 조건 검사해서 비어있거나, db랑 일치하는 정보가 없으면 모달창 띄움
      console.log('ID: ' + this.id + '\nPW: ' + this.pw)
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
</style>
