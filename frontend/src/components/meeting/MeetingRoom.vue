<template>
  <div id="main-container" class="container">
    <div id="join" v-if="!session">
      <div id="join-dialog" class="jumbotron vertical-center">
        <h1>Join a video session</h1>
        <div class="form-group">
          <p>
            <label>Participant</label>
            <input
              v-model="myUserName"
              class="form-control"
              type="text"
              required
            />
          </p>
          <p>
            <label>Session</label>
            <input
              v-model="mySessionId"
              class="form-control"
              type="text"
              required
            />
          </p>
          <p class="text-center">
            <button class="btn btn-lg btn-success" @click=";[joinSession()]">
              Join!
            </button>
          </p>
        </div>
      </div>
    </div>
    <!-- 사이트 디버깅 막기 -->
    <div id="session" v-if="session">
      <h1>{{ level }}단계</h1>

      <div id="session-header" class="m-ma-lg row justify-around">
        <div id="main-video">
          <user-video :stream-manager="mainStreamManager" :level="level" />
        </div>
        <div id="video-container">
          <user-video
            class="col-5 col-sm-3"
            v-for="sub in subscribers"
            :key="sub.stream.connection.connectionId"
            :stream-manager="sub"
            :level="level"
            @click="updateMainVideoStreamManager(sub)"
          />
        </div>
      </div>
      <div style="font-size: 24px">
        {{ question }}
      </div>
      <div>
        <q-btn
          @click="toggleAudio()"
          color="primary"
          v-if="isOn.audio"
          icon="mic_off"
          label="음소거 해제"
        />
        <q-btn
          @click="toggleAudio()"
          color="primary"
          v-else
          icon="mic"
          label="음소거 시작"
        />

        <q-btn
          @click="toggleVideo()"
          color="primary"
          v-if="isOn.video"
          icon="videocam_off"
          label="비디오 시작"
        />

        <q-btn
          @click="toggleVideo()"
          color="primary"
          v-else
          icon="videocam"
          label="비디오 중지"
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
            @click=";[levelUp(), question_pick(level)]"
          >
            <q-btn flat color="red" :ripple="false" />
          </q-img>
          <q-img
            src="../../assets/meeting/leave.png"
            width="130px"
            no-spinner
            @click="popupMatching = true"
            class="absolute-bottom-right"
          >
            <q-btn flat :ripple="false" />
          </q-img>
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
                        bg-color="white"
                        v-model="reportedNo"
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
                  @click=";[reportUser(), leaveSession()]"
                  type="submit"
                />
                <q-btn class="secondary" label="취소" v-close-popup />
              </q-card-section>
            </q-card>
          </q-dialog>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { OpenVidu } from 'openvidu-browser'
import UserVideo from './video/UserVideo.vue'
import { ref } from 'vue'
import { mapActions } from 'vuex'
import { reportUser } from '@/api/meeting'

const meetingStore = 'meetingStore'

