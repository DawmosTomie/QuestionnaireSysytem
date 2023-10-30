<template>
  <div>
    <el-container>
      <el-header>
        <div class="logo" @click="toIndex">
          <i class="el-icon-s-opportunity" style="font-size:23px;color:#409EFF;"></i>
          <span style="font-size: 25px;margin-left: 5px;color: #606266">高校满意度调查问卷系统</span>
        </div>
        <div style="float: right;margin-right: 50px;line-height: 60px;">
          <!-- 未登录时显示 -->
          <template v-if="!username">
            <el-button type="primary" style="font-size: 15px;" @click="toLogin">登录</el-button>
            <el-button type="primary" style="font-size: 15px;" @click="toRegist">注册</el-button>
          </template>
          <!-- 登录时显示 -->
          <template v-else>
            <!-- 登录成功，显示用户名 -->
            <i class="el-icon-user-solid"></i>
            <el-dropdown trigger="click" @command="handleCommand">
              <span style="font-size:18px">
                {{ username }}
                <i class="el-icon-arrow-down el-icon--right"></i>
              </span>
              <!-- 退出登录 -->
              <el-dropdown-menu slot="dropdown">
                <template
                  v-if="$store.state.store.roleId=='user'"
                >
                  <el-dropdown-item command="manageAnwserer">答者管理</el-dropdown-item>
                  <el-dropdown-item command="myQuestions">我的问卷</el-dropdown-item>
                  <el-dropdown-item command="UserPortrait">用户画像</el-dropdown-item>
                </template>
                <template
                  v-if="$store.state.store.roleId=='admin'"
                >
                  <el-dropdown-item command="bigData">数据大屏</el-dropdown-item>
                </template>
                <el-dropdown-item command="logout">退出登录</el-dropdown-item>
              </el-dropdown-menu>
            </el-dropdown>
          </template>
        </div>
      </el-header>

      <el-dialog :visible.sync="userAnaly">
        <img src="../assets/images/userAnaly.png" style="width: 80%;"/>
      </el-dialog>
    </el-container>
  </div>
</template>
<script>
export default {
  data() {
    return {
      userAnaly:false
    }
  },
  methods: {
    toIndex() {
      this.$router.push({ path: '/index' })
    },
    toLogin() {
      this.$router.push({ path: '/login' })
    },
    toRegist() {
      this.$router.push({ path: '/regist' })
    },
    //下拉菜单操作
    handleCommand(command) {
      if ('logout' == command) {
        this.exit()
      } else if ('manageAnwserer' == command) {
        this.$router.push('/user/anwserers')
      } else if ('myQuestions' == command) {
        this.$router.push('/user/index')
      } else if ('bigData' == command) {
        this.$router.push('/bigData')
      } else if('UserPortrait' == command){
        this.userAnaly = true
      }
    },
    //登出
    exit() {
      this.$store.dispatch('LogOut').then(() => {
        location.reload() // 为了重新实例化vue-router对象 避免bug
      })
    },
  },
  computed: {
    username: function () {
      return this.$store.state.store.username
    },
  },
}
</script>
<style scoped>
.logoImg {
  width: 30px;
  vertical-align: middle;
}
/* logo框样式 */
.logo {
  height: 60px;
  display: inline-block;
  line-height: 60px;
  font-size: 20px;
  position: absolute;
  left: 100px;
  color: #303133;
  cursor: pointer;
}
.el-header {
  border-bottom: 2px solid #409eff;
  background-color: white;
}
</style>