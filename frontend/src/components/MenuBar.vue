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
            <q-item-section @click="popupBlock = true">차단관리</q-item-section>
          </q-item>
          <q-separator />
          <q-item clickable>
            <q-item-section>로그아웃</q-item-section>
          </q-item>
        </q-menu>
      </q-btn>
    </q-avatar>

    <!-- 차단관리팝업 -->
    <q-dialog v-model="popupBlock" persistent>
      <q-card class="popup">
        <q-bar class="popup-bar">
          <q-space />
          <q-btn color="red" dense icon="close" v-close-popup />
        </q-bar>
        <q-card-section class="popup-text">
          차단리스트
          <div style="background-color: white">
            <div
              :key="block.nickname"
              v-for="(block, index) in blockList"
              class="full-width row justify-between"
            >
              {{ block.nickname }}
              <q-btn flat icon="close" @click=";[removeBlock(index)]" />
            </div>
          </div>
        </q-card-section>
        <q-card-section class="popup-text">
          최근 통화 목록
          <div style="background-color: white">
            <div
              :key="opponent.nickname"
              v-for="(opponent, index) in opponentList"
              class="full-width row justify-between"
            >
              {{ opponent.nickname }}
              <q-btn flat icon="add" @click="AddBlock(index)" />
            </div>
          </div>
        </q-card-section>
      </q-card>
    </q-dialog>
  </div>
</template>
<script>
import { ref } from 'vue'
// import Vue from 'vue'
export default {
  components: {},
  data() {
    return {
      sampleData: ''
    }
  },
  setup() {
    const index = ref(2)
    const popupBlock = ref(false)
    return {
      index,
      chatMsg: '',
      sendChat(chatMsg) {
        this.chat_log.push({ text: chatMsg, sent: true })
        console.log(this.chat_log)
      },
      popupBlock,
      blockList: [
        { userNo: 123, nickname: '소나무' },
        { userNo: 45, nickname: '비타민' },
        { userNo: 673, nickname: '하이' }
      ],
      blocknickname: ['소나무', '비타민', '하이'],
      opponentList: [
        { userNo: 45, nickname: '비타민' },
        { userNo: 373, nickname: '펭하' },
        { userNo: 994, nickname: '펭바' }
      ],
      removeBlock(index) {
        const idx = this.blocknickname.indexOf(this.blockList[index].nickname)
        if (idx !== -1) {
          this.blocknickname.splice(index, 1)
        }
        this.blockList.splice(index, 1)
      },
      AddBlock(index) {
        if (
          this.blocknickname.indexOf(this.opponentList[index].nickname) === -1
        ) {
          this.blocknickname.push(this.opponentList[index].nickname)
          this.blockList.push(this.opponentList[index])
        }
      }
    }
  },
  watch: {
    blockList: {
      handler(val) {
        console.log('변경', val)
      },
      deep: true
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
