<!--
程序名：问卷设计主页面
功能：对问卷进行设计
-->
<template>
  <div class="home" id="projectHome">
    <el-row :gutter="30" >
      <el-col :span="8" style="margin-left: 18px">
        <!--<el-card class="sidebar-card" v-for="item in detail" :key="item.id">-->
        <el-card>
          <el-row style="margin-top: 5px;margin-bottom: 5px;" :gutter="20">
            <el-col :span="30">
              <el-button size="small" type="primary" style="margin-left: 10px;height: 40px;"
                @click="projectDialog = true, editProject = {}">添加项目</el-button>
            </el-col>
            <el-col :span="30">
              <el-input placeholder="输入项目名称进行过滤" v-model="filterText"></el-input>
            </el-col>
          </el-row>

          <!--左侧导航栏-->
          <span class="mytree">
            <el-tree :data="wjList" :render-content="renderContent" :props="treeProp" @node-click="handleNodeClick"
              ref="tree" :filter-node-method="filterNode"></el-tree>
          </span>
        </el-card>
      </el-col>

      <el-col :span="14">
        <!--标签页-->
        <el-tabs type="border-card" v-model="activeName">
          <el-tab-pane label="问卷设计" name="wjsj">
            <!--内容区域-->
            <div class="content">
              <div v-show="curQuestionnaire.questionnaireId == 0 || curQuestionnaire.questionnaireId == null">请先选择问卷</div>
              <Design ref="design"
                v-show="curQuestionnaire.questionnaireId != 0 && curQuestionnaire.questionnaireId != null" ></Design>
            </div>
          </el-tab-pane>
          <el-tab-pane label="回答统计" name="hdtj">
            <div class="content" ref="pdf">
              <div v-show="curQuestionnaire.questionnaireId == 0 || curQuestionnaire.questionnaireId == null">请先选择问卷
              </div>
              <Data-show ref="dataShow"
                v-show="curQuestionnaire.questionnaireId != 0 && curQuestionnaire.questionnaireId != null" ></Data-show>
            </div>
          </el-tab-pane>
        </el-tabs>
        <el-button size="small" type="success" style="position: absolute;right:40px;top:50px;" @click="previewWj"><i
            class="el-icon-view"></i>预览</el-button>
        <el-button size="small" type="primary" style="position: absolute;right:40px;top:5px;" @click="shareWj"><i
            class="el-icon-share"></i>分享</el-button>
      </el-col>
    </el-row>

    <!--添加项目弹窗-->
    <el-dialog title="项目" :visible.sync="projectDialog" :close-on-click-modal="false" class="dialog">
      <el-form ref="formProject" :model="editProject" label-width="80px">
        <el-form-item label="项目名称" style="width: 100%;" required>
          <el-input v-model="editProject.projectName" placeholder="请输入项目名称"></el-input>
        </el-form-item>
        <el-form-item label="项目说明" style="width: 100%;">
          <el-input v-model="editProject.projectContent" type="textarea" placeholder="请输入项目说明" rows="5"></el-input>
        </el-form-item>
        <el-form-item label="项目时间" style="width: 100%;">
          <!--<template v-if="editProject.creationDate">
                <label class="label">{{ editProject.creationDate }}</label>
              </template>
              <template v-else>-->
                <el-date-picker type="datetime" value-format="yyyy-MM-dd HH:mm:ss" placeholder="开始日期"
                  v-model="editProject.creationDate" style="width: 100%;"></el-date-picker>
             <!-- </template>-->
        </el-form-item>
        <el-form-item label="项目创建" style="width: 100%;">
          <el-input v-model="editProject.createdBy" type="label" placeholder="请输入项目创建人" rows="5"></el-input>
        </el-form-item>
        <div style="width: 100%;text-align: right">
          <el-button style="margin-left: 10px;" @click="projectDialog = false">取消</el-button>
          <el-button type="primary" style="margin-left: 10px;" @click="addProject">确定</el-button>
        </div>
      </el-form>
    </el-dialog>

    <!--添加问卷弹窗-->
    <el-dialog title="问卷" :visible.sync="dialogShow" :close-on-click-modal="false" class="dialog">
      <el-form ref="form" :model="willAddWj" label-width="80px">
        <el-form-item label="问卷标题" style="width: 100%;" required>
          <el-input v-model="willAddWj.title" placeholder="请输入问卷标题"></el-input>
        </el-form-item>
        <el-form-item label="问卷描述" style="width: 100%;">
          <el-input v-model="willAddWj.desc" type="textarea" placeholder="请输入问卷描述" rows="5"></el-input>
        </el-form-item>
        <el-form-item label="调查人群" style="width: 100%;">
          <el-input v-model="willAddWj.surveyCrowd" placeholder="请输入调查人群"></el-input>
        </el-form-item>
        <el-form-item label="添加为模板" label-width="82px" style="width:0% ;">
          <el-switch v-model="willAddWj.isTemplate" :active-value="1" :inactive-value="0"></el-switch>
        </el-form-item>
        <el-form-item label="有效期" >
          <el-col :span="11">
            <el-form-item prop="startTime">
              <el-date-picker type="datetime" value-format="yyyy-MM-dd HH:mm:ss" placeholder="开始日期"
                v-model="willAddWj.startTime" style="width: 100%;"></el-date-picker>
            </el-form-item>
          </el-col>
          <el-col class="line" :span="2">-</el-col>
          <el-col :span="11">
            <el-form-item prop="endTime">
              <el-date-picker placeholder="结束日期" value-format="yyyy-MM-dd HH:mm:ss" type="datetime"
                v-model="willAddWj.endTime" style="width: 100%;"></el-date-picker>
            </el-form-item>
          </el-col>
        </el-form-item>
      </el-form>
      <div style="width: 100%;text-align: right">
        <el-button style="margin-left: 10px;" @click="openTemp">从模板库创建</el-button>
        <el-button style="margin-left: 10px;" @click="dialogShow = false">取消</el-button>
        <el-button type="primary" style="margin-left: 10px;" @click="addWj">确定</el-button>
      </div>
    </el-dialog>

    <!--模板库弹窗-->
    <el-dialog title="模板库" :visible.sync="tempDialog" :close-on-click-modal="false" class="dialog">
      <el-input placeholder="请输入关键词搜索" prefix-icon="el-icon-search" v-model="tempSearchText"></el-input>
      <el-table :data="tempData" style="width: 100%;" :loading="tempLoading" element-loading-text="加载中...">
        <el-table-column prop="title" label="模板名" width="250"></el-table-column>
        <el-table-column prop="createdBy" label="创建者"></el-table-column>
        <el-table-column label="预览">
          <template slot-scope="scope">
            <el-link type="primary" @click="lookTempWj(scope.row)">预览</el-link>
          </template>
        </el-table-column>
        <el-table-column label="操作">
          <template slot-scope="scope">
            <el-link type="primary" @click="useTemp(scope.row)">使用此模板</el-link>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination layout="prev, pager, next" :total="tempDataCount" @current-change="changeTempPage" :page-size="5">
      </el-pagination>
    </el-dialog>

    <!--分享问卷弹窗-->
    <el-dialog title="分享问卷" :visible.sync="shareDialogShow" :close-on-click-modal="true" class="dialog"
      @opened="makeQrcode">
      <el-form ref="form" :model="shareInfo" label-width="80px">
        <el-form-item label="分享给">
          <el-row>
            <el-col :span="16">
              <el-select v-model="shareInfo.users" multiple placeholder="请选择">
                <el-option v-for="item in users" :key="item.reaalName || item.username"
                  :label="item.reaalName || item.username" :value="item.id"></el-option>
              </el-select>
            </el-col>
            <el-col :span="8">
              <el-button style="margin-left: 5px;" @click="shareWj4User">确认分享</el-button>
            </el-col>
          </el-row>
        </el-form-item>
        <el-form-item label="问卷链接" style="width: 100%;">
          <el-row>
            <el-col :span="16">
              <el-input v-model="shareInfo.url" readonly></el-input>
            </el-col>
            <el-col :span="8">
              <el-button style="margin-left: 5px;" v-clipboard:copy="shareInfo.url" v-clipboard:success="copySuccess"
                v-clipboard:error="copyError">复制</el-button>
              <el-button style="margin-left: 5px;" @click="openShareUrl">打开</el-button>
            </el-col>
          </el-row>
        </el-form-item>
        <el-form-item label="二维码" style="width: 100%;">
          <canvas id="canvas" style="width: 150px;height: 150px;"></canvas>
        </el-form-item>
      </el-form>
    </el-dialog>
  </div>
