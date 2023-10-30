<!--
程序名：问卷填写页面
功能：用户打开问卷链接对问卷进行填写
-->
<template>
    <div class="display">
        <div v-if="!hasPermission">
            <v-card shadow="never" style="font-size: 18px;line-height: 40px;padding: 10px;">
                <i class="el-icon-circle-close" style="color: green;font-size: 50px"></i>
                <br />暂无权限
                <br />
                <br />
                <el-divider></el-divider>
            </v-card>
        </div>
        <div v-else class="content">
            <h3>{{ title }}</h3>
            <div class="top" v-if="desc != ''">{{ desc }}</div>
            <v-card class="box-card" v-for="(item, index) in detail">

                <div class="questionImage">
                    <v-img v-if="item.image" :src="'data:image/png;base64,' + item.image"></v-img>
                </div>
                <div class="questionTitle">
                    <!--显示必填标识-->
                    <span style="color: #F56C6C;">
                        <span v-if="item.isMust">*</span>
                        <span v-else>&nbsp;</span>
                    </span>
                    <v-card-title>{{ (index + 1) + '.' + item.questionTitle }}</v-card-title>
                </div>

                <!--单选题展示-->
                <div class="text item">
                    <v-radio-group v-if="item.type == 'radio'" v-model="item.radioValue">
                        <v-radio v-for="optionItem in item.options" :key="n" :label="optionItem.title"
                            :value="optionItem.optionId"></v-radio>
                    </v-radio-group>
                </div>

                <!--多选题展示-->
                <div v-if="item.type == 'checkbox'" class="text item" v-for="optionItem in item.options">
                    <v-checkbox :label="optionItem.optionId" style="margin: 5px;" :value="optionItem.title"
                        v-model="item.checkboxValue">
                    </v-checkbox>
                </div>

                <!--填空题展示-->
                <v-textarea v-if="item.type == 'text'" type="textarea" :rows="item.row" v-model="item.textValue" clearable
                    resize="none">
                </v-textarea>
            </v-card>
            <el-button type="primary" style="margin: 5px;" @click="submit" :loading="submitLoading">{{ submitText }}
            </el-button>
        </div>
    </div>
</template>
<script>
import { answerOpera, answerQuestionnaire } from '../utils/api'
export default {
    data() {
        return {
            title: '',
            desc: '',
            detail: [],
            startTimestamp: 0, //填写问卷开始时间戳 毫秒
            submitLoading: false, //提交按钮 加载中状态
            submitText: '提交', //提交按钮文字
            hasPermission: false,
        }
    },
    mounted() {
        var questionnaireId = this.$route.params.questionnaireId
        this.$post('/api/questionnaire/hasPermission', { questionnaireId: questionnaireId }).then(data => {
            this.hasPermission = data
            if (data) {
                answerOpera({
                    questionnaireId: questionnaireId,
                }).then((data) => {
                    if (data.code == '666') {
                        let questionnaire = data.data
                        this.title = questionnaire.title
                        this.desc = questionnaire.desc
                        questionnaire.questions.forEach((element) => {
                            if (element.type == 'radio') {
                                element.radioValue = ''
                            } else if (element.type == 'checkbox') {
                                element.checkboxValue = []
                            } else {
                                element.textValue = ''
                            }
                            this.detail.push(element)
                        })
                        document.title = questionnaire.title
                    } else {
                        this.$message({
                            type: 'error',
                            message: data.msg,
                        })
                    }
                })
            }
        }).catch(err => {
            this.hasPermission = false;
        })

        this.startTimestamp = new Date().getTime() //时间戳 毫秒
    },
    methods: {
        //提交问卷
        submit() {
            this.submitLoading = true
            this.submitText = '提交中'
            var questionnaireId = this.$route.params.questionnaireId
            let anwserTime = parseInt(
                (new Date().getTime() - this.startTimestamp) / 1000
            ) //填写问卷用时 秒
            answerQuestionnaire({
                questionnaireId: questionnaireId,
                anwserTime: anwserTime,
                questionOptionVos: this.detail,
            }).then((data) => {
                console.log(data)
                //提交成功
                this.submitLoading = false
                this.submitText = '提交'
                this.$router.push({ path: '/thankyou' }) //跳到欢迎页
            }).catch(err => {
                this.submitLoading = false
                this.submitText = '提交'
                this.$notify.error({
                    title: '错误',
                    message: data.msg,
                })
            })
        },
    },
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
    border-bottom: 3px solid #409eff;
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
  