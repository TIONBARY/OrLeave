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
        <q-form @submit="onSubmit" class="q-gutter-md">
          <table>
            <th colspan="2" class="q-pa-md" style="text-align: start">
              문의 수정
            </th>
            <tr>
              <td class="q-pa-md" style="width: 80px; text-align: start">
                제목
              </td>
              <td>
                <q-input
                  outlined
                  v-model="this.inquiry.title"
                  style="background-color: white"
                  dense
                />
              </td>
            </tr>
            <tr>
              <td class="q-pa-md" style="width: 20%; text-align: start">
                내용
              </td>
              <td>
                <q-editor
                  v-model="this.inquiry.content"
                  ref="editorRef"
                  style="text-align: left;"
                  toolbar-text-color="white"
                  toolbar-toggle-color="yellow-8"
                  toolbar-bg="primary"
                  :toolbar="[
                    [
                      {
                        label: $q.lang.editor.align,
                        icon: $q.iconSet.editor.align,
                        fixedLabel: true,
                        list: 'only-icons',
                        options: ['left', 'center', 'right', 'justify']
                      }
                    ],
                    ['bold', 'italic', 'underline']
                  ]"
                />
              </td>
            </tr>
          </table>
          <q-btn label="수정" type="submit" color="primary" />
          <q-btn @click="moveToDetail" class="secondary">취소</q-btn>
        </q-form>
        <br />
      </section>
      <br />
    </div>
    <ConfirmModal
      v-model="this.showConfirmModal"
      @close="movePage"
      :modalContent="this.confirmModalContent"
    />
  </div>
</template>

<script>
import { ref } from 'vue'
import { inquiryDetail, inquiryModify } from '@/api/inquiry'
import ConfirmModal from '../ConfirmModal.vue'

export default {
  setup() {
    const showConfirmModal = ref(false)
    const confirmModalContent = ref(null)
    const willPageMove = ref(false)
    const path = ref(null)
    const inquiry = ref({
      title: '',
      content: ''
    })
    const inquiryNo = ref(0)
    return {
      showConfirmModal,
      confirmModalContent,
      willPageMove,
      path,
      inquiry,
      inquiryNo
    }
  },
  components: {
    ConfirmModal
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
    onSubmit() {
      inquiryModify(
        this.inquiryNo,
        this.inquiry,
        ({ data }) => {
          if (data.statusCode === 200) {
            this.confirmModalContent = '문의 수정이 완료되었습니다.'
            this.showConfirmModal = true
            this.willPageMove = true
            this.path = `/inquiry/${this.inquiryNo}`
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
            this.confirmModalContent = '에러가 발생했습니다. 다시 시도해주세요.'
            this.willPageMove = false
          }
          this.showConfirmModal = true
        }
      )
    },
    moveToDetail() {
      this.$router.push(`/inquiry/${this.inquiryNo}`)
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
}

tr {
  border-bottom: 1px solid #979797;
}
</style>