</template>


<script>
import {
  designOpera,
  queryProjectList,
  addQuestionnaire,
  publishQuestionnaire,
  cancelQuestionnaire,
  updateQuestionnaire,
  post,
} from '../utils/api'
import Design from './questionnaire/Design.vue'
import DataShow from './questionnaire/DataShow.vue'
import ElButton from '../../node_modules/element-ui/packages/button/src/button'
import QRCode from 'qrcode'
export default {
  components: {
    ElButton,
    Design,
    QRCode,
    DataShow,
  },
  data() {
    return {
      treeProp: {
        children: 'questions',
      },
      filterText: '',
      defaultActive: 1, //当前激活菜单
      activeName: 'wjsj', //标签页当前选择项
      wjList: [], //问卷列表
      loading: false, //是否显示加载中
      dialogShow: false, //添加问卷弹窗是否显示
      shareDialogShow: false, //分享问卷弹窗是否显示
      tempDialog: false, //模板库弹窗是否显示
      //        nowTempData:[],//当前模板库页码
      projectDialog: false,
      tempData: [],
      tempDataCount: 0,
      tempLoading: false,
      tempSearchText: '',
      curProjectId: '',
      willAddWj: {
        id: 0,
        title: '',
        flag: 0,
        desc: '感谢您能抽时间参与本次问卷，您的意见和建议就是我们前行的动力！',
      },
      editProject: {},
      shareInfo: {
        url: '',
      },
      curQuestionnaire: {
        id: '',
        title: '',
        desc: '',
        status: '',
      },
      users: [],
    }
  },
  mounted() {
    this.$post('/api/admin/queryUserList', {
      page: {
        pageSize: 5,
        pageNum: 1,
      },
      queryParam: { userName: '', roleId: ['2', '3'] },
    }).then((data) => (this.users = data.list))
    this.getWjList()
  },
  watch: {
    filterText(val) {
      this.$refs.tree.filter(val)
    },
  },
  methods: {
    filterNode(value, data, node) {
      if (!value) return true
      if (node.level == 1) {
        return data.projectName && data.projectName.indexOf(value) !== -1
      }
      return (
        node.parent.data.projectName &&
        node.parent.data.projectName.indexOf(value) !== -1
      )
    },
    addProject() {
      if (!this.editProject.id) {
        post('/api/project/addProjectInfo', this.editProject)
          .then((res) => {
            this.$message({
              type: 'success',
              message: '新建项目成功',
              showClose: true,
            })
            this.projectDialog = false
            this.getWjList()
          })
          .catch((err) => {
            this.$message({
              type: 'error',
              message: err,
            })
          })
      } else {
        post('/api/project/modifyProjectInfo', this.editProject)
          .then((res) => {
            this.$message({
              type: 'success',
              message: '修改项目成功',
              showClose: true,
            })
            this.projectDialog = false
          })
          .catch((err) => {
            this.$message({
              type: 'error',
              message: err,
            })
          })
      }
    },
    deleteProject(data) {
      this.$confirm(
        '确定删除' + this.curQuestionnaire.title + '? 删除后不可恢复！',
        '提示',
        {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning',
        }
      ).then(() => {
        post('/api/deleteProjectById', data)
        .then((res) => {
          this.$message({
            type: 'success',
            message: '删除项目成功',
            showClose: true,
          })
          this.projectDialog = false
          this.getWjList()
        })
        .catch((err) => {
          this.$message({
            type: 'error',
            message: err,
          })
        })
      })
    },
    renderContent(h, { node, data, store }) {
      const _this = this
      if (data.projectName) {
        return h(
          'span',
          {
            style: 'width:100%;justify-content: center;font-size: 20px;',
          },
          [
            h('i', {
              class: 'el-icon-collection',
              style: 'margin-top: 11px;',
            }),
            h('span', data.projectName),
            h(
              'span',
              {
                style: 'float:right;',
              },
              [
                h(
                  'el-button',
                  {
                    props: {
                      type: 'text',
                    },
                    style: 'color: #55aaff;font-size: 15px;',
                    on: {
                      click: () => {
                        ; (_this.projectDialog = true),
                          (_this.editProject = data)
                      },
                    },
                  },
                  '查看'
                ),
                h(
                  'el-button',
                  {
                    props: {
                      type: 'text',
                    },
                    style: 'color: #55aa7f;font-size: 15px;',
                    on: {
                      click: () => {
                        ; (_this.projectDialog = true),
                          (_this.editProject = data)
                      },
                    },
                  },
                  '编辑'
                ),
                h(
                  'el-button',
                  {
                    props: {
                      type: 'text',
                    },
                    style: 'color: #ff0000;font-size: 15px;',
                    on: {
                      click: () => {
                        _this.deleteProject(data)
                      },
                    },
                  },
                  '删除'
                ),
                h(
                  'el-button',
                  {
                    props: {
                      type: 'text',
                    },
                    style: 'color: #ffaa00;font-size: 15px;',
                    on: {
                      click: () => {
                        ; (_this.dialogShow = true),
                          (_this.curProjectId = data.id)
                      },
                    },
                  },
                  '添加问卷'
                ),
              ]
            ),
          ]
        )
      } else {
        return h(
          'span',
          {
            style: 'width:100%;    justify-content: center;margin-top:0px',
          },
          [
            h('i', {
              class: 'el-icon-reading',
              style: '    margin-top: 11px;',
            }),
            h('span', data.title),
            h(
              'span',
              {
                style: 'float:right;',
              },
              [
               h(
                 'el-button',
                 {
                   props: {
                     type: 'text',
                   },
                   style: 'color: #55aaff;padding-right: 5px;',
                   on: {
                     click: () => _this.editWj(data),
                   },
                 },
                 '查看'
               ),

                h(
                  'el-button',
                  {
                    props: {
                      type: 'text',
                    },
                    style: 'color: #55aa7f;padding-right: 5px;',
                    on: {
                      click: () => _this.editWj(data),
                    },
                  },
                  '编辑'
                ),

                h(
                  'el-button',
                  {
                    props: {
                      type: 'text',
                    },
                    style: 'color: #ffaa00;padding-right: 5px;',
                    on: { click: () => _this.pushWj(data) },
                  },
                  data.status != 1 ? '发布' : '暂停'
                ),
                h(
                  'el-button',
                  {
                    props: {
                      type: 'text',
                    },
                    style:'color: #ff0000;',
                    on: { click: () => _this.deleteWj(data.id) },
                  },
                  '删除'
                ),
              ]
            ),
          ]
        )
      }
    },
    handleOptionCommand(command, data) {
      console.log(command)
      console.log(data)
    },
    //使用问卷
    useTemp(item) {
      this.tempLoading = true
      post('/api/questionnaire/useTemplate', {
        ...this.willAddWj,
        questionnaireId: item.questionnaireId,
        projectId: this.curProjectId,
      })
        .then((data) => {
          this.tempLoading = false
          this.$message({
            type: 'success',
            message: '使用模板成功！',
            showClose: true,
          })
          this.tempDialog = false
          this.dialogShow = false
          this.getWjList()
        })
        .catch((err) => {
          this.$message({
            type: 'error',
            message: err,
          })
        })
    },
    //打开问卷库
    openTemp() {
      this.tempDialog = true
      this.changeTempPage(1)
      //        this.getTempWjList();
    },
    //改变模板库页码
    changeTempPage(page) {
      this.tempLoading = true
      post('/api/questionnaire/templates', {
        page: page,
      }).then((data) => {
        console.log(data)
        this.tempDataCount = data.count
        this.tempData = data.list
        this.tempLoading = false
      })
    },
    //预览模板问卷
    lookTempWj(item) {
      let url = window.location.origin + '/tempdisplay/' + item.questionnaireId //问卷链接
      console.log(url)
      window.open(url)
    },
    //检查登录是否过期
    logincheck() {
      //     designOpera({
      //     opera_type:'logincheck',
      //   })
      //   .then(data=>{
      //     console.log(data);
      //     if(data.code==404){//如果返回的错误是404，跳转到登录页面
      //       this.$message({
      //         type: 'error',
      //         message: '您还未登录，请登录',
      //         showClose: true
      //       });
      //       this.$router.push({path:'/login'})
      //     }
      //     else{
      this.getWjList()
      //   }
      // })
    },
    //发布问卷/暂停问卷
    pushWj(data) {
      let status = 1
      if (data.status == 1) status = 0
      publishQuestionnaire({
        questionnaireId: data.questionnaireId,
        status: status,
      }).then((data) => {
        if (data.code == '666') {
          this.$message({
            type: 'success',
            message: status == 1 ? '问卷发布成功！' : '问卷暂停成功！',
          })
          this.getWjList()
        } else {
          this.$message({
            type: 'error',
            message: data.message,
          })
        }
      })
    },
    shareWj() {
      this.shareInfo.url =
        window.location.origin + '/display/' + this.curQuestionnaire.questionnaireId //问卷链接
      this.shareInfo.questionnaireId = this.curQuestionnaire.questionnaireId
      this.shareDialogShow = true
    },
    //分享问卷
    shareWj4User(data) {
      post('api/questionnaire/share', {
        url: window.location.origin,
        questionnaireId: this.shareInfo.questionnaireId,
        userIds: this.shareInfo.users,
      })
        .then((res) => {
          this.$message({
            message: '分享成功',
          })
        })
        .catch((err) => {
          this.$message({
            type: 'error',
            message: err.message,
          })
        })
    },
    //生成二维码
    makeQrcode() {
      var canvas = document.getElementById('canvas')
      QRCode.toCanvas(canvas, this.shareInfo.url)
    },
    //复制分享链接成功
    copySuccess(e) {
      console.log(e)
      this.$message({
        type: 'success',
        message: '已复制链接到剪切板',
      })
    },
    //复制分享链接失败
    copyError(e) {
      console.log(e)
      this.$message({
        type: 'error',
        message: '复制失败',
      })
    },
    //打开分享链接
    openShareUrl() {
      window.open(this.shareInfo.url)
    },
    //预览问卷
    previewWj() {
      let url =
        window.location.origin +
        '/display/' +
        this.curQuestionnaire.questionnaireId //问卷链接
      console.log(url)
      this.$router.push('/display/' +this.curQuestionnaire.questionnaireId);
      //window.open(url)
    },
    //编辑问卷
    editWj(data) {
      this.willAddWj = data
      this.dialogShow = true
    },
    //添加问卷按钮点击
    addWjButtonClick() {
      this.dialogShow = true
      this.willAddWj = {
        id: 0,
        title: '',
        flag: 0,
        desc: '感谢您能抽时间参与本次问卷，您的意见和建议就是我们前行的动力！',
      }
    },
    //添加问卷
    addWj() {
      if (this.willAddWj.title == '') {
        this.$message({
          type: 'error',
          message: '标题不能为空',
        })
        return
      }
      if (this.willAddWj.questionnaireId) {
        updateQuestionnaire(this.willAddWj).then((res) => {
          if (res.code == '666') {
            this.$message({
              type: 'success',
              message: '保存成功!',
            })
            this.getWjList()
            this.dialogShow = false
          } else {
            this.$message({
              type: 'error',
              message: res.message,
            })
          }
        })
      } else {
        const projectId = this.curProjectId
        addQuestionnaire({
          projectId: projectId,
          title: this.willAddWj.title,
          id: this.willAddWj.id,
          desc: this.willAddWj.desc,
          isTemplate:this.willAddWj.isTemplate,
        }).then((data) => {
          console.log(data)
          if (data.code == '666') {
            this.$message({
              type: 'success',
              message: '保存成功!',
            })
            this.getWjList()
            this.dialogShow = false
          } else {
            this.$message({
              type: 'error',
              message: data.messag,
            })
          }
        })
      }
    },
    //获取问卷列表
    getWjList() {
      this.loading = true
      queryProjectList({
        username: 'test',
      }).then((data) => {
        console.log(data)
        this.wjList = data.data
        this.loading = false
        //获取当前选中问卷题目
        this.lookDetail(data.data)
      })
    },
    //删除问卷
    deleteWj() {
      this.$confirm(
        '确定删除' + this.curQuestionnaire.title + '? 删除后不可恢复！',
        '提示',
        {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning',
        }
      ).then(() => {
        this.loading = true
        post('/api/questionnaire/delete', {
          questionnaireId: this.curQuestionnaire.questionnaireId,
        })
          .then((data) => {
            this.$message({
              type: 'success',
              message: '删除成功!',
            })
            this.loading = false
            this.getWjList()
            this.defaultActive = 1
          })
          .catch((err) => {
            this.$message({
              type: 'error',
              message: err,
            })
          })
      })
    },
    //问卷点击
    wjClick(data,node) {
      const index = data.id + 1;
      this.defaultActive = (index + 1).toString()
      this.lookDetail()
    },
    //查看问卷详情
    lookDetail() {
      //console.log(this.curQuestionnaire.questionnaireId,"--------",this.curQuestionnaire.title,"-----------", this.curQuestionnaire.desc);
          this.$refs.design.init(this.curQuestionnaire.questionnaireId,this.curQuestionnaire.title, this.curQuestionnaire.desc)
          this.$refs.dataShow.dataAnalysis(this.curQuestionnaire.questionnaireId)
    },
    handleNodeClick(node,data) {
      console.log('节点被点击:node:', node,"     data:", data);
          if (node && node.questionnaireId !== undefined) {
            const index = data.id + 1;
            this.curQuestionnaire = node;
            //console.log(this.curQuestionnaire.questionnaireId);
            this.wjClick(data,node, index);
            //console.log(this.curQuestionnaire.questionnaireId);
            }

    },
  },
}
</script>
<style scoped>
#projectHome {
  font-size: 16px;
}

