<template>
    <div class="container" style="position: absolute;left: 0;top: 60px;bottom: 0;width: 100%">
<!--        <div class="left-container">-->
<!--            <h5 style="font-size: 15px;color: gray">个人中心</h5>-->
<!--            <el-menu default-active="1"-->
<!--                     text-color="darkgray">-->
<!--                <el-menu-item index="1">-->
<!--                    <i class="el-icon-user-solid"></i>-->
<!--                    <span>基本信息</span>-->
<!--                </el-menu-item>-->
<!--                <el-menu-item index="2">-->
<!--                    <i class="el-icon-picture"></i>-->
<!--                    <span>我的图谱</span>-->
<!--                </el-menu-item>-->
<!--            </el-menu>-->
<!--        </div>-->
        <div class="right-container">
            <el-upload
                    v-if="modify"
                    class="avatar-uploader"
                    action="http://106.15.93.81:9001/ossservice/fileoss"
                    :show-file-list="false"
                    :on-success="handleAvatarSuccess">
                <img v-if="userInfo.avatar!=''" :src="userInfo.avatar" class="avatar">
                <i v-else class="el-icon-plus avatar-uploader-icon"></i>
            </el-upload>
            <img v-if="!modify" :src="userInfo.avatar" class="avatar">
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
                <el-form-item v-if="modify">
                    <el-button type="primary" size="small" @click="saveModify">保存</el-button>
                </el-form-item>
                <el-form-item v-else>
                    <el-button type="primary" size="small" @click="modifyInfo">编辑</el-button>
                </el-form-item>
            </el-form>
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
            }
        },
        computed:{
            ...mapGetters([
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
            //上传成功的方法
            handleAvatarSuccess(res) {
                console.log(this.userInfo)
                this.userInfo.avatar = res.url
                console.log(this.userInfo)
            },
        }
    }
</script>

<style scoped>
    .left-container{
        height: 100%;
        width: 250px;
        text-align: center;
    }
    .right-container{
        padding: 20px;
    }
    .right-container{
        position: absolute;
        left: 250px;
        width: 100%;
        height: 100%;
    }
    .el-upload el-upload--text{
        /*为啥这个不起作用呢*/
        border: 1px dashed gray;
        border-radius: 6px;
        cursor: pointer;
        overflow: hidden;
    }
    .avatar-uploader .el-upload:hover {
        border-color: #409EFF;
    }
    .avatar-uploader-icon {
        font-size: 28px;
        color: #8c939d;
        width: 178px;
        height: 178px;
        line-height: 178px;
        text-align: center;
    }
    .avatar {
        width: 178px;
        height: 178px;
        display: block;
    }
    .modifyForm .el-form-item{
        display: flex;
    }
</style>
