<template>
    <div class="login login_back">
        <div class="main_login">
            <div class="forgot-password">
                <el-button type="text" v-show="active >= '1'" class="return-page-wr" @click="$router.go(-1)"> <i
                        class="el-icon-arrow-left"></i> 上一步 </el-button>
                <div>
                    <el-steps :active="active" finish-status="success">
                        <el-step title="账号验证"></el-step>
                        <el-step title="重置密码"></el-step>
                        <el-step title="完成设置"></el-step>
                    </el-steps>
                    <div v-if="active == '0'">
                        <el-form ref="verificationForm" label-position="left" label-width="100px" :model="verificationForm" :rules="verificationFormRules"
                            class="demo-ruleForm" @submit.native.prevent>
                            <el-form-item prop="username" label="用户名">
                                <el-input v-model="verificationForm.username" type="text" placeholder="用户名">
                                </el-input>
                            </el-form-item>
                            <el-form-item prop="problemId" label="密保问题">
                                <el-select v-model="verificationForm.problemId" class="wid">
                                    <el-option v-for="item in options" :key="item.id" :label="item.problem"
                                        :value="item.id">
                                    </el-option>
                                </el-select>
                            </el-form-item>
                            <el-form-item prop="answer" label="答案">
                                <el-input v-model="verificationForm.answer" type="text" placeholder="不超过10个字符">
                                </el-input>
                            </el-form-item>
                        </el-form>
                        <!--            <el-button style="margin-top: 12px;" @click="checkSecurityQuestion">下一步</el-button>-->
                        <div class="btn-wr">
                            <el-button type="el-button" @click.prevent="checkSecurityQuestion">下一步 <i></i></el-button>
                        </div>
                    </div>
                    <div v-if="active == '1'">
                        <el-form ref="forgotPasswordForm" :model="forgotPasswordForm" :rules="forgotPasswordFormRules"
                            class="demo-ruleForm" @submit.native.prevent>
                            <el-form-item prop="password" label="密码:">
                                <el-input v-model="forgotPasswordForm.password" type="password" placeholder="6-16位大小写字母+数字组合"
                                    auto-complete="off">
                                </el-input>
                            </el-form-item>
                            <el-form-item prop="confirmPwd" label="确认密码:">
                                <el-input v-model="forgotPasswordForm.confirmPwd" placeholder="重复密码" type="password">
                                </el-input>
                            </el-form-item>
                        </el-form>
                        <div class="btn-wr">
                            <el-button type="el-button" class="login-btn" @click.prevent="resetPassword">确认 <i
                                    class="el-icon-arrow-right"></i></el-button>
                        </div>
                    </div>
                    <div v-if="active == '2'">
                        <div class="reset-success">
                            <p class="set-success">设置成功，去登录</p>
                        </div>
                        <div class="btn-wr">
                            <el-button type="el-button" class="login-btn" @click="$router.push({ path: '/login' })">登录
                                <i class="el-icon-arrow-right"></i>
                            </el-button>
                        </div>
                    </div>
                </div>


            </div>
        </div>
    </div>
