<template>
  <div>
    <section class="row items-center justify-center">
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
    </section>

    <table class="fixed-bottom-right q-ma-sm" style="text-align: right">
      <tr>
        처음으로
      </tr>
      <tr>
        서비스 소개
      </tr>
      <tr>
        개발자 소개
      </tr>
    </table>
    <!-- <div class="q-pa-md">
      <q-layout
        view="lHh Lpr lFf"
        container
        style="height: 400px"
        class="shadow-2 rounded-borders"
      >
        <q-header elevated>
          <q-toolbar>
            <q-avatar>
              <img
                src="https://cdn.quasar.dev/logo-v2/svg/logo-mono-white.svg"
              />
            </q-avatar>
            <q-toolbar-title>
              <strong>Quasar</strong> Framework
            </q-toolbar-title>
          </q-toolbar>
        </q-header>

        <q-page-container>
          <q-page padding>
            <p v-for="n in 15" :key="n">
              Lorem ipsum dolor sit amet consectetur adipisicing elit. Fugit
              nihil praesentium molestias a adipisci, dolore vitae odit, quidem
              consequatur optio voluptates asperiores pariatur eos numquam rerum
              delectus commodi perferendis voluptate?
            </p>

            place QPageScroller at end of page
 <q-page-scroller
              position="bottom-right"
              :scroll-offset="100"
              :offset="[18, 18]"
            >
              <q-btn fab icon="keyboard_arrow_up" color="accent" />
            </q-page-scroller>
            <q-page-scroller
              reverse
              position="top"
              :scroll-offset="20"
              :offset="[0, 18]"
            >
              <q-btn fab icon="keyboard_arrow_down" color="accent" />
            </q-page-scroller>
          </q-page>
        </q-page-container>
      </q-layout>
    </div> -->
  </div>
</template>
<script>
import { ref } from 'vue'
export default {
  components: {},
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
section {
  height: 100vh;
}
</style>
