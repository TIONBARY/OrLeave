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
              {{ boardDetail.boardTitle }}
            </td>
          </tr>
          <tr>
            <td
              colspan="2"
              class="q-pa-md"
              style="text-align: start; background-color: white"
            >
              {{ boardDetail.boardContent }}
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
import { ref } from 'vue'

export default {
  setup() {
    const boardDetail = ref({
      boardTitle: '매칭이 잘 안됩니다.',
      boardContent:
        '안녕하세요! 올리브 서비스를 2년째 이용중인 정승욱이라고 합니다. 일반 매칭은 매칭성사가 잘 되는데, 맞춤매칭으로 조건을 설정하면 매칭이 이상하게 잘 안되고 오래 걸리는 현상이 있는 것 같습니다. 혹시 이게 기술적인 문제인지 궁금해서 문의 남깁니다. 답변 주시면 감사하겠습니다!'
    })

    return {
      boardDetail
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
