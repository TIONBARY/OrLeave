<template>
  <div>
    <h1 class="q-ma-xs">
      <div class="dev-title text-center">개발자 소개</div>
    </h1>
    <q-card bordered class="team-card">
      <q-card-section>
        <div class="text-h6">참여한 이들</div>
        <div class="text-subtitle2">SSAFY 7기 서울 5반 2팀</div>
      </q-card-section>

      <q-separator inset />

      <q-card-section>
        <div class="row justify-center q-gutter-md">
          <q-card bordered class="member-card">
            <q-card-section horizontal>
              <q-card-section>
                <q-avatar size="60px">
                  <q-img src="../assets/profile/10.png" />
                </q-avatar>
                <div>김시언</div>
              </q-card-section>
              <q-separator vertical inset />
              <q-card-section class="text-left column">
                <div>Frontend</div>
                <div>가나</div>
                <q-space />
                <div class="q-gutter-sm">
                  <q-img src="../assets/main/github.png" width="20px" />
                  <q-img src="../assets/main/instagram.png" width="20px" />
                  <q-img src="../assets/main/tstory.png" width="20px" />
                </div>
              </q-card-section>
            </q-card-section>
          </q-card>
          <q-card bordered class="member-card">
            <q-card-section horizontal>
              <q-card-section>
                <q-avatar size="60px">
                  <q-img src="../assets/profile/25.png" />
                </q-avatar>
                <div>정승욱</div>
              </q-card-section>
              <q-separator vertical inset />
              <q-card-section class="text-left column">
                <div>Frontend</div>
                <div>다라</div>
                <q-space />
                <div class="q-gutter-sm">
                  <q-img src="../assets/main/github.png" width="20px" />
                  <q-img src="../assets/main/instagram.png" width="20px" />
                  <q-img src="../assets/main/tstory.png" width="20px" />
                </div>
              </q-card-section>
            </q-card-section>
          </q-card>
          <q-card bordered class="member-card">
            <q-card-section horizontal>
              <q-card-section>
                <q-avatar size="60px">
                  <q-img src="../assets/profile/2.png" />
                </q-avatar>
                <div>정지은</div>
              </q-card-section>
              <q-separator vertical inset />
              <q-card-section class="text-left column">
                <div>Frontend</div>
                <div>마바</div>
                <q-space />
                <div class="q-gutter-sm">
                  <q-img src="../assets/main/github.png" width="20px" />
                  <q-img src="../assets/main/instagram.png" width="20px" />
                  <q-img src="../assets/main/tstory.png" width="20px" />
                </div>
              </q-card-section>
            </q-card-section>
          </q-card>
          <q-card bordered class="member-card">
            <q-card-section horizontal>
              <q-card-section>
                <q-avatar size="60px">
                  <q-img src="../assets/profile/3.png" />
                </q-avatar>
                <div>박상수</div>
              </q-card-section>
              <q-separator vertical inset />
              <q-card-section class="text-left column">
                <div>Backend</div>
                <div>사아</div>
                <q-space />
                <div class="q-gutter-sm">
                  <q-img src="../assets/main/github.png" width="20px" />
                  <q-img src="../assets/main/instagram.png" width="20px" />
                  <q-img src="../assets/main/tstory.png" width="20px" />
                </div>
              </q-card-section>
            </q-card-section>
          </q-card>
          <q-card bordered class="member-card">
            <q-card-section horizontal>
              <q-card-section>
                <q-avatar size="60px">
                  <q-img src="../assets/profile/5.png" />
                </q-avatar>
                <div>윤지환</div>
              </q-card-section>
              <q-separator vertical inset />
              <q-card-section class="text-left column">
                <div>Backend</div>
                <div>자차</div>
                <q-space />
                <div class="q-gutter-sm">
                  <q-img src="../assets/main/github.png" width="20px" />
                  <q-img src="../assets/main/instagram.png" width="20px" />
                  <q-img src="../assets/main/tstory.png" width="20px" />
                </div>
              </q-card-section>
            </q-card-section>
          </q-card>
          <q-card bordered class="member-card">
            <q-card-section horizontal>
              <q-card-section>
                <q-avatar size="60px">
                  <q-img src="../assets/profile/4.png" />
                </q-avatar>
                <div>허상</div>
              </q-card-section>
              <q-separator vertical inset />
              <q-card-section class="text-left column">
                <div>Backend</div>
                <div>카타</div>
                <q-space />
                <div class="q-gutter-sm">
                  <q-img src="../assets/main/github.png" width="20px" />
                  <q-img src="../assets/main/instagram.png" width="20px" />
                  <q-img src="../assets/main/tstory.png" width="20px" />
                </div>
              </q-card-section>
            </q-card-section>
          </q-card>
        </div>
      </q-card-section>
    </q-card>
  </div>
</template>

<script>
export default {}
</script>

<style scoped>
* {
  white-space: nowrap;
}
.team-name {
  width: 50%;
  position: relative;
  left: 30px;
}
.team-card {
  top: 50px;
  max-width: 850px;
  margin: auto;
}
.member-card {
  background: #e5edb8;
  min-width: 250px;
}
</style>
