<template>
  <div id="stat" class="all-body">

    <el-container>
      <el-aside class="left-nav" id="statAside">
        <el-menu default-active="1" class="el-menu-vertical-demo" unique-opened style="    width: 50%;
    left: 20px;" @select="handleSelect">
          <el-menu-item index="0" @click.native="$router.push('/user/index')">
            <i class="el-icon-back nav-icon"></i>
            <br>
            <span slot="title">返回</span>
          </el-menu-item>
          <el-menu-item index="1">
            <i class="el-icon-time nav-icon"></i>
            <br>
            <span slot="title">统计&分析</span>
          </el-menu-item>

          <el-menu-item index="2">
            <i class="el-icon-s-data nav-icon"></i>
            <br>
            <span slot="title">查看答卷</span>
          </el-menu-item>

          <el-menu-item index="3">
            <i class="el-icon-document nav-icon"></i>
            <br>
            <span slot="title">数据大屏</span>
          </el-menu-item>
        </el-menu>
      </el-aside>
      <el-container>
        <component :is="componentObj[activeIdx]" :questionnaireId="$route.params.questionnaireId"></component>
      </el-container>
    </el-container>
  </div>
</template>

<script>
import DataShowVue from '@/components/questionnaire/DataShow.vue';
import CommitHis from '@/views/qn-stat/commit-his.vue'
import DataShow from '@/views/qn-stat/DataShow.vue'
export default {
  name: "QnStat",
  components: {
    DataShowVue,
    CommitHis,
    DataShow
  },
  data() {
    return {
      activeIdx: '1',
      componentObj: {
        '1': DataShowVue,
        '2': CommitHis,
        '3': DataShow
      }
    };
  },
  methods: {

    handleSelect(key) {
      this.activeIdx = key
      switch (this.activeIdx) {
        case '1':
          break
        case '2':
          break
        case '3':
          break
      }
    },
  },
}

</script>

<style scoped>
#stat {
  /*height: 1000px;*/
  width: 100%;
  min-width: 1200px;
  /* background-color: rgb(241, 241, 241); */
}

#statAside {
  width: 340px;
  /* background-color: antiquewhite; */
  min-width: 325px;
}

.el-aside {
  width: 120px !important;
  /*margin-left: 50px;*/
}

.el-menu-item {
  height: 120px !important;
}

.left-nav .el-menu-item {
  font-size: 16px;
  padding: 20px;
  text-align: center !important;
}

.left-nav .el-menu-item,
.el-submenu__title {
  height: 120px;
  line-height: 30px;
}

.left-nav .nav-icon {
  font-size: 35px;
}

.all-body {
  height: 100%;
}

a {
  text-decoration: none;
}

.el-container {
  margin-bottom: 20px;
}
</style>