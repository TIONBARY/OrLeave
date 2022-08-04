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
            to="/user/login"
            style="margin: auto"
          />
          <q-btn
            color="primary"
            @click="alert = true"
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
                          :min="0"
                          :max="3"
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
                          v-model="distance"
                          color="primary"
                          markers
                          :marker-labels="distances"
                          :min="0"
                          :max="3"
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
                        <q-radio v-model="smoke" val="0" label="상관없음" />
                        <q-radio v-model="smoke" val="1" label="비흡연" />
                        <q-radio v-model="smoke" val="2" label="흡연" />
                      </td>
                    </tr>
                    <tr>
                      <td>종교</td>
                      <td>
                        <q-radio v-model="religion" val="0" label="상관없음" />
                        <q-radio v-model="religion" val="1" label="같은 종교" />
                      </td>
                    </tr>
                  </table>
                </q-card-section>

                <q-card-actions align="center">
                  <q-btn flat label="OK" color="primary" v-close-popup />
                </q-card-actions>
              </q-card>
            </q-dialog>
          </div>
        </div>
      </div>
    </div>
    <div class="item pageSection">
      <div class="intro-title text-left">
        <h1>듣다,</h1>
        <h2>상대방과 이야기를 나누며<br />어떤 사람인지 알아보세요.</h2>
      </div>
      <q-img
        class="img-container intro-img"
        :src="require('../assets/main/dateimg1.png')"
      />
    </div>
    <div class="item pageSection">
      <div class="intro-title text-left">
        <h1>보다,</h1>
        <h2>마음이 잘 통한다면,<br />서로의 눈을 보며 대화해보세요.</h2>
      </div>
      <q-img
        class="img-container intro-img"
        :src="require('../assets/main/dateimg2.png')"
      />
    </div>
    <div class="item pageSection">
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
              정승욱
            </div>
            <div>
              <q-avatar size="25px">
                <q-img src="../assets/profile/2.png" />
              </q-avatar>
              정지은
            </div>
            <div class="text-h5">Backend</div>
            <div>
              <q-avatar size="25px">
                <q-img src="../assets/profile/3.png" />
              </q-avatar>
              박상수
            </div>
            <div>
              <q-avatar size="25px">
                <q-img src="../assets/profile/5.png" />
              </q-avatar>
              윤지환
            </div>
            <div>
              <q-avatar size="25px">
                <q-img src="../assets/profile/4.png" />
              </q-avatar>
              허상
            </div>
          </div>
        </div>
      </div>
    </div>
  </vue-scroll-snap>
</template>
<script>
import { ref } from 'vue'
import { onMounted } from '@vue/runtime-core'
import VueScrollSnap from 'vue-scroll-snap'

export default {
  components: { VueScrollSnap },
  data() {
    return {
      sampleData: ''
    }
  },
  setup() {
    onMounted(() => {})

    const index = ref(2)
    const popupMeetingsetting = ref(false)
    const distance = ref(1)
    const age = ref({
      min: 0,
      max: 1
    })
    const drink = ref({
      min: 0,
      max: 1
    })

    return {
      index,
      popupMeetingsetting,
      age,
      alert: ref(false),
      ages: { 0: '20', 1: '30', 2: '40', 3: '50' },
      distance,
      distances: { 0: '10km', 1: '20km', 2: '50km', 3: '100km' },
      drink,
      drinks: { 0: '안함', 1: '가끔', 2: '자주' },
      smoke: ref(null),
      religion: ref(null)
    }
  },
  created() {},
  mounted() {},
  unmounted() {},
  methods: {}
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
  background-color: whitesmoke;
  border-radius: 20px;
  padding: 0px;
  height: auto;
  width: auto;
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
  right: 20%;
  bottom: 15%;
  min-width: 30%;
  min-height: 30%;
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
