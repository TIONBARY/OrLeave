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
            공지사항
          </th>
          <tr>
            <td class="q-pa-md" style="width: 10%">제목</td>
            <td
              class="q-pa-md"
              style="text-align: start; background-color: white"
            >
              {{ this.notice.title }}
            </td>
          </tr>
          <tr>
            <td
              colspan="2"
              class="q-pa-md"
              style="text-align: start; background-color: white"
            >
              {{ this.notice.content }}
            </td>
          </tr>
        </table>
      </section>
      <br />
      <div>
        <router-link to="/notice/list">목록으로</router-link>
      </div>
    </div>
  </div>
</template>

<script>

import { mapState, mapActions } from 'vuex'
const noticeStore = 'noticeStore'

export default {
  computed: {
    ...mapState(noticeStore, ['notice'])
  },
  methods: {
    ...mapActions(noticeStore, ['noticeDetail'])
  },
  created() {
  //  this.noticeDetail(this.notice.no)
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