</template>
<script>
export default {
    name: "forgot-password",
    data() {
        let validateAccount = (rules, value, callback) => {
            if (/[\u4E00-\u9FA5]/g.test(value)) {
                callback(new Error('账户名称不能输入汉字!'));
            } else if (/^[0-9]+$/g.test(value)) {
                callback(new Error('账户名称不能输入纯数字!'));
            } else {
                callback();
            }

        }
        let validateConfirmPass = (rule, value, callback) => {
            if (value !== this.forgotPasswordForm.password) {
                callback(new Error('两次输入密码不一致,请重新输入'));
            } else {
                callback();
            }
        };
        return {
            active: 0,
            verificationForm: {
                problemId: '',
                answer: '',
                username: ''
            },
            forgotPasswordForm: {
                password: '',
                confirmPwd: '',
                username: ''
            },
            options: [{id:1,problem:'您的出生地是？'},{id:2,problem:'您的小学班主任是？'},{id:3,problem:'您母亲的生日是？'}],
            verificationFormRules: {
                problemId: [
                    { required: true, message: '请选择密保问题', trigger: 'change' }
                ],
                answer: [
                    { required: true, message: '请输入答案', trigger: 'blur' },
                    { min: 1, max: 10, message: '答案不可超过10个字符，请重新输入', trigger: "blur" },
                ],
                username: [
                    { required: true, message: '请输入用户名', trigger: 'blur' },
                    { min: 1, max: 15, message: '用户名不超过15个字符,请重新输入', trigger: "blur" },
                    { validator: validateAccount, trigger: 'blur' }
                ],
            },
            forgotPasswordFormRules: {
                password: [
                    { required: true, message: '请输入密码', trigger: 'blur' },
                    { min: 6, max: 16, message: '长度在6-16位之间', trigger: "blur" },
                    { pattern: new RegExp("^(?=.*[0-9].*)(?=.*[A-Z].*)(?=.*[a-z].*).{6,16}$"), message: '密码为6-16位大小写字母+数字组合，请重新输入', trigger: "blur" }
                ],
                confirmPwd: [
                    { required: true, message: '请输入确认密码', trigger: 'blur' },
                    { validator: validateConfirmPass, trigger: 'blur' },
                    { min: 6, max: 16, message: '长度在6-16位之间', trigger: "blur" },
                    { pattern: new RegExp("^(?=.*[0-9].*)(?=.*[A-Z].*)(?=.*[a-z].*).{6,16}$"), message: '密码为6-16位大小写字母+数字组合，请重新输入', trigger: "blur" }
                ],
            }

        }
    },
    methods: {
        next() {
            if (this.active++ > 2) this.active = 0;
        },
        checkSecurityQuestion() {
            this.$refs.verificationForm.validate(valid => {
                if (valid) {
                    this.$post('/api/admin/checkSecurityQuestion',this.verificationForm).then(res => {
                        this.forgotPasswordForm.username = this.verificationForm.username;
                        this.active++
                    })
                }
            })
        },
        resetPassword() {
            this.$refs.forgotPasswordForm.validate(valid => {
                if (valid) {
                    const md5Pwd = hex_md5(this.forgotPasswordForm.password)
                    this.$post('/api/admin/resetPwd',{...this.forgotPasswordForm,password:md5Pwd}).then(res => {
                        this.active++
                    })
                }
            })
        },
    },
}
</script>
<style scoped>
.login_back {
    align-items: center;
    background-color: #e9e9e9;
    background: url('../assets/home.png');
    background-attachment: fixed;
    /* */
    background-position: center;
    background-repeat: no-repeat;
    background-size: cover;
    display: grid;
    height: 100vh;
    place-items: center;
}

/* 主页面样式 */
.login {
    position: absolute;
    /*绝对定位*/
    width: 100%;
    height: 100%;
    background-color: #e4e7ed;
}

/* 标题样式 */
.title {
    font-size: large;
    /*字体大小*/
    font-weight: bolder;
    /*字体加粗*/
    text-align: center;
    color: black;
}

/* 登录部分div样式 */
.main_login {
    position: absolute;
    left: 48%;
    top: 40%;
    width: 450px;
    margin: -190px 0 0 -190px;
    padding: 40px;
    border-radius: 5px;
    /*圆角边框*/
    background: #f2f6fc;
}

/* 表单样式 */
.el-form {
    padding-top: 5%;
    padding-left: 10%;
    padding-right: 10%;
    width: 280px;
}

/* .el-row标签样式 */
.el-row {
    height: 100%;
    width: 100%;
}

/* 文字链接div样式 */
.link {
    margin-top: -13%;
    text-align: center;
    /* 文本居中 */
    margin-left: -5%;
}

/* 文字链接样式 */
.el-link {
    margin-left: 8px;
    line-height: 20px;
    font-size: 8px;
}
</style>
