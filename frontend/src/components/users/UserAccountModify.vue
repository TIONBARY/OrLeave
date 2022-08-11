<template>
  <div>
    <br />
    <br />
    <h1>비밀번호 변경</h1>
    <section class="basic-container" v-if="!emailCheck">
      <q-form class="q-gutter-md row justify-center">
        <section class="q-gutter-sm" style="width: 85%">
          <q-input
            label="이메일"
            outlined
            bg-color="white"
            type="email"
            v-model="checkingEmail"
            :readonly="isReadonly"
            :rules="[checkReadonly]"
            dense
            required
            lazy-rules="ondemand"
            hide-bottom-space
            id="checkingEmail"
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
        </section>
      </q-form>
    </section>
    <section class="basic-container" v-if="emailCheck">
      <q-form @submit="onSubmit" class="q-gutter-md row justify-center">
        <section class="q-gutter-sm" style="width: 85%">
          <q-input
            label="이메일"
            outlined
            bg-color="white"
            type="email"
            v-model="userEmail"
            dense
            readonly
          />

          <q-input
            label="비밀번호"
            outlined
            bg-color="white"
            type="password"
            v-model="password"
            placeholder="영문, 숫자, 특수문자 8~16"
            :rules="[
              () =>
                /^(?=.*[A-Za-z])(?=.*\d)(?=.*[$@$!%*#?&])[A-Za-z\d$@$!%*#?&]{8,}$/.test(
                  password
                ) || '영문, 숫자, 특수문자를 포함해 8~16자리'
            ]"
            dense
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
        </section>
        <q-btn label="다음" type="submit" class="primary" />
      </q-form>
    </section>
    <ConfirmModal v-model="this.showModal" @close="movePage" :modalContent="this.modalContent" />
  </div>
</template>

<script>
import { ref, reactive, computed } from 'vue'
import { confirmEmailCode, getEmail, modifyPassword } from '@/api/user'
import { mapActions } from 'vuex'
import ConfirmModal from '../ConfirmModal.vue'
const userStore = 'userStore'

export default {
  setup() {
    const userEmail = ref(null)
    const checkingEmail = ref(null)
    const code = ref(null)
    const isReadonly = ref(false)
    const emailCheck = ref(false)
    const timer = ref(null)
    const password = ref(null)
    const passwordCheck = ref(null)
    const showModal = ref(false)
    const willPageMove = ref(false)
    const path = ref(null)
    const intervalId = ref(0)
    const minutes = ref('03')
    const seconds = ref('00')
    const modalContent = 'ID 또는 PW가 일치하지 않습니다'
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

    return {
      userEmail,
      checkingEmail,
      emailCheck,
      isReadonly,
      code,
      timer,
      password,
      passwordCheck,
      pwState,
      pwMsg,
      showModal,
      modalContent,
      willPageMove,
      path,
      intervalId,
      minutes,
      seconds
    }
  },
  components: {
    ConfirmModal
  },
  created() {
    getEmail(({ data }) => {
      if (data.statusCode === 200) {
        this.userEmail = data.email
      }
    }, ({ response: { data } }) => {
      if (data.statusCode === 401) {
        this.showModal = true
        this.modalContent = '로그인이 필요합니다.'
        this.willPageMove = true
        this.path = '/user/login'
      } else {
        this.showModal = true
        this.modalContent = '에러가 발생했습니다. 메인 화면으로 이동합니다.'
        this.willPageMove = true
        this.path = '/'
      }
    })
  },
  methods: {
    ...mapActions(userStore, ['sendConfirmKey', 'modifyAccountInfo']),
    sendEmail() {
      if (this.checkingEmail < 3 || !this.checkingEmail.includes('@')) {
        return
      }
      this.sendConfirmKey(this.checkingEmail)
      this.isReadonly = true
      this.showModal = true
      this.modalContent = '인증번호가 전송되었습니다.'
      this.willPageMove = false
      this.startTimer(60 * 3)
    },
    async onSubmit() {
      modifyPassword({
        email: this.userEmail,
        password: this.password
      }, ({ data }) => {
        if (data.statusCode === 200) {
          this.showModal = true
          this.modalContent = '비밀번호가 변경되었습니다.'
          this.willPageMove = true
          this.path = '/'
        }
      }, ({ response: { data } }) => {
        if (data.statusCode === 401) {
          this.showModal = true
          this.modalContent = '로그인이 만료되었습니다. 다시 로그인해주세요.'
          this.willPageMove = true
          this.path = '/user/login'
        } else if (data.message === 'Wrong Password') {
          this.showModal = true
          this.modalContent = '이전과 동일한 비밀번호입니다. 다른 비밀번호를 입력하세요.'
          this.willPageMove = false
        } else {
          this.showModal = true
          this.modalContent = '에러가 발생했습니다. 다시 시도해주세요.'
          this.willPageMove = false
        }
      })
    },
    checkReadonly() {
      return this.emailCheck || '이메일을 인증해주세요'
    },
    rewrite() {
      this.isReadonly = false
      this.emailCheck = false
      clearInterval(this.intervalId)
    },
    startTimer(duration, display) {
      let remain = duration
      this.intervalId = setInterval(() => {
        this.minutes = parseInt(remain / 60, 10)
        this.seconds = parseInt(remain % 60, 10)

        this.minutes = this.minutes < 10 ? '0' + this.minutes : this.minutes
        this.seconds = this.seconds < 10 ? '0' + this.seconds : this.seconds
        this.timer = this.minutes + ':' + this.seconds
        if (--remain < 0) {
          remain = duration
          clearInterval(this.intervalId)
        }
      }, 1000)
    },
    confirm() {
      confirmEmailCode({
        email: this.checkingEmail,
        code: this.code
      }, (response) => {
        if (response.data.statusCode === 200 && this.userEmail === this.checkingEmail) {
          this.showModal = true
          this.modalContent = '인증되었습니다.'
          this.emailCheck = true
          this.willPageMove = false
        } else {
          this.showModal = true
          this.modalContent = '현재 로그인한 이메일과 일치하지 않습니다.'
          this.emailCheck = false
          this.willPageMove = false
        }
        clearInterval(this.intervalId)
      }, (error) => {
        if (error.response.data.message === 'Wrong Code') {
          this.showModal = true
          this.modalContent = '코드를 잘못 입력하셨습니다.'
        } else if (error.response.data.message === 'Email Confirm Time Out') {
          this.showModal = true
          this.modalContent = '인증코드 입력 시간이 초과되었습니다.'
          clearInterval(this.intervalId)
        } else if (error.response.data.message === 'Code Not Sent') {
          this.showModal = true
          this.modalContent = '인증코드를 전송해주세요.'
        } else {
          this.showModal = true
          this.modalContent = '에러가 발생했습니다. 다시 시도해주세요.'
        }
        this.emailCheck = false
        this.willPageMove = false
      })
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
