<template>
    <div class="container" style="position: absolute;left: 0;top: 60px;bottom: 0;width: 100%;padding-top:20px;display: flex">
        <div class="left-container" v-if="isLogin">
            <el-upload
                    class="avatar-uploader"
                    action="http://106.15.93.81:9001/ossservice/fileoss"
                    :show-file-list="false"
                    :on-success="handleAvatarSuccess"
                    accept=".jpg,.jpeg,.png,.JPG,.JPEG,.PNG"
            >
                <img v-if="userInfo.avatar!=''" :src="userInfo.avatar" class="avatar">
                <span v-else class="noAvatar">暂无头像</span>
                <div class="avatarChangeNote">更换头像</div>
            </el-upload>
            <div class="userBasicInfo-name">
                <div>{{userInfo.nickname}}</div>
                <img class="vipicon" v-if="userInfo.isVip" src="../../assets/VIP-gold.svg">
                <img class="vipicon" v-else src="../../assets/VIP-gray.svg">
            </div>
            <div class="userBasicInfo-sign" v-if="userInfo.sign!=''">{{userInfo.sign}}</div>
        </div>
        <div class="right-container" v-if="isLogin">
            <el-tabs v-model="activePart" @tab-click="handleClick" type="card">
                <el-tab-pane label="账号信息" name="accountInfo">
                    <el-form class="modifyForm" label-width="70px">
                        <el-form-item label="手机号">
                            <span>{{userInfo.mobile}}</span>
                        </el-form-item>
                        <el-form-item v-if="!changePwd" label="用户名">
                            <el-input v-if="modify" v-model="modifyUserInfoParams.nickname" placeholder="请输入用户名" >
                            </el-input>
                            <span v-else>{{userInfo.nickname}}</span>
                        </el-form-item>
                        <el-form-item v-if="!changePwd" label="个性签名">
                            <el-input v-if="modify" v-model="modifyUserInfoParams.sign" placeholder="请输入个性签名">
                            </el-input>
                            <span v-else>{{userInfo.sign}}</span>
                        </el-form-item>
                        <el-form-item v-if="changePwd" label="原密码">
                            <el-input v-model="changePwdParams.oldPWD" type="password" placeholder="请输入原密码">
                            </el-input>
                        </el-form-item>
                        <el-form-item v-if="changePwd" label="新密码">
                            <el-input v-model="changePwdParams.newPWD" type="password" placeholder="请输入新密码">
                            </el-input>
                        </el-form-item>
                        <el-form-item v-if="modify" class="modifyButton">
                            <el-button type="primary" size="small" @click="saveModify">保存</el-button>
                            <el-button size="small" @click="cancelModify">取消</el-button>
                        </el-form-item>
                        <el-form-item v-if="changePwd" class="modifyButton">
                            <el-button type="primary" size="small" @click="saveModifyPWD">保存</el-button>
                            <el-button size="small" @click="cancelModifyPWD">取消</el-button>
                        </el-form-item>
                        <el-form-item v-if="!modify&&!changePwd">
                            <el-button type="primary" size="small" @click="modifyInfo">编辑</el-button>
                            <el-button type="primary" size="small" @click="modifyPWD">修改密码</el-button>
                            <el-button type="primary" size="small" @click="deleteMyself">注销</el-button>
                        </el-form-item>
                    </el-form>
                </el-tab-pane>
                <el-tab-pane label="我的图谱" name="myDomains">
                    <div class="domain-table" style="margin-top: 10px">
                        <el-table
                                :data="domainList"
                                style="width:100%"
                                :row-style="{height:'30px'}"
                                :cell-style="{padding:'8px'}"
                        >
                            <el-table-column
                                    label="图谱名称"
                                    prop="name"
                                    sortable
                            >
                            </el-table-column>
                            <el-table-column
                                    sortable
                                    label="创建时间"
                                    prop="createTime"
                            >
                            </el-table-column>
                            <el-table-column
                                    sortable
                                    label="上次修改时间"
                                    prop="modifyTime"
                            >
                            </el-table-column>

                        </el-table>
                    </div>
                </el-tab-pane>
            </el-tabs>
        </div>
        <div v-if="!isLogin" style="font-size: 25px;color: #8c939d;margin-left: 20%;margin-top: 10%;">
            不好意思，请先登录哦~
        </div>
    </div>
</template>

