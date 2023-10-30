<template>
  <div class="list-main">
    <div class="search-list">
      <el-row>
        <el-col :span="3" style="width:0%">
          <el-button @click="handleAdd" type="primary">创建群组</el-button>
        </el-col>
        <el-col :span="12" :offset="3">
          <el-input v-model="userName" class="search-text mr10" placeholder="请输入内容"></el-input>
          <el-button type="primary" @click="queryUsers">搜索</el-button>
        </el-col>
      </el-row>
    </div>

    <!-- 列表 -->
    <el-table ref="multipleTable" :data="tableData3" border tooltip-effect="dark" style="width: 100%">
      <el-table-column type="expand">
        <template slot-scope="props">
          <el-table :data="props.row.users" :show-header="false">
            <el-table-column label="用户账号" >
              <template slot-scope="scope">
                <el-badge :value="scope.row.type" class="item" type="primary">
                  <span style="margin-left: 10px">{{ scope.row.username }}</span>
                </el-badge>
              </template>
            </el-table-column>
            <el-table-column prop="telephone" label="手机号" width="120"></el-table-column>
            <el-table-column prop="realName" label="姓名" width="120"></el-table-column>
          </el-table>
        </template>
      </el-table-column>
      <el-table-column prop="grpName" label="群组名称" ></el-table-column>
      <el-table-column prop="grpDesc" label="描述" ></el-table-column>
      <el-table-column width="400" label="操作">
        <template slot slot-scope="scope">
          <el-button size="mini" type="primary" @click="handleEdit(scope.$index, scope.row)">添加用户</el-button>
          <el-button size="mini" type="primary" @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
          <el-button size="mini" type="primary" @click="deleteGrp(scope.$index, scope.row)">删除</el-button>
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
    <el-dialog title="未分组答者" :visible.sync="centerDialogVisible" width="30%" center>
      <UserTree ref="userTree"></UserTree>
      <span slot="footer" class="dialog-footer">
        <el-button @click="centerDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="addUserGrp">确 定</el-button>
      </span>
    </el-dialog>

    <!-- 编辑弹窗 -->
    <el-dialog title="编辑" :visible.sync="dialogFormVisible" width="50%">
      <el-form :model="form" label-position="left" ref="questionerForm">
        <el-form-item label="名称" required :label-width="formLabelWidth" prop="grpName">
          <el-input v-model="form.grpName" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="描述" :label-width="formLabelWidth" prop="grpDesc">
          <el-input v-model="form.grpDesc" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="标签" :label-width="formLabelWidth" prop="grpDesc">
          <el-select v-model="form.tags" placeholder="请选择用来创建群组的标签" multiple>
            <el-option key="item1" label="男性" value="man">
            </el-option>
            <el-option key="item2" label="女性" value="female">
            </el-option>
            <el-option key="item3" label="电影" value="film">
            </el-option>
            <el-option key="item4" label="80后" value="80s">
            </el-option>
            <el-option key="item5" label="00后" value="00s">
            </el-option>
            <el-option key="item6" label="医生" value="doctor">
            </el-option>
            <el-option key="item5" label="程序员" value="coder">
            </el-option>
            <el-option key="item6" label="公务员" value="gwy">
            </el-option>
          </el-select>
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
import UserTree from '@/components/userTree/UserTree'
export default {
  components: {
    UserTree,
  },
  data() {
    return {
      //列表控件
      tableData3: [],
      validTime: [],

      //时间控件
      value1: '',
      value: '',
      noGrpUser: [],

      //输入框
      userName: '',

      //删除确认框
      centerDialogVisible: false,
      selectedGrp: {},
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
      this.centerDialogVisible = true
      this.selectedGrp = row
    },
    deleteGrp(index, row){

    },
    handleAdd() {
      this.dialogFormVisible = true
    },
    addUserGrp() {
      const data = this.$refs.userTree.$refs.userTree.getCheckedNodes()
      this.$post('/api/group/addUser', {
        groupId: this.selectedGrp.id,
        users: data,
      }).then((res) => {
        this.centerDialogVisible = false
      })
    },
    commitUser() {
      this.form.roleId = 1
      this.$refs.questionerForm.validate((valid) => {
        if (valid) {
          if (!this.form.id) {
            this.$post('/api/group/add', this.form).then((res) => {
              this.$message({
                message: '群组信息添加成功',
                type: 'success',
              })
              this.dialogFormVisible = false
              this.queryUsers()
            })
          } else {
            this.$post('/api/group/update', this.form).then((res) => {
              this.$message({
                message: '群组信息修改成功',
                type: 'success',
              })
              this.dialogFormVisible = false
              this.queryUsers()
            })
          }
        }
      })
    },
    queryUsers() {
      const _this = this
      const typeArray = ['男性','女性','电影','80后','90后','医生','程序员','公务员']
      this.$post('/api/group/query', {
        page: this.page,
        queryParam: {
          name: _this.grpName,
        },
      }).then((data) => {
        _this.tableData3 = data.list
        _this.tableData3.forEach((item,index) => {
          item.users = []
          for (let i = 0; i < index+1; i++) {
            let user = {username:'用户'+i,telephone:'17788887777',realName:'真实姓名'+i,type:typeArray[index%8]}
            item.users.push(user)
          }
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
          type: 'error',
        })
        return false
      }
      return true
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
</style>