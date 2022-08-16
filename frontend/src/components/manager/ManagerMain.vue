<template>
  <div>
    <br />
    <br />
    <q-img
      class="q-ma-lg"
      :src="require('../../assets/logo_manager.png')"
      alt="image"
      style="width: 100%; max-width: 300px"
      @click="this.$router.push('/manager/main')"
    />
    <!-- Content -->
    <!-- Login -->
    <section class="basic-container">
      <section class="q-gutter-md q-mx-auto" style="width: 150px">
        <div>
          <q-btn
            label="회원관리"
            size="lg"
            class="full-width secondary"
            @click="movePage('managerUsers')"
          />
        </div>
        <div>
          <q-btn
            label="공지사항"
            size="lg"
            class="full-width secondary"
            @click="movePage('managerNotice')"
          />
        </div>
        <div>
          <q-btn
            label="1:1문의"
            size="lg"
            class="full-width secondary"
            @click="movePage('managerInquiries')"
          />
        </div>
        <div>
          <q-btn
            label="로그아웃"
            size="lg"
            class="full-width secondary"
            @click="logout"
          />
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
    ...mapActions(userStore, ['logout']),
    movePage(name) {
      this.$router.push({ name })
    }
  }
}
</script>

<style scoped>
.basic-container {
  width: 400px;
}
</style>
