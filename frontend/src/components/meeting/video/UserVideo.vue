<template>
  <div v-if="streamManager">
    <ov-video :stream-manager="streamManager" />
    <div>
      <p>{{ clientData }}</p>
    </div>
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
    streamManager: Object
  },

  computed: {
    clientData() {
      const { clientData } = this.getConnectionData()
      return clientData
    }
  },

  methods: {
    getConnectionData() {
      console.log(this.streamManager.stream)
      const { connection } = this.streamManager.stream
      const bracketIdx = connection.data.indexOf('}')
      const data = connection.data.substring(0, bracketIdx + 1)
      console.log(data)
      return JSON.parse(data)
    }
  }
}
</script>
