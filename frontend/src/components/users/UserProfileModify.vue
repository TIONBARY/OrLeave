<template>
  <div>
    <br />
    <br />
    <h1>회원가입</h1>
    <section class="basic-container">
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
  </div>
</template>

<script>
import { ref, reactive } from 'vue'

import { mapState, mapActions } from 'vuex'
const userStore = 'userStore'

export default {
  setup() {
    const imageNo = ref(0)
    const imageUrl = ref(require('../../assets/profile/0.png'))
    const popupProfile = ref(false)
    const nickname = ref(null)
    const nicknameValid = ref(false)
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

    return {
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

      imgSelect(n) {
        imageNo.value = n
        imageUrl.value = require('../../assets/profile/' + n + '.png')
      },

      toggle(num, key) {
        let item = null
        if (num === 1) item = interests
        else item = personalities
        item[key].value = !item[key].value
      },
      checkNickname(nickname) {
        if (nickname !== null) nicknameValid.value = true
        console.log(nicknameValid.value)
      }
    }
  },
  computed: {
    ...mapState(userStore, ['accountInfo']),

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
    ...mapActions(userStore, ['trySignup']),

    async onSubmit() {
      await this.trySignup({
        ...this.accountInfo,
        imageNo: this.imageNo,
        nickname: this.nickname,
        drink: this.drinkSelected,
        smoke: this.smokeSelected,
        mbti: this.mbtiSelected,
        religion: this.religionSelected,
        interests: this.interestSelected,
        personalities: this.personalitySelected
      })
    }
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
