<template>
  <div>
    <q-img
      class="q-ma-lg"
      :src="require('../../assets/logo_l.png')"
      alt="image"
      style="width: 100%; max-width: 300px"
    />
    <hr />
    <div>
      <section class="basic-container">
        <q-form @submit="onSubmit">
          <table class="inquiryTable">
            <th colspan="2" class="q-pa-md" style="text-align: start">
              문의하기
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
              <td
                class="q-pa-md"
                style="width: 80px; text-align: left; vertical-align: text-top"
              >
                내용
              </td>
              <td>
                <q-editor
                  v-model="this.inquiry.content"
                  ref="editorRef"
                  style="text-align: left"
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
          <q-btn
            class="q-mt-lg"
            label="문의 접수"
            type="submit"
            color="primary"
          />
        </q-form>
        <br />
      </section>
      <br />
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
import { inquiryRegist } from '@/api/inquiry'
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
    return {
      showConfirmModal,
      confirmModalContent,
      willPageMove,
      path,
      inquiry
    }
  },
  components: {
    ConfirmModal
  },
  methods: {
    onSubmit() {
      inquiryRegist(
        this.inquiry,
        ({ data }) => {
          if (data.statusCode === 200) {
            this.confirmModalContent = '문의 작성이 완료되었습니다.'
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
          } else {
            this.confirmModalContent = '에러가 발생했습니다. 다시 시도해주세요.'
            this.willPageMove = false
          }
          this.showConfirmModal = true
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
}

tr {
  border-bottom: 1px solid #979797;
}
</style>
