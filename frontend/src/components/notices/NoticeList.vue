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
           <template v-if="notices.content.length === 0">
            <tr>
              <td style="height: 150px">작성한 공지가 없습니다.</td>
            </tr>
          </template>
          <template v-else>
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
        </template>
        </table>
      </section>
      <br />
      <div class="q-pa-lg flex flex-center">
        <q-pagination
          v-model="this.pageNum"
          :max="this.notices.totalPages"
          :max-pages="this.maxPages"
          direction-links
          boundary-links
          icon-first="skip_previous"
          icon-last="skip_next"
          icon-prev="fast_rewind"
          icon-next="fast_forward"
        />
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
    const pageNum = ref(1)
    const maxPages = ref(10)
    return {
      maxPages,
      pageNum
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
    async movepage(num) {
      await this.noticeList(num - 1)
      this.time()
    },
    time() {
      for (let i = 0; i < this.notices.content.length; i++) {
        this.notices.content[i].createdTime = this.notices.content[i].createdTime.split(' ')[0]
        console.log(this.notices.content[i].createdTime)
      }
    }
  },
  watch: {
    pageNum: function(num) {
      this.movepage(num)
    }
  },
  async created() {
    await this.noticeList(0)
    this.time()
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
