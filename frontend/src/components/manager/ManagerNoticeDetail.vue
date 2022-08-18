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
          <th colspan="2" class="q-pa-md" style="text-align: start">
            공지사항
          </th>
          <tr>
            <td
              class="q-pa-sm inquiryBox text-weight-bold"
              style="text-align: start"
            >
              {{ this.notice.title }}
            </td>
          </tr>
          <tr>
            <td class="q-pl-sm text-left" style="font-size: 15px">
              {{ this.notice.createdTime.split(' ')[0] }}
            </td>
          </tr>
          <q-separator size="2px" />
          <tr>
            <td
              colspan="2"
              class="q-pa-sm inquiryBox vertical-top"
              style="text-align: start; height: 200px"
            >
              <div v-html="this.notice.content" />
            </td>
          </tr>
          <tr style="margin: 10px 0">
            <td colspan="2" class="text-right">
              <q-btn
                @click="modifynotice(this.notice.no)"
                class="primary"
                style="margin-right: 10px"
                >수정</q-btn
              >
              <q-btn @click="asknotice()" class="negative">삭제</q-btn>
            </td>
          </tr>
        </table>
      </section>
      <br />

      <q-btn class="secondary" label="목록으로" to="/manager/notice/list" />
      <ConfirmModal
        v-model="this.show"
        @close="movePage"
        :modalContent="this.content"
      />
      <ChoiceModal
        v-model="this.Cshow"
        @confirm="deletenotice(this.notice.no)"
        :modalContent="this.content"
      />
    </div>
  </div>
</template>

<script>
import { ref } from 'vue'
import { mapState, mapActions } from 'vuex'
import ConfirmModal from '../ConfirmModal.vue'
import ChoiceModal from '../ChoiceModal.vue'
const noticeStore = 'noticeStore'

export default {
  setup() {
    const Cshow = ref(false)
    const show = ref(false)
    const content = ref(null)
    return {
      Cshow,
      show,
      content
    }
  },
  components: {
    ConfirmModal,
    ChoiceModal
  },
  computed: {
    ...mapState(noticeStore, ['notice', 'showModal', 'modalContent'])
  },
  methods: {
    ...mapActions(noticeStore, ['noticeDetail', 'noticeDelete', 'noticeList']),

    modifynotice(no) {
      this.$router.push('../notice/modify/' + no)
    },
    asknotice() {
      this.Cshow = true
      this.content = '정말로 삭제하시겠습니까?'
    },
    async deletenotice(no) {
      await this.noticeDelete(no)
      this.show = this.showModal
      this.content = this.modalContent
    },
    movePage() {
      this.$router.push('../notice/list')
    }
  },
  created() {
    this.noticeDetail(this.notice.no)
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
</style>
