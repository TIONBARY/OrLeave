<template>
  <q-dialog persistent>
    <q-card class="popup">
      <q-bar class="popup-bar items-right"
        ><q-space /><q-btn
          class="primary text-right"
          v-close-popup
          style="width: 6px"
          >X</q-btn
        >
      </q-bar>
      <h1 class="text-center">신고 관리</h1>
      <section class="q-mx-auto">
        <div class="q-pa-md">
          <q-input
            label="사용자 이메일"
            outlined
            bg-color="white"
            type="email"
            v-model="email"
            dense
            readonly
            class="userEmail"
          >
            <template v-slot:append>
              <q-btn
                class="negative"
                @click="checkBan"
                label="계정 정지"
                size="10px"
                dense
              />
            </template>
          </q-input>
          <q-markup-table>
            <thead>
              <tr class="table-header">
                <th class="text-center">No</th>
                <th class="text-center">신고한 유저</th>
                <th class="text-center">분류</th>
                <th class="text-center">신고일자</th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="report in reportlist" :key="report.no">
                <td class="text-center">{{ report.no }}</td>
                <td class="text-left">{{ report.user_email }}</td>
                <td class="text-center">
                  {{ this.categoryList[report.category] }}
                </td>
                <td class="text-center">
                  {{ changeToDate(report.reportTime) }}
                </td>
              </tr>
            </tbody>
          </q-markup-table>
          <div class="q-pa-lg flex flex-center">
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
      </section>
    </q-card>
    <ChoiceModal
      v-model="this.showChoiceModal"
      @confirm="this.banUser"
      :modalContent="this.choiceModalContent"
    />
    <ConfirmModal
      v-model="this.showConfirmModal"
      @close="movePage"
      :modalContent="this.confirmModalContent"
      class="modalToFront"
    />
  </q-dialog>
</template>
<script>
import { ref } from 'vue'
import { getUserReportList, BanUser } from '@/api/manager'
import ChoiceModal from '../ChoiceModal.vue'
import ConfirmModal from '../ConfirmModal.vue'

export default {
  setup() {
    const showChoiceModal = ref(false)
    const showConfirmModal = ref(false)
    const choiceModalContent = ref(null)
    const confirmModalContent = ref(null)
    const willPageMove = ref(false)
    const path = ref(null)
    const pageNum = ref(1)
    const pageSize = ref(10)
    const totalPages = ref(0)
    const maxPages = ref(10)
    const reportlist = ref([])
    const showDetailModal = ref(false)
    const reportNo = ref(0)
    const email = ref(null)
    const categoryList = ref([
      '',
      '욕설 및 폭언',
      '부적절한 닉네임',
      '부적절한 언행',
      '성희롱 및 성추행',
      '혐오발언',
      '기타'
    ])
    return {
      showChoiceModal,
      showConfirmModal,
      choiceModalContent,
      confirmModalContent,
      willPageMove,
      path,
      pageNum,
      pageSize,
      totalPages,
      reportlist,
      maxPages,
      showDetailModal,
      reportNo,
      categoryList,
      email
    }
  },
  props: {
    modalContent: String,
    userNo: Number,
    userEmail: String
  },
  components: {
    ChoiceModal,
    ConfirmModal
  },
  created() {
    this.getReportList()
  },
  updated() {
    this.getReportList()
  },
  methods: {
    getReportList() {
      this.email = this.userEmail
      getUserReportList(
        this.pageNum - 1,
        this.pageSize,
        this.userNo,
        ({ data }) => {
          if (data.statusCode === 200) {
            this.reportlist = data.userReportList.content
            this.totalPages = data.userReportList.totalPages
          }
        },
        ({ response }) => {
          if (response.status === 401) {
            this.confirmModalContent =
              '로그인이 만료되었습니다. 로그인해주세요.'
            this.path = '/manager/login'
          } else if (
            response.status === 403 &&
            response.data &&
            response.data.message === 'Not Manager'
          ) {
            this.confirmModalContent = '관리자만 접근 가능합니다.'
            this.path = '/'
          } else {
            this.confirmModalContent = '에러가 발생했습니다. 다시 시도해주세요.'
            this.path = '/manager/users'
          }
          this.showConfirmModal = true
          this.willPageMove = true
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
    checkBan() {
      this.showChoiceModal = true
      this.choiceModalContent = '정말로 정지하시겠습니까?'
    },
    banUser() {
      this.showChoiceModal = false
      BanUser(
        this.userNo,
        ({ data }) => {
          if (data.statusCode === 200) {
            this.showConfirmModal = true
            this.confirmModalContent = '해당 사용자를 정지하였습니다.'
          }
        },
        ({ response }) => {
          if (response.status === 401) {
            this.confirmModalContent =
              '로그인이 만료되었습니다. 로그인해주세요.'
            this.path = '/manager/login'
          } else if (
            response.status === 403 &&
            response.data &&
            response.data.message === 'Not Manager'
          ) {
            this.confirmModalContent = '관리자만 접근 가능합니다.'
            this.path = '/'
          } else {
            this.confirmModalContent = '에러가 발생했습니다. 다시 시도해주세요.'
            this.path = '/manager/users'
          }
          this.showConfirmModal = true
          this.willPageMove = true
        }
      )
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
.popup {
  background: #f3f1eb;
  width: 60%;
  min-width: 350px;
  max-width: 700px;
}
.popup-bar {
  background: #b3a286;
}
.popup-text {
  font-size: 150%;
  text-align: center;
}
.table-header {
  background: #b3a286;
}
.userEmail {
  width: 50%;
  margin: auto;
  margin-bottom: 10px;
}
.modalToFront {
  z-index: 10;
}
</style>
