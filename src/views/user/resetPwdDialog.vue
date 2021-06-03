<template>
    <el-dialog
            title="重置密码"
            :visible = "resetPwdDialogVisible"
            :before-close="cancelResetPwd"
    >
        <el-form :model="resetPwdForm" class="resetPwdForm">
            <div class="inputBox">
                <el-form-item label="手机号">
                    <el-input v-model="resetPwdForm.mobile" placeholder="请输入手机号"></el-input>
                </el-form-item>
                <span class="promptMsg" id="mobileMsgInReset">请输入正确格式的手机号</span>
            </div>
            <div class="inputBox">
                <el-form-item label="新密码">
                    <el-input type="password" v-model="resetPwdForm.password" placeholder="请输入新密码"></el-input>
                </el-form-item>
                <span class="promptMsg" id="pwdMsgInReset">密码最少6位，至少包括一个字母、一个数字、一个特殊字符</span>
            </div>
        </el-form>
        <span slot="footer" class="dialog-footer">
                    <el-button @click="cancelResetPwd">取消</el-button>
                    <el-button @click="submitResetPwd" type="primary">确认</el-button>
        </span>
    </el-dialog>
</template>

<script>
    import {mapGetters, mapMutations} from "vuex";
    import {resetPwdAPI} from "../../api/users";
    import {Message} from "element-ui";

    export default {
        name: "resetPwdDialog",
        data(){
            return{
                resetPwdForm:{
                    mobile:'',
                    password:'',
                }
            }
        },
        computed:{
            ...mapGetters([
                'resetPwdDialogVisible'
            ])
        },
        methods:{
            ...mapMutations([
                'set_resetPwdDialogVisible'
            ]),
            cancelResetPwd(){
                this.set_resetPwdDialogVisible(false)
                this.resetPwdForm = {
                    mobile: '',
                    password: '',
                }
                document.getElementById("mobileMsgInReset").style.display="none";
                document.getElementById("pwdMsgInReset").style.display="none";
            },
            submitResetPwd(){
                if(this.checkMobileInReset()&&this.checkPwdInReset()){
                    resetPwdAPI(this.resetPwdForm.mobile,this.resetPwdForm.password).then(res=>{
                        console.log(res)
                        if(res.data.code == 200){
                            Message({
                                type:'success',
                                message:'重置成功'
                            })
                            this.set_resetPwdDialogVisible(false)
                        }else{
                            Message({
                                type:'error',
                                message:res.data.message,
                            })
                        }
                        this.resetPwdForm = {
                            mobile: '',
                            password: '',
                        }
                    })
                }else{
                    this.checkMobileInReset()
                    this.checkPwdInReset()
                }
            },
            checkMobileInReset(){
                // 检查注册手机号格式
                var reg =  /^[1][3,4,5,7,8][0-9]{9}$/;
                var mobile = this.resetPwdForm.mobile.trim();
                if(!reg.test(mobile)){
                    document.getElementById("mobileMsgInReset").style.display="inline";
                    return false;
                }else{
                    document.getElementById("mobileMsgInReset").style.display="none";
                    return true;
                }
            },
            checkPwdInReset(){
                var reg =  /^(?=.*[a-zA-Z])(?=.*\d)(?=.*?[#?!@$%^&*-_]).{6,}$/;
                var password = this.resetPwdForm.password.trim();
                if(!reg.test(password)){
                    document.getElementById("pwdMsgInReset").style.display="inline";
                    return false;
                }else{
                    document.getElementById("pwdMsgInReset").style.display="none";
                    return true;
                }
            }
        }
    }
</script>

<style scoped>
    .inputBox{
        height: 12.7%;
        margin-bottom: 8%;
    }
    .resetPwdForm{
        width: 95%;
    }
    .el-form-item{
        margin-bottom: 0px;
        display: flex;
    }
    .promptMsg{
        color: #ff463f;
        font-size: 14px;
        display: none;
    }
</style>
