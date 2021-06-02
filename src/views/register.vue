<template>
    <div class="register-container">
        <div class="register-box">
            <div class="register-title">
                <img src="../assets/register.png">
            </div>
            <el-form :model="form" class="register-form">
                <div class="inputBox">
                    <el-form-item>
                        <el-input v-model="form.mobile"
                                  prefix-icon="el-icon-mobile-phone"
                                  placeholder="请输入手机号"
                        ></el-input>
                    </el-form-item>
                    <span class="promptMsg" id="mobileMsg">请输入正确格式的手机号</span>
                </div>
                <div class="inputBox">
                    <el-form-item>
                        <el-input v-model="form.nickname"
                                  prefix-icon="el-icon-user"
                                  placeholder="请输入用户名"
                        ></el-input>
                    </el-form-item>
                    <span class="promptMsg" id="nameMsg">请输入用户名</span>
                </div>
                <div class="inputBox">
                    <el-form-item>
                        <el-input v-model="form.password"
                                  prefix-icon="el-icon-lock"
                                  placeholder="请输入密码"
                                  type="password"
                        ></el-input>
                    </el-form-item>
                    <span class="promptMsg" id="pwdMsg">密码最少6位，至少包括一个字母、一个数字、一个特殊字符</span>
                </div>
                <el-button type="primary" @click="handleRegister" style="margin-top:5px;margin-bottom:10%;width: 100%"
                           :loading="registerLoading"
                >注册</el-button>
            </el-form>
        </div>
    </div>
</template>

<script>
    import {mapActions} from "vuex";
    import {registerAPI} from '../api/users';

    export default {
        name: "register",
        data(){
            return{
                form:{
                    mobile:'',
                    nickname:'',
                    password:'',
                },
                registerLoading:false,
            }
        },
        methods:{
            ...mapActions([
                'register'
            ]),
            handleRegister(){
                if(this.checkMobile()&&this.checkName()&&this.checkPwd()) {
                    this.registerLoading = true;
                    this.register(this.form);
                    // registerAPI(this.form).then(res => {
                    //     this.$message({
                    //         type: 'success',
                    //         message: '注册成功'
                    //     })
                    //     // this.$router.push({ path: '/login' })
                    //     this.form = {
                    //         mobile: '',
                    //         nickname: '',
                    //         password: ''
                    //     }
                    // })
                    this.registerLoading = false;
                    this.form = {
                                mobile: '',
                                nickname: '',
                                password: ''
                            }
                }else{
                    this.checkMobile();
                    this.checkName();
                    this.checkPwd();
                }
            },
            checkMobile(){
                // 检查注册手机号格式
                var reg =  /^[1][3,4,5,7,8][0-9]{9}$/;
                var mobile = this.form.mobile.trim();
                if(!reg.test(mobile)){
                    document.getElementById("mobileMsg").style.display="inline";
                    return false;
                }else{
                    document.getElementById("mobileMsg").style.display="none";
                    return true;
                }
            },
            checkName(){
                var name = this.form.nickname.trim();  // 获取字段值
                name = name.replace(/(^\s*)|(\s*$)/g, '');
                if(name == '' || name == undefined || name == null){
                    document.getElementById("nameMsg").style.display = "inline";
                    return false;
                }else{
                    document.getElementById("nameMsg").style.display = "none";
                    return true;
                }
            },
            checkPwd(){
                var reg =  /^(?=.*[a-zA-Z])(?=.*\d)(?=.*?[#?!@$%^&*-_]).{6,}$/;
                var password = this.form.password.trim();
                if(!reg.test(password)){
                    document.getElementById("pwdMsg").style.display="inline";
                    return false;
                }else{
                    document.getElementById("pwdMsg").style.display="none";
                    return true;
                }
            }
        }
    }
</script>

<style scoped>
    .register-container{
        position: fixed;
        margin: auto;
        top: 0;
        left: 0;
        bottom: 0;
        right: 0;
        width: 100%;
        height: 100%;
        background-size: 100% 100%;
        background-image: url("../assets/kg-background3.jpg");
    }
    .register-box{
        background-color: white;
        position: absolute;
        left: 50%;
        top: 50%;
        transform: translate(-50%,-50%);
        /*width: 450px;*/
        width: 33%;
        min-width: 300px;
        /*height: 450px;*/
        /*min-height: 350px;*/
        border-radius: 15px;
        box-shadow:0 0 10px gray;
    }
    .register-title{
        width: 100%;
        /*height: 20%;*/
        /*line-height: 20%;*/
        /*text-align: center;*/
        display: flex;
        align-items: center;
        justify-content: center;
        margin-top: 4%;
        margin-bottom: 4%;
    }
    .register-title img{
        width: 50%;
        min-height: 60%;
    }
    .register-form{
        /*width: 410px;*/
        width: 91.1%;
        flex: 1;
        /*position: absolute;*/
        /*bottom: 0;*/
        padding: 0 4.4%;
    }
    .inputBox{
        height: 12.7%;
        margin-bottom: 3.3%;
    }
    .el-form-item{
        margin-bottom: 0px;
    }
    .promptMsg{
        color: #ff463f;
        font-size: 14px;
        display: none;
    }
</style>
