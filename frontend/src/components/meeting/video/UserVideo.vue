<template>
  <div v-if="streamManager && level === 1">
    <ov-video :stream-manager="streamManager" style="filter: blur(14px)" />
  </div>
  <div v-if="streamManager && level === 2">
    <ov-video :stream-manager="streamManager" style="filter: blur(5px)" />
  </div>
  <div v-if="streamManager && level === 3">
    <ov-video :stream-manager="streamManager" />
  </div>
</template>

<script>
import OvVideo from './OvVideo'

export default {
  name: 'UserVideo',

  components: {
    OvVideo
  },

  props: {
    streamManager: Object,
    level: Number
  },

  computed: {
    clientData() {
      const { clientData } = this.getConnectionData()
      return clientData
    }
  },

  methods: {
    getConnectionData() {
      const { connection } = this.streamManager.stream
      const bracketIdx = connection.data.indexOf('}')
      const data = connection.data.substring(0, bracketIdx + 1)
      return JSON.parse(data)
    }
  }
}
</script>
