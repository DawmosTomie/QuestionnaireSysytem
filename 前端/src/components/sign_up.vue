<template>
  <div>
    <el-steps :active="active" finish-status="success" simple>
      <el-step title="注册账号"></el-step>
      <el-step title="设置密保"></el-step>
    </el-steps>
    <el-row v-if="active == '0'">
      <el-form ref="regisForm" :model="regisForm">
        <el-form-item prop="username">
          <el-input
            icon="el-icon-search"
            placeholder="请输入用户名"
            required
            v-model="regisForm.username"
          >
            <i class="el-icon-user" slot="prefix"></i>
          </el-input>
        </el-form-item>
        <el-form-item prop="telephone">
          <el-input
            icon="el-icon-search"
            placeholder="请输入手机号"
            required
            v-model="regisForm.telephone"
          >
            <i class="el-icon-user" slot="prefix"></i>
          </el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input placeholder="请输入密码" required v-model="regisForm.password" show-password>
            <i class="el-icon-lock" slot="prefix"></i>
          </el-input>
        </el-form-item>
        <el-form-item prop="confirmPassword">
          <el-input placeholder="请确认密码" v-model="regisForm.confirmPassword" show-password>
            <i class="el-icon-lock" slot="prefix"></i>
          </el-input>
        </el-form-item>
        <!-- 登录按钮 -->
        <el-form-item>
          <el-button type="primary" @click="regist" style="text-align: center;width: 150px">注 册</el-button>
        </el-form-item>
      </el-form>
    </el-row>
    <el-row v-if="active == '1'">
      <el-form ref="verifyForm" :model="verifyForm" :rules="verificationFormRules">
        <el-form-item prop="problemId" label="密保问题">
          <el-select v-model="verifyForm.problemId" class="wid">
            <el-option
              v-for="item in options"
              :key="item.id"
              :label="item.problem"
              :value="item.id"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item prop="answer" label="答案">
          <el-input v-model="verifyForm.answer" type="text" placeholder="不超过10个字符"></el-input>
        </el-form-item>
      </el-form>
      <div class="btn-wr">
        <el-button type="el-button" @click.prevent="savePwdVerify">
          保存
          <i></i>
        </el-button>
      </div>
    </el-row>
  </div>
</template>
<script>
import { hex_md5 } from 'js-md5'
export default {
  props: {
    role: String,
  },
  data() {
    return {
      verifyForm: {},
      active: 0,
      verificationFormRules: {
        problemId: [
          { required: true, message: '请选择密保问题', trigger: 'change' },
        ],
        answer: [
          { required: true, message: '请输入答案', trigger: 'blur' },
          {
            min: 1,
            max: 10,
            message: '答案不可超过10个字符，请重新输入',
            trigger: 'blur',
          },
        ],
      },
      options: [
        { id: 1, problem: '您的出生地是？' },
        { id: 2, problem: '您的小学班主任是？' },
        { id: 3, problem: '您母亲的生日是？' },
      ],
      regisForm: { roleId: '1' },
    }
  },
  methods: {
    regist() {
      this.$refs.regisForm.validate((valid) => {
        if (valid) {
          if (this.regisForm.password != this.regisForm.confirmPassword) {
            this.$message.error('两次输入的密码不一致')
            return
          }
          const md5Pwd = hex_md5(this.regisForm.password)
          let url = '/api/admin/addUserInfo'
          if (this.role == 'tenet') {
            url = '/api/tenant/add'
          } else if (this.role == 'anwserer') {
            url = '/api/respondent/add'
          }
          this.$post(url, { ...this.regisForm, password: md5Pwd })
            .then((res) => {
              this.active++
            })
            .catch((e) => this.$Message.error(e))
        }
      })
    },
    savePwdVerify() {
      this.$refs.verifyForm.validate((valid) => {
        if (valid) {
          this.$post('/api/admin/savePwdVerify', {
            ...this.verifyForm,
            username: this.regisForm.username,
            roleId:this.role
          }).then((res) => {
            this.$message.success('注册成功')
            this.$router.push('/login')
          })
        }
      })
    },
  },
}
</script>