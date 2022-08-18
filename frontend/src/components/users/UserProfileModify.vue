<template>
  <div>
    <br />
    <br />
    <h1>프로필 수정</h1>
    <section class="basic-container" v-if="!passwordCheck">
      <q-form @submit="submitPassword" class="q-gutter-md row justify-center">
        <section class="q-gutter-sm" style="width: 85%">
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
        </section>
        <q-btn label="확인" type="submit" class="primary" />
      </q-form>
    </section>
    <section class="basic-container" v-if="passwordCheck">
      <q-form @submit.prevent="onSubmit">
        <section class="row justify-center">
          <!-- 프로필 이미지들 넣어주기 -->
          <q-field
            borderless
            :rules="[() => imageNo > 0 || '프로필 이미지를 선택하세요']"
            lazy-rules="ondemand"
            hide-bottom-space
          >
            <article class="profile-div" style="margin: 5px">
              <q-img
                class="profile-img"
                :src="imageUrl"
                spinner-color="white"
                style="height: 100px; width: 100px"
              />
              <q-btn
                class="profile-select-btn"
                color="secondary"
                round
                @click="popupProfile = true"
                icon="collections"
              />
              <!-- Popup~ -->
              <q-dialog
                v-model="popupProfile"
                style="text-align: center"
                persistent
              >
                <q-card style="background-color: #c7d36f; color: white">
                  <q-btn
                    icon="close"
                    class="q-mt-sm"
                    flat
                    round
                    dense
                    v-close-popup
                  />
                  <q-card-section class="row items-center">
                    <div>
                      <span
                        v-for="index in 25"
                        :key="index"
                        @click="imgSelect(index)"
                      >
                        <q-avatar size="90px">
                          <q-btn round v-close-popup style="margin: 2px">
                            <img
                              :src="
                                require('../../assets/profile/' +
                                  index +
                                  '.png')
                              "
                              alt="image"
                              style="width: 100%; height: 100%"
                            />
                          </q-btn>
                        </q-avatar>
                      </span>
                    </div>
                  </q-card-section>
                </q-card>
              </q-dialog>
              <!-- ~Popup -->
            </article>
          </q-field>
          <!-- 닉네임 -->
          <table width="85%">
            <tr>
              <td class="q-pa-xs" style="width: 100%" colspan="2">
                <q-input
                  label="닉네임"
                  outlined
                  bg-color="white"
                  type="text"
                  v-model="nickname"
                  dense
                  :rules="[() => nicknameValid || '중복 검사를 해주세요']"
                  lazy-rules="ondemand"
                  hide-bottom-space
                  required
                >
                  <template v-slot:append>
                    <q-btn
                      color="secondary"
                      @click="checkNickname(nickname)"
                      label="중복 검사"
                      size="10px"
                    />
                  </template>
                </q-input>
              </td>
            </tr>

            <tr>
              <td class="q-pa-xs" style="width: 50%">
                <q-select
                  label="음주여부"
                  outlined
                  bg-color="white"
                  v-model="drinkSelected"
                  :options="drinkOptions"
                  emit-value
                  map-options
                  dense
                  :rules="[
                    () => drinkSelected !== null || '음주여부를 체크해주세요'
                  ]"
                  lazy-rules="ondemand"
                  hide-bottom-space
                />
              </td>
              <td class="q-pa-xs" style="width: 50%">
                <q-select
                  label="흡연여부"
                  outlined
                  bg-color="white"
                  v-model="smokeSelected"
                  :options="smokeOptions"
                  emit-value
                  map-options
                  dense
                  :rules="[
                    () => smokeSelected !== null || '흡연여부를 체크해주세요'
                  ]"
                  lazy-rules="ondemand"
                  hide-bottom-space
                />
              </td>
            </tr>
            <tr>
              <td class="q-pa-xs" style="width: 50%">
                <q-select
                  label="MBTI"
                  outlined
                  bg-color="white"
                  v-model="mbtiSelected"
                  :options="mbtiOptions"
                  dense
                  :rules="[
                    () => mbtiSelected !== null || 'MBTI를 선택해주세요'
                  ]"
                  lazy-rules="ondemand"
                  hide-bottom-space
                />
              </td>
              <td class="q-pa-xs" style="width: 50%">
                <q-select
                  label="종교"
                  outlined
                  bg-color="white"
                  v-model="religionSelected"
                  :options="religionOptions"
                  emit-value
                  map-options
                  dense
                  :rules="[
                    () => religionSelected !== null || '종교를 선택해주세요'
                  ]"
                  lazy-rules="ondemand"
                  hide-bottom-space
                />
              </td>
            </tr>

            <tr>
              <td class="q-pa-xs" style="width: 100%" colspan="2">
                <q-field
                  label="관심사"
                  stack-label
                  outlined
                  bg-color="white"
                  :rules="[
                    () =>
                      (interestSelected.length > 0 &&
                        interestSelected.length < 4) ||
                      '관심사를 1~3개 선택해주세요'
                  ]"
                  lazy-rules="ondemand"
                  hide-bottom-space
                >
                  <div class="row justify-center">
                    <template
                      v-for="(interest, index) in interests"
                      :key="index"
                    >
                      <q-btn
                        v-if="!interest.value"
                        class="q-ma-xs q-mb-sm q-px-sm"
                        :label="interest.name"
                        color="secondary"
                        text-color="white"
                        dense
                        rounded
                        @click="toggle(1, interest.key)"
                      />
                      <q-btn
                        v-if="interest.value"
                        class="q-ma-xs q-mb-sm q-px-sm"
                        :label="interest.name"
                        color="primary"
                        text-color="white"
                        dense
                        rounded
                        @click="toggle(1, interest.key)"
                      />
                    </template>
                  </div>
                </q-field>
              </td>
            </tr>
            <tr>
              <td class="q-pa-xs" style="width: 100%" colspan="2">
                <q-field
                  label="성격"
                  stack-label
                  outlined
                  bg-color="white"
                  :rules="[
                    () =>
                      (personalitySelected.length > 0 &&
                        personalitySelected.length < 4) ||
                      '성격을 1~3개 선택해주세요'
                  ]"
                  lazy-rules="ondemand"
                  hide-bottom-space
                >
                  <div class="row justify-center">
                    <template
                      v-for="(personality, index) in personalities"
                      :key="index"
                    >
                      <q-btn
                        v-if="!personality.value"
                        class="q-ma-xs q-mb-sm"
                        :label="personality.name"
                        color="secondary"
                        text-color="white"
                        dense
                        rounded
                        @click="toggle(2, personality.key)"
                      />
                      <q-btn
                        v-if="personality.value"
                        class="q-ma-xs q-mb-sm"
                        :label="personality.name"
                        color="primary"
                        text-color="white"
                        dense
                        rounded
                        @click="toggle(2, personality.key)"
                      />
                    </template>
                  </div>
                </q-field>
              </td>
            </tr>
          </table>
        </section>

        <q-btn label="다음" type="submit" class="primary q-mt-md" />
      </q-form>
    </section>
    <ConfirmModal
      v-model="this.showModal"
      @close="movePage"
      :modalContent="this.modalContent"
    />
  </div>
