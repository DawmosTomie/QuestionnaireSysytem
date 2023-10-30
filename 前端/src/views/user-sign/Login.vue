<!--
程序名：网站登录页面
功能：进入网站的入口
-->
<template>
  <div class="login_container">
    <el-header>
        <div class="logo">
          <i class="el-icon-s-opportunity" style="font-size:23px;color:#409EFF;"></i>
          <span style="font-size: 20px;margin-left: 5px;color: #606266">高校满意度调查问卷系统</span>
        </div>
      </el-header>
    <div class="login_box">
    <el-container style="height: 100%;width: 100%;">

      <el-main>
        <div>
          <div class="main_login">
            <el-tabs v-model="curTab" tab-position="left">
              <el-tab-pane label="个人登录" name="user">
                <el-row type="flex" justify="center">
                  <SignIn role="user"></SignIn>
                </el-row>
              </el-tab-pane>
              <el-tab-pane label="答者登录" name="anwserer">
                <el-row type="flex" justify="center">
                  <SignIn role="anwserer"></SignIn>
                </el-row>
              </el-tab-pane>
            </el-tabs>
          </div>
        </div>
      </el-main>

    </el-container>
  </div>
  </div>

</template>
<script>
import SignIn from '@/components/sign_in'
export default {
  name: 'Login',
  components: {
    SignIn,
  },
  data() {
    return {
      curTab: 'user',
    }
  },
  mounted() {

  },
}
</script>

<style scoped>

.login_box {
  overflow: hidden;
  background-color: white;
  padding: 40px 30px 30px 30px;
  border-radius: 10px;
  position: absolute;
  top: 50%;
  left: 50%;
  width: 400px;
  -webkit-transform: translate(-50%, -50%);
  -moz-transform: translate(-50%, -50%);
  -ms-transform: translate(-50%, -50%);
  -o-transform: translate(-50%, -50%);
  transform: translate(-50%, -50%);
  -webkit-transition: -webkit-transform 300ms, box-shadow 300ms;
  -moz-transition: -moz-transform 300ms, box-shadow 300ms;
  transition: transform 300ms, box-shadow 300ms;
  box-shadow: 5px 10px 10px rgba(2, 128, 144, 0.2);
}
.login_box::before, .login_box::after {
  content: "";
  position: absolute;
  width: 600px;
  height: 600px;
  border-top-left-radius: 40%;
  border-top-right-radius: 45%;
  border-bottom-left-radius: 35%;
  border-bottom-right-radius: 40%;
  z-index: -1;
}
.login_box::before {
  left: 40%;
  bottom: -130%;
  background-color: rgba(69, 105, 144, 0.15);
  -webkit-animation: wawes 6s infinite linear;
  -moz-animation: wawes 6s infinite linear;
  animation: wawes 6s infinite linear;
}
.login_box::after {
  left: 35%;
  bottom: -125%;
  background-color: rgba(2, 128, 144, 0.2);
  -webkit-animation: wawes 7s infinite;
  -moz-animation: wawes 7s infinite;
  animation: wawes 7s infinite;
}
.el-input {
bottom: 30px;
   font-family: "Asap", sans-serif;
  display: block;
  border-radius: 5px;
  font-size: 16px;
  background: white;
  width: 90%;
  border: 0;
  padding: 0px 0px;
  margin: 5px 0px;
}
.login_box > button:hover {
  background-color: #f24353;
}

@-webkit-keyframes wawes {
  from {
    -webkit-transform: rotate(0);
  }
  to {
    -webkit-transform: rotate(360deg);
  }
}
@-moz-keyframes wawes {
  from {
    -moz-transform: rotate(0);
  }
  to {
    -moz-transform: rotate(360deg);
  }
}
@keyframes wawes {
  from {
    -webkit-transform: rotate(0);
    -moz-transform: rotate(0);
    -ms-transform: rotate(0);
    -o-transform: rotate(0);
    transform: rotate(0);
  }
  to {
    -webkit-transform: rotate(360deg);
    -moz-transform: rotate(360deg);
    -ms-transform: rotate(360deg);
    -o-transform: rotate(360deg);
    transform: rotate(360deg);
  }
}
a {
  text-decoration: none;
  color: rgba(255, 255, 255, 0.6);
  position: absolute;
  right: 5px;
  bottom: 10px;
  font-size: 12px;
}
.login_container {
background: linear-gradient(
    to right bottom,
    rgba(255, 182, 185, 0.7),
    rgba(97, 192, 191, 0.3)
  );
  font-family: "Asap", sans-serif;
    height: 100%;
}
.login_box {
    width: 30%;
    height: 50%;
    background-color: #fff;
    border-radius: 10px;
    position: absolute;
    /* 下面三句代码是时登录框处于页面居中的位置 */
    left: 50%;
    top: 50%;
    transform: translate(-50%, -50%);
}
.login_form {
    position: absolute;
    /* 距离底部对齐 */
    bottom: 10px;
    width: 500%;
    padding: 0 20px;
    /* 转换为CSS3盒子，避免添加padding和border时撑大盒子 */
    box-sizing: border-box;
}
.el-button--primary {
  position: absolute;
  center: 50%;
  bottom: 0px;
 font-family: "Asap", sans-serif;
  cursor: pointer;
  color: #fff;
  font-size: 16px;
  text-transform: uppercase;
  width: 80px;
  border: 0;
  padding: 10px 0;
  margin-top: 10px;
  margin-left: -5px;
  border-radius: 5px;
  background-color: #ffb6b9;
  -webkit-transition: background-color 300ms;
  -moz-transition: background-color 300ms;
  transition: background-color 300ms;
}
.el-button--info {
  position: absolute;
  left: 75%;
  bottom: 0px;
  font-family: "Asap", sans-serif;
  cursor: pointer;
  color: #fff;
  font-size: 16px;
  text-transform: uppercase;
  width: 80px;
  padding: 10px 0;
  margin-top: 10px;
  margin-left: -5px;
  border-radius: 5px;
  border: 0;
  background-color: #bbded6;
  -webkit-transition: background-color 300ms;
  -moz-transition: background-color 300ms;
  transition: background-color 300ms;
}

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

.login_back {
  align-items: center;
  background-color: #e9e9e9;
  background: url('../../assets/images/largescreen/home-bg.png');
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
.main_left {
  position: absolute;
  left: 16%;
  top: 50%;
  width: 500px;
  margin: -190px 0 0 100px;
  padding: 40px;
  height: 395px;
  border-radius: 5px;
  background: #f2f6fccf;
}

.main_login {
  position: absolute;
  left: -25%;
  top: 55%;
  width: 500px;
  height: 395px;
  margin: -190px 0 0 100px;
  padding: 40px;
  border-radius: 5px;
  /*圆角边框*/
  background: transparent;
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
