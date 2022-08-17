<template>
  <div>
    <!-- 익명(로그인하지 않았을 때) -->
    <q-avatar
      v-if="nickname === null"
      size="90px"
      class="q-ma-lg fixed-top-right"
    >
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
                <span class="q-ma-md" style="font-size: 20px"
                  ><router-link to="/user/login">로그인</router-link> |

                  <router-link to="/user/signup/account"
                    >회원가입</router-link
                  ></span
                >
              </div>
            </q-item-section>
          </q-item>
          <q-separator color="black" />
          <router-link to="/notice/list">
            <q-item clickable>
              <q-item-section>공지사항</q-item-section>
            </q-item>
          </router-link>
        </q-menu>
      </q-btn>
    </q-avatar>
    <!-- 로그인되어있는 상태 -->

    <q-avatar
      v-if="nickname != null"
      size="90px"
      class="q-ma-lg fixed-top-right"
    >
      <q-btn round v-close-popup style="margin: 2px">
        <img
          :src="require('../assets/profile/' + imageno + '.png')"
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
                  :src="require('../assets/profile/' + imageno + '.png')"
                  width="80px"
                />
                <span class="q-ma-md" style="font-size: 24px"
                  >{{ nickname }} 님</span
                >
              </div>
            </q-item-section>
          </q-item>
          <q-separator color="black" />
          <router-link to="/user/modify/profile">
            <q-item clickable>
              <q-item-section>프로필 수정</q-item-section>
            </q-item>
          </router-link>
          <router-link to="/user/modify/account">
            <q-item clickable>
              <q-item-section>계정정보 수정</q-item-section>
            </q-item>
          </router-link>
          <q-separator />
          <router-link to="/notice/list">
            <q-item clickable>
              <q-item-section>공지사항</q-item-section>
            </q-item>
          </router-link>
          <router-link to="/inquiry/list">
            <q-item clickable>
              <q-item-section>1:1문의</q-item-section>
            </q-item>
          </router-link>
          <q-item clickable>
            <q-item-section @click="popupBlock = true">차단관리</q-item-section>
          </q-item>
          <q-separator />
          <q-item clickable>
            <q-item-section @click="Logout">로그아웃</q-item-section>
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
              :key="block.bannedUserNickname"
              v-for="(block, index) in banuserlist"
              class="full-width row justify-between"
            >
              {{ block.bannedUserNickname }}
              <q-btn flat icon="close" @click=";[removeBlock(index)]" />
            </div>
          </div>
        </q-card-section>
        <q-card-section class="popup-text">
          최근 통화 목록
          <div style="background-color: white">
            <div
              :key="opponent.nickname"
              v-for="(opponent, index) in meetinguserlist"
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
import { mapState, mapActions } from 'vuex'
import jwtdecode from 'jwt-decode'
const userStore = 'userStore'

export default {
  components: {},
  setup() {
    const addban = ref({ bannedNo: null })
    const nickname = ref(null)
    const imageno = ref(2)
    const popupBlock = ref(false)
    return {
      addban,
      nickname,
      imageno,
      popupBlock
    }
  },
  computed: {
    ...mapState(userStore, ['SET_LOGOUT', 'banuserlist', 'meetinguserlist'])
  },
  methods: {
    ...mapActions(userStore, [
      'logout',
      'getBanuser',
      'deleteBanuser',
      'getmeetinglog',
      'addBanuser'
    ]),
    Logout() {
      this.logout()
    },
    removeBlock(index) {
      this.deleteBanuser(this.banuserlist[index].bannedNo)
      this.banuserlist.splice(index, 1)
    },
    async AddBlock(index) {
      this.addban.bannedNo = this.meetinguserlist[index].userNo
      await this.addBanuser(this.addban)
      await this.getBanuser()
    }
  },
  async created() {
    if (sessionStorage.getItem('Authorization') != null) {
      const info = jwtdecode(sessionStorage.getItem('Authorization'))
      this.nickname = info.NickName
      this.imageno = info.imageNo
      await this.getBanuser()
      this.getmeetinglog()
    }
  }
}
</script>
<style scoped>
a {
  font-size: 100%;
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
