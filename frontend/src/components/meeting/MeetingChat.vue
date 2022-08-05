<template>
  <div>
    <q-btn icon="arrow_back_ios" class="chat-btn">
      <q-btn
        icon="arrow_forward_ios"
        v-show="showChat === true"
        class="chat-hide-btn"
        transition-show="slide-left"
        transition-hide="slide-right"
      />
      <q-menu
        v-model="showChat"
        class="chat-bar"
        anchor="center right"
        self="center right"
        persistent
      >
        <q-scroll-area class="chat-log">
          <q-chat-message
            v-for="chat in chat_log"
            :key="chat"
            :text="[chat.text]"
            :sent="chat.sent"
            :stamp="chat.time"
          />
        </q-scroll-area>
        <q-input
          outlined
          autogrow
          v-model="chatMsg"
          @keypress.enter="sendChat(chatMsg)"
        >
          <template v-slot:append>
            <q-btn label="전송" size="12px" @click="sendChat(chatMsg)" />
          </template>
        </q-input>
      </q-menu>
    </q-btn>

    <!-- 임시 나가기 버튼 -->
    <q-btn label="팝업을 띄워보자" @click="popupMatching = true" />
    <!-- 임시 나가기 팝업 -->
    <q-dialog v-model="popupMatching" persistent>
      <q-card class="popup">
        <q-bar class="popup-bar">
          <q-space />
          <q-btn color="red" dense icon="close" v-close-popup />
        </q-bar>
        <q-card-section class="popup-text">
          정말 떠나시겠습니까?
        </q-card-section>
        <q-card-section class="q-gutter-sm text-right">
          <q-btn class="primary" label="확인" />
          <q-btn
            class="negative"
            label="신고"
            @click="popupReport = true"
            v-close-popup
          />
          <q-btn class="secondary" label="취소" v-close-popup />
        </q-card-section>
      </q-card>
    </q-dialog>

    <!-- 임시 신고 팝업 -->
    <q-dialog v-model="popupReport" persistent>
      <q-card class="report-popup" style="height: 500px">
        <q-bar class="popup-bar">
          <q-space />
          <q-btn color="red" dense icon="close" v-close-popup />
        </q-bar>
        <q-card-section class="popup-text">
          <table style="width: 100%">
            <tr>
              <td style="width: 20%">닉네임</td>
              <td style="width: 40%">
                <q-input outlined bg-color="white" v-model="opponent" dense />
              </td>
            </tr>
            <tr>
              <td>신고분류</td>
              <td>
                <q-option-group
                  v-model="reportSelected"
                  :options="reportOptions"
                  type="checkbox"
                  color="primary"
                />
              </td>
            </tr>
            <tr>
              <td>신고내용</td>
              <td>
                <q-input
                  type="text"
                  autogrow
                  outlined
                  bg-color="white"
                  v-model="reportMsg"
                  dense
                />
              </td>
            </tr>
          </table>
        </q-card-section>
        <q-card-section class="q-gutter-sm text-right">
          <q-btn class="negative" label="신고하기" />
          <q-btn class="secondary" label="취소" />
        </q-card-section>
      </q-card>
    </q-dialog>
  </div>
</template>

<script>
import { ref } from 'vue'
export default {
  setup() {
    const showChat = ref(false)
    const popupMatching = ref(false)
    const popupReport = ref(false)
    const reportSelected = ref([])
    const reportMsg = ref('')
    return {
      showChat,
      chat_log: [
        {
          text: 'How are you',
          sent: false,
          time: '10 minutes ago'
        },
        {
          text: "I'm fine, and you?",
          sent: true,
          time: '9 minutes ago'
        },
        {
          text: 'Me too~',
          sent: false,
          time: '9 minutes ago'
        },
        {
          text: 'Me too~',
          sent: true,
          time: '7 minutes ago'
        },
        {
          text: 'hello~',
          sent: false,
          time: '3 minutes ago'
        },
        {
          text: '안농안농',
          sent: true,
          time: '1 minutes ago'
        }
      ],
      reportOptions: [
        { label: '폭언 및 욕설', value: 0 },
        { label: '부적절한 닉네임', value: 1 },
        { label: '부적절한 언행', value: 2 },
        { label: '성희롱 및 성추행', value: 3 },
        { label: '혐오발언', value: 4 },
        { label: '기타', value: 5 }
      ],
      reportSelected,
      reportMsg,
      chatMsg: '',
      sendChat(chatMsg) {
        this.chat_log.push({ text: chatMsg, sent: true })
        console.log(this.chat_log)
      },
      popupMatching,
      popupReport,
      opponent: '감자튀김'
    }
  }
}
</script>

<style scoped>
.chat-btn {
  position: fixed;
  right: -10px;
  top: 45%;
  width: 10px;
  height: 50px;
}
.chat-hide-btn {
  width: 10px;
  height: 50px;
  position: absolute;
  left: -260px;
}
.chat-bar {
  position: relative;
  width: 200px;
  padding: 15px;
  overflow: hidden;
}
.chat-log {
  height: 300px;
  margin: 10px;
}
.popup {
  background: #f3f1eb;
  width: 20%;
  min-width: 350px;
  max-width: 500px;
}
.popup-bar {
  background: #b3a286;
}
.popup-text {
  font-size: 150%;
}
.report-popup {
  background: #f3f1eb;
  width: 30%;
  min-width: 450px;
  max-width: 550px;
}
</style>
