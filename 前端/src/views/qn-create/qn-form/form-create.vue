<template>
  <div style="width:100%">
    <v-app v-bind:style="{ background: backgroundColor }">
      <Header ref="header" @changeThemeColor="(...args) => changeThemeColor(...args)" :title="title"
        @changeBackgroundColor="(...args) => changeBackgroundColor(...args)" @saveQuestion="checkAddQuestion"
        @saveSettings="saveSettings"></Header>
      <PageForm ref="questionForm" :themeColor="themeColor" :backgroudColor="backgroundColor" />
      <!-- <div class="fixed-button">

      </div> -->
    </v-app>
  </div>
</template>

<script>
  import Header from '@/components/include/Header'
  import PageForm from '@/components/pages/PageForm'
  import HelpButton from '@/components/include/HelpButton'
  import {
    answerOpera,
    queryQuestions
  } from '@/utils/api'

  export default {
    name: 'App',
    components: {
      Header,
      PageForm,
      HelpButton,
    },
    data() {
      return {
        themeColor: 'blue lighten-1',
        backgroundColor: '#CFE9F5',
        title: '',
        detail: [],
      }
    },
    methods: {
      changeThemeColor(args) {
        this.themeColor = args
      },
      changeBackgroundColor(args) {
        this.backgroundColor = args
      },
      saveSettings(val) {
        this.$refs.questionForm.questionnaire = {
          ...this.$refs.questionForm.questionnaire,
          ...val
        }
      },
      checkAddQuestion() {
        this.$message({
          type: 'success',
          message: '保存成功!',
        })
        /**this.$refs.questionForm.questionnaire.questions.forEach((item, i) => {
          item.sortNo = i
        })
        this.$post(
          '/api/questionnaire/add',
          this.$refs.questionForm.questionnaire
        ).then((data) => {
          // this.$refs.uploadImage.submit()
          this.$message({
            type: 'success',
            message: '保存成功!',
          })
        })**/
      },
    },
    mounted() {
      if (this.$route.query.title || this.$route.params.title) {
        this.$refs.questionForm.questionnaire.title = this.$route.query.title
      }
      if (this.$route.params.questionnaireId) {
        this.$post('/api/questionnaire/queryQuestions', {
          questionnaireId: this.$route.params.questionnaireId,
        }).then((data) => {
            data.forEach((element) => {
              if (element.type == 'radio') {
                element.radioValue = ''
              } else if (element.type == 'checkbox') {
                element.checkboxValue = []
              } else {
                element.textValue = ''
              }
              if (element.isMust) {
                element.required = true
              }
              this.detail.push(element);
            });
            answerOpera({
              questionnaireId: this.$route.params.questionnaireId,
            }).then(data => {
              this.title = data.data.title;
              this.desc = data.data.desc;
              this.$refs.questionForm.init(this.$route.params.questionnaireId, this.title, this.desc, data.data)
              //this.$refs.questionForm.questionnaire = data.data;
              this.$refs.header.questionnaire = data.data;
              // this.$refs.questionForm.questionnaireId=this.$route.params.questionnaireId;
              //console.log(this.$refs.questionForm.questionnaireId);
            });
          } //this.title = this.$refs.questionForm.questionnaire.title
        )
      }
      if (this.$route.query.questionnaireId) {
        this.$post('/api/questionnaire/queryQuestions', {
          questionnaireId: this.$route.query.questionnaireId,
        }).then((data) => {
          data.forEach((element) => {
            if (element.type == 'radio') {
              element.radioValue = ''
            } else if (element.type == 'checkbox') {
              element.checkboxValue = []
            } else {
              element.textValue = ''
            }
            if (element.isMust) {
              element.required = true
            }
            this.detail.push(element);
          });
          answerOpera({
            questionnaireId: this.$route.query.questionnaireId,
          }).then(data => {
            this.title = data.data.title;
            this.desc = data.data.desc;
            this.$refs.questionForm.init(this.$route.query.questionnaireId, this.title, this.desc, data.data)
            //this.$refs.questionForm.questionnaire = data.data;
            this.$refs.header.questionnaire = data.data;
            // this.$refs.questionForm.questionnaireId=this.$route.params.questionnaireId;
            //console.log(this.$refs.questionForm.questionnaireId);
          });
          //this.title = this.$refs.questionForm.questionnaire.title
        })
      }
    }

  }
</script>

<style>
  /* Glocal settings here */
  * {
    text-transform: none !important;
  }

  .fixed-button {
    position: fixed;
    bottom: 1px;
    right: 1px;
  }
</style>