</template>

<script>
import { ref, reactive } from 'vue'
import {
  checkPassword,
  requestProfile,
  requestModifyProfile,
  checkNicknameExist
} from '@/api/user'
import { mapState, mapActions } from 'vuex'
import ConfirmModal from '../ConfirmModal.vue'
const userStore = 'userStore'

export default {
  setup() {
    const beforeNickname = ref(null)
    const passwordCheck = ref(false)
    const password = ref(null)
    const imageNo = ref(0)
    const imageUrl = ref(require('../../assets/profile/0.png'))
    const popupProfile = ref(false)
    const nickname = ref(null)
    const nicknameValid = ref(true)
    const mbtiOptions = ref([
      '모름',
      'INFP',
      'ENFP',
      'ESFJ',
      'ISFJ',
      'ISFP',
      'ESFP',
      'INTP',
      'INFJ',
      'ENFJ',
      'ENTP',
      'ESTJ',
      'ISTJ',
      'INTJ',
      'ISTP',
      'ESTP',
      'ENTJ'
    ])
    const drinkSelected = ref(null)
    const smokeSelected = ref(null)
    const mbtiSelected = ref(null)
    const religionSelected = ref(null)
    // 2
    const interests = reactive([
      { key: 0, name: '게임', value: false },
      { key: 1, name: '운동', value: false },
      { key: 2, name: '영화', value: false },
      { key: 3, name: '독서', value: false },
      { key: 4, name: '음악', value: false },
      { key: 5, name: '맛집탐방', value: false },
      { key: 6, name: '패션', value: false },
      { key: 7, name: '채식', value: false },
      { key: 8, name: '반려동물', value: false },
      { key: 9, name: '재테크', value: false },
      { key: 10, name: '자동차', value: false }
    ])
    const personalities = reactive([
      { key: 0, name: '차분한', value: false },
      { key: 1, name: '발랄한', value: false },
      { key: 2, name: '센스있는', value: false },
      { key: 3, name: '배려많은', value: false },
      { key: 4, name: '당당한', value: false },
      { key: 5, name: '열정적인', value: false },
      { key: 6, name: '개인적인', value: false },
      { key: 7, name: '긍정적인', value: false },
      { key: 8, name: '감각적인', value: false },
      { key: 9, name: '온화한', value: false },
      { key: 10, name: '소박한', value: false }
    ])
    const showModal = ref(false)
    const willPageMove = ref(false)
    const path = ref(null)
    const modalContent = 'ID 또는 PW가 일치하지 않습니다'
    return {
      beforeNickname,
      password,
      passwordCheck,
      imageNo,
      imageUrl,
      popupProfile,
      nickname,
      nicknameValid,
      drinkOptions: [
        {
          label: '안함',
          value: 0
        },
        {
          label: '가끔',
          value: 1
        },
        {
          label: '자주',
          value: 2
        }
      ],
      smokeOptions: [
        {
          label: '비흡연',
          value: 0
        },
        {
          label: '흡연',
          value: 1
        }
      ],
      mbtiOptions,
      religionOptions: [
        {
          label: '무교',
          value: 0
        },
        {
          label: '개신교',
          value: 1
        },
        {
          label: '불교',
          value: 2
        },
        {
          label: '천주교',
          value: 3
        },
        {
          label: '기타',
          value: 4
        }
      ],
      drinkSelected,
      smokeSelected,
      mbtiSelected,
      religionSelected,
      interests,
      personalities,
      showModal,
      modalContent,
      willPageMove,
      path,

      imgSelect(n) {
        imageNo.value = n
        imageUrl.value = require('../../assets/profile/' + n + '.png')
      },

      toggle(num, key) {
        let item = null
        if (num === 1) item = interests
        else item = personalities
        item[key].value = !item[key].value
      }
    }
  },
  components: {
    ConfirmModal
  },
  computed: {
    ...mapState(userStore, ['profile']),
    imgUrl() {
      return '../../assets/profile/' + this.imageNo + '.png'
    },

    interestSelected() {
      const arr = []
      this.interests.forEach((interest) => {
        if (interest.value) arr.push(interest.key)
      })
      return arr
    },
    personalitySelected() {
      const arr = []
      this.personalities.forEach((personality) => {
        if (personality.value) arr.push(personality.key)
      })
      return arr
    }
  },
  methods: {
    ...mapActions(userStore, ['getProfile']),
    submitPassword() {
      checkPassword(
        { password: this.password },
        ({ data }) => {
          if (data.statusCode === 200) {
            this.showModal = true
            this.modalContent = '인증이 완료되었습니다.'
            this.willPageMove = false
            this.passwordCheck = true
          }
        },
        ({ response }) => {
          if (response.status === 404) {
            this.showModal = true
            this.modalContent = '비밀번호가 틀렸습니다.'
            this.willPageMove = false
          } else if (response.status === 401) {
            this.showModal = true
            this.modalContent = '로그인이 만료되었습니다. 다시 로그인해주세요.'
            this.willPageMove = true
            this.path = '/user/login'
          } else {
            this.showModal = true
            this.modalContent = '에러가 발생했습니다. 다시 시도해주세요.'
            this.willPageMove = false
          }
        }
      )
    },
    async onSubmit() {
      requestModifyProfile(
        {
          imageNo: this.imageNo,
          nickname: this.nickname,
          drink: this.drinkSelected,
          smoke: this.smokeSelected,
          mbti: this.mbtiSelected,
          religion: this.religionSelected,
          interests: this.interestSelected,
          personalities: this.personalitySelected
        },
        ({ data }) => {
          const token = data.authorization
          if (data.statusCode === 200) {
            this.showModal = true
            this.modalContent = '프로필 수정을 완료했습니다.'
            sessionStorage.setItem('Authorization', token)
            this.willPageMove = true
            this.path = '/'
          }
        },
        ({ response }) => {
          if (response.statusCode === 401) {
            this.showModal = true
            this.modalContent = '로그인이 만료되었습니다. 다시 로그인해주세요.'
            this.willPageMove = true
            this.path = '/user/login'
          } else {
            this.showModal = true
            this.modalContent = '에러가 발생했습니다. 다시 시도해주세요.'
            this.willPageMove = false
          }
        }
      )
    },
    checkNickname(nickname) {
      if (nickname === null) {
        this.showModal = true
        this.modalContent = '닉네임을 입력해주세요.'
        this.willPageMove = false
        this.nicknameValid = false
        return
      }
      if (nickname === this.beforeNickname) {
        this.showModal = true
        this.modalContent = '현재 사용 중인 닉네임입니다.'
        this.willPageMove = false
        this.nicknameValid = true
        return
      }
      checkNicknameExist(
        nickname,
        (response) => {
          if (response.data.statusCode === 200) {
            this.showModal = true
            this.modalContent = '해당 닉네임은 사용 가능합니다.'
            this.willPageMove = false
            this.nicknameValid = true
          }
        },
        (error) => {
          if (error.response.data.message === 'Duplicate Nickname') {
            this.modalContent = '이미 사용 중인 닉네임입니다.'
          } else {
            this.modalContent = '에러가 발생했습니다. 다시 시도해주세요.'
          }
          this.showModal = true
          this.willPageMove = false
          this.nicknameValid = false
        }
      )
    },
    movePage() {
      if (this.willPageMove) {
        this.$router.push(this.path)
      }
    }
  },
  created() {
    requestProfile(
      ({ data: { profile } }) => {
        this.imageNo = profile.imageNo
        this.imageUrl = require('../../assets/profile/' + this.imageNo + '.png')
        this.beforeNickname = profile.nickname
        this.nickname = profile.nickname
        this.drinkSelected = profile.drink
        this.smokeSelected = profile.smoke
        this.mbtiSelected = profile.mbti
        this.religionSelected = profile.religion
        profile.interests.forEach((interest) => {
          this.interests[interest].value = true
        })
        profile.personalities.forEach((personality) => {
          this.personalities[personality].value = true
        })
      },
      () => {
        this.showModal = true
        this.modalContent = '에러가 발생했습니다. 다시 시도해주세요.'
        this.willPageMove = true
        this.path = '/'
      }
    )
  }
}
</script>

<style scoped>
.basic-container {
  width: 400px;
}

.profile-div {
  position: relative;
  overflow: none;
}

.profile-img {
  width: 100%;
}

.profile-select-btn {
  position: absolute;
  bottom: 0%;
  right: -20%;
}
</style>
