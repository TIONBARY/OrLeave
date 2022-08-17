<template>
  <vue-scroll-snap>
    <div class="item row items-center justify-center">
      <div>
        <q-img
          :src="require('../assets/logo_l.png')"
          spinner-color="white"
          width="400px"
        />

        <div class="q-mt-sm">
          <q-btn
            class="primary q-px-xl"
            label="시작하기"
            size="lg"
            @click="startMatching"
            style="margin: auto"
          />
          <q-btn
            color="primary"
            @click="checkMeetingSetting"
            icon="arrow_drop_down"
            class="q-ma-sm"
            flat
            round
          />
          <div>
            <q-dialog v-model="alert">
              <q-card
                style="background-color: #b3a286; color: white; width: 500px"
              >
                <q-card-section>
                  <table width="100%" style="text-align: center">
                    <tr>
                      <td width="10%">나이</td>
                      <td width="70%" class="q-px-ml">
                        <q-range
                          class="q-ma-sm"
                          v-model="age"
                          color="primary"
                          markers
                          :marker-labels="ages"
                          :min="20"
                          :max="50"
                          style="width: 80%"
                        />
                      </td>
                    </tr>
                    <q-separator inset />
                    <tr>
                      <td>거리</td>
                      <td>
                        <q-slider
                          class="q-mt-sm"
                          v-model="this.meetingSetting.distance"
                          color="primary"
                          markers
                          :marker-labels="distances"
                          :min="0"
                          :max="4"
                          style="width: 80%"
                        />
                      </td>
                    </tr>
                    <tr>
                      <td>음주</td>
                      <td>
                        <q-range
                          class="q-mt-sm"
                          v-model="drink"
                          color="primary"
                          markers
                          :marker-labels="drinks"
                          :min="0"
                          :max="2"
                          style="width: 80%; white-space: nowrap"
                        />
                      </td>
                    </tr>
                    <tr>
                      <td>흡연</td>
                      <td>
                        <q-radio
                          v-model="this.meetingSetting.smoke"
                          :val="0"
                          label="상관없음"
                        />
                        <q-radio
                          v-model="this.meetingSetting.smoke"
                          :val="1"
                          label="비흡연"
                        />
                        <q-radio
                          v-model="this.meetingSetting.smoke"
                          :val="2"
                          label="흡연"
                        />
                      </td>
                    </tr>
                    <tr>
                      <td>종교</td>
                      <td>
                        <q-radio
                          v-model="this.meetingSetting.religion"
                          :val="0"
                          label="상관없음"
                        />
                        <q-radio
                          v-model="this.meetingSetting.religion"
                          :val="1"
                          label="같은 종교"
                        />
                      </td>
                    </tr>
                  </table>
                </q-card-section>

                <q-card-actions align="center">
                  <q-btn
                    flat
                    label="OK"
                    color="primary"
                    v-close-popup
                    @click="setMeetingSetting"
                  />
                </q-card-actions>
              </q-card>
            </q-dialog>
          </div>
        </div>
      </div>
    </div>
    <div class="item pageSection row">
      <div class="intro-title text-left">
        <h1>듣다,</h1>
        <h2>상대방과 이야기를 나누며<br />어떤 사람인지 알아보세요.</h2>
      </div>
      <q-img
        class="img-container intro-img"
        :src="require('../assets/main/dateimg1.png')"
      />
    </div>
    <div class="item pageSection row">
      <div class="intro-title text-left">
        <h1>보다,</h1>
        <h2>마음이 잘 통한다면,<br />서로의 눈을 보며 대화해보세요.</h2>
      </div>
      <q-img
        class="img-container intro-img"
        :src="require('../assets/main/dateimg2.png')"
      />
    </div>
    <div class="item pageSection row">
      <div class="intro-title text-left">
        <h1>잡다,</h1>
        <h2>마음이에 드는 상대를<br />쟁취하세요.</h2>
      </div>
      <q-img
        class="img-container intro-img"
        :src="require('../assets/main/dateimg3.png')"
      />
    </div>

    <div class="item">
      <div class="third">
        <h1>개발자 소개</h1>
        <div class="q-mt-md row justify-center">
          <div class="img-container">
            <q-img
              class="last-img"
              src="../assets/main/developers.png"
              width="500px"
              height="400px"
            />
          </div>
          <div class="column q-ma-xl q-gutter-sm text-left">
            <div class="text-h5">Frontend</div>
            <div>
              <q-avatar size="25px">
                <q-img src="../assets/profile/10.png" />
              </q-avatar>
              김시언:&nbsp;
              <q-avatar class="q-mr-sm" size="20px">
                <q-img src="../assets/main/github.png" />
              </q-avatar>
              <q-avatar size="20px">
                <q-img src="../assets/main/instagram.png" />
              </q-avatar>
            </div>
            <div>
              <q-avatar size="25px">
                <q-img src="../assets/profile/25.png" />
              </q-avatar>
              정승욱:&nbsp;
              <q-avatar class="q-mr-sm" size="20px">
                <q-img src="../assets/main/github.png" />
              </q-avatar>
              <q-avatar size="20px">
                <q-img src="../assets/main/instagram.png" />
              </q-avatar>
            </div>
            <div>
              <q-avatar size="25px">
                <q-img src="../assets/profile/2.png" />
              </q-avatar>
              정지은:&nbsp;
              <q-avatar class="q-mr-sm" size="20px">
                <q-img src="../assets/main/github.png" />
              </q-avatar>
              <q-avatar size="20px">
                <q-img src="../assets/main/instagram.png" />
              </q-avatar>
            </div>
            <div class="text-h5">Backend</div>
            <div>
              <q-avatar size="25px">
                <q-img src="../assets/profile/3.png" />
              </q-avatar>
              박상수:&nbsp;
              <q-avatar class="q-mr-sm" size="20px">
                <q-img src="../assets/main/github.png" />
              </q-avatar>
              <q-avatar size="20px">
                <q-img src="../assets/main/instagram.png" />
              </q-avatar>
            </div>
            <div>
              <q-avatar size="25px">
                <q-img src="../assets/profile/5.png" />
              </q-avatar>
              윤지환:&nbsp;
              <q-avatar class="q-mr-sm" size="20px">
                <q-img src="../assets/main/github.png" />
              </q-avatar>
              <q-avatar size="20px">
                <q-img src="../assets/main/instagram.png" />
              </q-avatar>
            </div>
            <div>
              <q-avatar size="25px">
                <q-img src="../assets/profile/4.png" />
              </q-avatar>
              허상:&nbsp;
              <q-avatar class="q-mr-sm" size="20px">
                <q-img src="../assets/main/github.png" />
              </q-avatar>
              <q-avatar size="20px">
                <q-img src="../assets/main/instagram.png" />
              </q-avatar>
            </div>
          </div>
        </div>
      </div>
    </div>
    <ConfirmModal
      v-model="this.showModal"
      @close="movePage"
      :modalContent="this.modalContent"
    />
  </vue-scroll-snap>
