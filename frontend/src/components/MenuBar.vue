<template>
  <div>
    <!-- 익명(로그인하지 않았을 때) -->
    <q-avatar size="90px" class="q-ma-lg fixed-top-right">
      <q-btn round v-close-popup style="margin: 2px">
        <img
          :src="require('../assets/anonymous.png')"
          alt="image"
          style="width: 100%; height: 100%"
        />
        <q-menu
          fit
          anchor="bottom right"
          self="top end"
          :offset="[0, 10]"
          style="
            background-color: #e5edb8;
            width: 300px;
            font-family: 'GowunDodum-Regular';
          "
        >
          <q-item>
            <q-item-section>
              <div>
                <q-img :src="require('../assets/anonymous.png')" width="80px" />
                <span class="q-ma-md" style="font-size: 24px"
                  ><router-link to="/user/login">로그인</router-link> |

                  <router-link to="/user/signup/account"
                    >회원가입</router-link
                  ></span
                >
              </div>
            </q-item-section>
          </q-item>
          <q-separator color="black" />
          <q-item clickable>
            <q-item-section>공지사항</q-item-section>
          </q-item>
          <q-item clickable>
            <q-item-section>1:1문의</q-item-section>
          </q-item>
        </q-menu>
      </q-btn>
    </q-avatar>
    <!-- 로그인되어있는 상태 -->
    <q-avatar size="90px" class="q-ma-lg fixed-top-right">
      <q-btn round v-close-popup style="margin: 2px">
        <img
          :src="require('../assets/profile/' + index + '.png')"
          alt="image"
          style="width: 100%; height: 100%"
        />
        <q-menu
          fit
          anchor="bottom right"
          self="top end"
          :offset="[0, 10]"
          style="
            background-color: #e5edb8;
            width: 300px;
            font-family: 'GowunDodum-Regular';
          "
        >
          <q-item>
            <q-item-section>
              <div>
                <q-img
                  :src="require('../assets/profile/' + index + '.png')"
                  width="80px"
                />
                <span class="q-ma-md" style="font-size: 24px">감자튀김 님</span>
              </div>
            </q-item-section>
          </q-item>
          <q-separator color="black" />
          <q-item clickable>
            <q-item-section>프로필 수정</q-item-section>
          </q-item>
          <q-item clickable>
            <q-item-section>계정정보 수정</q-item-section>
          </q-item>
          <q-separator />
          <q-item clickable>
            <q-item-section>공지사항</q-item-section>
          </q-item>
          <q-item clickable>
            <q-item-section>1:1문의</q-item-section>
          </q-item>
          <q-item clickable>
            <q-item-section @click="popupMatching = true"
              >차단관리</q-item-section
            >
          </q-item>
          <q-separator />
          <q-item clickable>
            <q-item-section>로그아웃</q-item-section>
          </q-item>
        </q-menu>
      </q-btn>
    </q-avatar>

    <!-- 차단관리팝업 -->
    <q-dialog v-model="popupMatching" persistent>
      <q-card class="popup">
        <q-bar class="popup-bar">
          <q-space />
          <q-btn color="red" dense icon="close" v-close-popup />
        </q-bar>
        <q-card-section class="popup-text">
          차단리스트
          <div style="background-color: white">
            <div :key="block.nickname" v-for="block in blockList">
              {{ block.nickname }}
              <q-btn flat icon="close" @click="removeBlock(block.nickname)" />
            </div>
          </div>
        </q-card-section>
        <q-card-section class="popup-text">
          최근 통화 목록
          <div style="background-color: white">
            <div :key="opponent.nickname" v-for="opponent in opponentList">
              {{ opponent.nickname }}
            </div>
          </div>
        </q-card-section>
      </q-card>
    </q-dialog>
  </div>
</template>
<script>
import { ref } from 'vue'

export default {
  components: {},
  data() {
    return {
      sampleData: ''
    }
  },
  setup() {
    const index = ref(2)
    const popupMatching = ref(false)
    return {
      index,
      chatMsg: '',
      sendChat(chatMsg) {
        this.chat_log.push({ text: chatMsg, sent: true })
        console.log(this.chat_log)
      },
      popupMatching,
      blockList: [
        { nickname: '소나무' },
        { nickname: '비타민' },
        { nickname: '하이' }
      ],
      opponentList: [
        { nickname: '시카' },
        { nickname: '펭하' },
        { nickname: '펭바' }
      ],
      removeBlock(nickname) {
        this.blockList.splice(this.blockList.indexOf(nickname), 1)
        console.log(this.blockList)
        console.log(nickname)
      }
    }
  },
  created() {},
  mounted() {},
  unmounted() {},
  methods: {}
}
</script>
<style scoped>
a {
  color: black;
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
