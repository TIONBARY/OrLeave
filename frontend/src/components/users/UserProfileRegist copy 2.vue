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
        <div class="q-gutter-md">
          <!-- 프로필 이미지들 넣어주기 -->
          <div class="row q-pa-sm items-center">
            <div class="col-4">
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
          <div class="row q-pa-sm items-center">
            <q-input
              outlined
              bg-color="white"
              type="text"
              v-model="nickname"
              dense="dense"
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
          </div>
          <div class="q-gutter-sm">
            <table width="100%">
            <th>
              <td style="width:50%"></td>
              <td style="width:50%"></td>
            </th>
              <tr>
                <td style="width:50%">
                  <div class="q-pa-sm items-center">
                    <q-select
                      outlined
                      bg-color="white"
                      v-model="drinkModel"
                      :options="drinkOptions"
                      label="음주여부"
                      dense
                    />
                  </div>
                </td>
                <td style="width:50%">
                  <div class="q-pa-sm items-center">
                    <q-select
                      outlined
                      bg-color="white"
                      v-model="smokeModel"
                      :options="smokeOptions"
                      label="흡연여부"
                      dense
                    />
                  </div>
                </td>
              </tr>
              <tr>
                <td style="width:50%">
                  <div class="q-pa-sm items-center">
                    <q-select
                      outlined
                      bg-color="white"
                      v-model="mbtiModel"
                      :options="mbtiOptions"
                      label="MBTI"
                      dense
                    />
                  </div>
                </td>
                <td style="width:50%">
                  <div class="q-pa-sm items-center">
                    <q-select
                      outlined
                      bg-color="white"
                      v-model="religionModel"
                      :options="religionOptions"
                      label="종교"
                      dense
                    />
                  </div>
                </td>
              </tr>
            </table>
          </div>

          <!-- 드롭다운-> 무엇을 선택했는지 보여주고 싶은데 잘 안됨 -->

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

      drinkModel: ref(null),
      smokeModel: ref(null),
      mbtiModel: ref(null),
      religionModel: ref(null),
      drinkOptions: ['좋아한다', '마시긴한다', '안마신다'],
      smokeOptions: ['흡연자', '비흡연자'],
      mbtiOptions: ['ISTJ', 'ENFP', 'INTJ', 'ISTP'],
      religionOptions: ['무교', '불교', '천주교', '기독교', '힌두교'],

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