</template>
<script>
import { ref } from 'vue'
import { getMeetingSetting, setMeetingSetting } from '@/api/meeting'
import { getEmail } from '@/api/user'
import VueScrollSnap from 'vue-scroll-snap'
import ConfirmModal from './ConfirmModal.vue'

export default {
  components: { VueScrollSnap, ConfirmModal },
  data() {
    return {
      sampleData: ''
    }
  },
  setup() {
    const index = ref(2)
    const popupMeetingsetting = ref(false)
    const distance = ref(1)
    const age = ref({
      min: 20,
      max: 20
    })
    const drink = ref({
      min: 0,
      max: 1
    })
    const showModal = ref(false)
    const modalContent = ref(null)
    const willPageMove = ref(false)
    const path = ref(null)
    const meetingSetting = ref({
      religion: 0,
      smoke: 0,
      drink_min: 0,
      drink_max: 2,
      distance: 4,
      age_min: 20,
      age_max: 50
    })
    return {
      index,
      popupMeetingsetting,
      age,
      alert: ref(false),
      distance,
      ages: { 20: '20', 30: '30', 40: '40', 50: '50' },
      distances: { 0: '10km', 1: '20km', 2: '50km', 3: '100km', 4: 'anywhere' },
      drink,
      drinks: { 0: '안함', 1: '가끔', 2: '자주' },
      smoke: ref(null),
      religion: ref(null),
      showModal,
      modalContent,
      willPageMove,
      path,
      meetingSetting
    }
  },
  methods: {
    checkMeetingSetting() {
      this.alert = true
      getMeetingSetting(
        ({ data }) => {
          if (data.statusCode === 200) {
            this.meetingSetting = data.meetingsetting
            this.age = {
              min: this.meetingSetting.age_min,
              max: this.meetingSetting.age_max
            }
            this.drink = {
              min: this.meetingSetting.drink_min,
              max: this.meetingSetting.drink_max
            }
          }
        },
        ({ response }) => {
          if (response.status === 401) {
            this.modalContent = '로그인이 필요합니다.'
            this.path = '/user/login'
          } else {
            this.modalContent = '에러가 발생했습니다. 다시 시도해주세요.'
            this.path = '/'
          }
          this.willPageMove = true
          this.showModal = true
        }
      )
    },
    setMeetingSetting() {
      this.meetingSetting.age_min = this.age.min
      this.meetingSetting.age_max = this.age.max
      this.meetingSetting.drink_min = this.drink.min
      this.meetingSetting.drink_max = this.drink.max
      setMeetingSetting(
        this.meetingSetting,
        () => {},
        ({ response }) => {
          if (response.status === 401) {
            this.modalContent = '로그인이 만료되었습니다. 로그인해주세요.'
            this.path = '/user/login'
          } else {
            this.modalContent = '에러가 발생했습니다. 다시 시도해주세요.'
            this.path = '/'
          }
          this.willPageMove = true
          this.showModal = true
        }
      )
    },
    startMatching() {
      getEmail(
        () => {
          this.$router.push('/meeting/lobby')
        },
        () => {
          this.showModal = true
          this.modalContent = '로그인이 필요합니다.'
          this.willPageMove = true
          this.path = '/user/login'
        }
      )
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
.third {
  white-space: nowrap;
}
.member-card {
  background: #e5edb8;
  width: auto;
  height: auto;
}
.img-container {
  background-color: transparent;
}

.pageSection {
  position: relative;
}

.intro-title {
  position: absolute;
  left: 20%;
  top: 10%;
}
.intro-img {
  position: absolute;
  right: 10%;
  bottom: 10%;
  width: 400px;
  height: auto;
  min-width: 40%;
  min-height: auto;
  max-width: none;
  background-color: transparent;
}
/* scroll-snap에 필요한 css */
.item {
  /* Set the minimum height of the items to be the same as the height of the scroll-snap-container.*/
  min-height: 100vh;
}
.scroll-snap-container {
  height: 100vh;
  width: 100vw;
}
/* scroll-snap에 필요한 css */
</style>
