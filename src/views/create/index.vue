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
                 :stretch="true"
        >
            <el-tab-pane label="上传csv文件" name="importCSV">
                csv文件格式：节点-节点-关系 三元组
                <el-upload
                        drag
                        :action="uploadUrl"
                        class="uploading"
                        :on-success="handleCsvSuccess"
                        accept=".csv"
                        ref="upload"
                >
                    <i class="el-icon-upload"></i>
                    <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
                </el-upload>
            </el-tab-pane>
            <el-tab-pane label="使用推荐模板" name="useTemplate">
                我们为您推荐以下几个图谱模板，欢迎使用哦:
                <div style="margin-top: 20px">
                    <el-button plain type="primary" size="small" round @click="handleCurrentChange(1)">宝可梦图谱</el-button>
                    <el-button plain type="primary" size="small" round @click="handleCurrentChange(2)">化学图谱</el-button>
                    <el-button plain type="primary" size="small" round @click="handleCurrentChange(3)">金融图谱</el-button>
                    <el-button plain type="primary" size="small" round @click="handleCurrentChange(4)">电影图谱</el-button>
                </div>
            </el-tab-pane>
            <el-tab-pane label="使用说明" name="instructions">

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
    import {getTemplateAPI} from "../../api/domain";
    import {Message} from "element-ui";

    export default {
        name: "index.vue",
        data() {
            return {
                createType: 'importCSV',
                extractedText: '',
                uploadParam: {},
                // uploadURL:"http://106.15.93.81:8002/coinservice/file/getCsv/"+this.userInfo.id,
                templates:[
                    {
                        id:1,
                        name:'宝可梦图谱'
                    },
                    {
                        id:2,
                        name:'化学图谱'
                    },
                    {
                        id:3,
                        name:'金融图谱'
                    },
                    {
                        id:4,
                        name:'电影图谱'
                    }
                ],
                selectedTemplate:'',
            }
        },
        computed: {
            ...mapGetters([
                'activeIndex',
                'userInfo',
                'isLogin'
            ]),
            uploadUrl(){
                // return "http://106.15.93.81:8002/coinservice/file/getCsv/"+this.userInfo.id
                return "http://localhost:8002/coinservice/file/getCsv/"+this.userInfo.id
            }
        },
        methods: {
            ...mapMutations([
                'set_activeIndex',
                'set_userInfo',
                'set_isLogin',
            ]),
            ...mapActions([
                'logout'
            ]),
            handleCurrentChange(val) {
                // console.log(val);
                // console.log(val.id);
                this.selectedTemplate = val.id
            },
            handleCsvSuccess(res) {
                console.log(res);
                this.$message({
                    message: '导入完成',
                    type: 'success'
                })
            },
            handleCommand(command){
                if(command=='userCenter'){
                    // 个人中心
                    this.set_activeIndex('3');
                    this.$router.push('/userCenter');
                }else{
                    this.logout();
                }
            },
            submitUpload() {
                if(this.createType=='importCsv'){
                    console.log('submiting');
                    this.$refs.upload.submit();
                    this.$router.push('/editor');
                }else if(this.createType=='useTemplate'){
                    console.log("using template")
                    getTemplateAPI(this.selectedTemplate,this.userInfo.id).then(res=>{
                        console.log(res);
                        if(res.data.code == 200){
                            Message({
                                type:'success',
                                message:'创建成功'
                            })
                            this.$router.push('/editor')
                        }else{
                            Message({
                                type:'error',
                                message:'创建失败'
                            })
                        }
                    })
                }
            },
            addRouterToEditor() {
                this.set_activeIndex('2');
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
