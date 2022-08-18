<template>
  <q-dialog persistent>
    <q-card class="popup">
      <q-bar class="popup-bar"> </q-bar>
      <q-card-section class="popup-text"
        ><q-circular-progress
          indeterminate
          size="40px"
          :thickness="0.4"
          font-size="50px"
          color="lime"
          track-color="grey-3"
          center-color="grey-8"
          class="q-ma-md"
          animation-speed="1"
        />
        <br /><q-img
          :src="require('../../assets/profile/' + imageNo + '.png')"
          width="30px"
          style="margin-right: 10px"
        />{{ modalContent }}</q-card-section
      >
      <div class="full-width row justify-evenly q-mb-lg">
        <q-btn
          class="primary"
          :disable="this.disable"
          @click="$emit('confirm'), $emit('changeDisable')"
          >수락</q-btn
        >
        <q-btn
          class="secondary"
          :disable="this.disable"
          @click="$emit('close'), $emit('changeDisable')"
        >
          거절
        </q-btn>
      </div>
    </q-card>
  </q-dialog>
</template>
<script>
export default {
  props: {
    modalContent: String,
    disable: Boolean,
    imageNo: String
  },
  emits: ['confirm', 'close', 'changeDisable']
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
  text-align: center;
}
</style>
