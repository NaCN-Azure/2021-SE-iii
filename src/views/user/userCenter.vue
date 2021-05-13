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
                    class="avatar-uploader"
                    action=""
                    :show-file-list="false"
                    :on-success="handleAvatarSuccess"
                    :before-upload="beforeAvatarUpload">
                <img v-if="userInfo.avatar!=null" :src="userInfo.avatar" class="avatar">
                <i v-else class="el-icon-plus avatar-uploader-icon"></i>
            </el-upload>
            <el-form>
                <el-form-item label="用户名">
                    <el-input v-if="modify" v-model="modifyUserInfoParams.nickname" placeholder="请输入用户名">
                    </el-input>
                    <span v-else>{{userInfo.nickname}}</span>
                </el-form-item>
                <el-form-item label="手机号">
                    <el-input v-if="modify" v-model="modifyUserInfoParams.mobile" placeholder="请输入手机号">
                    </el-input>
                    <span v-else>{{userInfo.mobile}}</span>
                </el-form-item>
                <el-form-item label="签名">
                    <el-input v-if="modify" v-model="modifyUserInfoParams.sign" placeholder="清输入个性签名">
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

    export default {
        name: "userCenter",
        data(){
            return{
                modify: false,
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

            }
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
    .avatar-uploader .el-upload {
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
</style>
