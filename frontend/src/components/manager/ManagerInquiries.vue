<template>
  <div>
    <q-img
      class="q-ma-lg absolute-top-left"
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
            문의 목록
          </th>
          <tr :key="inquiry" v-for="inquiry in inquiryList">
            <td class="q-pa-md" style="text-align: start">
              {{ inquiry.no }}
            </td>
            <td class="q-pa-md" style="text-align: start">
              <div class="cursor-pointer" @click="goManagerDetail(inquiry.no)">
                {{ inquiry.title }}
              </div>
            </td>
            <td class="q-pa-md" style="text-align: end">
              {{ inquiry.created_time }}
            </td>
          </tr>
        </table>
      </section>
      <br />
      <div class="q-pa-lg flex flex-center" @click="movePage">
        <q-pagination
          v-model="currentPage"
          :max="this.totalPages"
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
import { getInquiries } from '@/api/manager'

const userStore = 'userStore'
const inquiryStore = 'inquiryStore'
const perPage = ref(10)
const currentPage = ref(1)

export default {
  setup() {
    const pageNum = ref(1)
    const pageSize = ref(10)
    const totalPages = ref(0)
    const maxPages = ref(10)
    const inquiryList = ref([])
    return {
      pageNum,
      pageSize,
      totalPages,
      maxPages,
      inquiryList,
      perPage,
      currentPage
    }
  },
  computed: {
    ...mapState(userStore, ['userInfo']),
    ...mapState(inquiryStore, ['inquiries']),
    pagedInquiries() {
      const items = this.inquiries
      return items.slice(
        (this.currentPage - 1) * this.perPage,
        this.currentPage * this.perPage
      )
    },
    rows() {
      return this.inquiries.length
    }
  },
  created() {
    this.getInquiryList()
    // this.inquiries = this.getinquiries
    // console.log(this.getinquiries)
  },
  methods: {
    ...mapActions(inquiryStore, [
      'getinquiries',
      'inquiryDetail',
      'inquiryList'
    ]),
    goManagerDetail(no) {
      this.inquiryDetail(no)
      this.$router.push('/manager/inquiry/' + no)
    },
    async movepage(num) {
      await this.inquiryList(num - 1)
    },
    getInquiryList() {
      getInquiries(
        this.pageNum - 1,
        this.pageSize,
        ({ data }) => {
          this.inquiryList = data.inquiryList.content
          this.totalPages = data.inquiryList.totalPages
        },
        ({ response }) => {}
      )
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
