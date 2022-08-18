<template>
  <div id="main-container" class="container">
    <!-- 사이트 디버깅 막기 -->
    <div id="session">
      <h1>{{ level }}단계</h1>

      <div id="session-header" class="m-ma-lg row justify-around">
        <div class="column">
          <div class="video-box">
            <div id="user-video">
              <user-video :stream-manager="mainStreamManager" :level="level" />
            </div>
          </div>
          <div class="q-my-lg">
            <q-img
              :src="require('../../assets/profile/' + myImageNo + '.png')"
              width="30px"
              style="margin-right: 10px"
            />
            {{ myNickname }}
          </div>
        </div>
        <div id="video-container">
          <div class="col-5 col-sm-3 column">
            <div class="video-box">
              <user-video
                v-for="sub in subscribers"
                :key="sub.stream.connection.connectionId"
                :stream-manager="sub"
                :level="level"
              />
            </div>
            <div class="q-my-lg">
              <q-img
                :src="
                  require('../../assets/profile/' +
                    opponentInfo.imageNo +
                    '.png')
                "
                width="30px"
                style="margin-right: 10px"
              />
              {{ opponentInfo.nickname }}
            </div>
          </div>
        </div>
      </div>
      <div style="font-size: 24px">
        {{ question }}
      </div>
      <div class="q-mt-md">
        <q-btn
          @click="toggleAudio()"
          color="primary"
          v-if="isOn.audio"
          icon="mic"
          label="음소거 시작"
          class="q-mr-lg"
        />
        <q-btn
          @click="toggleAudio()"
          color="primary"
          v-else
          icon="mic_off"
          label="음소거 해제"
          class="q-mr-lg"
        />

        <q-btn
          @click="toggleVideo()"
          color="primary"
          v-if="isOn.video"
          icon="videocam"
          label="비디오 중지"
        />

        <q-btn
          @click="toggleVideo()"
          color="primary"
          v-else
          icon="videocam_off"
          label="비디오 시작"
        />
      </div>
      <div id="session-header">
        <div>
          <!-- <q-img
            id="skip_img"
            src="../../assets/meeting/skip.png"
            v-if="level < 3 && skipDisable === true"
            width="130px"
            no-spinner
            class="absolute-bottom-left"
            style="filter: grayscale(100%)"
          /> -->
          <q-img
            id="skip_img"
            src="../../assets/meeting/skip.png"
            width="130px"
            no-spinner
            class="absolute-bottom-left"
            :class="{ disabled: skipDisable, abled: !skipDisable }"
            @click="levelUp()"
            v-if="this.level < 3"
          >
            <q-tooltip v-if="skipDisable">
              <div>조금 더 대화를 나눠보세요.</div>
            </q-tooltip>
          </q-img>
          <!-- <q-btn  flat :ripple="false" /> -->

          <q-img
            src="../../assets/meeting/leave.png"
            width="130px"
            no-spinner
            @click="popupMatching = true"
            class="absolute-bottom-right abled"
          >
            <q-btn flat :ripple="false" />
          </q-img>
          <q-dialog v-model="popupMatching" persistent>
            <q-card class="popup">
              <q-bar class="popup-bar">
                <q-space />
                <q-btn color="red" dense icon="close" v-close-popup />
              </q-bar>
              <q-card-section class="popup-text text-center">
                정말 떠나시겠습니까?
              </q-card-section>
              <q-card-section class="q-gutter-sm text-center">
                <q-btn class="primary" label="확인" @click="leaveSession" />
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
                      <q-input
                        outlined
                        readonly
                        bg-color="white"
                        v-model="opponentInfo.nickname"
                        dense
                      />
                    </td>
                  </tr>
                  <tr>
                    <td>신고분류</td>
                    <td>
                      <q-option-group
                        v-model="category"
                        :options="reportOptions"
                        type="radio"
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
                        v-model="content"
                        dense
                      />
                    </td>
                  </tr>
                </table>
              </q-card-section>
              <q-card-section class="q-gutter-sm text-right">
                <q-btn
                  class="negative"
                  label="신고하기"
                  @click=";[report(), leaveSession()]"
                  type="submit"
                />
                <q-btn class="secondary" label="취소" v-close-popup />
              </q-card-section>
            </q-card>
          </q-dialog>
        </div>
      </div>
    </div>
    <MeetingChat />
    <ConfirmModal
      v-model="this.showModal"
      @close="this.leaveSession"
      :modalContent="this.modalContent"
    />
  </div>
</template>

