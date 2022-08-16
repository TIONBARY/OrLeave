<template>
  <div class="item row items-center justify-center">
    <div>
      <q-img src="../assets/error.png" width="300px" />
      <h2 class="q-mt-xs"><strong>오류가 발생했습니다.</strong></h2>
      <q-btn class="primary" label="돌아가기" @click="this.$router.push('/')" />
    </div>
  </div>
</template>
