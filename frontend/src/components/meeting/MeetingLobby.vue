<template>
  <div>
    <br />
    <h2>매칭 중 ...</h2>
    <br />
    <div class="row q-gutter-md justify-center items-center">
      <div id="user-video" class="video-container"></div>
      <div>
        <q-img
          :src="require('../../assets/profile/' + imageNo + '.png')"
          width="30%"
        />
        <q-btn
          color="primary"
          @click="alert = true"
          icon="arrow_drop_down"
          class="q-ma-sm"
          flat
          round
        />
        <div>
          <q-dialog v-model="alert">
            <q-card
              style="background-color: #b3a286; color: white; width: 200px"
            >
              <q-card-section>
                <table width="100%" style="text-align: center">
                  <tr>
                    <td width="10%">나이</td>
                    <td width="70%" class="q-px-ml">
                      {{ setting.ageMin }} - {{ setting.ageMax }}
                    </td>
                  </tr>
                  <q-separator inset />
                  <tr>
                    <td>거리</td>
                    <td>{{ filtered_distance }}</td>
                  </tr>
                  <tr>
                    <td>음주</td>
                    <td>
                      {{ filtered_drink }}
                    </td>
                  </tr>
                  <tr>
                    <td>흡연</td>
                    <td>{{ filtered_smoke }}</td>
                  </tr>
                  <tr>
                    <td>종교</td>
                    <td>{{ filtered_religion }}</td>
                  </tr>
                </table>
              </q-card-section>

              <q-card-actions align="center">
                <q-btn flat label="OK" color="primary" v-close-popup />
              </q-card-actions>
            </q-card>
          </q-dialog>
        </div>
        <br /><br />
        <q-btn-dropdown
          split
          color="primary"
          @click="toggleAudio()"
          @show="getAudioDevices()"
        >
          <template v-slot:label>
            <div>
              <template v-if="isOn.audio">
                <div class="row items-center no-wrap" style="width: 100px">
                  <q-icon left name="mic" />
                  <div class="text-center">음소거 시작</div>
                </div>
              </template>
              <template v-else>
                <div class="row items-center no-wrap" style="width: 100px">
                  <q-icon left name="mic_off" />
                  <div class="text-center">음소거 해제</div>
                </div>
              </template>
            </div>
          </template>
          <q-list>
            <q-item
              v-for="(device, index) in audioDevices"
              :key="index"
              clickable
              v-close-popup
              @click="updateAudio(index)"
            >
              <q-item-section>
                <q-item-label>{{ device.label }}</q-item-label>
              </q-item-section>
            </q-item>
          </q-list>
        </q-btn-dropdown>
        <br /><br />

        <br />
        <q-btn-dropdown
          split
          color="primary"
          @click="toggleVideo()"
          @show="getVideoDevices()"
        >
          <template v-slot:label>
            <div>
              <template v-if="isOn.video">
                <div class="row items-center no-wrap" style="width: 100px">
                  <q-icon left name="videocam" />
                  <div class="text-center">비디오 중지</div>
                </div>
              </template>
              <template v-else>
                <div class="row items-center no-wrap" style="width: 100px">
                  <q-icon left name="videocam_off" />
                  <div class="text-center">비디오 시작</div>
                </div>
              </template>
            </div>
          </template>
          <q-list>
            <q-item
              v-for="(device, index) in videoDevices"
              :key="index"
              clickable
              v-close-popup
              @click="updateVideo(index)"
            >
              <q-item-section>
                <q-item-label>{{ device.label }}</q-item-label>
              </q-item-section>
            </q-item>
          </q-list>
        </q-btn-dropdown>
      </div>
    </div>
    <br />
    <template v-if="!isMatching">
      <div class="q-gutter-md">
        <q-btn label="매칭 시작" color="primary" @click="startMatch()" />
        <q-btn
          label="메인으로"
          color="secondary"
          @click="this.$router.push('/')"
        />
      </div>
    </template>
    <template v-else>
      <div class="q-gutter-md">
        <q-btn label="매칭 취소" color="secondary" @click="cancelMatch()" />
        <q-btn label="메인으로" color="secondary" @click="backToMain()" />
      </div>
    </template>
    <MatchModal
      v-model="this.showChoiceModal"
      @confirm="confirm"
      @close="close"
      @changeDisable="this.disable = true"
      :imageNo="opponentInfo.imageNo + ''"
      :modalContent="this.modalContent"
      :disable="this.disable"
      @hide="this.disable = false"
    />
    <ConfirmModal
      v-model="this.showModal"
      :modalContent="this.confirlModalContent"
    />
  </div>
</template>

