<template>
  <div>
    <q-img
      class="q-ma-lg"
      :src="require('../../assets/logo_l.png')"
      alt="image"
      style="width: 100%; max-width: 300px"
      @click="this.$router.push('/')"
    />
    <hr />
    <div class="q-pa-xl">
      <section class="basic-container">
        <table>
          <th colspan="2" class="q-pa-md" style="text-align: start">
            공지사항
          </th>
          <tr>
            <td class="q-pa-sm text-weight-bold" style="text-align: start">
              {{ this.notice.title }}
            </td>
          </tr>
          <tr>
            <td class="q-pl-sm text-left" style="font-size: 15px">
              {{ this.notice.createdTime.substring(0, 10) }}
            </td>
          </tr>
          <q-separator size="2px" />
          <tr class="q-my-md">
            <td
              colspan="2"
              class="q-pa-sm inquiryBox vertical-top"
              style="text-align: start; height: 200px"
            >
              <div v-html="this.notice.content" />
            </td>
          </tr>
        </table>
      </section>
      <br />
      <div>
        <q-btn @click="this.$router.push('/notice/list')" class="primary">
          목록으로
        </q-btn>
      </div>
    </div>
  </div>
</template>

<script>
import { mapState } from 'vuex'
const noticeStore = 'noticeStore'

export default {
  computed: {
    ...mapState(noticeStore, ['notice'])
  },
  created() {
    // this.notice.createdTime = this.notice.createdTime
  }
}
</script>

<style scoped>
.basic-container {
  width: 100%;
  max-width: 600px;
  min-width: 300px;
}

table {
  width: 100%;
  border-spacing: 10px;
}

th {
  border-bottom: 2px solid #000000;
}
</style>
