<!--
程序名：Vue页面主入口
功能：Vue页面主入口
-->
<template>
  <div id="app">
    <router-view />
  </div>
</template>
<script>
export default {
  name: 'App'
}
</script>

<style>
#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  font-size: 15px;
  color: #2c3e50;
}

html,
body,
#app {
  margin: 0;
  padding: 0;
  height: 100%;
}

button.el-button--primary > span{
  color:white
}
button.el-button--success > span{
  color:white
}
button.el-button--info > span{
  color:white
}
</style>
