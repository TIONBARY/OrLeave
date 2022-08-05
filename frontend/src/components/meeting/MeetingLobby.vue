<template>
  <div>
    <br />
    <div>매 칭 중 ...</div>
    <br />
    <div class="row q-gutter-md justify-center items-center">
      <q-skeleton width="500px" height="375px" />
      <div>
        <q-img src="../../assets/profile/4.png" width="30%" />
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
              style="background-color: #b3a286; color: white; width: 200px"
            >
              <q-card-section>
                <table width="100%" style="text-align: center">
                  <tr>
                    <td width="10%">나이</td>
                    <td width="70%" class="q-px-ml">20 - 30</td>
                  </tr>
                  <q-separator inset />
                  <tr>
                    <td>거리</td>
                    <td>10km - 20km</td>
                  </tr>
                  <tr>
                    <td>음주</td>
                    <td>안함</td>
                  </tr>
                  <tr>
                    <td>흡연</td>
                    <td>비흡연</td>
                  </tr>
                  <tr>
                    <td>종교</td>
                    <td>상관없음</td>
                  </tr>
                </table>
              </q-card-section>

              <q-card-actions align="center">
                <q-btn flat label="OK" color="primary" v-close-popup />
              </q-card-actions>
            </q-card>
          </q-dialog>
        </div>
        <br /><br />
        <q-select
          filled
          bg-color="secondary"
          v-model="micSelected"
          label="마이크"
          :options="micOptions"
          style="width: 250px"
          behavior="menu"
        />
        <br />
        <q-select
          filled
          bg-color="secondary"
          v-model="videoSelected"
          label="비디오"
          :options="videoOptions"
          style="width: 250px"
          behavior="menu"
        />
      </div>
    </div>
    <br />
    <div>
      <q-btn label="매칭 취소" color="secondary"></q-btn>
    </div>
  </div>
</template>

<script>
import { ref } from 'vue'

export default {
  setup() {
    return {
      micSelected: ref(null),
      videoSelected: ref(null),
      micOptions: ['기본 마이크', '외장 마이크'],
      videoOptions: ['기본 캠', '외장 캠'],
      alert: ref(false)
    }
  }
}
</script>
