<template>
  <div>
    <q-img
      class="q-ma-lg"
      :src="require('../../assets/logo_l.png')"
      alt="image"
      style="width: 100%; max-width: 300px"
      @click="this.$router.push('/')"
    />
    <hr />
    <div>
      <section class="basic-container">
        <table>
          <th colspan="3" class="q-pa-md" style="text-align: start">내 문의</th>
          <template v-if="inquiryList.length === 0">
            <tr>
              <td style="height: 150px">작성한 문의가 없습니다.</td>
            </tr>
          </template>
          <template v-else>
            <tr :key="inquiry.no" v-for="inquiry in inquiryList">
              <td class="q-pa-md" style="text-align: start">
                <router-link :to="`/inquiry/${inquiry.no}`">
                  {{ inquiry.title }}
                </router-link>
              </td>
              <td class="q-pa-md" style="text-align: end">
                {{ changeToDate(inquiry.createdTime) }}
              </td>
              <td class="q-pa-md" style="text-align: end">
                <q-icon v-if="inquiry.answered" name="check_box" />
                <q-icon v-else name="check_box_outline_blank" />
              </td>
            </tr>
          </template>
        </table>
        <br />
        <div class="parent">
          <div class="child"></div>
          <div cclass="child center row justify-center">
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
          <div class="child right row justify-end">
            <q-btn @click="moveRegist()" class="primary">문의하기</q-btn>
          </div>
        </div>
      </section>
    </div>
    <ConfirmModal
      v-model="this.showConfirmModal"
      @close="movePage"
      :modalContent="this.confirmModalContent"
      class="modalToFront"
    />
  </div>
</template>

<script>
import { ref } from 'vue'
import { inquiryList } from '@/api/inquiry'
import ConfirmModal from '../ConfirmModal.vue'

export default {
  setup() {
    const showConfirmModal = ref(false)
    const confirmModalContent = ref(null)
    const willPageMove = ref(false)
    const path = ref(null)
    const pageNum = ref(1)
    const pageSize = ref(10)
    const totalPages = ref(0)
    const maxPages = ref(10)
    const inquiryList = ref([])
    const inquiryNo = ref(0)
    return {
      showConfirmModal,
      confirmModalContent,
      willPageMove,
      path,
      pageNum,
      pageSize,
      totalPages,
      maxPages,
      inquiryList,
      inquiryNo
    }
  },
  components: {
    ConfirmModal
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
    movepage() {
      this.getInquiryList()
    },
    getInquiryList() {
      inquiryList(
        this.pageNum - 1,
        ({ data }) => {
          if (data.statusCode === 200) {
            this.inquiryList = data.inquiryList.content
            this.totalPages = data.inquiryList.totalPages
          }
        },
        ({ response }) => {
          if (response.status === 401) {
            this.confirmModalContent =
              '로그인이 만료되었습니다. 로그인해주세요.'
            this.willPageMove = true
            this.path = '/user/login'
          } else {
            this.confirmModalContent = '에러가 발생했습니다. 다시 시도해주세요.'
            this.willPageMove = false
          }
          this.showConfirmModal = true
        }
      )
    },
    changeToDate(dateTime) {
      const date = new Date(dateTime)
      const year = date.getFullYear()
      const month =
        date.getMonth() + 1 < 10
          ? '0' + (date.getMonth() + 1)
          : date.getMonth() + 1
      const day = date.getDate() < 10 ? '0' + date.getDate() : date.getDate()
      return `${year}-${month}-${day}`
    },
    moveRegist() {
      this.$router.push('/inquiry/regist')
    },
    movePage() {
      if (this.willPageMove) {
        this.$router.push(this.path)
      }
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

a {
  color: black;
}
.parent {
  display: flex;
}

.parent .child {
  flex: 1;
}
</style>