export default {
  name: 'App',

  components: {
    UserVideo
  },
  setup() {
    const question = ref(null)
    const mute = ref(true)
    const camera = ref(false)
    const level = ref(1)
    const popupMatching = ref(false)
    const popupReport = ref(false)
    const category = ref(null)
    const content = ref('')
    const reportedNo = 0
    const skipDisable = ref(true)

    return {
      question,
      mute,
      camera,
      level,
      skipDisable,
      // 질문을 단계별, 상대의 취미 별로 나눠야함
      questions: [
        [
          '시작은 언제나 자기소개입니다.',
          '간단하게 본인을 설명하세요',
          '카메라를 보고 대답해주세요',
          '지금 상대방은 억양, 어조, 높낮이 정도만 들을 수 있어요',
          '프로필 사진을 고른 이유를 물어보세요',
          '시간대에 따라 식사를 하셨는지 물어보세요'
        ],
        [
          '상대방은 OOO 을/를 좋아합니다',
          '좋아하는 영화나 음식을 물어 공감대를 형성해 보세요',
          '오늘 또는 최근의 날씨 얘기를 하는 것도 좋습니다.',
          '외모에 대한 칭찬은 싸피에서 하지 말랬습니다. 목소리는 괜찮을지도?',
          '말이 겹쳤을 때는 먼저 말하세요, 상대방도 똑같이 양보할 생각만 합니다.'
        ],
        [
          '좋아하는 스포츠를 물어보세요. 직관을 좋아하면 같이 보러갈수도 있습니다.',
          '인생 영화에 대해 물어보세요',
          '여행 중 기억에 남는 에피소드를 물어보세요'
        ]
      ],
      reportOptions: [
        { label: '폭언 및 욕설', value: 0 },
        { label: '부적절한 닉네임', value: 1 },
        { label: '부적절한 언행', value: 2 },
        { label: '성희롱 및 성추행', value: 3 },
        { label: '혐오발언', value: 4 },
        { label: '기타', value: 5 }
      ],
      reportedNo,
      category,
      content,
      popupMatching,
      popupReport,

      question_pick() {
        this.question = this.questions[this.level - 1].at(
          Math.floor(Math.random() * this.questions.length)
        )
      }
    }
  },
  data() {
    return {
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

      mySessionId: 'A',
      myUserName: Math.floor(Math.random() * 100)
      // mySessionId: vuex에서 가져온다
      // myUserName: 비디오 아래에 들어갈 유저 닉네임 (jwt 디코드)
      // 사진도 넣어야한다
    }
  },

  methods: {
    ...mapActions(meetingStore, ['enterSession', 'leftMeeting']),

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
        console.log('ssdfsdfssd')
        console.log(stream)
      })

      // On every Stream destroyed...
      this.session.on('streamDestroyed', ({ stream }) => {
        const index = this.subscribers.indexOf(stream.streamManager, 0)
        if (index >= 0) {
          this.subscribers.splice(index, 1)
        }
      })

      // On every asynchronous exception...
      this.session.on('exception', ({ exception }) => {
        console.warn(exception)
      })

      // --- Connect to the session with a valid user token ---

      // 'getToken' method is simulating what your server-side should do.
      // 'token' parameter should be retrieved and returned by your own backend
      this.getToken(this.mySessionId).then((response) => {
        this.token = response.data.token
        console.log('토큰')
        console.log(this.token)
        this.session
          .connect(this.token, { clientData: this.myUserName })
          .then(() => {
            // --- Get your own camera stream with the desired properties ---

            const publisher = this.OV.initPublisher(undefined, {
              audioSource: undefined, // The source of audio. If undefined default microphone
              videoSource: undefined, // The source of video. If undefined default webcam
              publishAudio: true, // Whether you want to start publishing with your audio unmuted or not
              publishVideo: true, // Whether you want to start publishing with your video enabled or not
              resolution: '640x480', // The resolution of your video
              frameRate: 30, // The frame rate of your video
              insertMode: 'APPEND', // How the video is inserted in the target element 'video-container'
              mirror: false, // Whether to mirror your local video or not
              filter: {
                type: 'GStreamerFilter',
                options: {
                  command: 'pitch pitch=1.8'
                }
              }
            })

            this.mainStreamManager = publisher
            this.publisher = publisher
            console.log(this.publisher)
            // --- Publish your stream ---
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
      window.addEventListener('beforeunload', this.leaveSession)
    },

    leaveSession() {
      // --- Leave the session by calling 'disconnect' method over the Session object ---
      if (this.session) this.session.disconnect()

      this.session = undefined
      this.mainStreamManager = undefined
      this.publisher = undefined
      this.subscribers = []
      this.OV = undefined

      this.leftMeeting({ sessionId: this.mySessionId, token: this.token })

      window.removeEventListener('beforeunload', this.leaveSession)
      this.$router.push('/')
    },

    updateMainVideoStreamManager(stream) {
      if (this.mainStreamManager === stream) return
      this.mainStreamManager = stream
    },

    /**
     * --------------------------
     * SERVER-SIDE RESPONSIBILITY
     * --------------------------
     * These methods retrieve the mandatory user token from OpenVidu Server.
     * This behavior MUST BE IN YOUR SERVER-SIDE IN PRODUCTION (by using
     * the API REST, openvidu-java-client or openvidu-node-client):
     *   1) Initialize a Session in OpenVidu Server (POST /openvidu/api/sessions)
     *   2) Create a Connection in OpenVidu Server (POST /openvidu/api/sessions/<SESSION_ID>/connection)
     *   3) The Connection.token must be consumed in Session.connect() method
     */

    getToken(mySessionId) {
      return this.createSession(mySessionId)
    },

    // See https://docs.openvidu.io/en/stable/reference-docs/REST-API/#post-session
    createSession(sessionId) {
      return this.enterSession(sessionId)
    },
    time() {
      return String(Date.now())
    },
    testGo() {
      this.publisher.stream
        .applyFilter('GStreamerFilter', {
          command: 'pitch pitch=1.8'
        })
        .then((filter) => {
          console.log('필터~', filter)
        })
    },
    testStop() {
      this.publisher.stream
        .removeFilter()
        .then(() => {
          console.log('Filter removed')
        })
        .catch((error) => {
          console.error(error)
        })
    },
    levelUp() {
      this.level += 1
      // this.skipDisable = false
      if (this.level === 2) {
        this.testStop()
      }
    },
    reportUser() {
      const reportNo = this.reportNo
      const category = this.category
      const content = this.content
      reportUser({ reportNo, category, content })
    }
  },
  watch: {},
  beforeMount() {
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
</style>
