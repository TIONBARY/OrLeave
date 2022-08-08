<template>
  <menu-bar />
  <main-content />
  <!-- <main-index /> -->
</template>

<script>
// @ is an alias to /src
import MainContent from '@/components/MainContent.vue'
import MenuBar from '@/components/MenuBar.vue'
// import MainIndex from '@/components/MainIndex.vue'
export default {
  components: {
    MainContent,
    MenuBar
    // MainIndex
  }
}
</script>
