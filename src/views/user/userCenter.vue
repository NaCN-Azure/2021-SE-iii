<template>
    <div class="container" style="position: absolute;left: 0;top: 60px;bottom: 0;width: 100%;padding-top:20px;display: flex">
        <div class="left-container" v-if="isLogin">
            <el-upload
                    class="avatar-uploader"
                    action="http://106.15.93.81:9001/ossservice/fileoss"
                    :show-file-list="false"
                    :on-success="handleAvatarSuccess">
                <img v-if="userInfo.avatar!=''" :src="userInfo.avatar" class="avatar">
                <span v-else class="noAvatar">暂无头像</span>
                <div class="avatarChangeNote">更换头像</div>
            </el-upload>
            <div class="userBasicInfo-name">{{userInfo.nickname}}</div>
            <div class="userBasicInfo-sign" v-if="userInfo.sign!=''">{{userInfo.sign}}</div>
        </div>
        <div class="right-container" v-if="isLogin">
            <el-tabs v-model="activePart" @tab-click="handleClick" type="card">
                <el-tab-pane label="账号信息" name="accountInfo">
                    <el-form class="modifyForm" label-width="70px">
                        <el-form-item label="手机号">
                            <span>{{userInfo.mobile}}</span>
                        </el-form-item>
                        <el-form-item label="用户名">
                            <el-input v-if="modify" v-model="userInfoParams.nickname" placeholder="请输入用户名" >
                            </el-input>
                            <span v-else>{{userInfo.nickname}}</span>
                        </el-form-item>
                        <el-form-item label="个性签名">
                            <el-input v-if="modify" v-model="userInfoParams.sign" placeholder="请输入个性签名">
                            </el-input>
                            <span v-else>{{userInfo.sign}}</span>
                        </el-form-item>
                        <el-form-item v-if="modify" class="modifyButton">
                            <el-button type="primary" size="small" @click="saveModify">保存</el-button>
                            <el-button size="small" @click="cancelModify">取消</el-button>
                        </el-form-item>
                        <el-form-item v-else>
                            <el-button type="primary" size="small" @click="modifyInfo">编辑</el-button>
                            <el-button type="primary" size="small" @click="modifyPWD">修改密码</el-button>
                        </el-form-item>
                    </el-form>
                </el-tab-pane>
                <el-tab-pane label="我的图谱" name="myDomains">

                </el-tab-pane>
            </el-tabs>
        </div>
        <div v-if="!isLogin">
            不好意思，请先登录哦~
        </div>
    </div>
</template>

<script>
    import {mapGetters} from "vuex";
    import {updateUserInfoAPI} from "../../api/users";

    export default {
        name: "userCenter",
        data(){
            return{
                modify: false,
                userInfoParams: {
                    id: '',
                    nickname: '',
                    mobile: '',
                    sign: '',
                    avatar: ''
                },
                activePart:'accountInfo'
            }
        },
        computed:{
            ...mapGetters([
                'isLogin',
                'userInfo',
                'modifyUserInfoParams',
            ])
        },
        methods:{
            modifyInfo(){
                this.modify = true
            },
            saveModify(){
                this.userInfoParams.id = this.userInfo.id
                this.userInfoParams.avatar = this.userInfo.avatar
                updateUserInfoAPI(this.userInfoParams)
                    .then(res => {
                        console.log(res)
                        if(res.data.code == 200) {
                            this.$message({
                                type: 'success',
                                message: '修改成功'
                            })
                        } else {
                            this.$message({
                                type: 'error',
                                message: '该手机号码已经存在'
                            })
                        }
                    })
            },
            cancelModify(){
                this.modify = false;
            },
            //上传成功的方法
            handleAvatarSuccess(res) {
                console.log(this.userInfo)
                this.userInfo.avatar = res.url
                this.$message({
                    type: "success",
                    message: '修改成功'
                })
                console.log(this.userInfo)
            },
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
</style>
