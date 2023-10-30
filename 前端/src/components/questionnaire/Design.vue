<template>
  <div class="Design" v-loading="loading" element-loading-text="加载中...">
    <h3>{{ questionTitle }}</h3>
    <div class="top" v-if="desc != ''">
      {{ desc }}
    </div>
    <el-card v-for="(item, index) in detail" :key="index" class="box-card" style="margin: 10px;">
      <div slot="header" class="clearfix">
        <div class="questionTitle">
          <!--显示必填标识-->
          <span style="color: #F56C6C;">
            <span v-if="item.isMust">*</span>
            <span v-else>&nbsp;</span>
          </span>
          <span style="color: black;margin-right: 3px;">{{ (index + 1) + '.' }}</span>
          {{ item.questionTitle }}
        </div>
        <div style="float: right;">
          <el-button style="padding: 2px" type="text" @click="editorQuestion(item)">编辑</el-button>
          <el-button style="padding: 2px;color: #F56C6C" type="text" @click="deleteQuestion(index)">删除</el-button>
        </div>
      </div>

      <!--单选题展示-->
      <div class="text item" v-if="item.type == 'radio'" v-for="(option, optionIndex) in item.options" :key="optionIndex">
        <el-radio v-model="item.radioValue" :label="optionIndex" style="margin: 5px;">{{ option.title }}</el-radio>
      </div>

      <!--多选题展示-->
      <el-checkbox-group v-if="item.type == 'checkbox'" v-model="item.checkboxValue">
        <div class="text item" v-for="(option, optionIndex) in item.options" :key="optionIndex">
          <el-checkbox :label="optionIndex" style="margin: 5px;">{{ option.title }}</el-checkbox>
        </div>
      </el-checkbox-group>

      <!--填空题展示-->
      <el-input v-if="item.type == 'text'" type="textarea" :rows="item.row" resize="none" v-model="item.textValue">
      </el-input>

    </el-card>

    <el-button icon="el-icon-circle-plus" @click="addQuestion" style="margin-top: 10px;">添加题目</el-button>

    <br><br><br><br><br>

    <!--添加题目弹窗-->
    <el-dialog :title="dialogTitle" :visible.sync="dialogShow" :close-on-click-modal="false" class="dialog">
      <el-form ref="form" :model="willAddQuestion" label-width="80px">
        <el-form-item label="题目类型" required style="width: 100%;">
          <el-select v-model="willAddQuestion.type" placeholder="请选择题目类型" @change="typeChange">
            <el-option v-for="item in allType" :key="item.value" :label="item.label" :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="是否必填" style="width: 100%;">
          <el-checkbox v-model="willAddQuestion.isMust" :true-label="1" :false-label="0">必填</el-checkbox>
        </el-form-item>
        <el-form-item label="题目标题" required style="width: 100%;">
          <el-input v-model="willAddQuestion.questionTitle" placeholder="请输入标题"></el-input>
        </el-form-item>
        <!--<el-form-item label="上传图片" required style="width: 100%;">
          <el-upload action="/api/question/image" :show-file-list="false" :before-upload="beforeUpload" :data="willAddQuestion" :auto-upload="false" ref="uploadImage"   :http-request="UploadFile">
                <el-button type="primary">选择题目图片</el-button>
          </el-upload>
        </el-form-item>-->
        <template v-if="willAddQuestion.type == 'radio' || willAddQuestion.type == 'checkbox'">
          <el-form-item :label="'选项' + (optionIndex + 1)" v-for="(option, optionIndex) in willAddQuestion.options" :key="optionIndex">
            <el-row>
              <el-col :span="16">
                <el-input v-model="option.title" placeholder="请输入选项名" style="width: 90%;"></el-input>
              </el-col>
              <el-col :span="8">
                <el-button type="danger" plain class="" @click="deleteOption(optionIndex)">删除选项</el-button>
              </el-col>
            </el-row>
          </el-form-item>
          <el-button type="primary" plain class="addOptionButton" @click="addOption">新增选项</el-button>
        </template>
        <template v-if="willAddQuestion.type == 'text'">
          <el-form-item label="填空">
            <el-input type="textarea" :rows="willAddQuestion.row" style="width: 80%" resize="none"></el-input>
          </el-form-item>
          <el-form-item label="行数">
            <el-input-number v-model="willAddQuestion.row" :min="1" :max="10" label="描述文字"></el-input-number>
          </el-form-item>
        </template>
      </el-form>
      <br>
      <div style="width: 100%;text-align: right">
        <el-button style="margin-left: 10px;" @click="dialogShow = false">取消</el-button>
        <el-button type="primary" style="margin-left: 10px;" @click="checkAddQuestion">完成</el-button>
      </div>
    </el-dialog>
  </div>
</template>