<script>
import { OpenVidu } from 'openvidu-browser'
import { onMounted, onUnmounted } from 'vue'
import {
  getMeetingSetting,
  enterMeeting,
  startMatching,
  checkMatching,
  successMatching,
  stopMatching
} from '@/api/meeting.js'
import jwtDecode from 'jwt-decode'
import { mapState, mapActions } from 'vuex'
import { WEBSOCKET_URL } from '@/config'
import SockJS from 'sockjs-client'
import Stomp from 'stompjs'
import MatchModal from './MatchModal.vue'
import ConfirmModal from '../ConfirmModal.vue'

const meetingStore = 'meetingStore'

// stompClient.debug = null // disable stomp loggings

export default {
  name: 'App',

  components: { MatchModal, ConfirmModal },

  data() {
    onMounted(() => {
      this.imageNo = this.myProfile().imageNo
      // 미팅 설정 가져오기
      getMeetingSetting(
        (res) => {
          this.setting = res.data.meetingSetting
        },
        (error) => console.warn(error)
      )
      // 현재 위치 설정
      navigator.geolocation.getCurrentPosition((loc) => {
        this.location.lat = loc.coords.latitude
        this.location.lng = loc.coords.longitude
      })
      // 로비 세션 입장
      this.joinSession()
    })
    onUnmounted(() => {
      // 여기서 clearInterval을 해야됨.
      clearInterval(this.interval)
      this.leaveSession()
    })

    return {
      interval: null,

      socket: null,
      stompClient: null,

      location: { lat: 0, lng: 0 },

      disable: false,
      imageNo: 1,
      myInfo: {},
      opponentInfo: {},

      myGender: null,
      myNo: null,
      opponentNo: null,
      // modal ~
      newSessionId: null,
      showChoiceModal: false,
      modalContent: null,
      mAccepted: 0,
      fAccepted: 0,
      // ~ modal

      // confirm modal ~
      showModal: false,
      confirlModalContent: '매칭이 거절되었습니다.',
      // ~ confirm modal

      OV: undefined,
      session: undefined,
      mainStreamManager: undefined,

      publisher: undefined,
      videoDevices: [],
      audioDevices: [],
      audioId: undefined,
      videoId: undefined,

      subscribers: undefined,
      isOn: {
        audio: true,
        video: true
      },

      setting: null,

      mySessionId: new Date().toISOString() + Math.floor(Math.random() * 10000), // 임시로 현재 시간 + 4자리 난수로 설정
      myUserName: 'Participant' + Math.floor(Math.random() * 100), // 사용자의 이름으로 바꿔야함

      alert: false,

      isMatching: false
    }
  },
  computed: {
    ...mapState(meetingStore, ['myPublisher', 'sessionId']),

    filtered_distance() {
      if (this.setting.distance === 0) return '10km'
      else if (this.setting.distance === 1) return '20km'
      else if (this.setting.distance === 2) return '50km'
      else if (this.setting.distance === 3) return '100km'
      else if (this.setting.distance === 4) return 'anywhere'
      else return 'unknown'
    },
    filtered_drink() {
      let a = ''
      let b = ''
      if (this.setting.drinkMin === 0) a = '안함'
      else if (this.setting.drinkMin === 1) a = '가끔'
      else if (this.setting.drinkMin === 2) a = '자주'
      else a = 'unknown'
      if (this.setting.drinkMax === 0) b = '안함'
      else if (this.setting.drinkMax === 1) b = '가끔'
      else if (this.setting.drinkMax === 2) b = '자주'
      else b = 'unknown'
      return a + ' ~ ' + b
    },
    filtered_smoke() {
      if (this.setting.smoke === 0) return '상관없음'
      else if (this.setting.smoke === 1) return '비흡연'
      else if (this.setting.smoke === 2) return '흡연'
      else return 'unknown'
    },
    filtered_religion() {
      if (this.setting.religion === 0) return '상관없음'
      else if (this.setting.religion === 1) return '같은종교'
      else return 'unknown'
    }
  },
  methods: {
    ...mapActions(meetingStore, [
      'setSessionId',
      'setPublisher',
      'setOpponentInfo',
      'setIsMatched'
    ]),

    intervalMatching() {
      return setInterval(
        checkMatching,
        5000,
        (res) => {
          if (res.data.user !== null) {
            this.opponentInfo = res.data.user
            this.modalContent = res.data.user.nickname + '님과 매칭되었습니다.'
            this.showChoiceModal = true
            this.opponentInfo = { ...res.data.user } // 여자가 남자의 정보 저장
            clearInterval(this.interval)

            this.newSessionId =
              this.myProfile().NickName + new Date().toISOString()
            this.setSessionId(this.newSessionId)
            this.subscribeSession()
            // 11초

            this.stompClient.send(
              '/pub/match',
              {},
              JSON.stringify({
                roomId: this.newSessionId,
                maleNo: res.data.user.no,
                female: this.myInfo
              })
            )
          }
        },
        () => console.log('매칭에 실패했습니다.')
      )
    },
    startMatch() {
      this.isMatching = true
      // 공통 구독 (모든 회원이 구독함)
      this.commonSubscribe('/sub/match')

      // 여자는 5초에 한 번씩 매칭확인API 호출
      // (상대를 찾으면 공통 구독에 message를 보냄)
      if (this.myProfile().gender === 'F') {
        this.interval = this.intervalMatching()
      }
    },
    cancelMatch() {
      stopMatching()
      this.isMatching = false
      clearInterval(this.interval)
      this.stompClient.disconnect()
    },
    backToMain() {
      if (this.isMatching) stopMatching()
      this.stompClient.disconnect()
      this.$router.push('/')
    },

    // 모든 사용자는 구독을 시작
    commonSubscribe(url) {
      this.socket = new SockJS(WEBSOCKET_URL) // config로 옮겨야됨!!!!!!!!
      this.stompClient = Stomp.over(this.socket)
      this.stompClient.connect({}, (frame) => {
        startMatching(
          this.location,
          (res) => {
            this.myInfo = res.data.user
          },
          (err) => console.warn(err)
        )
        this.stompClient.subscribe(url, (msg) => {
          const response = JSON.parse(msg.body)
          if (
            this.myGender === 'M' &&
            response.maleNo === parseInt(this.myInfo.no)
          ) {
            this.opponentInfo = response.female
            console.log(this.opponentInfo)
            this.modalContent =
              response.female.nickname + '님과 매칭되었습니다.'
            this.showChoiceModal = true
            this.setSessionId(response.roomId)
            this.subscribeSession()
            // 11초
          }
        })
      })
    },

    // modal ~
    // 둘다 구독 시작 (setTimeout 11초 후 거절했다는 팝업창)
    subscribeSession() {
      stopMatching()
      this.stompClient.subscribe('/sub/chat/' + this.sessionId, (msg) => {
        const res = JSON.parse(msg.body)
        if (parseInt(res.nickname) === this.myNo) {
          if (this.myGender === 'M') this.mAccepted = res.content === 'true'
          else this.fAccepted = res.content === 'true'
        } else {
          if (this.myGender === 'M') this.fAccepted = res.content === 'true'
          else this.mAccepted = res.content === 'true'
          this.opponentNo = parseInt(res.nickname)
        }
        if (this.mAccepted && this.fAccepted) {
          // 유일한 매칭 성공 조건
          clearTimeout(timeout)
          this.setIsMatched(true)
          if (this.myGender === 'M') {
            successMatching(
              this.opponentNo,
              (res) => {
                this.opponentInfo = res.data.user
              },
              (error) => {
                console.log(error)
              }
            ) // 매칭 성사 API 남자만 호출
          }
          // vuex에 상대 정보 저장해줌
          this.setOpponentInfo(this.opponentInfo)
          this.sendSetting()

          this.$router.push('/meeting/room')
        }
        // 매칭 실패 조건 1
        if (this.mAccepted === false && this.fAccepted === false) {
          clearTimeout(timeout)
          this.showChoiceModal = false
          console.log('매칭에 실패했습니다.')
          if (this.myGender === 'F') this.interval = this.intervalMatching()
          this.mAccepted = null
          this.fAccepted = null
          startMatching(this.location)
          this.showModal = true
          setTimeout(() => (this.showModal = false), 3000)
        }
      })
      const timeout = setTimeout(() => {
        this.showChoiceModal = false
        // 매칭 실패 조건 2
        console.log('매칭에 실패했습니다.')
        startMatching(this.location)
        if (this.myGender === 'F') this.interval = this.intervalMatching()
        this.mAccepted = null
        this.fAccepted = null
        this.showModal = true
        setTimeout(() => (this.showModal = false), 3000)
      }, 11000)
    },

    confirm() {
      const request = { nickname: this.myNo, content: true }
      this.stompClient.send(
        '/pub/chat/' + this.sessionId,
        {},
        JSON.stringify(request)
      )
    },

    close() {
      const request = { nickname: this.myNo, content: false }
      this.stompClient.send(
        '/pub/chat/' + this.sessionId,
        {},
        JSON.stringify(request)
      )
    },

    // ~ modal

    myProfile() {
      const token = sessionStorage.getItem('Authorization')
      const profile = jwtDecode(token)
      this.myGender = profile.gender
      this.myNo = parseInt(profile.sub)
      return profile
    },

    // 오디오 비디오 장치 배열로 가져오기
    getVideoDevices() {
      this.OV.getDevices().then(
        (devices) =>
          (this.videoDevices = devices.filter(
            (device) => device.kind === 'videoinput'
          ))
      )
    },
    getAudioDevices() {
      this.OV.getDevices().then(
        (devices) =>
          (this.audioDevices = devices.filter(
            (device) => device.kind === 'audioinput'
          ))
      )
    },

    // 오디오 비디오 장치 전환
    updateAudio(index) {
      this.OV.getDevices().then((devices) => {
        const audioDevices = devices.filter(
          (device) => device.kind === 'audioinput'
        )
        if (audioDevices && audioDevices.length > 1) {
          const newPublisher = this.OV.initPublisher('user-video', {
            audioSource: audioDevices[index].deviceId,
            videoSource: this.videoId,
            publishAudio: this.isOn.audio,
            publishVideo: this.isOn.video,
            resolution: '500x375', // The resolution of your video
            mirror: false
          })
          this.session.unpublish(this.publisher).then(() => {
            this.publisher = newPublisher
            this.session.publish(this.publisher).then(() => {
              console.log('오디오 장치 전환')
            })
          })
          this.audioId = audioDevices[index].deviceId
        }
      })
    },
    updateVideo(index) {
      this.OV.getDevices().then((devices) => {
        const videoDevices = devices.filter(
          (device) => device.kind === 'videoinput'
        )
        if (videoDevices && videoDevices.length > 1) {
          const newPublisher = this.OV.initPublisher('user-video', {
            audioSource: this.audioId,
            videoSource: videoDevices[index].deviceId,
            publishAudio: this.isOn.audio,
            publishVideo: this.isOn.video,
            resolution: '500x375', // The resolution of your video
            mirror: false
          })
          this.session.unpublish(this.publisher).then(() => {
            this.publisher = newPublisher
            this.session.publish(this.publisher).then(() => {
              console.log('비디오 장치 전환')
            })
          })
          this.videoId = videoDevices[index].deviceId
        }
      })
    },

    // 오디오 비디오 토글
    toggleAudio() {
      this.isOn.audio = !this.isOn.audio
      this.publisher.publishAudio(this.isOn.audio)
    },
    toggleVideo() {
      this.isOn.video = !this.isOn.video
      this.publisher.publishVideo(this.isOn.video)
    },

    // 미팅 룸으로 넘어가기 전에 내 오디오 비디오 상태를 전달하기 위해 vuex에 저장하는 함수
    sendSetting() {
      this.setPublisher({
        audioSource: this.audioId,
        videoSource: this.videoId,
        publishAudio: this.isOn.audio,
        publishVideo: this.isOn.video
      })
    },

    // 로비 입장시 실행돼야 하는 함수
    joinSession() {
      this.OV = new OpenVidu()
      this.OV.enableProdMode() // @ disable all logging except error level
      this.session = this.OV.initSession()
      this.session.on('streamCreated', ({ stream }) => {
        const subscriber = this.session.subscribe(stream)
        this.subscribers = [subscriber]
      })
      this.session.on('streamDestroyed', () => {
        this.subscribers = []
      })
      this.session.on('exception', ({ exception }) => {
        console.warn(exception)
      })

      this.getToken(this.mySessionId).then((response) => {
        const token = response.data.token
        if (token === undefined) {
          console.warn('토큰이 존재하지 않습니다. 로그인 후 사용해주세요.')
        }
        this.session
          .connect(token, { clientData: this.myUserName })
          .then(() => {
            // --- Get your own camera stream with the desired properties ---
            const publisher = this.OV.initPublisher('user-video', {
              ...this.myPublisher,
              audioSource: undefined,
              videoSource: undefined,
              publishAudio: true,
              publishVideo: true
            })
            this.mainStreamManager = publisher
            this.publisher = publisher
            this.session.publish(this.publisher)
          })
          .catch((error) => {
            console.log(
              'There was an error connecting to the session:',
              error.code,
              error.message
            )
          })
      })
      window.addEventListener('beforeunload', (e) => {
        e.preventDefault()
        this.leaveSession()
        this.stompClient.disconnect()
      })
    },
    leaveSession() {
      // --- Leave the session by calling 'disconnect' method over the Session object ---
      if (this.session) this.session.disconnect()
      this.session = undefined
      this.mainStreamManager = undefined
      this.publisher = undefined
      this.subscribers = []
      this.OV = undefined
      window.removeEventListener('beforeunload', this.leaveSession)
    },
    getToken(mySessionId) {
      return this.createSession(mySessionId)
    },
    createSession(sessionId) {
      return enterMeeting(sessionId)
    }
  }
}
</script>

<style scoped>
.video-container {
  width: 500px;
  height: 375px;
}
</style>
