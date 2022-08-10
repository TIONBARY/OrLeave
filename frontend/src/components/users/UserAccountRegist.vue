<template>
  <div>
    <br />
    <br />
    <h1>회원가입</h1>
    <section class="basic-container">
      <q-form @submit="onSubmit" class="q-gutter-md row justify-center">
        <section class="q-gutter-sm" style="width: 85%">
          <q-input
            label="이메일"
            outlined
            bg-color="white"
            type="email"
            v-model="email"
            dense
            required
            :readonly="isReadonly"
            :rules="[checkReadonly]"
            lazy-rules="ondemand"
            hide-bottom-space
            id="Email"
          >
            <template v-slot:append>
              <q-btn
                v-if="!isReadonly"
                color="secondary"
                @click="sendEmail"
                label="인증번호전송"
                size="10px"
                dense
              />
              <q-btn
                v-else
                color="secondary"
                @click="rewrite"
                label="이메일재입력"
                size="10px"
                dense
              />
            </template>
          </q-input>
          <q-input
            label="인증번호"
            outlined
            bg-color="white"
            v-model="code"
            dense
            :hint="timer"
            required
          >
            <template v-slot:append>
              <q-btn
                color="secondary"
                @click="confirm"
                label="확인"
                size="10px"
              />
            </template>
          </q-input>

          <q-input
            label="비밀번호"
            outlined
            bg-color="white"
            type="password"
            v-model="password"
            placeholder="영문, 숫자, 특수문자 8~16"
            dense
            :rules="[
              () => /^(?=.*[A-Za-z])(?=.*\d)(?=.*[$@$!%*#?&])[A-Za-z\d$@$!%*#?&]{8,}$/.test(password) || '영문, 숫자, 특수문자를 포함해 8~16자리'
            ]"
            required
          >
          </q-input>
          <q-input
            label="비밀번호 확인"
            outlined
            bg-color="white"
            type="password"
            v-model="passwordCheck"
            dense
            :rules="[
              () => password === passwordCheck || '비밀번호가 일치하지 않습니다'
            ]"
            lazy-rules="ondemand"
            required
          />
          <q-field
            label="성별"
            stack-label
            outlined
            bg-color="white"
            :rules="[() => gender !== null || '성별을 선택해주세요']"
            lazy-rules="ondemand"
            hide-bottom-space
          >
            <div class="row justify-center q-gutter-md">
              <q-radio dense v-model="gender" val="M" label="남" />
              <q-radio dense v-model="gender" val="F" label="여" />
            </div>
          </q-field>

          <q-input
            label="생년월일"
            outlined
            v-model="birthday"
            bg-color="white"
            mask="date"
            :rules="[
              () =>
                parseInt(birthday.substring(0, 4)) <
                  new Date().getFullYear() - 18 || '생년월일을 확인해주세요'
            ]"
            lazy-rules="ondemand"
            hide-bottom-space
          >
            <template v-slot:append>
              <q-icon name="event" class="cursor-pointer">
                <q-popup-proxy
                  cover
                  transition-show="scale"
                  transition-hide="scale"
                >
                  <q-date v-model="birthday">
                    <div class="row items-center justify-end">
                      <q-btn v-close-popup label="Close" color="primary" flat />
                    </div>
                  </q-date>
                </q-popup-proxy>
              </q-icon>
            </template>
          </q-input>
        </section>
        <q-btn label="다음" type="submit" class="primary" />
      </q-form>
    </section>
  </div>
</template>

<script>
import { ref, reactive, computed } from 'vue'
import { confirmEmailCode } from '@/api/user'
import { mapActions } from 'vuex'
const userStore = 'userStore'

export default {
  setup() {
    const email = ref(null)
    const isReadonly = ref(false)
    const emailCheck = ref(false)
    const code = ref(null)
    const timer = ref('3:00')
    const password = ref(null)
    const passwordCheck = ref(null)
    const pwState = reactive(
      computed(() => {
        if (password.value === null) return 0
        else if (passwordCheck.value === null) return 1
        else if (password.value === passwordCheck.value) return 3
        else return 2
      })
    )
    const pwMsg = ref([
      '비밀번호를 입력하세요',
      '비밀번호를 재입력하세요.',
      '비밀번호가 일치하지 않습니다.',
      '비밀번호가 일치합니다.'
    ])
    const gender = ref(null)
    const birthday = ref('2022/01/01')
    return {
      email,
      isReadonly,
      code,
      emailCheck,
      timer,
      password,
      passwordCheck,
      pwState,
      pwMsg,
      gender,
      birthday
    }
  },
  methods: {
    ...mapActions(userStore, [
      'checkEmail',
      'sendConfirmKey',
      'saveSignupInfo'
    ]),
    async sendEmail() {
      if (this.email < 3 || !this.email.includes('@')) {
        return
      }
      await this.checkEmail(this.email)
        .then(() => {
          console.log('valid')
          this.sendConfirmKey(this.email)
        })
        .catch(() => {
          console.log('invalid')
        })

      console.log('END')
    },
    checkReadonly() {
      return this.isReadonly || '이메일을 인증해주세요'
    },
    rewrite() {
      this.isReadonly = false
    },
    async onSubmit(e) {
      e.preventDefault()
      await this.saveSignupInfo({
        email: this.email,
        password: this.password,
        gender: this.gender,
        birthDay: this.birthday.replaceAll('/', '-')
      })
      this.$router.push('/user/signup/profile')
    },
    confirm() {
      confirmEmailCode({
        email: this.email,
        code: this.code
      }, (response) => {
        if (response.data.statusCode === 200) {
          console.log('인증되었습니다.')
          this.isReadonly = true
        }
      }, (error) => {
        if (error.response.data.message === 'Wrong Code') {
          console.log('코드를 잘못 입력하셨습니다.')
        } else if (error.response.data.message === 'Email Confirm Time Out') {
          console.log('인증코드 입력 시간이 초과되었습니다.')
        } else {
          console.log('에러가 발생했습니다. 다시 시도해주세요.')
        }
        this.isReadonly = false
      })
    }
  }
}
</script>

<style scoped>
.basic-container {
  width: 400px;
}
</style>
