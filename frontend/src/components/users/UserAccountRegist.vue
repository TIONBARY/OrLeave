<template>
  <div>
    <h1>회원가입</h1>
    <router-link to="/user/login">로그인</router-link>
    <router-link to="/user/signup/profile">프로필생성</router-link>
    <section class="basic-container">
      <q-form @submit="onSubmit" @reset="onReset" class="q-gutter-sm">
        <article class="row items-center q-gutter-sm">
          <div class="col-3 text-right">이메일</div>
          <div class="col-5">
            <q-input
              outlined
              bg-color="white"
              type="email"
              v-model="email"
              dense
            />
          </div>
          <div class="col-3">
            <q-btn
              color="secondary"
              @click="codeTransfer"
              label="인증번호전송"
            />
          </div>
        </article>
        <article class="row items-center q-gutter-sm">
          <div class="col-3">인증번호</div>
          <div class="col-5">
            <q-input outlined bg-color="white" v-model="code" dense="dense" />
          </div>
          <div class="col-2">
            <q-btn color="secondary" @click="confirm" label="확인" />
          </div>
          <div class="col-1">{{ timer }}</div>
        </article>
        <article class="row items-center q-gutter-sm">
          <div class="col-3">비밀번호</div>
          <div class="col-5">
            <q-input
              outlined
              bg-color="white"
              type="password"
              v-model="password"
              placeholder="영문, 숫자, 특수문자 8~16"
              dense="dense"
            />
          </div>
        </article>
        <article class="row items-center q-gutter-sm">
          <div class="col-3">비밀번호확인</div>
          <div class="col-5">
            <q-input
              outlined
              bg-color="white"
              type="password"
              v-model="passwordCheck"
              dense="dense"
            />
          </div>
        </article>
        <article>
          <q-btn label="다음" type="submit" color="primary" />
          <q-btn
            label="취소"
            type="reset"
            color="primary"
            flat
            class="q-ml-sm"
          />
        </article>
      </q-form>
    </section>
  </div>
</template>

<script>
import { ref } from 'vue'

export default {
  setup() {
    const email = ref(null)
    const code = ref(null)
    const timer = ref('3:00')
    const password = ref(null)
    const passwordCheck = ref(null)
    const tempCode = ref('1234')
    const tempEmail = ref('rlatldjs123@gmail.com')

    return {
      email,
      code,
      timer,
      password,
      passwordCheck,
      tempCode,
      tempEmail,

      onSubmit() {
        console.log(email)
      },

      onReset() {
        email.value = null
        code.value = null
        password.value = null
        passwordCheck.value = null
        console.log('취소')
      },

      codeTransfer() {
        if (email.value === tempEmail.value) {
          console.log('중복이메일입니다.')
        } else {
          console.log('인증번호 전송')
        }
      },

      confirm() {
        if (code.value === tempCode.value) {
          console.log('인증번호 확인')
        } else {
          console.log('재인증')
        }
      }
    }
  }
}
</script>

<style scoped>
.basic-container {
  width: 550px;
}
</style>
