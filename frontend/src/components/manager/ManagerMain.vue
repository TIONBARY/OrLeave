<template>
  <div>
    <br />
    <br />
    <h1>관리자 메인화면</h1>
    <!-- Content -->
    <!-- Login -->
    <section class="basic-container">
      <section class="q-gutter-md q-mx-auto" style="width: 150px">
      <div>
        <q-btn label="회원관리" size="lg" class="full-width secondary" @click="movePage('managerUsers')"/>
      </div>
      <div>
        <q-btn label="공지사항" size="lg" class="full-width secondary" @click="movePage('managerNotice')"/>
      </div>
      <div>
        <q-btn label="1:1문의" size="lg" class="full-width secondary" @click="movePage('managerInquiry')"/>
      </div>
      <div>
        <q-btn label="로그아웃" size="lg" class="full-width secondary" @click="logout"/>
      </div>
      </section>

    </section>
    <ConfirmModal
      v-model="this.showModal"
      @close="movePage"
      :modalContent="this.modalContent"
    />
  </div>
</template>

<script>
import { ref } from 'vue'
import { mapActions } from 'vuex'
import ConfirmModal from '../ConfirmModal.vue'
const userStore = 'userStore'

export default {
  setup() {
    const showModal = ref(false)
    const willPageMove = ref(false)
    const modalContent = ref(null)
    return {
      showModal,
      modalContent,
      willPageMove
    }
  },
  components: {
    ConfirmModal
  },
  methods: {
    ...mapActions(userStore, [
      'logout'
    ]),
    movePage(name) {
      if (this.willPageMove) {
        this.$router.push({ name })
      }
    }
  }
}
</script>

<style scoped>
.basic-container {
  width: 400px;
}
</style>
