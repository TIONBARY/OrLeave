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
              {{ noticeDetail.noticeTitle }}
            </td>
          </tr>
          <tr>
            <td
              colspan="2"
              class="q-pa-md"
              style="text-align: start; background-color: white"
            >
              {{ noticeDetail.noticeContent }}
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
import { ref } from 'vue'

export default {
  setup() {
    const noticeDetail = ref({
      noticeTitle: '시스템 점검 안내',
      noticeContent:
        '안녕하세요, 역삼 더 프리미엄 센트럴 메트로 어쩌구 개발팀 정승욱입니다. 2022년 07월 20일 04:00~06:00시까지 시스템 점검이 있음을 알립니다. 해당 시간에는 서비스 이용이 금지되오니 사용에 주의하시기 바랍니다. 늘 저희의 서비스를 사랑해주시고 이용해주셔서 감사합니다. 응원하는 마음으로 지켜봐주시면 감사하겠습니다. 감사합니다.'
    })

    return {
      noticeDetail
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
}

th {
  border-bottom: 2px solid #000000;
}
</style>
