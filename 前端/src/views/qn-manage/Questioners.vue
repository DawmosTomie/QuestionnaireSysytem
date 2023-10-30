<template>
  <div class="list-main">
    <div class="search-list">
      <el-row>
        <el-col :span="3" style="width:0%">
          <el-upload action="/api/respondent/batchUpload" :show-file-list="false" :before-upload="beforeUpload"
            :on-success="queryUsers">
            <el-button type="primary">批量导入</el-button>
          </el-upload>
        </el-col>
        <el-col :span="12" :offset="3">
          <el-input v-model="username" class="search-text mr10" placeholder="请输入内容"></el-input>
          <el-button type="primary" @click="queryUsers">搜索</el-button>
        </el-col>
      </el-row>
    </div>

    <!-- 列表 -->
    <el-table ref="multipleTable" :data="tableData3" border tooltip-effect="dark" style="width: 100%">
      <el-table-column label="用户账号" >
        <template slot-scope="scope">
          <el-badge :value="scope.row.type" class="item" type="primary">
            <span style="margin-left: 10px">{{ scope.row.username }}</span>
          </el-badge>
        </template>
      </el-table-column>
      <el-table-column prop="status" label="状态" >
        <template slot-scope="scope">
          <p v-if="scope.row.status == '02'">无效</p>
          <p v-if="scope.row.status == '01'">有效</p>
        </template>
      </el-table-column>
      <el-table-column prop="telephone" label="手机号"></el-table-column>
      <el-table-column prop="realName" label="姓名" ></el-table-column>
      <el-table-column width="400" label="操作">
        <template slot slot-scope="scope">
          <el-button size="mini" type="primary" @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
          <el-button size="mini" type="danger" @click="handleDelete(scope.$index, scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!--分页-->
    <div class="pagination">
      <el-pagination background @size-change="handleSizeChange" @current-change="handleCurrentChange"
        :current-page="page.pageNum" :page-sizes="[10, 20, 30, 40]" :page-size="page.pageSize" :page-count="page.pages"
        layout="total, sizes, prev, pager, next, jumper" :total="page.totalSize"></el-pagination>
    </div>

    <!-- 删除确认框 -->
    <el-dialog title="提示" :visible.sync="centerDialogVisible" width="30%" center>
      <span>确定删除当前数据</span>
      <span slot="footer" class="dialog-footer">
        <el-button @click="centerDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="centerDialogVisible = false">确 定</el-button>
      </span>
    </el-dialog>

    <!-- 编辑弹窗 -->
    <el-dialog title="编辑" :visible.sync="dialogFormVisible" width="50%">
      <div>
        <el-tag>女性</el-tag>
        <el-tag type="success">学生</el-tag>
        <el-tag type="info">电影</el-tag>
      </div>
      <el-form :model="form" label-position="left" ref="questionerForm">
        <el-form-item label="账号" required :label-width="formLabelWidth" prop="username">
          <el-input v-model="form.username" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="姓名" required :label-width="formLabelWidth" prop="realName">
          <el-input v-model="form.realName" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="手机号" required :label-width="formLabelWidth" prop="telephone">
          <el-input v-model="form.telephone" auto-complete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="commitUser()">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>
										
<script>
import {
  queryUserList,
  modifyUserStatus,
  deleteUser,
  resetPwd,
  modifyUserInfo,
  addUserInfo,
} from '../../utils/api'
import { hex_md5 } from 'js-md5'
export default {
  data() {
    return {
      //列表控件
      tableData3: [],
      validTime: [],

      //时间控件
      value1: '',
      value: '',

      //输入框
      username: '',

      //删除确认框
      centerDialogVisible: false,

      //编辑弹窗
      dialogFormVisible: false,
      form: {},
      formLabelWidth: '100px',
      page: {
        pageNum: 1,
        pageSize: 10,
        pages: 1,
      },
    }
  },

  methods: {
    handleSizeChange(val) {
      this.page.pageSize = val
      this.queryUsers()
    },
    handleCurrentChange(val) {
      this.page.pageNum = val
      this.queryUsers()
    },
    handleEdit(index, row) {
      this.dialogFormVisible = true
      this.form = row
    },
    handleAdd() {
      this.form = { roleId: '11111111' }
      this.dialogFormVisible = true
    },
    commitUser() {
      this.form.roleId = 1
      this.$refs.questionerForm.validate((valid) => {
        if (valid) {
          if (!this.form.id) {
            this.$post('/api/respondent/add', this.form).then((res) => {
              this.$message({
                message: '答者信息添加成功',
                type: 'success',
              })
              this.dialogFormVisible = false
              this.queryUsers()
            })
          } else {
            this.$post('/api/respondent/update', this.form).then((res) => {
              this.$message({
                message: '答者信息修改成功',
                type: 'success',
              })
              this.dialogFormVisible = false
              this.queryUsers()
            })
          }
        }
      })
    },
    resetPwdCur(index, row) {
      resetPwd(row)
        .then((res) => {
          this.$message({
            message: '密码成功重置为11111111',
            type: 'success',
          })
        })
        .catch((err) => {
          this.$message.error(err)
        })
    },
    handleDelete(index, row) {
      this.centerDialogVisible = true
      deleteUser(row)
      this.queryUsers()
    },
    queryUsers() {
      const _this = this
      const typeArray = ['男性', '女性', '电影', '80后', '90后', '医生', '程序员', '公务员']
      this.$post('/api/respondent/query', {
        page: this.page,
        queryParam: {
          userName: _this.username,
        },
      }).then((data) => {
        _this.tableData3 = data.list
        _this.tableData3.forEach((item, index) => {
          item.type = typeArray[index % 8]
        })
        _this.page = {
          pageSize: data.pageSize,
          pageNum: data.pageNum,
          totalSize: data.total,
          pages: data.pages,
        }
      })
    },

    beforeUpload(file) {
      const msg = file.name.substring(file.name.lastIndexOf('.') + 1)
      if (msg !== 'xls' && msg !== 'xlsx') {
        this.$message({
          message: '上传文件只能是xls,xlsx格式',
          type: 'error'
        });
        return false;
      }
      return true;
    },
  },

  mounted() {
    this.queryUsers()
  },
}
</script>

<style scoped>
.list-main {
  padding: 20px;
  margin: 20px;
  width: 100%;
  background: #fff;
}

.search-list {
  padding-bottom: 20px;
}

.search-list .search-text {
  width: 200px;
}

.item {
  margin-top: 10px;
  margin-right: 40px;
}
</style>