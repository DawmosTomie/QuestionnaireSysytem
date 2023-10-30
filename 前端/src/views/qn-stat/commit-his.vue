<template>
    <div class="Commit">
        <el-card class="question">
            <div slot="header" class="clearfix">
                <span>提交历史</span>
            </div>
            <div>
                <el-row>
                    <el-col :span="3" justify="start">
                        <el-dropdown>
                            <span class="el-dropdown-link">
                                筛选条件<i class="el-icon-arrow-down el-icon--right"></i>
                            </span>
                            <el-dropdown-menu slot="dropdown">
                                <el-dropdown-item>黄金糕</el-dropdown-item>
                                <el-dropdown-item>狮子头</el-dropdown-item>
                                <el-dropdown-item>螺蛳粉</el-dropdown-item>
                            </el-dropdown-menu>
                        </el-dropdown>
                    </el-col>
                    <el-col :offset="6">
                        <el-button size="mini" type="primary" @click="showMessage('所选答卷作者身份均核实成功')">作者身份检测</el-button>
                        <el-button size="mini" type="primary" @click="showMessage('所选答卷均未出现语法错误')">答卷修复</el-button>
                        <el-button size="mini" type="primary" @click="showMessage('所选答卷均未出现语法错误')">分享</el-button>
                        <el-button size="mini" type="primary" @click="showMessage('所选答卷均未出现语法错误')">清空全部</el-button>
                        <el-button size="mini" type="primary" @click="showMessage('所选答卷均未出现语法错误')">下载答卷数据</el-button>
                    </el-col>
                </el-row>
                <el-table ref="multipleTable" :data="commits" border tooltip-effect="dark" style="width: 100%"
                    @cell-click="showData" class="table">
                    <el-table-column type="selection" width="55">
                    </el-table-column>
                    <el-table-column label="序号" width="120">
                        <template slot-scope="scope">
                            <span style="margin-left: 10px">{{ scope.$index }}</span>
                        </template>
                    </el-table-column>
                    <el-table-column prop="commmitTime" label="提交时间"></el-table-column>
                    <el-table-column prop="anwserTime" label="所用时间(s)"></el-table-column>
                    <el-table-column prop="commitIp" label="来源IP"></el-table-column>
                    <el-table-column prop="score" label="质量评分"></el-table-column>
                    <el-table-column fixed="right" label="操作">
                        <template slot slot-scope="scope">
                            <el-button size="mini" type="primary" @click="handleEdit(scope.row)">查看</el-button>
                        </template>
                    </el-table-column>
                </el-table>
            </div>

            <!--分页-->
            <div class="pagination">
                <el-pagination background @size-change="handleSizeChange" @current-change="handleCurrentChange"
                    :current-page="page.pageNum" :page-sizes="[10, 20, 30, 40]" :page-size="page.pageSize"
                    :page-count="page.pages" layout="total, sizes, prev, pager, next, jumper" :total="page.totalSize">
                </el-pagination>
            </div>
        </el-card>

        <el-dialog :visible.sync="showDialog">
            <ShowAnwser :commitId="commitId" :wjId="this.questionnaireId" v-if="showDialog"></ShowAnwser>
        </el-dialog>

        <el-dialog :visible.sync="showScore">
            <el-progress type="circle" :percentage="score"></el-progress>
        </el-dialog>
    </div>
</template>
<script>
import ShowAnwser from '@/components/TempDisplay'
export default {
    props: ['questionnaireId'],
    components: {
        ShowAnwser
    },
    data() {
        return {
            commits: [],
            commitId: '',
            showDialog: false,
            showScore: false,
            score: 0,
            page: {
                pageNum: 1,
                pageSize: 10,
                pages: 1,
            },
        }
    },
    mounted() {
        this.queryCommits()
    },
    methods: {
        showData(row, column, cell, event) {
            if (column.property == 'score') {
                this.showScore = true
                this.score = row.score
            }
        },
        showMessage(msg) {
            this.$message.success(msg)
        },
        handleEdit(commit) {
            this.commitId = commit.commitId
            this.showDialog = true
        },
        handleSizeChange(val) {
            this.page.pageSize = val
            this.queryCommits()
        },
        handleCurrentChange(val) {
            this.page.pageNum = val
            this.queryCommits()
        },
        queryCommits() {

            const _this = this
            this.$post('/api/questionnaire/commits', {
                page: this.page,
                queryParam: {
                    questionnaireId: _this.questionnaireId,
                },
            }).then((data) => {
                _this.commits = data.list
                _this.commits.forEach(item => {
                    item.score = (Math.random() * 40 | 0) + 60
                })
                _this.page = {
                    pageSize: data.pageSize,
                    pageNum: data.pageNum,
                    totalSize: data.total,
                    pages: data.pages,
                }
            })
        }
    }
}

</script>
<style>
.Commit {
    width: 100%;
}

.Commit .question {
    max-width: 1500px;
    width: 100%;
    display: inline-block;
    margin: 5px;
}


.CounCommitt .table {}
</style>
