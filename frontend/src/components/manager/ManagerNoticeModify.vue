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
                  v-model="noticemod.title"
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
                  v-model="noticemod.content"
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
          <q-btn label="공지 수정" type="submit" color="primary" />
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

const noticeStore = 'noticeStore'

export default {
  setup() {
    const noticemod = ref({ no: null, title: null, content: null })
    return {
      noticemod
    }
  },
  created() {
    console.log(this.notice)
    this.noticemod.title = this.notice.title
    this.noticemod.content = this.notice.content
    this.noticemod.no = this.notice.no
  },
  computed: {
    ...mapState(noticeStore, ['notice'])
  },
  methods: {
    ...mapActions(noticeStore, ['noticeModify']),
    onSubmit() {
      this.$router.push('../list')
      return this.noticeModify(
        this.noticemod
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
