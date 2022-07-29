<template>
  <!-- secondary의 색 바꿔야함. 임시적으로 secondary 사용! -->
  <!-- 배경 전체 되도록 조절 -->
  <!-- 관심사나 성격  -->
  <div>
    <h1 style="font-size: 30pt">회원가입</h1>
    <router-link to="/user/login">로그인</router-link>
    <router-link to="/user/signup/account">계정생성</router-link>
    <section class="basic-container">
      <q-form @submit="onSubmit" @reset="onReset" class="q-gutter-md">
        <section class="q-gutter-md">
          <!-- 프로필 이미지들 넣어주기 -->
          <div class="row q-pa-sm items-center">
            <div class="col-3">프로필 이미지</div>
            <div class="col-6">
              <q-img
                :src="url"
                spinner-color="white"
                style="height: 140px; max-width: 150px; border-radius: 100%"
              />
            </div>
            <div class="col-3">
              <q-btn
                color="secondary"
                @click=";[codeTransfer('이미지 선택'), refresh()]"
                label="이미지 선택"
              />
            </div>
          </div>
          <!-- 닉네임 -->
          <q-input
            label="닉네임"
            outlined
            bg-color="white"
            type="text"
            v-model="nickname"
          >
            <template v-slot:append>
              <q-btn
                color="secondary"
                @click="codeTransfer(nickname)"
                label="중복 검사"
                size="12px"
              />
            </template>
          </q-input>
          <!-- 음주 여부 -->
          <q-select
            label="음주 여부"
            outlined
            bg-color="white"
            v-model="drinkSelected"
            :options="drinkOption"
          />
          <q-select
            label="흡연 여부"
            outlined
            bg-color="white"
            v-model="smokeSelected"
            :options="smokeOption"
          />
          <q-select
            label="MBTI"
            outlined
            bg-color="white"
            v-model="mbtiSelected"
            :options="mbtiOption"
          />
          <q-select
            label="종교"
            outlined
            bg-color="white"
            v-model="religionSelected"
            :options="religionOption"
          />

          <!-- 관심사 -->
          <div class="row q-pa-sm items-center">
            <div class="col-3">관심사</div>
            <div class="col-6">
              <div class="q-gutter-xs">
                <q-chip
                  :label="interest.name"
                  v-model:selected="interest.value"
                  color="secondary"
                  text-color="white"
                  v-for="(interest, index) in interests"
                  :key="index"
                />
              </div>
            </div>
            <div class="col-3">
              {{ selection1 }}
            </div>
          </div>
          <!-- 성격 -->
          <div class="row q-pa-sm items-center">
            <div class="col-3">성격</div>
            <div class="col-6">
              <div class="q-gutter-xs">
                <q-chip
                  :label="personality.name"
                  v-model:selected="personality.value"
                  color="secondary"
                  text-color="white"
                  v-for="(personality, index) in personalities"
                  :key="index"
                />
              </div>
            </div>
            <div class="col-3">
              {{ selection2 }}
            </div>
          </div>
        </section>

        <div>
          <q-btn label="다음" type="submit" color="secondary" />
          <q-btn
            label="취소"
            type="reset"
            color="secondary"
            flat
            class="q-ml-sm"
          />
        </div>
      </q-form>
    </section>
  </div>
</template>

<script>
import { ref, reactive, computed } from 'vue'

export default {
  setup() {
    const email = ref(null)
    const code = ref(null)
    const password = ref(null)
    const passwordCheck = ref(null)
    const tempCode = ref('1234')
    const tempEmail = ref('rlatldjs123@gmail.com')
    const url = ref('https://placeimg.com/500/300/nature')
    const gender = ref(null)
    const date = ref(null)
    const nickname = ref(null)
    const smoke = ref(null)
    const interests = reactive([
      { key: 1, name: '게임', value: false },
      { key: 2, name: '운동', value: false },
      { key: 3, name: '영화', value: false },
      { key: 4, name: '독서', value: false },
      { key: 5, name: '음악', value: false },
      { key: 6, name: '맛집탐방', value: false },
      { key: 7, name: '패션', value: false },
      { key: 8, name: '채식', value: false },
      { key: 9, name: '반려동물', value: false },
      { key: 10, name: '재테크', value: false },
      { key: 11, name: '자동차', value: false }
    ])
    const personalities = reactive([
      { key: 1, name: '차분한', value: false },
      { key: 2, name: '발랄한', value: false },
      { key: 3, name: '센스있는', value: false },
      { key: 4, name: '배려많은', value: false },
      { key: 5, name: '쿨한', value: false },
      { key: 6, name: '열정적인', value: false },
      { key: 7, name: '개인적인', value: false },
      { key: 8, name: '긍정적인', value: false },
      { key: 9, name: '감각적인', value: false },
      { key: 10, name: '온화한', value: false },
      { key: 11, name: '소박한', value: false }
    ])

    // 음주 여부
    const drinkOption = ref(['안함', '가끔', '자주'])
    const smokeOption = ref(['비흡연', '흡연'])
    const drinkSelected = ref(null)
    const smokeSelected = ref(null)

    return {
      url,
      gender,
      date,
      nickname,
      smoke,

      drinkOption,
      smokeOption,
      drinkSelected,
      smokeSelected,
      interests,
      personalities,

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

      codeTransfer(e) {
        if (e) {
          console.log(e)
        } else if (email.value === tempEmail.value) {
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
      },

      refresh() {
        url.value = 'https://placeimg.com/500/300/nature?t=' + Math.random()
      },

      onItemClick(e) {
        console.log(e)
      },

      selection1: computed(() => {
        return Object.keys(interests)
          .filter((type) => interests[type].value === true)
          .join(', ')
      }),

      selection2: computed(() => {
        return Object.keys(personalities)
          .filter((type) => personalities[type].value === true)
          .join(', ')
      })
    }
  }
}
</script>

<style scoped>
.basic-container {
  width: 550px;
}
</style>
