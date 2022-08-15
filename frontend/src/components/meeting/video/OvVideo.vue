<template>
  <video autoplay width="500" />
</template>

<script>
export default {
  name: 'OvVideo',

  props: {
    streamManager: Object
  },

  mounted() {
    this.streamManager.addVideoElement(this.$el)
  }
}
</script>
