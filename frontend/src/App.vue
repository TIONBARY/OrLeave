<template>
  <router-view class="App" />
</template>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}

.basic-container {
  background-color: #e5edb8;
  border-radius: 10px;
  padding: 20px;
}

.App {
  position: absolute;
  height: 100vh;
  width: 100vw;
  font-family: 'GowunDodum-Regular', sans-serif !important;
  font-size: 130% !important;
  background-color: #f3f1eb;
}

.w10 {
  width: 10%;
}

.w20 {
  width: 20%;
}

.w30 {
  width: 30%;
}

.w40 {
  width: 40%;
}

/* FONT */
@font-face {
  font-family: 'GowunDodum-Regular';
  src: url('assets/font/GowunDodum-Regular.ttf') format('woff');
  font-weight: normal;
  font-style: normal;
}
</style>
