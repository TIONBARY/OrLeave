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
    <h1>회원관리</h1>
    <!-- Content -->
    <!-- Login -->
    <section class="q-mx-auto" style="width: 850px">
      <div class="q-pa-md">
        <q-markup-table>
          <thead>
            <tr class="table-header">
              <th class="text-center">회원번호</th>
              <th class="text-center">이메일</th>
              <th class="text-center">닉네임</th>
              <th class="text-center">성별</th>
              <th class="text-center">생년월일</th>
              <th class="text-center">매칭 횟수</th>
              <th class="text-center">신고당한 횟수</th>
              <th class="text-center">정지 여부</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="user in userlist" :key="user.no">
              <td class="text-center">{{ user.no }}</td>
              <td class="text-left">{{ user.email }}</td>
              <td class="text-left cursor-pointer">
                {{ user.nickname }}
                <q-popup-edit v-model="user.nickname" auto-save v-slot="scope">
                  <q-input
                    v-model="scope.value"
                    dense
                    autofocus
                    counter
                    hint="저장하려면 enter를 누르세요"
                    @keyup.enter="
                      nicknameChange(user.no, scope.value, user.nickname)
                    "
                  />
                </q-popup-edit>
              </td>
              <td class="text-center">{{ changeGender(user.gender) }}</td>
              <td class="text-center">{{ user.birthDay }}</td>
              <td class="text-left">{{ user.matchcount }}</td>
              <td class="text-left">
                {{ user.reportcount }}
                <q-icon
                  class="cursor-pointer report-icon"
                  name="dangerous"
                  size="2rem"
                  @click="showReportList(user.no, user.email)"
                />
              </td>
              <td class="text-center isBanned">
                {{ checkingUserType(user.userType) }}
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
    <ConfirmModal
      v-model="this.showModal"
      @close="movePage"
      :modalContent="this.modalContent"
    />
    <ReportListModal
      v-model="this.showReportModal"
      :modalContent="this.modalContent"
      :userNo="this.reportUserNo"
      :userEmail="this.reportUserEmail"
    />
  </div>
</template>

<script>
import { ref } from 'vue'
import { getAllUsers, ModifyNickname } from '@/api/manager'
import { mapActions } from 'vuex'
import ConfirmModal from '../ConfirmModal.vue'
import ReportListModal from './ReportListModal.vue'
const userStore = 'userStore'

export default {
  setup() {
    const showModal = ref(false)
    const willPageMove = ref(false)
    const modalContent = ref(null)
    const path = ref(null)
    const pageNum = ref(1)
    const pageSize = ref(10)
    const totalPages = ref(0)
    const maxPages = ref(10)
    const userlist = ref([])
    const showReportModal = ref(false)
    const reportUserNo = ref(0)
    const reportUserEmail = ref(null)
    return {
      showModal,
      modalContent,
      willPageMove,
      path,
      pageNum,
      pageSize,
      totalPages,
      userlist,
      maxPages,
      showReportModal,
      reportUserNo,
      reportUserEmail
    }
  },
  components: {
    ConfirmModal,
    ReportListModal
  },
  created() {
    this.getUserList()
  },
  updated() {
    this.getUserList()
  },
  methods: {
    ...mapActions(userStore, ['logout']),
    movePage() {
      if (this.willPageMove) {
        this.$router.push(this.path)
      }
    },
    getUserList() {
      getAllUsers(
        this.pageNum - 1,
        this.pageSize,
        ({ data }) => {
          this.userlist = data.userList.content
          this.totalPages = data.userList.totalPages
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
    },
    nicknameChange(no, nickname, beforeNickname) {
      if (nickname === beforeNickname) {
        this.showModal = true
        this.modalContent = '현재 사용 중인 닉네임입니다.'
        this.willPageMove = false
        return
      }
      ModifyNickname(
        no,
        nickname,
        ({ data }) => {
          if (data.statusCode === 200) {
            this.modalContent = '닉네임이 수정되었습니다.'
          }
          this.showModal = true
          this.willPageMove = false
        },
        ({ response }) => {
          if (response.status === 401) {
            this.modalContent = '로그인이 만료되었습니다. 로그인해주세요.'
            this.willPageMove = true
            this.path = '/manager/login'
          } else if (
            response.status === 403 &&
            response.data &&
            response.data.message === 'Not Manager'
          ) {
            this.modalContent = '관리자만 접근 가능합니다.'
            this.willPageMove = true
            this.path = '/'
          } else if (response.data.message === 'Duplicate Nickname') {
            this.modalContent = '이미 사용 중인 닉네임입니다.'
            this.willPageMove = false
          } else {
            this.modalContent = '에러가 발생했습니다. 다시 시도해주세요.'
            this.willPageMove = false
          }
          this.showModal = true
        }
      )
    },
    showReportList(no, email) {
      this.showReportModal = true
      this.reportUserNo = no
      this.reportUserEmail = email
    },
    checkingUserType(userType) {
      if (userType === 'Banned') {
        return '■'
      } else {
        return '□'
      }
    },
    changeGender(gender) {
      if (gender === 'M') {
        return '남'
      } else {
        return '여'
      }
    }
  }
}
</script>

<style scoped>
.basic-container {
  width: 400px;
}
.table-header {
  background: #b3a286;
}
.isBanned {
  color: #6b7a16 !important;
  font-size: 1.5rem;
}
.report-icon {
  color: red;
}
</style>
