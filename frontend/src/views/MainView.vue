<template>
  <div>
    <menu-bar />
    <main-content />
  </div>
</template>

<script>
// @ is an alias to /src
import MainContent from '@/components/MainContent.vue'
import MenuBar from '@/components/MenuBar.vue'
export default {
  components: {
    MainContent,
    MenuBar
  }
}
</script>