<script>
    import {mapActions, mapGetters, mapMutations} from "vuex";
    import {closeAccountAPI, updateAvatarAPI, updateUserInfoAPI, updateUserPwdAPI} from "../../api/users";
    import {Message} from "element-ui";

    export default {
        name: "userCenter",
        data(){
            return{
                modify: false,
                activePart:'accountInfo',
                changePwd: false,
                changePwdParams:{
                    oldPWD:'',
                    newPWD:'',
                },
                avatarUpdater:{
                    id:'',
                    avatar:'',
                },
            }
        },
        computed:{
            ...mapGetters([
                'isLogin',
                'userInfo',
                'modifyUserInfoParams',
                'domainList'
            ])
        },
        methods:{
            ...mapActions([
                'getUserInfo',
                'closeAccount'
            ]),
            modifyInfo(){
                this.modify = true
            },
            modifyPWD(){
                this.changePwd = true
            },
            saveModify(){
                updateUserInfoAPI(this.modifyUserInfoParams.id, this.modifyUserInfoParams.nickname, this.modifyUserInfoParams.sign)
                    .then(res => {
                        console.log(res)
                        if(res.data.code == 200) {
                            this.$message({
                                type: 'success',
                                message: '修改成功'
                            })
                            this.modify = false
                        } else {
                            this.$message({
                                type: 'error',
                                message: '修改失败'
                            })
                        }
                    })
            },
            cancelModify(){
                this.modify = false;
            },
            saveModifyPWD(){
                updateUserPwdAPI(this.userInfo.id,this.changePwdParams.oldPWD, this.changePwdParams.newPWD)
                    .then(res => {
                        console.log(res)
                        if(res.data.code == 200) {
                            this.$message({
                                type: 'success',
                                message: '修改成功'
                            })
                            this.changePwd = false;
                        } else {
                            this.$message({
                                type: 'error',
                                message: res.data.message,
                            })
                        }
                    })
            },
            cancelModifyPWD(){
                this.changePwd = false;
            },
            //上传成功的方法
            handleAvatarSuccess(res) {
                console.log("avatar",res.data.url);
                this.avatarUpdater = {
                    id: this.userInfo.id,
                    avatar: res.data.url
                }
                updateAvatarAPI(this.avatarUpdater).then(res =>{
                    console.log("res",res);
                    if(res.data.code == 200){
                        this.getUserInfo();
                        this.$message({
                            message: '修改成功',
                            type: 'success'
                        })
                    }else{
                        this.$message({
                            message:'修改失败',
                            type:'error'
                        })
                    }
                })
            },
            deleteMyself(){
                this.$confirm('此操作将注销当前账户并清除其所有图谱（不可恢复），是否继续？',{
                    confirmButtonText:'确认',
                    cancelButtonText:'取消',
                    type: 'warning'
                }).then(() => {
                    this.closeAccount();
                })
            },
            changeSortOrder(){

            }
        }
    }
</script>

<style scoped>
    .left-container{
        width: 200px;
        height: 100%;
        padding-left: 10%;
        margin-right: 5%;
        text-align: center;
        display: flex;
        flex-direction: column;
    }
    .right-container{
        width: 60%;
    }
    .userBasicInfo-name{
        font-weight: bold;
        font-size: 20px;
        margin-top: 20px;
        align-items: center;
    }
    .userBasicInfo-sign{
        color: #8c939d;
        font-size: 15px;
        margin-top: 20px;
    }
    .avatarChangeNote{
        position: absolute;
        top: 20px;
        left: 10%;
        width: 200px;
        height: 200px;
        color: #FFF;
        font-size: 14px;
        text-align: center;
        line-height: 200px;
        border: 4px solid #EEE;
        background: rgba(0,0,0,0.5);
        cursor: pointer;
        display: none;
    }
    .avatar-uploader:hover .avatarChangeNote{
        display: block;
    }
    .avatar-uploader{
        width: 200px;
        height: 200px;
        border: 2px solid #EEEEEE;
        border-radius: 5px;
        cursor: pointer;
        overflow: hidden;
    }
    .noAvatar {
        font-size: 20px;
        color: #8c939d;
        width: 200px;
        height: 200px;
        line-height: 200px;
        text-align: center;
    }
    .avatar {
        width: 200px;
        height: 200px;
        display: block;
    }
    .modifyForm .el-form-item{
        display: flex;
    }
    .domain-table >>> .el-table__row > td{
        /* 去除表格线 */
        border: none;
    }
    .domain-table >>> .el-table::before{
        /* 去除下边框 */
        height: 0;
    }
    .vipicon{
        height: 20px;
    }
</style>
