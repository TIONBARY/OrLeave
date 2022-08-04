<template>
  <div class="chat-bar">
    <q-btn icon="arrow_back_ios" style="width: 50px" />
    <q-menu
      v-model="showing"
      transition-show="slide-left"
      transition-hide="slide-right"
      persistent
    >
      <q-btn icon="arrow_forward_ios" @click="hide_chat" />
      <q-list style="min-width: 100px">
        <q-item clickable>
          <q-item-section>New tab</q-item-section>
        </q-item>
      </q-list>
    </q-menu>
  </div>
</template>

<script>
import { ref } from 'vue'
export default {
  setup() {
    const showing = ref(false)
    return {
      showing,
      hide_chat() {
        showing.value = false
      }
    }
  }
}
</script>

<style scoped></style>
