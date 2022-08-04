<template>
  <div>
    <br />
    <div>매 칭 중 ...</div>
    <br />
    <div class="row q-gutter-md justify-center items-center">
      <q-skeleton width="500px" height="375px" />
      <div>
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
      videoOptions: ['기본 캠', '외장 캠']
    }
  }
}
</script>
