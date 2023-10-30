<!--
程序名：网站登录页面
功能：进入网站的入口
-->
<template>
  <div class="login login_back">
    <el-container>
      <el-header></el-header>
      <el-main>
        <div class="main_login">
          <el-row type="flex" justify="center">
            <SignIn role="admin"></SignIn>
          </el-row>
        </div>
      </el-main>
      <el-footer style="line-height: 60px;">
        <div class="footer-certification"><a data-tj-name="b_bottom_icp_click" rel="noopener norefferrer"
            href="http://beian.miit.gov.cn/" target="_blank" class="icp">&nbsp;&nbsp;&nbsp;ICP备案 粤B2-20090191-18</a>
          <span class="copyright"><a href="http://www.beian.gov.cn/portal/registerSystemInfo?recordcode=44010602006299"
              rel="noopener norefferrer" target="_blank"><img src="https://mimg.127.net/p/images/logo/gonganlogo.png"
                alt="公安" style="position: relative;top: 4px;vertical-align: baseline;"> 粤公网安备 44010602006299
            </a></span><span class="copyright"><span>增值电信业务许可证</span> <a class="link"
              href="https://cms-bucket.ws.126.net/2019/05/30/f0b8f62f908a411e944aa93f0727272d.jpeg"
              target="_blank">粤B2-20090191</a> <a class="link"
              href="https://cms-bucket.ws.126.net/2019/06/12/ff11d8c212a24203adcef9a83317edd3.jpeg"
              target="_blank">&nbsp;B2-20090058</a></span> <span class="copyright">（中文邮箱第一品牌 数据来源：艾媒邮箱报告）</span>
        </div>
      </el-footer>
    </el-container>

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
  width: 320px;
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
  