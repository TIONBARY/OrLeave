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
        <q-form @submit="onSubmit" class="q-gutter-md">
          <table>
            <th colspan="2" class="q-pa-md" style="text-align: start">
              문의하기
            </th>
            <tr>
              <td class="q-pa-md" style="width: 20%; text-align: start">
                문의 제목
              </td>
              <td>
                <q-input
                  outlined
                  v-model="inquiry.title"
                  style="background-color: white"
                  dense
                />
              </td>
            </tr>
            <tr>
              <td class="q-pa-md" style="width: 20%; text-align: start">
                문의 내용
              </td>
              <td>
                <q-editor
                  v-model="inquiry.content"
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
          <q-btn label="문의 접수" type="submit" color="primary" />
        </q-form>
        <br />
      </section>
      <br />
    </div>
  </div>
</template>

<script>
import { ref } from 'vue'
import { mapActions, mapState } from 'vuex'

const inquiryStore = 'inquiryStore'

export default {
  setup() {
    const inquiryTest = ref({ no: null, title: null, content: null })
    return {
      inquiryTest
    }
  },
  created() {
    console.log(this.$route.params.inquiryNo)
    this.inquiryDetail(this.$route.params.inquiryNo)
    this.inquiryTest.title = this.inquiry.title
    this.inquiryTest.content = this.inquiry.content
    this.inquiryTest.no = this.$route.params.inquiryNo
  },
  computed: {
    ...mapState(inquiryStore, ['inquiry'])
  },
  methods: {
    ...mapActions(inquiryStore, ['inquiryModify', 'inquiryDetail']),
    onSubmit() {
      this.$router.go(-1)
      return this.inquiryModify(
        this.inquiry
      )
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
}

th {
  border-bottom: 2px solid #000000;
}

tr {
  border-bottom: 1px solid #979797;
}
</style>
