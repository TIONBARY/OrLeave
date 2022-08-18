<template>
  <div>
    <q-img
      class="q-ma-lg absolute-top-left cursor-pointer"
      :src="require('../../assets/logo_manager.png')"
      alt="image"
      style="width: 80%; max-width: 200px"
      @click="this.$router.push('/manager/main')"
    />
    <br />
    <br />
    <br />
    <br />
    <br />
    <hr />
    <div class="q-pa-xl">
      <section class="basic-container">
        <table>
          <th colspan="2" class="q-pa-md" style="text-align: start">
            공지사항
          </th>
          <tr :key="noticeList" v-for="noticeList in notices.content">
            <td
              class="q-pa-md"
              style="text-align: start"
              @click="goDetail(noticeList.no)"
            >
              <router-link :to="`${noticeList.no}`">{{
                noticeList.title
              }}</router-link>
            </td>
            <td class="q-pa-md" style="text-align: end">
              {{ noticeList.createdTime }}
            </td>
          </tr>
        </table>
        <div class="right row justify-end q-pt-lg">
          <q-btn class="primary" @click="move('managerNoticeRegist')"
            >글쓰기</q-btn
          >
        </div>
      </section>
      <div class="q-pa-lg flex flex-center" @click="movePage">
        <q-pagination
          v-model="currentPage"
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
const maxPages = ref(10)

export default {
  setup() {
    const page = ref(0)
    return {
      page,
      pageNum: 0,
      currentPage: ref(1),
      maxPages
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
        this.notices.content[i].createdTime =
          this.notices.content[i].createdTime.split(' ')[0]
      }
    }
  },
  watch: {
    currentPage: function (num) {
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
