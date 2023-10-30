<!--
程序名：模板问卷填写页面
功能：用户打开问卷链接对问卷进行填写
-->
<template>
  <div class="display">
    <div class="content">
      <h3>{{ title }}</h3>
      <div class="top" v-if="desc !== ''">
        {{ desc }}
      </div>
      <el-card class="box-card" v-for="(item, index) in detail" :key="index">
        <div slot="header" class="clearfix">
          <div class="questionTitle">
            <span style="color: #F56C6C;">
              <span v-if="item.isMust">*</span>
              <span v-else>&nbsp;</span>
            </span>
            {{ (index + 1) + '.' + item.questionTitle +'---'+'答卷人答案:'+ item.radioValue ||item.checkboxValue||item.textValue||"未作答"}}
          </div>
        </div>

        <!-- 单选题展示 -->
        <div class="text item" v-if="item.type === 'radio'" v-for="(optionItem,opindex) in item.options"
          :key="opindex">
          <el-radio v-model="item.radioValue" :label="opindex.toString()"
            style="margin: 5px;">{{ optionItem.title}}</el-radio>
        </div>

        <!-- 多选题展示 -->
        <el-checkbox-group v-if="item.type === 'checkbox'" v-model="item.checkboxValue">
          <div class="text item" v-for="optionItem in item.options" :key="opindex">
            <el-checkbox  :checked="item.checkboxValue.includes(opindex.toString())" :label="opindex.toString()" style="margin: 5px;">{{ optionItem.title}}</el-checkbox>
          </div>
        </el-checkbox-group>

        <!-- 填空题展示 -->
        <el-input v-if="item.type === 'text'" type="textarea" :rows="item.row" v-model="item.textValue"
          resize="none"></el-input>
      </el-card>
    </div>
  </div>
</template>

<script>
  import {
    designOpera,
    analysis,
    answerView,
    Post,
  } from "@/utils/api";
  import {
    answerOpera
  } from '@/utils/api'
  export default {
    props: {
      commitId: {
        type: String,
        default: ''
      },
      wjId: {
        type: String,
        default: ''
      }
    },
    data() {
      return {
        dialogShow: false,
        dialogTitle: '',
        dialogType: 1, //1添加 2修改
        oldItem: null, //编辑中问题的对象
        willAddQuestion: {
          type: '',
          title: '',
          options: [''],
          text: '',
          row: 1,
        },
        allType: [{
            value: 'radio',
            label: '单选题',
          },
          {
            value: 'checkbox',
            label: '多选题',
          },
          {
            value: 'text',
            label: '填空题',
          },
        ],
        title: '',
        desc: '',
        detail: [],
        startTimestamp: 0, //填写问卷开始时间戳 毫秒
        submitLoading: false, //提交按钮 加载中状态
        submitText: '提交', //提交按钮文字
        data: [],
        questions: [],
        wjId: 0,
        questions: [],
      }
    },
    mounted() {
      var questionnaireId = this.$route.params.questionnaireId;

      this.data = [];
      this.questions = [];
      this.wjId = questionnaireId;
      console.log(this.wjId);

      this.$post('/api/questionnaire/answerView', {
        questionnaireId: this.wjId
      }).then((data) => {
        console.log(data);
        this.title=data.title;
        this.desc=data.desc;
        this.questions = data.questions;
        this.$post('/api/qnCommit/get', {
          questionnaireId: this.wjId,
        }).then((data) => {
          this.questionCommitList = data;
          console.log(this.questionCommitList);
          let answerList = [];
          this.$post('/api/qnAnswer/get', {
            commitId: this.commitId,
          }).then((data) => {
            this.answerList = data;
            for (const item of this.answerList) {
              let dataItem = [];
              for (let i = 0; i < this.questions.length; i++) {
                const q = this.questions[i];
                if (item.questionId === q.questionId) {
                  dataItem.questionTitle = q.questionTitle;
                  dataItem.type = q.type;
                  dataItem.questionId = q.questionId;
                  if (dataItem.type == 'radio') {
                    dataItem.options=q.options;
                    if(item.anwser==null||item.anwser=='') dataItem.radioValue =1 || "1";
                    dataItem.radioValue = item.anwser;
                  } else if (dataItem.type == 'checkbox') {
                    dataItem.options=q.options;
                    if(item.anwser==null||item.anwser=='') dataItem.checkboxValue =[0,1];
                    dataItem.checkboxValue = item.anwser ;
                  } else {
                    if(item.anwser==null||item.anwser=='') dataItem.textValue ="hello,world";
                    dataItem.textValue = item.anwser;
                  }console.log("hhh",dataItem);
                  //dataItem.result = item.anwser;
                  this.data.push(dataItem);
                  this.detail.push(dataItem);

                }
              }
            }



          });
        });

        this.startTimestamp = new Date().getTime(); //时间戳 毫秒
      });
    },


        methods: {
          //提交问卷
          submit() {
            //提交成功
            this.submitLoading = false;
            this.submitText = '提交';
            this.$router.push({
              path: '/thankyou'
            }); //跳到欢迎页
          }

        }
      }
</script>
<style scoped>
  .display {
    text-align: center;
    padding: 20px;
  }

  .display .top {
    color: #606266;
    padding: 0 10px 10px 10px;
    border-bottom: 3px solid #409EFF;
    font-size: 15px;
    line-height: 22px;
    text-align: left;
  }

  .display .content {
    width: 100%;
    max-width: 800px;
    display: inline-block;
    text-align: center;
  }

  .display .box-card {
    text-align: left;
    width: 100%;
    margin: 10px 0 10px 0;
  }

  .display .bottom {
    margin: 20px 10px 20px 10px;
    color: #909399;
  }

  .display a:link,
  a:visited,
  a:active {
    color: #909399;
    text-decoration: none;
  }
</style>
