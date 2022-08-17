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
            <button class="btn btn-lg btn-success" @click="joinSession()">
              Join!
            </button>
          </p>
        </div>
      </div>
    </div>

    <div id="session" v-if="session">
      <div id="session-header">
        <h1 id="session-title">{{ mySessionId }}</h1>
        <input
          class="btn btn-large btn-danger"
          type="button"
          id="buttonLeaveSession"
          @click="leaveSession"
          value="Leave session"
        />
      </div>
      <div id="main-video" class="col-md-6">
        <user-video :stream-manager="mainStreamManager" />
      </div>
      <div id="video-container" class="col-md-6">
        <user-video
          :stream-manager="publisher"
          @click="updateMainVideoStreamManager(publisher)"
        />
        <user-video
          v-for="sub in subscribers"
          :key="sub.stream.connection.connectionId"
          :stream-manager="sub"
          @click="updateMainVideoStreamManager(sub)"
        />
      </div>
      <button @click="toggleAudio()">
        <template v-if="isOn.audio">음소거</template>
        <template v-else>음소거 해제</template>
      </button>
      <button @click="toggleVideo()">
        <template v-if="isOn.video">비디오 끄기</template>
        <template v-else>비디오 시작</template>
      </button>
      <button @click="testGo()">go</button>
    </div>
  </div>
</template>

<script>
import axios from 'axios'
import { OpenVidu } from 'openvidu-browser'
import UserVideo from './video/UserVideo.vue'

import { mapActions } from 'vuex'
const meetingStore = 'meetingStore'

axios.defaults.headers.post['Content-Type'] = 'application/json'

const OPENVIDU_SERVER_URL = 'https://i7a502.p.ssafy.io:8443'
const OPENVIDU_SERVER_SECRET = 'cityview1202502'

export default {
  name: 'App',

  components: {
    UserVideo
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

      mySessionId: 'SessionA',
      myUserName: 'Participant' + Math.floor(Math.random() * 100)
    }
  },

  methods: {
    ...mapActions(meetingStore, ['enterSession']),

    toggleAudio() {
      this.isOn.audio = !this.isOn.audio
      this.publisher.publishAudio(this.isOn.audio)
    },
    toggleVideo() {
      this.isOn.video = !this.isOn.video
      this.publisher.publishVideo(this.isOn.video)
    },

    testGo() {
      this.publisher.stream.applyFilter('FaceOverlayFilter').then((filter) => {
        filter.execMethod('setOverlayedImage', {
          uri: 'https://cdn.pixabay.com/photo/2013/07/12/14/14/derby-148046_960_720.png',
          offsetXPercent: '-0.2F',
          offsetYPercent: '-0.8F',
          widthPercent: '1.3F',
          heightPercent: '1.0F'
        })
      })
      console.log(this.publisher.stream)
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
      this.session.on('exception', ({ exception }) => {
        console.warn(exception)
      })

      // --- Connect to the session with a valid user token ---

      // 'getToken' method is simulating what your server-side should do.
      // 'token' parameter should be retrieved and returned by your own backend
      this.getToken(this.mySessionId).then((response) => {
        const token = response.data.token
        console.log(token)
        this.session
          .connect(token, { clientData: this.myUserName })
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
              mirror: false // Whether to mirror your local video or not
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

      window.removeEventListener('beforeunload', this.leaveSession)
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

    // See https://docs.openvidu.io/en/stable/reference-docs/REST-API/#post-connection
    createToken(sessionId) {
      console.log('new')
      console.log(sessionId)
      return new Promise((resolve, reject) => {
        axios
          .post(
            `${OPENVIDU_SERVER_URL}/openvidu/api/sessions/${sessionId}/connection`,
            {},
            {
              auth: {
                username: 'OPENVIDUAPP',
                password: OPENVIDU_SERVER_SECRET
              }
            }
          )
          .then((response) => response.data)
          .then((data) => resolve(data.token))
          .catch((error) => reject(error.response))
      })
    }
  }
}
</script>

<style scoped></style>
