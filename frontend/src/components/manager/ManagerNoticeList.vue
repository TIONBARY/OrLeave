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
            <td class="q-pa-md" style="text-align: start" @click="goDetail(noticeList.no)" >
              <router-link :to="`${noticeList.no}`"   >{{
                noticeList.title
              }}</router-link>
            </td>
            <td class="q-pa-md" style="text-align: end">
              {{ noticeList.createdTime }}
            </td>
          </tr>
        </table>
      </section>
       <div class="q-pa-lg" >
        <q-btn @click="move('managerNoticeRegist')">글쓰기</q-btn>
      </div>
      <div class="q-pa-lg flex flex-center" @click="movePage">
        <q-pagination
          v-model="current"
          :max="this.notices.totalPages"
          :max-pages="3"
          boundary-numbers="false"
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
    const page = ref(0)
    return {
      page,
      pageNum: 0,
      current: ref(1)
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
    move(name) {
      this.$router.push({ name })
    },
    time() {
      for (let i = 0; i < this.notices.content.length; i++) {
        this.notices.content[i].createdTime = this.notices.content[i].createdTime.split(' ')[0]
        console.log(this.notices.content[i].createdTime)
      }
    }
  },
  watch: {
    current: function(num) {
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
