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
          <tr :key="noticeList" v-for="noticeList in noticeLists">
            <td class="q-pa-md" style="text-align: start">
              <router-link to="/notice/detail/1">{{
                noticeList.noticeTitle
              }}</router-link>
            </td>
            <td class="q-pa-md" style="text-align: end">
              {{ noticeList.noticeDate }}
            </td>
          </tr>
        </table>
      </section>
      <br />
      <div>
        <q-btn>이전</q-btn>
        <q-btn>1</q-btn>
        <q-btn>2</q-btn>
        <q-btn>다음</q-btn>
      </div>
    </div>
  </div>
</template>

<script>
import { ref } from 'vue'

export default {
  setup() {
    const noticeLists = ref([
      {
        noticeTitle: '시스템 점검 안내',
        noticeDate: '2022/08/02'
      },
      {
        noticeTitle: 'Internet Explorer 11 지원 종료 안내',
        noticeDate: '2022/07/02'
      },
      {
        noticeTitle: '서비스 이용 약관 개정 안내',
        noticeDate: '2022/06/02'
      }
    ])

    return {
      noticeLists
    }
  }
}
</script>

<style scoped>
.basic-container {
  width: 50%;
}

table {
  width: 100%;
  border-bottom: 2px solid #000000;
  border-collapse: collapse;
}

th {
  border-bottom: 2px solid #000000;
}

tr {
  border-bottom: 1px solid #979797;
}
</style>
