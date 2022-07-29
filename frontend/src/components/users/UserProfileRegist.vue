<template>
  <!-- secondary의 색 바꿔야함. 임시적으로 secondary 사용! -->
  <!-- 배경 전체 되도록 조절 -->
  <!-- 관심사나 성격  -->
  <div>
    <h1 style="font-size: 30pt">회원가입</h1>
    <router-link to="/user/login">로그인</router-link>|
    <router-link to="/user/signup/account">계정생성</router-link>
    <section class="basic-container">
      <q-form @submit="onSubmit" @reset="onReset" class="q-gutter-md">
        <div class="q-pa-md">
          <!-- 프로필 이미지들 넣어주기 -->
          <table style="width: 100%">
            <tr>
              <td style="width: 30%">프로필<br />이미지</td>
              <td>
                <q-img
                  :src="url"
                  spinner-color="white"
                  style="height: 140px; max-width: 150px; border-radius: 100%"
                />
              </td>
              <td style="width: 30%">
                <q-btn
                  color="secondary"
                  @click=";[codeTransfer('이미지 선택'), refresh()]"
                  label="이미지 선택"
                />
              </td>
            </tr>

            <!-- <div class="row q-pa-sm items-center">
            <div class="col-3">프로필<br />이미지</div>
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
          </div> -->
            <tr class="items-center">
              <td colspan="3">
                <q-input
                  color="primary"
                  outlined
                  v-model="nickname"
                  label="닉네임"
                >
                  <template v-slot:append>
                    <q-btn
                      color="secondary"
                      @click="codeTransfer(nickname)"
                      label="중복 검사"
                    />
                  </template>
                </q-input>
              </td>
            </tr>
          </table>
          <!-- 드롭다운-> 무엇을 선택했는지 보여주고 싶은데 잘 안됨 -->
          <div class="row q-pa-sm items-center">
            <q-select
              filled
              v-model="model"
              :options="options"
              label="음주 여부"
              stack-label
              :dense="dense"
              :options-dense="denseOpts"
            />
          </div>
          <div class="row q-pa-sm items-center">
            <div class="col-3">흡연 여부</div>
            <div class="col-6">
              <q-btn-dropdown color="secondary" label="흡연 여부" class="fit">
                <q-list v-model="smoke">
                  <q-item clickable v-close-popup @click="onItemClick(0)">
                    <q-item-section>
                      <q-item-label>비흡연</q-item-label>
                    </q-item-section>
                  </q-item>
                  <q-item clickable v-close-popup @click="onItemClick(1)">
                    <q-item-section>
                      <q-item-label>흡연</q-item-label>
                    </q-item-section>
                  </q-item>
                </q-list>
              </q-btn-dropdown>
            </div>
            <div class="col-3"></div>
          </div>
          <div class="row q-pa-sm items-center">
            <div class="col-3">MBTI</div>
            <div class="col-6">
              <q-btn-dropdown color="secondary" label="MBTI" class="fit">
                <q-list>
                  <q-item clickable v-close-popup @click="onItemClick(0)">
                    <q-item-section>
                      <q-item-label>모름</q-item-label>
                    </q-item-section>
                  </q-item>
                  <q-item clickable v-close-popup @click="onItemClick(1)">
                    <q-item-section>
                      <q-item-label>INTJ</q-item-label>
                    </q-item-section>
                  </q-item>
                  <q-item clickable v-close-popup @click="onItemClick(2)">
                    <q-item-section>
                      <q-item-label>INTP</q-item-label>
                    </q-item-section>
                  </q-item>
                  <q-item clickable v-close-popup @click="onItemClick(3)">
                    <q-item-section>
                      <q-item-label>ENTJ</q-item-label>
                    </q-item-section>
                  </q-item>
                  <q-item clickable v-close-popup @click="onItemClick(4)">
                    <q-item-section>
                      <q-item-label>ENTP</q-item-label>
                    </q-item-section>
                  </q-item>
                  <q-item clickable v-close-popup @click="onItemClick(5)">
                    <q-item-section>
                      <q-item-label>INFJ</q-item-label>
                    </q-item-section>
                  </q-item>
                  <q-item clickable v-close-popup @click="onItemClick(6)">
                    <q-item-section>
                      <q-item-label>INFP</q-item-label>
                    </q-item-section>
                  </q-item>
                  <q-item clickable v-close-popup @click="onItemClick(7)">
                    <q-item-section>
                      <q-item-label>ENFJ</q-item-label>
                    </q-item-section>
                  </q-item>
                  <q-item clickable v-close-popup @click="onItemClick(8)">
                    <q-item-section>
                      <q-item-label>ENFP</q-item-label>
                    </q-item-section>
                  </q-item>
                  <q-item clickable v-close-popup @click="onItemClick(9)">
                    <q-item-section>
                      <q-item-label>ISTJ</q-item-label>
                    </q-item-section>
                  </q-item>
                  <q-item clickable v-close-popup @click="onItemClick(10)">
                    <q-item-section>
                      <q-item-label>ISFJ</q-item-label>
                    </q-item-section>
                  </q-item>
                  <q-item clickable v-close-popup @click="onItemClick(11)">
                    <q-item-section>
                      <q-item-label>ESTJ</q-item-label>
                    </q-item-section>
                  </q-item>
                  <q-item clickable v-close-popup @click="onItemClick(12)">
                    <q-item-section>
                      <q-item-label>ESFJ</q-item-label>
                    </q-item-section>
                  </q-item>
                  <q-item clickable v-close-popup @click="onItemClick(13)">
                    <q-item-section>
                      <q-item-label>ISTP</q-item-label>
                    </q-item-section>
                  </q-item>
                  <q-item clickable v-close-popup @click="onItemClick(14)">
                    <q-item-section>
                      <q-item-label>ISFP</q-item-label>
                    </q-item-section>
                  </q-item>
                  <q-item clickable v-close-popup @click="onItemClick(15)">
                    <q-item-section>
                      <q-item-label>ESTP</q-item-label>
                    </q-item-section>
                  </q-item>
                  <q-item clickable v-close-popup @click="onItemClick(16)">
                    <q-item-section>
                      <q-item-label>ESFP</q-item-label>
                    </q-item-section>
                  </q-item>
                </q-list>
              </q-btn-dropdown>
            </div>
          </div>
          <div class="row q-pa-sm items-center">
            <div class="col-3">종교</div>
            <div class="col-6">
              <q-btn-dropdown color="secondary" label="종교" class="fit">
                <q-list>
                  <q-item clickable v-close-popup @click="onItemClick(0)">
                    <q-item-section>
                      <q-item-label>기독교</q-item-label>
                    </q-item-section>
                  </q-item>
                  <q-item clickable v-close-popup @click="onItemClick(1)">
                    <q-item-section>
                      <q-item-label>이슬람교</q-item-label>
                    </q-item-section>
                  </q-item>
                  <q-item clickable v-close-popup @click="onItemClick(2)">
                    <q-item-section>
                      <q-item-label>힌두교</q-item-label>
                    </q-item-section>
                  </q-item>
                  <q-item clickable v-close-popup @click="onItemClick(3)">
                    <q-item-section>
                      <q-item-label>불교</q-item-label>
                    </q-item-section>
                  </q-item>
                </q-list>
              </q-btn-dropdown>
            </div>
          </div>
          <!-- 관심사,성격 배열에 추가하기. id 그냥 숫자로 하면 될지? -->
          <!-- 클릭했을 때 색 진하게 바꾸기. 도형 안에 관심사들/성격들 넣기 -->
          <div class="row q-pa-sm items-center">
            <div class="col-3">관심사</div>
            <div class="col-6">
              <div class="q-gutter-xs">
                <q-chip
                  v-model:selected="desert.Icecream"
                  color="secondary"
                  text-color="white"
                >
                  게임
                </q-chip>
                <q-chip
                  v-model:selected="desert.Eclair"
                  color="secondary"
                  text-color="white"
                >
                  운동
                </q-chip>
                <q-chip
                  v-model:selected="desert.Cupcake"
                  color="secondary"
                  text-color="white"
                >
                  영화
                </q-chip>
                <q-chip
                  v-model:selected="desert.Gingerbread"
                  color="secondary"
                  text-color="white"
                >
                  독서
                </q-chip>
                <q-chip
                  v-model:selected="desert.Icecream"
                  color="secondary"
                  text-color="white"
                >
                  음악
                </q-chip>
                <q-chip
                  v-model:selected="desert.Eclair"
                  color="secondary"
                  text-color="white"
                >
                  맛집탐방
                </q-chip>
                <q-chip
                  v-model:selected="desert.Cupcake"
                  color="secondary"
                  text-color="white"
                >
                  패션
                </q-chip>
                <q-chip
                  v-model:selected="desert.Gingerbread"
                  color="secondary"
                  text-color="white"
                >
                  채식
                </q-chip>
                <q-chip
                  v-model:selected="desert.Icecream"
                  color="secondary"
                  text-color="white"
                >
                  반려동물
                </q-chip>
                <q-chip
                  v-model:selected="desert.Eclair"
                  color="secondary"
                  text-color="white"
                >
                  재테크
                </q-chip>
                <q-chip
                  v-model:selected="desert.Cupcake"
                  color="secondary"
                  text-color="white"
                >
                  자동차
                </q-chip>
              </div>
            </div>
            <div class="col-3">
              {{ selection }}
            </div>
          </div>
          <div class="row q-pa-sm items-center">
            <div class="col-3">성격</div>
            <div class="col-6">
              <div class="col-6">
                <div class="q-gutter-xs">
                  <q-chip
                    v-model:selected="desert1.Icecream"
                    color="secondary"
                    text-color="white"
                  >
                    차분한
                  </q-chip>
                  <q-chip
                    v-model:selected="desert1.Eclair"
                    color="secondary"
                    text-color="white"
                  >
                    발랄한
                  </q-chip>
                  <q-chip
                    v-model:selected="desert1.Cupcake"
                    color="secondary"
                    text-color="white"
                  >
                    센스있는
                  </q-chip>
                  <q-chip
                    v-model:selected="desert1.Gingerbread"
                    color="secondary"
                    text-color="white"
                  >
                    배려많은
                  </q-chip>
                  <q-chip
                    v-model:selected="desert1.Icecream"
                    color="secondary"
                    text-color="white"
                  >
                    쿨한
                  </q-chip>
                  <q-chip
                    v-model:selected="desert1.Eclair"
                    color="secondary"
                    text-color="white"
                  >
                    열정적인
                  </q-chip>
                  <q-chip
                    v-model:selected="desert1.Cupcake"
                    color="secondary"
                    text-color="white"
                  >
                    개인적인
                  </q-chip>
                  <q-chip
                    v-model:selected="desert1.Gingerbread"
                    color="secondary"
                    text-color="white"
                  >
                    긍정적인
                  </q-chip>
                  <q-chip
                    v-model:selected="desert1.Icecream"
                    color="secondary"
                    text-color="white"
                  >
                    감각적인
                  </q-chip>
                  <q-chip
                    v-model:selected="desert1.Eclair"
                    color="secondary"
                    text-color="white"
                  >
                    온화한
                  </q-chip>
                  <q-chip
                    v-model:selected="desert1.Cupcake"
                    color="secondary"
                    text-color="white"
                  >
                    소박한
                  </q-chip>
                </div>
              </div>
            </div>
            <div class="col-3">
              {{ selection1 }}
            </div>
          </div>
        </div>
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
    const desert = reactive({
      Icecream: false,
      Eclair: false,
      Cupcake: false,
      Gingerbread: false
    })
    const desert1 = reactive({
      Icecream: false,
      Eclair: false,
      Cupcake: false,
      Gingerbread: false
    })

    return {
      url,
      gender,
      date,
      nickname,
      smoke,
      desert,
      desert1,

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

      selection: computed(() => {
        return Object.keys(desert)
          .filter((type) => desert[type] === true)
          .join(', ')
      }),

      selection1: computed(() => {
        return Object.keys(desert1)
          .filter((type) => desert1[type] === true)
          .join(', ')
      }),

      model: ref(null),

      options: ['안함', '가끔', '자주'],

      dense: ref(false),
      denseOpts: ref(false)
    }
  }
}
</script>

<style scoped>
.basic-container {
  width: 550px;
}
</style>
