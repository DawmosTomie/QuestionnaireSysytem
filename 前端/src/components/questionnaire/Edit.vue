<template>
    <div>
        <el-form ref="form" :model="questionnaire" label-width="80px">
            <el-form-item label="问卷标题" style="width: 100%;" required>
                <el-input v-model="questionnaire.title" placeholder="请输入问卷标题"></el-input>
            </el-form-item>
            <el-form-item label="问卷描述" style="width: 100%;">
                <el-input v-model="questionnaire.desc" type="textarea" placeholder="请输入问卷描述" rows="5"></el-input>
            </el-form-item>
            <el-form-item label="调查人群" style="width: 100%;">
                <el-input v-model="questionnaire.surveyCrowd" placeholder="请输入调查人群"></el-input>
            </el-form-item>
            <el-form-item label="是否添加为模板" label-width="30">
                <el-switch v-model="questionnaire.isTemplate" :active-value="1" :inactive-value="0"></el-switch>
            </el-form-item>
            <el-form-item label="有效期" label-width="80px">
                <el-col :span="11">
                    <el-form-item prop="startTime">
                        <el-date-picker type="datetime" value-format="yyyy-MM-dd HH:mm:ss" placeholder="开始日期"
                            v-model="questionnaire.startTime" style="width: 100%;"></el-date-picker>
                    </el-form-item>
                </el-col>
                <el-col class="line" :span="2">-</el-col>
                <el-col :span="11">
                    <el-form-item prop="endTime">
                        <el-date-picker placeholder="结束日期" value-format="yyyy-MM-dd HH:mm:ss" type="datetime"
                            v-model="questionnaire.endTime" style="width: 100%;"></el-date-picker>
                    </el-form-item>
                </el-col>
            </el-form-item>
        </el-form>
        <div style="width: 100%;text-align: right">
            <el-button style="margin-left: 10px;" @click="openTemp">从模板库创建</el-button>
            <el-button style="margin-left: 10px;" @click="dialogShow">取消</el-button>
            <el-button type="primary" style="margin-left: 10px;" @click="addWj">确定</el-button>
        </div>


        <!--模板库弹窗-->
        <el-dialog title="模板库" :visible.sync="tempDialog" :close-on-click-modal="false" class="dialog" append-to-body>
            <el-input placeholder="请输入关键词搜索" prefix-icon="el-icon-search" v-model="tempSearchText"></el-input>
            <el-table :data="tempData" style="width: 100%;" v-loading="tempLoading" element-loading-text="加载中...">
                <el-table-column prop="title" label="模板名" width="250"></el-table-column>
                <el-table-column prop="createdBy" label="创建者"></el-table-column>
                <el-table-column label="预览">
                    <el-link slot-scope="scope" type="primary" @click="lookTempWj(scope.row)">预览</el-link>
                </el-table-column>
                <el-table-column label="操作">
                    <el-link slot-scope="scope" type="primary" @click="useTemp(scope.row)">使用此模板</el-link>
                </el-table-column>
            </el-table>
            <el-pagination layout="prev, pager, next" :total="page.totalSize" @current-change="changeTempPage"
                :page-size="page.pageSize">
            </el-pagination>
        </el-dialog>
    </div>
</template>
<script>

export default {
    data() {
        return {
            questionnaire: {},
            tempDialog: false,
            tempSearchText: '',
            tempLoading: false,
            tempData: [],
            page: {
                pageNum: 1,
                pageSize: 5,
                pages: 1,
                totalSize: 0
            }

        }
    },
    props: {
        isAdd: {
            type: Boolean,
            required: true,
            default: true
        },
        questionnaireId:String,
        projectId:String
    },
    methods: {
        addWj() {
            if (!this.questionnaire.title) {
                this.$message({
                    type: 'error',
                    message: '标题不能为空'
                });
                return;
            }
            if (!this.isAdd) {
                this.$post('/api/questionnaire/update', this.questionnaire)
                    .then(res => {
                        this.$message({
                            type: 'success',
                            message: '保存成功!'
                        });
                        this.$emit('closeQuestionnaireDialog')
                    }).catch(err => {
                        this.$message({
                            type: 'error',
                            message: err
                        });
                    })

            } else {
                const projectId = this.curProjectId
                this.$post('/api/questionnaire/add', {
                    projectId: projectId,
                    title: this.questionnaire.title,
                    id: this.questionnaire.id,
                    desc: this.questionnaire.desc,
                    isTemplate:this.questionnaire.isTemplate,
                })
                    .then(data => {
                        this.$message({
                            type: 'success',
                            message: '保存成功!'
                        });
                        this.$emit('closeQuestionnaireDialog')
                    }).catch(err => {
                        this.$message({
                            type: 'error',
                            message: err
                        });
                    })
            }
        },
        dialogShow() {
            this.$emit('closeQuestionnaireDialog')
        },

        //使用问卷
        useTemp(item) {
            this.tempLoading = true;
            this.$post('/api/questionnaire/useTemplate', {
                ...this.willAddWj, questionnaireId: item.questionnaireId, projectId: this.curProjectId
            })
                .then(data => {
                    this.tempLoading = false;
                    this.$message({
                        type: 'success',
                        message: '使用模板成功！',
                        showClose: true
                    });
                    this.tempDialog = false;
                    this.$emit('closeQuestionnaireDialog')
                }).catch(err => {
                    this.$message({
                        type: 'error',
                        message: err
                    });
                })
        },
        //打开问卷库
        openTemp() {
            this.tempDialog = true;
            this.changeTempPage(1);
            //        this.getTempWjList();
        },
        //改变模板库页码
        changeTempPage(page) {
            this.tempLoading = true;
            this.$post('/api/questionnaire/templates', {
                page: page
            })
                .then(data => {
                    this.page = {
                        pageSize: data.pageSize,
                        pageNum: data.pageNum,
                        totalSize: data.total,
                        pages: data.pages
                    }
                    this.tempData = data.list
                    this.tempLoading = false;
                })

        },
        //预览模板问卷
        lookTempWj(item) {
            let url = window.location.origin + "/tempdisplay/" + item.questionnaireId;//问卷链接
            console.log(url);
            window.open(url);
        },
    },
    mounted(){
        if(this.questionnaireId){
            this.$post('/api/questionnaire/get',{
                questionnaireId:this.questionnaireId
            }).then(data =>{
                this.questionnaire = data
            })
        }
    }
}
</script>