.home {
  width: 100%;
  height: calc(100vh - 100px);
  text-align: center;
}

.home .badgeItem {
  margin-top: 40px;
}

.content {
  padding: 20px;
  padding-right: 50px;
  height: calc(100vh - 175px);
  text-align: center;
  overflow-y: scroll;
  overflow-x: hidden;
}

.menu {
  background-color: white;
  height: calc(100vh - 100px);
  overflow-x: scroll;
  overflow-y: scroll;
  left: 0;
}

.home .opera {
  position: relative;
  background-color: #55ffff;
  text-align: center;
  height: 40px;
}

.home .rightButton {
  font-size: 16px;
}

.home .addWjDiv {
  height: 50px;
  line-height: 50px;
  text-align: center;
  border-bottom: 1px solid #eee;
}

.mytree /deep/ {
  overflow-y: auto;
}

.mytree /deep/ .el-tree>.el-tree-node:after {
  border-top: none;
}

.mytree /deep/ .el-tree-node {
  position: relative;
  padding-left: 16px;
}

.mytree /deep/ .el-tree-node__expand-icon.is-leaf {
  display: none;
}

.mytree /deep/ .el-tree-node__content {
  display: -webkit-box;
  display: -ms-flexbox;
  display: flex;
  -webkit-box-align: center;
  -ms-flex-align: center;
  align-items: center;
  height: 40px;
  cursor: pointer;
  padding-top: 0px;
}

