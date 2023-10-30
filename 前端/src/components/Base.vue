<!--
程序名：网站导航栏
功能：网站顶部导航栏
-->
<template>
  <div class="main">
    <el-container style="height: 100%;width: 100%;">
      <el-header v-show="$route.path.indexOf('/user/edit_ques/') < 0">
        <header-bar></header-bar>
      </el-header>
      <el-container style="width:100%">
        <router-view />
      </el-container>
      <div style="z-index: 2001;">
        <el-button @click="dialogVisible = !dialogVisible" class="fixed-button" icon="el-icon-question" circle>
        </el-button>
        <div style="    
          position: absolute;
          right: 15px;
          bottom: 45px;
          width: 35%;
          height: 80%;" v-show="dialogVisible">
          <Chat v-show="dialogVisible"></Chat>
        </div>
      </div>
    </el-container>
  </div>
</template>
<script>
import HeaderBar from './HeaderBar'
import Chat from './chat/chat'
export default {
  name: 'Base',
  components: {
    HeaderBar, Chat
  },
  data: function () {
    return {
      dialogVisible: false,
      isCollapse: false,
      showPath: '',
      popperClass: '{  font-size: 15px;}',
    }
  },
  methods: {
    //判断session中是否存在数据，存在将showname置为true，否则false
    state() {
      console.log(this.$store.state)
      if (this.$store.state.store.username) {
        this.showname = true
        this.username = this.$store.state.store.username
      } else {
        this.showname = false
      }
    },
  },
}
</script>
<style scoped>
.main {
  position: absolute;
  width: 100%;
  height: 100%;
}

.fixed-button {
  position: fixed;
  bottom: 10px;
  right: 10px;
}
</style>
