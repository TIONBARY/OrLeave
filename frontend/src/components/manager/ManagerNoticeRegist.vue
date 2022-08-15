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
              공지사항
            </th>
            <tr>
              <td class="q-pa-md" style="width: 20%; text-align: start">
                공지 제목
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
              <td class="q-pa-md" style="width: 20%; text-align: start">
                공지 내용
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
          <q-btn label="공지등록" type="submit" color="primary" />
        </q-form>
        <br />
      </section>
      <br />
    </div>
  </div>
</template>

<script>
import { ref } from 'vue'
import { mapActions } from 'vuex'

const noticeStore = 'noticeStore'

export default {
  setup() {
    const noticeTitle = ref(null)
    const noticeContent = ref(null)
    return {
      noticeTitle,
      noticeContent,

      editor: ref(null)

    }
  },
  methods: {
    ...mapActions(noticeStore, ['noticeRegist']),
    onSubmit() {
      this.$router.push('../notice')
      this.noticeRegist({
        title: this.noticeTitle,
        content: this.editor
      })
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