.mytree /deep/ .el-tree-node__children {
  padding-left: 16px;
}

.mytree /deep/ .el-tree-node :last-child:before {
  height: 40px;
}

.mytree /deep/ .el-tree>.el-tree-node:before {
  border-left: none;
}

.mytree /deep/ .el-tree>.el-tree-node:after {
  border-top: none;
}

.mytree /deep/ .el-tree-node:before {
  content: '';
  left: -4px;
  position: absolute;
  right: auto;
  border-width: 1px;
}

.mytree /deep/ .el-tree-node:after {
  content: '';
  left: -4px;
  position: absolute;
  right: auto;
  border-width: 1px;
}

.mytree /deep/ .el-tree-node:before {
  border-left: 1px dashed #1389bc;
  bottom: 0px;
  height: 100%;
  top: -26px;
  width: 1px;
}

.mytree /deep/ .el-tree-node:after {
  border-top: 1px dashed #1389bc;
  height: 20px;
  top: 12px;
  width: 20px;
}

.mytree /deep/ .el-tree .el-tree-node__expand-icon.expanded {
  -webkit-transform: rotate(0deg);
  transform: rotate(0deg);
}

.mytree /deep/ .el-tree .el-icon-caret-right:before {
  content: '\e723';
  font-size: 16px;
  color: #1389bc;
  position: absolute;
  left: -15px;
  top: -8px;
}

.mytree /deep/ .el-tree .el-tree-node__expand-icon.expanded.el-icon-caret-right:before {
  content: '\e722';
  font-size: 16px;
  color: #1389bc;
  position: absolute;
  left: -15px;
  top: -8px;
}

.mytree /deep/ .el-tree-node__content>.el-tree-node__expand-icon {
  padding: 0;
}

.tree-padding-right {
  position: absolute;
  width: 200px;
  right: 15px;
  top: -5px;
}

</style>
