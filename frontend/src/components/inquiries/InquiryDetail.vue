<template>
  <div>
    <q-img
      class="q-ma-lg"
      :src="require('../../assets/logo_l.png')"
      alt="image"
      style="width: 100%; max-width: 300px;"
    />
    <hr />
    <div>
      <section class="basic-container">
        <table class="inquiryTable">
          <th colspan="2" class="q-pa-md" style="text-align: start">
            1:1 문의
          </th>
          <tr>
            <td class="q-pa-md" style="width: 80px">제목</td>
            <td
              class="q-pa-md inquiryBox"
              style="text-align: start; background-color: white"
            >
              {{ this.inquiry.title }}
            </td>
          </tr>
          <tr class="q-my-md">
            <td
              colspan="2"
              class="q-pa-md inquiryBox vertical-top"
              style="text-align: start; background-color: white; height: 200px"
            >
              {{ this.inquiry.content }}
            </td>
          </tr>
          <tr style="margin: 10px 0">
            <td colspan="2" class="text-right">
              <q-btn @click="modifyInquiry" class="primary" style="margin-right: 10px">수정</q-btn>
              <q-btn @click="checkDelete" class="negative">삭제</q-btn>
            </td>
          </tr>
          <tr>
            <td colspan="2" style="text-align: start; width: 80px">답변</td>
          </tr>
          <tr>
            <td
              colspan="2"
              class="q-pa-md inquiryBox vertical-top"
              style="text-align: start; background-color: white; height: 100px;"
            >
              {{ this.inquiry.answer }}
            </td>
          </tr>
        </table>
        <br />
        <div class="full-width row justify-evenly"></div>
      </section>
      <br />
      <div class="q-mb-xl">
        <q-btn @click="moveToList" class="primary">목록으로</q-btn>
      </div>
    </div>
    <ConfirmModal
      v-model="this.showConfirmModal"
      @close="movePage"
      :modalContent="this.confirmModalContent"
    />
    <ChoiceModal
      v-model="this.showChoiceModal"
      @confirm="deleteInquiry"
      :modalContent="this.choiceModalContent"
    />
  </div>
</template>

<script>
import { ref } from 'vue'
import { inquiryDetail, inquiryDelete } from '@/api/inquiry'
import ConfirmModal from '../ConfirmModal.vue'
import ChoiceModal from '../ChoiceModal.vue'

export default {
  setup() {
    const showConfirmModal = ref(false)
    const showChoiceModal = ref(false)
    const confirmModalContent = ref(null)
    const choiceModalContent = ref(null)
    const willPageMove = ref(false)
    const path = ref(null)
    const inquiry = ref({
      no: 0,
      title: null,
      content: null,
      createdTime: null
    })
    const inquiryNo = ref(0)
    return {
      showConfirmModal,
      showChoiceModal,
      confirmModalContent,
      choiceModalContent,
      willPageMove,
      path,
      inquiry,
      inquiryNo
    }
  },
  components: {
    ConfirmModal,
    ChoiceModal
  },
  created() {
    this.inquiryNo = this.$route.params.inquiryNo
    inquiryDetail(
      this.$route.params.inquiryNo,
      ({ data }) => {
        if (data.statusCode === 200) {
          this.inquiry = data.inquiry
        }
      },
      ({ response }) => {
        if (response.status === 401) {
          this.confirmModalContent = '로그인이 만료되었습니다. 로그인해주세요.'
          this.willPageMove = true
          this.path = '/user/login'
        } else if (response.status === 404) {
          this.confirmModalContent = '해당 문의가 존재하지 않습니다.'
          this.willPageMove = true
          this.path = '/inquiry/list'
        } else {
          this.confirmModalContent = '에러가 발생했습니다. 다시 시도해주세요.'
          this.willPageMove = false
        }
        this.showConfirmModal = true
      }
    )
  },
  methods: {
    checkDelete() {
      this.choiceModalContent = '정말로 삭제하시겠습니까?'
      this.showChoiceModal = true
    },
    deleteInquiry() {
      inquiryDelete(
        this.$route.params.inquiryNo,
        ({ data }) => {
          if (data.statusCode === 200) {
            this.confirmModalContent = '삭제가 완료되었습니다.'
            this.willPageMove = true
            this.path = '/inquiry/list'
            this.showConfirmModal = true
          }
        },
        ({ response }) => {
          if (response.status === 401) {
            this.confirmModalContent =
              '로그인이 만료되었습니다. 로그인해주세요.'
            this.path = '/user/login'
            this.willPageMove = true
          } else if (response.status === 404) {
            this.confirmModalContent = '해당 문의가 존재하지 않습니다.'
            this.path = '/inquiry/list'
            this.willPageMove = true
          } else {
            this.confirmModalContent = '에러가 발생했습니다. 다시 시도해주세요.'
            this.willPageMove = false
          }
          this.showConfirmModal = true
        }
      )
    },
    modifyInquiry(no) {
      this.$router.push('/inquiry/modify/' + this.inquiryNo)
    },
    moveToList() {
      this.$router.push('/inquiry/list')
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
  border-spacing: 10px;
}

th {
  border-bottom: 2px solid #000000;
}

.inquiryBox {
  border-radius: 5px;
}
</style>
