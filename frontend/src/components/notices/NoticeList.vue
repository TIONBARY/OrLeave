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
          <tr :key="noticeList" v-for="noticeList in notices.content">
            <td class="q-pa-md" style="text-align: start" >
              <router-link :to="`/notice/${noticeList.no}`" @click="goDetail(noticeList.no)"   >{{
                noticeList.title
              }}</router-link>
            </td>
            <td class="q-pa-md" style="text-align: end">
              {{ noticeList.createdTime }}
            </td>
          </tr>
        </table>
      </section>
      <br />
      <div class="btn-cover">
        <q-btn :disabled="pageNum===0" @click="prevPage">이전</q-btn>
        <span :key="num" v-for="num in 3" >
        <q-btn  v-if="pageNum+num <= this.notices.totalPages"  @click="movePage(pageNum+num-1)" >
          {{pageNum+num}}
          </q-btn>
        </span>
        <q-btn :disabled="pageNum >= this.notices.totalPages-1" @click="nextPage">다음</q-btn>
      </div>
    </div>
  </div>
</template>

<script>
import { ref } from 'vue'

import { mapState, mapActions } from 'vuex'
const noticeStore = 'noticeStore'

export default {
  setup() {
    const page = ref(0)
    const size = ref(5)
    return {
      page,
      size,
      pageNum: 0
    }
  },
  computed: {
    ...mapState(noticeStore, ['notices'])
  },
  methods: {
    ...mapActions(noticeStore, ['noticeList', 'noticeDetail']),
    goDetail(no) {
      this.noticeDetail(no)
    },
    nextPage() {
      this.pageNum += 1
      this.noticeList(this.pageNum)
    },
    prevPage() {
      this.pageNum -= 1
      this.noticeList(this.pageNum)
    },
    movePage(num) {
      this.pageNum = num
      this.noticeList(this.pageNum)
      console.log(this.pageNum + 1)
    }
  },
  async created() {
    await this.noticeList(0)
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
