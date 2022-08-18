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
          <th colspan="4" class="q-pa-md" style="text-align: start">
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
              {{ inquiry.createdTime.split(' ')[0] }}
            </td>
            <td class="q-pa-md" style="text-align: center">
              <q-icon v-if="!inquiry.answered" name="check_box" />
              <q-icon v-else name="check_box_outline_blank" />
            </td>
          </tr>
        </table>
      </section>
      <div class="q-pa-lg flex flex-center" @click="movePage">
        <q-pagination
          v-model="this.pageNum"
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
const perPage = ref(5)
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
  },
  watch: {
    pageNum: function () {
      this.movepage()
    }
  },
  methods: {
    ...mapActions(inquiryStore, ['inquiryDetail', 'inquiryList']),
    goManagerDetail(no) {
      this.inquiryDetail(no)
      this.$router.push('/manager/inquiry/' + no)
    },
    async movepage() {
      await this.getInquiryList()
    },
    getInquiryList() {
      getInquiries(
        this.pageNum - 1,
        this.perPage,
        ({ data }) => {
          this.inquiryList = data.inquiryList.content
          this.totalPages = data.inquiryList.totalPages
        },
        ({ response }) => {
          if (response.status === 401) {
            this.modalContent = '로그인이 만료되었습니다. 로그인해주세요.'
            this.path = '/manager/login'
          } else if (
            response.status === 403 &&
            response.data &&
            response.data.message === 'Not Manager'
          ) {
            this.modalContent = '관리자만 접근 가능합니다.'
            this.path = '/'
          } else {
            this.modalContent = '에러가 발생했습니다. 다시 시도해주세요.'
            this.path = '/manager/main'
          }
          this.showModal = true
          this.willPageMove = true
        }
      )
    }
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
