<template>
  <q-dialog persistent>
    <q-card class="popup">
      <q-bar class="popup-bar items-right"> </q-bar>
      <section class="q-mx-auto report">
        <div class="row">
          <div class="q-my-sm reportNo">
            <span>신고 번호</span
            ><q-input
              v-model="no"
              readonly
              dense
              bg-color="white"
              class="reportNoInput"
              input-style="width: 40px; text-align: center;"
            />
          </div>
        </div>
        <div class="reportContent">
          <span>신고 내용</span>
          <q-input
            class="q-mt-sm"
            type="textarea"
            readonly
            dense
            bg-color="white"
            v-model="this.content"
            input-style="padding: 5px"
          />
        </div>
      </section>
      <div class="full-width row justify-center q-mb-lg">
        <q-btn class="primary" v-close-popup @click="$emit('close')"
          >확인</q-btn
        >
      </div>
      <ConfirmModal
        v-model="this.showConfirmModal"
        @close="movePage"
        :modalContent="this.confirmModalContent"
        class="modalToFront"
      />
    </q-card>
  </q-dialog>
</template>
<script>
import { ref } from 'vue'
import { getReportDetail } from '@/api/manager'
import ConfirmModal from '../ConfirmModal.vue'

export default {
  setup() {
    const showConfirmModal = ref(false)
    const confirmModalContent = ref(null)
    const willPageMove = ref(false)
    const path = ref(null)
    const no = ref(0)
    const content = ref(null)
    return {
      showConfirmModal,
      confirmModalContent,
      willPageMove,
      path,
      no,
      content
    }
  },
  props: {
    reportNo: Number
  },
  components: {
    ConfirmModal
  },
  updated() {
    this.getReportDetail()
  },
  methods: {
    getReportDetail() {
      this.no = this.reportNo
      getReportDetail(
        this.no,
        ({ data }) => {
          if (data.statusCode === 200) {
            this.content = data.report.content
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
  width: 20%;
  min-width: 350px;
  max-width: 500px;
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
.report {
  width: 70%;
}
.reportNoInput {
  margin-left: 5px;
  padding-left: 5px;
  display: inline-block;
}
.reportContent {
  margin-bottom: 20px;
}
</style>
