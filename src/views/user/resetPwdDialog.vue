<template>
    <el-dialog
            title="重置密码"
            :visible = "resetPwdDialogVisible"
            :before-close="cancelResetPwd"
    >
        <el-form :model="resetPwdForm">
            <el-form-item label="手机号">
                <el-input v-model="resetPwdForm.mobile" placeholder="请输入手机号"></el-input>
            </el-form-item>
            <el-form-item label="新密码">
                <el-input v-model="resetPwdForm.password" placeholder="请输入新密码"></el-input>
            </el-form-item>
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
            },
            submitResetPwd(){
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
            }
        }
    }
</script>

<style scoped>

</style>
