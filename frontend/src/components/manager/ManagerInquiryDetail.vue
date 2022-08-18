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
          <th colspan="2" class="q-pa-md">1:1 문의</th>
          <tr>
            <td
              class="q-pa-sm inquiryBox text-weight-bold"
              style="text-align: start"
            >
              {{ this.inquiry.title }}
            </td>
          </tr>
          <tr>
            <td
              class="q-pl-sm text-left"
              style="font-size: 15px; border-bottom: 2px solid #0000001f"
            >
              {{ this.inquiry.createdTime }}
            </td>
          </tr>
          <tr>
            <td
              colspan="2"
              class="q-pa-sm inquiryBox vertical-top"
              style="text-align: start; height: 200px"
            >
              <div v-html="this.inquiry.content" />
            </td>
          </tr>
        </table>
      </section>
      <br />
      <section class="basic-container">
        <div id="ans" class="q-pb-md">&emsp;답변</div>
        <br />
        <table>
          <tr>
            <td
              class="text-left cursor-pointer"
              style="width: 100%; height: 100px"
            >
              <q-input
                v-model="this.inquiry.answer"
                type="textarea"
                dense
                readonly
              />
              <q-popup-edit
                v-model="this.inquiry.answer"
                auto-save
                v-slot="scope"
              >
                <q-input
                  v-model="scope.value"
                  type="textarea"
                  dense
                  autofocus
                  hint="저장하려면 esc를 누르세요"
                  @keyup.esc="modifyAnswer(this.inquiry.no, scope.value)"
                />
              </q-popup-edit>
            </td>
          </tr>
        </table>
      </section>
      <br />
      <br />
      <q-btn class="secondary" label="목록으로" to="/manager/inquiries" />
    </div>
    <ConfirmModal
      v-model="this.showModal"
      @close="movePage"
      :modalContent="this.modalContent"
    />
  </div>
</template>

<script>
import { ref } from 'vue'
import { answerInquiry, inquiryDetail } from '@/api/manager'
import ConfirmModal from '../ConfirmModal.vue'

export default {
  setup() {
    const showModal = ref(false)
    const willPageMove = ref(false)
    const modalContent = ref(null)
    const path = ref(null)
    const inquiry = ref({
      no: 0,
      title: null,
      content: null,
      createdTime: null
    })

    return {
      showModal,
      modalContent,
      willPageMove,
      path,
      inquiry
    }
  },
  components: {
    ConfirmModal
  },
  created() {
    inquiryDetail(
      this.$route.params.inquiryNo,
      ({ data }) => {
        if (data.statusCode === 200) {
          this.inquiry = data.inquiry
          this.inquiry.createdTime = this.inquiry.createdTime.substring(0, 10)
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
    modifyAnswer(no, newAnswer) {
      answerInquiry(
        {
          no: no,
          title: this.inquiry.title,
          content: this.inquiry.content,
          answer: newAnswer,
          createdTime: this.inquiry.createdTime
        },
        ({ data }) => {
          if (data.statusCode === 200) {
            this.modalContent = '답변이 등록되었습니다.'
          }
          this.showModal = true
          this.willPageMove = false
          inquiryDetail(
            this.$route.params.inquiryNo,
            ({ data }) => {
              if (data.statusCode === 200) {
                this.inquiry = data.inquiry
                this.inquiry.createdTime = this.inquiry.createdTime.substring(
                  0,
                  10
                )
              }
            },
            ({ response }) => {
              if (response.status === 401) {
                this.confirmModalContent =
                  '로그인이 만료되었습니다. 로그인해주세요.'
                this.willPageMove = true
                this.path = '/user/login'
              } else if (response.status === 404) {
                this.confirmModalContent = '해당 문의가 존재하지 않습니다.'
                this.willPageMove = true
                this.path = '/inquiry/list'
              } else {
                this.confirmModalContent =
                  '에러가 발생했습니다. 다시 시도해주세요.'
                this.willPageMove = false
              }
              this.showConfirmModal = true
            }
          )
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
          } else {
            this.modalContent = '에러가 발생했습니다. 다시 시도해주세요.'
            this.willPageMove = false
          }
          this.showModal = true
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
.basic-container {
  width: 100%;
  max-width: 600px;
  min-width: 300px;
}

table {
  width: 100%;
}

th {
  border-bottom: 2px solid #000000;
  text-align: start;
}

#ans {
  text-align: start;
  font-weight: bold;
  border-bottom: 2px solid #000000;
}
</style>
