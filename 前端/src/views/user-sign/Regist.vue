<!--
程序名：网站登录页面
功能：进入网站的入口
-->
<template>
  <div class="login login_back">
    <div class="main_login">
      <el-tabs v-model="curTab">
        <el-tab-pane label="用户注册" name="user">
          <el-row type="flex" justify="center">
            <SignOut role="user"></SignOut>
          </el-row>
        </el-tab-pane>
        <el-tab-pane label="租户注册" name="tenet">
          <el-row type="flex" justify="center">
            <SignOut role="tenet"></SignOut>
          </el-row>
        </el-tab-pane>
        <el-tab-pane label="答者注册" name="anwserer">
          <el-row type="flex" justify="center">
            <SignOut role="anwserer"></SignOut>
          </el-row>
        </el-tab-pane>
      </el-tabs>
    </div>
  </div>
</template>
<script>
import SignOut from '@/components/sign_up'
export default {
  name: 'Regist',
  components: {
    SignOut
  },
  data() {
    return {
      curTab: 'user'
    }
  }
}
</script>

<style scoped>
.login_back {
  align-items: center;
  background-color: #e9e9e9;
  background: url('../../assets/home.png');
  background-attachment: fixed;
  /* */
  background-position: center;
  background-repeat: no-repeat;
  background-size: cover;
  display: grid;
  height: 100vh;
  place-items: center;
}

/* 主页面样式 */
.login {
  position: absolute;
  /*绝对定位*/
  width: 100%;
  height: 100%;
  background-color: #e4e7ed;
}

/* 标题样式 */
.title {
  font-size: large;
  /*字体大小*/
  font-weight: bolder;
  /*字体加粗*/
  text-align: center;
  color: black;
}

/* 登录部分div样式 */
.main_login {
  position: absolute;
  left: 48%;
  top: 40%;
  width: 500px;
  margin: -190px 0 0 -190px;
  padding: 40px;
  border-radius: 5px;
  /*圆角边框*/
  background: #f2f6fc;
}

/* 表单样式 */
.el-form {
  padding-top: 5%;
  padding-left: 10%;
  padding-right: 10%;
  width: 280px;
}

/* .el-row标签样式 */
.el-row {
  height: 100%;
  width: 100%;
}

/* 文字链接div样式 */
.link {
  margin-top: -13%;
  text-align: center;
  /* 文本居中 */
  margin-left: -5%;
}

/* 文字链接样式 */
.el-link {
  margin-left: 8px;
  line-height: 20px;
  font-size: 8px;
}
</style>
