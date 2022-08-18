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
        <q-form @submit="onSubmit" class="q-gutter-md">
          <table>
            <th colspan="2" class="q-pa-md" style="text-align: start">
              공지사항
            </th>
            <tr>
              <td class="q-pa-md" style="width: 20%; text-align: center">
                제목
              </td>
              <td>
                <q-input
                  outlined
                  v-model="noticeTitle"
                  style="background-color: white"
                  dense
                />
              </td>
            </tr>
            <tr>
              <td class="q-pa-md" style="width: 20%; text-align: center">
                내용
              </td>
              <td>
                <q-editor
                  v-model="editor"
                  ref="editorRef"
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
          <br />
          <q-btn label="등록" type="submit" color="primary" />
          <q-btn label="취소" to="/manager/notice/list" class="negative" />
        </q-form>
        <br />
      </section>
      <br />
      <ConfirmModal
        v-model="this.show"
        @close="movePage"
        :modalContent="this.content"
      />
    </div>
  </div>
</template>

<script>
import { ref } from 'vue'
import { mapState, mapActions } from 'vuex'
import ConfirmModal from '../ConfirmModal.vue'

const noticeStore = 'noticeStore'

export default {
  setup() {
    const noticeTitle = ref(null)
    const noticeContent = ref(null)
    const show = ref(false)
    const content = ref(null)
    return {
      noticeTitle,
      noticeContent,
      show,
      content,

      editor: ref(null)
    }
  },
  components: {
    ConfirmModal
  },
  computed: {
    ...mapState(noticeStore, ['showModal', 'modalContent'])
  },
  methods: {
    ...mapActions(noticeStore, ['noticeRegist']),
    async onSubmit() {
      await this.noticeRegist({
        title: this.noticeTitle,
        content: this.editor
      })
      this.show = this.showModal
      this.content = this.modalContent
    },
    movePage() {
      this.$router.push('../notice/list')
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
