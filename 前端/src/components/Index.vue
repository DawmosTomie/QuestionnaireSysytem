<!--
程序名：网站首页
功能：对网站进行介绍
-->
<template>
  <div class="main">
    <el-container>
      <el-header>
        <header-bar></header-bar>
      </el-header>
      <el-main>
        <el-carousel height="500px">
          <el-carousel-item v-for="(item, index) in topImg" :key="index">
            <!--<div style="background-color: black;width: 100%;height: 100%;">-->
            <img :src="item.url" style="width: 100%;height: 100%;" />
            <div class="cover">
              {{ item.title }}<br />
              <span style="font-size: 35px">{{ item.subTitle }}</span>
            </div>
          </el-carousel-item>
        </el-carousel>
      </el-main>

      <el-footer class="bottom">
      </el-footer>
    </el-container>
  </div>
</template>
<script>
import HeaderBar from './HeaderBar'
export default {
  components: {
    HeaderBar,
    Map
  },
  data() {
    return {
      topImg: [
        {
          title: "免费在线问卷调查系统",
          subTitle: "Free Online Questionnaire System",
          url: "/static/images/4.jpg"
        }
        //          {
        //            'title':'简单的打开方式',
        //            'subTitle':'Simple Opening Method',
        //            'url':'/static/images/6.jpg'
        //          },
        //          {
        //            'title':'多样的数据可视化',
        //            'subTitle':'Diversified data visualization',
        //            'url':'/static/images/11.jpg'
        //          },
      ]
    };
  }
};
</script>
<style scoped>
.main {
  position: relative;
  width: 100%;
  height: 100%;
}

.bottom {
  height: 150px !important;
  background-color: #2e3e4e;
  color: #9b9ea0;
  position: relative;
  padding: 20px;
}

.demoImg {
  width: 100%;
  height: 100%;
  /*border-radius: 5px;*/
}

.cover {
  position: absolute;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.6);
  z-index: 100;
  left: 0;
  top: 0;
  color: white;
  font-size: 50px;
  line-height: 60px;
  padding-top: 180px;
}

.lj {}

.b {
  font-weight: 700;
}
</style>
