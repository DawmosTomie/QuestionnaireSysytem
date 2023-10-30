<template>
	<div class="list-main">
		<div class="search-list">

			<el-row :gutter="20">
        <el-col :span="3" style="width:0%;">
          <el-button type="primary"  @click="addQuestionnaires">添加模板</el-button>
        </el-col>
        <el-col :span="12" :offset="3">
          <el-input v-model="title" class="search-text mr10" placeholder="请输入内容"></el-input>
          <el-button type="primary" @click="queryQuestionnaires">搜索</el-button>
        </el-col>
      </el-row>
	
		</div>
						
		<!-- 列表 -->
		<el-table ref="multipleTable" :data="questionDatas" border tooltip-effect="dark" style="width: 100%">
		    <el-table-column prop="questionnaireId" label="问卷编号" width="120"></el-table-column>
            <el-table-column prop="title" label="标题" width="120"></el-table-column>
            <el-table-column prop="surveyCrowd" label="调查人群" width="120"></el-table-column>
		    <el-table-column prop="status" label="状态" width="120">
          <template slot-scope="scope">
              <p v-if="scope.row.status=='0'">未发布</p>
              <p v-if="scope.row.status=='1'">已发布</p>
          </template>
        </el-table-column>
		    <el-table-column prop="desc" label="描述" width="200"></el-table-column>
		    <el-table-column prop="startTime" label="开始时间" width="180"></el-table-column>
		    <el-table-column prop="endTime" label="结束时间" width="180"></el-table-column>
		    <el-table-column prop="isTemplate" label="是否作为模板" width="120">
          <template slot-scope="scope">
              <p v-if="scope.row.status=='0'">否</p>
              <p v-if="scope.row.status=='1'">是</p>
          </template>
        </el-table-column>
		    <el-table-column prop="projectId" label="所属文件夹" width="120"></el-table-column>
		    <el-table-column width="400" fixed="right" label="操作">
                <template slot slot-scope="scope">	
                    <el-button size="mini" type="success" @click="pushWj(scope.$index, scope.row)" v-if="scope.row.status == 0">发布</el-button>
                    <el-button size="mini" type="info" @click="pushWj(scope.$index, scope.row)" v-if="scope.row.status == 1">暂停</el-button>
                    <el-button size="mini" type="primary" @click="handleEdit(scope.$index, scope.row)">修改</el-button>
                    <el-button size="mini" type="warning" @click="previewWj(scope.$index, scope.row)">预览</el-button>
                    <el-button size="mini" type="danger" @click="deleteWj(scope.$index, scope.row)">删除</el-button>
                </template>		
		    </el-table-column>		
		</el-table>
				
		<!--分页-->
		<div class="pagination">
			<el-pagination background
		      @size-change="handleSizeChange"
		      @current-change="handleCurrentChange"
		      :current-page="page.pageNum"
		      :page-sizes="[5, 10, 15, 20]"
          :page-count="page.pages"
		      :page-size="page.pageSize"
		      layout="total, sizes, prev, pager, next, jumper"
		      :total="page.totalSize">
		    </el-pagination>
		</div>
		
		<!-- 删除确认框 -->
		<el-dialog title="提示" :visible.sync="centerDialogVisible" width="30%" center>
		  <span>确定删除当前数据</span>
		  <span slot="footer" class="dialog-footer">
		    <el-button @click="centerDialogVisible = false">取 消</el-button>
		    <el-button type="primary" @click="centerDialogVisible = false">确 定</el-button>
		  </span>
		</el-dialog>

    <el-dialog title="添加问卷" :visible.sync="editDialog" class="dialog">
      <QuestionnaireEdit v-if="editDialog" :isAdd="isAdd" @closeQuestionnaireDialog="closeDialog" :questionnaireId="form.questionnaireId"></QuestionnaireEdit>
    </el-dialog>

		
	</div>
</template>
										
<script>
import QuestionnaireEdit from './Edit.vue'
  export default {
  components: {
    QuestionnaireEdit,
  },
    data() {
      return {
      	//列表控件
        questionDatas: [],
        validTime:[],
        		
        //时间控件
        value1: "",	
        
        //下拉控件
        options: [{value: '选项1',label: '黄金糕'},
	       {value: '选项2',label: '双皮奶'},
	       {value: '选项3',label: '蚵仔煎'},
	       {value: '选项4',label: '龙须面'},
	       {value: '选项5',label: '北京烤鸭'}],
        value: '',
        
        //输入框
        title: '',
        
        //删除确认框
        centerDialogVisible: false,
        editDialog:false,
        isAdd:true,
        
        form: {},
        formLabelWidth: "100px",
        page:{
            pageNum:1,
            pageSize:5,
            pages:1,
            totalSize:0
        }
        
      }
    },
		
    methods: {
      handleSizeChange(val) {
        this.page.pageSize = val
        this.queryQuestionnaires()
      },
      handleCurrentChange(val) {
        this.page.pageNum = val
        this.queryQuestionnaires()
      },
      handleEdit(index, row) {
      	this.editDialog = true;
        this.isAdd = false
        this.form = row
      },
      closeDialog(){
      	this.editDialog = false;
        this.queryQuestionnaires()
      },
     addQuestionnaires(){
      this.editDialog = true
      this.isAdd = true
      this.form = {}
     },
      queryQuestionnaires(){
        const _this = this
        this.$post('/api/questionnaire/all',{
            page:this.page,
            queryParam:{
                title:_this.title
            }
        }).then(data => {
            _this.page = {
                pageSize:data.pageSize,
                pageNum:data.pageNum,
                totalSize:data.total,
                pages:data.pages
            }
            _this.questionDatas = data.list
        })
      },
      //发布问卷/暂停问卷
    pushWj(index,data) {
      let status = 1;
      if (data.status == 1)
        status = 0;
      this.$post('/api/questionnaire/publish',{
        questionnaireId: data.questionnaireId,
        status: status
      })
        .then(data => {
          this.$message({
            type: 'success',
            message: status == 1 ? '问卷发布成功！' : '问卷暂停成功！'
          });
          this.queryQuestionnaires();
        }).catch(err => {
            this.$message({
              type: 'error',
              message: data.message
            });
        })
    },
    //预览问卷
    previewWj(index,row) {
      let url = window.location.origin + "/display/" + row.questionnaireId;//问卷链接
      window.open(url);
    },
    //删除问卷
    deleteWj(index,row) {
      this.$confirm('确定删除' + row.title + '? 删除后不可恢复！', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.loading = true;
        this.$post('/api/questionnaire/delete',{questionnaireId:row.questionnaireId})
          .then(data => {
              this.$message({
                type: 'success',
                message: '删除成功!'
              });
              this.loading = false;
              this.queryQuestionnaires();
              this.defaultActive = 1;
          }).catch(err => {
              this.$message({
                type: 'error',
                message: err
              });
          })
      });
    },
    },
    
    mounted(){
        this.queryQuestionnaires()
    }
  }
</script>

<style scoped>
	.list-main {
		padding: 20px;
		margin: 20px;
		background: #fff;
	}
	.search-list {
		padding-bottom: 20px;
		
		
	}	
    .search-list .search-text {
			width: 200px;
		}

</style>