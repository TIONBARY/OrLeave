<template>
  <div style="background-color: #f3f1eb">
    <h1 style="font-size: 30pt">회원가입</h1>
    <div class="fit row justify-center">
      <div
        class="basic-container"
        style="width: 40%; background-color: #e5edb8; border-radius: 10pt"
      >
        <q-form @submit="onSubmit" @reset="onReset" class="q-gutter-md">
          <div class="q-pa-md">
            <div class="row q-pa-sm items-center">
              <div class="col-3">이메일</div>
              <div class="col-6">
                <q-input
                  outlined
                  bg-color="white"
                  type="email"
                  v-model="email"
                  dense="dense"
                />
              </div>
              <div class="col-3">
                <q-btn
                  color="secondary"
                  @click="codeTransfer"
                  label="인증번호전송"
                />
              </div>
            </div>
            <div class="row q-pa-sm items-center">
              <div class="col-3">인증번호</div>
              <div class="col-6">
                <q-input
                  outlined
                  bg-color="white"
                  v-model="code"
                  dense="dense"
                />
              </div>
              <div class="col-2">
                <q-btn color="secondary" @click="confirm" label="확인" />
              </div>
              <div class="col-1">{{ timer }}</div>
            </div>
            <div class="row q-pa-sm items-center">
              <div class="col-3">비밀번호</div>
              <div class="col-6">
                <q-input
                  outlined
                  bg-color="white"
                  type="password"
                  v-model="password"
                  placeholder="영문, 숫자, 특수문자 8~16"
                  dense="dense"
                />
              </div>
            </div>
            <div class="row q-pa-sm items-center">
              <div class="col-3">비밀번호확인</div>
              <div class="col-6">
                <q-input
                  outlined
                  bg-color="white"
                  type="password"
                  v-model="passwordCheck"
                  dense="dense"
                />
              </div>
            </div>
          </div>
          <div>
            <q-btn label="다음" type="submit" color="primary" />
            <q-btn
              label="취소"
              type="reset"
              color="primary"
              flat
              class="q-ml-sm"
            />
          </div>
        </q-form>
      </div>
    </div>
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
