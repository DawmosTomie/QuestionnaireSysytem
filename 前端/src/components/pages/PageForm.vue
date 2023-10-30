<template>
  <div style="border-radius: 10px 10px 10px 10px;">
    <BottomFormEditor v-show="tabs == 0 && $viewport.width < 850" @addQuestion="addQuestion" />

    <div class="container whole-container">
      <v-tabs v-model="tabs" background-color="white" style="border-radius: 10px 10px 10px 10px;" :color="themeColor"
        :slider-color="themeColor" centered>
        <v-tabs-slider></v-tabs-slider>

        <v-tab key="question">问题</v-tab>
        <v-tab key="answer">回答</v-tab>
      </v-tabs>

      <v-tabs-items active-class="transparentBack" v-model="tabs">
        <v-tab-item :color="themeColor" key="question">
          <v-card>
            <div
              style="border-top-left-radius: 8px; border-top-right-radius: 8px; height: 10px; left: -1px; position: absolute; top: -1px; width: calc(100% + 2px); background-color: transparent;">
            </div>
            <v-form>
              <v-container>
                <v-row>
                  <v-col>
                    <v-text-field class="title font-weight-medium" :color="themeColor" v-model="questionnaire.title"
                      placeholder="表单标题" />
                    <v-text-field :color="themeColor" placeholder="表单描述" v-model="questionnaire.desc" />
                  </v-col>
                </v-row>
              </v-container>
            </v-form>
            <FormEditor v-show="$viewport.width > 850" @addQuestion="addQuestion" :themeColor="themeColor"
              @importQns="importQns"></FormEditor>
          </v-card>

          <draggable v-model="questionnaire.questions" handle=".handle" @end="onEnd">
            <v-card class="card" style="border-radius: 10px 10px 10px 10px; margin-top: 10px;"
              v-for="(question, questionIndex) in questionnaire.questions" :key="questionIndex"
              @click.native="focusForm(questionIndex)" :elevation="questionIndex === focusedFormIndex ? '24' : '0'">
              <div v-show="isFocused(questionIndex)"
                style="display: -webkit-box;
                display: -webkit-flex;
                display: flex;
                height: -webkit-calc(100% + 1px);
                height: calc(100% + 1px);
                left: -1px;
                position: absolute;
                bottom: -1px;
                width: 1px;">
                <div
                  style="display: -webkit-box;
                   display: -webkit-flex;
                   display: flex;
                   -webkit-flex-direction: column;
                   flex-direction: column;
                   -webkit-box-flex: 1;
                   box-flex: 1;
                   -webkit-flex-grow: 1;
                   flex-grow: 1;">
                  <div
                    style="border-top-left-radius: 8px;
                    border-bottom-left-radius: 8px; -
                    webkit-box-flex: 1;
                    box-flex: 1;
                    -webkit-flex-grow: 1;
                    flex-grow: 1;
                    position: relative;
                    background-color: #ff0000;">
                  </div>
                </div>
              </div>
              <div class="handle-icon-container">
                <v-icon class="handle" v-show="isFocused(questionIndex)">mdi-dots-horizontal</v-icon>
              </div>
              <!-- Replace the whole element if the format is File upload -->
              <component :is="questionFormatList[question.type]" v-if="question.type === 'File upload'"
                @cancelFormFile="cancelFormFile(questionIndex)" @advanceToFormFile="advanceToFormFile(questionIndex)"
                :themeColor="themeColor" />
              <div v-else>
                <v-form>
                  <v-container>
                    <v-row>
                      <v-col cols="12" xl="8" lg="8" md="8" sm="8">
                        <v-text-field class="title" :color="themeColor" :disabled="!isFocused(questionIndex)"
                          placeholder="问题" v-model="question.questionTitle"></v-text-field>
                      </v-col>
                      <v-col cols="8" xl="4" lg="4" md="4" sm="4">
                        <v-menu offset-y>
                          <template v-slot:activator="{ on }">
                            <v-btn depressed v-on="on" v-show="isFocused(questionIndex)">
                              <v-icon color="grey darken-1" left>{{ question.questionIcon }}</v-icon>
                              {{ questionFormat[question.type].label }}
                            </v-btn>
                          </template>
                          <v-list>
                            <v-list-item v-for="(format, index) in questionFormat" :key="index"
                              @click="changeQuestionFormat(questionIndex, question.type, question.questionIcon, format)">
                              <v-list-item-icon>
                                <v-icon>{{ format.icon }}</v-icon>
                              </v-list-item-icon>
                              <v-list-item-title>{{ format.label }}</v-list-item-title>
                            </v-list-item>
                          </v-list>
                        </v-menu>
                      </v-col>
                    </v-row>
                    <v-row>
                      <v-col>
                        <component :is="questionFormatList[question.type]" @addOption="addOption(questionIndex)"
                          @deleteOption="(...args) => deleteOption(questionIndex, ...args)"
                          @addRow="addRow(questionIndex)" @deleteRow="(...args) => deleteRow(questionIndex, ...args)"
                          @addColumn="addColumn(questionIndex)"
                          @deleteColumn="(...args) => deleteColumn(questionIndex, ...args)" :question="question"
                          :questionIndex="questionIndex" :themeColor="themeColor"
                          :otherQuestions="getOtherQuestion(questionIndex)" :focusedFormIndex="focusedFormIndex" />
                      </v-col>
                    </v-row>

                    <v-row v-if="isFocused(questionIndex)" style="margin-top: -50px;">
                      <v-col>
                        <div v-if="question.options && question.options.length > 0">
                          <div v-for="(option, optionIndex) in question.options" :key="optionIndex" style="margin-top: 10px;">
                            <template v-if="question.type === 'text'">
                              <v-text-field label="请输入内容"></v-text-field>
                            </template>
                            <template v-if="question.type === 'checkbox'">
                              <v-checkbox v-model="option.selected" :label="option.title"></v-checkbox>
                            </template>
                            <template v-if="question.type === 'radio'">
                              <v-radio v-model="option.selected" :label="option.title"></v-radio>
                            </template>
                          </div>
                        </div>
                        <div v-else>
                          <template v-if="question.type === 'text'">
                            <v-text-field label="请输入内容"></v-text-field>
                          </template>
                        </div>
                      </v-col>
                    </v-row>


                  </v-container>
                </v-form>

                <v-container v-show="isFocused(questionIndex)">
                  <v-divider></v-divider>
                </v-container>

                <v-card-actions class="card-actions" v-show="isFocused(questionIndex)">
                  <div class="flex-grow-1"></div>
                  <v-tooltip bottom>
                    <template v-slot:activator="{ on }">
                      <v-btn class="actions-icon" icon @click="duplicateQuestion(questionIndex)" v-on="on">
                        <v-icon>mdi-content-copy</v-icon>
                      </v-btn>
                    </template>
                    <span class="tooltip">复制</span>
                  </v-tooltip>

                  <v-tooltip bottom>
                    <template v-slot:activator="{ on }">
                      <v-btn class="actions-icon" icon @click="deleteQuestion(questionIndex)" v-on="on">
                        <v-icon>mdi-delete</v-icon>
                      </v-btn>
                    </template>
                    <span class="tooltip">删除</span>
                  </v-tooltip>

                  <v-divider vertical></v-divider>
                  <span class="switch-label">必填</span>
                  <v-switch class="switch" :color="themeColor" v-model="question.required"></v-switch>
                  <v-btn icon>
                    <v-icon>mdi-dots-vertical</v-icon>
                  </v-btn>
                </v-card-actions>
              </div>


            </v-card>
          </draggable>
        </v-tab-item>

        <v-tab-item key="answer">
          <ItemResponse :themeColor="themeColor" />
        </v-tab-item>
      </v-tabs-items>
    </div>

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
        <template v-if="willAddQuestion.type == 'radio' || willAddQuestion.type == 'checkbox'">
          <el-form-item :label="'选项' + (index + 1)" v-for="(option, optionIndex) in willAddQuestion.options" :key="optionIndex">
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
  import draggable from 'vuedraggable'
  import { designOpera, queryQuestions, sageQuestions,answerOpera } from '../../utils/api'
  import FormChoices from '@/components/widgets/forms/FormChoices'
  import FormText from '@/components/widgets/forms/FormText'
  import FormFile from '@/components/widgets/forms/FormFile'
  import FormBeforeUploadFile from '@/components/widgets/forms/FormBeforeUploadFile'
  import FormScale from '@/components/widgets/forms/FormScale'
  import FormGrid from '@/components/widgets/forms/FormGrid'
  import ItemReponse from '@/components/widgets/tabItems/ItemReponse'
  import FormEditor from '@/components/widgets/cards/CardFormEditor'
  import BottomFormEditor from '@/components/widgets/cards/CardBottomFormEditor'

  export default {
    props: ['themeColor'],
    components: {
      draggable,
      FormText,
      FormChoices,
      FormFile,
      FormBeforeUploadFile,
      FormScale,
      FormGrid,
      ItemReponse,
      FormEditor,
      BottomFormEditor,
    },
    data() {
      return {
        focusedFormIndex: 0,
        tabs: 'question',
        questionnaire: {
          title: '未命名表单',
          description: '',
          questions: [{
            questionTitle: '',
            type: 'checkbox',
            questionIcon: 'mdi-radiobox-marked',
            oldFormat: 'checkbox',
            oldIcon: 'mdi-radiobox-marked',
            options: [{
              title: '选项1',
            }, ],
            minScale: '1',
            maxScale: '5',
            minScaleLabel: '',
            maxScaleLabel: '',
            isSpecificFileAllowed: false,
            numberOfFiles: '1',
            fileSize: '10MB',
            rows: [{
              row: 'Row1',
            }, ],
            cols: [{
              col: 'Column1',
            }, ],
          }, ],
        },
        questionFormat: {
          text: {
            name: 'text',
            icon: 'mdi-text-short',
            label: '问答题'
          },
          // { name: 'Paragraph', icon: 'mdi-text' },
          checkbox: {
            name: 'checkbox',
            icon: 'mdi-radiobox-marked',
            label: '多选题',
          },
          // { name: 'Checkboxes', icon: 'mdi-checkbox-marked' },
          radio: {
            name: 'radio',
            icon: 'mdi-arrow-down-drop-circle',
            label: '单选题',
          },
          // { name: 'File upload', icon: 'mdi-cloud-upload' },
          // { name: 'Linear scale', icon: 'mdi-ray-vertex' },
          // { name: 'Multiple choice grid', icon: 'mdi-gamepad-circle' },
          // { name: 'Checkbox grid', icon: 'mdi-apps' },
          // { name: 'Date', icon: 'mdi-calendar-range' },
          // { name: 'Time', icon: 'mdi-clock-outline' },
        },
        questionFormatList: {
          text: 'FormText',
          Paragraph: 'FormText',
          checkbox: 'FormChoices',
          Checkboxes: 'FormChoices',
          radio: 'FormChoices',
          'File upload': 'FormBeforeUploadFile',
          'Actually file upload': 'FormFile',
          'Linear scale': 'FormScale',
          'Multiple choice grid': 'FormGrid',
          'Checkbox grid': 'FormGrid',
          Date: 'FormText',
          Time: 'FormText',
        },
        loading: false,//页面加载中
        dialogShow: false,
        dialogTitle: '',
        detail: [],//questionnaire.questions
        questionnaireId: '',
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
      getOtherQuestion(questionIndex) {
        if (this.questionnaire.questions) {
          return this.questionnaire.questions.map((val, index) => {
            val.sortNo = index + 1
            return val
          }).filter((val, index) => {
            return index != questionIndex
          })
        } else {
          return []
        }

      },
      /**addQuestion() {
        // Insert form component into the next to the focused form
        this.questionnaire.questions.splice(this.focusedFormIndex + 1, 0, {
          questionTitle: '',
          type: 'checkbox',
          questionIcon: 'mdi-radiobox-marked',
          options: [{
            title: '选项1',
            jumpTo: ''
          }, ],
          minScale: '1',
          maxScale: '5',
          minScaleLabel: '',
          maxScaleLabel: '',
          isSpecificFileAllowed: false,
          numberOfFiles: '1',
          fileSize: '10MB',
          rows: [{
            row: 'Row1',
          }, ],
          cols: [{
            col: 'Column1',
          }, ],
        })
        this.focusedFormIndex += 1
      },**/
      importQns(qns) {
        //this.questionnaire.questions = this.questionnaire.questions.concat(qns);
        this.getQuestionList();
        console.log(this.questionnaireId);
        for (const questionItem of qns) {
          sageQuestions({
          questionnaireId: this.questionnaireId,
          questionId: questionItem.questionId,
          questionTitle: questionItem.questionTitle,
          type: questionItem.type,
          options: questionItem.options,
          row: questionItem.row,
          isMust: questionItem.isMust,
        })
          .then(data => {
            if (data.code == '666') {
              this.dialogShow = false;
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


          this.focusedFormIndex += 1;
          this.getQuestionList();
        }


      },
      duplicateQuestion(questionIndex) {
        var copiedQuestion = JSON.parse(
          JSON.stringify(this.questionnaire.questions[questionIndex])
        )
        this.questionnaire.questions.splice(questionIndex + 1, 0, copiedQuestion)
        this.focusedFormIndex += 1 // This line doesn't work
      },
      deleteQuestion(questionIndex) {
        console.log("我在这里")
        this.getQuestionList();
        this.$confirm('确定删除此题目?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.$post('/api/question/delete', {
            questionId: this.detail[questionIndex].questionId,
          })
            .then(data => {
              this.detail.splice(questionIndex, 1);
              this.$message({
                type: 'success',
                message: '删除成功!'
              });
              this.questionnaire.questions.splice(questionIndex, 1);
            }).catch(err => {
              this.$message({
                type: 'error',
                message: data.msg
              });
            })
        });
         this.getQuestionList();

      },
      changeQuestionFormat(questionIndex, oldFormat, oldIcon, format) {
        this.questionnaire.questions[questionIndex].oldFormat = oldFormat
        this.questionnaire.questions[questionIndex].oldIcon = oldIcon
        this.questionnaire.questions[questionIndex].type = format.name
        this.questionnaire.questions[questionIndex].questionIcon = format.icon
      },
      addOption(questionIndex) {
        this.questionnaire.questions[questionIndex].options.push({
          title: '',
        })
      },
      deleteOption(questionIndex, args) {
        this.questionnaire.questions[questionIndex].options.splice(args, 1)
      },
      addRow(questionIndex) {
        this.questionnaire.questions[questionIndex].rows.push({
          row: '',
        })
      },
      deleteRow(questionIndex, args) {
        this.questionnaire.questions[questionIndex].rows.splice(args, 1)
      },
      addColumn(questionIndex) {
        this.questionnaire.questions[questionIndex].cols.push({
          col: '',
        })
      },
      deleteColumn(questionIndex, args) {
        this.questionnaire.questions[questionIndex].cols.splice(args, 1)
      },
      cancelFormFile(questionIndex) {
        this.questionnaire.questions[questionIndex].type =
          this.questionnaire.questions[questionIndex].oldFormat
        this.questionnaire.questions[questionIndex].questionIcon =
          this.questionnaire.questions[questionIndex].oldIcon
      },
      advanceToFormFile(questionIndex) {
        this.questionnaire.questions[questionIndex].type = 'Actually file upload'
      },
      focusForm(index) {
        this.focusedFormIndex = index
        // In case the index is out of range when deleting the last questionnaire while focusing on the form
        if (this.focusedFormIndex > this.questionnaire.questions.length - 1) {
          this.focusedFormIndex -= 1
        }
      },
      isFocused(index) {
        if (index == this.focusedFormIndex) {
          return true
        } else {
          return false
        }
      },
      onEnd(event) {
        this.focusedFormIndex = event.newIndex
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
          getQuestionList();
        },
        init(questionnaireId, questionTitle, desc,questionnaire) {
          this.questionnaireId = questionnaireId;
          this.questionTitle = questionTitle;
          this.desc = desc;
          this.questionnaire=questionnaire;
          this.getQuestionList();
        },
        //获取问题列表(问卷内容)
        getQuestionList() {
          this.detail = [];
          const _this = this;
          this.loading = true;
          queryQuestions({
            questionnaireId: this.questionnaireId,
          })
            .then(data => {
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
                  _this.detail.push(element);
                  this.detail.push(element)
                })
              }
            })
            answerOpera({
                   questionnaireId: this.questionnaireId,
                 }).then(data =>{
                   this.title=data.data.title;
                   this.desc=data.data.desc;
                   this.questionnaire = data.data;
                 });
                 console.log(this.questionnaire);
        },
        //确认添加/保存题目
        checkAddQuestion() {
          //添加保存问题
          let newItem = {};//新添加的问题对象
          newItem = {
            type: this.willAddQuestion.type,
            questionId:this.willAddQuestion.questionId,
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
        //切换问题类型
        typeChange(value) {
          console.log(value);
          this.willAddQuestion.type = value;
          this.willAddQuestion.text = '';
          this.row = 1;
        },
    },
  }
</script>

<style scoped>

  .container {
    max-width: 770px;
    border-radius: 10px 10px 10px 10px;
  }

  .whole-container {
    margin-bottom: 50px;
  }

  @media (max-width: 450px) {
    .whole-container {
      margin-bottom: 100px;
    }
  }

  .handle-icon-container {
    text-align: center;
  }

  .switch-label {
    margin-right: 15px;
    margin-left: 15px;
    color: #616161;
    font-weight: bold;
  }

  .switch {
    padding-top: 8px;
  }

  .card-actions {
    height: 45px;
  }

  .actions-icon {
    margin: 0 20px 0 5px;
  }

  .transparentBack {
    background-color: transparent !important;
  }

  .theme--light.v-tabs-items {
    background-color: transparent;
  }
</style>
