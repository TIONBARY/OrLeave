<template>
  <div>
    <div class="fit column items-center justify-center">
      <q-img
        class="q-mt-xl"
        :src="require('../assets/logo_l.png')"
        spinner-color="white"
        style="min-width: 280px; max-width: 500px; margin: 100px auto 0px"
      />
      <div>
        <q-btn class="primary q-px-xl" label="시작하기" size="lg" />
        <q-fab
          class="column items-center"
          color="primary"
          icon="arrow_drop_down"
          direction="down"
          vertical-actions-align="center"
          flat
          round
        >
          <q-card style="background-color: #b3a286; color: white; width: 500px">
            <table width="100%" style="text-align: center">
              <tr>
                <td width="10%">나이</td>
                <td width="60%">
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
                    :max="3"
                    style="width: 80%"
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
          </q-card>
        </q-fab>
      </div>
    </div>

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
      ages: { 0: '20', 1: '30', 2: '40', 3: '50' },
      distance,
      distances: { 0: '10km', 1: '20km', 2: '50km', 3: ' ' },
      drink,
      drinks: { 0: '20', 1: '30', 2: '40', 3: '50' },
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
