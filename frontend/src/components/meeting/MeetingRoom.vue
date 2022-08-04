<template>
  <div>
    <h2>1단계</h2>
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
    <div class="row justify-between">
      <q-btn flat color="warning" to="/">
        <q-img src="../../assets/meeting/skip.png" width="130px" />
      </q-btn>

      <q-btn flat color="primary" to="/">
        <q-img src="../../assets/meeting/leave.png" width="130px" no-spinner />
      </q-btn>
    </div>
  </div>
</template>
<style scoped></style>
