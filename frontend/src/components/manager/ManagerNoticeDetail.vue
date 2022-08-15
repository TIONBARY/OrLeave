<template>
  <div>
    <q-img
      class="q-ma-lg"
      :src="require('../../assets/logo_l.png')"
      alt="image"
      style="width: 20%; height: 20%"
    />
    <hr />
    <div class="q-pa-xl">
      <section class="basic-container">
        <table>
          <th colspan="2" class="q-pa-md" style="text-align: start">
            공지사항
          </th>
          <tr>
            <td class="q-pa-md" style="width: 10%">제목</td>
            <td
              class="q-pa-md"
              style="text-align: start; background-color: white"
            >
              {{ this.notice.title }}
            </td>
          </tr>
          <tr>
            <td
              colspan="2"
              class="q-pa-md"
              style="text-align: start; background-color: white"
            >
              {{ this.notice.content }}
            </td>
          </tr>
        </table>
          <q-btn @click="modifynotice(this.notice.no)">수정</q-btn>
          <q-btn @click="asknotice()">삭제</q-btn>
      </section>
      <br />
      <div>
        <router-link to="../notice/list">목록으로</router-link>
      </div>
    <ConfirmModal v-model="this.show" @close="movePage" :modalContent="this.content" />
    <ChoiceModal v-model="this.Cshow" @confirm="deletenotice(this.notice.no)" :modalContent="this.content" />
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
  width: 50%;
}

table {
  width: 100%;
}

th {
  border-bottom: 2px solid #000000;
}
</style>
