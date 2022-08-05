<template>
  <div>
    <q-avatar size="90px" class="q-my-lg">
      <q-btn round v-close-popup style="margin: 2px">
        <img
          :src="require('../assets/profile/' + index + '.png')"
          alt="image"
          style="width: 100%; height: 100%"
        />
        <q-menu
          fit
          anchor="bottom right"
          self="top end"
          :offset="[0, 10]"
          style="
            background-color: #e5edb8;
            width: 300px;
            font-family: 'GowunDodum-Regular';
          "
        >
          <q-item>
            <q-item-section>
              <div>
                <q-img
                  :src="require('../assets/profile/' + index + '.png')"
                  width="80px"
                />
                <span class="q-ma-md" style="font-size: 24px">감자튀김 님</span>
              </div>
            </q-item-section>
          </q-item>
          <q-separator color="black" />
          <q-item clickable>
            <q-item-section>프로필 수정</q-item-section>
          </q-item>
          <q-item clickable>
            <q-item-section>계정정보 수정</q-item-section>
          </q-item>
          <q-separator />
          <q-item clickable>
            <q-item-section>공지사항</q-item-section>
          </q-item>
          <q-item clickable>
            <q-item-section>1:1문의</q-item-section>
          </q-item>
          <q-item clickable>
            <q-item-section>차단관리</q-item-section>
          </q-item>
          <q-separator />
          <q-item clickable>
            <q-item-section>로그아웃</q-item-section>
          </q-item>
        </q-menu>
      </q-btn>
    </q-avatar>
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
    return {
      index
    }
  },
  created() {},
  mounted() {},
  unmounted() {},
  methods: {}
}
</script>
