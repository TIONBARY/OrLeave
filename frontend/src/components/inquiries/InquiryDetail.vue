<template>
  <div>
    <q-img
      class="q-ma-lg"
      :src="require('../../assets/logo_l.png')"
      alt="image"
      style="width: 20%; height: 20%"
    />
    <hr />
    <div class="q-pa-xl">
      <section class="basic-container">
        <table>
          <th colspan="2" class="q-pa-md" style="text-align: start">
            1:1 문의
          </th>
          <tr>
            <td class="q-pa-md" style="width: 10%">제목</td>
            <td
              class="q-pa-md"
              style="text-align: start; background-color: white"
            >
              {{ this.inquiry.title }}
            </td>
          </tr>
          <tr>
            <td class="q-pa-md" style="width: 10%">내용</td>
            <td
              colspan="2"
              class="q-pa-md"
              style="text-align: start; background-color: white"
            >
              {{ this.inquiry.content }}
            </td>
          </tr>
          <tr>
            <td class="q-pa-md" style="width: 10%">답변</td>
            <td
              colspan="2"
              class="q-pa-md"
              style="text-align: start; background-color: white"
            >
              {{ this.inquiry.answer }}
            </td>
          </tr>
        </table>
        <br />
        <div>
          <q-btn>수정</q-btn>
          <q-btn>삭제</q-btn>
        </div>
      </section>
      <br />
      <div>
        <router-link to="/inquiry/list">목록으로</router-link>
      </div>
    </div>
  </div>
</template>

<script>
// import { ref } from 'vue'
import { mapState, mapActions } from 'vuex'
const inquiryStore = 'inquiryStore'

export default {
  setup() {
    // const inquiryDetail = ref({
    //   inquiryTitle: '',
    //   inquiryContent: '',
    //   inquiryAnswer: ''
    // })
  },
  computed: {
    ...mapState(inquiryStore, ['inquiry'])
  },
  methods: {
    ...mapActions(inquiryStore, ['inquiryDetail'])
  }
}
</script>

<style scoped>
.basic-container {
  width: 50%;
}

table {
  width: 100%;
}

th {
  border-bottom: 2px solid #000000;
}
</style>