<script>
import { OpenVidu } from 'openvidu-browser'
import UserVideo from './video/UserVideo.vue'
import { onMounted } from 'vue'
import { mapActions, mapState } from 'vuex'
import { reportUser, enterMeeting, leaveMeeting } from '@/api/meeting'
import jwtDecode from 'jwt-decode'
import { WEBSOCKET_URL } from '@/config/index'
import SockJS from 'sockjs-client'
import Stomp from 'stompjs'
import MeetingChat from './MeetingChat.vue'
import ConfirmModal from '../ConfirmModal.vue'

// stompClient.debug = null // disable stomp loggings

const meetingStore = 'meetingStore'

export default {
  name: 'App',

  components: {
    UserVideo,
    MeetingChat,
    ConfirmModal
  },
  computed: {
    ...mapState(meetingStore, [
      'sessionId',
      'myPublisher',
      'opponentInfo',
      'isMatched'
    ])
  },
  data() {
    onMounted(() => {
      if (this.sessionId === '' || this.isMatched === false) {
        this.$router.push('/404')
        return
      }
      if (!this.myPublisher.publishAudio) this.isOn.audio = false
      if (!this.myPublisher.publishVideo) this.isOn.video = false

      const token = jwtDecode(sessionStorage.getItem('Authorization'))

      const myGender = token.gender
      if (myGender === 'M') {
        setTimeout(this.joinSession, 1000)
      } else {
        this.joinSession()
      }
      this.myNickname = token.NickName
      this.myImageNo = token.imageNo

      this.socket = new SockJS(WEBSOCKET_URL) // config로 옮겨야됨!!!!!!!!
      this.stompClient = Stomp.over(this.socket)

      this.skip1 = setTimeout(() => {
        this.skipDisable = false
      }, 30000)
      this.stompClient.connect({}, () => {
        this.stompClient.subscribe(
          '/sub/chat/' + this.sessionId + 's',
          (msg) => {
            // 메세지를 받았을 때 실행하는 부분
            const response = JSON.parse(msg.body)
            const msgNickname = response.nickname
            if (
              response.content !== 'true' &&
              msgNickname !== this.myNickname
            ) {
              // 팝업을 띄워도 되고... 나가기 전에 알려주는 어떤 그런거
              let seconds = 5
              const msg = '초 후 방에서 나갑니다.'
              this.modalContent = seconds + '초 후 방에서 나갑니다.'
              const msgInterval = setInterval(() => {
                --seconds
                if (seconds === 0) clearInterval(msgInterval)
                this.modalContent = seconds + msg
              }, 1000)
              this.showModal = true
              this.autoExit = setTimeout(this.leaveSession, 5000)
              return
            }
            if (msgNickname === this.myNickname) {
              this.mySkip = true
            } else {
              this.yourSkip = true
            }

            if (this.mySkip && this.yourSkip) {
              this.level++
              this.mySkip = false
              this.yourSkip = false
              this.question_pick()
              if (this.level === 2) {
                this.skipDisable = true
                this.skip2 = setTimeout(() => {
                  this.skipDisable = false
                }, 30000)
                this.publisher.stream
                  .removeFilter()
                  .then(() => {})
                  .catch((error) => {
                    console.error(error)
                  })
              } else {
                this.skipDisable = true
              }
            }
          }
        )
      })
    })
    return {
      socket: null,
      stompClient: null,
      autoExit: null,
      question: null,
      camera: false,
      level: 1,
      skipDisable: true,
      myNickname: null,
      myImageNo: 0,
      skip1: null,
      skip2: null,
      // 질문을 단계별, 상대의 취미 별로 나눠야함
      questions: [
        // [0]~[10]: 관심사
        [
          '즐겨하는 게임이 무엇인지 물어보세요.',
          '좋아하는 e스포츠 선수가 있는지 물어보세요.',
          '좋아하는 게임 유튜브가 있는지 물어보세요.'
        ],
        [
          '평소 꾸준히 하는 운동이 있는지 물어보세요.',
          '즐겨 보는(하는) 스포츠에 대해 물어보세요.',
          '좋아하는 운동 선수가 있는지 물어보세요.'
        ],
        [
          '어떤 장르의 영화를 좋아하는지 물어보세요.',
          '가장 최근 본 영화를 물어보세요.',
          '인생 영화가 무엇인지 물어보세요.'
        ],
        [
          '어떤 장르의 책을 좋아하는지 물어보세요.',
          '가장 최근에 본 책을 물어보세요.',
          '가장 감명깊게 읽은 책에 대해 물어보세요.'
        ],
        [
          '어떤 장르의 음악을 좋아하는지 물어보세요.',
          '좋아하는 가수가 누구인지 물어보세요.',
          '가장 좋아하는 노래를 물어보세요.'
        ],
        [
          '인생 최고의 맛집은 어디였는지 물어보세요.',
          '대체로 어떤 맛집을 탐방하는지 물어보세요.',
          '자신이 가봤던 맛집을 소개해보세요.'
        ],
        [
          '패션에 얼마나 관심이 있는지 물어보세요.',
          '어떤 패션을 선호하는지 물어보세요.',
          '패션에 관심이 생긴 계기를 물어보세요.'
        ],
        [
          '왜 보드게임을 좋아하는지 물어보세요.',
          '보드게임 카페에 자주 가는지 물어보세요.',
          '가장 좋아하는 보드게임을 물어보세요.'
        ],
        [
          '반려동물 유튜브를 보는지 물어보세요.',
          '반려동물과의 특별한 추억을 물어보세요.',
          '동물로 환생한다면, 어떻게 하고싶은지 물어보세요.'
        ],
        [
          '자신있는 요리를 물어보세요.',
          '가장 최근에 한 요리를 물어보세요.',
          '인생 처음으로 했던 요리를 물어보세요.'
        ],
        [
          '정중하게 상대의 백준 티어를 물어보세요.',
          '같이 모각코 할 생각이 있는지 물어보세요.',
          '기억에 남는 알고리즘 문제에 대해 물어보세요.'
        ],
        // [11]~[21]: 성격
        ['상대는 차분한 사람입니다. 귀를 기울여 보아요.'],
        ['상대는 발랄한 사람입니다. 텐션에 놀라지 마세요.'],
        ['상대는 센스있는 사람입니다. 유심히 지켜보세요.'],
        ['상대의 배려심을 주목하세요.'],
        ['상대는 자신감이 넘치고 당당한 사람입니다.'],
        ['상대는 어떤 상황에서도 항상 열정적입니다.'],
        ['상대는 부지런한 사람이네요!'],
        ['상대는 매사에 긍정적인 편입니다.'],
        ['상대는 세심한 성격의 소유자입니다.'],
        ['상대방은 온화한 사람입니다. 존중하며 대화해요.'],
        ['상대는 솔직한 사람입니다. 조금 직설적인가요?'],
        // [22]: 공통 질문
        [
          '프로필 사진을 고른 이유를 물어보세요.',
          '시간대에 따라 식사를 하셨는지 물어보세요.',
          '좋아하는 것을 얘기하며 공감대를 형성해 보세요.',
          '요즘 날씨에 대해 얘기해볼까요?',
          '친구들 사이에서 어떤 이미지인지 물어보세요.',
          '가족들 사이에서 어떤 이미지인지 물어보세요.'
        ]
        // mbti질문 무조건 하나(모름이면 안넣기), 관심사 랜덤으로 하나?, 성격 랜덤으로 하나?
        // 공통질문1 + MBTI질문0/1 + 관심사당질문1*1~3 + 성격당질문1*1~3
        // 나온 질문은 pop
      ],
      customQuestions: [],
      reportOptions: [
        { label: '폭언 및 욕설', value: 0 },
        { label: '부적절한 닉네임', value: 1 },
        { label: '부적절한 언행', value: 2 },
        { label: '성희롱 및 성추행', value: 3 },
        { label: '혐오발언', value: 4 },
        { label: '기타', value: 5 }
      ],
      reportNo: 0,
      category: null,
      content: '',
      popupMatching: false,
      popupReport: false,
      mySkip: false,
      yourSkip: false,

      OV: undefined,
      session: undefined,
      mainStreamManager: undefined,
      publisher: undefined,
      subscribers: [],
      isOn: {
        audio: true,
        video: true
      },
      token: undefined,
      showModal: false,
      modalContent: '',
      myUserName: Math.floor(Math.random() * 100)
      // myUserName: 비디오 아래에 들어갈 유저 닉네임 (jwt 디코드)
      // 사진도 넣어야한다
    }
  },

  methods: {
    ...mapActions(meetingStore, ['setSessionId', 'setIsMatched']),
    question_pick() {
      const rand = Math.floor(Math.random() * this.customQuestions.length)
      const picked = this.customQuestions.pop(rand)
      this.question = picked
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

    joinSession() {
      // --- Get an OpenVidu object ---
      this.OV = new OpenVidu()

      // --- Init a session ---
      this.session = this.OV.initSession()
      // --- Specify the actions when events take place in the session ---

      // On every new Stream received...
      this.session.on('streamCreated', ({ stream }) => {
        const subscriber = this.session.subscribe(stream)
        this.subscribers.push(subscriber)
      })

      // On every Stream destroyed...
      this.session.on('streamDestroyed', ({ stream }) => {
        const index = this.subscribers.indexOf(stream.streamManager, 0)
        if (index >= 0) {
          this.subscribers.splice(index, 1)
        }
      })

      // On every asynchronous exception...
      this.session.on('exception', () => {})

      // --- Connect to the session with a valid user token ---

      // 'getToken' method is simulating what your server-side should do.
      // 'token' parameter should be retrieved and returned by your own backend
      this.getToken(this.sessionId).then((response) => {
        this.token = response.data.token
        this.session
          .connect(this.token, { clientData: this.myUserName })
          .then(() => {
            // --- Get your own camera stream with the desired properties ---

            const publisher = this.OV.initPublisher(undefined, {
              ...this.myPublisher,
              // audioSource: undefined, // The source of audio. If undefined default microphone
              // videoSource: undefined, // The source of video. If undefined default webcam
              // publishAudio: true, // Whether you want to start publishing with your audio unmuted or not
              // publishVideo: true, // Whether you want to start publishing with your video enabled or not
              // resolution: '640x480', // The resolution of your video
              // frameRate: 30, // The frame rate of your video
              // insertMode: 'APPEND', // How the video is inserted in the target element 'video-container'
              // mirror: false, // Whether to mirror your local video or not
              filter: {
                type: 'GStreamerFilter',
                options: {
                  command: 'pitch pitch=1.5'
                }
              }
            })

            this.mainStreamManager = publisher
            this.publisher = publisher
            // --- Publish your stream ---
            this.session.publish(this.publisher)
          })
          .catch((error) => {
            console.warn(
              'There was an error connecting to the session:',
              error.code,
              error.message
            )
          })
      })
      window.addEventListener('beforeunload', (e) => {
        e.preventDefault()

        this.setIsMatched(false)
        this.leaveSession()
      })
    },

    leaveSession() {
      const msg = { nickname: this.myNickname, content: false }
      this.stompClient.send(
        '/pub/chat/' + this.sessionId + 's',
        {},
        JSON.stringify(msg)
      )
      this.stompClient.disconnect()
      clearTimeout(this.autoExit)
      // --- Leave the session by calling 'disconnect' method over the Session object ---
      if (this.session) this.session.disconnect()

      this.session = undefined
      this.mainStreamManager = undefined
      this.publisher = undefined
      this.subscribers = []
      this.OV = undefined

      leaveMeeting(this.sessionId, this.token)

      window.removeEventListener('beforeunload', this.leaveSession)
      this.$router.push('/')
    },

    updateMainVideoStreamManager(stream) {
      if (this.mainStreamManager === stream) return
      this.mainStreamManager = stream
    },

    getToken(sessionId) {
      return this.createSession(sessionId)
    },
    // See https://docs.openvidu.io/en/stable/reference-docs/REST-API/#post-session
    createSession(sessionId) {
      return enterMeeting(sessionId)
    },
    levelUp() {
      if (this.skipDisable) {
        return
      }
      const msg = { nickname: this.myNickname, content: true }
      this.stompClient.send(
        '/pub/chat/' + this.sessionId + 's',
        {},
        JSON.stringify(msg)
      )
    },
    report() {
      const reportedNo = this.opponentInfo.no
      const category = this.category
      const content = this.content
      reportUser({ reportedNo, category, content })
    }
  },
  beforeMount() {
    // 공통질문 push
    this.customQuestions.push(
      this.questions[22].at(
        Math.floor(Math.random() * this.questions[22].length)
      )
    )
    // 관심사질문 push
    this.opponentInfo.interests.forEach((index) => {
      const picked = this.questions[index].at(Math.floor(Math.random() * 3))
      this.customQuestions.push(picked)
    })
    // 성격질문 push
    this.opponentInfo.personalities.forEach((index) => {
      Array.prototype.push.apply(
        this.customQuestions,
        this.questions[index + 11]
      )
    })
    this.question_pick()
  }
}
</script>

<style scoped>
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
.video-box {
  width: 500px;
  height: 375px;
  overflow: hidden;
  border-radius: 10px;
}
.disabled {
  filter: grayscale(80%);
  cursor: wait;
}
.abled {
  cursor: pointer;
}
</style>
