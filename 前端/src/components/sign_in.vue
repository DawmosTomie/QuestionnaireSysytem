<template>
    <div>
        <el-tabs>
            <el-tab-pane label="密码登录">
                <!-- 登录表单 -->
                <el-form ref="loginForm" :rules="rules" :model="loginForm">
                    <el-form-item prop="username">
                        <el-input @keyup.enter.native="login('loginForm')" icon="el-icon-search" placeholder="请输入用户名"
                            v-model="loginForm.username">
                            <i class="el-icon-user" slot="prefix"></i>
                        </el-input>
                    </el-form-item>
                    <el-form-item prop="password">
                        <el-input @keyup.enter.native="login('loginForm')" placeholder="请输入密码"
                            v-model="loginForm.password" show-password>
                            <i class="el-icon-lock" slot="prefix"></i>
                        </el-input>
                    </el-form-item>
                    <!-- 登录按钮 -->
                    <el-form-item>
                        <el-button type="primary" @click="login('loginForm')" style="text-align: center;width: 150px">登
                            录
                        </el-button>
                    </el-form-item>
                    <div class="tips" style="float:center;">
                        <el-link type="white" href='/retrievePassword' style="text-align: center;">忘记密码</el-link>
                    </div>
                </el-form>
            </el-tab-pane>
            <el-tab-pane label="验证码登录">
                <el-form ref="vloginForm" :model="loginForm">
                    <el-form-item prop="telephone">
                        <el-input @keyup.enter.native="login('vloginForm')" icon="el-icon-search" placeholder="请输入用户名"
                            v-model="loginForm.telephone">
                            <i class="el-icon-user" slot="prefix"></i>
                        </el-input>
                    </el-form-item>
                    <el-form-item prop="validCode">
                        <el-input @keyup.enter.native="login('vloginForm')" placeholder="请输入验证码"
                            v-model="loginForm.validCode">
                            <i class="el-icon-lock" slot="prefix"></i>
                            <el-button slot="append" @click="sendValidCode" :disabled="vaildDisabled">{{ this.vaildText
                            }}
                            </el-button>
                        </el-input>
                    </el-form-item>
                    <!-- 登录按钮 -->
                    <el-form-item>
                        <el-button type="primary" @click="login('vloginForm')" style="text-align: center;width: 150px">登
                            录
                        </el-button>
                    </el-form-item>
                </el-form>
            </el-tab-pane>
            <el-tab-pane label="扫码登录">
                <img src="../assets/images/qrcode.gif" style="width:70%;"/>
            </el-tab-pane>
        </el-tabs>
    </div>
</template>
<script>
import hex_md5 from 'js-md5'
export default {
    props: {
        role: String,
        isAdmin: {
            type: Boolean,
            default: () => {
                return false;
            }
        }
    },
    data() {
        return {
            // 表单数据
            loginForm: {
                username: '', //用户名
                password: '', //密码
                telephone: '',
                validCode: '',
            },
            timer: null, // 该变量是用来记录定时器的,防止点击的时候触发多个setInterval
            vaildText: '获取验证码',
            vaildDisabled: false,
            validTime: 60,
            flag: true,
            rules: {
                //表单验证（用户名验证规则）
                username: [
                    { required: true, message: '账号不能为空', trigger: 'blur' },
                    { max: 20, message: '账号长度最长20位', trigger: 'blur' },
                ],
                //表单验证（密码验证规则）
                password: [
                    { required: true, message: '密码不能为空', trigger: 'blur' },
                    { min: 6, message: '密码长度最少为6位', trigger: 'blur' },
                ],
            },
        }
    },
    methods: {
        validCountDown() {
            const _this = this
            let timeStop = setInterval(function () {
                _this.validTime--
                if (_this.validTime > 0) {
                    _this.vaildText = '重新发送' + _this.validTime + 's'
                } else {
                    _this.validTime = 60 //当减到0时赋值为60
                    _this.vaildText = '获取验证码'
                    clearInterval(timeStop) //清除定时器
                    _this.vaildDisabled = false //移除属性，可点击
                }
            }, 1000)
        },
        isTel(s) {
            if (s != null) {
                if (/^[1][3-9][0-9]{9}$/.test(s)) {
                    return true
                } else {
                    return false
                }
            }
        },
        sendValidCode() {
            // if (!this.isTel(this.loginForm.username)) {
            //   this.accountFocus = true
            //   this.error = true
            //   this.errMess = '请输入正确的手机号!'
            //   return
            // }
            this.$post('/api/validCode/send', { telephone: this.loginForm.telephone })
                .then((res) => {
                    this.$message.success('获取验证码成功！')
                    this.vaildDisabled = true //禁止点击
                    this.validCountDown() //验证码倒计时
                })
                .catch((err) => {
                    this.validLoading = false
                    this.$message.error(err.message)
                })
        },
        //登录方法
        login(formName) {
            // 表单验证通过，可进行操作
            this.$refs[formName].validate((valid) => {
                if (valid) {
                    const actionName =
                        formName == 'loginForm' ? 'Login' : 'LoginValidCode'
                    this.$store
                        .dispatch(actionName, {
                            username: this.loginForm.username,
                            telephone: this.loginForm.telephone, //用户名
                            password: this.loginForm.password,
                            roleId:this.role
                        })
                        .then((data) => {
                            //登录成功，并提示
                            this.$notify({
                                type: 'success',
                                message: '欢迎你,' + this.loginForm.username + '!',
                                duration: 3000,
                            })
                            if (data.roleId == '1'||data.roleId == 'user') {
                                this.$router.push({ path: '/user/index' }) //跳转到用户主页面
                            } else if (data.roleId == 'admin') {
                                this.$router.push({ path: '/admin' }) //跳转到用户主页面
                            } else if (data.roleId == 'tenet') {
                                this.$router.push({ path: '/tenant' }) //跳转到用户主页面
                            } else if (data.roleId == 'anwserer') {
                                this.$router.push({ path: '/user/anwserers' }) //跳转到用户主页面
                            } else {
                                this.$router.back()
                            }
                        })
                        .catch((err) => {
                            console.log(err)
                            this.loading = false
                        })
                } else {
                    return false //表单验证错误返回false
                }
            })
        },
    }
}
</script>
