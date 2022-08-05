<template>
  <div>
    <h2 :value="level">{{ level }}단계</h2>
    <div class="row q-gutter-md justify-center">
      <div>
        <q-skeleton width="500px" height="375px" />
        <div class="q-ma-sm">
          <q-img src="../../assets/profile/13.png" width="70px" />
          <span class="q-ml-sm" style="font-size: 24px">ooo님</span>
        </div>
      </div>
      <div>
        <q-skeleton width="500px" height="375px" />
        <div class="q-ma-sm">
          <q-img src="../../assets/profile/23.png" width="70px" />
          <span class="q-ml-sm" style="font-size: 24px">ㅁㅁㅁ님</span>
        </div>
      </div>
    </div>

    <div style="font-size: 24px">
      {{ question }}
    </div>

    <q-btn
      v-if="camera === true"
      color="primary"
      icon="videocam"
      label="비디오 시작"
      @click="toggle_cam(camera)"
    />
    <q-btn
      v-if="camera === false"
      color="primary"
      icon="videocam_off"
      label="비디오 해제"
      @click="toggle_cam(camera)"
    />
    <!-- @click="simulateProgress(0)" -->

    <q-btn
      v-if="mute === false"
      color="primary"
      icon="mic"
      label="음소거 해제"
      @click="toggle_mute(mute)"
    />
    <q-btn
      v-if="mute === true"
      color="primary"
      icon="mic_off"
      label="음소거 시작"
      @click="toggle_mute(mute)"
    />

    <div>
      <q-btn
        class="absolute-bottom-left"
        v-if="level < 3"
        flat
        color="warning"
        :ripple="false"
        @click=";[skipped(level), question_pick(level)]"
        @load="question_pick(level)"
      >
        <q-img
          src="../../assets/meeting/skip.png"
          width="130px"
          no-spinner
          bottom-left
        />
      </q-btn>

      <q-btn
        class="absolute-bottom-right"
        flat
        color="warning"
        to="/"
        :ripple="false"
      >
        <q-img src="../../assets/meeting/leave.png" width="130px" no-spinner />
      </q-btn>
    </div>

    <meeting-chat />
  </div>
</template>

<script>
import MeetingChat from '@/components/meeting/MeetingChat.vue'
import { ref } from 'vue'
export default {
  components: {},
  data() {
    return {
      MeetingChat
    }
  },
  setup() {
    const level = ref(1)
    const question = ref(null)
    const mute = ref(true)
    const camera = ref(false)

    return {
      level,
      question,
      mute,
      camera,
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
      question_pick() {
        this.question = this.questions[this.level - 1].at(
          Math.floor(Math.random() * this.questions.length)
        )
      },
      skipped(n) {
        this.level = n + 1
      },
      toggle_cam(n) {
        this.camera = !n
      },
      toggle_mute(n) {
        this.mute = !n
      }
    }
  },
  created() {},
  mounted() {},
  unmounted() {},
  methods: {},
  beforeMount() {
    this.question_pick()
  }
}
</script>
<style scoped></style>
