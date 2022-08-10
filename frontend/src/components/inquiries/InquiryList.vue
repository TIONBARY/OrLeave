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
          <th colspan="2" class="q-pa-md" style="text-align: start">내 문의</th>
          <tr :key="inquiry" v-for="inquiry in inquiries">
            <td class="q-pa-md" style="text-align: start">
              {{ inquiry.no }}
            </td>
            <td class="q-pa-md" style="text-align: start">
              <router-link to="/inquiry/detail/1">{{
                inquiry.title
              }}</router-link>
            </td>
            <td class="q-pa-md" style="text-align: end">
              {{ inquiry.created_time }}
            </td>
          </tr>
        </table>
      </section>
      <br />
      <q-btn @click="moveRegist()">문의하기</q-btn>
      <div class="q-pa-lg flex flex-center">
        <q-pagination v-model="currentPage" :max="5" input />
      </div>
    </div>
  </div>
</template>

<script>
import { ref } from 'vue'
import { mapState, mapActions } from 'vuex'

const userStore = 'userStore'
const inquiryStore = 'inquiryStore'
const perPage = ref(10)
const currentPage = ref(1)

export default {
  setup() {
    return {
      perPage,
      currentPage
    }
  },
  computed: {
    ...mapState(userStore, ['userInfo']),
    ...mapState(inquiryStore, ['inquiries']),
    pagedInquiries() {
      const items = this.inquiries
      return items.slice(
        (this.currentPage - 1) * this.perPage,
        this.currentPage * this.perPage
      )
    },
    rows() {
      return this.inquiries.length
    }
  },
  created() {
    console.log('InquiryList Comp.')
    this.inquiryList(currentPage, perPage)
  },
  methods: {
    ...mapActions(inquiryStore, ['getinquiries']),
    moveRegist() {
      this.$router.push('/inquiry/regist')
    }
  }
}
</script>

<style scoped>
.basic-container {
  width: 50%;
}

table {
  width: 100%;
  border-bottom: 2px solid #000000;
  border-collapse: collapse;
}

th {
  border-bottom: 2px solid #000000;
}

tr {
  border-bottom: 1px solid #979797;
}
</style>
