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
            v-for="chat in chatLog"
            :key="chat"
            :text="[chat.text]"
            :sent="chat.sent"
          />
        </q-scroll-area>
        <input v-model="userName" type="text" />
        <input v-model="message" type="text" @keyup="sendMessage" />
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
import Stomp from 'webstomp-client'
import SockJS from 'sockjs-client'

export default {
  setup() {
    const showChat = ref(false)
    const popupMatching = ref(false)
    const popupReport = ref(false)
    const reportSelected = ref([])
    const reportMsg = ref('')
    const chatMsg = ref(null)
    const recvList = ref([])
    const chatLog = ref([])
    return {
      userName: '',
      message: '',
      recvList,
      showChat,
      chatLog,
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
      chatMsg,
      popupMatching,
      popupReport,
      opponent: '감자튀김'
    }
  },
  created() {
    this.connect()
  },
  methods: {
    sendMessage(e) {
      if (e.keyCode === 13 && this.userName !== '' && this.message !== '') {
        this.send()
        this.message = ''
      }
    },
    send() {
      console.log('Send message:' + this.message)
      if (this.stompClient && this.stompClient.connected) {
        const msg = {
          userName: this.userName,
          content: this.message
        }
        this.stompClient.send('/receive', JSON.stringify(msg), {})
      }
    },
    connect() {
      const serverURL = 'http://localhost:8080/api/v1/ws'
      const socket = new SockJS(serverURL)
      this.stompClient = Stomp.over(socket)
      console.log(`소켓 연결을 시도합니다. 서버 주소: ${serverURL}`)
      this.stompClient.connect(
        {},
        (frame) => {
          // 소켓 연결 성공
          this.connected = true
          console.log('소켓 연결 성공', frame)
          // 서버의 메시지 전송 endpoint를 구독합니다.
          // 이런형태를 pub sub 구조라고 합니다.
          this.stompClient.subscribe('/send', (res) => {
            console.log('구독으로 받은 메시지 입니다.', res.body)

            // 받은 데이터를 json으로 파싱하고 리스트에 넣어줍니다.
            this.recvList.push(JSON.parse(res.body))
            const temp = this.recvList.pop()
            if (temp.userName === '김시언') {
              this.chatLog.push({ text: temp.content, sent: true })
            } else if (temp.userName === '정승욱') {
              this.chatLog.push({ text: temp.content, sent: false })
            }
          })
        },
        (error) => {
          // 소켓 연결 실패
          console.log('소켓 연결 실패', error)
          this.connected = false
        }
      )
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
