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
        <q-form @submit="onSubmit" class="q-gutter-md">
          <table>
            <th colspan="2" class="q-pa-md" style="text-align: start">
              문의하기
            </th>
            <tr>
              <td class="q-pa-md" style="width: 20%; text-align: start">
                문의 분류
              </td>
              <td>
                <q-select
                  outlined
                  v-model="boardSelected"
                  :options="boardOptions"
                  style="background-color: white"
                  dense
                />
              </td>
            </tr>
            <tr>
              <td class="q-pa-md" style="width: 20%; text-align: start">
                문의 제목
              </td>
              <td>
                <q-input
                  outlined
                  v-model="boardTitle"
                  style="background-color: white"
                  dense
                />
              </td>
            </tr>
            <tr>
              <td class="q-pa-md" style="width: 20%; text-align: start">
                문의 내용
              </td>
              <td>
                <q-input
                  type="textarea"
                  outlined
                  v-model="boardContent"
                  style="background-color: white"
                  dense
                />
              </td>
            </tr>
            <tr>
              <td class="q-pa-md" style="width: 20%; text-align: start">
                이미지 첨부
              </td>
              <td>
                <q-input
                  outlined
                  type="file"
                  v-model="boardImage"
                  style="background-color: white"
                  dense
                />
              </td>
            </tr>
          </table>
          <q-btn label="문의 접수" type="submit" color="primary" />
        </q-form>
        <br />
      </section>
      <br />
    </div>
  </div>
</template>

<script>
import { ref } from 'vue'

export default {
  setup() {
    const boardTitle = ref(null)
    const boardContent = ref(null)
    const boardImage = ref(null)

    const boardSelected = ref(null)
    const boardOptions = ref(['버그 수정', '계정 문의', '건의 사항'])

    return {
      boardTitle,
      boardContent,
      boardImage,

      boardSelected,
      boardOptions,

      onSubmit() {
        console.log(boardImage)
        // 채워주삼
        // 아니면 q-form에 action method로 되면 이거 지우삼
      }
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

tr {
  border-bottom: 1px solid #979797;
}
</style>
