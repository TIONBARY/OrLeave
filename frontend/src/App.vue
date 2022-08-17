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
h1 {
  font-size: 200% !important;
}
h2 {
  font-size: 175% !important;
}
.basic-container {
  background-color: #e5edb8;
  border-radius: 10px;
  padding: 30px;
  margin: auto;
}

.App {
  position: absolute;
  width: 100%;
  min-height: 100vh;
  font-family: 'GowunDodum-Regular', sans-serif !important;
  font-size: 130% !important;
  background-color: #f3f1eb;
}

/* FONT */
@font-face {
  font-family: 'GowunDodum-Regular';
  src: url('assets/font/GowunDodum-Regular.ttf') format('woff');
  font-weight: normal;
  font-style: normal;
}

.primary {
  background: #6b7a16 !important;
  color: white !important;
}
.secondary {
  background: white !important;
  color: #6b7a16 !important;
}
.negative {
  background: #eed7ce !important;
  color: #6b7a16 !important;
}

a {
  text-decoration: none;
  font-size: 80%;
}

* {
  font-family: 'GowunDodum-Regular';
}
</style>
