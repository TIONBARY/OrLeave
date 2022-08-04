<template>
  <div class="chat-bar">
    <q-btn icon="arrow_back_ios" style="width: 100%; height: 100%" />
    <q-menu
      anchor="center right"
      self="center right"
      v-model="showing"
      transition-show="slide-left"
      transition-hide="slide-right"
      persistent
      style="width: 200px; padding: 5px; overflow: hidden"
    >
      <q-btn icon="arrow_forward_ios" @click="showing = false" />
      <q-scroll-area style="height: 300px">
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
  </div>
</template>

<script>
import { ref } from 'vue'
export default {
  setup() {
    const showing = ref(false)
    return {
      showing,
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
      chatMsg: '',
      sendChat(chatMsg) {
        this.chat_log.push({ text: chatMsg, sent: true })
        console.log(this.chat_log)
      }
    }
  }
}
</script>

<style scoped>
.chat-bar {
  position: fixed;
  right: 0px;
  top: 45%;
  width: 20px;
  height: 50px;
}
</style>
