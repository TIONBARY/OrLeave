<template>
  <div>
    <br />
    <div>매 칭 중 ...</div>
    <br />
    <div class="row q-gutter-md justify-center items-center">
      <div id="user-video" class="video-container"></div>
      <div>
        <q-img src="../../assets/profile/4.png" width="30%" />
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
                    <td width="70%" class="q-px-ml">20 - 30</td>
                  </tr>
                  <q-separator inset />
                  <tr>
                    <td>거리</td>
                    <td>10km - 20km</td>
                  </tr>
                  <tr>
                    <td>음주</td>
                    <td>안함</td>
                  </tr>
                  <tr>
                    <td>흡연</td>
                    <td>비흡연</td>
                  </tr>
                  <tr>
                    <td>종교</td>
                    <td>상관없음</td>
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
                  <div class="text-center">음소거</div>
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
                  <div class="text-center">비디오 끄기</div>
                </div>
              </template>
              <template v-else>
                <div class="row items-center no-wrap" style="width: 100px">
                  <q-icon left name="videocam_off" />
                  <div class="text-center">비디오 켜기</div>
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
    <div>
      <q-btn label="매칭 취소" color="secondary"></q-btn>
    </div>
    <button @click="savePublisher()">SAVE SETTING</button>
  </div>
</template>

<script>
import { OpenVidu } from 'openvidu-browser'
import { onMounted } from 'vue'

import { mapState, mapActions } from 'vuex'
const meetingStore = 'meetingStore'

export default {
  name: 'App',

  components: {},

  data() {
    onMounted(() => {
      if (this.OV === undefined) this.joinSession()
    })
    return {
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

      alert: false
    }
  },
  computed: {
    ...mapState(meetingStore, ['myPublisher'])
  },
  methods: {
    ...mapActions(meetingStore, ['enterSession', 'setPublisher']),

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
            console.log('Old publisher unpublished')
            this.publisher = newPublisher
            this.session.publish(this.publisher).then(() => {
              console.log('New publisher published')
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
            console.log('Old publisher unpublished')
            this.publisher = newPublisher
            this.session.publish(this.publisher).then(() => {
              console.log('New publisher published')
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
    savePublisher() {
      this.setPublisher({
        audioSource: this.audioId,
        videoSource: this.videoId,
        publishAudio: this.isOn.audio,
        publishVideo: this.isOn.video
      })
      console.log({
        audioSource: this.audioId,
        videoSource: this.videoId,
        publishAudio: this.isOn.audio,
        publishVideo: this.isOn.video
      })
    },

    // 로비 입장시 실행돼야 하는 함수
    joinSession() {
      this.OV = new OpenVidu()
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
      window.removeEventListener('beforeunload', this.leaveSession)
    },
    getToken(mySessionId) {
      return this.createSession(mySessionId)
    },
    createSession(sessionId) {
      return this.enterSession(sessionId)
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
