<template>
    <div class="create-container">
        <div class="right-info">
            <el-dropdown @command="handleCommand" v-if="isLogin">
                <div class="user">
                    <el-avatar :src=userInfo.avatar alt="user" :size="45" v-if="isLogin"></el-avatar>
                    <span class="username">{{userInfo.nickname}}</span>
                </div>
                <el-dropdown-menu slot="dropdown">
                    <el-dropdown-item command="userCenter">个人中心</el-dropdown-item>
                    <el-dropdown-item command="logout">退出</el-dropdown-item>
                </el-dropdown-menu>
            </el-dropdown>
            <el-button type="primary" size="mini" round="true" @click="goLogin" v-else>登录</el-button>
        </div>
        <div class="title">快来创建属于你的知识图谱!</div>
        <div class="createPane">
        <el-tabs v-model="createType"
                 type="border-card"
                 class="createTab"
                 @tab-click="handleClick">
            <el-tab-pane label="上传csv文件" name="importCSV">
                csv文件格式：节点-节点-关系 三元组
                <el-upload
                        drag
                        action="http://106.15.93.81:8002/coinservice/file/getCsv"
                        class="uploading"
                        on-success="handleCsvSuccess"
                        data="uploadParam"
                        accept=".csv"
                        :auto-upload=false
                        ref="upload"
                >
                    <i class="el-icon-upload"></i>
                    <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
                </el-upload>
            </el-tab-pane>
            <el-tab-pane label="上传json文件" name="importJSON">
                json文件格式：
                <el-upload
                        drag
                        class="uploading"
                        on-success="jsonsuccess"
                        accept=".json"
                        auto-upload="false"
                >
                    <i class="el-icon-upload"></i>
                    <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
                </el-upload>
            </el-tab-pane>
            <el-tab-pane label="上传xml文件" name="importXML">
                xml文件格式：
                <el-upload
                        drag
                        :action="uploadUrl"
                        class="uploading"
                        on-success="xmlsuccess"
                        accept=".xml"
                        auto-upload="false"
                >
                    <i class="el-icon-upload"></i>
                    <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
                </el-upload>
            </el-tab-pane>
            <el-tab-pane label="文本提取生成" name="ExtractFromText">
                <el-input
                        type="textarea"
                        :rows="10"
                        placeholder="请输入要提取知识图谱的内容"
                        v-model="extractedText"
                >
                </el-input>
            </el-tab-pane>
        </el-tabs>
        </div>
<!--        <div class="createButton">-->
            <el-button style="margin-top: 1%" @click="submitUpload">
                生成图谱
            </el-button>
            <el-button @click="addRouterToEditor">
                进入工作区
            </el-button>
<!--        </div>-->
    </div>
</template>

<script>
    import {mapActions, mapGetters, mapMutations} from "vuex";
    import cookie from 'js-cookie';

    export default {
        name: "index.vue",
        data() {
            return {
                createType: 'importCSV',
                extractedText: '',
                uploadParam: {},
            }
        },
        created() {
            // this.set_userInfo(cookie.get('coin_user'))
            console.log(this.userInfo)
            console.log(this.isLogin)
        },
        computed: {
            ...mapGetters([
                'userInfo',
                'isLogin'
            ])
        },
        methods: {
            ...mapMutations([
                'set_userInfo',
                'set_isLogin',
            ]),
            ...mapActions([
                'test'
            ]),
            handleCsvSuccess() {
                this.$refs.upload.clearFiles();
                this.$message({
                    message: '导入中',
                    type: 'success'
                })
            },
            handleCommand(command){
                if(command=='userCenter'){
                    // 个人中心
                    this.$router.push('/userCenter');
                }else{
                    // logout
                }
            },
            submitUpload() {
                console.log('submiting');
                this.$refs.upload.submit();
                this.$router.push('/editor');
            },
            addRouterToEditor() {
                this.$router.push('/editor');
            },
            goLogin(){
                this.$router.push('/login');
            }

        }
    }
</script>

<style scoped>
    .title{
        margin-top: 20%;
        font-size: 7vw;
        color: #333361;
        font-weight: bold;
        margin-bottom: 3%;
    }
    @media (max-height: 410px){
        .title{
            margin-top: 10%;
            font-size: 50px;
        }
    }
    @media (min-width: 500px) {
        .title{
            margin-top: 10%;
            font-size: 50px;
        }
    }
    .create-container {
        text-align: center;
        margin: auto;
        top: 0;
        left: 0;
        bottom: 0;
        right: 0;
        height: auto;
        width: 100%;
        position: absolute;
        background-size: 100% 100%;
        background-image: url("../../assets/kg-background3.jpg");
    }
    .createTab{
        width:47%;
        height: 35%;
        /*max-width:550px;*/
        min-width:287px;
        display: inline-block;
    }
    @media (min-width: 501px) {
        .createTab{
            width: 500px;
        }

    }
    /*@media (max-width: 500px){*/
    /*    .title{*/
    /*        margin-top: 20%;*/
    /*        font-size: 7vw;*/
    /*    }*/
    /*    !*.createTab{*!*/
    /*    !*    width: 90%;*!*/
    /*    !*    font-size: 15px;*!*/
    /*    !*}*!*/

    /*}*/
    .createButton{
        width: 100%;
    }
    .uploading {
        margin-top: 10px;
        width: 100%;
    }
    .right-info{
        height: 45px;
        float: right;
        margin-right: 20px;
        margin-top: 10px;
    }
    .user{
        display: flex;
        align-items: center;
        cursor: pointer;
    }
    .username{
        margin-left: 15px;
        font-size: 15px;
    }

</style>
