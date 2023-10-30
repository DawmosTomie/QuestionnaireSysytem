<template>
  <div class="list-main">
    <div class="search-list">

      <el-row>
        <el-col :span="6" style="width:auto">
          <el-row>
            <el-col :span="6">
              <el-button @click="handleAdd">创建用户</el-button>
            </el-col>

            <el-col :span="6" :offset="8">
              <el-upload action="/api/admin/batchUpload" :show-file-list="false" :before-upload="beforeUpload"
                :on-success="queryUsers">
                <el-button type="primary">批量导入</el-button>
              </el-upload>
            </el-col>
          </el-row>
        </el-col>
        <el-col :span="12" :offset="3">
          <el-input v-model="username" class="search-text mr10" placeholder="请输入内容"></el-input>
          <el-button type="primary" @click="queryUsers">搜索</el-button>
        </el-col>
      </el-row>

    </div>

    <!-- 列表 -->
    <el-table ref="multipleTable" :data="tableData3" border tooltip-effect="dark" style="width: 100%">
      <el-table-column prop="username" label="用户账号" width="120"></el-table-column>
      <el-table-column prop="status" label="状态" width="120">
        <template slot-scope="scope">
          <p v-if="scope.row.status == '02'">无效</p>
          <p v-if="scope.row.status == '01'">有效</p>
        </template>
      </el-table-column>
      <el-table-column prop="reaalName" label="姓名" width="120"></el-table-column>
      <el-table-column prop="gender" label="性别" width="120"></el-table-column>
      <el-table-column prop="telephone" label="手机号" width="120"></el-table-column>
      <el-table-column prop="email" label="邮箱" width="120"></el-table-column>
      <el-table-column width="400" fixed="right" label="操作">
        <template slot slot-scope="scope">
          <el-button size="mini" type="primary" @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
          <el-button size="mini" type="danger" @click="handleDelete(scope.$index, scope.row)">删除</el-button>
          <el-button size="mini" type="warning" @click="invalidUser(scope.$index, scope.row)"
            v-if="scope.row.status === '01'">关闭</el-button>
          <el-button size="mini" type="success" @click="validUser(scope.$index, scope.row)"
            v-if="scope.row.status === '02'">开启</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!--分页-->
    <div class="pagination">
      <el-pagination background @size-change="handleSizeChange" @current-change="handleCurrentChange"
        :current-page="page.pageNum" :page-sizes="[10, 20, 30, 40]" :page-size="page.pageSize" :page-count="page.pages"
        layout="total, sizes, prev, pager, next, jumper" :total="page.totalSize">
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

    <!-- 编辑弹窗 -->
    <el-dialog title="编辑" :visible.sync="dialogFormVisible" width="50%">
      <el-form :model="form" label-position="left" ref="userForm">
        <el-form-item label="账号" required :label-width="formLabelWidth" prop="username">
          <el-input v-model="form.username" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="姓名" required :label-width="formLabelWidth" prop="reaalName">
          <el-input v-model="form.reaalName" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="性别" required :label-width="formLabelWidth">
          <el-radio-group v-model="form.gender">
            <el-radio label="男"></el-radio>
            <el-radio label="女"></el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="手机号" :label-width="formLabelWidth">
          <el-input v-model="form.telephone" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="邮箱" :label-width="formLabelWidth">
          <el-input v-model="form.email" auto-complete="off"></el-input>
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
import { queryUserList, modifyUserStatus, deleteUser, resetPwd, modifyUserInfo, addUserInfo } from '../../utils/api'
export default {
  data() {
    return {
      //列表控件
      tableData3: [],
      validTime: [],

      value: '',

      //输入框
      username: '',

      //删除确认框
      centerDialogVisible: false,

      //编辑弹窗
      dialogFormVisible: false,
      form: {},
      formLabelWidth: "100px",
      page: {
        pageNum: 1,
        pageSize: 10,
        pages: 1
      }

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
      this.dialogFormVisible = true;
      this.form = row
    },
    handleAdd() {
      this.form = {}
      this.dialogFormVisible = true;
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
    commitUser() {
      this.$refs.userForm.validate((valid) => {
        if (valid) {
          if (!this.form.id) {
            addUserInfo(this.form).then(res => {
              this.$message({
                message: '用户信息添加成功',
                type: 'success'
              });
              this.dialogFormVisible = false;
              this.queryUsers()
            })
          } else {
            modifyUserInfo(this.form).then(res => {
              this.$message({
                message: '用户信息修改成功',
                type: 'success'
              });
              this.dialogFormVisible = false;
              this.queryUsers()
            })
          }
        }
      })
    },
    resetPwdCur(index, row) {
      resetPwd(row).then(res => {
        this.$message({
          message: '密码成功重置为11111111',
          type: 'success'
        });
      }).catch((err) => {
        this.$message.error(err)
      });

    },

    invalidUser(index, row) {
      modifyUserStatus({
        userId: row.id,
        status: "02"
      }).then(() => {
        this, queryUserList()
      })
    },
    validUser(index, row) {
      modifyUserStatus({
        userId: row.id,
        status: "01"
      }).then(() => {
        this, queryUserList()
      })
    },
    handleDelete(index, row) {
      this.centerDialogVisible = true;
      deleteUser(row)
      this.queryUsers()
    },
    queryUsers() {
      const _this = this
      queryUserList({
        page: this.page,
        queryParam: {
          userName: _this.username,
          roleId: ['2', '3']
        }
      }).then(data => {
        _this.page = {
          pageSize: data.data.pageSize,
          pageNum: data.data.pageNum,
          totalSize: data.data.total,
          pages: data.data.pages
        }
        _this.tableData3 = data.data.list
      })
    }
  },

  mounted() {
    this.queryUsers()
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
