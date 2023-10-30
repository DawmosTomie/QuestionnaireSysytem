<template>
  <div style="  padding-top: 20px;  width: 100%;  text-align: center;">
    <el-card>
      <el-row type="flex" justify="space-around">
        <el-col :span="8">
          <el-card style="height:300px">
            <div class="details-title clearfix" slot="header">
              <span>从空白处创建</span>
            </div>
            <el-input style="width:80%" v-model="surveyTitle" placeholder="请输入标题"></el-input>
            <div style="margin-top:30px">
              <span class="dialog-footer" style="text-align: center">
                <el-row class="bt-group">
                  <el-button :span="2" type="primary" @click="createConfirm">立即创建</el-button>
                </el-row>
              </span>
            </div>
          </el-card>
        </el-col>
        <el-col :span="6">
          <el-card style="height:300px" :body-style="{ padding: '10px' }" @click="$message.error('待实现')">
            <div slot="header">
              <div class="details-title">文本导入</div>
              <div style="font-size: 14px;color: #A6A6A6;line-height: 20px;margin-bottom: 15px;">
                <span>批量导入，自动生成问卷</span>
              </div>
            </div>
            <img style="width: 100px; height: 100px" src="../../assets/images/text-lead.png" :fit="fit" />
          </el-card>
        </el-col>
        <el-col :span="6">
          <el-card style="height:300px" :body-style="{ padding: '10px' }" @click="$message.error('待实现')">
            <div slot="header">
              <div class="details-title">OCR创建</div>
              <div style="font-size: 14px;color: #A6A6A6;line-height: 20px;margin-bottom: 15px;">
                <span>OCR识别，自动生成问卷</span>
              </div>
            </div>
            <img style="width: 100px; height: 100px" src="../../assets/images/ocr.png" :fit="fit" />
          </el-card>
        </el-col>
      </el-row>
      <el-row style="padding-top:20px">
        <el-card>
          <div slot="header" class="details-title clearfix">
            <span>推荐模板</span>
          </div>
          <Recommend></Recommend>
        </el-card>
      </el-row>
    </el-card>
  </div>
</template>
<script>
  import Recommend from '@/components/questionnaire/Recommend';
  import {
    designOpera,
    queryProjectList,
    addQuestionnaire,
    publishQuestionnaire,
    cancelQuestionnaire,
    updateQuestionnaire,
    post,
  } from '@/utils/api';
  import {
    v4 as uuidv4
  } from 'uuid';
  export default {
    components: {
      Recommend,
    },
    data() {
      return {
        surveyTitle: '',
        project: {},
      }
    },
    methods: {
      createConfirm() {

        this.project.projectName = "未分类";
        console.log("666");
        this.$post('/api/project/queryProjectName', this.project)
          .then((data) => {
            console.log(data.id)
            if (data.id) {
              const projectId = data.id;
              //this.$message({
              //  type: 'success',
              //  message: '创建成功!',
              //});
              console.log("--------------" + data.id);
              addQuestionnaire({
                projectId: data.id,
                title: this.surveyTitle,
              }).then((data) => {
                console.log(data)
                if (data.code == '666') {
                  this.$message({
                    type: 'success',
                    message: '创建问卷成功--跳转至编辑页面',
                  })
                  this.$router.push({
                    path: '/user/edit_ques/',
                    query: {
                      title: this.surveyTitle,
                      questionnaireId:data.data,
                    },
                  })
                } else {
                  this.$message({
                    type: 'error',
                    message: '失败!',
                  })
                }
              })
            } else {
              this.$message({
                type: 'error',
                message: data.messag,
              })
            }
          });



      },
    },
  }
</script>
