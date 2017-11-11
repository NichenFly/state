<template>
    <div class="login">
        <div>
            
        </div>
        <div class="content">
            <Card>
                <!-- <div class="logo">
                    <img src="static/img/logo.gif" class="logo-img">
                </div> -->
                <div>
                    <h2 class="title">超级监控系统</h2>
                    <Form ref="formLogin" :model="formLogin" :rules="ruleCustom" :label-width="80">
                        <FormItem label="用户名" prop="username">
                            <Input type="text" v-model="formLogin.username" placeholder="请输入用户名"></Input>
                        </FormItem>
                        <FormItem label="密码" prop="passwordCheck">
                            <Input type="password" v-model="formLogin.password"  @keyup.enter.native="handleSubmit('formLogin')" placeholder="请输入密码"></Input>
                        </FormItem>
                        <FormItem>
                            <Button type="primary" @click="handleSubmit('formLogin')">提交</Button>
                            <Button type="ghost" @click="handleReset('formLogin')" style="margin-left: 8px">重置</Button>
                        </FormItem>
                    </Form>
                </div>
            </Card>
        </div>
    </div>
</template>
<script>
    import { login } from 'api/login'
    import { CODE_OK } from 'constants/constants'
    import { getOriginUrl } from 'common/js/utils'

    const loginNoticeName = 'login'

    export default {
        data () {
            return {
                formLogin: {
                    username: '',
                    password: ''
                },
                ruleCustom: {
                    username: [
                        { required: true, message: '用户名不允许为空', trigger: 'blur' }
                    ],
                    password: [
                        { required: true, message: '密码不允许为空', trigger: 'blur' }
                    ]
                }
            }
        },
        methods: {
            handleSubmit (name) {
                this.$refs[name].validate((valid) => {
                    if (!valid) {
                        return
                    }
                    login(this.formLogin).then((res) => {
                        if (res.code === CODE_OK) {
                            this.$Notice.close(loginNoticeName)
                            let url = getOriginUrl()
                            if (url) {
                                this.$router.push({path: url})
                            } else {
                                this.$router.push({name: 'bases'})
                            }
                        } else {
                            this.$Notice.error({
                                title: '信息提示',
                                name: loginNoticeName,
                                desc: res.msg,
                                duration: 3
                            })
                        }
                    })
                })
            },
            handleReset (name) {
                this.$refs[name].resetFields()
            }
        }
    }
</script>
<style lang="scss" type="stylesheet/scss" scoped>
    .login {
        height: 100%;
        display: flex;
        justify-content: center;
        align-items: center;
        background-color: #1c2438;
    }
    .content {
        width: 400px;
    }
    .title {
        margin-top: 20px;
        margin-bottom: 20px;
        // text-align: left;
        // text-indent: 4em;
    }
    .ivu-form-item .ivu-form-item-label {
        color: #ccc !important;
    }
</style>