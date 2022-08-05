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
          <th colspan="2" class="q-pa-md" style="text-align: start">내 문의</th>
          <tr :key="boardList" v-for="boardList in boardLists">
            <td class="q-pa-md" style="text-align: start">
              <router-link to="/inquiry/detail/1">{{
                boardList.boardTitle
              }}</router-link>
            </td>
            <td class="q-pa-md" style="text-align: end">
              {{ boardList.boardDate }}
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
        <router-link to="/board/write">문의하기</router-link>
      </div>
    </div>
  </div>
</template>

<script>
import { ref } from 'vue'

export default {
  setup() {
    const boardLists = ref([
      {
        boardTitle: '화상통화시 음성 연결이 잘 안되는 문제가 자주 있습니다.',
        boardDate: '2022/08/02'
      },
      {
        boardTitle: '매칭이 잘 안됩니다.',
        boardDate: '2022/07/02'
      },
      {
        boardTitle: '알림 소리를 키우고 싶습니다.',
        boardDate: '2022/06/02'
      }
    ])

    return {
      boardLists
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