<script>
import { designOpera, queryQuestions, sageQuestions,postFile } from '../../utils/api'
export default {
  data() {
    return {
      loading: false,//页面加载中
      dialogShow: false,
      dialogTitle: '',
      detail: [],
      questionnaireId: 0,
      questionTitle: '',
      desc: '',
      willAddQuestion: {
        id: '',
        type: '',
        questionTitle: '',
        options: [
          {
            questionTitle: '',//选项标题
            id: ''//选项id
          }
        ],
        row: 1,
        isMust: 0,//是否必填
      },
      allType: [
        {
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
    }
  },
  methods: {
    //初始化问卷所有问题
    init(questionnaireId, questionTitle, desc) {
      this.questionnaireId = questionnaireId;
      this.questionTitle = questionTitle;
      this.desc = desc;
      this.getQuestionList();
      //console.log(this.questionnaireId)
    },
    //获取问题列表(问卷内容)
    getQuestionList() {
      this.detail = [];
      const _this = this;
      this.loading = true;
      //console.log(this.questionnaireId);
      queryQuestions({
        questionnaireId: this.questionnaireId,
      })
        .then(data => {
          console.log(data)
          _this.loading = false;
          if (data.data) {
            data.data.forEach((element) => {
              if (element.type == 'radio') {
                element.radioValue = ''
              } else if (element.type == 'checkbox') {
                element.checkboxValue = []
              } else {
                element.textValue = ''
              }
              _this.detail.push(element)
            })
          }
        })
    },
    //点击添加问题按钮
    addQuestion() {
      if (this.questionnaireId == 0 || this.questionnaireId == null) {
        this.$message({
          type: 'error',
          message: '清先创建问卷!'
        });
        return;
      }
      this.dialogTitle = '添加题目';
      this.willAddQuestion = {
        id: '',
        type: '',
        questionTitle: '',
        options: [
          {
            questionTitle: '',//选项标题
            id: 0//选项id
          }
        ],
        row: 1,
        isMust: 0,//是否必填
      };
      this.dialogShow = true;
    },
    //删除问题
    deleteQuestion(index) {
      this.$confirm('确定删除此题目?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$post('/api/question/delete', {
          questionId: this.detail[index].questionId,
        })
          .then(data => {
            this.detail.splice(index, 1);
            this.$message({
              type: 'success',
              message: '删除成功!'
            });
          }).catch(err => {
            this.$message({
              type: 'error',
              message: data.msg
            });
          })
      });

    },
    beforeUpload(file) {
      const msg = file.name.substring(file.name.lastIndexOf('.') + 1)
      if (msg !== 'jpg' && msg !== 'jpeg' && msg !== 'png') {
        this.$message({
          message: '上传文件只能是图片格式',
          type: 'error'
        });
        return false;
      }
      return true;
    },
    //确认添加/保存题目
    checkAddQuestion() {
      //添加保存问题
      let newItem = {};//新添加的问题对象
      newItem = {
        type: this.willAddQuestion.type,
        questionId:this.detail.length,
        questionTitle: this.willAddQuestion.questionTitle,
        options: this.willAddQuestion.options,
        row: this.willAddQuestion.row,
        isMust: this.willAddQuestion.isMust,
      };
      newItem.radioValue = -1;
      newItem.checkboxValue = [];
      newItem.textValue = '';
      if (!this.willAddQuestion.type) {
        this.$message({
          type: 'error',
          message: '请选择题目类型'
        });
        return
      }
      if (!this.willAddQuestion.questionTitle) {
        this.$message({
          type: 'error',
          message: '请输入题目标题'
        });
        return
      }
      if (newItem.type != 'checkbox' && (!newItem.options || newItem.options.length < 1)) {
        this.$message({
          type: 'error',
          message: '请添加至少一个选项'
        });
        return
      }
      console.log(this.questionnaireId,"--------",this.willAddQuestion.questionId,"-------")
      sageQuestions({
        questionnaireId: this.questionnaireId,
        questionId: this.willAddQuestion.questionId,
        questionTitle: this.willAddQuestion.questionTitle,
        type: this.willAddQuestion.type,
        options: this.willAddQuestion.options,
        row: this.willAddQuestion.row,
        isMust: this.willAddQuestion.isMust,
      })
        .then(data => {

          newItem.id = data.id;
          if (data.code == '666') {
            this.dialogShow = false;
            //this.$refs.uploadImage.submit()
            this.$message({
              type: 'success',
              message: '保存成功!'
            });
            this.getQuestionList();
          }
          else {
            this.dialogShow = false;
            this.$message({
              type: 'error',
              message: data.message
            });
          }
          this.willAddQuestion = {
            id: '',
            type: '',
            questionTitle: '',
            options: [''],
            row: 1,
            isMust: 0,
          };
        });
    },
    //点击编辑问题按钮
    editorQuestion(item) {
      this.willAddQuestion.questionTitle = item.questionTitle;
      this.willAddQuestion.type = item.type;
      this.willAddQuestion.options = JSON.parse(JSON.stringify(item.options));
      this.willAddQuestion.text = item.text;
      this.willAddQuestion.row = item.row;
      this.willAddQuestion.isMust = item.isMust;
      this.willAddQuestion.questionId = item.questionId;
      this.dialogTitle = '编辑问题';
      this.dialogShow = true;
    },
    //添加选项
    addOption() {
      if (!this.willAddQuestion.options) {
        this.willAddQuestion.options = []
      }
      this.willAddQuestion.options.push({
        title: '',
        id: 0,
      });
    },
    //删除选项
    deleteOption(index) {
      this.willAddQuestion.options.splice(index, 1);
    },
    UploadFile(params){
      let param = {quetsionId:this.willAddQuestion.questionId}
      postFile('/api/question/image',this.willAddQuestion.questionId,params.file)
    },
    //切换问题类型
    typeChange(value) {
      console.log(value);
      this.willAddQuestion.type = value;
      this.willAddQuestion.text = '';
      this.row = 1;
    },
  }
}
</script>
<style scoped>
.Design {}

.Design .dialog {
  text-align: left;
}

.Design .questionTitle {
  display: inline-block;
  width: 80%;
  font-size: 16px;
  color: #303133;
}

.Design .addOptionButton {
  display: inline-block;
  margin-left: 80px;
}

.box-card {
  width: 100%;
  text-align: left;
}

.Design .top {
  color: #606266;
  margin-left: 20px;
  padding: 0 10px 10px 10px;
  border-bottom: 3px solid #409EFF;
  font-size: 15px;
  line-height: 22px;
  text-align: left;
}
</style>
